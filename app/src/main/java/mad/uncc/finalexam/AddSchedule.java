package mad.uncc.finalexam;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class AddSchedule extends AppCompatActivity {

   // public static String place = null;
    EditText schName;
   public static String placeName = "";
    public static String date = null;
    public static String time ="" ;
    Button addPlace,addDate,addTime;
    TextView placenameTV,dateTV,timeTV;
    DatePickerDialog dpd;
    TimePickerDialog tpd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        placenameTV = findViewById(R.id.setPlacenameTV);
        dateTV = findViewById(R.id.setDateTV);
        timeTV = findViewById(R.id.setTimeTV);
        addPlace = findViewById(R.id.addPlaceBtn);
        addDate =findViewById(R.id.setDateBtn);
        addTime = findViewById(R.id.setTimeBtn);
        schName = findViewById(R.id.scheduleName);
        final Intent intent = getIntent();
       if(intent!=null) {
           placeName = intent.getStringExtra("PLACE");
       }
        if(placeName ==null)
        {
            Log.d("demo","null");
        }
        else{
            if(!placeName.equals("")){
                placenameTV.setText(placeName);
                addPlace.setVisibility(View.INVISIBLE);}
        }

        findViewById(R.id.addPlaceBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddSchedule.this,AddPlace.class);
                startActivity(i);
            }
        });

        findViewById(R.id.setDateBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                dpd  = new DatePickerDialog(AddSchedule.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        addDate.setVisibility(View.INVISIBLE);
                      //  addTime.setVisibility(View.INVISIBLE);
                        dateTV.setText(month +"/" +day+"/" +year);
                    }
                },year,month,day);
                dpd.show();

//                DialogFragment newFragment = new DatePickerFragment();
//                newFragment.show(getSupportFragmentManager(), "datePicker");
//                dateTV.setText(date);
//                timeTV.setText(time);
            }
        });

        findViewById(R.id.setTimeBtn).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR);
                int min = c.get(Calendar.MINUTE);
             //  int am_pm = c.get(Calendar.AM_PM);


                tpd = new TimePickerDialog(AddSchedule.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int min) {
                        timeTV.setText(hour +":"+min);
                        addTime.setVisibility(View.INVISIBLE);
                    }
                },hour,min,true);
                tpd.show();


//                DialogFragment newFragment = new TimePickerFragment();
//                newFragment.show(getSupportFragmentManager(), "timePicker");
////
////                timeTV.setText(time);
            }
        });

        findViewById(R.id.saveSchBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateTV.setText(date);
                timeTV.setText(time);
                if(schName.getText() == null && schName.getText().equals("")){
                    Toast.makeText(AddSchedule.this, "Please fill Schedeule name", Toast.LENGTH_SHORT).show();
                }
                else{
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Schedule sch = new Schedule();
                sch.setDate(date.toString());
                String title = schName.getText().toString();
                sch.setTitle(title);
                sch.setPlace(dateTV.getText().toString());
                sch.setTime(timeTV.getText().toString());
                db.collection("Schedules").document(title).set(sch);
                Intent i = new Intent(AddSchedule.this,MainActivity.class);
                startActivity(i);}
            }
        });

    }


//    public void onTimePicked(Calendar time)
//    {
//        timeTV.setText(DateFormat.format("h:mm a", time));
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 101 && resultCode== RESULT_OK ){
////            String
////            Trips c = (Trips) data.getSerializableExtra("Trip");
////            trips.add(c);
////            recyclerView.setLayoutManager(layoutManager);
////            mAdapter = new TripAdapter(trips,MainActivity.this);
////            recyclerView.setAdapter(mAdapter);
//        }
//    }

}

