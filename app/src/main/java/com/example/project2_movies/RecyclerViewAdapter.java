package com.example.project2_movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Callback;

public class RecyclerViewAdapter  extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Movie.ResultsBean> mData;

    final private ListItemClickListener mOnClickListener;

    public RecyclerViewAdapter(Context mContext, List<Movie.ResultsBean> mData, ListItemClickListener mOnClickListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.mOnClickListener = (ListItemClickListener) mOnClickListener;
    }

    public interface ListItemClickListener{
        void onListItemClick (int clickedItemIndex);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.grid_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Movie.ResultsBean obj=mData.get(position);
       holder.tv_movie_title.setText(obj.getTitle());
        String url="http://image.tmdb.org/t/p/w500/"+obj.getPoster_path();

        Glide.with(mContext).load(url).into(holder.movie_thumbnail);



    }

    @Override
    public int getItemCount() {

        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_movie_title;
        ImageView movie_thumbnail;

       public MyViewHolder(View itemView) {
           super(itemView);

           tv_movie_title = (TextView) itemView.findViewById(R.id.Movie_title_id);
           movie_thumbnail = (ImageView) itemView.findViewById(R.id.movie_img);
           itemView.setOnClickListener(this);

       }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }




}
