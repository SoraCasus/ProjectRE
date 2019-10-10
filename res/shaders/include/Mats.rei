struct Mats {
    mat4 tfMat;
    mat4 viewMat;
    mat4 projMat;
};

mat4 getModelView(Mats mat) {
    return mat.viewMat * mat.tfMat;
}

mat4 getMVP(Mats mat) {
    return mat.projMat * mat.viewMat * mat.tfMat;
}
