package app.storytel.candidate.com.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.Bindable;
import android.util.Log;
import android.view.View;

import java.util.List;

import app.storytel.candidate.com.BR;
import app.storytel.candidate.com.adapters.CommentAdapter;
import app.storytel.candidate.com.listeners.ResultListener;
import app.storytel.candidate.com.model.Comment;
import app.storytel.candidate.com.model.Details;
import app.storytel.candidate.com.repository.CommentsRepository;
import app.storytel.candidate.com.util.ViewModelCallback;


public class DetailingViewModel extends ViewModelCallback implements ResultListener {

    /**
     * TAG
     */
    private static final String TAG = "DetailingViewModel";

    /**
     * Commments repository
     */
    private CommentsRepository postAndImagesRepository;

    /**
     * MutableLiveData
     */
    private MutableLiveData<Details> detailsMutableLiveData;

    /**
     * CommentAdapter
     */
    private CommentAdapter commentAdapter;

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



    public DetailingViewModel(Application mApplication, Details mParam) {
        application = mApplication;
        postAndImagesRepository = new CommentsRepository();
        detailsMutableLiveData = new MutableLiveData<>();
        setDetailsMutableLiveData(mParam);
    }

    public MutableLiveData<List<Comment>> getAllComments() {
        Log.d(TAG,"getAllComments: "+ getDetailsMutableLiveData().getValue().id);
        return postAndImagesRepository.getMutableLiveData(getDetailsMutableLiveData().getValue().id, this,application.getApplicationContext());
    }

    private LiveData<Details> getDetailsMutableLiveData() {
        return detailsMutableLiveData;
    }

    private void setDetailsMutableLiveData(Details detailsMutableLiveData) {
        this.detailsMutableLiveData.setValue(detailsMutableLiveData); //the live data will help u push data
    }

    public void setCommentAdapter(CommentAdapter commentAdapter) {
        this.commentAdapter = commentAdapter;
    }

    public CommentAdapter getCommentAdapter() {
        return commentAdapter;
    }


    @Bindable
    public int getProgressBarVisible() {
        return this.progressBarVisible;
    }

    public void setProgressBarVisible(int progressBarVisible) {
        this.progressBarVisible = progressBarVisible;
        notifyPropertyChanged(app.storytel.candidate.com.BR.progressBarVisible);
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



}
