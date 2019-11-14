package moneyassistant.expert.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "category")
public class Category {

    public interface CategoryTypes {
        String Income = "income";
        String Expense = "expense";
        String Transfer = "transfer";
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "category_id")
    private long id;

    @ColumnInfo(name = "category_name")
    private String name;

    @ColumnInfo(name = "category_type")
    private String type;

    @ColumnInfo(name = "category_icon")
    private String icon;

    public Category() { }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}
