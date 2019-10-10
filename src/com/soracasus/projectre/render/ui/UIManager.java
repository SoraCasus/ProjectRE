package com.soracasus.projectre.render.ui;

import com.soracasus.projectre.core.REFile;
import com.soracasus.projectre.core.Window;
import com.soracasus.projectre.util.Utils;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.util.List;

public class UIManager {

	public static long vg;

	// This cannot be a local variable, if GC takes this NanoVG will crash
	@SuppressWarnings("FieldCanBeLocal")
	private static ByteBuffer fontBuffer;

	private static Window window;

	public static void init(Window window) {

		UIManager.window = window;

		vg = NanoVGGL3.nvgCreate(NanoVGGL3.NVG_ANTIALIAS | NanoVGGL3.NVG_STENCIL_STROKES);
		if (vg == MemoryUtil.NULL)
			System.err.println("Failed to initialize NanoVG!");

		REFile fontFile = new REFile("font/data.ttf");
		fontBuffer = Utils.loadToByteBuffer(fontFile, 21 * 1024);
		int font = NanoVG.nvgCreateFontMem(vg, "font", fontBuffer, 0);
		if (font == -1)
			System.err.println("Failed to load font: " + fontFile);

	}

	public static void render (Window window, List<IUIObject> uiElements) {

		NanoVG.nvgBeginFrame(vg, window.getOpts().width, window.getOpts().height, 1);

		for (IUIObject o : uiElements) {
			o.render(vg, window);
		}

		NanoVG.nvgEndFrame(vg);

		window.restoreState();
	}

}
