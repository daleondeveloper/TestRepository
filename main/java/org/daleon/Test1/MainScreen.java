package org.daleon.Test1;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.*;
import org.daleon.framework.Game;
import org.daleon.framework.Input;
import org.daleon.framework.gl.Camera2D;
import org.daleon.framework.gl.SpriteBatcher;
import org.daleon.framework.impl.GLScreen;
import org.daleon.framework.math.OverlapTester;
import org.daleon.framework.math.Vector2;

import javax.microedition.khronos.opengles.GL10;
import java.text.DecimalFormat;
import java.util.List;

public class MainScreen extends GLScreen {

    final float heightMax;
    final float widthMax;

    Camera2D guiCam;
    SpriteBatcher batcher;
    Vector2 touchPoint;
    static float width;
    static float height;
    float[] maxminPosition = new float[4];
    Point movePoint = null;

    EditText editText;
    Button button;
    TextView textView;

    static  float stateTime = 0;
    final static DecimalFormat df = new DecimalFormat(".0");

    Point[] points;
    public MainScreen(Game game){
        super(game);
        maxminPosition[0] = 7;
        maxminPosition[1] = 0;
        maxminPosition[2] = 10;
        maxminPosition[3] = 0;

        width = 15;
        height = 5;

        guiCam = new Camera2D(glGraphics, width,height);
        batcher = new SpriteBatcher(glGraphics, 10000);
        touchPoint =new Vector2();

        // realise five point
        points = new Point[5];
        for(int i = 1; i <= points.length; i++){
            points[i-1] = new Point(i,i,width*0.05f,height*0.5f);
        }
        movePoint = points[0];

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) glGame.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        heightMax = metrics.heightPixels;
        widthMax = metrics.widthPixels;
        glGame.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                button = new Button(glGame);
                button.setText("SetX");

                button.setX(widthMax - (widthMax*3/7));

                editText = new EditText(glGame);
                editText.setText("Write Y cordinate");
//
                editText.setX(widthMax-(widthMax/6));
                editText.setY(heightMax-(heightMax*4/5));

                textView = new TextView(glGame);
                textView.setX(widthMax - (widthMax/4));
                textView.setY(heightMax - ((heightMax*2)/3));

                LinearLayout testLayout = new LinearLayout(glGame);

                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams((int)widthMax/8, (int)(heightMax/10));

