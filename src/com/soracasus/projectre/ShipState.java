package com.soracasus.projectre;

import com.soracasus.projectre.core.*;
import com.soracasus.projectre.render.Camera;
import com.soracasus.projectre.render.entity.Entity;
import com.soracasus.projectre.render.entity.Player;
import com.soracasus.projectre.render.entity.ShipPlayer;
import com.soracasus.projectre.render.entity.terrain.Terrain;
import com.soracasus.projectre.render.entity.terrain.TerrainMaterial;
import com.soracasus.projectre.render.light.Light;
import com.soracasus.projectre.render.model.Material;
import com.soracasus.projectre.render.renderers.DebugRenderer;
import com.soracasus.projectre.render.renderers.EntityRenderer;
import com.soracasus.projectre.render.renderers.TerrainRenderer;
import com.soracasus.projectre.render.texture.Texture;
import com.soracasus.projectre.render.ui.UIManager;
import com.soracasus.projectre.world.WorldGenerator;
import org.joml.Vector3f;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class ShipState implements IGameLogic {

    private NVGColor colour;

    private Light light;

    private Camera camera;

    private ShipPlayer player;
    private EntityRenderer entityRenderer;

    private DebugRenderer debugRenderer;

    private List<Entity> entities;

    private Terrain terrain;
    private TerrainRenderer terrainRenderer;

    private boolean initialized = false;

    @Override
    public void init(Window window) {
        TerrainMaterial terrainMaterial = new TerrainMaterial(null, null, null, null, null, null);

        colour = NVGColor.create();

        terrain = new Terrain(0, 0, terrainMaterial);

        terrainRenderer = new TerrainRenderer(window);

        debugRenderer = new DebugRenderer(window);

        player = new ShipPlayer(new Vector3f(0, 0, 0));
        entityRenderer = new EntityRenderer(window);

        entities = WorldGenerator.generateWorld(new REFile("world/ship.lay"), player);
        entities.add(player);

        camera = new Camera(player, 0.1F);
        camera.setAngleAroundTarget(180);

        light = new Light(new Vector3f(20, 20, 20), new Vector3f(1, 1, 1));

        this.initialized = true;
    }

    @Override
    public void input(Window window, MouseInput mouseInput) {
        player.input(window, mouseInput);
        camera.input(window, mouseInput);
    }

    @Override
    public void render(Window window) {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        terrainRenderer.render(terrain, light, camera);
        entityRenderer.render(entities, light, camera);
       // debugRenderer.render(entities, camera);

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
    public void update(float dt) {
        player.update(dt, entities, terrain);
        camera.update(dt);
    }

    @Override
    public boolean initialized() {
        return initialized;
    }
}
