package aiims.survey.techmahindra.myapplication.SurveyComponents;

import java.util.Arrays;
import java.util.List;

import aiims.survey.techmahindra.myapplication.Database.DbConstants;


/**
 * Created by yashjain on 7/4/17.
 */

public class QuestionResolver {


    private AnsweredQuestion[] requiredAnsweredQuestions;
    private String[][] requiredOptions;


    private Question question;
    private boolean resolved;

    public QuestionResolver() {
        resolved = false;
    }

    public QuestionResolver(Question question) {
        this.question = question;
        resolved = false;
    }

    public void resolveQuestion() {
        int length = requiredAnsweredQuestions.length;
        if (requiredOptions.length != length) {
            resolved = false;
        }
        for (int i = 0; i < length; i++) {
            if (requiredAnsweredQuestions[i].isAnswered()) {
                if (!isPresent(i)) {
                    resolved = false;
                    return;
                }
            }
            else {
                resolved = false;
            }
            resolved = true;
        }
    }

    private boolean isPresent(int i) {
        String[] answer = requiredAnsweredQuestions[i].getAnswer();
        List<String> mList=Arrays.asList(requiredOptions[i]);
        for (int j = 0; j < answer.length; j++){
            if (mList.contains(answer[j])) {
                if(question.getMode()== DbConstants.OR)
                    return true;
            }
            else{
                if(question.getMode()==DbConstants.AND)
                    return false;
            }
        }
        return true;
    }


    /*Getter and Setters*/


    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public AnsweredQuestion[] getRequiredAnsweredQuestions() {
        return requiredAnsweredQuestions;
    }

    public void setRequiredAnsweredQuestions(AnsweredQuestion[] requiredAnsweredQuestions) {
        this.requiredAnsweredQuestions = requiredAnsweredQuestions;
    }

    public String[][] getRequiredOptions() {
        return requiredOptions;
    }

    public void setRequiredOptions(String[][] requiredOptions) {
        this.requiredOptions = requiredOptions;
    }

    public boolean isResolved() {
        resolveQuestion();
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }
}
