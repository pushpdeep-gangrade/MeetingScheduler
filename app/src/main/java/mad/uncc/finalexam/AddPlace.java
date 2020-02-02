package mad.uncc.finalexam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.IOUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AddPlace extends AppCompatActivity {
    EditText placename;
    RecyclerView recyclerView ;
    public static RecyclerView.Adapter mAdapter;
    public RecyclerView.LayoutManager layoutManager;
    ArrayList<String> autoPlaces = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        recyclerView = findViewById(R.id.AutoCompleteRC);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(AddPlace.this);
        placename = findViewById(R.id.editTextPlace);
        findViewById(R.id.GoBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=" + placename.getText().toString()+ "&key=AIzaSyD9MdKROlLwXjhPSHiMj5l6EBhoEd6fAVk";
                new getAutoComplete().execute(url);
            }
        });


    }


    public class getAutoComplete extends AsyncTask<String, Void, ArrayList<String>> {
        @Override
        protected void onPostExecute(ArrayList<String> autoCities) {

            recyclerView.setLayoutManager(layoutManager);
            mAdapter = new PlaceAdapter(autoPlaces,AddPlace.this);
          //  mAdapter = new PlaceAdapter(autoPlaces);
            recyclerView.setAdapter(mAdapter);
            recyclerView.setAdapter(mAdapter);
            super.onPostExecute(autoCities);
        }

        @SuppressLint("WrongThread")
        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            HttpURLConnection con = null;
            try {
                URL url = new URL(strings[0]);
                con = (HttpURLConnection) url.openConnection();
                con.connect();
                if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {

                    String json = IOUtil.toString(con.getInputStream(), "UTF-8");
                    JSONObject root = new JSONObject(json);
                    JSONArray cities = root.getJSONArray("predictions");
                    for (int i = 0; i < cities.length(); i++) {
                        JSONObject city = cities.getJSONObject(i);
                        String cityname = city.getString("description");
                     //   String placeId = city.getString("place_id");
                        autoPlaces.add(cityname);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return autoPlaces;
        }
    }
}
