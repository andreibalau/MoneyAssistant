package moneyassistant.expert.injection;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import moneyassistant.expert.database.AccountDao;
import moneyassistant.expert.database.AppDatabase;
import moneyassistant.expert.database.CategoryDao;
import moneyassistant.expert.database.TransactionDao;

/**
 * MoneyAssistant
 * Created by catalin on 14.11.2019
 */
@Module
public class DatabaseModule {

    @Provides
    @Singleton
    public AppDatabase appDatabase(Context context) {
        return AppDatabase.getInstance(context);
    }

    @Provides
    @Singleton
    public AccountDao accountDao(AppDatabase database) {
        return database.getAccountDao();
    }

    @Provides
    @Singleton
    public CategoryDao categoryDao(AppDatabase database) {
        return database.getCategoryDao();
    }

    @Provides
    @Singleton
    public TransactionDao transactionDao(AppDatabase database) {
        return database.getTransactionDao();
    }

}
