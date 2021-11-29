package com.example.githubtes;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.example.githubtes.dataBookmark.BookmarkDatabase;
import com.example.githubtes.dataBookmark.BookmarkList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/***
 * The adapter class for the RecyclerView, contains the sports data.
 */
class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder>  {

    // Member variables.
    private Context mContext;
    private OnItemClickCallback onItemClickCallback;
    private List<BookmarkList> bookmarkList;
    private BookmarkDatabase db;
    private ArrayList<Sport> mSportsData;

    BookmarkAdapter(Context context, List<BookmarkList> bookmarkList) {
        this.bookmarkList = bookmarkList;
        this.mContext = context;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setSportsData(List<BookmarkList> items) {
        bookmarkList.clear();
        bookmarkList.addAll(items);
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
    public BookmarkAdapter.ViewHolder onCreateViewHolder(
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
    public void onBindViewHolder(BookmarkAdapter.ViewHolder holder,
                                 int position) {
        BookmarkList bl = bookmarkList.get(position);

        int posisi = position;

        // Get current sport.
        db = Room.databaseBuilder(mContext,
                BookmarkDatabase.class, "bookmarkDB2").allowMainThreadQueries().build();
        // Populate the textviews with data.
        holder.bindTo(bl, posisi);
    }

    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */
    @Override
    public int getItemCount() {
        return bookmarkList.size();
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

        void bindTo(BookmarkList bookmarkList, int posisi){
            // Populate the textviews with data.
            mTitleText.setText(bookmarkList.getTitle());
            mAuthorText.setText("Dibuat Oleh :  "+ bookmarkList.getAuthor());
            mInfoText.setText(bookmarkList.getDescription());

            // Load the images into the ImageView using the Glide library.
            Glide.with(mContext).load(
                    bookmarkList.getPhoto()).into(mSportsImage);

            if (bookmarkList.getStatus_fav() != 1){
                bookbtn.setImageResource(R.drawable.ic_favorite);
            }else{
                bookbtn.setImageResource(R.drawable.ic_baseline_favorite_border_24);
            }

            bookbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onDeleteBookmark(posisi);
                }
            });
        }


        private void onDeleteBookmark(int position){
            db.bookmarkDao().deleteBookmark(bookmarkList.get(position));
            bookmarkList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeRemoved(position, bookmarkList.size());
            Toast.makeText(mContext, "Berhasil dihapus", Toast.LENGTH_SHORT).show();
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

