package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.physiks.Box2DWorld;

public class Top extends Entities {

    private Body body;

    public Top(Box2DWorld box2DWorld, float width, float height, float x, float y, Texture texture) {
        super(box2DWorld, width, height, x, y, texture);
        type = EntitiType.TOP;
    }

    @Override
    public void createBody(Box2DWorld box2DWorld, Entities ent) {
        body = box2DWorld.createTop(width,height,pos.x,pos.y);
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
}
