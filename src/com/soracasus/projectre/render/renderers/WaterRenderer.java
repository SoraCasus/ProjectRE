package com.soracasus.projectre.render.renderers;

import com.soracasus.projectre.core.Window;
import com.soracasus.projectre.render.Camera;
import com.soracasus.projectre.render.globjects.Vao;
import com.soracasus.projectre.render.shaders.WaterShader;
import com.soracasus.projectre.render.water.WaterTile;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class WaterRenderer {

	private final Window window;
	private WaterShader shader;
	private Vao waterPlane;

	public WaterRenderer(Window window) {
		this.window = window;
		this.shader = new WaterShader();
		this.waterPlane = generateVao();
		shader.start();
		shader.projMat.load(window.getProjMat());
		shader.stop();
	}

	public void render(List<WaterTile> water, Camera camera) {
		prepareRender(camera);
		for(WaterTile tile : water) {
			Matrix4f tfMat = new Matrix4f().identity();
			tfMat = tfMat.translate(tile.getX(), tile.getHeight(), tile.getZ());
			tfMat = tfMat.scale(WaterTile.TILE_SIZE);
			shader.tfMat.load(tfMat);
			GL11.glDrawElements(GL11.GL_TRIANGLES, waterPlane.getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
		}
		unbind();
	}

	private void unbind() {
		shader.stop();
		waterPlane.unbind(0);
	}

	private void prepareRender(Camera camera) {
		shader.start();
		shader.viewMat.load(camera.getViewMat());
		waterPlane.bind(0);
	}

	private static Vao generateVao() {
		float[] vertices = {
				-1.0F, 0, -1.0F,
				-1.0F, 0,  1.0F,
				 1.0F, 0, -1.0F,
				 1.0F, 0,  1.0F
		};

		int[] indices = {
				0, 1, 2, 2, 1, 3
		};

		Vao vao = new Vao();
		vao.bind(0);
		vao.createIndexBuffer(indices);
		vao.createAttribute(0, 3, vertices);
		vao.unbind(0);
		return vao;
	}

}
