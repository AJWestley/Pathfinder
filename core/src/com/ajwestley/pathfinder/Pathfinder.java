package com.ajwestley.pathfinder;

import com.ajwestley.pathfinder.Screens.MenuScreen;
import com.ajwestley.pathfinder.tools.ScreenCamera;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Pathfinder extends Game {

	public static final int MENU_WIDTH = 500;
	public static final int MENU_HEIGHT = 720;
	public static final int GRID_SCREEN_WIDTH = 1080;
	public static final int GRID_SCREEN_HEIGHT = 720;

	public static int CURRENT_WIDTH = MENU_WIDTH;
	public static int CURRENT_HEIGHT = MENU_HEIGHT;

	public ScreenCamera camera;
	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new ScreenCamera(CURRENT_WIDTH, CURRENT_HEIGHT);
		this.setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		batch.setProjectionMatrix(camera.combined());
		super.render();
	}

	@Override
	public void resize (int width, int height) {
		camera.update(width, height);
		super.resize(width, height);
	}
}
