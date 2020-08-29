package logic;

import screens.WaitingActivity;

public class WaitingThread implements Runnable {

    private boolean isLast = false;
    private WaitingActivity activity;

    public WaitingThread(WaitingActivity activity) {
        this.activity = activity;
    }

    @Override
    public void run() {
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            if(isLast){
                activity.endGame();
            }else{
                activity.startQuestion();
            }
        }
    }

    public void isLast(){
        isLast = true;
    }

    public synchronized void wake(){
        notify();
    }
}
