package com.mygdx.game.graphic;

import com.badlogic.gdx.graphics.Texture;

public class Graphics {

    public static Texture bird;
    public static Texture sadBird;
    public static Texture background;
    public static Texture topPipe;
    public static Texture bottomPipe;

    public static void loadGraphics(){
        bottomPipe = new Texture("pipe.png");
        topPipe = new Texture("pipe.png");
        background = new Texture("background.png");
        bird = new Texture("bird.png");
        sadBird = new Texture("sadbird.png");
    }
}
