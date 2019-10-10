package com.soracasus.projectre.render.shaders.uniform;

import com.soracasus.projectre.render.model.Material;

public class UniformMaterial extends UniformStruct {

	private UniformBoolean hasDiffuse;
	private UniformSampler diffuse;

	private UniformBoolean hasSpecular;
	private UniformSampler specular;

	private UniformBoolean hasNormal;
	private UniformSampler normal;

	private UniformBoolean hasHeight;
	private UniformSampler height;

	private UniformVec3 colour;

	private UniformFloat shineDamper;
	private UniformFloat reflectivity;

	public UniformMaterial (String name) {
		super(name);
		hasDiffuse = new UniformBoolean(name + ".hasDiffuse");
		diffuse = new UniformSampler(name + ".diffuse");

		hasSpecular = new UniformBoolean(name + ".hasSpecular");
		specular = new UniformSampler(name + ".specular");

		hasNormal = new UniformBoolean(name + ".hasNormal");
		normal = new UniformSampler(name + ".normal");

		hasHeight = new UniformBoolean(name + ".hasHeight");
		height = new UniformSampler(name + ".height");

		colour = new UniformVec3(name + ".colour");

		shineDamper = new UniformFloat(name + ".shineDamper");
		reflectivity = new UniformFloat(name + ".reflectivity");

		setMembers(diffuse, specular, normal, height, colour, hasDiffuse, hasSpecular, hasNormal, hasHeight,
				shineDamper, reflectivity);
	}

	public void load (Material material) {
		hasDiffuse.load(material.diffuse != null);
		diffuse.load(0);

		hasSpecular.load(material.specular != null);
		specular.load(2);

		hasNormal.load(material.normal != null);
		normal.load(1);

		hasHeight.load(material.height != null);
		height.load(3);

		colour.load(material.colour);

		shineDamper.load(material.shineDamper);
		reflectivity.load(material.reflectivity);
	}

}
