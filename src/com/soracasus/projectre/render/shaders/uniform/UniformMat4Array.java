package com.soracasus.projectre.render.shaders.uniform;

import org.joml.Matrix4f;

public class UniformMat4Array extends Uniform {

	private UniformMat4[] matrixUniforms;

	public UniformMat4Array(String name, int size) {
		super(name);
		this.matrixUniforms = new UniformMat4[size];
		for(int i = 0; i < size; i++) {
			matrixUniforms[i] = new UniformMat4(name + "[" + i + "]");
		}
	}

	@Override
	public void storeUniformLocation(int programID) {
		for(UniformMat4 u : matrixUniforms)
			u.storeUniformLocation(programID);
	}

	public void load(Matrix4f[] matrices) {
		for(int i = 0; i < matrixUniforms.length; i++) {
			matrixUniforms[i].load(matrices[i]);
		}
	}

}
