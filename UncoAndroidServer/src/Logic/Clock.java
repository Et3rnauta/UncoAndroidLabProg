package Logic;

//@author guido
public class Clock {

    static final int TIME = 1000;

    /**
     * Wait for received seconds
     * @param seconds  : time to wait
     */
    public static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * TIME);
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }

}
