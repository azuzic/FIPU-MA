package hr.fipu.room2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private List<BookReview> reviews = new ArrayList<>();

    public void setReviews(List<BookReview> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        holder.bind(reviews.get(position));
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView ratingView, textView, dateView;

        ReviewViewHolder(View itemView) {
            super(itemView);
            ratingView = itemView.findViewById(R.id.review_rating);
            textView = itemView.findViewById(R.id.review_text);
            dateView = itemView.findViewById(R.id.review_date);
        }

        void bind(BookReview review) {
            ratingView.setText("⭐ " + review.rating + "/5");
            textView.setText(review.reviewText);
            dateView.setText(review.dateReviewed);
        }
    }
}
