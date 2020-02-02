package mad.uncc.finalexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView ;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static RecyclerView.Adapter mAdapter;
    public RecyclerView.LayoutManager layoutManager;
    ArrayList<Schedule> schedules =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rc_schedules);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(MainActivity.this);


        findViewById(R.id.AddSchedule).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,AddSchedule.class);
                startActivity(i);
            }
        });

        db.collection("Schedules")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Schedule sch1 = document.toObject(Schedule.class);
                                schedules.add(sch1);
                            }
                                recyclerView.setLayoutManager(layoutManager);
                                mAdapter = new ScheduleAdapter(schedules,MainActivity.this);
                                recyclerView.setAdapter(mAdapter);
                                mAdapter.notifyDataSetChanged();

                        } else {
                            Log.d("demo", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    }

