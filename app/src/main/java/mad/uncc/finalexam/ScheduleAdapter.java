package mad.uncc.finalexam;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {
    ArrayList<Schedule> schedules = new ArrayList<>();
    Context context;

    public ScheduleAdapter(ArrayList<Schedule> schedules,Context context) {
        this.schedules = schedules;
    this.context = context;

    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedule_item,parent,false);
        ScheduleViewHolder userViewHolder = new ScheduleViewHolder(view);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        final Schedule s = schedules.get(position);
        holder.schName.setText(s.getTitle());
        holder.schLocation.setText(s.getPlace());
        holder.schDateTime.setText(s.getDate() + " " +s.getTime());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showDeleteMessageDialog(s);
                return false;
            }
        });

    }
    private void showDeleteMessageDialog(final Schedule item){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Do you want to delete the selected message ?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                            String id = item.getTitle();
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection("Schedules").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(context," deleted",Toast.LENGTH_LONG).show();
                                    MainActivity.mAdapter.notifyDataSetChanged();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context,"Error while deleting message",Toast.LENGTH_LONG).show();
                                }
                            });
                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public static class ScheduleViewHolder  extends RecyclerView.ViewHolder {
        public TextView schName;
        public TextView schLocation;
        public TextView schDateTime;



        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            schName = itemView.findViewById(R.id.main_sch_name);
            schLocation = itemView.findViewById(R.id.main_location);
            schDateTime = itemView.findViewById(R.id.main_date_time);

        }}
}
