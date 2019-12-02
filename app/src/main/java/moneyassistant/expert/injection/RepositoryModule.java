package moneyassistant.expert.injection;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import moneyassistant.expert.database.AccountDao;
import moneyassistant.expert.database.CategoryDao;
import moneyassistant.expert.database.TransactionDao;
import moneyassistant.expert.repository.AccountRepository;
import moneyassistant.expert.repository.CategoryRepository;
import moneyassistant.expert.repository.TransactionRepository;

/**
 * MoneyAssistant
 * Created by catalin on 14.11.2019
 */
@Module
class RepositoryModule {

    @Provides
    @Singleton
    AccountRepository accountRepository(AccountDao accountDao) {
        return new AccountRepository(accountDao);
    }

    @Provides
    @Singleton
    CategoryRepository categoryRepository(CategoryDao categoryDao) {
        return new CategoryRepository(categoryDao);
    }

    @Provides
    @Singleton
    TransactionRepository transactionRepository(TransactionDao transactionDao) {
        return new TransactionRepository(transactionDao);
    }

}
