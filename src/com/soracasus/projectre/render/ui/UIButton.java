package com.soracasus.projectre.render.ui;

import com.soracasus.projectre.core.MouseInput;
import com.soracasus.projectre.core.REFile;
import com.soracasus.projectre.core.Window;
import com.soracasus.projectre.util.Utils;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class UIButton extends UIBox implements IUIObject {

	private NVGColor textColour;
	private String text;

	private IOnClick onClickFunc;

	private boolean hovering;

	public UIButton (UIBox box, String text, Colour textColour, IOnClick onClickFunc) {
		super(box);
		this.text = text;
		this.textColour = NVGColor.create();
		NanoVG.nvgRGBAf(textColour.r(), textColour.g(), textColour.b(), textColour.a(), this.textColour);

		this.onClickFunc = onClickFunc;
		this.hovering = false;
	}

	@Override
	public void input (Window window, MouseInput mouseInput) {
		Vector2d mouse = mouseInput.getCurrentPos();

		this.hovering = (mouse.x >= x) && (mouse.x <= x + size.x) && (mouse.y >= y) && (mouse.y <= y + size.y);
		if (hovering && mouseInput.isLeftDown())
			onClickFunc.OnClick();
	}

	@Override
	public void render (long vg, Window window) {
		super.render(vg, window);

		NanoVG.nvgFontSize(vg, 25F);
		NanoVG.nvgFontFace(vg, "font");
		NanoVG.nvgTextAlign(vg, NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_MIDDLE);
		NanoVG.nvgFillColor(vg, textColour);
		NanoVG.nvgText(vg, x + (size.x / 2), y + (size.y / 2), text);
	}

	public interface IOnClick {
		void OnClick ();
	}
}
