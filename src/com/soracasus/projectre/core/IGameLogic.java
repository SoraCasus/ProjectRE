package com.soracasus.projectre.core;

public interface IGameLogic {

	void init(final Window window);
	void input(final Window window, final MouseInput mouseInput);
	void render(final Window window);
	void update(float dt);

	boolean initialized();

}
