package com.mygdx.game;

import static com.mygdx.game.SatBox2D.WORLD_WIDTH;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class KinematicBody {
    private float x, y;
    private float width, height;
    private Body body;
    private float vx = 2, vy = 0;
    private float av = 8;

    public KinematicBody(World world, float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2, height/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.5f;

        Fixture fixture = body.createFixture(fixtureDef);

        shape.dispose();
        body.setLinearVelocity(vx, vy);
        body.setAngularVelocity(av);
    }

    public void move() {
        x = body.getPosition().x;
        if(x>WORLD_WIDTH | x<0) {
            vx = -vx;
            av = -av;
            body.setLinearVelocity(vx, vy);
            body.setAngularVelocity(av);
        }
    }

    public float getX() {
        return body.getPosition().x-width/2;
    }

    public float getY() {
        return body.getPosition().y-height/2;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getAngle() {
        return body.getAngle()*MathUtils.radiansToDegrees;
    }

    public void setPosition(float x, float y) {
        body.setTransform(x, y, 0);
    }
}
