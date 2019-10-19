package com.soracasus.projectre.core;

import com.soracasus.projectre.DebugState;
import com.soracasus.projectre.DocumentState;
import com.soracasus.projectre.ForestState;
import com.soracasus.projectre.MenuState;
import com.soracasus.projectre.RutikalEscape;
import com.soracasus.projectre.ShipState;

import javax.swing.plaf.nimbus.State;

public class StateManager {

	public static RutikalEscape mainGameState;
	public static MenuState menuState;
	public static DocumentState documentState;
	public static ShipState shipState;
	public static ForestState forestState;
	public static DebugState debugState;

	private static IGameLogic currentState;

	private static Window window;

	public static void init(final Window window) {
		mainGameState = new RutikalEscape();
		menuState = new MenuState();
		documentState = new DocumentState();
		shipState = new ShipState();
		forestState = new ForestState();
		debugState = new DebugState();
		StateManager.window = window;
	}

	public static void setState(IGameLogic state) {
		if(!state.initialized())
			state.init(window);
		currentState = state;
	}

	public static IGameLogic getCurrentState() {
		return currentState;
	}


}
