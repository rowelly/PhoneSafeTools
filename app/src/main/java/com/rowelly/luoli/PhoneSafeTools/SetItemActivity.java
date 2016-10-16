package com.rowelly.luoli.PhoneSafeTools;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.rowelly.luoli.user_defined.SettingItemView;

public class SetItemActivity extends Activity {

    private SettingItemView siv;
    private Boolean update;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_layout);
        siv = (SettingItemView)findViewById(R.id.siv);
        sharedPreferences = getSharedPreferences("config",MODE_PRIVATE);
        update = sharedPreferences.getBoolean("auto_update",true);
        siv.setCheck(update);
        siv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(siv.isCheck()) {
                    siv.setCheck(false);
                    sharedPreferences.edit().putBoolean("auto_update",false).apply();
                }

                else {
                    siv.setCheck(true);
                    sharedPreferences.edit().putBoolean("auto_update",true).apply();
                }
            }
        });

    }

}
