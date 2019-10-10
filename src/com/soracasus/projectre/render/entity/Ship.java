package com.soracasus.projectre.render.entity;

import com.soracasus.projectre.audio.AudioEngine;
import com.soracasus.projectre.audio.SoundSource;
import com.soracasus.projectre.core.REFile;
import com.soracasus.projectre.core.StateManager;
import com.soracasus.projectre.physics.AABB;
import com.soracasus.projectre.render.model.Model;
import com.soracasus.projectre.render.model.loader.Loader;
import com.soracasus.projectre.render.texture.Texture;
import org.joml.Vector3f;

public class Ship extends Entity {

	private static Model SHIP_MODEL;

	static {
		SHIP_MODEL = Loader.load(new REFile("models/FreighterShip.fbx"));
		SHIP_MODEL.material.diffuse = Texture.newTexture(new REFile("textures/Oxar_Diffuse.png")).anisotropic().create();
		SHIP_MODEL.material.normal = Texture.newTexture(new REFile("textures/Oxar_Normal.png")).anisotropic().create();
		SHIP_MODEL.material.reflectivity = 0.5F;
		SHIP_MODEL.material.shineDamper = 32F;
	}

	private SoundSource soundSource;

	public Ship (Vector3f position) {
		super(SHIP_MODEL, new AABB(new Vector3f(-10.0F, -10.0F, -29.5F),
				new Vector3f(10.0F, 10.0F, 29.5F)), true);


		super.getTransform().setScale(1.0F);
		super.getTransform().setPosition(position);

		this.soundSource = new SoundSource(0, 1, 2);
		this.soundSource.setBuffer(AudioEngine.crashSound);
		this.soundSource.setLooping(false);
		this.soundSource.setPosition(super.getTransform().getPosition());
		this.soundSource.setPitch(0.8F);
	}

	@Override
	public void onCollide (Entity entity) {
		// If keys are not collected
		soundSource.setPosition(entity.getTransform().getPosition());

		if (!soundSource.isPlaying())
			soundSource.play();

		if(entity instanceof Player) {
			Player p = (Player)entity;
			p.bgMusic.stop();
			p.sfxSource.stop();
			p.soundSource.stop();
		}

		soundSource.stop();
		// If keys are collected
		StateManager.setState(StateManager.shipState);
	}
}
