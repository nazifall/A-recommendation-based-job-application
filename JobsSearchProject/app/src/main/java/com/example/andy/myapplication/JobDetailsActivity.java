package com.example.andy.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.TextView;



public class JobDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
  //      requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getSupportActionBar().hide();
        setContentView(R.layout.job_details);

        Bundle extras = getIntent().getExtras();
        String id = extras.getString("id");
        String job_name = extras.getString("job_name");
        String company_name = extras.getString("company_name");
        String vacancy_no = extras.getString("vacancy_no");
        String job_context = extras.getString("job_context");
        String job_responsibilities = extras.getString("job_responsibilities");
        String employment_status = extras.getString("employment_status");
        String educational_requirement = extras.getString("educational_requirement");
        String experience_requirements = extras.getString("experience_requirements");
        String additonal_requirement = extras.getString("additonal_requirement");
        String job_location = extras.getString("job_location");
        String salary = extras.getString("salary");
        String compensation_other_benefits = extras.getString("compensation_other_benefits");
        String company_info = extras.getString("company_info");
        String apply = extras.getString("apply");

        TextView jobName = (TextView)findViewById(R.id.job_name);
        TextView companyName = (TextView)findViewById(R.id.company_name);
        TextView vacancyNo = (TextView)findViewById(R.id.vacancy_no);
        TextView jobContext = (TextView)findViewById(R.id.job_context);
        TextView jobResponsibilities = (TextView)findViewById(R.id.job_responsibilities);
        TextView employmentStatus = (TextView)findViewById(R.id.employment_status);
        TextView educationalRequirement = (TextView)findViewById(R.id.educational_requirement);
        TextView experienceRequirements = (TextView)findViewById(R.id.experience_requirements);
        TextView additonalRequirement = (TextView)findViewById(R.id.additonal_requirement);
        TextView jobLocation = (TextView)findViewById(R.id.location);
        TextView Salary = (TextView)findViewById(R.id.salary);
        TextView compensationOther_benefits = (TextView)findViewById(R.id.compensation_other_benefits);
        TextView companyInfo = (TextView)findViewById(R.id.company_info);
        TextView Apply = (TextView)findViewById(R.id.apply);

        jobName.setText(job_name);
        companyName.setText(company_name);
        vacancyNo.setText(vacancy_no);
        jobContext.setText(job_context);
        jobResponsibilities.setText(job_responsibilities);
        employmentStatus.setText(employment_status);
        educationalRequirement.setText(educational_requirement);
        experienceRequirements.setText(experience_requirements);
        additonalRequirement.setText(additonal_requirement);
        jobLocation.setText(job_location);
        Salary.setText(salary);
        compensationOther_benefits.setText(compensation_other_benefits);
        companyInfo.setText(company_info);
        Apply.setText(apply);
    }
}
