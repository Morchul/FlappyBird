package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.physiks.Box2DWorld;

public class Bird extends Entities {
    public boolean hit = false;
    private Body body;

    public Bird(Box2DWorld box2DWorld, float width, float height, float x, float y, Texture  texture){
        super(box2DWorld,width,height,x,y,texture);
        type = EntitiType.BIRD;
    }

    @Override
    public void createBody(Box2DWorld box2DWorld,Entities ent) {
        body = box2DWorld.createBird(width,pos.x,pos.y);
        body.setUserData(ent);
    }


    public void jump(){
        if(!hit)
            body.setLinearVelocity(0,12);
    }

    public void hit(){
        if(!hit) {
            body.setLinearVelocity(10, 10);
            body.applyAngularImpulse(-20f, true);
            hit = true;
        }
    }


    @Override
    public void destroyBody(Box2DWorld box2DWorld) {
        box2DWorld.world.destroyBody(body);
        body.setUserData(null);
        body = null;
    }

    @Override
    public Body getBody() {
        return body;
    }
}
