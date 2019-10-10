package com.soracasus.projectre.render;

import com.soracasus.projectre.render.entity.Entity;
import org.joml.Vector3f;

public class ShipCamera {

	private float distanceFromTarget = -1;
	private float angleAroundTarget = 0;
	private float heightOffset = 0;

	private Vector3f position;
	private Vector3f rotation;

	private Entity target;

	public ShipCamera(Entity target, float heightOffset) {
		this.position = new Vector3f();
		this.rotation = new Vector3f();
		this.target = target;
		this.heightOffset = heightOffset;
	}

	// Todo (Sora): Finish later



}
