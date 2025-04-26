package ru.samsung.satbox2d;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
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
    KinematicBody platform2;
    DynamicBodyCircle[] balls = new DynamicBodyCircle[2];
    DynamicBodyTriangle[] triangles = new DynamicBodyTriangle[3];
    DynamicBodyBox[] boxes = new DynamicBodyBox[4];

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
        Box2D.init();
        world = new World(new Vector2(0, -10), true);
        debugRenderer = new Box2DDebugRenderer();
        Gdx.input.setInputProcessor(new MyInputProcessor());

        StaticBody floor = new StaticBody(world, 8, 1, 15.5f, 0.5f);
        StaticBody wall1 = new StaticBody(world, 1, 5, 0.5f, 6);
        StaticBody wall2 = new StaticBody(world, 15, 5, 0.5f, 6);

        for (int i = 0; i < balls.length; i++) {
            balls[i] = new DynamicBodyCircle(world, 3, 6+i, 0.3f);
        }

        for (int i = 0; i < boxes.length; i++) {
            boxes[i] = new DynamicBodyBox(world, 13, 6+i, 0.4f, 0.8f);
        }

        for (int i = 0; i < triangles.length; i++) {
            triangles[i] = new DynamicBodyTriangle(world, 8, 5+i, 1, 0.7f);
        }

        //platform = new KinematicBody(world, 1, 4f, 5f, 1);
        //platform2 = new KinematicBody(world, 8, 7f, 3f, 0.5f);
    }

    @Override
    public void render() {
        // события
        //platform.move();
        //platform2.move();

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

    class MyInputProcessor implements InputProcessor{
        Vector3 startTouch = new Vector3();
        Vector3 endTouch = new Vector3();
        Body bodyTouched;

        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            startTouch.set(screenX, screenY, 0);
            camera.unproject(startTouch);
            for (int i = 0; i < balls.length; i++) {
                if(balls[i].hit(startTouch)){
                    bodyTouched = balls[i].body;
                }
            }
            for (int i = 0; i < boxes.length; i++) {
                if(boxes[i].hit(startTouch)){
                    bodyTouched = boxes[i].body;
                }
            }
            for (int i = 0; i < triangles.length; i++) {
                if(triangles[i].hit(startTouch)){
                    bodyTouched = triangles[i].body;
                }
            }
            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            endTouch.set(screenX, screenY, 0);
            camera.unproject(endTouch);
            if(bodyTouched != null) {
                Vector3 drag = new Vector3(endTouch).sub(startTouch);
                bodyTouched.applyLinearImpulse(new Vector2(drag.x, drag.y), bodyTouched.getPosition(), true);
                bodyTouched = null;
            }
            return false;
        }

        @Override
        public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(float amountX, float amountY) {
            return false;
        }
    }
}
