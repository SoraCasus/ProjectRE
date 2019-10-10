package com.soracasus.projectre.render.entity;

import com.soracasus.projectre.audio.AudioEngine;
import com.soracasus.projectre.core.REFile;
import com.soracasus.projectre.physics.AABB;
import com.soracasus.projectre.render.model.Model;
import com.soracasus.projectre.render.model.loader.Loader;
import com.soracasus.projectre.render.texture.Texture;
import org.joml.Vector3f;

public class Rock3 extends Entity {


	private static Model ROCK_MODEL;

	static {
		ROCK_MODEL = Loader.load(new REFile("models/rock_03.obj"));
		ROCK_MODEL.material.diffuse = Texture.newTexture(new REFile("textures/rock03_diffuse.png")).anisotropic().create();
		ROCK_MODEL.material.normal = Texture.newTexture(new REFile("textures/rock03_normal.png")).anisotropic().create();
		ROCK_MODEL.material.reflectivity = 0.5F;
		ROCK_MODEL.material.shineDamper = 32F;
	}

	public Rock3 (Vector3f position) {
		super(ROCK_MODEL, new AABB(new Vector3f(-1.0F, -0.5F, -1.5F),
				new Vector3f(0.5F, 3.5F, 0.8F)), true);

		super.getTransform().setScale(0.01F);
		super.getTransform().setPosition(position);
		super.getTransform().setRotation(new Vector3f(0, 0, 45));

		generateVariance();
	}

	@Override
	public void onCollide (Entity e) {
		System.out.println(toString());
		if (e instanceof Player) {
			Player p = (Player) e;
			p.playSound(AudioEngine.crashSound);
		}
	}
}
