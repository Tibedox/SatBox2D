package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class StaticBody {
    private float x, y;
    private float width, height;
    private Body body;

    public StaticBody(World world, float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2, height/2);
        Fixture fixture = body.createFixture(shape, 1);

        shape.dispose();
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

    public void setPosition(float x, float y) {
        body.setTransform(x, y, 0);
    }
}
