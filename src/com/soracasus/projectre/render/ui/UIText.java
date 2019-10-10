package com.soracasus.projectre.render.ui;

import com.soracasus.projectre.core.MouseInput;
import com.soracasus.projectre.core.Window;
import org.joml.Vector2f;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;

public class UIText implements IUIObject {

	private String text;
	private float fontSize;
	private Vector2f position;
	private int alignmentFlags;
	private NVGColor nvgColour;

	public UIText (Vector2f position, String text, float fontSize, int alignmentFlags, Colour colour) {
		this.position = position;
		this.text = text;
		this.fontSize = fontSize;
		this.alignmentFlags = alignmentFlags;
		this.nvgColour = NVGColor.create();
		NanoVG.nvgRGBAf(colour.r(), colour.g(), colour.b(), colour.a(), nvgColour);
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

		NanoVG.nvgText(vg, _x, _y, text);
	}

	@Override
	public void input (Window window, MouseInput mouseInput) {
		// Note(Sora): No input needed for this
	}
}
