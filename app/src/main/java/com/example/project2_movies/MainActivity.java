package com.example.project2_movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.example.project2_movies.Movie;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ListItemClickListener,  AdapterView.OnItemSelectedListener {
       RecyclerView recyclerView;
        public static String BASE_URL = "https://api.themoviedb.org";
        public static int PAGE = 1;
        public static String API_KEY = "648a59eca4f3fd6ec718ed097da52f2b";
        public static String LANGUAGE = "en-US";
        public static String CATEGORY = "popular";
        Spinner category;
        Button search;
        String[] categoryArray={"popular","top_rated","upcoming"};
        public static final String TAG=MainActivity.class.getSimpleName();
        private List<Movie.ResultsBean> listOfMovies;
        RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerview_id);
        category=findViewById(R.id.category);
        search=findViewById(R.id.submit);
        listOfMovies=new ArrayList<Movie.ResultsBean>();

        //Staggered layout defining
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        retrofit();
        category.setOnItemSelectedListener(this);

        ArrayAdapter a1=new ArrayAdapter(this,android.R.layout.simple_spinner_item,categoryArray);
        a1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(a1);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listOfMovies.clear();
                recyclerViewAdapter.notifyDataSetChanged();
                retrofit();

            }
        });



    }
    void retrofit(){
        //infusing retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface myinterface = retrofit.create(apiInterface.class);
        //myTextview =(TextView) findViewById(R.id.my_tv);


        Call<Movie> call = myinterface.listOfMovies(CATEGORY,API_KEY,LANGUAGE,PAGE);

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie results = response.body();
                Log.e(TAG, response.body().toString());
                Toast.makeText(MainActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                listOfMovies = results.getResults();
                recyclerViewAdapter=new RecyclerViewAdapter(MainActivity.this,listOfMovies,MainActivity.this::onListItemClick);
                recyclerView.setAdapter(recyclerViewAdapter);

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                t.printStackTrace();

                Log.e(TAG, "Error" + t.getCause());
            }
        });



    }


    @Override
    public void onListItemClick(int clickedItemIndex) {
        //TODO PASS AN INTENT
        //TODO IN PUT EXTRA SEND OBJECT OF THE MOVIE WHICH USER CLICKED USING THE CLICKEDITEMINDEX
        //TODO IN DETAIL ACTIVITY USE GET EXTRA AND FETCH THE DATA ON THE DETAIL ACTIVITY LAYOt
        //passing activity in intent
         Intent intent = new Intent(MainActivity.this,Detail_activity.class);
         intent.putExtra("MovieObject",listOfMovies.get(clickedItemIndex));
         startActivity(intent);

        }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        CATEGORY=categoryArray[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}