package com.soracasus.projectre.render.renderers;

import com.soracasus.projectre.core.Window;
import com.soracasus.projectre.physics.AABB;
import com.soracasus.projectre.render.Camera;
import com.soracasus.projectre.render.entity.Entity;
import com.soracasus.projectre.render.globjects.Vao;
import com.soracasus.projectre.render.shaders.DebugShader;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class DebugRenderer {

	private final Window window;
	private final DebugShader shader;

	public DebugRenderer(Window window) {
		this.window = window;
		this.shader = new DebugShader();
	}

	public void render(List<Entity> entities, Camera camera) {

		shader.start();

		GL11.glDisable(GL11.GL_CULL_FACE);

		for(Entity e : entities) {
			// if( e instanceof Player)
			// continue;
			AABB aabb = e.getAABB();
			shader.mats.load(e.getTransform().getTfMat(), camera.getViewMat(), window.getProjMat());
			shader.translation.load(e.getTransform().getPosition());
			shader.colliding.load(aabb.getColliding());
			Vao vao = aabb.getDebugBox();
			vao.bind(0);
			GL11.glDrawElements(GL11.GL_TRIANGLES, vao.getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
			vao.unbind(0);
		}

		shader.stop();

		window.restoreState();

	}

}
