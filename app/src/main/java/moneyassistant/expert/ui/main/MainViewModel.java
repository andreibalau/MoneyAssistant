package moneyassistant.expert.ui.main;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

/**
 * MoneyAssistant
 * Created by catalin on 16.11.2019
 */
public class MainViewModel extends ViewModel {

    private MutableLiveData<Fragment> mutableFragment = new MutableLiveData<>();

    @Inject
    public MainViewModel() {

    }

    public MutableLiveData<Fragment> getMutableFragment() {
        return mutableFragment;
    }

    public void setFragment(Fragment fragment) {
        this.mutableFragment.setValue(fragment);
    }

}
