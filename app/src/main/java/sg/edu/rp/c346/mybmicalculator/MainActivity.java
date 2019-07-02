package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight;
    EditText etHeight;
    Button btCalc;
    Button btReset;
    TextView tvDate;
    TextView tvBmi;
    TextView tvJudge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.etWeight);
        etHeight = findViewById(R.id.etHeight);
        btCalc = findViewById(R.id.btCalc);
        btReset = findViewById(R.id.btReset);
        tvDate = findViewById(R.id.tvDate);
        tvBmi = findViewById(R.id.tvBMI);
        tvJudge = findViewById(R.id.tvJudge);

        btCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float weight = Float.parseFloat(etWeight.getText().toString());
                float height = Float.parseFloat(etHeight.getText().toString());
                float bmi = weight / (height * height);

                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                String datetime = getString(R.string.date) + " " + now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);

                String strBmi = getString(R.string.bmi) + " "+  bmi;
                if (bmi < 18.5){
                    tvJudge.setText("You are underweight");
                }
                else if (bmi < 25){
                    tvJudge.setText("Your BMI is normal");
                }
                else if (bmi < 30){
                    tvJudge.setText("You are overweight");
                }
                else{
                    tvJudge.setText("dude stop eating so much");
                }

                tvDate.setText(datetime);
                tvBmi.setText(strBmi);
                etHeight.setText("");
                etWeight.setText("");

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor editPrefs = prefs.edit();
                editPrefs.putString("date",datetime);
                editPrefs.putString("bmi",strBmi);
                editPrefs.putString("judge",tvJudge.getText().toString());
                editPrefs.commit();
            }
        });
        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etHeight.setText("");
                etWeight.setText("");
                tvDate.setText(getString(R.string.date));
                tvBmi.setText(getString(R.string.bmi));
                tvJudge.setText("");

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor editPrefs = prefs.edit();
                editPrefs.putString("date",getString(R.string.date));
                editPrefs.putString("bmi",getString(R.string.bmi));
                editPrefs.putString("judge","");
                editPrefs.commit();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        etHeight.setText(prefs.getString("weight",""));
        etWeight.setText(prefs.getString("height",""));

        tvDate.setText(prefs.getString("date", getString(R.string.date)));
        tvBmi.setText(prefs.getString("bmi", getString(R.string.bmi)));
        tvJudge.setText(prefs.getString("judge",""));
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editPrefs = prefs.edit();

        editPrefs.putString("height",etHeight.getText().toString());
        editPrefs.putString("weight",etWeight.getText().toString());
        editPrefs.commit();
    }
}
