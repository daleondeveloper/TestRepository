package org.daleon.framework.Object;

import org.daleon.framework.math.Vector2;

public class DynamicGameObject extends GameObject {
    public final Vector2 velocity;
    public final Vector2 copyVelocity;
    public final Vector2 timeVelocity;
    public final Vector2 accel;

    public DynamicGameObject(float x, float y, float width, float height){
        super(x, y, width, height);
        velocity = new Vector2();
        accel = new Vector2();
        copyVelocity = new Vector2();
        timeVelocity = new Vector2();
    }

}
