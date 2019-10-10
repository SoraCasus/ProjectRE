package com.soracasus.projectre.render.renderers;

import com.soracasus.projectre.core.Window;
import com.soracasus.projectre.render.Camera;
import com.soracasus.projectre.render.globjects.Vao;
import com.soracasus.projectre.render.shaders.SkyboxShader;
import com.soracasus.projectre.render.skybox.Skybox;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

public class SkyboxRenderer {

	private final SkyboxShader shader;
	private final Window window;

	public SkyboxRenderer(final Window window) {
		this.shader = new SkyboxShader();
		this.window = window;
	}

	public void render(Skybox skybox, Camera camera) {
		shader.start();

		shader.projMat.load(window.getProjMat());
		shader.viewMat.load(prepViewMat(camera));

		skybox.getTexture().bindToUnit(0);

		Vao vao = skybox.getCube();
		vao.bind(0);
		GL11.glDrawElements(GL11.GL_TRIANGLES, vao.getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
		vao.unbind(0);

		shader.stop();
	}

	private Matrix4f prepViewMat(Camera camera) {
		Matrix4f viewMat = camera.getViewMat();
		viewMat.m30(0);
		viewMat.m31(0);
		viewMat.m32(0);

		return viewMat;
	}



}
