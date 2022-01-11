package com.ajwestley.pathfinder.Screens;

import com.ajwestley.pathfinder.Pathfinder;
import com.ajwestley.pathfinder.tools.PathGrid;
import com.ajwestley.pathfinder.tools.ScreenCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import javax.swing.*;

public class GridCreationScreen implements Screen {

    int[][][] coOrds;
    int cellDimensions;
    int overallWidth;
    int overallHeight;
    int borderSize;
    char selected;

    int buttonX = 50;
    int obstacleButtonY = Pathfinder.CURRENT_HEIGHT - 150;
    int startButtonY = Pathfinder.CURRENT_HEIGHT - 250;
    int endButtonY = Pathfinder.CURRENT_HEIGHT - 350;
    int eraseButtonY = Pathfinder.CURRENT_HEIGHT - 450;
    int buttonWidth = 200, buttonHeight = 60;
    int simulateButtonX = 80, simulateButtonY = 20;
    int simulateButtonWidth = 150, simulateButtonHeight = 60;
    int clearButtonWidth = 100, clearButtonHeight = 40;
    int clearButtonX = Pathfinder.GRID_SCREEN_WIDTH - clearButtonWidth - 10, clearButtonY = Pathfinder.GRID_SCREEN_HEIGHT - clearButtonHeight - 10;
    int menuButtonWidth = 100, menuButtonHeight = 40;
    int menuButtonX = Pathfinder.GRID_SCREEN_WIDTH - menuButtonWidth - 10, menuButtonY = 10;

    private final float simWaitBase;
    int simWaitMultiplier;
    float waitTime;
    float timePassed;
    boolean simStarted;
    boolean editingEnabled;

    PathGrid grid;
    Texture empty;
    Texture obstacle;
    Texture start;
    Texture destination;
    Texture active;
    Texture visited;
    Texture showPath;
    Texture outBorder;
    Texture inBorder;
    Texture selectedEraseButton;
    Texture selectedEndButton;
    Texture selectedStartButton;
    Texture selectedObstacleButton;
    Texture unselectedButton;
    Texture simulateUnselected;
    Texture simulateSelected;
    Texture clearSelected;
    Texture clearUnselected;
    Texture menuSelected;
    Texture menuUnselected;
    BitmapFont btnText;
    JPanel invis;

    Pathfinder app;

