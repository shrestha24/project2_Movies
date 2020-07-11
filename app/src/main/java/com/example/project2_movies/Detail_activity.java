package com.example.project2_movies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Detail_activity extends AppCompatActivity {
    ImageView thumbnail;
    TextView title,description,rating,release_Date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activity);

        Intent intent = getIntent();
        Movie.ResultsBean object = intent.getParcelableExtra("MovieObject");

      thumbnail =findViewById(R.id.movie_thumbnail);
      title=findViewById(R.id.title_main);
      description=findViewById(R.id.description);
      rating = findViewById(R.id.ratings);
      release_Date=findViewById(R.id.release_date);

        String url="http://image.tmdb.org/t/p/w500/"+object.getPoster_path();
        Glide.with(this).load(url).into(thumbnail);
        Log.e(Detail_activity.class.getSimpleName(),"DETAIL OF OBJECT"+object.toString());
        title.setText(object.getTitle());
        description.setText(object.getOverview());
        rating.setText(Float.toString(object.getVote_average()));
        release_Date.setText(object.getRelease_date());



    }


}