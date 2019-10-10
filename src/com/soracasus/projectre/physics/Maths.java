package com.soracasus.projectre.physics;

import org.joml.Vector3f;

public class Maths {

	public static boolean checkPointInTriangle (Vector3f point, Vector3f pa, Vector3f pb, Vector3f pc) {
		Vector3f e10 = pb.sub(pa);
		Vector3f e20 = pc.sub(pa);

		float a = e10.dot(e10);
		float b = e10.dot(e20);
		float c = e20.dot(e20);
		float ac_bb = (a * c) - (b * b);

		Vector3f vp = new Vector3f(point.x - pa.x, point.y - pa.y, point.z - pa.z);

		float d = vp.dot(e10);
		float e = vp.dot(e20);
		float x = (d * c) - (e * b);
		float y = (e * a) - (d * b);
		float z = x + y - ac_bb;

		return ((int) (z) & ~((int) (x) | (int) (y)) & 0x80000000) != 0;
	}

	public static RootResult getLowestRoot (float a, float b, float c, float maxR) {
		RootResult res = new RootResult();

		float determinant = b * b - 4.0F * a * c;

		if (determinant < 0.0F) {
			res.exists = false;
			res.root = 0.0F;
			return res;
		}

		float sqrtD = (float) Math.sqrt(determinant);
		float r1 = (-b - sqrtD) / (2 * a);
		float r2 = (-b + sqrtD) / (2 * a);

		if (r1 > r2) {
			float tmp = r2;
			r2 = r1;
			r1 = tmp;
		}

		// Get lowest root
		if(r1 > 0 && r1 < maxR) {
			res.root = r1;
			res.exists = true;
			return res;
		}

		if(r2 > 0 && r2 < maxR) {
			res.root = r2;
			res.exists = true;
			return res;
		}

		return res;
	}

	public static class RootResult {
		public boolean exists = false;
		public float root = 0;
	}

}
