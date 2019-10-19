package com.soracasus.projectre.render.model;

import com.soracasus.projectre.core.REFile;
import com.soracasus.projectre.render.texture.Texture;
import org.joml.Vector3f;

public class Material {

	private static final Texture NULL_NORMAL = Texture.newTexture(new REFile("textures/null_normal.png")).create();

	private Texture diffuse;
	private Texture normal;
	private Texture specular;
	private Texture height;
	private Vector3f colour;
	private float shineDamper;
	private float reflectivity;

	public Material() {
		this.diffuse = null;
		this.normal = null;
		this.specular = null;
		this.height = null;
		this.colour = new Vector3f(1, 1, 1);
		this.shineDamper = 1;
		this.reflectivity = 0;
	}

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

	public Texture getDiffuse () {
		return diffuse;
	}

	public void setDiffuse (Texture diffuse) {
		this.diffuse = diffuse;
	}

	public Texture getNormal () {
		return normal;
	}

	public void setNormal (Texture normal) {
		this.normal = normal;
	}

	public Texture getSpecular () {
		return specular;
	}

	public void setSpecular (Texture specular) {
		this.specular = specular;
	}

	public Texture getHeight () {
		return height;
	}

	public void setHeight (Texture height) {
		this.height = height;
	}

	public Vector3f getColour () {
		return colour;
	}

	public void setColour (Vector3f colour) {
		this.colour = colour;
	}

	public float getShineDamper () {
		return shineDamper;
	}

	public void setShineDamper (float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public float getReflectivity () {
		return reflectivity;
	}

	public void setReflectivity (float reflectivity) {
		this.reflectivity = reflectivity;
	}
}
