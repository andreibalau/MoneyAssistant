package moneyassistant.expert.ui.splash;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import moneyassistant.expert.util.SharedDataHandler;

import static moneyassistant.expert.util.SharedDataHandler.FIRST_TIME;

/**
 * MoneyAssistant
 * Created by catalin on 14.11.2019
 */
public class SplashViewModel extends ViewModel {

    private final SharedDataHandler sharedDataHandler;
    private MutableLiveData<Boolean> isFirstTime = new MutableLiveData<>();

    @Inject
    SplashViewModel(SharedDataHandler sharedDataHandler) {
        this.sharedDataHandler = sharedDataHandler;
        checkIfFirstTime();
    }

    MutableLiveData<Boolean> getIsFirstTime() {
        return isFirstTime;
    }

    private void checkIfFirstTime() {
        isFirstTime.setValue(sharedDataHandler.getBoolean(FIRST_TIME, true));
    }

}
