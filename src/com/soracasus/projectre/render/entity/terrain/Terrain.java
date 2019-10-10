package com.soracasus.projectre.render.entity.terrain;

import com.soracasus.projectre.render.globjects.Vao;
import com.soracasus.projectre.util.Maths;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.awt.image.BufferedImage;

public class Terrain {

	private static final float SIZE = 800;
	private static final int VERTEX_COUNT = 128;
	private static final float MAX_HEIGHT = 40;
	private static final float MAX_PIXEL_COLOUR = 0x1000000;

	private float x;
	private float z;
	private Vao vao;
	private TerrainMaterial material;
	private Matrix4f transform;
	private float[][] heights;


	public Terrain (int gridX, int gridZ, TerrainMaterial material) {
		this.material = material;
		this.x = gridX * SIZE;
		this.z = gridZ * SIZE;
		this.transform = new Matrix4f();
		transform.translate(x, 0, z);
		this.vao = generateTerrain();
	}

	private Vao generateTerrain () {
		int v_count = VERTEX_COUNT;
		BufferedImage image = null;
		if (material.getHeightMap() != null) {
			image = material.getHeightMap();
			v_count = image.getHeight();
		}

		this.heights = new float[v_count][v_count];

		int count = v_count * v_count;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textures = new float[count * 2];
		int[] indices = new int[6 * (v_count - 1) * (v_count - 1)];
		int vertexPointer = 0;

		for (int i = 0; i < v_count; i++) {
			for (int j = 0; j < v_count; j++) {
				vertices[vertexPointer * 3] = (float) j / ((float) v_count - 1) * SIZE;
				float height = image != null ? getHeight(j, i) : 0;
				vertices[vertexPointer * 3 + 1] = height;
				heights[j][i] = height;
				vertices[vertexPointer * 3 + 2] = (float) i / ((float) v_count - 1) * SIZE;

				Vector3f norm = image != null ? calculateNormal(j, i, image) : new Vector3f(0, 1, 0);
				normals[vertexPointer * 3] = norm.x;
				normals[vertexPointer * 3 + 1] = norm.y;
				normals[vertexPointer * 3 + 2] = norm.z;

				textures[vertexPointer * 2] = (float) j / ((float) v_count - 1);
				textures[vertexPointer * 2 + 1] = (float) i / ((float) v_count - 1);
				vertexPointer++;
			}
		}

		int pointer = 0;
		for (int gz = 0; gz < v_count - 1; gz++) {
			for (int gx = 0; gx < v_count - 1; gx++) {
				int topLeft = (gz * v_count) + gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz + 1) * v_count) + gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}

		Vao vao = new Vao();
		vao.bind(0, 1, 2);
		vao.createIndexBuffer(indices);
		vao.createAttribute(0, 3, vertices);
		vao.createAttribute(1, 2, textures);
		vao.createAttribute(2, 3, normals);
		vao.unbind(0, 1, 2);

		return vao;
	}

	private Vector3f calculateNormal (int x, int z, BufferedImage image) {
		float heightL = getHeight(x - 1, z);
		float heightR = getHeight(x + 1, z);
		float heightD = getHeight(x, z - 1);
		float heightU = getHeight(x, z + 1);
		Vector3f normal = new Vector3f(heightL - heightR, 2f, heightD - heightU);
		normal.normalize();
		return normal;
	}

	public float getHeightAtPoint (float worldX, float worldZ) {
		float terrainX = worldX - this.x;
		float terrainZ = worldZ - this.z;
		float gridSquareSize = SIZE / ((float) heights.length - 1);
		int gridX = (int) Math.floor(terrainX / gridSquareSize);
		int gridZ = (int) Math.floor(terrainZ / gridSquareSize);
		if (gridX >= heights.length - 1 || gridZ >= heights.length - 1 || gridX < 0 || gridZ < 0) {
			return 0;
		}

		float xCoord = (terrainX % gridSquareSize) / gridSquareSize;
		float zCoord = (terrainZ % gridSquareSize) / gridSquareSize;
		float answer;
		if (xCoord <= (1 - zCoord)) {
			answer = Maths.barryCentric(new Vector3f(0, heights[gridX][gridZ], 0), new Vector3f(1, heights[gridX + 1][gridZ], 0), new Vector3f(0, heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
		} else {
			answer = Maths.barryCentric(new Vector3f(1, heights[gridX + 1][gridZ], 0), new Vector3f(1, heights[gridX + 1][gridZ + 1], 1), new Vector3f(0, heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
		}
		return answer;
	}

	private float getHeight (int x, int z) {
		BufferedImage image = material.getHeightMap();
		if (x < 0 || x >= image.getHeight() || z < 0 || z >= image.getHeight()) {
			return 0;
		}
		float height = image.getRGB(x, z);
		height += MAX_PIXEL_COLOUR / 2F;
		height /= MAX_PIXEL_COLOUR / 2F;
		height *= MAX_HEIGHT;
		return height;
	}

	public float getX () {
		return x;
	}

	public float getZ () {
		return z;
	}

	public Vao getVao () {
		return vao;
	}

	public TerrainMaterial getMaterial () {
		return material;
	}

	public Matrix4f getTransform () {
		return this.transform;
	}
}
