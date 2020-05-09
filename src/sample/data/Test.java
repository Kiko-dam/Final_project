package sample.data;

public class Test
{
    public String question;
    public String answer1;
    public String answer2;
    public String answer3;
    public int correctAnswer;

    public Test(String question, String answer1, String answer2, String answer3, int correctAnswer) {
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer1() {
        return answer1;
    }

    public String getAnswer2() {
        return answer2;
    }


    public String getAnswer3() {
        return answer3;
    }
}
