package app.storytel.candidate.com.factory;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import app.storytel.candidate.com.model.Details;
import app.storytel.candidate.com.viewmodel.DetailingViewModel;

/**
 * Scrolling viewModel factory class
 */
public class DetailingViewModelFactory implements ViewModelProvider.Factory {


    @NonNull
    private final Application mApplication;

    private Details details;

    public DetailingViewModelFactory(@NonNull Application application, Details details1) {
        mApplication= application;
        details = details1;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new DetailingViewModel(mApplication, details);
    }
}