package moneyassistant.expert.model;

import android.app.Dialog;
import android.content.Intent;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * MoneyAssistant
 * Created by catalin on 30.11.2019
 */
@Getter
@Setter
@RequiredArgsConstructor
public class Setting {

    public static final int LABEL_TYPE = 0;
    public static final int SETTING_TYPE = 1;

    private final int type;
    private final int icon;
    private final int title;
    private Intent intent;
    private Dialog dialog;

}
