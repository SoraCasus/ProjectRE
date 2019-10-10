package com.soracasus.projectre.render.shaders.uniform;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL20;

public class UniformVec3 extends Uniform {

	private boolean used = false;
	private Vector3f vec;

	public UniformVec3 (String name) {
		super(name);
	}

	public void load (Vector3f vec) {
		if (!used || !vec.equals(this.vec)) {
			GL20.glUniform3f(super.getLocation(), vec.x, vec.y, vec.z);
			used = true;
			this.vec = vec;
		}
	}

}
