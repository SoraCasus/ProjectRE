package com.soracasus.projectre.render.shaders.uniform;

import org.joml.Vector4f;
import org.lwjgl.opengl.GL20;

public class UniformVec4 extends Uniform {
	public UniformVec4 (String name) {
		super(name);
	}

	public void load(Vector4f vec) {
		GL20.glUniform4f(super.getLocation(), vec.x, vec.y, vec.z, vec.w);
	}
}
