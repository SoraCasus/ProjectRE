package com.soracasus.projectre.core;

import com.soracasus.projectre.audio.AudioEngine;
import com.soracasus.projectre.render.entity.Radio;
import com.soracasus.projectre.util.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RutikalEngine implements Runnable {

	public static final boolean DEBUG = true;
	public static final String VERSION = "Beta 0.2.5c";

	private static final int TARGET_FPS = 75;
	private static final int TARGET_UPS = 30;

	private static List<IDisposable> disposables = new ArrayList<>();

	private final Window window;
	private final Thread thread;
	private final MouseInput mouseInput;

	public RutikalEngine (Window.WindowOpts opts) {
		this.window = new Window(opts);
		this.thread = new Thread(this, "RUTIKAL_ENGINE_THREAD");
		this.mouseInput = new MouseInput(); }

	public synchronized void start () {
		String osName = System.getProperty("os.name");
		if (osName.contains("Mac"))
			thread.run();
		else
			thread.start();
	}

	@Override
	public void run () {
		init();
		gameLoop();
		saveGame();
		cleanUp();
	}

	private void init () {
		window.createWindow();
		loadFile();
		AudioEngine.init();
		mouseInput.init(window);
		StateManager.init(window);
		StateManager.setState(StateManager.forestState);
	}

	private void gameLoop () {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1e9 / (double) TARGET_UPS;
		double delta = 0;

		while (window.isOpen()) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			input();

			while (delta >= 1) {
				update((float) delta);
				delta--;
			}

			input();
			render();
		}
	}

	private void loadFile() {
		REFile file = new REFile("saves/save.json");

		if(file.getStream() == null) return;

		StringBuilder sb = new StringBuilder();
		try(BufferedReader reader = file.getReader()) {
			String line;
			while((line = reader.readLine()) != null)
				sb.append(line);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		if(sb.length() != 0) {
			System.out.println(sb);
			JSONObject obj = new JSONObject(sb.toString());
			// Radio.radioCount = obj.getInt("radiosCollected");
		}
	}

	private void saveGame() {
		JSONObject saveFile = new JSONObject();
		saveFile.put("radiosCollected", Radio.radioCount);

		File file = new File("res/saves/save.json");
		file.getParentFile().mkdirs();

		try(FileWriter fw = new FileWriter(file)) {
			fw.write(saveFile.toString());
			System.out.println("Successfully saved game");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void cleanUp () {
		for (IDisposable i : disposables)
			i.delete();
		AudioEngine.delete();
	}

	private void input () {
		if (StateManager.getCurrentState() != null)
			StateManager.getCurrentState().input(window, mouseInput);
		mouseInput.input(window);
	}

	private void update (float interval) {
		if (StateManager.getCurrentState() != null)
			StateManager.getCurrentState().update(interval);

	}

	private void render () {
		if (StateManager.getCurrentState() != null)
			StateManager.getCurrentState().render(window);

		window.update();
	}

	public static void addDisposable (IDisposable d) {
		disposables.add(d);
	}

	public static void main (String[] args) {
		Window.WindowOpts opts = new Window.WindowOpts();
		opts.title = "Project RE";
		opts.width = 1280;
		opts.height = 720;

		RutikalEngine engine = new RutikalEngine(opts);
		engine.start();
	}
}
