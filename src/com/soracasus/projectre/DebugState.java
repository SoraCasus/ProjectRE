package com.soracasus.projectre;

import com.soracasus.projectre.core.IGameLogic;
import com.soracasus.projectre.core.MouseInput;
import com.soracasus.projectre.core.REFile;
import com.soracasus.projectre.core.Window;
import com.soracasus.projectre.render.Camera;
import com.soracasus.projectre.render.entity.Entity;
import com.soracasus.projectre.render.entity.Player;
import com.soracasus.projectre.render.entity.ShipPlayer;
import com.soracasus.projectre.render.entity.terrain.Terrain;
import com.soracasus.projectre.render.entity.terrain.TerrainMaterial;
import com.soracasus.projectre.render.light.Light;
import com.soracasus.projectre.render.renderers.EntityRenderer;
import com.soracasus.projectre.render.renderers.WaterRenderer;
import com.soracasus.projectre.render.water.WaterTile;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class DebugState implements IGameLogic {

	private Light light;
	private Camera camera;
	private ShipPlayer player;
	private EntityRenderer renderer;

	private WaterRenderer waterRenderer;
	private WaterTile tile;

	private List<Entity> entities;
	private List<WaterTile> waters;

	private Terrain terrain;

	private boolean initialized = false;

	@Override
	public void init (Window window) {

		player = new ShipPlayer(new Vector3f(0, 0, 0));
		entities = new ArrayList<>();
		entities.add(player);

		TerrainMaterial terrainMaterial = new TerrainMaterial(
				new REFile("textures/terrain/forest/heightMap.png"),    // Height map file
				new REFile("textures/terrain/forest/blendMap.png"),     // Texture map file
				new REFile("textures/terrain/forest/red.png"),          // Red texture file
				new REFile("textures/terrain/forest/green.png"),        // Green texture file
				new REFile("textures/terrain/forest/blue.png"),         // Blue texture file
				new REFile("textures/terrain/forest/black.png")         // Background texture file
		);
		terrain = new Terrain(0, 0, terrainMaterial);

		camera = new Camera(player, 0);
		light = new Light(new Vector3f(10, 10, 10), new Vector3f(1, 1, 1));

		renderer = new EntityRenderer(window);
		waterRenderer = new WaterRenderer(window);

		tile = new WaterTile(0, 0, 0);
		waters = new ArrayList<>();
		waters.add(tile);
		initialized = true;
	}

	@Override
	public void input (Window window, MouseInput mouseInput) {
		player.input(window, mouseInput);
		camera.input(window, mouseInput);
	}

	@Override
	public void render (Window window) {
		GL11.glClearColor(1, 1, 0, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);

		waterRenderer.render(waters, camera);
		renderer.render(entities, light, camera);

		window.restoreState();
	}

	@Override
	public void update (float dt) {
		player.update(dt, entities, terrain);
	}

	@Override
	public boolean initialized () {
		return initialized;
	}
}
