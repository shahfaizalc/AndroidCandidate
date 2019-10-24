package app.storytel.candidate.com.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.google.gson.Gson;
import app.storytel.candidate.com.R;
import app.storytel.candidate.com.databinding.FragmentDetailsBinding;
import app.storytel.candidate.com.factory.DetailingViewModelFactory;
import app.storytel.candidate.com.model.Details;
import app.storytel.candidate.com.util.Constants;
import app.storytel.candidate.com.util.NetworkChangeEventListener;
import app.storytel.candidate.com.viewmodel.DetailingViewModel;

public class DetailsFragment extends Fragment implements NetworkChangeEventListener.NetworkStateListener {


    /**
     * Binding
     */
    FragmentDetailsBinding binding;

    /**
     * Network change listener
     */
    NetworkChangeEventListener networkChangeEventListener;

    /**
     * Navigation controller
     */
    NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (binding == null) {
            Details myObject = getDetails();
            binding = FragmentDetailsBinding.inflate(inflater, container, false);
            DetailingViewModel areaViewModel = ViewModelProviders.of(this,
                    new DetailingViewModelFactory(this.getActivity().getApplication(), myObject)).get(DetailingViewModel.class);
            setBindingAttributes(myObject, areaViewModel);
        }
        return binding.getRoot();
    }

    private Details getDetails() {
        String jsonValue = getArguments().getString(Constants.KEY_POST_RESULT);
        return new Gson().fromJson(jsonValue, Details.class);
    }

    private void setBindingAttributes(Details myObject, DetailingViewModel areaViewModel) {
        binding.setMainData(areaViewModel);
        binding.included.setMainData(areaViewModel);
        binding.included.setScrollingActivity(this);
        binding.fab.setOnClickListener(view -> retryRequest());
        binding.included.details.setText(myObject.body);
        binding.setImageurl(myObject.thumbnail);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        networkChangeEventListener = new NetworkChangeEventListener();
        navController = Navigation.findNavController(this.getActivity(), R.id.my_nav_host_fragment);
        binding.included.retry.setOnClickListener(view1 -> retryRequest());
    }


    private void retryRequest() {
        binding.getMainData().setProgressBarVisible(View.VISIBLE);
        binding.getMainData().getAllComments().observe(
                this, commentList ->
                        binding.getMainData().getCommentAdapter().setCommentsList(commentList));
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
