package ru.samsung.satbox2d;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
    public static final float WORLD_WIDTH = 16, WORLD_HEIGHT = 9;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    KinematicBody platform;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
        Box2D.init();
        world = new World(new Vector2(0, -10), true);
        debugRenderer = new Box2DDebugRenderer();

        StaticBody floor = new StaticBody(world, 8, 1, 15.5f, 0.5f);
        StaticBody wall1 = new StaticBody(world, 1, 5, 0.5f, 6);
        StaticBody wall2 = new StaticBody(world, 15, 5, 0.5f, 6);

        DynamicBodyCircle[] ball = new DynamicBodyCircle[220];
        for (int i = 0; i < ball.length; i++) {
            ball[i] = new DynamicBodyCircle(world, 7+MathUtils.random(-0.1f, 0.1f), 6+i, 0.2f+MathUtils.random(0, 0.3f));
        }

        DynamicBodyBox[] box = new DynamicBodyBox[220];
        for (int i = 0; i < box.length; i++) {
            box[i] = new DynamicBodyBox(world, 9+MathUtils.random(-0.1f, 0.1f), 6+i, 0.2f+MathUtils.random(0, 0.3f), 0.2f+MathUtils.random(0, 0.3f));
        }

        platform = new KinematicBody(world, 1, 4f, 3f, 1);
    }

    @Override
    public void render() {
        // события
        if(platform.body.getPosition().x>WORLD_WIDTH ||
            platform.body.getPosition().x<0) {
            platform.vx = -platform.vx;
            platform.body.setLinearVelocity(platform.vx, 0);
        }
            // отрисовка
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        debugRenderer.render(world, camera.combined);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.end();
        world.step(1/60f, 6, 2);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
