package Logic;

//@author guido
public class Clock {

    private static final int TIME = 1000;

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
     * Wait for received seconds and prints the remaining time every second
     *
     * @param seconds time to wait
     */
    public static void sleepPrint(int seconds) {
        if (seconds > 0) {
            while (seconds > 0) {
                try {
                    Thread.sleep(TIME);
                } catch (InterruptedException ex) {
                    System.out.println(ex);
                }
                seconds--;
                System.out.println(seconds);
            }
        }
    }

    private CountdownTimer countdownTimer;

    /**
     *
     * @param seconds
     * @param printActive
     */
    public void startCountdown(int seconds, boolean printActive) {
        countdownTimer = new CountdownTimer(seconds, printActive);
        new Thread(countdownTimer, "countdownTimer").start();
    }

    public int getCountdownTime() {
        if(countdownTimer == null){
            return 0;
        }
        return countdownTimer.seconds;
    }

    private static class CountdownTimer implements Runnable {

        int seconds;
        boolean printActive;

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
