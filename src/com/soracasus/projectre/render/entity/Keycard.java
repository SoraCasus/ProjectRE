package com.soracasus.projectre.render.entity;

import com.soracasus.projectre.audio.AudioEngine;
import com.soracasus.projectre.core.REFile;
import com.soracasus.projectre.physics.AABB;
import com.soracasus.projectre.render.model.Model;
import com.soracasus.projectre.render.model.loader.Loader;
import com.soracasus.projectre.render.texture.Texture;
import org.joml.Vector3f;

public class Keycard extends Entity {


	private static Model KEYCARD_MODEL;

	static {
		KEYCARD_MODEL = Loader.load(new REFile("models/keycard.obj"));
		KEYCARD_MODEL.material.setDiffuse(Texture.newTexture(new REFile("textures/keycard.png")).anisotropic().create());
		KEYCARD_MODEL.material.setNormal(Texture.newTexture(new REFile("textures/null_normal.png")).anisotropic().create());
		KEYCARD_MODEL.material.setReflectivity(0.5F);
		KEYCARD_MODEL.material.setShineDamper(32F);
	}

	public static int keyCount = 0;

	public Keycard (Vector3f position) {
		super(KEYCARD_MODEL, new AABB(new Vector3f(-0.01F, -0.3F, -0.2F),
				new Vector3f(0.01F, 0.3F, 0.2F)), false);

		super.getTransform().setScale(0.25F);
		super.getTransform().setPosition(position);
	}

	@Override
	public void onCollide (Entity entity) {
		if (entity instanceof Player) {
			// Player p = (Player) entity;
			keyCount++;
			this.remove = true;
		}
	}

}
