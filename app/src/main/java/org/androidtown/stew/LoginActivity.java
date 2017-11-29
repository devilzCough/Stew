package org.androidtown.stew;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


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
            Toast.makeText(this,"Input!!",Toast.LENGTH_LONG).show();
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
            startActivity(menuIntent);
        }
        else if(loginFlag == 0){

            final AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
            alertDialog.setTitle("LOGIN");
            alertDialog.setMessage("LOGIN FAIL!");
            alertDialog.setIcon(R.drawable.notok);
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
        }
    }
}
