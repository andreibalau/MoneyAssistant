package moneyassistant.expert.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "money_transaction")
public class Transaction {

    public interface TransactionTypes {
        String Income = "income";
        String Expense = "expense";
        String Transfer = "transfer";
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transaction_id")
    private long id;

    @ColumnInfo(name = "transaction_type")
    private String type;

    @ColumnInfo(name = "transaction_date")
    private String date;

    @ColumnInfo(name = "account_id_f")
    private long accountId;

    @ColumnInfo(name = "category_id_f")
    private long categoryId;

    @ColumnInfo(name = "transaction_amount")
    private double amount;

    @ColumnInfo(name = "transaction_details")
    private String details;

    @ColumnInfo(name = "transaction_day")
    private int day;

    @ColumnInfo(name = "transaction_month")
    private int month;

    @ColumnInfo(name = "transaction_year")
    private int year;

    public Transaction() { }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
