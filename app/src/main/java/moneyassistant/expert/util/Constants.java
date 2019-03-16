package moneyassistant.expert.util;

import java.util.HashMap;

import moneyassistant.expert.R;

public class Constants {

    private Constants() {}

    public static final String FIRST_TIME_RUN = "first_time_run";
    public static final String PREFERED_CURRENCY = "prefered_currency";

    public static final int SPLASH_SCREEN_TIMEOUT = 777;

    public static final String APP = "MoneyAssistant";
    public static final String resourceId = "id";
    public static final String CATEGORY = "category";

    public static final int CHANGE_ICON_REQUEST_CODE = 11;
    public static final int CHANGE_ICON_RESULT_CODE = 12;
    public static final int CHANGE_ACCOUNT_REQUEST_CODE = 13;
    public static final int CHANGE_ACCOUNT_RESULT_CODE = 14;
    public static final int CHANGE_CATEGORY_REQUEST_CODE = 15;
    public static final int CHANGE_CATEGORY_RESULT_CODE = 16;
    public static final int CHANGE_ACCOUNT2_REQUEST_CODE = 17;
    public static final int CHANGE_ACCOUNT2_RESULT_CODE = 18;

    public static final String INCOME = "income";
    public static final String EXPENSE = "expense";
    public static final String TRANSFER = "transfer";

    public static final String DATE_FORMAT_1 = "dd MMMM yyyy";
    public static final String DATE_FORMAT_2 = "MMMM yyyy";
    public static final String DATE_FORMAT_3 = "yyyy";

    public static final String[] WALLET_TYPES = new String[] {
            "bank",
            "overdrafts",
            "card",
            "cash",
            "debit_card",
            "insurance",
            "investments",
            "loan",
            "prepaid",
            "savings",
            "others"
    };

    public static final HashMap<Integer, Integer> I_CATEGORY_MAP = new HashMap<Integer, Integer>() {{
        put(R.string.salary, R.drawable.icons8_refund_24);
    }};

    public static final HashMap<Integer, Integer> E_CATEGORY_MAP = new HashMap<Integer, Integer>() {{
       put(R.string.fuel, R.drawable.icons8_gas_station_24);
       put(R.string.clothes, R.drawable.icons8_jacket_24);
       put(R.string.restaurant, R.drawable.icons8_dining_room_24);
       put(R.string.entertainment, R.drawable.icons8_3d_glasses_24);
       put(R.string.gifts, R.drawable.icons8_wedding_gift_24);
       put(R.string.holidays, R.drawable.icons8_christmas_tree_24);
       put(R.string.travel, R.drawable.icons8_night_landscape_24);
       put(R.string.shopping, R.drawable.icons8_shopping_cart_24);
       put(R.string.sports, R.drawable.icons8_running_24);
       put(R.string.health, R.drawable.icons8_hospital_3_24);
       put(R.string.transportation, R.drawable.icons8_public_transportation_24);
       put(R.string.education, R.drawable.icons8_scholarship_24);
       put(R.string.self_development, R.drawable.icons8_development_skill_24);
       put(R.string.general, R.drawable.icons8_price_tag_24);
    }};

    public static final String[] CATEGORY_ICONS = new String[] {
            "icons8_account_24",
            "icons8_bad_idea_24",
            "icons8_bank_cards_24",
            "icons8_fund_accounting_24",
            "icons8_money_24",
            "icons8_profit_24",
            "icons8_refund_24",
            "icons8_3d_glasses_24",
            "icons8_airplane_take_off_24",
            "icons8_archers_arrow_24",
            "icons8_asteroid_24",
            "icons8_baby_24",
            "icons8_barber_scissors_24",
            "icons8_beach_24",
            "icons8_biotech_24",
            "icons8_blu_ray_24",
            "icons8_camera_24",
            "icons8_cards_24",
            "icons8_cash_24",
            "icons8_cash_in_hand_24",
            "icons8_cat_24",
            "icons8_cave_24",
            "icons8_chichen_itza_24",
            "icons8_christmas_tree_24",
            "icons8_city_24",
            "icons8_colosseum_24",
            "icons8_company_24",
            "icons8_development_skill_24",
            "icons8_dining_room_24",
            "icons8_dna_helix_24",
            "icons8_documentary_24",
            "icons8_dog_24",
            "icons8_dog_park_24",
            "icons8_eiffel_tower_24",
            "icons8_euro_money_24",
            "icons8_firework_24",
            "icons8_flower_24",
            "icons8_flower_bouquet_24",
            "icons8_flowers_24",
            "icons8_forest_24",
            "icons8_game_controller_24",
            "icons8_gas_station_24",
            "icons8_goatee_24",
            "icons8_horse_24",
            "icons8_horseback_riding_24",
            "icons8_hospital_3_24",
            "icons8_phone_24",
            "icons8_iphone_24",
            "icons8_jacket_24",
            "icons8_landscape_24",
            "icons8_loudspeaker_24",
            "icons8_movie_24",
            "icons8_musical_notes_24",
            "icons8_netflix_24",
            "icons8_night_landscape_24",
            "icons8_paragliding_24",
            "icons8_pizza_24",
            "icons8_playstation_24",
            "icons8_price_tag_24",
            "icons8_public_transportation_24",
            "icons8_radioactive_24",
            "icons8_reading_24",
            "icons8_reflection_24",
            "icons8_rock_music_24",
            "icons8_rock_music_24",
            "icons8_scholarship_24",
            "icons8_school_24",
            "icons8_shopping_bag_24",
            "icons8_shopping_cart_24",
            "icons8_skiing_24",
            "icons8_slr_camera_24",
            "icons8_smartphone_tablet_24",
            "icons8_smoking_24",
            "icons8_speed_24",
            "icons8_stethoscope_24",
            "icons8_suitcase_24",
            "icons8_surfing_24",
            "icons8_tennis_racquet_24",
            "icons8_train_24",
            "icons8_transportation_24",
            "icons8_us_capitol_24",
            "icons8_user_groups_24",
            "icons8_vegetarian_food_24",
            "icons8_viking_helmet_24",
            "icons8_wedding_cake_24",
            "icons8_wedding_gift_24",
            "icons8_running_24"
    };

}
