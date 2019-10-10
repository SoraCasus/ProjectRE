#shader vertex
#version 400 core

#include Mats.rei
#include Light.rei
#include Material.rei

#define MAX_LIGHTS 10

layout (location = 0) in vec3 in_position;
layout (location = 1) in vec2 in_texCoord;
layout (location = 2) in vec3 in_normal;
layout (location = 3) in vec3 in_tangent;
layout (location = 4) in vec3 in_biTangent;

out vec2 pass_texCoords;
out vec3 pass_surfaceNormal;
out vec3 pass_toLightVector[MAX_LIGHTS];
out vec3 pass_toCameraVector;

uniform Mats u_mats;
uniform Light u_light[MAX_LIGHTS];
uniform Material u_material;

void main(void) {

    vec4 worldPosition = u_mats.tfMat * vec4(in_position, 1.0);
    gl_Position = u_mats.projMat * u_mats.viewMat * worldPosition;
    pass_texCoords = in_texCoord;

    pass_surfaceNormal = (u_mats.tfMat * vec4(in_normal, 0.0)).xyz;

    vec3 norm = normalize(pass_surfaceNormal);
    vec3 tang = normalize((getModelView(u_mats) * vec4(in_tangent, 0.0)).xyz);
    vec3 bitang = normalize(cross(norm, tang));

    mat3 toTangentSpace = mat3(
		tang.x, bitang.x, norm.x,
		tang.y, bitang.y, norm.y,
		tang.z, bitang.z, norm.z
    );

    for(int i = 0; i < MAX_LIGHTS; i++) {
        pass_toLightVector[i] = toTangentSpace * (u_light[i].position - worldPosition.xyz);
    }

    pass_toCameraVector = toTangentSpace * ((inverse(u_mats.viewMat) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - worldPosition.xyz);
}

#shader fragment
#version 400 core

#include Light.rei
#include Material.rei

#define MAX_LIGHTS 10

in vec2 pass_texCoords;
in vec3 pass_surfaceNormal;
in vec3 pass_toLightVector[MAX_LIGHTS];
in vec3 pass_toCameraVector;

out vec4 out_colour;

uniform Light u_light[MAX_LIGHTS];
uniform Material u_material;

void main(void) {

    vec4 normalMapValue = 2.0 * texture(u_material.normal, pass_texCoords) - 1.0;

    vec3 unitNormal = normalize(normalMapValue.rgb);
    vec3 unitVectorToCamera = normalize(pass_toCameraVector);

    vec3 totalDiffuse = vec3(0.0);
    vec3 totalSpecular = vec3(0.0);

    for(int i = 0; i < MAX_LIGHTS; i++) {

        vec3 unitLightVector = normalize(pass_toLightVector[i]);

        float nDotl = dot(unitNormal, unitLightVector);
        float brightness = max(nDotl, 0.0);

        vec3 lightDirection = -unitLightVector;
        vec3 reflLightDir = reflect(lightDirection, unitNormal);

        float specularFactor = dot(reflLightDir, unitVectorToCamera);
        specularFactor = max(specularFactor, 0.0);
        float dampedFactor = pow(specularFactor, u_material.shineDamper);


        totalDiffuse += brightness * u_light[i].colour;
        totalSpecular += dampedFactor * u_material.reflectivity * u_light[i].colour;
    }

    totalDiffuse = max(totalDiffuse, 0.2);

    vec4 textureColour = texture(u_material.diffuse, pass_texCoords);
    if(textureColour.a < 0.5)
        discard;


    out_colour = vec4(totalDiffuse, 1.0) * texture(u_material.diffuse, pass_texCoords) + vec4(totalSpecular, 1.0);

}
