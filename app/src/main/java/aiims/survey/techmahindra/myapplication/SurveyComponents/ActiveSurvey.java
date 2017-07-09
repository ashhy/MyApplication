package aiims.survey.techmahindra.myapplication.SurveyComponents;

import java.util.ArrayList;

/**
 * Created by yashjain on 7/4/17.
 */

public class ActiveSurvey  extends Survey{

    private AnsweredQuestion[] answeredQuestions;
    private Response response;
    private ResponderInfo responderInfo;

    public ActiveSurvey(Survey survey){
        super(survey);
        //TODO: Find Questions from DB and set answeredQuestions!!!
    }

    public void setQuestions(){
        //TODO: Find Questions from DB and set answerQuestions!!!
    }

    public void saveResponse(){

    }

    public ActiveSurvey(){
    }


    public AnsweredQuestion[] getQuestionsForPage(int pageNo){
        if(pageNo<1&&pageNo>this.totalPage) return null;
        ArrayList<AnsweredQuestion> questionList=new ArrayList<>();
        for(int i=0;i<answeredQuestions.length;i++){
            if(answeredQuestions[i].getPageNo()==pageNo){
                questionList.add(answeredQuestions[i]);
            }
        }
        return (AnsweredQuestion[]) questionList.toArray();
    }


    public AnsweredQuestion[] getAnsweredQuestions() {
        return answeredQuestions;
    }

    public void setAnsweredQuestions(AnsweredQuestion[] answeredQuestions) {
        this.answeredQuestions = answeredQuestions;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public ResponderInfo getResponderInfo() {
        return responderInfo;
    }

    public void setResponderInfo(ResponderInfo responderInfo) {
        this.responderInfo = responderInfo;
    }
}
