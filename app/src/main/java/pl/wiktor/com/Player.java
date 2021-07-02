package pl.wiktor.com;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Player {

    Bitmap player;
    final int SPEED = 8;
    int x;
    int y;
    boolean isClicked;
    boolean firstClick;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Player( Bitmap bmp){
        player = bmp;
        player = Bitmap.createScaledBitmap(player, 100,100,false);
        isClicked = false;
        firstClick = false;
        x = 100;
        y = 450;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(player,x,y,null);
    }

    public void update(){
        if(firstClick) {
                if (isClicked) {
                    y = y - SPEED;
                } else {
                    y = y + SPEED;
            }
        }
    }

    public Bitmap rotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        Bitmap rotateBitmap = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        source.recycle();
        return rotateBitmap;
    }
}
