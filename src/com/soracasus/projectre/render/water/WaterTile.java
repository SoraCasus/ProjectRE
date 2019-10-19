package com.soracasus.projectre.render.water;


public class WaterTile {

	public static final float TILE_SIZE = 800;

	private float height;
	private float x, z;

	/**
	 * This represents the quad in which the water is rendered upon.
	 *
	 * @param height
	 * 		- What level in the world to place the quad
	 * @param x
	 * 		- The center X coordinate
	 * @param z
	 * 		- The center Z coordinate
	 * @param size
	 * 		- The scaled size (half length of one edge)
	 */
	public WaterTile (float height, float x, float z, float size) {
		this.height = height;
		this.x = x;
		this.z = z;
	}

	public float getHeight () {
		return height;
	}

	public float getX () {
		return x;
	}

	public float getZ () {
		return z;
	}

}
