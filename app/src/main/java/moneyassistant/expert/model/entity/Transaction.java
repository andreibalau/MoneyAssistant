package moneyassistant.expert.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(tableName = "transactions")
public class Transaction {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transaction_id")
    private Long id;

    @ColumnInfo(name = "transaction_created_date")
    private Long createdDate;

    @ColumnInfo(name = "transaction_type")
    private String type;

    @ColumnInfo(name = "account_id_f")
    private Long accountId;

    @ColumnInfo(name = "category_id_f")
    private Long categoryId;

    @ColumnInfo(name = "transaction_amount")
    private Double amount;

    @ColumnInfo(name = "transaction_details")
    private String details;

    @ColumnInfo(name = "transaction_date")
    private Long date;

}
