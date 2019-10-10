package com.soracasus.projectre.render.entity;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transform {

	private Vector3f position;

	private Vector3f rotation;

	private float scale;

	private Matrix4f tfMat;

	public Transform () {
		this.position = new Vector3f();
		this.rotation = new Vector3f();
		this.scale = 1F;
		this.tfMat = new Matrix4f().identity();
		updateTFMat();
	}

	private void updateTFMat () {
		tfMat.identity();
		tfMat.translate(position);
		tfMat.rotate((float) Math.toRadians(rotation.x), new Vector3f(1, 0, 0));
		tfMat.rotate((float) Math.toRadians(rotation.y), new Vector3f(0, 1, 0));
		tfMat.rotate((float) Math.toRadians(rotation.z), new Vector3f(0, 0, 1));
		tfMat.scale(scale);
	}

	public void move (Vector3f delta) {

		position.add(delta, position);
		updateTFMat();

	}

	public void rotate (Vector3f delta) {
		rotation.add(delta, rotation);
		updateTFMat();
	}

	public Matrix4f getTfMat () {
		return tfMat;
	}

	public Vector3f getPosition () {
		return position;
	}

	public void setPosition (Vector3f position) {
		this.position = position;
		updateTFMat();
	}

	public Vector3f getRotation () {
		return rotation;
	}

	public void setRotation (Vector3f rotation) {
		this.rotation = rotation;
		updateTFMat();
	}

	public float getScale () {
		return scale;
	}

	public void setScale (float scale) {
		this.scale = scale;
		updateTFMat();
	}

	@Override
	public String toString () {
		return "Transform{" +
				"position=" + position +
				", rotation=" + rotation +
				", scale=" + scale +
				'}';
	}
}
