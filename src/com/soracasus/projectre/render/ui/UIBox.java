package com.soracasus.projectre.render.ui;

import com.soracasus.projectre.core.MouseInput;
import com.soracasus.projectre.core.Window;
import org.joml.Vector2f;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;

public class UIBox implements IUIObject {

	protected Vector2f position;
	protected Vector2f size;

	protected float x;
	protected float y;


	private NVGColor nvgColour;

	public UIBox (Vector2f position, Vector2f size, Colour colour) {
		this.position = position;
		this.size = size;
		this.nvgColour = NVGColor.create();
		NanoVG.nvgRGBAf(colour.r(), colour.g(), colour.b(), colour.a(), nvgColour);
	}

	public UIBox (UIBox box) {
		this.position = box.position;
		this.size = box.size;
		this.nvgColour = box.nvgColour;
	}

	@Override
	public void render (long vg, Window window) {
		float w = window.getOpts().width;
		float h = window.getOpts().height;

		float _x = (w * (position.x + 1.0F)) / 2.0F;
		float _y = (h * (position.y + 1.0F)) / 2.0F;

		this.x = _x;
		this.y = _y;

		NanoVG.nvgBeginPath(vg);
		NanoVG.nvgRect(vg, _x, _y, size.x, size.y);
		NanoVG.nvgFillColor(vg, nvgColour);
		NanoVG.nvgFill(vg);

	}

	@Override
	public void input (Window window, MouseInput mouseInput) {

	}
}
