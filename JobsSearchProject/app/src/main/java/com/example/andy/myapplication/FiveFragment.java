package com.example.andy.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class FiveFragment extends Fragment {

    ProgressDialog pDialog;
    String response;
    JSONArray List = null;
    ArrayList<HashMap<String,String>> itemList=new ArrayList<HashMap<String,String>>();
    String category_id="";

    ListView Lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_three, container, false);
        Lv=v.findViewById(R.id.KeySearchList);
        // Lv.setText("Three Fragment");
        new AsyncKeySearch5().execute();

        Lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                HashMap<String, String> mapContent = new HashMap<String, String>();
                mapContent = itemList.get(i);

                Intent idn = new Intent(getActivity(),JobDetailsActivity.class);
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
        return v;
    }

    class AsyncKeySearch5 extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(getContext());
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
                String MyJson = key_search_Json("5");
                Log.e("MyJson:", "PartsName MyJson:"+MyJson);
                response = JsonParse.makeServiceCall(url,MyJson);
                Log.e("RESPONSE:", "PartsName RESPONSE:"+response);

            } catch (Exception e) {
            }

            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {

            getActivity().runOnUiThread(new Runnable() {
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

                            AdapterForKeySearch adpater=new AdapterForKeySearch(getActivity(), itemList);
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

