package app.storytel.candidate.com.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.Bindable;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import app.storytel.candidate.com.BR;
import app.storytel.candidate.com.adapters.PostAdapter;
import app.storytel.candidate.com.listeners.ResultListener;
import app.storytel.candidate.com.model.Details;
import app.storytel.candidate.com.model.Photo;
import app.storytel.candidate.com.model.Post;
import app.storytel.candidate.com.model.PostAndImages;
import app.storytel.candidate.com.repository.PostAndImagesRepository;
import app.storytel.candidate.com.util.ViewModelCallback;


public class ScrollingViewModel extends ViewModelCallback implements ResultListener {

    /**
     * TAG
     */
    private static final String TAG = "ScrollingViewModel";

    /**
     * Post And Images Repository
     */
    private PostAndImagesRepository postAndImagesRepository;

    /**
     * To handle multiple clicks
     */
    private long mLastClickTime;

    /**
     * Details Mutable LiveData
     */
    private MutableLiveData<Details> detailsMutableLiveData;

    /**
     * Post adapter
     */
    private PostAdapter postAdapter;

    /**
     * Application
     */
    private Application application;

    /**
     * Progress bar visibility
     */
    private int progressBarVisible = View.VISIBLE;

    /**
     * UI state on error
     */
    private int onErrorUIVisibility = View.GONE;


    public ScrollingViewModel(@NonNull Application application) {
        postAndImagesRepository = new PostAndImagesRepository();
        detailsMutableLiveData = new MutableLiveData<>();
        this.application = application;

    }

    public LiveData<PostAndImages> getAllPostAndImages() {
        return postAndImagesRepository.getMutableLiveData(this,application.getApplicationContext());
    }

    public void doOnListItemClick(Post blogItemModel, Photo photo) {

        Log.d("TAG", "doOnListItemClick: requestState clicked ");
        if (!handleMultipleClicks()) {
            Details details = new Details();
            details.body = blogItemModel.body;
            details.thumbnail = photo.thumbnailUrl;
            details.id = String.valueOf(blogItemModel.id);
            detailsMutableLiveData.setValue(details);
        }
    }

    public void setPostAdapter(PostAdapter postAdapter) {
        this.postAdapter = postAdapter;
    }

    public PostAdapter getPostAdapter() {
        return postAdapter;
    }

    /**
     * To get article url
     */
    public LiveData<Details> getBlogArticleLink() {
        return detailsMutableLiveData;
    }

    @Bindable
    public int getProgressBarVisible() {
        return this.progressBarVisible;
    }

    public void setProgressBarVisible(int progressBarVisible) {
        this.progressBarVisible = progressBarVisible;
        notifyPropertyChanged(BR.progressBarVisible);
    }

    @Bindable
    public int getOnError() {
        return this.onErrorUIVisibility;
    }

    public void setOnError(int onErrror) {
        this.onErrorUIVisibility = onErrror;
        notifyPropertyChanged(BR.onError);
    }

    @Override
    public void onError(String err) {
        Log.d(TAG, "onError: onError ");
        setProgressBarVisible(View.GONE);
        setOnError(View.VISIBLE);
    }

    @Override
    public void onSuccess() {
        Log.d(TAG, "onSuccess: onSuccess ");
        setProgressBarVisible(View.GONE);
        setOnError(View.GONE);
    }


    public Boolean handleMultipleClicks() {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return true;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        return false;
    }
}
