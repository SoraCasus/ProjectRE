package com.soracasus.projectre.physics;

import org.joml.Vector3f;

public class CollisionPacket {

	public Vector3f eRadius;

	// Information in R3Space
	public Vector3f r3Velocity;
	public Vector3f r3Position;

	// Information in eSpace
	public Vector3f velocity;
	public Vector3f normalizedVelocity;
	public Vector3f basePoint;

	// Hit information
	public boolean foundCollision;
	public float nearestDistance;
	public Vector3f intersectionPoint;

	public CollisionPacket checkTriangle (Vector3f p1, Vector3f p2, Vector3f p3) {
		// Make the plane containing the triangle
		Plane trianglePlane = new Plane(p1, p2, p3);

		if (trianglePlane.isFrontFacingTo(this.normalizedVelocity)) {
			// Get interval of plane intersection
			double t0, t1;
			boolean embeddedInPlane = false;

			double signedDistToTrianglePlane = trianglePlane.signedDistanceTo(this.basePoint);

			float normalDotVelocity = trianglePlane.normal.dot(this.velocity);

			// If sphere is travelling parallel
			if (normalDotVelocity == 0.0F) {
				if (Math.abs(signedDistToTrianglePlane) >= 1.0F) {
					// Sphere is not embedded in plane
					// no collision possible
					return this;
				} else {
					// Sphere is embedded in plane
					embeddedInPlane = true;
					t0 = 0.0;
					t1 = 1.0;
				}
			} else {
				// N dot D is not 0. Calculate intersection interval
				t0 = (-1.0 - signedDistToTrianglePlane) / normalDotVelocity;
				t1 = (1.0 - signedDistToTrianglePlane) / normalDotVelocity;

				// Swap so t0 < t1
				if (t1 > t0) {
					double tmp = t0;
					t0 = t1;
					t1 = tmp;
				}

				// Check that at least one result is in the range [0, 1]
				if (t0 > 1.0F || t1 < 0.0F) {
					// Both values are outside of range
					return this;
				}

				// Clamp to [0, 1]
				if (t0 < 0.0F) t0 = 0.0F;
				if (t1 < 0.0F) t1 = 0.0F;
				if (t0 > 1.0F) t0 = 1.0F;
				if (t1 > 1.0F) t1 = 1.0F;


				Vector3f collisionPoint = new Vector3f();
				boolean foundCollision = false;
				float t = 1.0F;


			}
		}
		return null;
	}

}


