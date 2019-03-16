package moneyassistant.expert.view.activity;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import moneyassistant.expert.R;
import moneyassistant.expert.util.Constants;
import moneyassistant.expert.util.OnItemClickListener;
import moneyassistant.expert.viewmodel.adapter.IconAdapter;

public class IconsDialog extends AppCompatActivity implements OnItemClickListener {

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
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        List<String> icons = new ArrayList<>(Arrays.asList(Constants.CATEGORY_ICONS));
        iconAdapter.submitList(icons);
    }

    @Override
    public void onClick(int position) {
        String icon = iconAdapter.getIconAt(position);
        Intent intent = new Intent();
        intent.putExtra("icon", icon);
        setResult(Constants.CHANGE_ICON_RESULT_CODE, intent);
        onBackPressed();
    }
}
