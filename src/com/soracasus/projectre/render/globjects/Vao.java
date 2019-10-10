package com.soracasus.projectre.render.globjects;

import com.soracasus.projectre.core.IDisposable;
import com.soracasus.projectre.core.RutikalEngine;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.List;

public class Vao implements IDisposable {

	private static final int BYTES_PER_FLOAT = 4;

	public int vaoID;
	private List<Vbo> dataVbos;
	private Vbo indexVbo;
	private int indexCount;

	public Vao() {
		this.vaoID = GL30.glGenVertexArrays();
		this.dataVbos = new ArrayList<>();
		RutikalEngine.addDisposable(this);
	}

	public void bind() {
		GL30.glBindVertexArray(vaoID);
	}

	public void bind(int... attributes) {
		bind();
		for(int i : attributes)
			GL20.glEnableVertexAttribArray(i);
	}

	public void unbind(int... attributes) {
		for(int i : attributes)
			GL20.glDisableVertexAttribArray(i);
		 unbind();
	}

	public void unbind() {
		GL30.glBindVertexArray(0);
	}

	public void createIndexBuffer(int[] indices) {
		this.indexVbo = new Vbo(GL15.GL_ELEMENT_ARRAY_BUFFER);
		indexVbo.bind();
		indexVbo.storeData(indices);
		this.indexCount = indices.length;
	}

	public void createAttribute(int attribute, int dims, float[] data) {
		Vbo dataVbo = new Vbo(GL15.GL_ARRAY_BUFFER);
		dataVbo.bind();
		dataVbo.storeData(data);
		GL20.glVertexAttribPointer(attribute, dims, GL11.GL_FLOAT, false, dims * BYTES_PER_FLOAT, 0);
		dataVbo.unbind();
		dataVbos.add(dataVbo);
	}

	public void createAttribute(int attribute, int dims, int[] data) {
		Vbo dataVbo = new Vbo(GL15.GL_ARRAY_BUFFER);
		dataVbo.bind();
		dataVbo.storeData(data);
		GL30.glVertexAttribIPointer(attribute, dims, GL11.GL_INT, dims * BYTES_PER_FLOAT, 0);
		dataVbo.unbind();
		dataVbos.add(dataVbo);
	}

	@Override
	public void delete () {
		GL30.glDeleteVertexArrays(vaoID);
	}

	public int getIndexCount () {
		return indexCount;
	}

	@Override
	public String toString () {
		return "Vao{" +
				"vaoID=" + vaoID +
				", indexVbo=" + indexVbo +
				", indexCount=" + indexCount +
				'}';
	}
}
