package com.ajwestley.pathfinder.Screens;

import com.ajwestley.pathfinder.Pathfinder;
import com.ajwestley.pathfinder.tools.PathGrid;
import com.ajwestley.pathfinder.tools.ScreenCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GridCreationScreen implements Screen {

    int[][][] coOrds;
    int cellDimensions;
    int overallWidth;
    int overallHeight;
    int borderSize;

    PathGrid grid;
    Texture empty;
    Texture obstacle;
    Texture start;
    Texture destination;
    Texture outBorder;
    Texture inBorder;

    Pathfinder app;

    public GridCreationScreen(Pathfinder app, int width, int height) {

        this.app = app;

        Pathfinder.CURRENT_HEIGHT = Pathfinder.GRID_SCREEN_HEIGHT;
        Pathfinder.CURRENT_WIDTH = Pathfinder.GRID_SCREEN_WIDTH;
        Gdx.graphics.setWindowedMode(Pathfinder.GRID_SCREEN_WIDTH, Pathfinder.GRID_SCREEN_HEIGHT);
        app.camera = new ScreenCamera(Pathfinder.CURRENT_WIDTH, Pathfinder.CURRENT_HEIGHT);

        grid = new PathGrid(width, height);

        coOrds = new int[width][height][2];
        determineCoOrds();

        obstacle = new Texture(Gdx.files.internal("obstacle.png"));
        empty = new Texture(Gdx.files.internal("empty.png"));
        start = new Texture(Gdx.files.internal("start.png"));
        destination = new Texture(Gdx.files.internal("end.png"));
        outBorder = new Texture(Gdx.files.internal("outerBorder.png"));
        inBorder = new Texture(Gdx.files.internal("innerBorder.png"));

    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {

        ScreenUtils.clear(1, 1, 1, 1);

        int x = checkMouseOver()[0], y = checkMouseOver()[1];

        if (x != -1 && y != -1 && Gdx.input.isTouched()) {

            grid.addObstacle(x, y);

        }

        app.batch.begin();

        drawGrid(app.batch);

        app.batch.end();

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

    private void drawGrid(SpriteBatch batch) {

        for (int i = 0; i < grid.width; i++) {
            batch.draw(inBorder, coOrds[i][0][0] - borderSize, coOrds[i][0][1] - borderSize, borderSize, overallHeight);
            for (int j = 0; j < grid.height; j++) {
                batch.draw(inBorder, coOrds[0][j][0] - borderSize, coOrds[0][j][1] - borderSize, overallWidth, borderSize);
                if (grid.grid[i][j] == PathGrid.OBSTACLE) batch.draw(obstacle, coOrds[i][j][0], coOrds[i][j][1], cellDimensions, cellDimensions);
                else if (grid.grid[i][j] == PathGrid.START_POINT) batch.draw(start, coOrds[i][j][0], coOrds[i][j][1], cellDimensions, cellDimensions);
                else if (grid.grid[i][j] == PathGrid.DESTINATION_POINT) batch.draw(destination, coOrds[i][j][0], coOrds[i][j][1], cellDimensions, cellDimensions);
                else batch.draw(empty, coOrds[i][j][0], coOrds[i][j][1], cellDimensions, cellDimensions);

            }
        }

        batch.draw(outBorder, coOrds[0][0][0] - borderSize, coOrds[0][0][1] - borderSize, borderSize, overallHeight );
        batch.draw(outBorder, coOrds[0][0][0] - borderSize, coOrds[0][0][1] - borderSize, overallWidth, borderSize );
        batch.draw(outBorder, coOrds[grid.width - 1][0][0] + cellDimensions, coOrds[grid.width - 1][0][1] - borderSize, borderSize, overallHeight);
        batch.draw(outBorder, coOrds[0][grid.height - 1][0] - borderSize, coOrds[0][grid.height - 1][1] + cellDimensions, overallWidth, borderSize);

    }

    private void determineCoOrds() {

        if (grid.width > 70 || grid.height > 50) borderSize = 1;
        else if (grid.width >= 30 || grid.height >= 20) borderSize = 2;
        else borderSize = 3;

        int maxHeight = Pathfinder.CURRENT_HEIGHT - 100, maxWidth = Pathfinder.CURRENT_WIDTH - 250;
        int cellWidth = (maxWidth - borderSize * (grid.width + 1)) / grid.width;
        int cellHeight = (maxHeight - borderSize * (grid.height + 1)) / grid.height;
        cellDimensions = Math.min(cellWidth, cellHeight);

        overallWidth = borderSize * (grid.width + 1) + grid.width * cellDimensions;
        overallHeight = borderSize * (grid.height + 1) + grid.height * cellDimensions;
        int minX = Pathfinder.CURRENT_WIDTH / 2 - overallWidth / 2 + 100 ;
        int minY = Pathfinder.CURRENT_HEIGHT / 2 - overallHeight / 2;

        for (int i = 0; i < grid.width; i++) {
            for (int j = 0; j < grid.height; j++) {

                coOrds[i][j][0] = minX + borderSize * (i + 1) + i * cellDimensions;
                coOrds[i][j][1] = minY + borderSize * (j + 1) + j * cellDimensions;

            }
        }

    }

    private int[] checkMouseOver() {

        int[] coOrds = new int[2];
        coOrds[0] = -1;
        coOrds[1] = -1;

        for (int i = 0; i < grid.width; i++) {
            for (int j = 0; j < grid.height; j++) {

                if (app.camera.getInput().x > this.coOrds[i][j][0] &&
                        app.camera.getInput().x < (this.coOrds[i][j][0] + cellDimensions) &&
                        app.camera.getInput().y > this.coOrds[i][j][1] &&
                        app.camera.getInput().y < this.coOrds[i][j][1] + cellDimensions) {

                    coOrds[0] = i;
                    coOrds[1] = j;

                }

            }
        }

        return coOrds;

    }
}
