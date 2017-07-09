package aiims.survey.techmahindra.myapplication.NetworkComponents;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import aiims.survey.techmahindra.myapplication.SharedPreferences.SharedPrefManager;
import aiims.survey.techmahindra.myapplication.SurveyComponents.Survey;

/**
 * Created by yashjain on 7/8/17.
 */

public class SurveySync {


    public static final String SUCCESS_CONN="success";
    private static final String URL_SURVEY_SYNC="";
    private static final String URL_QUESTION_SYNC="";
    private static final String URL_RESPONE_SYNC="";

    private Context applicationContext;
    private RequestHandler requestHandler;

    public SurveySync(Context applicationContext){
        this.applicationContext=applicationContext;
        requestHandler=RequestHandler.getInstance(this.applicationContext);
    }

    public void syncSurvey(final ConnectionCallback connectionCallback){

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_SURVEY_SYNC, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                SurveyResult surveyResult=gson.fromJson(response,SurveyResult.class);
                if(surveyResult.getSuccess().equals(SUCCESS_CONN)){
                    ArrayList<Survey> updatedSurvey=surveyResult.addOrUpdateInDb(applicationContext);
                    if(!updatedSurvey.isEmpty()){
                        syncQuestion(updatedSurvey,connectionCallback);
                    }
                    else {
                        connectionCallback.onSuccess();
                    }
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //TODO: HANDLE ERROR
                        connectionCallback.onFailure();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                SurveyRequest surveyRequest=new SurveyRequest(applicationContext);
                map.put(SurveyRequest.KEY_SURVEY_REQUEST,new Gson().toJson(surveyRequest,SurveyRequest.class));
                map.put(SharedPrefManager.KEY_USERNAME,SharedPrefManager.getInstance(applicationContext).getUsername());
                return map;
            }
        };
        requestHandler.addToRequestQueue(stringRequest);
    }



    public void syncResponse(final ConnectionCallback connectionCallback){

        ResponseRequest responseRequest=new ResponseRequest();
        responseRequest.setData(applicationContext);
        if(responseRequest.getTotalResponses()<=0)return;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_RESPONE_SYNC, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ResponseResult responseResult=new Gson().fromJson(response,ResponseResult.class);
                if(responseResult.getSuccess().equals(SUCCESS_CONN)){
                    responseResult.setSync(applicationContext);
                    responseResult.removeResponse(applicationContext);
                    connectionCallback.onSuccess();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //TODO: HAndle the error
                        connectionCallback.onFailure();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map= new HashMap<String,String>();
                map.put(SharedPrefManager.KEY_USERNAME,SharedPrefManager.getInstance(applicationContext).getUsername());
                ResponseRequest responseRequest=new ResponseRequest();
                responseRequest.setData(applicationContext);
                map.put(ResponseRequest.KEY_RESPONE_REQUEST,new Gson().toJson(responseRequest,ResponseRequest.class));
                return map;
            }
        };
        requestHandler.addToRequestQueue(stringRequest);
    }

    public void syncQuestion(final ArrayList<Survey> surveys,final ConnectionCallback connectionCallback){

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_QUESTION_SYNC, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    QuestionResult questionResult = new Gson().fromJson(response, QuestionResult.class);
                if(questionResult.getSuccess().equals(SUCCESS_CONN)) {
                    questionResult.addOrUpdateInDb(applicationContext);
                    connectionCallback.onSuccess();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //TODO:Handle Error
                        connectionCallback.onFailure();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put(SharedPrefManager.KEY_USERNAME,SharedPrefManager.getInstance(applicationContext).getUsername());
                QuestionRequest questionRequest=new QuestionRequest();
                questionRequest.setData(surveys);
                map.put(QuestionRequest.KEY_QUESTION_REQUEST,new Gson().toJson(questionRequest,QuestionRequest.class));
                return map;
            }
        };

        requestHandler.addToRequestQueue(stringRequest);
    }


    public interface ConnectionCallback{
        void onSuccess();
        void onFailure();
    }


}
