package com.mygdx.game;

import static com.mygdx.game.SatBox2D.*;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class DynamicBody {
    private float x, y;
    private float width, height;
    private float r;
    Body body;
    public int type;

    public DynamicBody(World world, float x, float y, float r) {
        type = CIRCLE;
        this.x = x;
        this.y = y;
        this.r = r;
        width = height = 2*r;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(r);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.8f;

        Fixture fixture = body.createFixture(fixtureDef);
        shape.dispose();
    }

    public DynamicBody(World world, float x, float y, float width, float height) {
        type = BOX;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
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
    }

    public DynamicBody(World world, float x, float y, Polygon p) {
        type = POLY;
        this.x = x;
        this.y = y;
        this.width = p.getBoundingRectangle().getWidth();
        this.height = p.getBoundingRectangle().getHeight();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.set(p.getVertices());

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.3f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.5f;

        Fixture fixture = body.createFixture(fixtureDef);
        shape.dispose();
    }

    public DynamicBody(World world, float x, float y, Polygon p1, Polygon p2) {
        type = POLY;
        this.x = x;
        this.y = y;
        this.width = p1.getBoundingRectangle().getWidth();
        this.height = p1.getBoundingRectangle().getHeight();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);

        PolygonShape shape1 = new PolygonShape();
        shape1.set(p1.getVertices());

        FixtureDef fixtureDef1 = new FixtureDef();
        fixtureDef1.shape = shape1;
        fixtureDef1.density = 0.3f;
        fixtureDef1.friction = 0.4f;
        fixtureDef1.restitution = 0.5f;

        Fixture fixture1 = body.createFixture(fixtureDef1);
        shape1.dispose();

        PolygonShape shape2 = new PolygonShape();
        shape2.set(p2.getVertices());

        FixtureDef fixtureDef2 = new FixtureDef();
        fixtureDef2.shape = shape2;
        fixtureDef2.density = 0.3f;
        fixtureDef2.friction = 0.4f;
        fixtureDef2.restitution = 0.5f;

        Fixture fixture2 = body.createFixture(fixtureDef2);
        shape2.dispose();
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
        return body.getAngle()* MathUtils.radiansToDegrees;
    }

    public void setPosition(float x, float y) {
        body.setTransform(x, y, body.getAngle());
    }

    public boolean hit(float tx, float ty) {
        for(Fixture f: body.getFixtureList()){
            if(f.testPoint(tx, ty)){
                return true;
            }
        }
        return false;
    }

    public void setImpulse(Vector2 p) {
        body.applyLinearImpulse(p, body.getWorldCenter(), true);
    }
}
