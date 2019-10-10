struct Material {
    float hasDiffuse;
    sampler2D diffuse;

    float hasSpecular;
    sampler2D specular;

    float hasNormal;
    sampler2D normal;

    float hasHeight;
    sampler2D height;

    vec3 colour;

    float shineDamper;
    float reflectivity;
};