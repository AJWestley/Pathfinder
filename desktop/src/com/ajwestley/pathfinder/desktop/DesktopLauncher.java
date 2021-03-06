package com.ajwestley.pathfinder.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ajwestley.pathfinder.Pathfinder;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = Pathfinder.CURRENT_HEIGHT;
		config.width = Pathfinder.CURRENT_WIDTH;
		new LwjglApplication(new Pathfinder(), config);
	}
}
