package com.soracasus.projectre.render.entity;

import com.soracasus.projectre.audio.AudioEngine;
import com.soracasus.projectre.core.REFile;
import com.soracasus.projectre.physics.AABB;
import com.soracasus.projectre.render.model.Model;
import com.soracasus.projectre.render.model.loader.Loader;
import com.soracasus.projectre.render.texture.Texture;
import org.joml.Vector3f;

public class Rock1 extends Entity {

	private static Model ROCK_MODEL;

	static {
		ROCK_MODEL = Loader.load(new REFile("models/rock_01.obj"));
		ROCK_MODEL.material.setDiffuse(Texture.newTexture(new REFile("textures/rock01_diffuse.png")).anisotropic().create());
		ROCK_MODEL.material.setNormal(Texture.newTexture(new REFile("textures/rock01_normal.png")).anisotropic().create());
		ROCK_MODEL.material.setReflectivity(0.5F);
		ROCK_MODEL.material.setShineDamper(32F);
	}

	public Rock1 (Vector3f position) {
		super(ROCK_MODEL, new AABB(new Vector3f(-0.6F, -0.6F, -0.6F),
				new Vector3f(0.6F, 0.6F, 0.6F)), true);

		super.getTransform().setScale(0.01F);
		super.getTransform().setPosition(position);

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
