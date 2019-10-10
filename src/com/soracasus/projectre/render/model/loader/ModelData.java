package com.soracasus.projectre.render.model.loader;

public class ModelData {

	private static final int DIMENSIONS = 3;

	private float[] vertices;
	private float[] textureCoords;
	private float[] normals;
	private float[] tangents;
	private float[] biTangents;
	private int[] indices;
	private float xDist;
	private float yDist;
	private float zDist;

	public ModelData (float[] vertices, float[] textureCoords, float[] normals, float[] tangents, float[] biTangents, int[] indices, float xDist, float yDist, float zDist) {
		this.vertices = vertices;
		this.textureCoords = textureCoords;
		this.normals = normals;
		this.indices = indices;
		this.tangents = tangents;
		this.biTangents = biTangents;
		this.xDist = xDist;
		this.yDist = yDist;
		this.zDist = zDist;
	}

	public int getVertexCount () {
		return vertices.length / DIMENSIONS;
	}

	public float[] getVertices () {
		return vertices;
	}

	public float[] getTextureCoords () {
		return textureCoords;
	}

	public float[] getNormals () {
		return normals;
	}

	public int[] getIndices () {
		return indices;
	}

	public float[] getTangents () {
		return tangents;
	}

	public float[] getBiTangents () {
		return biTangents;
	}

	public float getxDist () {
		return xDist;
	}

	public float getyDist () {
		return yDist;
	}

	public float getzDist () {
		return zDist;
	}
}
