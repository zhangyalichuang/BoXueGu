package boxuegu.com.boxuegu.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import boxuegu.com.boxuegu.R;
import boxuegu.com.boxuegu.utils.MD5Utils;

public class LoginActivity extends AppCompatActivity {
    private  TextView tv_main_title;
    private  TextView tv_back,tv_register,tv_find_psw;
    private  Button btn_login;
    private  String username,psw,spPsw;
    private  EditText et_user_name,et_psw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                init();
    }

    private void init() {
        tv_main_title=(TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("注册");
        tv_back=(TextView) findViewById(R.id.tv_back);
        tv_register=(TextView) findViewById(R.id.tv_register);
        tv_find_psw=(TextView) findViewById(R.id.tv_find_psw);
        btn_login=(Button)findViewById(R.id.btn_login);
        et_user_name=(EditText)findViewById(R.id.et_user_name);
        et_psw=(EditText)findViewById(R.id.et_psw);
        //返回按钮的点击事件
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.finish();
            }
        });
        //立即注册控件的点击事件
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent,1);
            }
        });
        //找回密码的点击事件
        tv_find_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到找回密码界面（此界面暂时为创建）
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=et_user_name.getText().toString().trim();
                String md5Psw= MD5Utils.md5(psw);
                spPsw=readPsw(username);
                if (TextUtils.isEmpty(username)){
                    Toast.makeText(LoginActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                    return;
                }else  if (TextUtils.isEmpty(psw)){
                    Toast.makeText(LoginActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                    return;
                }else if (md5Psw.equals(spPsw)){
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                    //保存登录状态和登录的用户名
                    saveLoginStatus(true,username);
                    //把成功的状态传递到Maintivity中
                    Intent data=new Intent();
                    data.putExtra("isLogin",true);
                    setResult(RESULT_OK,data);
                    LoginActivity.this.finish();
                    return;
                }else  if ((!TextUtils.isEmpty(spPsw)&&!md5Psw.equals(spPsw))){
                    Toast.makeText(LoginActivity.this,"输入的用户名和密码不一致",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Toast.makeText(LoginActivity.this,"此用户名不存在",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    /**
     * 从SharedPreferendces中根据用户名读取密码
     */
    private  String readPsw(String username){
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        return  sp.getString(username,"");
    }
/**
 * 保存登录状态和登录用户名到SharedgetPreences中
 */
private  void  saveLoginStatus(boolean status,String username){
    //loginInfo表示文件名
    SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
    SharedPreferences.Editor editor=sp.edit();
    editor.putBoolean("isLogin" ,status);
    editor.commit();
}
@Override
    protected  void onActivityResult(int resultCode,int ResultCode,Intent data){
    super.onActivityResult(resultCode,resultCode,data);
    if (data!=null){
        //从注册界面传递过来的用户名
        String userName=data.getStringExtra("username");
    if (!TextUtils.isEmpty(userName)){
        et_user_name.setSelection(userName.length());
    }
    }
}
}
