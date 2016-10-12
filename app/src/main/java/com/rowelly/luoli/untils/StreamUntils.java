package com.rowelly.luoli.untils;

import android.os.Message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by LuoLi on 2016/10/3.
 */

public class StreamUntils {

    public static String ReadFromStream(InputStream inputStream)throws IOException{
        ByteArrayOutputStream os=new ByteArrayOutputStream();
        int len;
        byte[] buffer=new byte[1024];

            while ((len=inputStream.read(buffer))!=-1){
                os.write(buffer,0,len);
            }
        String out=os.toString();
            os.close();
            inputStream.close();
        return out;
    }
}
