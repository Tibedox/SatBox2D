package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

public class SatBox2D extends ApplicationAdapter {
	// глобальные константы
	public static final float WORLD_WIDTH = 16, WORLD_HEIGHT = 9;
	public static final int CIRCLE = 0, BOX = 1, POLY = 2;

	// системные объекты
	SpriteBatch batch;
	OrthographicCamera camera;
	Vector3 touch;
	World world;
	Box2DDebugRenderer debugRenderer;

	// ресурсы
	Texture imgBrickTexture;
	Texture imgBadTexture;
	Texture imgSmileTexture;
	TextureRegion imgBrick;
	TextureRegion imgBad;
	TextureRegion imgSmile;

	// наши объекты и переменные
	StaticBody floor;
	StaticBody wallLeft, wallRight;
	KinematicBody platform;
	DynamicBody[] ball = new DynamicBody[20];
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
		touch = new Vector3();
		world = new World(new Vector2(0, -9.8f), true);
		debugRenderer = new Box2DDebugRenderer();
		//debugRenderer.setDrawVelocities(true);

		imgBrickTexture = new Texture("grass.png");
		imgBadTexture = new Texture("brick.png");
		imgSmileTexture = new Texture("smile.png");
		imgBrick = new TextureRegion(imgBrickTexture, 0, 0, 100, 100);
		imgBad = new TextureRegion(imgBadTexture, 0, 0, 100, 100);
		imgSmile = new TextureRegion(imgSmileTexture, 0, 0, 128, 128);

		floor = new StaticBody(world, 8, 0.6f, 15.5f, 0.8f);
		wallLeft = new StaticBody(world, 0.6f, 5, 0.5f, 7.6f);
		wallRight = new StaticBody(world, 15.4f, 5, 0.5f, 7.6f);
		//platform = new KinematicBody(world, 0, 2.8f, 3, 0.5f);
		for (int i = 0; i < ball.length; i++) {
			if(i<2) {
				ball[i] = new DynamicBody(world, WORLD_WIDTH / 2 + MathUtils.random(-7, 7), 8 + i, 0.3f);
			}
			else if(i<4) {
				ball[i] = new DynamicBody(world, WORLD_WIDTH / 2 + MathUtils.random(-7, 7), 8 + i, 0.8f, 0.4f);
			}
			else {
				Polygon polygon = new Polygon(new float[]{0, 1, 1, -1, -1, -1});
				for (int j = 0; j < polygon.getVertices().length; j++) {
					polygon.getVertices()[j] *= 0.5f;
				}
				ball[i] = new DynamicBody(world, WORLD_WIDTH / 2 + MathUtils.random(-7, 7), 8 + i, polygon);
			}
		}
	}

	@Override
	public void render () {
		// касания
		if(Gdx.input.justTouched()){
			touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touch);
			for(DynamicBody b: ball){
				if(b.hit(touch.x, touch.y)) {
					b.setImpulse(new Vector2(0, 2));
				}
			}
		}

		// события
		//platform.move();

		// отрисовка
		ScreenUtils.clear(0.2f, 0, 0.3f, 1);
		debugRenderer.render(world, camera.combined);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		/*batch.draw(imgBrick, floor.getX(), floor.getY(), floor.getWidth(), floor.getHeight());
		batch.draw(imgBrick, wallLeft.getX(), wallLeft.getY(), wallLeft.getWidth(), wallLeft.getHeight());
		batch.draw(imgBrick, wallRight.getX(), wallRight.getY(), wallRight.getWidth(), wallRight.getHeight());
		batch.draw(imgBrick, platform.getX(), platform.getY(), platform.getWidth()/2, platform.getHeight()/2,
				platform.getWidth(), platform.getHeight(), 1, 1, platform.getAngle());
		for (DynamicBody b: ball) {
			if(b.type == CIRCLE) {
				batch.draw(imgSmile, b.getX(), b.getY(), b.getWidth() / 2, b.getHeight() / 2,
						b.getWidth(), b.getHeight(), 1, 1, b.getAngle());
			} else {
				batch.draw(imgBad, b.getX(), b.getY(), b.getWidth() / 2, b.getHeight() / 2,
						b.getWidth(), b.getHeight(), 1, 1, b.getAngle());
			}
		}*/
		batch.end();
		world.step(1/60f, 6, 2);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		debugRenderer.dispose();
		world.dispose();
		imgBrickTexture.dispose();
		imgSmileTexture.dispose();
	}
}

