package app.storytel.candidate.com.repository;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import java.util.List;

import app.storytel.candidate.com.listeners.ResultListener;
import app.storytel.candidate.com.model.Comment;
import app.storytel.candidate.com.network.GetDataService;
import app.storytel.candidate.com.network.RetrofitClientInstance;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CommentsRepository {

    /**
     * TAG
     */
    private static final String TAG = "CommentsRepository";


    /**
     * funtion to provide list of comments
     */
    public MutableLiveData<List<Comment>> getMutableLiveData(String id, final ResultListener resultListener, Context context) {

        //Comments list
        MutableLiveData<List<Comment>> mutableLiveData = new MutableLiveData<>();

        //retrofit service
        GetDataService service = RetrofitClientInstance.getRetrofitInstance(context).create(GetDataService.class);

        Observable<List<Comment>> responseObservable =  service.getAllComments(id);
        responseObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Comment>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Comment> comments) {
                        mutableLiveData.setValue(comments);
                        resultListener.onSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "initRecycler: initRequest onFailure "+e.getLocalizedMessage());
                        resultListener.onError(e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "initRecycler: initRequest onComplete");
                    }
                });

        return  mutableLiveData;
    }
}
