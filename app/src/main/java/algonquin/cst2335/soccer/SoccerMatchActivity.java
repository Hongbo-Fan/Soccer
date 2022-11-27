package algonquin.cst2335.soccer;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.soccer.databinding.ActivitySoccerMatchBinding;
import algonquin.cst2335.soccer.databinding.SoccerListLayoutBinding;


public class SoccerMatchActivity extends AppCompatActivity {

    ActivitySoccerMatchBinding binding;
    SoccerMatchViewModel model;
    ArrayList<SoccerMatchMessage> matches;
    SoccerMatchMessageDAO smDAO;
    RequestQueue queue = null;
    private RecyclerView.Adapter<MyRowHolder> myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //create a Volley object that will connect to a server
        queue = Volley.newRequestQueue(this);

        //load from the database
        SoccerDatabase db = Room.databaseBuilder(getApplicationContext(), SoccerDatabase.class, "SoccerDatabase").build();
        smDAO = db.smDAO();

        //initiate view model
        model = new ViewModelProvider(this).get(SoccerMatchViewModel.class);
        matches = model.matches.getValue();

        if(matches == null)
        {
            model.matches.postValue( matches = new ArrayList<>());


            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute( ()->{
                //this runs in another thread, load everything from database;
                matches.addAll( smDAO.getAllMatches() );

                //load the RecyclerView
                runOnUiThread( () ->  binding.recyclerView.setAdapter( myAdapter ));
            });
        }

        binding = ActivitySoccerMatchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //saying it's a vertical list
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String url = "https://www.scorebat.com/video-api/v1/";

        JsonArrayRequest request = new JsonArrayRequest(url,
                (response)-> {
                    try {
                        for(int i=0; i<response.length(); i++){
                            JSONObject soccer = response.getJSONObject(i);
                            String title = soccer.getString("title");
                            String date = soccer.getString("date");
                            String thumbnailUrl = soccer.getString("thumbnail");
                            JSONObject side1 = soccer.getJSONObject("side1");
                            String team1 = side1.getString("name");
                            JSONObject side2 = soccer.getJSONObject("side2");
                            String team2 = side2.getString("name");
                            JSONObject compet = soccer.getJSONObject("competition");
                            String competition = compet.getString("name");
                            JSONArray videoArray = soccer.getJSONArray("videos");
                            JSONObject video1 = videoArray.getJSONObject(0);
                            String embed = video1.getString("embed");
                            int startIndex = embed.indexOf("src=")+5;
                            int endIndex = embed.indexOf("frameborder")-2;
                            String videoUrl = embed.substring(startIndex, endIndex);

                            SoccerMatchMessage match = new SoccerMatchMessage(title, team1, team2, videoUrl, date, competition, thumbnailUrl );
                            matches.add(match);

                            //to know which position has changed
                            myAdapter.notifyItemInserted(matches.size()-1);

                            Executor thread = Executors.newSingleThreadExecutor();
                            thread.execute( ()-> {

                                smDAO.insertMatch(match);
                            });
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                },
                (error)->{});
        queue.add(request);


        binding.recyclerView.setAdapter(myAdapter = new  RecyclerView.Adapter<MyRowHolder>() {

        @NonNull
        @Override
        public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            SoccerListLayoutBinding binding = SoccerListLayoutBinding.inflate(getLayoutInflater());
            return new MyRowHolder( binding.getRoot() );
        }

        @Override
        public int getItemViewType (int position){
            return 0;
        }

        @Override
        public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
            holder.titleText.setText(matches.get(position).getTitle());
            holder.competText.setText(matches.get(position).getCompetition());
            holder.dateText.setText(matches.get(position).getDate());
        }

        @Override
        public int getItemCount() {

            return matches.size();
        }

    });

}

class MyRowHolder extends RecyclerView.ViewHolder {
    TextView titleText;
    TextView competText;
    TextView dateText;

    public MyRowHolder(@NonNull View itemView) {
        super(itemView);

        titleText = itemView.findViewById(R.id.soccerTitle);
        competText = itemView.findViewById(R.id.soccerCompetition);
        dateText = itemView.findViewById(R.id.soccerDate);
    }
}

}