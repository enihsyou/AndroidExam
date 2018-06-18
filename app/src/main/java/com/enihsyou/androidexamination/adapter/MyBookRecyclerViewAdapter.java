package com.enihsyou.androidexamination.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.enihsyou.androidexamination.R;
import com.enihsyou.androidexamination.adapter.MyBookRecyclerViewAdapter.BookViewHolder;
import com.enihsyou.androidexamination.domain.BookItem;
import com.enihsyou.androidexamination.domain.BookSearchResult;
import com.enihsyou.androidexamination.fragment.BookFragment.OnBookListFragmentInteractionListener;

@Deprecated
public class MyBookRecyclerViewAdapter extends RecyclerView.Adapter<BookViewHolder> {

    private final BookSearchResult bookItems;

    private final OnBookListFragmentInteractionListener mListener;

    public MyBookRecyclerViewAdapter(BookSearchResult items, OnBookListFragmentInteractionListener listener) {
        this.bookItems = items;
        this.mListener = listener;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_book_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BookViewHolder holder, int position) {
        holder.item = this.bookItems.getSubjects()[position];

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (null != mListener) {
                //    // Notify the active callbacks interface (the activity, if the
                //    // fragment is attached to one) that an item has been selected.
                //    mListener.onMovieItemSelected(holder.item);
                //}
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.bookItems.getSubjects().length;
    }

    class BookViewHolder extends RecyclerView.ViewHolder {

        final View view;

        final TextView bookTitle;

        final ImageView bookImage;

        BookItem item;

        public BookViewHolder(View view) {
            super(view);
            this.view = view;
            this.bookTitle = view.findViewById(R.id.book_title);
            this.bookImage = view.findViewById(R.id.book_post);
        }

        @Override
        public String toString() {
            return String.format("%s '%s'", super.toString(), this.bookTitle.getText());
        }
    }
}
