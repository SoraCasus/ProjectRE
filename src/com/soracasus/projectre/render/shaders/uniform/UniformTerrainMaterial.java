package com.soracasus.projectre.render.shaders.uniform;

import com.soracasus.projectre.render.entity.terrain.TerrainMaterial;

public class UniformTerrainMaterial extends UniformStruct {

	private UniformSampler blendMap;
	private UniformSampler rTexture;
	private UniformSampler gTexture;
	private UniformSampler bTexture;
	private UniformSampler bgTexture;

	public UniformTerrainMaterial (String name) {
		super(name);

		blendMap = new UniformSampler(name + ".blendMap");
		rTexture = new UniformSampler(name + ".rTexture");
		gTexture = new UniformSampler(name + ".gTexture");
		bTexture = new UniformSampler(name + ".bTexture");
		bgTexture = new UniformSampler(name + ".bgTexture");

		super.setMembers(blendMap, rTexture, gTexture, bTexture, bgTexture);
	}

	public void load() {
		blendMap.load(0);
		rTexture.load(1);
		gTexture.load(2);
		bTexture.load(3);
		bgTexture.load(4);
	}
}
