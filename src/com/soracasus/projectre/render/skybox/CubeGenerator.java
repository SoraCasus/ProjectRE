package com.soracasus.projectre.render.skybox;

import com.soracasus.projectre.physics.AABB;
import com.soracasus.projectre.render.globjects.Vao;

public class CubeGenerator {

	private static final int VERTEX_COUNT = 8;
	private static final int[] INDICES = {
			0, 1, 3,
			1, 2, 3,
			1, 5, 2,
			2, 5, 6,
			4, 7, 5,
			5, 7, 6,
			0, 3, 4,
			4, 3, 7,
			7, 3, 6,
			6, 3, 2,
			4, 5, 0,
			0, 5, 1
	};

	public static Vao generateCube (float size) {
		Vao vao = new Vao();
		vao.bind(0);
		vao.createAttribute(0, 3, getVertexPositions(size));
		vao.createIndexBuffer(INDICES);
		vao.unbind(0);
		return vao;
	}

	private static float[] getVertexPositions (float size) {
		return new float[]{
				-size, size, size,
				size, size, size,
				size, -size, size,
				-size, -size, size,
				-size, size, -size,
				size, size, -size,
				size, -size, -size,
				-size, -size, -size
		};
	}

	public static Vao generateAABBDebugBox (AABB box) {
		float[] vertices = {
				box.getMinX(), box.getMaxY(), box.getMaxZ(),
				box.getMaxX(), box.getMaxY(), box.getMaxZ(),
				box.getMaxX(), box.getMinY(), box.getMaxZ(),
				box.getMinX(), box.getMinY(), box.getMaxZ(),

				box.getMinX(), box.getMaxY(), box.getMinZ(),
				box.getMaxX(), box.getMaxY(), box.getMinZ(),
				box.getMaxX(), box.getMinY(), box.getMinZ(),
				box.getMinX(), box.getMinY(), box.getMinZ()
		};

		Vao vao = new Vao();
		vao.bind(0);
		vao.createIndexBuffer(INDICES);
		vao.createAttribute(0, 3, vertices);
		vao.unbind(0);
		return vao;
	}


}
