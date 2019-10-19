package com.soracasus.projectre.render.shaders;

import com.soracasus.projectre.core.REFile;
import com.soracasus.projectre.render.shaders.uniform.UniformMat4;

public class WaterShader extends ShaderProgram {

	private static final REFile SHADER = new REFile("shaders/water.res");

	public UniformMat4 tfMat = new UniformMat4("u_tfMat");
	public UniformMat4 viewMat = new UniformMat4("u_viewMat");
	public UniformMat4 projMat = new UniformMat4("u_projMat");

	public WaterShader () {
		super(SHADER, "in_position");
		super.storeUniformLocations(tfMat, viewMat, projMat);
	}
}
