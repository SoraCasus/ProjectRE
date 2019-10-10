package com.soracasus.projectre.render.renderers;

import com.soracasus.projectre.core.Window;
import com.soracasus.projectre.render.Camera;
import com.soracasus.projectre.render.entity.terrain.Terrain;
import com.soracasus.projectre.render.entity.terrain.TerrainMaterial;
import com.soracasus.projectre.render.light.Light;
import com.soracasus.projectre.render.shaders.TerrainShader;
import org.lwjgl.opengl.GL11;

public class TerrainRenderer {

	private final TerrainShader shader;
	private final Window window;

	public TerrainRenderer (final Window window) {
		this.shader = new TerrainShader();
		this.window = window;
	}

	public void render (Terrain terrain, Light light, Camera camera) {
		shader.start();
		TerrainMaterial mat = terrain.getMaterial();
		mat.getTextureMap().bindToUnit(0);
		mat.getrTexture().bindToUnit(1);
		mat.getgTexture().bindToUnit(2);
		mat.getbTexture().bindToUnit(3);
		mat.getBgTexture().bindToUnit(4);

		shader.material.load();
		shader.mats.load(terrain.getTransform(), camera.getViewMat(), window.getProjMat());
		shader.light.load(light);

		terrain.getVao().bind(0, 1, 2);
		GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getVao().getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
		terrain.getVao().unbind(0, 1, 2);

		shader.stop();
	}

}
