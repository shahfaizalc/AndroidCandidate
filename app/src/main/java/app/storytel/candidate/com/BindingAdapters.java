package app.storytel.candidate.com;

import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import app.storytel.candidate.com.adapters.CommentAdapter;
import app.storytel.candidate.com.adapters.PostAdapter;
import app.storytel.candidate.com.view.DetailsFragment;
import app.storytel.candidate.com.view.ScrollingFragment;
import app.storytel.candidate.com.viewmodel.DetailingViewModel;
import app.storytel.candidate.com.viewmodel.ScrollingViewModel;

/**
 * Class to manage all binding adapters
 */
public class BindingAdapters {


    /**
     * To show the list of posts in recyclerview
     * @param scrollingViewModel : View model of the home class
     * @param recyclerView       : jsonplaceholder Photos and posts recycler view
     * @param activity:          Scrolling activity
     */
    @BindingAdapter({"app:postListAdpater", "app:postListActivity"})
    public static void adapter(final RecyclerView recyclerView, final ScrollingViewModel scrollingViewModel, final ScrollingFragment activity) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        PostAdapter listAdapter = new PostAdapter(scrollingViewModel);
        recyclerView.setAdapter(listAdapter);
        scrollingViewModel.setPostAdapter(listAdapter);

        //set post and images object values to  list
        scrollingViewModel.getAllPostAndImages().observe(activity, listAdapter::setPostAndImages);
    }


    /**
     * To show the list of comments in recyclerview
     * @param detailingViewModel : View model of comments lists
     * @param recyclerView       : jsonplaceholder comments recycler view
     * @param activity:          Details Activity
     */

    @BindingAdapter({"app:postInfoListAdpater", "app:postInfoListActivity"})
    public static void adapter(final RecyclerView recyclerView, final DetailingViewModel detailingViewModel, final DetailsFragment activity) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        final CommentAdapter listAdapter = new CommentAdapter(detailingViewModel);
        recyclerView.setAdapter(listAdapter);
        detailingViewModel.setCommentAdapter(listAdapter);

        //set comments object values to  list
        detailingViewModel.getAllComments().observe(activity, listAdapter::setCommentsList);
    }

    /**
     * To display images
     * @param view     image view
     * @param imageUrl image URL
     */
    @BindingAdapter({"app:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.get()
                .load(imageUrl)
                .into(view);
    }

}
