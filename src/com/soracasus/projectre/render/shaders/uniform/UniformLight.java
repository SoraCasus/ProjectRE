package com.soracasus.projectre.render.shaders.uniform;

import com.soracasus.projectre.render.light.Light;

public class UniformLight extends UniformStruct {

    private UniformVec3 position;
    private UniformVec3 colour;
    private UniformVec3 attenuation;

    public UniformLight(String name) {
        super(name);
        position = new UniformVec3(name + ".position");
        colour = new UniformVec3(name + ".colour");
        attenuation = new UniformVec3(name + ".attenuation");

        setMembers(position, colour);
    }

    public void load(Light light) {
        position.load(light.getPosition());
        colour.load(light.getColour());
    }
}
