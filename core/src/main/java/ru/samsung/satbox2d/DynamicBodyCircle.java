package ru.samsung.satbox2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class DynamicBodyCircle {
    public float x, y;
    public float radius;
    public Body body;
    Fixture fixture;

    public DynamicBodyCircle(World world, float x, float y, float radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.5f;

        fixture = body.createFixture(fixtureDef);

        shape.dispose();
    }

    public boolean hit(Vector3 t){
        Array<Fixture> fixture = body.getFixtureList();
        for ( Fixture f:fixture) {
            if (f.testPoint(new Vector2(t.x, t.y))) {
                return true;
            }
        }
        return false;
    }
}
