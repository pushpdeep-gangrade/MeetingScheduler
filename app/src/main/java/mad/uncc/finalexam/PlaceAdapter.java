package mad.uncc.finalexam;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.TripViewHolder> {
    ArrayList<String> autoPlaces = new ArrayList<>();
    Context context;

    public PlaceAdapter(ArrayList<String> autoPlaces, Context context) {
        this.autoPlaces = autoPlaces;
        this.context = context;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.place_item,parent,false);
        TripViewHolder userViewHolder = new TripViewHolder(view);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        final String place = autoPlaces.get(position);
        holder.tripName.setText(place);

        holder.tripName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              // AddSchedule.place = place;
               Intent i = new Intent(context,AddSchedule.class);
               i.putExtra("PLACE",place);
             //  context.setre
               context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return autoPlaces.size();
    }

    public static class TripViewHolder  extends RecyclerView.ViewHolder {
        public TextView tripName;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
            tripName = itemView.findViewById(R.id.placeName);

        }}
}
