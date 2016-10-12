package com.rowelly.luoli.PhoneSafeTools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.rowelly.luoli.user_defined.AutoScrollTextView;

import static com.rowelly.luoli.PhoneSafeTools.R.id.textView;

public class Home_Activity extends Activity {
    private GridView gv_home;
    private String[] mitems=new String[]{"手机防盗", "通讯卫士","软件管理", "进程管理",
            "流量统计","手机杀毒", "缓存清理", "高级工具", "设置中心"};
    private int[] mpics=new int[]{R.mipmap.home_safe,R.mipmap.home_callmsgsafe,
                        R.mipmap.home_apps,R.mipmap.home_taskmanager,
                        R.mipmap.home_netmanager, R.mipmap.home_trojan,
                        R.mipmap.home_sysoptimize,R.mipmap.home_tools,
                        R.mipmap.home_settings};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        gv_home=(GridView)findViewById(R.id.gv_home);
        gv_home.setAdapter(new HomeAdapter());
        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 8:
                        startActivity(new Intent(Home_Activity.this,SetItemActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });
    }
    class HomeAdapter extends BaseAdapter{
         @Override
         public int getCount() {
             return mitems.length;
         }

         @Override
         public Object getItem(int position) {
             return mitems[position];
         }

         @Override
         public long getItemId(int position) {
             return position;
         }

         @Override
         public View getView(int position, View convertView, ViewGroup parent) {
             View view =View.inflate(Home_Activity.this, R.layout.home_list_item, null);
             ImageView imageView = (ImageView) view.findViewById(R.id.list_iv);
             TextView textView=(TextView) view.findViewById(R.id.list_tv);
             imageView.setImageResource(mpics[position]);
             textView.setText(mitems[position]);
             return view;
         }
     }

}
