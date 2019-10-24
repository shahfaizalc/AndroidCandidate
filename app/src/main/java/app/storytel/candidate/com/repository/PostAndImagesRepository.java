package app.storytel.candidate.com.repository;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import app.storytel.candidate.com.listeners.ResultListener;
import app.storytel.candidate.com.model.Photo;
import app.storytel.candidate.com.model.Post;
import app.storytel.candidate.com.model.PostAndImages;
import app.storytel.candidate.com.network.GetDataService;
import app.storytel.candidate.com.network.RetrofitClientInstance;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class PostAndImagesRepository {

    /**
     * TAG
     */
    private static final String TAG = "PostAndImagesRepository";

    /**
     * Post and images
     */
    private PostAndImages postAndImages;

    /**
     * function to get the list of post and photos
     * @return MutableLiveData<PostAndImages> result
     */
    public MutableLiveData<PostAndImages> getMutableLiveData(final ResultListener resultListener, Context context) {

        //live data object
        final MutableLiveData<PostAndImages> postAndImagesMutableLiveData = new MutableLiveData<>();

        //Retrofit service
        GetDataService service = RetrofitClientInstance.getRetrofitInstance(context).create(GetDataService.class);

        //request objects
        List<Observable<?>> requests = new ArrayList<>();
        requests.add(service.getAllPosts());
        requests.add(service.getAllPhotos());

        //Observable
        Observable.zip(
                requests,
                (Function<Object[], Object>) objects -> {
                    // Objects[] is an array of combined results of completed request
                    return objects;
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                // After all requests had been performed the next observer will receive the Object, returned from Function
                .subscribe(new Observer<Object>() {
                               @Override
                               public void onSubscribe(Disposable d) {
                               }

                               @Override
                               public void onNext(Object obj) {
                                   Log.d(TAG, "observer onnext called ");

                                   //Object extracted and  passed to result object. Object extracted in the same order of added.
                                   //TODO: Yet to optimize to handle in case number of expected results becomes multiple
                                   postAndImages = new PostAndImages(((List<Post>) ((Object[]) obj)[0]), ((List<Photo>) ((Object[]) obj)[1]));
                                   postAndImagesMutableLiveData.setValue(postAndImages);
                                   resultListener.onSuccess();
                               }

                               @Override
                               public void onError(Throwable e) {
                                   Log.d(TAG, "Error on fetching results "+e.getLocalizedMessage());
                                   resultListener.onError(e.getLocalizedMessage());

                               }

                               @Override
                               public void onComplete() {
                                   Log.d(TAG, "Request completed ");

                               }
                           }

                );

        return postAndImagesMutableLiveData;
    }


}
