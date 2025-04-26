package ru.samsung.satbox2d;

import static ru.samsung.satbox2d.Main.WORLD_WIDTH;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class KinematicBody {
    private float x, y;
    private float width, height;
    private Body body;
    private float vx = 0;
    private float va = 0;

    public KinematicBody(World world, float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);

        PolygonShape shape0 = new PolygonShape();
        shape0.setAsBox(width/2, height/2);
        PolygonShape shape1 = new PolygonShape();
        shape1.setAsBox(height/2, width/2);

        body.createFixture(shape0, 0);
        body.createFixture(shape1, 0);
        shape0.dispose();
        shape1.dispose();

        body.setLinearVelocity(vx, 0);
        body.setAngularVelocity(va);
    }

    public void move(){
        if(body.getPosition().x>WORLD_WIDTH || body.getPosition().x<0) {
            vx = -vx;
            body.setLinearVelocity(vx, 0);
            va = -va;
            body.setAngularVelocity(va);
        }
    }
}
