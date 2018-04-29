package org.daleon.Test1;

import org.daleon.framework.Object.DynamicGameObject;
import org.daleon.framework.math.Rectangle;

public class Point extends DynamicGameObject{


    public Point(float x, float y, float width, float height) {
        super(x, y, width, height);

    }
    public void update(float deltaTime){
        bounds.lowerLeft.set(position).sub(bounds.width/2,bounds.height/2);
    }
}
