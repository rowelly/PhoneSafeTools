package com.rowelly.luoli.PhoneSafeTools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.rowelly.luoli.untils.StreamUntils;
import com.rowelly.luoli.user_defined.SettingItemView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by LuoLi on 2016/10/2.
 */

public class SplashActivity extends Activity {

    static final int CODE_UPDATE_DIALOG = 0;
    protected static final int CODE_URL_ERROR = 1;
    protected static final int CODE_NET_ERROR = 2;
    protected static final int CODE_JSON_ERROR = 3;
    protected static final int CODE_ENTER_HOME = 4;
    private static int mVersionCode;
    private static String mVersionName;

    Handler  handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CODE_ENTER_HOME:
                    Enter_Home();
                    break;
                case CODE_JSON_ERROR:
                    Toast.makeText(SplashActivity.this,"数据解析错误",Toast.LENGTH_SHORT).show();
                    Enter_Home();
                    break;
                case CODE_URL_ERROR:
                    Toast.makeText(SplashActivity.this,"URL错误",Toast.LENGTH_SHORT).show();
                    Enter_Home();
                    break;
                case CODE_NET_ERROR:
                    Toast.makeText(SplashActivity.this,"网络错误",Toast.LENGTH_SHORT).show();
                    Enter_Home();
                    break;
                case CODE_UPDATE_DIALOG:
                    Update_Version();
                    break;
            }
        }
    };
    private String des;
    private String downloadUrl;
    private ProgressBar pbh;
    private TextView tv2;

//这是台式机提交的。
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        TextView tv=(TextView)findViewById(R.id.tv1);
        tv2 = (TextView)findViewById(R.id.tv2);
        pbh = (ProgressBar)findViewById(R.id.pbH);
        tv.setText(getVersionName());
        View view=View.inflate(this,R.layout.set_layout,null);
        SettingItemView siv=(SettingItemView) view.findViewById(R.id.siv);
        siv.setCheck(false);
        if(siv.isCheck())
            checkVersion();
        else
            Enter_Home();
        //Update_Version();
    }
    public String getVersionName(){
        String versionName = BuildConfig.VERSION_NAME;
        return ("版本号:"+ versionName);
    }
    public int getVersionCode(){
        int versionCode = BuildConfig.VERSION_CODE;
        //System.out.print("版本号"+versionCode);
        return (versionCode);
    }
    public void checkVersion(){
        final long startTime=System.currentTimeMillis();
        new Thread(){

            @Override
            public void run() {
                Message message=Message.obtain();
                HttpURLConnection coon=null;
                try {
                    URL url=new URL("http://192.168.31.51:8080/update.json");
                    coon = (HttpURLConnection)url.openConnection();
                    coon.setRequestMethod("GET");
                    coon.setReadTimeout(5000);
                    coon.setConnectTimeout(5000);
                    coon.connect();
                    if(coon.getResponseCode()==200){
                        InputStream is= coon.getInputStream();
                        String result= StreamUntils.ReadFromStream(is);
                        JSONObject jsonObject=new JSONObject(result);
                        mVersionName = jsonObject.getString("versionName");
                        mVersionCode = jsonObject.getInt("versionCode");
                        des = jsonObject.getString("description");
                        downloadUrl = jsonObject.getString("downloadUrl");
                        if(mVersionCode >getVersionCode()){
                            message.what=CODE_UPDATE_DIALOG;
                        }else {
                            message.what=CODE_ENTER_HOME;
                        }
                    }
                } catch (MalformedURLException e) {
                    //网址异常报错
                    message.what=CODE_URL_ERROR;
                    System.out.println("你大爷的傻B");
                    e.printStackTrace();
                } catch (IOException e) {
                    //连接异常报错
                    message.what=CODE_NET_ERROR;

                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                    message.what=CODE_JSON_ERROR;
                }finally {
                    long currentTime=System.currentTimeMillis();
                    long useTime=currentTime-startTime;

                    if(useTime<2000){
                        try {
                            Thread.sleep(2000-useTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    handler.sendMessage(message);
                    if(coon!=null)
                        coon.disconnect();
                }

            }
        }.start();
    }
    public void Enter_Home(){
        Intent intent=new Intent(this,Home_Activity.class);
        startActivity(intent);
        finish();
    }
    public void Update_Version(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("有新的更新哦"+mVersionName);
        builder.setMessage(des);
        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                down_apk();
            }
        });
        builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Enter_Home();
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Enter_Home();
            }
        });
        builder.show();
    }
    public void down_apk(){
        //System.out.println("下载升级");
        HttpUtils http=new HttpUtils();
        String target= Environment.getExternalStorageDirectory()+"/temp.apk";
        http.download(downloadUrl, target, new RequestCallBack<File>() {
            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                super.onLoading(total, current, isUploading);
                tv2.setVisibility(View.VISIBLE);
                pbh.setVisibility(View.VISIBLE);
                String progress="下载进度:"+current*100/total+"%";
                tv2.setText(progress);
                pbh.setMax((int)total);
                pbh.setProgress((int)current);
            }

            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setDataAndType(Uri.fromFile(responseInfo.result),"application/vnd.android.package-archive");
                startActivityForResult(intent,RESULT_CANCELED);

            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(SplashActivity.this,"下载失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Enter_Home();
    }
}
