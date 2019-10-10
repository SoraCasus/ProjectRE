package com.soracasus.projectre.core;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

public class Window implements IDisposable {

    private static final float Z_NEAR = 0.01F;
    private static final float Z_FAR = 1000.0F;
    private static final float FOV = 60.0F;

    private WindowOpts opts;
    private long windowID;
    private boolean resized;
    private Matrix4f projMat;

    public Window(WindowOpts opts) {
        this.opts = opts;
        this.resized = true;
        this.projMat = new Matrix4f().identity();
        RutikalEngine.addDisposable(this);
    }

    public void createWindow() {

        GLFWErrorCallback.createPrint(System.err).set();

        if (!GLFW.glfwInit())
            throw new IllegalStateException("Failed to initialize GLFW");

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 4);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, 16);

        this.windowID = GLFW.glfwCreateWindow(opts.width, opts.height, opts.title, MemoryUtil.NULL, MemoryUtil.NULL);
        if (windowID == MemoryUtil.NULL)
            throw new IllegalStateException("Failed to create GLFW Window");

        GLFW.glfwSetWindowSizeCallback(windowID, (window, width, height) -> {
            opts.width = width;
            opts.height = height;
            resized = true;
            GL11.glViewport(0, 0, width, height);
        });

        resized = true;
        updateProjMat();

        GLFW.glfwMakeContextCurrent(windowID);

        GL.createCapabilities();

        GL11.glViewport(0, 0, opts.width, opts.height);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_STENCIL_TEST);

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        GLFW.glfwShowWindow(windowID);
    }

    public void close() {
        GLFW.glfwSetWindowShouldClose(windowID, true);
    }

    public boolean isKeyDown(int key) {
        return GLFW.glfwGetKey(windowID, key) == GLFW.GLFW_PRESS;
    }

    public Matrix4f getProjMat() {
        return projMat;
    }

    public void update() {
        updateProjMat();
        GLFW.glfwSwapBuffers(windowID);
        GLFW.glfwPollEvents();
    }

    private void updateProjMat() {
        if (resized) {
            projMat.identity();
            projMat.perspective((float) Math.toRadians(FOV), ((float) opts.width / (float) opts.height), Z_NEAR, Z_FAR);
            resized = false;
        }
    }

    public boolean isOpen() {
        return !GLFW.glfwWindowShouldClose(windowID);
    }

    @Override
    public void delete() {
        GLFW.glfwDestroyWindow(windowID);
        GLFW.glfwTerminate();
    }

    public long getWindowID() {
        return windowID;
    }

    public WindowOpts getOpts() {
        return this.opts;
    }

    public void restoreState() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_STENCIL_TEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }

    public static class WindowOpts {
        public String title;
        public int width;
        public int height;
    }

}
