package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.physiks.Box2DWorld;

public class Pipes extends Entities {

    public static float SPEED = -6;
    private Body body;

    public Pipes(Box2DWorld box2DWorld, float width, float height, float x, float y, Texture texture,boolean bottomPype) {
        super(box2DWorld, width, height, x, y, texture);
        if (bottomPype) {type = EntitiType.BOTTOM_PIPE;}
        else {type = EntitiType.TOP_PIPE;}
    }

    @Override
    public void createBody(Box2DWorld box2DWorld, Entities ent) {
        body = box2DWorld.createPipe(width,height,pos.x,pos.y);
        body.setUserData(ent);
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

    @Override
    public void act(float delta){
        super.act(delta);
        if(body == null) {
            this.remove();
            return;
        }
        body.setLinearVelocity(SPEED, 0);
    }
}
