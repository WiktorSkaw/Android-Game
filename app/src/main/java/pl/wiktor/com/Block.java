package pl.wiktor.com;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Block {
    Bitmap block;
    int x,y;
    final int SPEED=10;

    public Block(Resources res){
        block = BitmapFactory.decodeResource(res,R.drawable.blok);
        block = Bitmap.createScaledBitmap(block, 400,50,false);
    }

    public void draw(Canvas canvas, int y) {
        canvas.drawBitmap(block,x,y,null);
    }

}
