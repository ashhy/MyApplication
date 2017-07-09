package aiims.survey.techmahindra.myapplication.SurveyComponents;

/**
 * Created by yashjain on 7/6/17.
 */

public class AnsweredQuestion extends Question {

    private String[] answer;
    private int oNo;

    public boolean isAnswered(){
        return oNo==-1;
    }


    /*Constructor*/

    public AnsweredQuestion(){
        this.oNo=-1;//Required to Set if Question is UnAnswered
    }

    public AnsweredQuestion(Question question,String[] answer,int oNo){
        super(question);
        this.answer = answer;
        if(answer==null)
            this.oNo=-1;
        else if(answer.length==0)
            this.oNo=-1;
        else
            this.oNo = oNo;
    }

    /*Getter and Setter*/

    public Question getQuestion() {
        return this;
    }

    public void setQuestion(Question question) {
        this.sId=question.sId;
        this.qNo=question.qNo;
        this.qText=question.qText;
        this.options=question.options;
        this.language=question.language;
        this.multiSelect=question.multiSelect;
        this.pageNo=question.pageNo;
        this.resolver=question.resolver;
    }

    public String[] getAnswer() {
        return answer;
    }

    public void setAnswer(String[] answer) {
        this.answer = answer;
        if(answer==null)
            this.oNo=-1;
    }

    public int getoNo() {
        return oNo;
    }

    public void setoNo(int oNo) {
        this.oNo = oNo;
    }
}
