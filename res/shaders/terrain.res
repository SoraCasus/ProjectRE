#shader vertex
#version 400 core

#include Mats.rei
#include Light.rei

layout (location = 0) in vec3 in_position;
layout (location = 1) in vec2 in_textures;
layout (location = 2) in vec3 in_normal;

out vec2 pass_texCoords;
out vec3 pass_surfaceNormal;
out vec3 pass_toLightVector;
out vec3 pass_toCameraVector;

uniform Mats u_mats;
uniform Light u_light;

void main(void) {

    vec4 worldPosition = u_mats.tfMat * vec4(in_position, 1.0);
    gl_Position = u_mats.projMat * u_mats.viewMat * worldPosition;
    pass_texCoords = in_textures;    // Change this to increase LOD, maybe make it a uniform

    pass_surfaceNormal = (u_mats.tfMat * vec4(in_normal, 0.0)).xyz;
    pass_toLightVector = u_light.position - worldPosition.xyz;
    pass_toCameraVector = (inverse(u_mats.viewMat) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - worldPosition.xyz;

}

#shader fragment
#version 400 core

#include Light.rei
#include TerrainMaterial.rei

in vec2 pass_texCoords;
in vec3 pass_surfaceNormal;
in vec3 pass_toLightVector;
in vec3 pass_toCameraVector;

out vec4 out_colour;

uniform TerrainMaterial u_material;
uniform Light u_light;

const float reflectivity = 0.0;
const float shineDamper = 1.0;

void main(void) {

    vec4 blendMapColour = texture(u_material.blendMap, pass_texCoords);

    float backTextureAmount = 1 - (blendMapColour.r + blendMapColour.g + blendMapColour.b);
    // Adjust the constant to increase/decrease resolution on the terrain
    vec2 tiledCoords = pass_texCoords * 1280.0;
    vec4 bgTextureColour = texture(u_material.bgTexture, tiledCoords) * backTextureAmount;
    vec4 rTextureColour = texture(u_material.rTexture, tiledCoords) * blendMapColour.r;
    vec4 gTextureColour = texture(u_material.gTexture, tiledCoords) * blendMapColour.g;
    vec4 bTextureColour = texture(u_material.bTexture, tiledCoords) * blendMapColour.b;

    vec4 totalColour = bgTextureColour + rTextureColour + gTextureColour + bTextureColour;

    vec3 unitNormal = normalize(pass_surfaceNormal);
    vec3 unitLightVector = normalize(pass_toLightVector);

    float nDotl = dot(unitNormal, unitLightVector);
    float brightness = max(nDotl, 0.2);
    vec3 diffuse = brightness * u_light.colour;

    vec3 unitVectorToCamera = normalize(pass_toCameraVector);
    vec3 lightDirection = -unitLightVector;
    vec3 reflLightDir = reflect(lightDirection, unitNormal);

    float specularFactor = dot(reflLightDir, unitVectorToCamera);
    specularFactor = max(specularFactor, 0.0);
    float dampedFactor = pow(specularFactor, shineDamper);
    vec3 finalSpecular = dampedFactor * reflectivity * u_light.colour;

    out_colour = vec4(diffuse, 1.0) * totalColour + vec4(finalSpecular, 1.0);
}

















