package com.soracasus.projectre.render.light;

import org.joml.Vector3f;

public class Light {

    private Vector3f position;
    private Vector3f colour;


    public Light(Vector3f direction, Vector3f colour) {
        this.position = direction;
        this.colour = colour;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getColour() {
        return colour;
    }
}
