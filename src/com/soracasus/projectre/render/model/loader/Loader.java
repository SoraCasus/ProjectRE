package com.soracasus.projectre.render.model.loader;

import com.soracasus.projectre.core.REFile;
import com.soracasus.projectre.render.globjects.Vao;
import com.soracasus.projectre.render.model.Model;

public class Loader {

	public static Model load(REFile file) {

		ModelData data = AssimpLoader.load(file, false);
		assert data != null;

		Vao vao = new Vao();
		vao.bind(0, 1, 2, 3, 4);
		vao.createIndexBuffer(data.getIndices());
		vao.createAttribute(0, 3, data.getVertices());
		vao.createAttribute(1, 2, data.getTextureCoords());
		vao.createAttribute(2, 3, data.getNormals());
		vao.createAttribute(3, 3, data.getTangents());
		vao.createAttribute(4, 3, data.getBiTangents());
		vao.unbind(0, 1, 2, 3, 4);


		return new Model(vao, data);
	}

	public static Model load(REFile file, boolean flip) {

		ModelData data = AssimpLoader.load(file, flip);
		assert data != null;

		Vao vao = new Vao();
		vao.bind(0, 1, 2, 3, 4);
		vao.createIndexBuffer(data.getIndices());
		vao.createAttribute(0, 3, data.getVertices());
		vao.createAttribute(1, 2, data.getTextureCoords());
		vao.createAttribute(2, 3, data.getNormals());
		vao.createAttribute(3, 3, data.getTangents());
		vao.createAttribute(4, 3, data.getBiTangents());
		vao.unbind(0, 1, 2, 3, 4);


		return new Model(vao, data);
	}

}
