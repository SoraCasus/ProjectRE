package com.soracasus.projectre.render.model;

import com.soracasus.projectre.render.globjects.Vao;
import com.soracasus.projectre.render.model.loader.ModelData;

public class Model {

	public Material material;
	private Vao vao;
	private ModelData data;

	public Model (Vao vao, ModelData data) {
		this.data = data;
		this.vao = vao;
		this.material = new Material();
	}

	public ModelData getData () {
		return data;
	}

	public Vao getVao () {
		return vao;
	}
}
