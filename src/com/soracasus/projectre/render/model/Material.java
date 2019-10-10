package com.soracasus.projectre.render.model;

import com.soracasus.projectre.core.REFile;
import com.soracasus.projectre.render.texture.Texture;
import org.joml.Vector3f;

public class Material {

	private static final Texture NULL_NORMAL = Texture.newTexture(new REFile("textures/null_normal.png")).create();

	public Texture diffuse = null;
	public Texture normal = null;
	public Texture specular = null;
	public Texture height = null;
	public Vector3f colour = new Vector3f(1, 1, 1);
	public float shineDamper = 1;
	public float reflectivity = 0;

	public void bind() {
		if(normal == null)
			normal = NULL_NORMAL;
		if(diffuse != null)
			diffuse.bindToUnit(0);
		if(normal != null)
			normal.bindToUnit(1);
		if (specular != null)
			specular.bindToUnit(2);
		if(height != null)
			height.bindToUnit(3);
	}

}
