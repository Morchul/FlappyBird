package com.mygdx.game.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.controler.InputController;
import com.mygdx.game.entities.*;
import com.mygdx.game.graphic.Graphics;
import com.mygdx.game.physiks.Box2DWorld;
import com.mygdx.game.physiks.CollisionController;
import com.mygdx.game.screen.FirstScreen;

import java.util.ArrayList;
import java.util.List;

public class FirstScreenStage extends Stage {

    private List<Entities> entities = new ArrayList<Entities>();
    private OrthographicCamera camera;
    private Box2DDebugRenderer debugRenderer;
    private Box2DWorld box2DWorld;
    private CollisionController collisionController;
    private InputController controller;
    public static Bird bird;
    private SpriteBatch batch;
    private FirstScreen parent;

    private static float distanceHeight = 10;
    private static float time = 3.5f, timeDone = 0;
    private static final float STAGE_WIDTH = 40;
    private static final float STAGE_HEIGHT = 40;
    private static float score;
    private static final float TIME_STEP = 1 / 300f;
    private float accumulator  = 0f;


    public FirstScreenStage(FirstScreen parent){
        score = 0;
        timeDone = 0;
        Graphics.loadGraphics();
        batch = new SpriteBatch();
        this.parent = parent;
        setupCamera();
        collisionController = new CollisionController();
        debugRenderer = new Box2DDebugRenderer(true,true,false,true,true,true);
        box2DWorld = new Box2DWorld();
        box2DWorld.world.setContactListener(collisionController);
        controller = new InputController();
        batch.setProjectionMatrix(camera.combined);
        Gdx.input.setInputProcessor(controller);
        createTop();
        createBird();
        createNewPipes();

    }

    private void createTop(){
        new Top(box2DWorld,STAGE_WIDTH,1,0,STAGE_HEIGHT,null);
    }

    private void createBird(){
        bird = new Bird(box2DWorld,3,3,STAGE_WIDTH/4,STAGE_HEIGHT/2,Graphics.bird);
        addActor(bird);
        entities.add(bird);
    }

    private void createNewPipes(){
            float width = 3;

            float distance = MathUtils.random(distanceHeight) + 7;
            float bottomHeight = MathUtils.random(20) + 3;
            float topHeight = STAGE_HEIGHT - (bottomHeight + distance);

            Pipes topPipe = new Pipes(box2DWorld, width, topHeight, 50, bottomHeight + distance,Graphics.topPipe,false);
            Pipes bottomPipe = new Pipes(box2DWorld, width, bottomHeight, 50, 0,Graphics.bottomPipe,true);
            ScoreArea score = new ScoreArea(box2DWorld, width, STAGE_HEIGHT, 50, 0,null);

            addActor(score);
            addActor(topPipe);
            addActor(bottomPipe);
            entities.add(topPipe);
            entities.add(bottomPipe);
            entities.add(score);
    }

    public static void score(){
        if(!bird.hit) {
            score++;
            System.out.println("Score: " + score);
            if (Pipes.SPEED >= -15)
                Pipes.SPEED = -1 * ((score+30)/5);
            if(distanceHeight > 1 && score % 3 == 0)
                distanceHeight--;
            if(time > 1.5)
                time -= 0.01;
        }
    }

    @Override
    public void draw(){
        super.draw();
        batch.begin();

        drawBackground();
        drawBird();
        drawPipes();

        batch.end();
        //debugRenderer.render(box2DWorld.world,camera.combined);
    }
    private void drawBird(){
        batch.draw(bird.texture,    //texture
                bird.getBody().getPosition().x-(bird.width/2),    //position x
                bird.getBody().getPosition().y-(bird.height/2),   //position y
                bird.width/2,       // originX
                bird.height/2,      // originY
                bird.width,    //width
                bird.height,   //height
                1f,   //scale x
                1f,   //scale y
                bird.getBody().getAngle() * MathUtils.radiansToDegrees, //rotation
                0,      //src x
                0,      //sry y
                bird.texture.getWidth(), //src width
                bird.texture.getHeight(), //src height
                false,
                false);
    }
    private void drawPipes(){
        boolean rotate = false;
        for(Entities ent : entities){
            if(ent.type == Entities.EntitiType.TOP_PIPE)
                rotate = true;
            if (ent.texture != null && ent.type != Entities.EntitiType.BIRD) {
                batch.draw(ent.texture,
                        ent.getBody().getPosition().x-ent.width/2,
                        ent.getBody().getPosition().y-ent.height/2,
                        ent.width,
                        ent.height,0,0,ent.texture.getWidth(),ent.texture.getHeight(),false,rotate);
            }
            rotate = false;
        }
    }

    private void drawBackground(){
        batch.draw(Graphics.background,0,0,STAGE_WIDTH,STAGE_HEIGHT);
    }

    private void setupCamera(){
        camera = new OrthographicCamera(STAGE_WIDTH, STAGE_HEIGHT);
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0f);
        camera.update();
    }

    @Override
    public void act(float delta){
        super.act(delta);
        timeDone += delta;

        accumulator += delta;
        while(accumulator >= delta){
            box2DWorld.world.step(TIME_STEP,6,2);
            accumulator -= TIME_STEP;
        }

        if(timeDone >= time){
            timeDone = 0;
            createNewPipes();
        }else{
            tryRemoveBodies();
        }

        checkOutOfWorld();
    }

    private void tryRemoveBodies(){

            List<Entities> bodyToRemove = new ArrayList<Entities>();
            for (Entities e : entities) {
                    if (e.getBody() != null) {
                        if (e.remove) {
                            System.out.println("Destroy: " + e.getBody());
                            bodyToRemove.add(e);
                            e.destroyBody(box2DWorld);
                        }
                    }
            }
            entities.removeAll(bodyToRemove);
    }

    private void checkOutOfWorld(){

        for(Entities e : entities) {
            if (e.getBody().getPosition().x + e.width < 0 && e.type != Entities.EntitiType.BIRD) {
                e.remove = true;
            }
            if(e.type == Entities.EntitiType.BIRD && e.getBody().getPosition().y + e.height < 0){
                finish();
            }
        }
    }

    private void finish(){
        System.out.println("Finish");
        parent.finish();
    }

    public static float getScore(){
        return score;
    }

    @Override
    public void dispose(){
        super.dispose();
        batch.dispose();
    }
}
