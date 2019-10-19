#shader vertex
#version 400 core

layout (location = 0) in vec3 in_position;

out vec2 pass_textureCoords;

uniform mat4 u_projMat;
uniform mat4 u_viewMat;
uniform mat4 u_tfMat;

void main(void) {
	gl_Position = u_projMat * u_viewMat * u_tfMat * vec4(in_position, 1.0);
	pass_textureCoords = vec2(in_position.x / 2.0 + 0.5, in_position.y / 2.0 + 0.5);
}

#shader fragment
#version 400 core

in vec2 pass_textureCoords;

out vec4 out_colour;

void main(void) {
	out_colour = vec4(0.0, 0.0, 1.0, 1.0);
}