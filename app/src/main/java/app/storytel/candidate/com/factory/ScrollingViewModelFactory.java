package app.storytel.candidate.com.factory;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import app.storytel.candidate.com.viewmodel.ScrollingViewModel;

/**
 * Detailing viewModel factory class
 */
public class ScrollingViewModelFactory implements ViewModelProvider.Factory {


    @NonNull
    private final Application mApplication;


    public ScrollingViewModelFactory(@NonNull Application application) {
        mApplication= application;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new ScrollingViewModel(mApplication);
    }
}