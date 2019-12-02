package moneyassistant.expert.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(tableName = "categories")
public class Category {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "category_id")
    private Long id;

    @ColumnInfo(name = "category_created_date")
    private Long createdDate;

    @ColumnInfo(name = "category_name")
    private String name;

    @ColumnInfo(name = "category_type")
    private String type;

    @ColumnInfo(name = "category_icon")
    private String icon;

}
