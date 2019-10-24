package app.storytel.candidate.com.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import app.storytel.candidate.com.R;
import app.storytel.candidate.com.databinding.CommentItemBinding;
import app.storytel.candidate.com.model.Comment;
import app.storytel.candidate.com.viewmodel.DetailingViewModel;


/**
 * Adapter to show the list of comments
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    /**
     * TAG
     */
    private final String TAG = "CommentAdapter";

    /**
     * Detailing view model
     */
    private DetailingViewModel detailViewModel;

    /**
     * List of comments
     */
    private List<Comment> comments;

    /**
     *
     * @param detailViewModel
     */
    public CommentAdapter(DetailingViewModel detailViewModel) {
        this.detailViewModel = detailViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        DetailingViewModel viewModel = detailViewModel;
        viewHolder.binding.setPostData(comments.get(i));
        viewHolder.binding.setMainData(viewModel);
        viewHolder.binding.setSimpleListAdapter(this);
        viewHolder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return (comments == null) ? 0 : ((comments.size() > 3) ? 3 : comments.size());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setCommentsList(List<Comment> commentsList) {
        Log.d(TAG, "setCommentsList:");
        this.comments = commentsList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        //Comment item binding
        CommentItemBinding binding;

        ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}