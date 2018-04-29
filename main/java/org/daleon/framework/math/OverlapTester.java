package org.daleon.framework.math;

public class OverlapTester {
    public static boolean overlapCircle(Circle c1, Circle c2){
        float distance = c1.center.distSquared(c2.center);
        float radiusSum = c1.radius + c2.radius;
        return distance <= radiusSum * radiusSum;
    }
    public static boolean pointInRectanglesWithLimit(Rectangle r1,Vector2 point, float percent){
        Rectangle r2 = new Rectangle(r1.lowerLeft.x*percent,r1.lowerLeft.y*percent,
                r1.width*percent,r1.height*percent);
        if(percent > 1){
            if(!OverlapTester.pointInRectangle(r1,point) && OverlapTester.pointInRectangle(r2,point)){
                return true;
            }else return false;
        }else if(percent < 1){
            if(OverlapTester.pointInRectangle(r1,point) && !OverlapTester.pointInRectangle(r2,point)){
                return true;
            }else return false;
        }else if(percent == 1){
            if(OverlapTester.pointInRectangle(r1,point)){
                return true;
            }else return false;
        }
        return false;
    }
    public static boolean overlapRectanglesWithPosition(Rectangle r1,Rectangle r2, int positionX,int positionY,float scale) {
        if (positionX == 0 && positionY == 1) {
            if (r1.lowerLeft.x + scale <= r2.lowerLeft.x + r2.width && r1.lowerLeft.x + r1.width - scale >= r2.lowerLeft.x &&
                    r1.lowerLeft.y + r1.height / 2 <= r2.lowerLeft.y && r1.lowerLeft.y + r1.height >= r2.lowerLeft.y)
                return true;
            else return false;
        } else if (positionX == 0 && positionY == -1) {
            if (r1.lowerLeft.x + scale <= r2.lowerLeft.x + r2.width && r1.lowerLeft.x + r1.width - scale >= r2.lowerLeft.x &&
                    r1.lowerLeft.y + r1.height / 2 >= r2.lowerLeft.y + r2.height && r1.lowerLeft.y <= r2.lowerLeft.y + r2.height)
                return true;
            else return false;
        }else if( positionX == 1 && positionY ==0){
            if(r1.lowerLeft.x + r2.width/2 <= r2.lowerLeft.x && r1.lowerLeft.x + r1.width >= r2.lowerLeft.x &&
                    r1.lowerLeft.y + scale <= r2.lowerLeft.y + r2.height  && r1.lowerLeft.y + r1.height - scale >= r2.lowerLeft.y)
                return true;
            else return false;
        }else if(positionX == -1 && positionY == 0){
            if(r1.lowerLeft.x  <= r2.lowerLeft.x + r2.width && r1.lowerLeft.x + r1.width/2 >= r2.lowerLeft.x + r2.width &&
                    r1.lowerLeft.y + scale <= r2.lowerLeft.y + r2.height  && r1.lowerLeft.y + r1.height - scale >= r2.lowerLeft.y)
                return true;
            else return false;
        }else if(positionX == 1 && positionY == 1) {
            return (overlapRectanglesWithPosition(r1, r2, 0, 1 , scale) &&
                    overlapRectanglesWithPosition(r1, r2, 1, 0,scale));
        }else if(positionX == 1 && positionY == -1) {
            return (overlapRectanglesWithPosition(r1, r2, 0, -1,scale) &&
                    overlapRectanglesWithPosition(r1, r2, 1, 0,scale));
        }else if(positionX == -1 && positionY == 1) {
            return (overlapRectanglesWithPosition(r1, r2, 0, 1,scale) &&
                    overlapRectanglesWithPosition(r1, r2, -1, 0,scale));
        }else if(positionX == -1 && positionY == -1) {
            return (overlapRectanglesWithPosition(r1, r2, 0, -1,scale) &&
                    overlapRectanglesWithPosition(r1, r2, -1, 0,scale));
        }
        else return false;

    }
    public static boolean overlapRectangles(Rectangle r1, Rectangle r2 ){
        if(r1.lowerLeft.x <= r2.lowerLeft.x + r2.width && r1.lowerLeft.x + r1.width >= r2.lowerLeft.x && r1.lowerLeft.y <= r2.lowerLeft.y + r2.height && r1.lowerLeft.y + r1.height >= r2.lowerLeft.y)
            return true;
        else
            return false;
    }
    public static float takeAngelRectangles(Rectangle r1, Rectangle r2){
        float x1 = r1.lowerLeft.x + r1.width/2;
        float x2 = r2.lowerLeft.x + r2.width/2;
        float y1 = r1.lowerLeft.y + r1.height/2;
        float y2 = r2.lowerLeft.y + r2.height/2;
        Vector2 v1 = new Vector2(x2-x1,y2-y1);
        float v1d = (float)Math.sqrt(v1.x*v1.x+v1.y*v1.y);
        Vector2 v2 = new Vector2(x1+0.5f,y1-y1);
        float v2d = (float)Math.sqrt(v2.x*v2.x+v2.y*v2.y);
        float cosAngle = (v1.x*v2.x+v1.y*v2.y)/(v1d*v2d);
        float angle = (float)Math.toDegrees(Math.acos(cosAngle));
        if(y1 > y2){
            angle = 360 - angle;
        }
        return angle;
    }
    public static boolean overlapCircleRectangle(Circle c, Rectangle r){
        float closestX = c.center.x;
        float closestY = c.center.y;
        if(c.center.x < r.lowerLeft.x){
            closestX = r.lowerLeft.x;
        }else if(c.center.x > r.lowerLeft.x + r.width){
            closestX = r.lowerLeft.x + r.width;
        }

        if(c.center.y < r.lowerLeft.y){
            closestY = r.lowerLeft.y;
        }else if(c.center.y > r.lowerLeft.y + r.height){
            closestY = r.lowerLeft.y + r.height;
        }
        return c.center.distSquared(closestX, closestY) < c.radius * c.radius;
    }
    public static boolean pointInCircle(Circle c, Vector2 p){
        return c.center.distSquared(p) < c.radius * c.radius;
    }
    public static boolean pointInCircle(Circle c, float x, float y){
        return c.center.distSquared(x,y) < c.radius * c.radius;
    }
    public static boolean pointInRectangle(Rectangle r, Vector2 p){
        return r.lowerLeft.x <= p.x && r.lowerLeft.x + r.width >= p.x && r.lowerLeft.y <= p.y && r.lowerLeft.y + r.height >= p.y;
    }
    public static boolean pointInRectangle(Rectangle r, float x, float y){
        return r.lowerLeft.x <= x && r.lowerLeft.x + r.width >= x && r.lowerLeft.y <= y && r.lowerLeft.y + r.height >= y;
    }
}
