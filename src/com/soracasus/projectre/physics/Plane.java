package com.soracasus.projectre.physics;

import org.joml.Vector3d;
import org.joml.Vector3f;

public class Plane {

	float[] equasion;
	Vector3f origin;
	Vector3f normal;

	public Plane (Vector3f origin, Vector3f normal) {
		this.normal = normal;
		this.origin = origin;

		this.equasion = new float[4];
		this.equasion[0] = normal.x;
		this.equasion[1] = normal.y;
		this.equasion[2] = normal.z;
		this.equasion[3] = -(normal.x * origin.x + normal.y * origin.y + normal.z * origin.z);

	}

	public Plane (Vector3f p1, Vector3f p2, Vector3f p3) {
		this.normal = (p1.sub(p2)).cross(p3.sub(p1));
		this.normal.normalize();

		this.origin = p1;
		this.equasion = new float[4];
		this.equasion[0] = normal.x;
		this.equasion[1] = normal.y;
		this.equasion[2] = normal.z;
		this.equasion[3] = -(normal.x * origin.x + normal.y * origin.y + normal.z * origin.z);
	}

	public boolean isFrontFacingTo (Vector3f direction) {
		float dot = normal.dot(direction);
		return dot <= 0;
	}

	public float signedDistanceTo (Vector3f point) {
		return (point.dot(normal)) + equasion[3];
	}

}
