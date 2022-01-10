package com.ajwestley.pathfinder.Screens;

import com.ajwestley.pathfinder.Pathfinder;
import com.ajwestley.pathfinder.tools.PathGrid;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class GridCreationScreen implements Screen {

    PathGrid grid;

    Pathfinder app;

    public GridCreationScreen(Pathfinder app, int width, int height) {

        this.app = app;

        Pathfinder.CURRENT_HEIGHT = Pathfinder.GRID_SCREEN_HEIGHT;
        Pathfinder.CURRENT_WIDTH = Pathfinder.GRID_SCREEN_WIDTH;
        Gdx.graphics.setWindowedMode(Pathfinder.GRID_SCREEN_WIDTH, Pathfinder.GRID_SCREEN_HEIGHT);

        grid = new PathGrid(width, height);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
}
