package com.soracasus.projectre.render.ui;

import com.soracasus.projectre.core.MouseInput;
import com.soracasus.projectre.core.Window;

public interface IUIObject {

	void render(long vg, Window window);
	void input(Window window, MouseInput mouseInput);

}
