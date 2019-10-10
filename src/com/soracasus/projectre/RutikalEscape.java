package com.soracasus.projectre;

import com.soracasus.projectre.core.IGameLogic;
import com.soracasus.projectre.core.MouseInput;
import com.soracasus.projectre.core.REFile;
import com.soracasus.projectre.core.RutikalEngine;
import com.soracasus.projectre.core.StateManager;
import com.soracasus.projectre.core.Window;
import com.soracasus.projectre.render.Camera;
import com.soracasus.projectre.render.entity.Entity;
import com.soracasus.projectre.render.entity.Keycard;
import com.soracasus.projectre.render.entity.Player;
import com.soracasus.projectre.render.entity.Radio;
import com.soracasus.projectre.render.light.Light;
import com.soracasus.projectre.render.renderers.DebugRenderer;
import com.soracasus.projectre.render.renderers.EntityRenderer;
import com.soracasus.projectre.render.renderers.SkyboxRenderer;
import com.soracasus.projectre.render.skybox.Skybox;
import com.soracasus.projectre.render.texture.Texture;
import com.soracasus.projectre.render.ui.Colour;
import com.soracasus.projectre.render.ui.UIButton;
import com.soracasus.projectre.util.Utils;
import com.soracasus.projectre.world.WorldGenerator;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class RutikalEscape implements IGameLogic {

	private static final int KEYS_NEEDED = 5;

	private boolean initialized = false;

	private List<Entity> entities;
	private EntityRenderer entityRenderer;
	private SkyboxRenderer skyRenderer;
	private DebugRenderer debugRenderer;
	private Player player;
	private Camera camera;
	private Light light;
	private Skybox sky;

	private long vg;
	private NVGColor colour;
	@SuppressWarnings("FieldCanBeLocal") // Font buffer can't be local because GC is an asshole
	private ByteBuffer fontBuffer;

	@Override
	public void init (final Window window) {
		entityRenderer = new EntityRenderer(window);
		skyRenderer = new SkyboxRenderer(window);
		debugRenderer = new DebugRenderer(window);


		entities = new ArrayList<>();

		player = new Player(new Vector3f(-40, 0, 0));

		entities = WorldGenerator.generateEntities(-100, 100, -100, 100, -100, 100, 5);

		// Keycard keycard = new Keycard(new Vector3f(0, 0, -10));
		// entities.add(keycard);

		entities.add(player);

		camera = new Camera(this.player, 0F);

		light = new Light(new Vector3f(20, -20, 20), new Vector3f(1.0F, 1.0F, 1.0F));


		REFile[] skyTextures = {
				new REFile("skybox/red/bkg2_right1.png"),
				new REFile("skybox/red/bkg2_left2.png"),
				new REFile("skybox/red/bkg2_top3.png"),
				new REFile("skybox/red/bkg2_bottom4.png"),
				new REFile("skybox/red/bkg2_front5.png"),
				new REFile("skybox/red/bkg2_back6.png"),
		};
		Texture skyTexture = Texture.newCubeMap(skyTextures);
		sky = new Skybox(skyTexture, 100);

		vg = NanoVGGL3.nvgCreate(NanoVGGL3.NVG_ANTIALIAS | NanoVGGL3.NVG_STENCIL_STROKES);
		if (vg == MemoryUtil.NULL)
			System.err.println("  Failed to initialize NanoVG!");
		REFile fontFile = new REFile("font/data.ttf");
		fontBuffer = Utils.loadToByteBuffer(fontFile, 250 * 1024);
		int font = NanoVG.nvgCreateFontMem(vg, "font", fontBuffer, 0);
		if (font == -1)
			System.err.println(" Failed to load Font: " + fontFile);

		colour = NVGColor.create();

		GL11.glClearColor(0, 0, 0.1F, 1);

		initialized = true;
	}

	@Override
	public void input (final Window window, final MouseInput mouseInput) {
		if (window.isKeyDown(GLFW.GLFW_KEY_ESCAPE))
			StateManager.setState(StateManager.menuState);
		player.input(window, mouseInput);
		camera.input(window, mouseInput);
	}

	@Override
	public void render (final Window window) {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);

		entityRenderer.render(entities, light, camera);

		skyRenderer.render(sky, camera);

		//     debugRenderer.render(entities, camera);

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
		player.update(dt, entities);

		entities.removeIf(entity -> entity.remove);

		if(Keycard.keyCount >= KEYS_NEEDED) {
			// Todo(Sora): Move to internal state
		}

		camera.update(dt);
	}

	@Override
	public boolean initialized () {
		return initialized;
	}


}
