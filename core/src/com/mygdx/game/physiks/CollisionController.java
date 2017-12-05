package com.mygdx.game.physiks;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.entities.Bird;
import com.mygdx.game.entities.Entities;
import com.mygdx.game.stage.FirstScreenStage;

public class CollisionController implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if(hitBird(a,b)){
            ((Bird)a.getBody().getUserData()).hit();
        } else if(hitBird(b,a)){
            ((Bird)b.getBody().getUserData()).hit();
        }
    }
    private boolean hitBird(Fixture a, Fixture b){
        Entities ea = (Entities)a.getBody().getUserData();
        Entities eb = (Entities)b.getBody().getUserData();
        return ea.type == Entities.EntitiType.BIRD && (eb.type == Entities.EntitiType.BOTTOM_PIPE || eb.type == Entities.EntitiType.TOP_PIPE);
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if(score(a,b)){
            FirstScreenStage.score();
        } else if(score(b,a)){
            FirstScreenStage.score();
        }
    }

    private boolean score(Fixture a, Fixture b){
        Entities ea = (Entities)a.getBody().getUserData();
        Entities eb = (Entities)b.getBody().getUserData();
        return ea.type == Entities.EntitiType.BIRD && eb.type == Entities.EntitiType.SCORE;
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
