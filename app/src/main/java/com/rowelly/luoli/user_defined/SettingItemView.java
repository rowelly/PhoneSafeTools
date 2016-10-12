package com.rowelly.luoli.user_defined;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rowelly.luoli.PhoneSafeTools.R;

/**
 * Created by LuoLi on 2016/10/11.
 */

public class SettingItemView extends RelativeLayout {

    private TextView tv_title;
    private TextView tv_desc;
    private CheckBox cb_status;
    private static final String NAMESPACE="http://schemas.android.com/apk/res/com.rowelly.luoli.PhoneSafeTools";
    private String mTitle;
    private String mDescOn;
    private String mDescOff;

    public SettingItemView(Context context) {
        super(context);
        initView();
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mTitle = attrs.getAttributeValue(NAMESPACE,"title");
        mDescOn = attrs.getAttributeValue(NAMESPACE,"desc_on");
        mDescOff = attrs.getAttributeValue(NAMESPACE,"desc_off");
        initView();
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    public void initView(){
        View.inflate(getContext(), R.layout.set_item_layout,this);
        tv_title  = (TextView)findViewById(R.id.tv_title);
        tv_desc   = (TextView)findViewById(R.id.tv_desc);
        cb_status = (CheckBox)findViewById(R.id.cb_status);
        settitle(mTitle);
    }
    public void settitle(String title){
        tv_title.setText(title);
    }
    public void setdesc(String desc){
        tv_desc.setText(desc);
    }
    public boolean isCheck(){
        return cb_status.isChecked();
    }
    public void setCheck(boolean check){
        cb_status.setChecked(check);
        if(check)
            setdesc(mDescOn);
        else setdesc(mDescOff);
    }
}
