package pl.wiktor.com;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MyMainThread extends Thread implements Runnable{

    final private SurfaceHolder surfaceHolder;
    final private GameView gameView;
    boolean running;
    public static Canvas canvas;

    public MyMainThread(SurfaceHolder surfaceHolder, GameView gameView){

        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    public void setRunning(boolean isRunning){

        running = isRunning;
    }

    @Override
    public void run(){

        while(running){
            canvas = null;
            try{
            canvas = this.surfaceHolder.lockCanvas();
            synchronized (surfaceHolder) {
                this.gameView.update();
                this.gameView.draw(canvas);
            }
            }catch(Exception E){
                System.out.println("Catch:" + E);
            }
            finally {
                if(canvas!=null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
