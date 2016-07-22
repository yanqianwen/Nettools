package com.qianfeng.nettools;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;



/**
 * Created by Administrator on 2016/3/29.
 */
public class MyApp extends Application {
    private static  String  SHARF_NAME="myapp";
    private static String Islogin_KEY="islogin";
    private static Context context;

    public static  boolean isFirstlogin(){
        SharedPreferences sharf=context.getSharedPreferences(SHARF_NAME, Context.MODE_PRIVATE);
        boolean islogin=sharf.getBoolean(Islogin_KEY,true);
        if(islogin){
            sharf.edit().putBoolean(Islogin_KEY,false).apply();
        }
        return  islogin;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
    }
    public  static Context getContext(){
        return  context;
    }
}
