package com.example.intermediates;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.intermediates.DB.SQLFunction;
import com.example.intermediates.bean.Info;
import com.example.intermediates.bean.MyObject;
import com.google.gson.Gson;
import com.google.zxing.activity.CaptureActivity;
import com.google.zxing.util.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    SQLFunction sqlFunction=new SQLFunction();
    List<Info> lists=new ArrayList<Info>();
    MyListAdapter myListAdapter=new MyListAdapter();
    ListView lv;
     WebView webView ;
    Button debugbtn;
    Button sparebtn;
    Button settingbtn;
    LinearLayout webviewlayout;
    LinearLayout btnlayout;
    LinearLayout linelayout;
    LinearLayout infos;

    LinearLayout sparelayout;
    LinearLayout settinglayout;
    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);
         /*
        初始化数据库
         */
        SQLFunction.initTable(MainActivity.this);

       lv=findViewById(R.id.listview);
       select();
       if(lists!=null){
           lv.setAdapter(myListAdapter);
       }

        //页面控制
         debugbtn=findViewById(R.id.debug);
        sparebtn=findViewById(R.id.spare);
        settingbtn=findViewById(R.id.setting);

         webviewlayout=findViewById(R.id.webviewlayout);
         btnlayout=findViewById(R.id.btnlayout);
         linelayout=findViewById(R.id.linelayout);
         infos=findViewById(R.id.infos);

        sparelayout=findViewById(R.id.sparelayout);
        settinglayout=findViewById(R.id.settinglayout);
       sparelayout.setVisibility(View.GONE);
       settinglayout.setVisibility(View.GONE);
       debugbtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               debugVisible();
               sparelayout.setVisibility(View.GONE);
               settinglayout.setVisibility(View.GONE);
           }
       });
       sparebtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               debugGone();
               sparelayout.setVisibility(View.VISIBLE);
               settinglayout.setVisibility(View.GONE);
           }
       });
       settingbtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               debugGone();
               sparelayout.setVisibility(View.GONE);
               settinglayout.setVisibility(View.VISIBLE);
           }
       });


        //网页
        //获得控件
        webView = (WebView) findViewById(R.id.wv_webview);
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.requestFocus();
        //设置本地调用对象及其接口
        //webView.addJavascriptInterface(new JavascriptObject(),"myObj");
        //访问网页
        webView.loadUrl("file:///android_asset/web/main.html");
        //addJavascriptInterface这个方法中有两个参数，第一个参数是添加一个对象，这个对象中封装了在js中要调用的native方法，第二个参数是告诉js对象的名称以便于调用native方法
       // webView.addJavascriptInterface(new MyObject(MainActivity.this,"dd"),"fdd");
        webView.addJavascriptInterface(new MyObject(MainActivity.this,"dd"),"fddd");

        webView.addJavascriptInterface(this,"fdd");
        //系统默认会通过手机浏览器打开网页，为了能够直接通过WebView显示网页，则必须设置
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                //使用WebView加载显示url
                view.loadUrl(url);
                //返回true
                return true;
            }
        });

        Button addBtn=findViewById(R.id.add);
        Button selectBtn=findViewById(R.id.select);
        Button webBtn=findViewById(R.id.webview);
        Button testBtn=findViewById(R.id.test);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //insert();
                select();
                lv.setAdapter(myListAdapter);

            }
        });
       selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lists=SQLFunction.select(MainActivity.this);
                lv.setAdapter(myListAdapter);
            }
        });
       webBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
               startActivity(intent);
           }
       });

       //android操作js

       testBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //webView.addJavascriptInterface(new MyObject(MainActivity.this,"dd"),"fddd");
               //两种方法
               webView.loadUrl("javascript:showlist()");
           }
       });



    }
    //隐藏调试页面
    public void debugGone(){
        webviewlayout.setVisibility(View.GONE);
        btnlayout.setVisibility(View.GONE);
        linelayout.setVisibility(View.GONE);
        infos.setVisibility(View.GONE);
    }
    //显示调试页面
    public void debugVisible(){
        webviewlayout.setVisibility(View.VISIBLE);
        btnlayout.setVisibility(View.VISIBLE);
        linelayout.setVisibility(View.VISIBLE);
        infos.setVisibility(View.VISIBLE);
    }
    @JavascriptInterface
    public void  startcamera(){
        Log.d("wo bei dian","dao l ");
        startQrCode();
    }
    @JavascriptInterface//android 4.2之上的版本需要加上这个注解，表示将这个方法暴露给js调用
    public void addInfo(String str){
        Log.d("MainActivity","---方法被执行了！！！-"+str);
        try {
            JSONObject jsonObject=new JSONObject(str);
            Info info=new Info();
            info.setName(jsonObject.get("name").toString());
            info.setDescribe(jsonObject.get("describe").toString());
            insert(info);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    @JavascriptInterface
    public void deleteInfo(String deletename){
        Log.d("删除数据名称",deletename);
        Info info=new Info();
        info.setName(deletename);
        delete(info);

    }
    @JavascriptInterface
    public void jscommit(String strs){
        Log.d("我是js传来的数据",strs);
        List<Info> infoList=new ArrayList<>();
        try {
            JSONArray jsonArray=new JSONArray(strs);
            for (int i=0;i<jsonArray.length();i++){
                Info info=new Info();
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                info.setName(jsonObject.get("name").toString());
                info.setDescribe(jsonObject.get("describe").toString());
                infoList.add(info);
                Log.d("jsonobject",jsonObject.get("name").toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
            for(int i=0;i<infoList.size();i++){
                update(infoList.get(i));
            }

    }
    /**
     * 获取info字符串传Html
     * @return
     */
    @JavascriptInterface
    public String getData(){
        List<Info> mlist = new ArrayList<>();
        mlist=sqlFunction.select(MainActivity.this);
        Gson gson = new Gson();
        String d  = gson.toJson(mlist);
        return d;
    }
    public void insert(Info info){
        sqlFunction.insert(MainActivity.this,info);
    }
    public void delete(Info info){
        sqlFunction.delete(MainActivity.this,info);
    }
    public void update(Info info){
        sqlFunction.update(MainActivity.this,info);
    }
    public List<Info> select(){
        lists= sqlFunction.select(MainActivity.this);
        return  lists;
    }

    //适配器
    private class MyListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            int count = lists.size(); //条目个数=集合的size
            return count;
        }

        @Override
        public Object getItem(int position) {
            return lists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        //getView控制每个条目显示的内容,依据position来控制，传进来的位置是什么就把此位置的view对象给他
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            //得到某个位置对应的person对象
            final Info info = lists.get(position);
            final Info inf = new Info();
            inf.setName(info.getName());
            inf.setDescribe(info.getDescribe());
            View view = View.inflate(MainActivity.this, R.layout.adapter, null);
            holder = new ViewHolder();

            holder.nameText=view.findViewById(R.id.name);
            holder.describeText=view.findViewById(R.id.describe);
            holder.delete=view.findViewById(R.id.adapterdelete);
            holder.update=view.findViewById(R.id.adapterupdate);


            view.setTag(holder);
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Info info1=new Info();
                    info1.setName(info.getName());
                    sqlFunction.delete(MainActivity.this,info1);
                    select();
                    lv.setAdapter(myListAdapter);
                }
            });
            holder.update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Info info1=new Info();

                    select();
                    lv.setAdapter(myListAdapter);
                }
            });
                holder.nameText.setText("姓名:"+info.getName());
                holder.describeText.setText("描述:"+info.getDescribe());

            return view;
        }
    }
    private class ViewHolder {
        Button delete;
        Button update;
        TextView nameText;
        TextView describeText;

    }



    // 开始扫码
    private void startQrCode() {
        // 申请相机权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // 申请权限
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, Constant.REQ_PERM_CAMERA);
            return;
        }
        // 申请文件读写权限（部分朋友遇到相册选图需要读写权限的情况，这里一并写一下）
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 申请权限
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constant.REQ_PERM_EXTERNAL_STORAGE);
            return;
        }
        // 二维码扫码
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
        startActivityForResult(intent, Constant.REQ_QR_CODE);
    }

    //扫码回调结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //扫描结果回调
        if (requestCode == Constant.REQ_QR_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            final String scanResult = bundle.getString(Constant.INTENT_EXTRA_KEY_QR_SCAN);
                Log.d("我是扫描二维码的信息",scanResult);
            webView.loadUrl("javascript:showqrcode('"+scanResult+"')");
        }
    }

    //扫码权限设置
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constant.REQ_PERM_CAMERA:
                // 摄像头权限申请
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获得授权
                    startQrCode();
                } else {
                    // 被禁止授权
                    Toast.makeText(MainActivity.this, "请至权限中心打开本应用的相机访问权限", Toast.LENGTH_LONG).show();
                }
                break;
            case Constant.REQ_PERM_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 文件读写权限申请
                    // 获得授权
                    startQrCode();
                } else {
                    // 被禁止授权
                    Toast.makeText(MainActivity.this, "请至权限中心打开本应用的文件读写权限", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


}