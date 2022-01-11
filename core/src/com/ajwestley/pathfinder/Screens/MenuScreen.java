package com.ajwestley.pathfinder.Screens;

import com.ajwestley.pathfinder.Pathfinder;
import com.ajwestley.pathfinder.tools.ScreenCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;

import javax.swing.*;

public class MenuScreen implements Screen {

    private final int midOffset = 50;

    Stage stage;
    BitmapFont title;
    BitmapFont fields;
    TextButton create;
    TextField gridWidth;
    TextField gridHeight;
    JPanel invis;

    Pathfinder app;

    public MenuScreen (Pathfinder app) {

        this.app = app;
        invis = new JPanel();

        Pathfinder.CURRENT_HEIGHT = Pathfinder.MENU_HEIGHT;
        Pathfinder.CURRENT_WIDTH = Pathfinder.MENU_WIDTH;
        Gdx.graphics.setWindowedMode(Pathfinder.MENU_WIDTH, Pathfinder.MENU_HEIGHT);
        app.camera = new ScreenCamera(Pathfinder.CURRENT_WIDTH, Pathfinder.CURRENT_HEIGHT);

        int createButtonWidth = 150;
        int createButtonHeight = 50;
        int createButtonX = Pathfinder.CURRENT_WIDTH / 2 - createButtonWidth / 2;
        int createButtonY = 100;

        int gridWidthWidth = 150;
        int gridWidthHeight = 40;
        int gridWidthX = Pathfinder.CURRENT_WIDTH / 2;
        int gridWidthY = Pathfinder.CURRENT_HEIGHT / 2 + midOffset;
        int gridHeightWidth = 150;
        int gridHeightHeight = 40;
        int gridHeightX = Pathfinder.CURRENT_WIDTH / 2;
        int gridHeightY = Pathfinder.CURRENT_HEIGHT / 2 - midOffset;

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        title = new BitmapFont(Gdx.files.internal("fonts/font60.fnt"));
        title.setColor(Color.BLACK);
        fields = new BitmapFont(Gdx.files.internal("fonts/font30.fnt"));
        fields.setColor(Color.BLACK);

        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        gridWidth = new TextField("", skin);
        gridWidth.setPosition(gridWidthX, gridWidthY);
        gridWidth.setSize(gridWidthWidth, gridWidthHeight);
        gridHeight = new TextField("", skin);
        gridHeight.setPosition(gridHeightX, gridHeightY);
        gridHeight.setSize(gridHeightWidth, gridHeightHeight);

        create = new TextButton("Create Grid", skin);
        create.setPosition(createButtonX, createButtonY);
        create.setSize(createButtonWidth, createButtonHeight);
        create.addListener(new ClickListener() {
            @Override
            public void touchUp (InputEvent e, float x, float y, int point, int button) {

                createButtonClick();

            }
        });

        stage.addActor(create);
        stage.addActor(gridWidth);
        stage.addActor(gridHeight);

    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {

        ScreenUtils.clear(1, 1, 1, 1);

        GlyphLayout title = new GlyphLayout(this.title, "Pathfinder");
        GlyphLayout gridWidth = new GlyphLayout(this.fields, "Grid Width:");
        GlyphLayout gridHeight = new GlyphLayout(this.fields, "Grid Height:");

        float titleX = Pathfinder.CURRENT_WIDTH / 2f - title.width / 2f;
        float titleY = Pathfinder.CURRENT_HEIGHT - 50f;
        float gridWidthX = 50;
        float gridWidthY = Pathfinder.CURRENT_HEIGHT / 2f + midOffset + gridHeight.height + 12;
        float gridHeightX = 50;
        float gridHeightY = Pathfinder.CURRENT_HEIGHT / 2f - midOffset + gridHeight.height + 12;

        stage.act(delta);

        app.batch.begin();

        this.title.draw(app.batch, title, (int) titleX, (int) titleY);
        this.fields.draw(app.batch, gridWidth, (int) gridWidthX, (int) gridWidthY);
        this.fields.draw(app.batch, gridHeight, (int) gridHeightX, (int) gridHeightY);

        stage.draw();

        app.batch.end();
    }

    private void createButtonClick () {

        if (checkNumberInvalid(gridWidth.getText())) {
            JOptionPane.showMessageDialog(invis, "Invalid Width! Width must be between 2 and 100!", "Pathfinder", JOptionPane.ERROR_MESSAGE);
        }
        else if (checkNumberInvalid(gridHeight.getText())) {
            JOptionPane.showMessageDialog(invis, "Invalid Height! Height must be between 2 and 100!", "Pathfinder", JOptionPane.ERROR_MESSAGE);
        }
        else app.setScreen(new GridCreationScreen(app, Integer.parseInt(gridWidth.getText()), Integer.parseInt(gridHeight.getText())));

        create.addListener(new ClickListener() {
            @Override
            public void touchUp (InputEvent e, float x, float y, int point, int button) {

                createButtonClick();

            }
        });

    }

    private boolean checkNumberInvalid(String num) {

        int numerical;

        try {
            numerical = Integer.parseInt(num);
        } catch (NumberFormatException e) {
            return true;
        }

        return numerical < 2 || numerical > 100;
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