                //lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                 testLayout.addView(editText,new RelativeLayout.LayoutParams((int)widthMax/7,(int)heightMax/8));
                testLayout.addView(textView,lp);
                 testLayout.addView(button,lp);
                glGame.frameLayout.addView(testLayout);
            }
        });
    }

    @Override
    public void update(float deltaTime) {
        for(int i = 0; i < points.length; i++){
            points[i].update(deltaTime);
        }
        //check alltouch on touchScreen
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        if(touchEvents != null)
            for(Input.TouchEvent touchEvent : touchEvents){
                Input.TouchEvent event = touchEvent;

                if(event.type == Input.TouchEvent.TOUCH_DOWN){

                    touchPoint.set(event.x,event.y);
                    guiCam.touchToWorld(touchPoint);
                    for(int i = 0; i < points.length; i++) {

                        // set move point
                       if (OverlapTester.pointInRectangle(points[i].bounds, touchPoint)) {
                            movePoint = points[i];
                            final String s = "SetYPoint" + points[i].position.x;
                            glGame.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    button.setText(s);
                                }
                            });
                       }

                   }


                }
                if(event.type == Input.TouchEvent.TOUCH_DRAGGED){
                    touchPoint.set(event.x,event.y);
                    guiCam.touchToWorld(touchPoint);

                    //Move move point
                    if(movePoint != null){
                        movePoint.position.y = touchPoint.y;
                        movePoint.bounds.lowerLeft.set(movePoint.position).sub(movePoint.bounds.width/2,movePoint.bounds.height/2);
                    }
                }
            }
            glGame.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //delete all text if edit text pressed
                    if(editText.isPressed()){
                        editText.setText("");
                    }
                    //set cordinate to movePoint from editText if button pressed
                    if(button.isPressed()){
                        String s = editText.getText().toString();
                        char [] c = s.toCharArray();
                        s = "";
                        boolean point = false;
                        boolean noBreak = true;
                        for(int i = 0 ; i < c.length; i++){
                            if((int)c[i] > 47 && (int)c[i]<58){
                               s +=c[i];
                            }else if(!point && (int)c[i] == 46){
                                s += c[i];
                            }else{
                                noBreak = false;
                                break;
                            }
                        }
                        if(noBreak ){
                            movePoint.position.y = Float.parseFloat(s);
                            textView.setText("");
                        }else if(!noBreak){
                            textView.setText("Incorect number");
                        }

                    }
                }
            });
            stateTime += deltaTime;
    }

    @Override
    public void present(float deltaTime) {
        GL10 gl = glGraphics.getGL();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        setCameraView(maxminPosition[0],maxminPosition[1],maxminPosition[2],maxminPosition[3]);
        guiCam.setViewportAndMatrices();
        gl.glEnable(GL10.GL_TEXTURE_2D);


        batcher.beginBatch(Assets.picture);
        //white fon
        batcher.drawSprite(guiCam.position.x ,guiCam.position.y,guiCam.frustumWidth,guiCam.frustumHeight,Assets.white);

        //show cordinate
        presentTitleCordinate(maxminPosition[0],maxminPosition[1],maxminPosition[2],maxminPosition[3]);

        //present five point
        for(int i = 0; i< points.length; i ++)
            batcher.drawSprite(points[i].position.x,points[i].position.y,width*0.005f,height*0.05f,Assets.black);

        // graf by method square
        List<float[]> list = MyMathTest.metodKvadrativ(points);
        drawGraf(list);

        // graf by lagrant
        list = MyMathTest.buildLagran(points);
        drawGraf(list);

        batcher.endBatch();

        //enable A
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA,GL10.GL_ONE_MINUS_SRC_ALPHA);

        //present fontTexture
        drawNumberCordinate(maxminPosition[0],maxminPosition[1],maxminPosition[2],maxminPosition[3]);

        gl.glDisable(GL10.GL_BLEND);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
    public void drawGraf(List<float[]> list){
        for (int i = 1; i < list.size()-1; i++){
            float[] d1 = list.get(i -1);
            float[] d2 = list.get(i);
            float[] d3 = list.get(i+1);
            float angle = MyMathTest.takeAngelRectangles(d2[0],d2[1],d3[0],d3[1]);
            while (angle > 180){
                angle -= 180;
            }while (angle < 0){
                angle+=180;
            }
            batcher.drawSprite(d2[0],d2[1],Math.abs(d1[0]-d3[0]),Math.abs(d1[1]-d3[1])<height*0.01?height*0.02f:Math.abs(d1[1]-d3[1]),(angle+90),Assets.black);

        }


    }

    public void setCameraView(float maxX,float minX, float maxY , float minY){
        float widthX = Math.abs(minX*1.4f) + Math.abs(maxX*1.4f);
        float heightY = Math.abs(minY * 1.4f) + Math.abs(maxY*1.4f);
        float x = (maxX + minX)/2;
        float y = (maxY + minY)/2;
        guiCam.frustumHeight = heightY;
        guiCam.frustumWidth = widthX;
        guiCam.position.x = x;
        guiCam.position.y = y;
    }
    public void presentTitleCordinate(float maxX,float minX ,float maxY, float minY){
        float x = (maxX + minX)/2;
        float y = (maxY + minY)/2;
        float widthX = Math.abs(minX*1.2f) + Math.abs(maxX*1.2f);
        float widthY = widthX/200;
        float heightY = Math.abs(minY * 1.2f) + Math.abs(maxY*1.2f);
        float heightX = heightY/200;
        float xy = 0;
        float yx = 0;
        if(maxX < 0){
            xy = maxX * 1.2f;
        }else if(minX > 0){
            xy = minX * 1.2f;
        }
        if(maxY < 0){
            yx = maxY * 1.2f;
        }else if(minY > 0){
            yx = minY*1.2f;
        }
        batcher.drawSprite(x,xy,widthX,heightX,Assets.black);
        batcher.drawSprite(yx,y,widthY,heightY,Assets.black);

        float xLine = (maxX*1.3f)/10;
        float yLine = maxY/10;
        for(float i = 1; i < maxX; i += xLine){
            batcher.drawSprite(i*xLine,0,width*0.001f,height*0.1f,Assets.black);
        }
        for(float i = 1; i < maxY; i +=yLine){
            batcher.drawSprite(0,i*yLine,width*0.01f, height*0.01f,Assets.black);
        }

    }
    public void drawNumberCordinate(float maxX, float minX, float maxY, float minY){
        batcher.beginBatch(Assets.fontTexture);

        float xLine = (maxX*1.3f)/10;
        float yLine = maxY/10;

        float showNumber = 0;
        for(float i = 1; i < maxX; i += xLine){
            showNumber = i * xLine;
            String s = df.format(showNumber);
            Assets.font.drawText(batcher,s,(i*xLine)- width*0.008f,-height*0.1f,0.005f);
        }

        for(float i = 1; i < maxY; i +=yLine){
            showNumber = i * yLine;
            String s = df.format(showNumber);
            Assets.font.drawText(batcher,s,-width*0.05f,i*yLine,0.005f);
        }
        batcher.endBatch();
    }

    @Override
    public void dispose() {

    }
}
