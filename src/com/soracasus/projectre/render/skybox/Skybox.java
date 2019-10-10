package com.soracasus.projectre.render.skybox;

import com.soracasus.projectre.render.globjects.Vao;
import com.soracasus.projectre.render.texture.Texture;

public class Skybox {

	private Vao cube;
	private Texture texture;

	public Skybox(Texture cubeMapTexture, float size) {
		this.cube = CubeGenerator.generateCube(size);
		this.texture = cubeMapTexture;
	}

	public Vao getCube () {
		return cube;
	}

	public Texture getTexture () {
		return texture;
	}
}
