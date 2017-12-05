package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.enums.Screens;
import com.mygdx.game.graphic.Graphics;

public class EndScreen implements Screen{
    private Stage stage;
    private Skin skin;
    private int score,highScore;
    private MyGdxGame parent;

    public EndScreen(float score, MyGdxGame parent){
        stage = new Stage(new ScreenViewport());
        this.score = (int)score;
        this.parent = parent;
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        setHighScore();
    }
    private void setHighScore(){
        FileHandle file = Gdx.files.local("score.txt");
        try {
            highScore = Integer.parseInt(file.readString());
        }catch (NumberFormatException e){highScore = 0;}
        if(score > highScore){
            System.out.println(score);
            file.writeString(score+"", false);
            highScore = score;
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
//        table.setDebug(true);
        stage.addActor(table);

        TextButton tryAgain = new TextButton("Try Again", skin);
        tryAgain.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.setScreen(Screens.FIRST_SCREEN);
            }
        });
        TextButton exit = new TextButton("Exit", skin);
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        Label l = new Label("Game Over\nYour Score: " + score + "\nYour High Score: " + highScore,skin);
        l.setColor(Color.BLUE);
        Image i = new Image(Graphics.sadBird);

        table.add(i);
        table.row().pad(10,0,10,0);
        table.add(l);
        table.row();
        table.add(tryAgain).width(100);
        table.row();
        table.add(exit).width(100);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(1,1,1,1);
        stage.draw();
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
