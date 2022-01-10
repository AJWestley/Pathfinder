package com.ajwestley.pathfinder.Screens;

import com.ajwestley.pathfinder.Pathfinder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;

public class MenuScreen implements Screen {

    BitmapFont title;
    BitmapFont create;

    Pathfinder app;

    public MenuScreen (Pathfinder app) {

        this.app = app;

        Pathfinder.CURRENT_HEIGHT = Pathfinder.MENU_HEIGHT;
        Pathfinder.CURRENT_WIDTH = Pathfinder.MENU_WIDTH;
        Gdx.graphics.setWindowedMode(Pathfinder.MENU_WIDTH, Pathfinder.MENU_HEIGHT);

        title = new BitmapFont(Gdx.files.internal("fonts/font60.fnt"));
        title.setColor(Color.BLACK);

        create = new BitmapFont(Gdx.files.internal("fonts/font40.fnt"));
        create.setColor(Color.BLACK);

    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {

        ScreenUtils.clear(1, 1, 1, 1);

        GlyphLayout title = new GlyphLayout(this.title, "Pathfinder");
        float xTitle = Pathfinder.CURRENT_WIDTH / 2f - title.width / 2f;
        float yTitle = Pathfinder.CURRENT_HEIGHT - title.height - 20;

        GlyphLayout create = new GlyphLayout(this.create,"Create Grid");
        float xCreate = Pathfinder.CURRENT_WIDTH / 2f - create.width / 2f;
        float yCreate = 100;

        if (isHoveredOver((int) xCreate, (int) yCreate, (int) create.width, (int) create.height)) this.create.setColor(Color.GREEN);
        else this.create.setColor(Color.BLACK);

        app.batch.begin();

        this.title.draw(app.batch, title, xTitle, yTitle);
        this.create.draw(app.batch, create, xCreate, yCreate);

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

    private boolean isHoveredOver (int x, int y, int width, int height) {
        int mouseX = (int) app.camera.getInput().x, mouseY = (int) app.camera.getInput().y;
        return mouseX < (x + width) && mouseX > x && mouseY > y - height
                && mouseY < y;
    }
}
