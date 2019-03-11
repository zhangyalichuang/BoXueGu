package boxuegu.com.boxuegu.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import boxuegu.com.boxuegu.R;

public class SplashActivity extends AppCompatActivity {
        private TextView tv_version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //设此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }

    private void init() {
        tv_version=(TextView)findViewById(R.id.tv_version);
        //获取程序包信息
        try {
            PackageInfo info=getPackageManager().getPackageInfo(getPackageName(),0);
            tv_version.setText("V"+info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        tv_version.setText("V");
        }
        //让此界面延迟3秒在跳转，Timer中有一个线程，这个线程不断执行task
        Timer timer=new Timer();
        //TimeTask类表示在一个指定时间内执行的task
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        };
        timer.schedule(task,3000);//设置这个TASK在延迟3秒后跳转
    }
}
