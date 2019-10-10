package com.soracasus.projectre.render.shaders;

import com.soracasus.projectre.core.REFile;
import com.soracasus.projectre.render.shaders.uniform.UniformLight;
import com.soracasus.projectre.render.shaders.uniform.UniformMaterial;
import com.soracasus.projectre.render.shaders.uniform.UniformMats;
import com.soracasus.projectre.render.shaders.uniform.UniformTerrainMaterial;

public class TerrainShader extends ShaderProgram {

	private static final REFile SHADER = new REFile("shaders/terrain.res");

	public UniformMats mats = new UniformMats("u_mats");
	public UniformTerrainMaterial material = new UniformTerrainMaterial("u_material");
	public UniformLight light = new UniformLight("u_light");

	public TerrainShader () {
		super(SHADER, "in_position", "in_textures", "in_normal");
		super.storeUniformLocations(mats, material, light);
	}
}
