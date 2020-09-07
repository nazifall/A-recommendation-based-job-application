package com.example.andy.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;

import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;



public class CategoryListWiseActivity extends AppCompatActivity {


    // BroadcastReceiver broadcastReceiver;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    ProgressDialog pDialog;
    String response;
    JSONArray List = null;
    ArrayList<HashMap<String,String>> itemList=new ArrayList<HashMap<String,String>>();
    String category_id="";

    ListView Lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   requestWindowFeature(Window.FEATURE_NO_TITLE);
      //  getSupportActionBar().hide();
        setContentView(R.layout.navigation_drawer);

        Bundle extras = getIntent().getExtras();
        category_id = extras.getString("category_id");


        toolbar = (Toolbar) findViewById(R.id.toolbarId);
        //setSupportActionBar(toolbar);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayoutId);
        navigationView = (NavigationView) findViewById(R.id.navigationViewId);

        Lv = (ListView)findViewById(R.id.KeySearchList);


        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationViewId);
        View hView =  navigationView.getHeaderView(0);


        new AsyncKeySearch().execute();

        Lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                HashMap<String, String> mapContent = new HashMap<String, String>();
                mapContent = itemList.get(i);

                Intent idn = new Intent(CategoryListWiseActivity.this,JobDetailsActivity.class);
                idn.putExtra("id",mapContent.get("id"));
                idn.putExtra("job_name",mapContent.get("job_name"));
                idn.putExtra("company_name",mapContent.get("company_name"));
                idn.putExtra("vacancy_no",mapContent.get("vacancy_no"));
                idn.putExtra("job_context",mapContent.get("job_context"));
                idn.putExtra("job_responsibilities",mapContent.get("job_responsibilities"));
                idn.putExtra("employment_status",mapContent.get("employment_status"));
                idn.putExtra("educational_requirement",mapContent.get("educational_requirement"));
                idn.putExtra("experience_requirements",mapContent.get("experience_requirements"));
                idn.putExtra("additonal_requirement",mapContent.get("additonal_requirement"));
                idn.putExtra("job_location",mapContent.get("job_location"));
                idn.putExtra("salary",mapContent.get("salary"));
                idn.putExtra("compensation_other_benefits",mapContent.get("compensation_other_benefits"));
                idn.putExtra("company_info",mapContent.get("company_info"));
                idn.putExtra("apply",mapContent.get("apply"));
                startActivity(idn);

            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                Intent idn;
                switch (id){

                    case R.id.it :
                        idn = new Intent(CategoryListWiseActivity.this, CategoryListWiseActivity.class);
                        idn.putExtra("category_id","2");
                        startActivity(idn);
                        break;

                    case R.id.garments :
                        idn = new Intent(CategoryListWiseActivity.this, CategoryListWiseActivity.class);
                        idn.putExtra("category_id","3");
                        startActivity(idn);

                        break;

                    case R.id.hr :
                        idn = new Intent(CategoryListWiseActivity.this, CategoryListWiseActivity.class);
                        idn.putExtra("category_id","4");
                        startActivity(idn);
                        break;

                    case R.id.ngo :
                        idn = new Intent(CategoryListWiseActivity.this, CategoryListWiseActivity.class);
                        idn.putExtra("category_id","5");
                        startActivity(idn);
                        break;
                    case R.id.tourism :
                        idn = new Intent(CategoryListWiseActivity.this, CategoryListWiseActivity.class);
                        idn.putExtra("category_id","6");
                        startActivity(idn);
                        break;

                    case R.id.bank :
                        idn = new Intent(CategoryListWiseActivity.this, CategoryListWiseActivity.class);
                        idn.putExtra("category_id","7");
                        startActivity(idn);
                        break;



                }

                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });//SetNavigationSelectedListener End


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();

    }//OnCreate End

    @Override
    public void onBackPressed() {
        if ( drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }



    class AsyncKeySearch extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(CategoryListWiseActivity.this);
            pDialog.setMessage("Key Search. Please wait.....");
            pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {

            try {
                String url= URL.get_job_list_category;
                Log.e("url:", "url :"+url);
                String MyJson = key_search_Json(category_id);
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
                            List = jsonObj.getJSONArray("job_details_list");
                            itemList.clear();

                            for (int i = 0; i <List.length(); i++)
                            {
                                JSONObject obj = List.getJSONObject(i);
//                                LoginStatus=c.getString("status");

                                // JSONObject obj = c.getJSONObject("Brand");
                                String id = obj.getString("id");
                                String job_name = obj.getString("job_name");
                                String company_name = obj.getString("company_name");
                                String vacancy_no = obj.getString("vacancy_no");
                                String job_context = obj.getString("job_context");
                                String job_responsibilities = obj.getString("job_responsibilities");
                                String employment_status = obj.getString("employment_status");
                                String educational_requirement = obj.getString("educational_requirement");
                                String experience_requirements = obj.getString("experience_requirements");
                                String additonal_requirement = obj.getString("additonal_requirement");
                                String job_location = obj.getString("job_location");
                                String salary = obj.getString("salary");
                                String compensation_other_benefits = obj.getString("compensation_other_benefits");
                                String company_info = obj.getString("company_info");
                                String apply = obj.getString("apply");

                                Log.e("job_name:", "job_name RESPONSE:"+job_name);
                                Log.e("company_name:", "company_name RESPONSE:"+company_name);
                                Log.e("vacancy_no:", "vacancy_no RESPONSE:"+vacancy_no);
                                Log.e("job_context:", "job_context RESPONSE:"+job_context);
                                Log.e("job_responsibilities:", "job_responsibilities RESPONSE:"+job_responsibilities);
                                Log.e("employment_status:", "employment_status RESPONSE:"+employment_status);
                                Log.e("educatio_requirement:", "educational_requirement RESPONSE:"+educational_requirement);
                                Log.e("experie_requirements:", "experience_requirements RESPONSE:"+experience_requirements);
                                Log.e("additonal_requirement:", "additonal_requirement RESPONSE:"+additonal_requirement);
                                Log.e("job_location:", "job_location RESPONSE:"+job_location);
                                Log.e("salary:", "salary RESPONSE:"+salary);
                                Log.e("compensa_benefits:", "compensation_other_benefits RESPONSE:"+compensation_other_benefits);
                                Log.e("company_info:", "company_info RESPONSE:"+company_info);

                                HashMap<String,String> map=new HashMap<String,String>();
                                map.put("job_name", job_name);
                                map.put("id", id);
                                map.put("company_name", company_name);
                                map.put("vacancy_no", vacancy_no);
                                map.put("job_context", job_context);
                                map.put("job_responsibilities", job_responsibilities);
                                map.put("employment_status", employment_status);
                                map.put("educational_requirement", educational_requirement);
                                map.put("experience_requirements", experience_requirements);
                                map.put("additonal_requirement", additonal_requirement);
                                map.put("job_location", job_location);
                                map.put("salary", salary);
                                map.put("compensation_other_benefits", compensation_other_benefits);
                                map.put("company_info", company_info);
                                map.put("apply", apply);
                                itemList.add(map);
                            }

                            AdapterForKeySearch adpater=new AdapterForKeySearch(getApplicationContext(), itemList);
                            Lv.setAdapter(adpater);
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


    public String key_search_Json(String category_id)
    {

        String json = "";

        // 3. build jsonObject
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("category_id", category_id);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        json = jsonObject.toString();

        return json;
    }


}
