package com.soracasus.projectre.render.shaders.uniform;

public abstract class UniformStruct extends Uniform {

	private Uniform[] members;

	public UniformStruct (String name) {
		super(name);
	}

	public void setMembers (Uniform... uniforms) {
		this.members = uniforms;
	}

	@Override
	public void storeUniformLocation (int programID) {
		for (Uniform u : members)
			u.storeUniformLocation(programID);
	}
}
