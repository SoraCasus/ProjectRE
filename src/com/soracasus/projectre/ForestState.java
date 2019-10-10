package com.soracasus.projectre;

import com.soracasus.projectre.core.IGameLogic;
import com.soracasus.projectre.core.MouseInput;
import com.soracasus.projectre.core.REFile;
import com.soracasus.projectre.core.Window;
import com.soracasus.projectre.render.Camera;
import com.soracasus.projectre.render.entity.Entity;
import com.soracasus.projectre.render.entity.ShipPlayer;
import com.soracasus.projectre.render.entity.forest.Tree;
import com.soracasus.projectre.render.entity.terrain.Terrain;
import com.soracasus.projectre.render.entity.terrain.TerrainMaterial;
import com.soracasus.projectre.render.light.Light;
import com.soracasus.projectre.render.renderers.EntityRenderer;
import com.soracasus.projectre.render.renderers.SkyboxRenderer;
import com.soracasus.projectre.render.renderers.TerrainRenderer;
import com.soracasus.projectre.render.skybox.Skybox;
import com.soracasus.projectre.render.texture.Texture;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ForestState implements IGameLogic {

	private Light light;
	private Camera camera;
	// Todo(Sora): Rebuild Player
	private ShipPlayer player;

	private Tree tree;

	private EntityRenderer entityRenderer;
	private List<Entity> entities;
	private Terrain terrain;
	private TerrainRenderer terrainRenderer;
	private Skybox skybox;
	private SkyboxRenderer skyboxRenderer;

	private boolean initialized = false;

	@Override
	public void init (Window window) {
		TerrainMaterial terrainMaterial = new TerrainMaterial(
				new REFile("textures/terrain/forest/heightMap.png"),    // Height map file
				new REFile("textures/terrain/forest/blendMap.png"),     // Texture map file
				new REFile("textures/terrain/forest/red.png"),          // Red texture file
				new REFile("textures/terrain/forest/green.png"),        // Green texture file
				new REFile("textures/terrain/forest/blue.png"),         // Blue texture file
				new REFile("textures/terrain/forest/black.png")         // Background texture file
		);


		terrain = new Terrain(0, 0, terrainMaterial);

		player = new ShipPlayer(new Vector3f(10, 0, 10));
		entities = new ArrayList<>();
		entities.add(player);
		Random rand = new Random(System.nanoTime());

		for (int z = 0; z < 800; z++) {
			for(int x = 0; x < 800; x++) {
				double r = rand.nextDouble();
				float height = terrain.getHeightAtPoint(x, z);
				if(r < 0.005 && height > 0) {
					entities.add(new Tree(new Vector3f(x, height, z)));
				}
			}
		}
//
//		tree = new Tree(new Vector3f(0, 0, 0));
//		entities.add(tree);

		camera = new Camera(player, 0);

		light = new Light(new Vector3f(20, 20, 20), new Vector3f(1, 1, 1));

		terrainRenderer = new TerrainRenderer(window);
		entityRenderer = new EntityRenderer(window);

		REFile[] skyTextures = {
				new REFile("skybox/red/bkg2_right1.png"),
				new REFile("skybox/red/bkg2_left2.png"),
				new REFile("skybox/red/bkg2_top3.png"),
				new REFile("skybox/red/bkg2_bottom4.png"),
				new REFile("skybox/red/bkg2_front5.png"),
				new REFile("skybox/red/bkg2_back6.png"),
		};
		Texture skyTexture = Texture.newCubeMap(skyTextures);
		skybox = new Skybox(skyTexture, 100);

		skyboxRenderer = new SkyboxRenderer(window);

		initialized = true;
	}

	@Override
	public void input (Window window, MouseInput mouseInput) {
		player.input(window, mouseInput);
		camera.input(window, mouseInput);
	}

	@Override
	public void render (Window window) {
		// Todo(Sora): This should be taken care of by the renderer
		GL11.glClearColor(1, 1, 1, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);

		skyboxRenderer.render(skybox, camera);
		terrainRenderer.render(terrain, light, camera);
		entityRenderer.render(entities, light, camera);

		window.restoreState();
	}

	@Override
	public void update (float dt) {
		player.update(dt, entities, terrain);
		camera.update(dt);
	}

	@Override
	public boolean initialized () {
		return false;
	}

}
