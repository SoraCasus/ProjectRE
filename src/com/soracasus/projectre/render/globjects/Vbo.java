package com.soracasus.projectre.render.globjects;

import com.soracasus.projectre.core.IDisposable;
import com.soracasus.projectre.core.RutikalEngine;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Vbo implements IDisposable {

	private int vboID;
	private int type;

	public Vbo(int type) {
		this.vboID = GL15.glGenBuffers();
		this.type = type;
		RutikalEngine.addDisposable(this);
	}

	public void storeData(float[] data) {
		assert data != null;
		FloatBuffer buf = BufferUtils.createFloatBuffer(data.length);
		buf.put(data);
		buf.flip();

		GL15.glBufferData(type, buf, GL15.GL_STATIC_DRAW);
	}

	public void storeData(int[] data) {
		IntBuffer buf = BufferUtils.createIntBuffer(data.length);
		buf.put(data);
		buf.flip();

		GL15.glBufferData(type, buf, GL15.GL_STATIC_DRAW);
	}

	public void bind() {
		GL15.glBindBuffer(type, vboID);
	}

	public void unbind() {
		GL15.glBindBuffer(type, 0);
	}

	@Override
	public void delete () {
		GL15.glDeleteBuffers(vboID);
	}

	@Override
	public String toString () {
		return "Vbo{" +
				"vboID=" + vboID +
				", type=" + type +
				'}';
	}
}
