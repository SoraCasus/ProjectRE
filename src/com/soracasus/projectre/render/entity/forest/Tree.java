package com.soracasus.projectre.render.entity.forest;

import com.soracasus.projectre.core.REFile;
import com.soracasus.projectre.physics.AABB;
import com.soracasus.projectre.render.entity.Entity;
import com.soracasus.projectre.render.model.Model;
import com.soracasus.projectre.render.model.loader.Loader;
import com.soracasus.projectre.render.texture.Texture;
import org.joml.Vector3f;

public class Tree extends Entity {

	private static Model TREE_MODEL;

	static {
		TREE_MODEL = Loader.load(new REFile("models/forest/huge tree/huge_tree.obj"));
		TREE_MODEL.material.diffuse = Texture.newTexture(new REFile("textures/forest/huge_tree.png")).anisotropic().create();
		TREE_MODEL.material.normal = Texture.newTexture(new REFile("textures/forest/huge_tree_normal.png")).anisotropic().create();
		TREE_MODEL.material.reflectivity = 1F;
		TREE_MODEL.material.shineDamper = 1F;
	}

	public Tree (Vector3f position) {
		super(TREE_MODEL, new AABB(new Vector3f(-1, -1, -1), new Vector3f(1, 1, 1)), true);

		super.getTransform().setScale(0.01F);
		super.getTransform().setPosition(position);
	}

	@Override
	public void onCollide (Entity entity) {}

}
