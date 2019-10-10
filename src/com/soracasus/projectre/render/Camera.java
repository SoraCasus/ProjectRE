package com.soracasus.projectre.render;

import com.soracasus.projectre.core.MouseInput;
import com.soracasus.projectre.core.Window;
import com.soracasus.projectre.render.entity.Entity;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {

    private static final float SCROLL_SENSITIVITY = 0.1F;
    private static final float MOUSE_SENSITIVITY = 0.2F;

    private float distanceFromTarget = -1;
    private float angleAroundTarget = 0;
    private float heightOffset = 0;

    private Vector3f position;
    private Vector3f rotation;

    private Entity target;

    private boolean menuCam;

    public Camera(Entity target, float heightOffset) {
        this.position = new Vector3f();
        this.rotation = new Vector3f();
        this.target = target;
        this.heightOffset = heightOffset;
        this.menuCam = false;
    }

    public void input(final Window window, MouseInput mouseInput) {
        calculateZoom(mouseInput);
        calculatePitch(mouseInput);
        calculateAngleAroundTarget(mouseInput);
        float hDist = calculateHDist();
        float vDist = calculateVDist();
        calculateCameraPosition(hDist, vDist);
    }

    public void update(float dt) {
        if (menuCam) {
            angleAroundTarget -= 0.25F * dt;
            angleAroundTarget %= 360;
        }
    }

    private void calculateCameraPosition(float hDist, float vDist) {
        float theta = target.getTransform().getRotation().y + angleAroundTarget;
        this.rotation.y = -theta;
        float dx = (float) (hDist * Math.sin(Math.toRadians(theta)));
        float dz = (float) (hDist * Math.cos(Math.toRadians(theta)));
        position.x = target.getTransform().getPosition().x - dx;
        position.z = target.getTransform().getPosition().z - dz;
        position.y = target.getTransform().getPosition().y + heightOffset + vDist;
    }

    private float calculateHDist() {
        return (float) (distanceFromTarget * Math.cos(Math.toRadians(rotation.x)));
    }

    private float calculateVDist() {
        return (float) (distanceFromTarget * Math.sin(Math.toRadians(rotation.x)));
    }

    private void calculateZoom(MouseInput mouseInput) {
        if (!menuCam) {
            float zoomLevel = mouseInput.getScroll() * SCROLL_SENSITIVITY;
            distanceFromTarget -= zoomLevel;
        }
    }

    private void calculatePitch(MouseInput mouseInput) {
        if (mouseInput.isRightDown() && !menuCam) {
            float pitchChange = mouseInput.getDisplVec().y * MOUSE_SENSITIVITY;
            this.rotation.x -= pitchChange;
        }
    }

    private void calculateAngleAroundTarget(MouseInput mouseInput) {
        if (mouseInput.isRightDown() && !menuCam) {
            float angleChange = mouseInput.getDisplVec().x * MOUSE_SENSITIVITY;
            angleAroundTarget -= angleChange;
        }
    }

    public void move(float offsetX, float offsetY, float offsetZ) {
        position.x += offsetX;
        position.y += offsetY;
        position.z += offsetZ;
    }

    public void rotate(float offsetX, float offsetY, float offsetZ) {
        rotation.x += offsetX;
        rotation.y += offsetY;
        rotation.z += offsetZ;
    }

    public void setMenuCam(boolean menuCam) {
        this.menuCam = menuCam;
    }

    public Matrix4f getViewMat() {
        Matrix4f viewMat = new Matrix4f().identity();


        viewMat.rotate((float) Math.toRadians(-rotation.x), new Vector3f(1, 0, 0));
        viewMat.rotate((float) Math.toRadians(rotation.y), new Vector3f(0, 1, 0));
        viewMat.translate(-position.x, -position.y, -position.z);

        return viewMat;
    }

    public void setAngleAroundTarget(float angleAroundTarget) {
        this.angleAroundTarget = angleAroundTarget;
    }

    public void setDistanceFromTarget(float distanceFromTarget) {
        this.distanceFromTarget = distanceFromTarget;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }
}
