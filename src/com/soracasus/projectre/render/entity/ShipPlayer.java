package com.soracasus.projectre.render.entity;

import com.soracasus.projectre.audio.AudioEngine;
import com.soracasus.projectre.audio.SoundSource;
import com.soracasus.projectre.core.MouseInput;
import com.soracasus.projectre.core.REFile;
import com.soracasus.projectre.core.Window;
import com.soracasus.projectre.physics.AABB;
import com.soracasus.projectre.render.entity.terrain.Terrain;
import com.soracasus.projectre.render.model.Model;
import com.soracasus.projectre.render.model.loader.Loader;
import com.soracasus.projectre.render.texture.Texture;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class ShipPlayer extends Entity {

	private static Model PLAYER_MODEL;

	static {
		PLAYER_MODEL = Loader.load(new REFile("models/player.obj"), false);
		PLAYER_MODEL.material.diffuse = Texture.newTexture(new REFile("textures/playerTexture.png")).anisotropic().create();
		PLAYER_MODEL.material.normal = Texture.newTexture(new REFile("textures/null_normal.png")).anisotropic().create();
		PLAYER_MODEL.material.reflectivity = 1F;
		PLAYER_MODEL.material.shineDamper = 10F;
	}

	private static final float RUN_SPEED = 0.08F;
	private static final float TURN_SPEED = 2;
	private static final float GRAVITY = -50;
	private static final float JUMP_POWER = 30;

	private float currentSpeed = 0;
	private float currentTurnSpeed = 2;
	private float upwardsSpeed = 0;

	private boolean isAirborn = false;

	private SoundSource soundSource;

	public ShipPlayer (Vector3f position) {
		super(PLAYER_MODEL, new AABB(
				new Vector3f(-0.1F, 0.0F, -0.1F), new Vector3f(0.1F, 0.8F, 0.1F)), true);
		super.getTransform().setPosition(position);
		super.getTransform().setScale(0.1F);


		soundSource = new SoundSource(0, 1, 2);
		// Set the sound buffer to step sound
		soundSource.setLooping(true);
		soundSource.setVolume(0.75F);
		soundSource.setPosition(super.getTransform().getPosition());


	}

	@Override
	public void onCollide (Entity entity) {
		// Nothing should happen since the PLayer should never collide with its self
	}

	public void update (float dt, List<Entity> entities, Terrain terrain) {
		super.getTransform().rotate(new Vector3f(0, currentTurnSpeed * dt, 0));
		float distance = currentSpeed * dt;
		float dx = distance * (float) Math.sin(Math.toRadians(getTransform().getRotation().y));
		float dz = distance * (float) Math.cos(Math.toRadians(getTransform().getRotation().y));
		float dy = 0;

		super.getTransform().move(new Vector3f(dx, 0, dz));

		upwardsSpeed += GRAVITY * dt;
		super.getTransform().move(new Vector3f(0, upwardsSpeed * dt, 0));

		Vector3f pos = super.getTransform().getPosition();

		float terrainHeight = terrain.getHeightAtPoint(pos.x, pos.z);
		if(pos.y < terrainHeight) {
			upwardsSpeed = 0;
			super.getTransform().setPosition(new Vector3f(pos.x, terrainHeight, pos.z));
			isAirborn = false;
		}

		AudioEngine.setListenerData(super.getTransform().getPosition());

		soundSource.setPosition(super.getTransform().getPosition());

	}

	public void input (final Window window, MouseInput mouseInput) {
		if (window.isKeyDown(GLFW.GLFW_KEY_W)) {
			this.currentSpeed = RUN_SPEED;
		} else if (window.isKeyDown(GLFW.GLFW_KEY_S)) {
			this.currentSpeed = -RUN_SPEED;
		} else {
			this.currentSpeed = 0;
		}

		if (this.currentSpeed != 0) {
			if (!soundSource.isPlaying())
				soundSource.play();
		} else {
			soundSource.pause();
		}

		if (window.isKeyDown(GLFW.GLFW_KEY_D)) {
			this.currentTurnSpeed = -TURN_SPEED;
		} else if (window.isKeyDown(GLFW.GLFW_KEY_A)) {
			this.currentTurnSpeed = TURN_SPEED;
		} else {
			this.currentTurnSpeed = 0;
		}

		if(window.isKeyDown(GLFW.GLFW_KEY_SPACE) && !isAirborn) {
			this.upwardsSpeed = JUMP_POWER;
			isAirborn = true;
		}
	}

}
