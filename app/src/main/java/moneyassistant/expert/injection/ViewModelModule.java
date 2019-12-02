package moneyassistant.expert.injection;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import moneyassistant.expert.ui.account.viewmodel.AccountViewModel;
import moneyassistant.expert.ui.category.viewmodel.CategoryViewModel;
import moneyassistant.expert.ui.main.MainViewModel;
import moneyassistant.expert.ui.settings.viewmodel.SettingsViewModel;
import moneyassistant.expert.ui.splash.SplashViewModel;
import moneyassistant.expert.ui.transaction.viewmodel.SaveTransactionViewModel;
import moneyassistant.expert.ui.transaction.viewmodel.TransactionViewModel;
import moneyassistant.expert.ui.transaction.viewmodel.ViewTransactionViewModel;
import moneyassistant.expert.util.IntelViewModelFactory;

/**
 * MoneyAssistant
 * Created by catalin on 14.11.2019
 */
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel.class)
    abstract ViewModel bindSplashViewModel(SplashViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindMainViewModel(MainViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TransactionViewModel.class)
    abstract ViewModel bindTransactionViewModel(TransactionViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel.class)
    abstract ViewModel bindAccountViewModel(AccountViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CategoryViewModel.class)
    abstract ViewModel bindCategoryViewModel(CategoryViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SaveTransactionViewModel.class)
    abstract ViewModel bindSaveTransactionViewModel(SaveTransactionViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ViewTransactionViewModel.class)
    abstract ViewModel bindViewTransactionViewModel(ViewTransactionViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel.class)
    abstract ViewModel bindSettingsViewModel(SettingsViewModel viewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(IntelViewModelFactory intelViewModelFactory);
}
