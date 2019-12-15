package moneyassistant.expert.ui.category.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import moneyassistant.expert.R;
import moneyassistant.expert.ui.category.adapter.IconAdapter;
import moneyassistant.expert.ui.category.listener.OnIconClickListener;

import static moneyassistant.expert.util.Icons.CATEGORY_ICONS;

public class IconsDialog extends AppCompatActivity implements OnIconClickListener {

    public static final String ICON = "icon";
    public static final int CHANGE_ICON_REQUEST_CODE = 11;
    public static final int CHANGE_ICON_RESULT_CODE = 12;
    private IconAdapter iconAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icons_dialog);
        setTitle(getString(R.string.choose_icon));
        RecyclerView recyclerView = findViewById(R.id.recycler);
        iconAdapter = new IconAdapter(this);
        recyclerView.setAdapter(iconAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        recyclerView.setHasFixedSize(true);
        List<String> icons = new ArrayList<>(Arrays.asList(CATEGORY_ICONS));
        iconAdapter.submitList(icons);
    }

    @Override
    public void onClick(String icon) {
        Intent intent = new Intent();
        intent.putExtra(ICON, icon);
        setResult(CHANGE_ICON_RESULT_CODE, intent);
        onBackPressed();
    }
}
