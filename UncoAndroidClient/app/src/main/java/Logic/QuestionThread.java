package Logic;

import Screens.QuestionActivity;

public class QuestionThread implements Runnable {

    QuestionActivity questionActivity;

    public QuestionThread(QuestionActivity questionActivity) {
        this.questionActivity = questionActivity;
    }

    @Override
    public void run() {
        try {
            synchronized (this) {
                wait();
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        questionActivity.endQuestion();
    }

    public synchronized void wake() {
        notify();
    }
}