    public GridCreationScreen(Pathfinder app, int width, int height) {

        this.app = app;
        invis = new JPanel();

        simWaitBase = 0.1f;
        simWaitMultiplier = 1;
        waitTime = simWaitBase / simWaitMultiplier;
        timePassed = 0;
        simStarted = false;
        editingEnabled = true;

        Pathfinder.CURRENT_HEIGHT = Pathfinder.GRID_SCREEN_HEIGHT;
        Pathfinder.CURRENT_WIDTH = Pathfinder.GRID_SCREEN_WIDTH;
        Gdx.graphics.setWindowedMode(Pathfinder.GRID_SCREEN_WIDTH, Pathfinder.GRID_SCREEN_HEIGHT);
        app.camera = new ScreenCamera(Pathfinder.CURRENT_WIDTH, Pathfinder.CURRENT_HEIGHT);

        grid = new PathGrid(width, height);
        selected = PathGrid.OBSTACLE;

        coOrds = new int[width][height][2];
        determineCoOrds();

        obstacle = new Texture(Gdx.files.internal("obstacle.png"));
        empty = new Texture(Gdx.files.internal("empty.png"));
        start = new Texture(Gdx.files.internal("start.png"));
        destination = new Texture(Gdx.files.internal("end.png"));
        active = new Texture(Gdx.files.internal("activespace.png"));
        visited = new Texture(Gdx.files.internal("visitedspace.png"));
        showPath = new Texture(Gdx.files.internal("path.png"));
        outBorder = new Texture(Gdx.files.internal("outerBorder.png"));
        inBorder = new Texture(Gdx.files.internal("innerBorder.png"));
        selectedEraseButton = new Texture(Gdx.files.internal("selectedEraseButton.png"));
        selectedEndButton = new Texture(Gdx.files.internal("selectedEndButton.png"));
        selectedStartButton = new Texture(Gdx.files.internal("selectedStartButton.png"));
        selectedObstacleButton = new Texture(Gdx.files.internal("selectedObstacleButton.png"));
        unselectedButton = new Texture(Gdx.files.internal("unselectedButton.png"));
        simulateSelected = new Texture(Gdx.files.internal("simulateSelected.png"));
        simulateUnselected = new Texture(Gdx.files.internal("simulateUnselected.png"));
        clearSelected = new Texture(Gdx.files.internal("clearSelected.png"));
        clearUnselected = new Texture(Gdx.files.internal("clearUnselected.png"));
        menuSelected = new Texture(Gdx.files.internal("menuSelected.png"));
        menuUnselected = new Texture(Gdx.files.internal("menuUnselected.png"));
        btnText = new BitmapFont(Gdx.files.internal("fonts/font30.fnt"));
        btnText.setColor(Color.BLACK);

    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {

        if (grid.complete) simStarted = false;

        if (simStarted) {
            timePassed += delta;
            editingEnabled = false;
        }
        else editingEnabled = true;

        ScreenUtils.clear(1, 1, 1, 1);

        if (app.camera.getInput().x > simulateButtonX && app.camera.getInput().x < (simulateButtonX + simulateButtonWidth) &&
                app.camera.getInput().y > simulateButtonY && app.camera.getInput().y < (simulateButtonY + simulateButtonHeight) &&
                Gdx.input.justTouched()) {
            if (editingEnabled) {
                if (grid.isValidGrid()) {
                    simStarted = true;
                    grid.complete = false;
                    grid.softClear();
                    grid.begin();
                } else
                    JOptionPane.showMessageDialog(invis, "Invalid Grid! Grid must have a Start point and an End point.", "Pathfinder", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (app.camera.getInput().x > clearButtonX && app.camera.getInput().x < (clearButtonX + clearButtonWidth) &&
                app.camera.getInput().y > clearButtonY && app.camera.getInput().y < (clearButtonY + clearButtonHeight) &&
                Gdx.input.justTouched()) {

            if (editingEnabled) grid.clear();

        }

        if (app.camera.getInput().x > menuButtonX && app.camera.getInput().x < (menuButtonX + menuButtonWidth) &&
                app.camera.getInput().y > menuButtonY && app.camera.getInput().y < (menuButtonY + menuButtonHeight) &&
                Gdx.input.justTouched()) {

            if (editingEnabled) app.setScreen(new MenuScreen(app));

        }



        move();

        GlyphLayout obstacle = new GlyphLayout(btnText, "Obstacle");
        GlyphLayout start = new GlyphLayout(btnText, "Start Point");
        GlyphLayout destination = new GlyphLayout(btnText, "End Point");
        GlyphLayout erase = new GlyphLayout(btnText, "Erase");

        int x = checkMouseOver()[0], y = checkMouseOver()[1];

        if (x != -1 && y != -1) {

            if (selected == PathGrid.OBSTACLE && Gdx.input.isTouched() && editingEnabled) grid.addObstacle(x, y);
            else if (selected == PathGrid.START_POINT && Gdx.input.isTouched() && editingEnabled) grid.addStart(x, y);
            else if (selected == PathGrid.DESTINATION_POINT && Gdx.input.isTouched() && editingEnabled) grid.addDestination(x, y);
            else if (selected == PathGrid.EMPTY && Gdx.input.isTouched() && editingEnabled) grid.erase(x, y);

            if (editingEnabled && Gdx.input.isTouched()) grid.softClear();

        }

        checkButtonClicked();

        app.batch.begin();

        drawGrid(app.batch);

        if (!simStarted) {
            if (selected == PathGrid.OBSTACLE)
                app.batch.draw(selectedObstacleButton, buttonX, obstacleButtonY, buttonWidth, buttonHeight);
            else app.batch.draw(unselectedButton, buttonX, obstacleButtonY, buttonWidth, buttonHeight);
            if (selected == PathGrid.START_POINT)
                app.batch.draw(selectedStartButton, buttonX, startButtonY, buttonWidth, buttonHeight);
            else app.batch.draw(unselectedButton, buttonX, startButtonY, buttonWidth, buttonHeight);
            if (selected == PathGrid.DESTINATION_POINT)
                app.batch.draw(selectedEndButton, buttonX, endButtonY, buttonWidth, buttonHeight);
            else app.batch.draw(unselectedButton, buttonX, endButtonY, buttonWidth, buttonHeight);
            if (selected == PathGrid.EMPTY)
                app.batch.draw(selectedEraseButton, buttonX, eraseButtonY, buttonWidth, buttonHeight);
            else app.batch.draw(unselectedButton, buttonX, eraseButtonY, buttonWidth, buttonHeight);

            btnText.draw(app.batch, obstacle, buttonX + 20, obstacleButtonY + buttonHeight / 2f + obstacle.height / 2);
            btnText.draw(app.batch, start, buttonX + 20, startButtonY + buttonHeight / 2f + start.height / 2);
            btnText.draw(app.batch, destination, buttonX + 20, endButtonY + buttonHeight / 2f + destination.height / 2);
            btnText.draw(app.batch, erase, buttonX + 20, eraseButtonY + buttonHeight / 2f + erase.height / 2);

            if (app.camera.getInput().x > simulateButtonX && app.camera.getInput().x < (simulateButtonX + simulateButtonWidth) &&
                    app.camera.getInput().y > simulateButtonY && app.camera.getInput().y < (simulateButtonY + simulateButtonHeight)) {
                app.batch.draw(simulateSelected, simulateButtonX, simulateButtonY, simulateButtonWidth, simulateButtonHeight);
            } else
                app.batch.draw(simulateUnselected, simulateButtonX, simulateButtonY, simulateButtonWidth, simulateButtonHeight);

            if (app.camera.getInput().x > clearButtonX && app.camera.getInput().x < (clearButtonX + clearButtonWidth) &&
                    app.camera.getInput().y > clearButtonY && app.camera.getInput().y < (clearButtonY + clearButtonHeight)) {
                app.batch.draw(clearSelected, clearButtonX, clearButtonY, clearButtonWidth, clearButtonHeight);
            } else
                app.batch.draw(clearUnselected, clearButtonX, clearButtonY, clearButtonWidth, clearButtonHeight);

            if (app.camera.getInput().x > menuButtonX && app.camera.getInput().x < (menuButtonX + menuButtonWidth) &&
                    app.camera.getInput().y > menuButtonY && app.camera.getInput().y < (menuButtonY + menuButtonHeight)) {
                app.batch.draw(menuSelected, menuButtonX, menuButtonY, menuButtonWidth, menuButtonHeight);
            } else
                app.batch.draw(menuUnselected, menuButtonX, menuButtonY, menuButtonWidth, menuButtonHeight);
        }

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
                else if (grid.grid[i][j] == PathGrid.EMPTY) batch.draw(empty, coOrds[i][j][0], coOrds[i][j][1], cellDimensions, cellDimensions);
                else if (grid.grid[i][j] == PathGrid.ACTIVE) batch.draw(active, coOrds[i][j][0], coOrds[i][j][1], cellDimensions, cellDimensions);
                else if (grid.grid[i][j] == PathGrid.VISITED) batch.draw(visited, coOrds[i][j][0], coOrds[i][j][1], cellDimensions, cellDimensions);
                else if (grid.grid[i][j] == PathGrid.PATH) batch.draw(showPath, coOrds[i][j][0], coOrds[i][j][1], cellDimensions, cellDimensions);

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

        int maxHeight = Pathfinder.CURRENT_HEIGHT - 100, maxWidth = Pathfinder.CURRENT_WIDTH - 350;
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

    private void checkButtonClicked() {
        if (!simStarted) {
            if (app.camera.getInput().x > buttonX && app.camera.getInput().x < (buttonX + buttonWidth) &&
                    app.camera.getInput().y > obstacleButtonY && app.camera.getInput().y < (obstacleButtonY + buttonHeight) &&
                    Gdx.input.justTouched()) {
                selected = PathGrid.OBSTACLE;
                grid.softClear();
            }

            if (app.camera.getInput().x > buttonX && app.camera.getInput().x < (buttonX + buttonWidth) &&
                    app.camera.getInput().y > startButtonY && app.camera.getInput().y < (startButtonY + buttonHeight) &&
                    Gdx.input.justTouched()) {
                selected = PathGrid.START_POINT;
                grid.softClear();
            }

            if (app.camera.getInput().x > buttonX && app.camera.getInput().x < (buttonX + buttonWidth) &&
                    app.camera.getInput().y > endButtonY && app.camera.getInput().y < (endButtonY + buttonHeight) &&
                    Gdx.input.justTouched()) {
                selected = PathGrid.DESTINATION_POINT;
                grid.softClear();
            }

            if (app.camera.getInput().x > buttonX && app.camera.getInput().x < (buttonX + buttonWidth) &&
                    app.camera.getInput().y > eraseButtonY && app.camera.getInput().y < (eraseButtonY + buttonHeight) &&
                    Gdx.input.justTouched()) {
                selected = PathGrid.EMPTY;
                grid.softClear();
            }
        }

    }

    private void move() {

        if (timePassed >= waitTime) {

            timePassed = 0;
            grid.progress();
            if (grid.nosolution) {
                JOptionPane.showMessageDialog(invis, "No Solution.", "Pathfinder", JOptionPane.ERROR_MESSAGE);
            }

        }

    }
}
