package com.example.andy.myapplication;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;



public class LoginActivity extends AppCompatActivity {

    ProgressDialog pDialog;
    String response;
    JSONArray List = null;
    ArrayList<HashMap<String, String>> itemList=new ArrayList<HashMap<String, String>>();
    private ViewPager viewPager;
    //  private Toolbar toolbar;
    private TabLayout tabLayout;
    Button LoginBtn,SignupBtn;
    EditText NameEd, PasswordEd;

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        LoginBtn = (Button)findViewById(R.id.LoginBtn);
        SignupBtn = (Button)findViewById(R.id.Signup);
        NameEd= (EditText) findViewById(R.id.name);
        PasswordEd= (EditText) findViewById(R.id.password);


        SignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent idn = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(idn);
                finish();

            }
        });


        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(NameEd.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(LoginActivity.this,"Enter Name", Toast.LENGTH_LONG).show();
                }
                else if(PasswordEd.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(LoginActivity.this,"Enter Password", Toast.LENGTH_LONG).show();
                }
                else
                {
                    new AsyncLogin().execute();
                }

            }
        });
    }


    class AsyncLogin extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Login. Please wait.....");
            pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {

            try {
                String url= URL.login;
                Log.e("url:", "url :"+url);
                String name = NameEd.getText().toString();
                String password = PasswordEd.getText().toString();
                String MyJson = login_Json(name,password);
                Log.e("MyJson:", "PartsName MyJson:"+MyJson);
                response = JsonParse.makeServiceCall(url,MyJson);
                Log.e("RESPONSE:", "PartsName RESPONSE:"+response);

            } catch (Exception e) {
            }

            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {


                    try{

                        if (response!= null)
                        {

                            Log.e("RESPONSE:", "PartsName RESPONSE:"+response);
                            JSONObject jsonObj = new JSONObject(response);

                            // Getting JSON Array node
                            List = jsonObj.getJSONArray("user");
                            itemList.clear();

                            for (int i = 0; i <List.length(); i++)
                            {
                                JSONObject obj = List.getJSONObject(i);
//                                LoginStatus=c.getString("status");

                                // JSONObject obj = c.getJSONObject("Brand");
                                String id = obj.getString("id");
                                String name = obj.getString("name");
                                String email = obj.getString("email");
                                String category_id = obj.getString("category_id");
                                String status = obj.getString("status");
                                String message = obj.getString("message");

                                SharedPreferences.Editor editor = getSharedPreferences("MyPrefsFile", MODE_PRIVATE).edit();
                                editor.putString("name", name);
                                editor.putString("id", id);
                                editor.putString("category_id", category_id);
                                editor.apply();


                                Log.e("name:", "job_name RESPONSE:"+name);
                                Log.e("email:", "company_name RESPONSE:"+email);
                                Log.e("status:", "vacancy_no RESPONSE:"+status);
                                Log.e("message:", "job_context RESPONSE:"+message);

                                if(status.equalsIgnoreCase("success"))
                                {
                                    Toast.makeText(LoginActivity.this,message, Toast.LENGTH_LONG).show();
                                    Intent idn = new Intent(LoginActivity.this,MainActivity.class);
                                    startActivity(idn);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(LoginActivity.this,message, Toast.LENGTH_LONG).show();
                                }


                            }

                        }
                        else
                        {
                            Log.e("ServiceHandler", "Couldn't get any data from the url");
                        }


                    }catch(Exception e){/*Toast.makeText(getApplicationContext(), Constants.SERVER_MESSAGE, 1000).show();*/}
                }
            });

            pDialog.dismiss();
        }
    }


    public String login_Json(String name, String password)
    {

        String json = "";

        // 3. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("name", name);
            jsonObject.accumulate("password", password);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        json = jsonObject.toString();

        return json;
    }
}

