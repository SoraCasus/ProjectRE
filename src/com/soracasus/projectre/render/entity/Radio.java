package com.soracasus.projectre.render.entity;

import com.soracasus.projectre.audio.AudioEngine;
import com.soracasus.projectre.audio.SoundSource;
import com.soracasus.projectre.core.REFile;
import com.soracasus.projectre.physics.AABB;
import com.soracasus.projectre.render.model.Model;
import com.soracasus.projectre.render.model.loader.Loader;
import com.soracasus.projectre.render.texture.Texture;
import org.joml.Vector3f;

public class Radio extends Entity {


	private static Model RADIO_MODEL;

	static {
		RADIO_MODEL = Loader.load(new REFile("models/HT-SF_medium.obj"));
		RADIO_MODEL.material.diffuse = Texture.newTexture(new REFile("textures/radio_diffuse.png")).anisotropic().create();
		RADIO_MODEL.material.normal = Texture.newTexture(new REFile("textures/radio_normal.png")).anisotropic().create();
		RADIO_MODEL.material.reflectivity = 0.5F;
		RADIO_MODEL.material.shineDamper = 32F;
	}

	public static int radioCount = 0;

	public Radio (Vector3f position) {
		super(RADIO_MODEL, new AABB(new Vector3f(-0.3F, 0.0F, -0.1F),
				new Vector3f(0.3F, 0.7F, 0.1F)), false);

		super.getTransform().setScale(0.01F);
		super.getTransform().setPosition(position);
	}

	@Override
	public void onCollide (Entity entity) {
		if (entity instanceof Player) {
			Player p = (Player) entity;
			if(radioCount < AudioEngine.radioLog.length)
				p.playRadioLog(AudioEngine.radioLog[radioCount++]);
			this.remove = true;
		}
	}

}
