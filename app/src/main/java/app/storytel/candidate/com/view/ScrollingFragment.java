package app.storytel.candidate.com.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.gson.Gson;

import app.storytel.candidate.com.R;
import app.storytel.candidate.com.databinding.FragmentScrollingBinding;
import app.storytel.candidate.com.factory.ScrollingViewModelFactory;
import app.storytel.candidate.com.model.Details;
import app.storytel.candidate.com.util.Constants;
import app.storytel.candidate.com.util.NetworkChangeEventListener;
import app.storytel.candidate.com.viewmodel.ScrollingViewModel;

public class ScrollingFragment extends Fragment implements NetworkChangeEventListener.NetworkStateListener {

    /**
     * TAG
     */
    private static final String TAG = "ScrollingActivity";

    /**
     * Binding
     */
    FragmentScrollingBinding binding;

    /**
     * Network change event listener
     */
    NetworkChangeEventListener networkChangeEventListener;

    /**
     * Fragment Navigation controller
     */
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (binding == null) {
            binding = FragmentScrollingBinding.inflate(inflater, container, false);
            ScrollingViewModel areaViewModel = ViewModelProviders.of(this, new ScrollingViewModelFactory(getActivity().getApplication()))
                    .get(ScrollingViewModel.class);
            setBindingAttributes(areaViewModel);
        }
        return binding.getRoot();
    }

    private void setBindingAttributes(ScrollingViewModel areaViewModel) {
        binding.setMainData(areaViewModel);
        binding.included.setMainData(areaViewModel);
        binding.included.setScrollingActivity(this);
        binding.included.retry.setOnClickListener(view1 -> retryRequest());
        binding.fab.setOnClickListener(view -> retryRequest());
        networkChangeEventListener = new NetworkChangeEventListener();
        navController = Navigation.findNavController(this.getActivity(), R.id.my_nav_host_fragment);
        handleCallBacks();
    }

    /**
     * Handling call backs which involves activity
     */
    private void handleCallBacks() {
        binding.getMainData().getBlogArticleLink().observe(this, post -> {
            Log.d(TAG, "handleCallBacks: Launch DetailsActivity");
            launchDetailsFragment(post);
        });
    }

    private void launchDetailsFragment(Details post) {
        try {
            final Bundle bundle = new Bundle();
            bundle.putString(Constants.KEY_POST_RESULT, new Gson().toJson(post));
            navController.navigate(R.id.action_ScrollingFragment_to_detailsFragment, bundle);
        }catch (Exception e){
            Log.d(TAG, "launchDetailsFragment: IllegalArgumentException"+ e);
        }

    }

    private void retryRequest() {
        if (binding.getMainData().handleMultipleClicks()) {
            binding.getMainData().setProgressBarVisible(View.VISIBLE);
            binding.getMainData().getAllPostAndImages().observe(
                    getActivity(), postAndImages ->
                            binding.getMainData().getPostAdapter().setPostAndImages(postAndImages));
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        networkChangeEventListener.unRegisterNetWorkChangeBroadCast(this.getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        networkChangeEventListener.setNetworkStateListener(this);
        networkChangeEventListener.registerNetWorkChangeBroadCast(this.getContext());
    }

    @Override
    public void onStateReceived(boolean state) {
        binding.fab.setEnabled(state);
        binding.included.retry.setEnabled(state);
        if (!state) {
            Toast.makeText(this.getContext(), R.string.network_ErrorMsg, Toast.LENGTH_LONG).show();
        }
    }
}
