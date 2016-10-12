package com.rowelly.luoli.PhoneSafeTools;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.rowelly.luoli.user_defined.SettingItemView;

public class SetItemActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_layout);
        final SettingItemView siv=(SettingItemView)findViewById(R.id.siv);

        siv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(siv.isCheck())
                    siv.setCheck(false);
                else
                    siv.setCheck(true);
            }
        });
    }

}
