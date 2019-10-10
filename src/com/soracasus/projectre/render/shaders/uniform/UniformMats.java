package com.soracasus.projectre.render.shaders.uniform;

import org.joml.Matrix4f;

public class UniformMats extends UniformStruct {

	public UniformMat4 tfMat;
	public UniformMat4 viewMat;
	public UniformMat4 projMat;

	public UniformMats (String name) {
		super(name);

		tfMat = new UniformMat4(name + ".tfMat");
		viewMat = new UniformMat4(name + ".viewMat");
		projMat = new UniformMat4(name + ".projMat");

		setMembers(tfMat, projMat, viewMat);
	}

	public void load(Matrix4f tfMat, Matrix4f viewMat, Matrix4f projMat) {
		this.tfMat.load(tfMat);
		this.viewMat.load(viewMat);
		this.projMat.load(projMat);
	}

}
