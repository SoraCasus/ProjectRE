package com.soracasus.projectre.render.renderers;

import com.soracasus.projectre.core.Window;
import com.soracasus.projectre.render.Camera;
import com.soracasus.projectre.render.entity.Entity;
import com.soracasus.projectre.render.light.Light;
import com.soracasus.projectre.render.model.Model;
import com.soracasus.projectre.render.shaders.BasicShader;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class EntityRenderer {

	private final BasicShader shader;
	private final Window window;

	public EntityRenderer(final Window window) {
		this.shader = new BasicShader();
		this.window = window;
	}

	public void render(List<Entity> entities, Light light, Camera camera) {
		shader.start();
		for(Entity e : entities) {
			Model model = e.getModel();

			model.material.bind();
			shader.material.load(model.material);
			shader.mats.load(e.getTransform().getTfMat(), camera.getViewMat(), window.getProjMat());
			shader.viewPos.load(camera.getPosition());
			shader.light.load(light);

			model.getVao().bind(0, 1, 2, 3, 4);
			GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVao().getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
			model.getVao().unbind(0, 1, 2, 3, 4);
		}
		shader.stop();
	}



}
