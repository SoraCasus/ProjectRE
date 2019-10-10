package com.soracasus.projectre.render.entity;

import com.soracasus.projectre.physics.AABB;
import com.soracasus.projectre.render.model.Model;
import org.joml.Vector3f;

import java.util.Random;

public abstract class Entity {

	private Model model;
	public boolean remove;
	private boolean solid;
	private Transform transform;
	private AABB aabb;
	private EnumVariance variance;


	public Entity (Model model, AABB aabb, boolean solid) {
		this.model = model;
		this.remove = false;
		this.solid = solid;
		this.aabb = aabb;
		this.transform = new Transform();
		this.variance = EnumVariance.NONE;
	}

	public void generateVariance () {
		Random random = new Random();
		int var = random.nextInt(4) + 1;

		switch (var) {
			case 1: {
				variance = EnumVariance.NONE;
			}
			break;
			case 2: {
				variance = EnumVariance.X_AXIS;
				getAABB().swapY();
				getAABB().swapZ();
				getTransform().rotate(new Vector3f(180, 0, 0));
			}
			break;

			case 3: {
				variance = EnumVariance.Y_AXIS;
				getAABB().swapX();
				getAABB().swapZ();
				getTransform().rotate(new Vector3f(0, 180, 0));
			}
			break;

			case 4: {
				variance = EnumVariance.Z_AXIS;
				getAABB().swapX();
				getAABB().swapY();
				getTransform().rotate(new Vector3f(0, 0, 180));
			}
			break;
		}
	}

	/**
	 * Will be used to execute an event when the player collides with given entity
	 *
	 * @param entity - The entity which collided with this entity
	 */
	@Deprecated
	public abstract void onCollide (Entity entity);

	@Override
	public String toString () {
		return "Entity{" +
				"transform=" + transform +
				", variance=" + variance +
				'}';
	}

	public Model getModel () {
		return model;
	}

	public void setModel (Model model) {
		this.model = model;
	}

	public boolean isSolid () {
		return solid;
	}

	public Transform getTransform () {
		return transform;
	}

	public AABB getAABB () {
		return aabb;
	}

	private enum EnumVariance {
		X_AXIS,
		Y_AXIS,
		Z_AXIS,
		NONE
	}
}
