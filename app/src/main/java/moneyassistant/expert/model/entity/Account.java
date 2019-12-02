package moneyassistant.expert.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(tableName = "accounts")
public class Account {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "account_id")
    private Long id;

    @ColumnInfo(name = "account_created_date")
    private Long createdDate;

    @ColumnInfo(name = "account_type")
    private String type;

    @ColumnInfo(name = "account_name")
    private String name;

    @ColumnInfo(name = "account_starting_amount")
    private Double startingAmount;

    @ColumnInfo(name = "account_current_amount")
    private Double currentAmount;

}
