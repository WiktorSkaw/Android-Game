package pl.wiktor.com;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.BoringLayout;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private MyMainThread thread;
    Player player;
    Block[] blocks;
    private Bitmap background;


    public GameView(Context context) {
        super(context);

        getHolder().addCallback(this);
        thread = new MyMainThread(getHolder(), this);
        setFocusable(true);


        blocks = new Block[8];
        for(int i = 0 ; i < 8 ; i++) {
            Block block = new Block(getResources());
            blocks[i] = block;
        }
        //!!!!!!!TEST!!!!!!!!!
        blocks[0].x = 1600;
        blocks[1].x = 2000;
        blocks[2].x = 2500;
        blocks[3].x = 100;
        blocks[4].x = 0;
        blocks[5].x = 500;
        blocks[6].x = 1000;
        blocks[7].x = 800;

        blocks[0].y = 0;
        blocks[1].y = 200;
        blocks[2].y = 400;
        blocks[3].y = 800;
        blocks[4].y = 900;
        blocks[5].y = 1000;
        blocks[6].y = 1900;
        blocks[7].y = 1100;

    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {

        player = new Player(BitmapFactory.decodeResource(getResources(),R.drawable.ninjaa));

        background = BitmapFactory.decodeResource(getResources(),R.drawable.background);
        background = Bitmap.createScaledBitmap(background,2520 , 1080, false);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        boolean retry = true;
        while(retry){
            try{
                thread.setRunning(false);
                thread.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            retry=false;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawBitmap(background, 0, 0, null);
            player.draw(canvas);
            for (Block block : blocks)
                block.draw(canvas,block.y);
        }
    }

    public void update(){

        player.update();

        for(Block block : blocks){
            block.x -= block.SPEED;

           if(block.x<-400) { //jezeli wyjdzie poza plansze w calosci
                Random random = new Random();
                block.x = 2520; //wraca na poczatek
                block.y = random.nextInt(1000);

                //poprawic lepiej byloby zrobic funkcje ktora by rowniez patrzyla czy te randy nie sa podobne zeby bloki na siebie nie nachodzily (rekurencja)
                if(block.y > 300 && block.y < 900)
                    block.y = random.nextInt(1080);
           }

           //przykeljanie sie do blokow w zaleznosci od strony bloku
            //z gory
            if( player.y + 100 >= block.y - 8 && player.y + 100 <= block.y + 8 && player.x >= block.x - 130 && player.x <= block.x + 400 ) {
                player.y = block.y - 100;
            //z dolu
            }else if(player.y >= block.y + 42 && player.y <= block.y + 58 && player.x >= block.x - 130 && player.x <= block.x + 400 ){
                player.y = block.y + 50;
                }

        }
        // koniec gry przy wyleceniu z mapy
        if(player.getY() <= -50 || player.getY() >= 1080 ) { //bo krotnosc predkosci czyli nie moze byc ==
            thread.setRunning(false);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                player.firstClick = true;
                player.isClicked = !player.isClicked;
                if (player.isClicked) {
                    player.y = player.y - player.SPEED;
                } else {
                    player.y = player.y + player.SPEED;
                }
            break;
        }
        return true;
    }
}


