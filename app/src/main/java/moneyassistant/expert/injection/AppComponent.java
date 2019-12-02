package moneyassistant.expert.injection;

import javax.inject.Singleton;

import dagger.Component;
import moneyassistant.expert.ui.account.activity.AccountActivity;
import moneyassistant.expert.ui.account.fragment.AccountsFragment;
import moneyassistant.expert.ui.category.activity.CategoriesActivity;
import moneyassistant.expert.ui.category.activity.CategoryActivity;
import moneyassistant.expert.ui.category.fragment.ExpenseCategories;
import moneyassistant.expert.ui.category.fragment.IncomeCategories;
import moneyassistant.expert.ui.intro.IntroActivity;
import moneyassistant.expert.ui.main.MainActivity;
import moneyassistant.expert.ui.settings.fragment.SettingsFragment;
import moneyassistant.expert.ui.splash.SplashActivity;
import moneyassistant.expert.ui.transaction.activity.AddTransactionActivity;
import moneyassistant.expert.ui.transaction.activity.TransactionActivity;
import moneyassistant.expert.ui.transaction.fragment.BottomTransaction;
import moneyassistant.expert.ui.transaction.fragment.TransactionsFragment;

/**
 * MoneyAssistant
 * Created by catalin on 14.11.2019
 */
@Singleton
@Component(modules = {
        AppModule.class,
        DatabaseModule.class,
        RepositoryModule.class,
        ViewModelModule.class
})
public interface AppComponent {
    void inject(SplashActivity splashActivity);
    void inject(MainActivity mainActivity);
    void inject(AccountActivity accountActivity);
    void inject(CategoryActivity categoryActivity);
    void inject(AddTransactionActivity addTransactionActivity);
    void inject(TransactionActivity transactionActivity);
    void inject(CategoriesActivity categoriesActivity);
    void inject(IntroActivity introActivity);
    void inject(AccountsFragment accountsFragment);
    void inject(IncomeCategories incomeCategories);
    void inject(ExpenseCategories expenseCategories);
    void inject(TransactionsFragment transactionFragment);
    void inject(BottomTransaction bottomTransaction);
    void inject(SettingsFragment settingsFragment);
}
