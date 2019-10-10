package com.soracasus.projectre.render.entity;

import com.soracasus.projectre.audio.AudioEngine;
import com.soracasus.projectre.audio.SoundBuffer;
import com.soracasus.projectre.audio.SoundSource;
import com.soracasus.projectre.core.MouseInput;
import com.soracasus.projectre.core.REFile;
import com.soracasus.projectre.core.Window;
import com.soracasus.projectre.physics.AABB;
import com.soracasus.projectre.render.model.Model;
import com.soracasus.projectre.render.model.loader.Loader;
import com.soracasus.projectre.render.texture.Texture;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class Player extends Entity {

    private static Model PLAYER_MODEL;

    static {
        PLAYER_MODEL = Loader.load(new REFile("models/fighter01.obj"));
        PLAYER_MODEL.material.diffuse = Texture.newTexture(new REFile("textures/f01_diffuse.png")).anisotropic().create();
        PLAYER_MODEL.material.normal = Texture.newTexture(new REFile("textures/f01_normal.png")).anisotropic().create();
        PLAYER_MODEL.material.reflectivity = 1F;
        PLAYER_MODEL.material.shineDamper = 10F;
    }

    private static final float RUN_SPEED = 0.1F;
    private static final float TURN_SPEED = 2;

    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float liftSpeed = 0;

    public SoundSource soundSource;
    public SoundSource radioSource;
    public SoundSource bgMusic;
    public SoundSource sfxSource;

    public Player(Vector3f position) {
        super(PLAYER_MODEL, new AABB(
                new Vector3f(-0.3F, -0.1F, -0.3F), new Vector3f(0.3F, 0.1F, 0.3F)), true);

        super.getTransform().setPosition(position);
        super.getTransform().setScale(0.1F);

        soundSource = new SoundSource(0, 1, 2);
        soundSource.setBuffer(AudioEngine.shipSound);
        soundSource.setLooping(true);
        soundSource.setVolume(0.75F);
        soundSource.setPosition(super.getTransform().getPosition());

        radioSource = new SoundSource(0, 1, 2);
        sfxSource = new SoundSource(0, 1, 2);

        bgMusic = new SoundSource(0, 1, 2);
        bgMusic.setBuffer(AudioEngine.bgMusic);
        bgMusic.setLooping(true);
        bgMusic.setVolume(1.0F);
        bgMusic.setPosition(super.getTransform().getPosition());
        bgMusic.play();
    }

    @Override
    public void onCollide(Entity e) {
        // Note(Sora): There should only ever be one player instance in the game
        // nothing should happen
    }

    public void playSound(SoundBuffer sound) {
        sfxSource.setBuffer(sound);
        sfxSource.setPosition(super.getTransform().getPosition());
        if (!sfxSource.isPlaying())
            sfxSource.play();
    }

    public void playRadioLog(SoundBuffer log) {
        radioSource.setBuffer(log);
        radioSource.setPosition(super.getTransform().getPosition());
        radioSource.play();
    }

    public void update(float dt, List<Entity> entities) {
        super.getTransform().rotate(new Vector3f(0, currentTurnSpeed * dt, 0));
        float distance = currentSpeed * dt;
        float dx = distance * (float) Math.sin(Math.toRadians(getTransform().getRotation().y));
        float dz = distance * (float) Math.cos(Math.toRadians(getTransform().getRotation().y));
        float dy = liftSpeed * dt;

        Vector3f newPos = new Vector3f(getTransform().getPosition());
        newPos.add(dx, dy, dz);

        boolean xAxis = false;
        boolean yAxis = false;
        boolean zAxis = false;
        for (Entity e : entities) {
            if (e instanceof Player)
                continue;
            AABB.CollideData data = AABB.intersects(getAABB(), newPos, e.getAABB(), e.getTransform().getPosition());

            if (data.collides) {
                if (e.isSolid()) {
                    AABB.CollideData xData = AABB.intersects(getAABB(), new Vector3f(newPos.x, 0, 0),
                            e.getAABB(), e.getTransform().getPosition());
                    xAxis = xData.xAxis;

                    AABB.CollideData yData = AABB.intersects(getAABB(), new Vector3f(0, newPos.y, 0),
                            e.getAABB(), e.getTransform().getPosition());
                    yAxis = yData.yAxis;

                    AABB.CollideData zData = AABB.intersects(getAABB(), new Vector3f(0, 0, newPos.z),
                            e.getAABB(), e.getTransform().getPosition());
                    zAxis = zData.zAxis;
                }
                e.onCollide(this);
            }
        }

        // System.out.println("x: " + xAxis + " Y: " + yAxis + " Z: " + zAxis);

        super.getTransform().move(new Vector3f(xAxis ? 0 : dx, yAxis ? 0 : dy, zAxis ? 0 : dz));

        if (getTransform().getPosition().y < 0) {
            float x = getTransform().getPosition().x;
            float z = getTransform().getPosition().z;
            super.getTransform().setPosition(new Vector3f(x, 0.01F, z));
        } else if (getTransform().getPosition().y > 10) {
            float x = getTransform().getPosition().x;
            float z = getTransform().getPosition().z;
            super.getTransform().setPosition(new Vector3f(x, 9.99F, z));
        }

        AudioEngine.setListenerData(getTransform().getPosition());

        bgMusic.setPosition(super.getTransform().getPosition());
        soundSource.setPosition(super.getTransform().getPosition());
        radioSource.setPosition(super.getTransform().getPosition());
    }

    public void input(final Window window, MouseInput mouseInput) {
        if (window.isKeyDown(GLFW.GLFW_KEY_W)) {
            this.currentSpeed = -RUN_SPEED;
        } else if (window.isKeyDown(GLFW.GLFW_KEY_S)) {
            this.currentSpeed = RUN_SPEED;
        } else {
            this.currentSpeed = 0;
        }

        if (window.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
            this.liftSpeed = RUN_SPEED;
        } else if (window.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) {
            this.liftSpeed = -RUN_SPEED;
        } else {
            this.liftSpeed = 0;
        }

        if (this.currentSpeed != 0 || liftSpeed != 0) {
            if (!soundSource.isPlaying())
                soundSource.play();
        } else {
            soundSource.pause();
        }

        if (window.isKeyDown(GLFW.GLFW_KEY_D)) {
            this.currentTurnSpeed = -TURN_SPEED;
        } else if (window.isKeyDown(GLFW.GLFW_KEY_A)) {
            this.currentTurnSpeed = TURN_SPEED;
        } else {
            this.currentTurnSpeed = 0;
        }
    }

}
