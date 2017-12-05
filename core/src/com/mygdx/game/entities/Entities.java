package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.physiks.Box2DWorld;


public abstract class Entities extends Actor{

    public float width;
    public float height;
    public Vector3 pos;
    public EntitiType type;
    public boolean remove = false;
    public Texture texture;

    public Entities(Box2DWorld box2DWorld, float width, float height, float x, float y, Texture texture){
        pos = new Vector3(x,y,0);
        this.width = width;
        this.height = height;
        this.texture = texture;
        createBody(box2DWorld,this);
    }

    public abstract void createBody(Box2DWorld box2DWorld,Entities ent);
    public abstract void destroyBody(Box2DWorld box2DWorld);

    public enum EntitiType{
        BIRD,
        BOTTOM_PIPE,
        TOP_PIPE,
        SCORE,
        TOP
    }
    public abstract Body getBody();
}
