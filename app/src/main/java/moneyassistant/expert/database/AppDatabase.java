package moneyassistant.expert.database;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import moneyassistant.expert.model.Type;
import moneyassistant.expert.model.Wallet;
import moneyassistant.expert.model.entity.Account;
import moneyassistant.expert.model.entity.Category;
import moneyassistant.expert.model.entity.Transaction;

@Database(entities = {Account.class, Category.class, Transaction.class},
        version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase appDatabase;

    public static synchronized AppDatabase getInstance(Context context) {
        if (appDatabase == null) {
            appDatabase = Room
                    .databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "moneyinthebank")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return appDatabase;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.d("ROOM_CALLBACK", "onCreate: database");

            ContentValues accountValues = new ContentValues();
            accountValues.put("account_type", Wallet.CASH.name());
            accountValues.put("account_name", "Cash");
            accountValues.put("account_starting_amount", 0);
            accountValues.put("account_current_amount", 0);
            db.insert("accounts", OnConflictStrategy.IGNORE, accountValues);

            ContentValues categoryValues = new ContentValues();
            categoryValues.put("category_name", "Salary");
            categoryValues.put("category_type", Type.INCOME.name());
            categoryValues.put("category_icon", "icons8_refund");
            db.insert("categories", OnConflictStrategy.IGNORE, categoryValues);

            categoryValues = new ContentValues();
            categoryValues.put("category_name", "Fuel");
            categoryValues.put("category_type", Type.EXPENSE.name());
            categoryValues.put("category_icon", "icons8_gas_station");
            db.insert("categories", OnConflictStrategy.IGNORE, categoryValues);

            categoryValues = new ContentValues();
            categoryValues.put("category_name", "Clothes");
            categoryValues.put("category_type", Type.EXPENSE.name());
            categoryValues.put("category_icon", "icons8_jacket");
            db.insert("categories", OnConflictStrategy.IGNORE, categoryValues);

            categoryValues = new ContentValues();
            categoryValues.put("category_name", "Restaurant");
            categoryValues.put("category_type", Type.EXPENSE.name());
            categoryValues.put("category_icon", "icons8_dining_room");
            db.insert("categories", OnConflictStrategy.IGNORE, categoryValues);

            categoryValues = new ContentValues();
            categoryValues.put("category_name", "Entertainment");
            categoryValues.put("category_type", Type.EXPENSE.name());
            categoryValues.put("category_icon", "icons8_3d_glasses");
            db.insert("categories", OnConflictStrategy.IGNORE, categoryValues);

            categoryValues = new ContentValues();
            categoryValues.put("category_name", "Gifts");
            categoryValues.put("category_type", Type.EXPENSE.name());
            categoryValues.put("category_icon", "icons8_wedding_gift");
            db.insert("categories", OnConflictStrategy.IGNORE, categoryValues);

            categoryValues = new ContentValues();
            categoryValues.put("category_name", "Holidays");
            categoryValues.put("category_type", Type.EXPENSE.name());
            categoryValues.put("category_icon", "icons8_christmas_tree");
            db.insert("categories", OnConflictStrategy.IGNORE, categoryValues);

            categoryValues = new ContentValues();
            categoryValues.put("category_name", "Travel");
            categoryValues.put("category_type", Type.EXPENSE.name());
            categoryValues.put("category_icon", "icons8_night_landscape");
            db.insert("categories", OnConflictStrategy.IGNORE, categoryValues);

            categoryValues = new ContentValues();
            categoryValues.put("category_name", "Shopping");
            categoryValues.put("category_type", Type.EXPENSE.name());
            categoryValues.put("category_icon", "icons8_shopping_cart");
            db.insert("categories", OnConflictStrategy.IGNORE, categoryValues);

            categoryValues = new ContentValues();
            categoryValues.put("category_name", "Sports");
            categoryValues.put("category_type", Type.EXPENSE.name());
            categoryValues.put("category_icon", "icons8_running");
            db.insert("categories", OnConflictStrategy.IGNORE, categoryValues);

            categoryValues = new ContentValues();
            categoryValues.put("category_name", "Health");
            categoryValues.put("category_type", Type.EXPENSE.name());
            categoryValues.put("category_icon", "icons8_hospital");
            db.insert("categories", OnConflictStrategy.IGNORE, categoryValues);

            categoryValues = new ContentValues();
            categoryValues.put("category_name", "Transportation");
            categoryValues.put("category_type", Type.EXPENSE.name());
            categoryValues.put("category_icon", "icons8_public_transportation");
            db.insert("categories", OnConflictStrategy.IGNORE, categoryValues);

            categoryValues = new ContentValues();
            categoryValues.put("category_name", "Education");
            categoryValues.put("category_type", Type.EXPENSE.name());
            categoryValues.put("category_icon", "icons8_school");
            db.insert("categories", OnConflictStrategy.IGNORE, categoryValues);

            categoryValues = new ContentValues();
            categoryValues.put("category_name", "Self Development");
            categoryValues.put("category_type", Type.EXPENSE.name());
            categoryValues.put("category_icon", "icons8_development_skill");
            db.insert("categories", OnConflictStrategy.IGNORE, categoryValues);

            categoryValues = new ContentValues();
            categoryValues.put("category_name", "General");
            categoryValues.put("category_type", Type.EXPENSE.name());
            categoryValues.put("category_icon", "icons8_price_tag");
            db.insert("categories", OnConflictStrategy.IGNORE, categoryValues);
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

    public abstract AccountDao getAccountDao();
    public abstract CategoryDao getCategoryDao();
    public abstract TransactionDao getTransactionDao();

}
