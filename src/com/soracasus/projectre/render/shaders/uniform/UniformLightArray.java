package com.soracasus.projectre.render.shaders.uniform;

import com.soracasus.projectre.render.light.Light;
import org.joml.Vector3f;

public class UniformLightArray extends Uniform {

	private UniformLight[] lights;

	public UniformLightArray (String name, int size) {
		super(name);
		this.lights = new UniformLight[size];
		for (int i = 0; i < size; i++) {
			lights[i] = new UniformLight(name + "[" + i + "]");
		}
	}

	@Override
	public void storeUniformLocation (int programID) {
		for (UniformLight u : lights)
			u.storeUniformLocation(programID);
	}

	public void load (Light[] lightArr) {
		for (int i = 0; i < lights.length; i++) {
			if (i >= lightArr.length) {
				Light light = new Light(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0));
				lights[i].load(light);
			} else {
				lights[i].load(lightArr[i]);
			}
		}
	}
}
