package com.soracasus.projectre.render.shaders;

import com.soracasus.projectre.core.REFile;
import com.soracasus.projectre.render.shaders.uniform.UniformLight;
import com.soracasus.projectre.render.shaders.uniform.UniformMaterial;
import com.soracasus.projectre.render.shaders.uniform.UniformMats;
import com.soracasus.projectre.render.shaders.uniform.UniformVec3;

public class BasicShader extends ShaderProgram {

	private static final REFile SHADER = new REFile("shaders/basic.res");

	public UniformMats mats = new UniformMats("u_mats");
	public UniformLight light = new UniformLight("u_light");
	public UniformMaterial material = new UniformMaterial("u_material");
	public UniformVec3 viewPos = new UniformVec3("u_viewPos");

	public BasicShader () {
		super(SHADER, "in_position", "in_texCoord", "in_normal", "in_tangent", "in_biTangent");
		super.storeUniformLocations(mats, material, light, viewPos);
	}
}
