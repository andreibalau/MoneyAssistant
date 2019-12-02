package moneyassistant.expert.model;

import androidx.room.Embedded;

import moneyassistant.expert.model.entity.Account;
import moneyassistant.expert.model.entity.Category;
import moneyassistant.expert.model.entity.Transaction;

public class TransactionWithCA {

    @Embedded
    private Transaction transaction;

    @Embedded
    private Account account;

    @Embedded
    private Category category;

    public TransactionWithCA() { }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
