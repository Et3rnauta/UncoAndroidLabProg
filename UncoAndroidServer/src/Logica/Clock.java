package Logica;

//@author guido
public class Clock {

    private static final int TIME = 1000;
    private static CountdownTimer countdownTimer;

    /**
     * Wait for received seconds
     *
     * @param seconds time to wait
     */
    public static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * TIME);
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Sets a Thread that waits and prints the remaining time every second
     *
     * @param seconds time to wait
     * @param printActive if true, the clock prints the remaining time every
     * second
     */
    public static void startCountdown(int seconds, boolean printActive) {
        countdownTimer = new CountdownTimer(seconds, printActive);
        new Thread(countdownTimer, "countdownTimer").start();
    }

    /**
     * If there is a countdownTimer set, returns the remaining time
     * 
     * @return the time left on the countdown
     */
    public static int getCountdownTime() {
        if (countdownTimer == null) {
            return 0;
        }
        return countdownTimer.seconds;
    }

    private static class CountdownTimer implements Runnable {

        int seconds;
        
        private boolean printActive;

        public CountdownTimer(int seconds, boolean printActive) {
            this.seconds = seconds;
            this.printActive = printActive;
        }

        @Override
        public void run() {
            while (seconds > 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    System.out.println(ex);
                }
                seconds--;
                if (printActive) {
                    System.out.println(seconds);
                }
            }
        }
    }

}
