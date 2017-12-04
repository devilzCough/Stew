package org.androidtown.stew;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;


public class LoginActivity extends AppCompatActivity {


    EditText editID,editPW;
    String id,pw;

    BackgroundWebview webView;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editID = (EditText)findViewById(R.id.editId);
        editPW = (EditText)findViewById(R.id.editPw);
        webView = new BackgroundWebview(this);
        AppManager.getInstance().setWebView(webView);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("LOGIN");
        progressDialog.setMessage("Please wait...");
    }

    public void onLoginClicked(View view) {
        id = editID.getText().toString();
        pw = editPW.getText().toString();

        if(id.equals("") || pw.equals("")){
            CustomDialog alert = new CustomDialog(this,0,"학번과 비밀번호를 입력하세요");
        }
        else{
            webView.web_login(id,pw);
            progressDialog.show();

            LoginCheckThread loginCheck = new LoginCheckThread();
            loginCheck.start();
        }
    }
    public class LoginCheckThread extends Thread{
        public void run(){
            while(webView.getLoginflag() == -1){
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            loginProcess();
            interrupt();
        }
    }
    public void loginProcess(){

        int loginFlag=webView.getLoginflag();

        progressDialog.dismiss();

        if(loginFlag == 1){
            Intent menuIntent = new Intent(getApplicationContext(),MenuActivity.class);
            menuIntent.putExtra("user_id",id);
            menuIntent.putExtra("user_pw",pw);
            startActivity(menuIntent);
        }
        else if(loginFlag == 0){
            CustomDialog alert = new CustomDialog(this,0,"LOGIN FAIL");
        }
    }
}
