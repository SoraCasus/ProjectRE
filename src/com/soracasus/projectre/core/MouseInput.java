package com.soracasus.projectre.core;

import org.joml.Vector2d;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

public class MouseInput {

	private final Vector2d previousPos;
	private final Vector2d currentPos;
	private final Vector2f displVec;

	private boolean inWindow = false;
	private boolean leftDown = false;
	private boolean rightDown = false;

	private float scroll;

	public MouseInput () {
		this.previousPos = new Vector2d(-1, -1);
		this.currentPos = new Vector2d(0, 0);
		this.displVec = new Vector2f();
		this.scroll = 0;
	}

	public void init (Window window) {
		GLFW.glfwSetCursorPosCallback(window.getWindowID(), (windowID, xpos, ypos) -> {
			this.currentPos.x = xpos;
			this.currentPos.y = ypos;
		});

		GLFW.glfwSetCursorEnterCallback(window.getWindowID(), (windowID, entered) -> {
			this.inWindow = entered;
		});

		GLFW.glfwSetMouseButtonCallback(window.getWindowID(), (windowID, button, action, mode) -> {
			this.leftDown = button == GLFW.GLFW_MOUSE_BUTTON_1 && action == GLFW.GLFW_PRESS;
			this.rightDown = button == GLFW.GLFW_MOUSE_BUTTON_2 && action == GLFW.GLFW_PRESS;
		});

		GLFW.glfwSetScrollCallback(window.getWindowID(), ((windowID, xoffset, yoffset) -> {
			this.scroll = (float)yoffset;
		}));
	}

	public void input (Window window) {
		scroll = 0;
		displVec.set(0F, 0F);
		if (previousPos.x > 0 && previousPos.y > 0 && inWindow) {
			double dx = currentPos.x - previousPos.x;
			double dy = currentPos.y - previousPos.y;

			if (dx != 0) {
				displVec.y = (float) dy;
			}

			if (dy != 0) {
				displVec.x = (float) dx;
			}
		}
		previousPos.x = currentPos.x;
		previousPos.y = currentPos.y;

	}


	public float getScroll () {
		return scroll;
	}

	public boolean isLeftDown () {
		return leftDown;
	}

	public boolean isRightDown () {
		return rightDown;
	}

	public boolean isInWindow () {
		return inWindow;
	}

	public Vector2f getDisplVec () {
		return displVec;
	}

	public Vector2d getCurrentPos () {
		return currentPos;
	}
}
