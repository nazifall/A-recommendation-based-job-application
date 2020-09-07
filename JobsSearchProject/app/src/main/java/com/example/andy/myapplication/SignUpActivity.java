package com.example.andy.myapplication;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ViewPager viewPager;
    //  private Toolbar toolbar;
    private TabLayout tabLayout;

    ProgressDialog pDialog;
    String response;
    JSONArray List = null;

    Button SignupBtn;
    EditText NameEd, PasswordEd,ConfirmPasswordEd,EmailEd;
    Spinner CategorySp;
    private String CategoryId = "";

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        SignupBtn = (Button)findViewById(R.id.submitBtn);
        NameEd= (EditText) findViewById(R.id.name);
        PasswordEd= (EditText) findViewById(R.id.password);
        ConfirmPasswordEd= (EditText) findViewById(R.id.confirm_password);
        EmailEd= (EditText) findViewById(R.id.email);
        CategorySp= (Spinner) findViewById(R.id.job_category);



        // Spinner click listener
        CategorySp.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        java.util.List<String> categories = new ArrayList<String>();
        categories.add("select category");
        categories.add("IT");
        categories.add("Garments");
        categories.add("HR");
        categories.add("NGO");
        categories.add("Tourism");
        categories.add("Bank");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(SignUpActivity.this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        CategorySp.setAdapter(dataAdapter);

        /*CategorySp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String item = parent.getItemAtPosition(position).toString();

                // Showing selected spinner item
                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

            }
        });*/


        SignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(NameEd.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(SignUpActivity.this,"Enter Name", Toast.LENGTH_LONG).show();
                }
                else if(PasswordEd.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(SignUpActivity.this,"Enter Password", Toast.LENGTH_LONG).show();
                }
                else if(ConfirmPasswordEd.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(SignUpActivity.this,"Enter Confirm Password", Toast.LENGTH_LONG).show();
                }
                else if(!ConfirmPasswordEd.getText().toString().equals(PasswordEd.getText().toString()))
                {
                    Toast.makeText(SignUpActivity.this,"Password don't match", Toast.LENGTH_LONG).show();
                }
                else if(EmailEd.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(SignUpActivity.this,"Enter Email", Toast.LENGTH_LONG).show();
                }
                else
                {
                    new AsyncSignup().execute();
                }

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String item = parent.getItemAtPosition(position).toString();

        CategoryId = String.valueOf( position+1);

        // S
        //
        //
        //
        //
        //
        // wing selected spinner item
       // Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    class AsyncSignup extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(SignUpActivity.this);
            pDialog.setMessage("Login. Please wait.....");
            pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {

            try {
                String url= URL.customer_info;
                Log.e("url:", "url :"+url);
                String name = NameEd.getText().toString();
                String password = ConfirmPasswordEd.getText().toString();
                String email = EmailEd.getText().toString();
                String MyJson = login_Json(name,password,email,CategoryId);
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
                            List = jsonObj.getJSONArray("customer_info");


                            for (int i = 0; i <List.length(); i++)
                            {
                                JSONObject obj = List.getJSONObject(i);
//                                LoginStatus=c.getString("status");

                                // JSONObject obj = c.getJSONObject("Brand");

                                String status = obj.getString("status");
                                String message = obj.getString("message");



                                Log.e("status:", "vacancy_no RESPONSE:"+status);
                                Log.e("message:", "job_context RESPONSE:"+message);

                                if(status.equalsIgnoreCase("success"))
                                {
                                    Toast.makeText(SignUpActivity.this,message, Toast.LENGTH_LONG).show();
                                    Intent idn = new Intent(SignUpActivity.this,LoginActivity.class);
                                    startActivity(idn);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(SignUpActivity.this,message, Toast.LENGTH_LONG).show();
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


    public String login_Json(String name, String password,String email,String Category)
    {

        String json = "";

        // 3. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("name", name);
            jsonObject.accumulate("password", password);
            jsonObject.accumulate("email", email);
            jsonObject.accumulate("category_id", CategoryId);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        json = jsonObject.toString();

        return json;
    }
}


