package app.storytel.candidate.com.adapters;


import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.storytel.candidate.com.R;
import app.storytel.candidate.com.databinding.PostItemBinding;
import app.storytel.candidate.com.listeners.ItemEventListener;
import app.storytel.candidate.com.model.PostAndImages;
import app.storytel.candidate.com.viewmodel.ScrollingViewModel;
import io.reactivex.annotations.NonNull;


/**
 * Adapter class for post recyclerview
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>
        implements ItemEventListener {

    /**
     * TAG
     */
    private final String TAG = "PostAdapter";

    /**
     * Scrolling activity view model
     */
    private ScrollingViewModel viewModel;

    /**
     * Post and images object
     */
    private PostAndImages postAndImages;

    public PostAdapter(ScrollingViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ScrollingViewModel viewModel = this.viewModel;
        viewHolder.binding.setPostData(postAndImages.mPosts.get(i));
        viewHolder.binding.setPhotoData(postAndImages.mPhotos.get(i));
        viewHolder.binding.setItemPosition(i);
        viewHolder.binding.setMainData(viewModel);
        viewHolder.binding.setItemClickListener(this);
        viewHolder.binding.setSimpleListAdapter(this);
        viewHolder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return (postAndImages == null) ? 0 : postAndImages.mPosts.size();
    }

    /**
     * function to set post and images object
     *
     * @param postAndImages postandimages object
     */
    public void setPostAndImages(PostAndImages postAndImages) {
        this.postAndImages = postAndImages;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onClickListItem(int position) {
        Log.d(TAG, "onClickListItem: viewModel position " + position);
        viewModel.doOnListItemClick(postAndImages.mPosts.get(position), postAndImages.mPhotos.get(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        // Post item binding
        PostItemBinding binding;

        ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}