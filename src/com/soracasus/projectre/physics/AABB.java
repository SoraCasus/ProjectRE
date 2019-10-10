package com.soracasus.projectre.physics;

import com.soracasus.projectre.render.globjects.Vao;
import com.soracasus.projectre.render.skybox.CubeGenerator;
import org.joml.Vector3f;

public class AABB {


	private float minX, maxX;
	private float minY, maxY;
	private float minZ, maxZ;

	private Vao debugBox;
	private boolean colliding;

	public AABB (Vector3f min, Vector3f max) {
		this.minX = min.x;
		this.maxX = max.x;
		this.minY = min.y;
		this.maxY = max.y;
		this.minZ = min.z;
		this.maxZ = max.z;
		debugBox = CubeGenerator.generateAABBDebugBox(this);
		this.colliding = false;
	}

	public Vao getDebugBox () {
		return debugBox;
	}

	public void swapX() {
		float tmp = minX;
		minX = -maxX;
		maxX = -tmp;
		debugBox = CubeGenerator.generateAABBDebugBox(this);
	}

	public void swapY() {
		float tmp = minY;
		minY = -maxY;
		maxY = -tmp;
		debugBox = CubeGenerator.generateAABBDebugBox(this);
	}

	public void swapZ() {
		float tmp = minZ;
		minZ = -maxZ;
		maxZ = -tmp;
		debugBox = CubeGenerator.generateAABBDebugBox(this);
	}

	public static CollideData intersects (AABB a1, Vector3f p1, AABB a2, Vector3f p2) {
		float axmin = a1.minX + p1.x;
		float axmax = a1.maxX + p1.x;
		float bxmin = a2.minX + p2.x;
		float bxmax = a2.maxX + p2.x;
		boolean xAxis = (axmin >= bxmin && axmin <= bxmax) || (axmax >= bxmin && axmax <= bxmax);
		xAxis = xAxis || (axmin <= bxmin && axmax >= bxmax);

		float aymin = a1.minY + p1.y;
		float aymax = a1.maxY + p1.y;
		float bymin = a2.minY + p2.y;
		float bymax = a2.maxY + p2.y;
		boolean yAxis = (aymin >= bymin && aymin <= bymax) || (aymax >= bymin && aymax <= bymax);
		yAxis = yAxis || (aymin <= bymin && aymax >= bymax);

		float azmin = a1.minZ + p1.z;
		float azmax = a1.maxZ + p1.z;
		float bzmin = a2.minZ + p2.z;
		float bzmax = a2.maxZ + p2.z;
		boolean zAxis = (azmin >= bzmin && azmin <= bzmax) || (azmax >= bzmin && azmax <= bzmax);
		zAxis = zAxis || (azmin <= bzmin && azmax >= bzmax);

		boolean colliding = xAxis && yAxis && zAxis;

		a1.colliding = colliding;
		a2.colliding = colliding;

		CollideData data = new CollideData();
		data.xAxis = xAxis;
		data.yAxis = yAxis;
		data.zAxis = zAxis;
		data.collides = colliding;

		return data;
	}

	public float getMinX () {
		return minX;
	}

	public void setMinX (float minX) {
		this.minX = minX;
		debugBox = CubeGenerator.generateAABBDebugBox(this);
	}

	public float getMaxX () {
		return maxX;
	}

	public void setMaxX (float maxX) {
		this.maxX = maxX;
		debugBox = CubeGenerator.generateAABBDebugBox(this);
	}

	public float getMinY () {
		return minY;
	}

	public void setMinY (float minY) {
		this.minY = minY;
		debugBox = CubeGenerator.generateAABBDebugBox(this);
	}

	public float getMaxY () {
		return maxY;
	}

	public void setMaxY (float maxY) {
		this.maxY = maxY;
		debugBox = CubeGenerator.generateAABBDebugBox(this);
	}

	public float getMinZ () {
		return minZ;
	}

	public void setMinZ (float minZ) {
		this.minZ = minZ;
		debugBox = CubeGenerator.generateAABBDebugBox(this);
	}

	public float getMaxZ () {
		return maxZ;
	}

	public void setMaxZ (float maxZ) {
		this.maxZ = maxZ;
		debugBox = CubeGenerator.generateAABBDebugBox(this);
	}

	public boolean getColliding () {
		return colliding;
	}

	public void setCollided (boolean collided) {
		this.colliding = collided;
	}

	public static class CollideData {
		public boolean xAxis;
		public boolean yAxis;
		public boolean zAxis;
		public boolean collides;
	}

}
