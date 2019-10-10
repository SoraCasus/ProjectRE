package com.soracasus.projectre.render.shaders;

import com.soracasus.projectre.core.REFile;
import com.soracasus.projectre.render.shaders.uniform.UniformMat4;

public class SkyboxShader extends ShaderProgram {

	private static final REFile SHADER = new REFile("shaders/skybox.res");

	public UniformMat4 projMat = new UniformMat4("u_projMat");
	public UniformMat4 viewMat = new UniformMat4("u_viewMat");

	public SkyboxShader () {
		super(SHADER, "in_position");
		super.storeUniformLocations(projMat, viewMat);
	}

}
