#shader vertex
#version 400 core

#include Mats.rei

layout (location  = 0) in vec3 in_position;

uniform Mats u_mats;
uniform vec3 u_translation;

void main(void) {

    gl_Position = u_mats.projMat * u_mats.viewMat * vec4(in_position + u_translation, 1.0);

}

#shader fragment
#version 400 core

out vec4 out_colour;

uniform float u_colliding;

void main(void) {

    if(u_colliding > 0.5)
        out_colour = vec4(1.0, 0.0, 0.0, 1.0);
    else
        out_colour = vec4(1.0, 1.0, 1.0, 1.0);
}