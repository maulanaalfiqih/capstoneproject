package com.example.githubtes;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.example.githubtes.dataBookmark.BookmarkDatabase;
import com.example.githubtes.dataBookmark.BookmarkList;

import java.util.ArrayList;
import java.util.List;

/***
 * The adapter class for the RecyclerView, contains the sports data.
 */
class SportsAdapter extends RecyclerView.Adapter<SportsAdapter.ViewHolder>  {

    // Member variables.
    private ArrayList<Sport> mSportsData;
    private Context mContext;
    private OnItemClickCallback onItemClickCallback;
    private List<BookmarkList> bookmarkList;
    private BookmarkDatabase db;

    SportsAdapter(Context context, ArrayList<Sport> sportsData) {
        this.mSportsData = sportsData;
        this.mContext = context;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setSportsData(ArrayList<Sport> items) {
        mSportsData.clear();
        mSportsData.addAll(items);
        notifyDataSetChanged();
    }


    /**
     * Required method for creating the viewholder objects.
     *
     * @param parent The ViewGroup into which the new View will be added
     *               after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return The newly created ViewHolder.
     */
    @Override
    public SportsAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.list_item, parent, false));
    }

    /**
     * Required method that binds the data to the viewholder.
     *
     * @param holder The viewholder into which the data should be put.
     * @param position The adapter position.
     */
    @Override
    public void onBindViewHolder(SportsAdapter.ViewHolder holder,
                                 int position) {
        BookmarkList bookmarkList = new BookmarkList();
        // Get current sport.
        Sport currentSport = mSportsData.get(position);
        // Populate the textviews with data.
        holder.bindTo(currentSport);

        String title = mSportsData.get(position).getTitle();
        String author = mSportsData.get(position).getAuthor();
        String description = mSportsData.get(position).getDescription();
        String content = mSportsData.get(position).getContent();
        String publish = mSportsData.get(position).getPublishedAt();
        String image = mSportsData.get(position).getPhoto();

        db = Room.databaseBuilder(mContext,BookmarkDatabase.class, "bookmarkDB2").build();
        if (MainActivity.db.bookmarkDao().isBookmark(title) == 1){
            holder.bookbtn.setImageResource(R.drawable.ic_favorite);
        }else {
            holder.bookbtn.setImageResource(R.drawable.ic_baseline_favorite_border_24);
            holder.bookbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int status_fav = 0;
                    bookmarkList.setTitle(title);
                    bookmarkList.setAuthor(author);
                    bookmarkList.setDescription(description);
                    bookmarkList.setContent(content);
                    bookmarkList.setPublish(publish);
                    bookmarkList.setPhoto(image);
                    bookmarkList.setStatus_fav(status_fav);
                    insertDataBookmark(bookmarkList);

                    if (MainActivity.db.bookmarkDao().isBookmark(title) != 1) {
                        holder.bookbtn.setImageResource(R.drawable.ic_favorite);
                    } else {
                        holder.bookbtn.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                    }
                }
            });
        }
    }

    private void insertDataBookmark(final BookmarkList bookmarkList){

        new AsyncTask<Void, Void, Long>(){
            @Override
            protected Long doInBackground(Void... voids) {
                long status = db.bookmarkDao().addData(bookmarkList);
                return status;
            }

            @Override
            protected void onPostExecute(Long status) {
                Toast.makeText(mContext, "Berhasil ditambahkan", Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }

    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */
    @Override
    public int getItemCount() {
        return mSportsData.size();
    }

    void setFilter(ArrayList<Sport> filterModel) {
        mSportsData = new ArrayList<>();
        mSportsData.addAll(filterModel);
        notifyDataSetChanged();
    }


    /**
     * ViewHolder class that represents each row of data in the RecyclerView.
     */
    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        // Member Variables for the TextViews
        private TextView mTitleText;
        private TextView mAuthorText;
        private TextView mInfoText;
        private ImageView mSportsImage, bookbtn ;

        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView The rootview of the list_item.xml layout file.
         */
        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            mTitleText = itemView.findViewById(R.id.newsTitle);
            mAuthorText = itemView.findViewById(R.id.title);
            mInfoText = itemView.findViewById(R.id.subTitle);
            mSportsImage = itemView.findViewById(R.id.sportsImage);
            bookbtn = itemView.findViewById(R.id.bookbtn);

            // Set the OnClickListener to the entire view.
            itemView.setOnClickListener(this);
        }

        void bindTo(Sport currentSport){
            // Populate the textviews with data.
            mTitleText.setText(currentSport.getTitle());
            mAuthorText.setText("Dibuat Oleh :  "+currentSport.getAuthor());
            mInfoText.setText(currentSport.getDescription());

            // Load the images into the ImageView using the Glide library.
            Glide.with(mContext).load(
                    currentSport.getPhoto()).into(mSportsImage);
        }

        /**
         * Handle click to show DetailActivity.
         *
         * @param view View that is clicked.
         */
        @Override
        public void onClick(View view) {
            Sport currentSport = mSportsData.get(getAdapterPosition());
            Intent detailIntent = new Intent(mContext, DetailActivity.class);
            detailIntent.putExtra("title", currentSport.getTitle());
            detailIntent.putExtra("content", currentSport.getContent());
            detailIntent.putExtra("publishedAt","Diterbitkan Pada :  " +  currentSport.getPublishedAt());
            detailIntent.putExtra("urlToImage", currentSport.getPhoto());
            mContext.startActivity(detailIntent);
        }

    }
    public interface OnItemClickCallback {
        void onItemClicked(Sport data);
    }
}

