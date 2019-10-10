package com.soracasus.projectre.render.shaders;

import com.soracasus.projectre.core.REFile;
import com.soracasus.projectre.render.shaders.uniform.UniformBoolean;
import com.soracasus.projectre.render.shaders.uniform.UniformMats;
import com.soracasus.projectre.render.shaders.uniform.UniformVec3;

public class DebugShader extends ShaderProgram {

	private static final REFile SHADER = new REFile("shaders/debug.res");

	public UniformMats mats = new UniformMats("u_mats");
	public UniformVec3 translation = new UniformVec3("u_translation");
	public UniformBoolean colliding = new UniformBoolean("u_colliding");

	public DebugShader () {
		super(SHADER, "in_position");
		super.storeUniformLocations(mats, translation, colliding);
	}
}
