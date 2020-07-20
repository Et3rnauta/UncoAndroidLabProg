package Logic;

// @author guido
import java.util.ArrayList;
import java.util.Collections;

public class Question {

    private String question, rightAns, wrongAns1, wrongAns2, wrongAns3;
    public int time;
    
    private static int DEFAULTTIME = 60;

    public Question(String[] qString) {
        this.question = qString[0];
        this.rightAns = qString[1];
        this.wrongAns1 = qString[2];
        this.wrongAns2 = qString[3];
        this.wrongAns3 = qString[4];
        if (qString.length == 6) {
            this.time = Integer.decode(qString[5]);
        } else {
            this.time = DEFAULTTIME;
        }
        
    }

    public String toRequest() {
        ArrayList<String> answers = new ArrayList<>();

        answers.add(rightAns);
        answers.add(wrongAns1);
        answers.add(wrongAns2);
        answers.add(wrongAns3);

        Collections.shuffle(answers);

        return String.format("setQuestion:(%s)(%s)(%s)(%s)(%s)(%d)",
                question, answers.get(0), answers.get(1), answers.get(2), answers.get(3), time);
    }

    public boolean isRightAns(String answer) {
        return answer.equals(rightAns);
    }
}
