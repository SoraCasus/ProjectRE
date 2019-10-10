#shader vertex
#version 400 core

layout (location = 0) in vec3 in_position;

out vec3 pass_textureCoords;

uniform mat4 u_projMat;
uniform mat4 u_viewMat;

void main(void) {
    gl_Position = u_projMat * u_viewMat * vec4(in_position, 1.0);
    pass_textureCoords = in_position;
}

#shader fragment
#version 400 core

in vec3 pass_textureCoords;

out vec4 out_colour;

uniform samplerCube cubeMap;

void main(void) {
    out_colour = texture(cubeMap, pass_textureCoords);
}








