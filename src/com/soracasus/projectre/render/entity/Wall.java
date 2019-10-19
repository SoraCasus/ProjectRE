package com.soracasus.projectre.render.entity;

import com.soracasus.projectre.core.REFile;
import com.soracasus.projectre.physics.AABB;
import com.soracasus.projectre.render.model.Model;
import com.soracasus.projectre.render.model.loader.Loader;
import com.soracasus.projectre.render.texture.Texture;
import org.joml.Vector3f;

public class Wall extends Entity {

    private static Model WALL_MODEL;

    static {
        WALL_MODEL = Loader.load(new REFile("models/wall.obj"));
        WALL_MODEL.material.setDiffuse(Texture.newTexture(new REFile("textures/wall.png")).anisotropic().create());
        WALL_MODEL.material.setNormal(Texture.newTexture(new REFile("textures/null_normal.png")).create());
        WALL_MODEL.material.setReflectivity(0.5F);
        WALL_MODEL.material.setShineDamper(32F);
    }

    public Wall(Vector3f position) {
        super(WALL_MODEL, new AABB(new Vector3f(-0.501F, 0F, -0.501F), new Vector3f(0.501F, 1.001F, 0.501F)), true);

        super.getTransform().setScale(0.5F);
        super.getTransform().setPosition(position);
    }

    @Override
    public void onCollide(Entity entity) {

    }
}
