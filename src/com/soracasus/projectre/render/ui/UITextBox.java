package com.soracasus.projectre.render.ui;

import com.soracasus.projectre.core.MouseInput;
import com.soracasus.projectre.core.Window;
import org.joml.Vector2f;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;

public class UITextBox implements IUIObject {

	private Vector2f position;
	private float maxWidth;
	private String text;
	private float fontSize;

	private int alignmentFlags;

	private NVGColor nvgColour;

	public UITextBox (Vector2f position, float fontSize, float maxWidth, Colour colour, int alignmentFlags, String text) {
		this.position = position;
		this.maxWidth = maxWidth;
		this.text = text;
		this.nvgColour = NVGColor.create();
		NanoVG.nvgRGBAf(colour.r(), colour.g(), colour.b(), colour.a(), nvgColour);
		this.alignmentFlags = alignmentFlags;
		this.fontSize = fontSize;
	}

	@Override
	public void render (long vg, Window window) {
		float w = window.getOpts().width;
		float h = window.getOpts().height;

		float _x = (w * (position.x + 1.0F)) / 2.0F;
		float _y = (h * (position.y + 1.0F)) / 2.0F;


		NanoVG.nvgFontSize(vg, fontSize);
		NanoVG.nvgFontFace(vg, "font");
		NanoVG.nvgTextAlign(vg, alignmentFlags);
		NanoVG.nvgFillColor(vg, nvgColour);
		NanoVG.nvgTextBox(vg, _x, _y, maxWidth, text);

	}

	@Override
	public void input (Window window, MouseInput mouseInput) {

	}

	public Vector2f getPosition () {
		return position;
	}

	public void setPosition (Vector2f position) {
		this.position = position;
	}

	public float getMaxWidth () {
		return maxWidth;
	}

	public void setMaxWidth (float maxWidth) {
		this.maxWidth = maxWidth;
	}

	public String getText () {
		return text;
	}

	public void setText (String text) {
		this.text = text;
	}
}
