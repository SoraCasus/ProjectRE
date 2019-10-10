package com.soracasus.projectre;

import com.soracasus.projectre.core.*;
import com.soracasus.projectre.render.Camera;
import com.soracasus.projectre.render.entity.Entity;
import com.soracasus.projectre.render.entity.Ship;
import com.soracasus.projectre.render.light.Light;
import com.soracasus.projectre.render.renderers.EntityRenderer;
import com.soracasus.projectre.render.renderers.SkyboxRenderer;
import com.soracasus.projectre.render.skybox.Skybox;
import com.soracasus.projectre.render.texture.Texture;
import com.soracasus.projectre.render.ui.Colour;
import com.soracasus.projectre.render.ui.IUIObject;
import com.soracasus.projectre.render.ui.UIBox;
import com.soracasus.projectre.render.ui.UIButton;
import com.soracasus.projectre.render.ui.UIManager;
import com.soracasus.projectre.world.WorldGenerator;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;
import java.util.List;

public class MenuState implements IGameLogic {

	public boolean initialzied = false;

	private EntityRenderer entityRenderer;
	private SkyboxRenderer skyboxRenderer;
	private Camera camera;
	private Light light;
	private Skybox skybox;

	private List<Entity> entities;

	private List<IUIObject> uiElements;

	private NVGColor colour;

	@Override
	public void init (Window window) {
		entityRenderer = new EntityRenderer(window);
		skyboxRenderer = new SkyboxRenderer(window);

		UIManager.init(window);

		colour = NVGColor.create();

		uiElements = new ArrayList<>();
		entities = new ArrayList<>();

		entities = WorldGenerator.generateEntities(-100, 100, -10, 10, -100, 100, 5);

		Ship ship = null;
		for (Entity e : entities) {
			if (e instanceof Ship)
				ship = (Ship) e;
		}

		assert ship != null;

		// ship.getTransform().setScale(0.02F);

		camera = new Camera(ship, 0);
		camera.setMenuCam(true);
		camera.rotate(-20, 0, 0);
		camera.setDistanceFromTarget(-50F);

		light = new Light(new Vector3f(200, 200, 200), new Vector3f(1F, 1F, 1F));

		REFile[] skyTextures = {
				new REFile("skybox/red/bkg2_right1.png"),
				new REFile("skybox/red/bkg2_left2.png"),
				new REFile("skybox/red/bkg2_top3.png"),
				new REFile("skybox/red/bkg2_bottom4.png"),
				new REFile("skybox/red/bkg2_front5.png"),
				new REFile("skybox/red/bkg2_back6.png"),
		};

		Texture skyTexture = Texture.newCubeMap(skyTextures);

		skybox = new Skybox(skyTexture, 500);

		UIButton playButton = new UIButton(new UIBox(new Vector2f(-0.75F, 0), new Vector2f(100, 50),
				new Colour(0xC7 / 255.0F, 0x00 / 255.0F, 0x39 / 255.0F)), "Play", new Colour(0, 0, 0),
				() -> StateManager.setState(StateManager.mainGameState));

		UIButton recoveredDocsButton = new UIButton(new UIBox(new Vector2f(-0.75F, 0.25F), new Vector2f(200, 50),
				new Colour(0xC7 / 255.0F, 0x00 / 255.0F, 0x39 / 255.0F)), "Rutikal Codex", new Colour(0, 0, 0),
				() -> StateManager.setState(StateManager.documentState));

		UIButton quitButton = new UIButton(new UIBox(new Vector2f(-0.75F, 0.5F), new Vector2f(100, 50),
				new Colour(0xC7 / 255.0F, 0x00 / 255.0F, 0x39 / 255.0F)), "Quit", new Colour(0, 0, 0),
				() -> GLFW.glfwSetWindowShouldClose(window.getWindowID(), true));

		uiElements.add(playButton);
		uiElements.add(recoveredDocsButton);
		uiElements.add(quitButton);

		initialzied = true;
	}

	@Override
	public void input (Window window, MouseInput mouseInput) {
		camera.input(window, mouseInput);

		for(IUIObject o : uiElements)
			o.input(window, mouseInput);
	}

	@Override
	public void render (Window window) {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);

		entityRenderer.render(entities, light, camera);
		skyboxRenderer.render(skybox, camera);

		UIManager.render(window, uiElements);

		long vg = UIManager.vg;

		NanoVG.nvgBeginFrame(vg, window.getOpts().width, window.getOpts().height, 1);


		// Lower Ribbon
		NanoVG.nvgBeginPath(vg);
		NanoVG.nvgRect(vg, 0, window.getOpts().height - 20, window.getOpts().width, 50);
		colour.r(0xc1 / 255.0F);
		colour.g(0xe3 / 255.0F);
		colour.b(0xf9 / 255.0F);
		colour.a(1.0F);
		NanoVG.nvgFillColor(vg, colour);
		NanoVG.nvgFill(vg);

		NanoVG.nvgFontSize(vg, 20F);
		NanoVG.nvgFontFace(vg, "font");
		NanoVG.nvgTextAlign(vg, NanoVG.NVG_ALIGN_CENTER);
		colour.r(0).g(0).b(0).a(1);
		NanoVG.nvgFillColor(vg, colour);
		NanoVG.nvgText(vg, window.getOpts().width - 150, window.getOpts().height - 4, "Rutikal Engine " + RutikalEngine.VERSION);
		NanoVG.nvgText(vg, 50, window.getOpts().height - 4, "Project RE");

		NanoVG.nvgEndFrame(vg);

		window.restoreState();
	}

	@Override
	public void update (float dt) {
		camera.update(dt);
	}

	@Override
	public boolean initialized () {
		return initialzied;
	}
}
