package com.speedata.readpowermsg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.speedata.libutils.excel.ExcelUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jxl.write.Colour;

public class MainActivity extends AppCompatActivity {
    private BatteryReceiver batteryReceiver;
    private Button mButton;
    private TextView mTv;
    private long saveTime = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        batteryReceiver = new BatteryReceiver();
        registerReceiver(batteryReceiver, intentFilter);

        initView();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(batteryReceiver);
    }

    private void initView() {
        mButton = findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<Power> powers = MyApp.getDaoInstant().getPowerDao().loadAll();
                        if (powers.size() > 0) {
                            ExcelUtils.getInstance()
                                    .setSHEET_NAME("PowerMsg")//设置表格名称
                                    .setFONT_COLOR(Colour.BLUE)//设置标题字体颜色
                                    .setFONT_TIMES(8)//设置标题字体大小
                                    .setFONT_BOLD(true)//设置标题字体是否斜体
                                    .setBACKGROND_COLOR(Colour.GRAY_25)//设置标题背景颜色
                                    .setContent_list_Strings(powers)//设置excel内容
                                    .setWirteExcelPath(Environment.getExternalStorageDirectory() + File.separator + "PowerMsg.xls")
                                    .createExcel(MainActivity.this);

                            handler.sendMessage(handler.obtainMessage(2));
                        }

                    }
                }).start();
            }
        });
        mTv = findViewById(R.id.tv);
    }

    class BatteryReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            //判断它是否是为电量变化的Broadcast Action
            if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                try {
                    //获取当前电量
                    int level = intent.getIntExtra("level", 0);
                    int voltage = intent.getIntExtra("voltage", 0);

//                    if (System.currentTimeMillis() - saveTime > 600000 ) {
                    Power power = new Power();
                    power.setCollectTime(getCurrentTimeMillis("yyyy-MM-dd HH:mm:ss"));
                    power.setLevel(level);
                    power.setVoltage(voltage);
                    power.setElectric(readNode(Dianliu));
                    power.setAverageElectric(readNode(Dianliu2));
                    MyApp.getDaoInstant().getPowerDao().insert(power);
//                    saveTime = System.currentTimeMillis();
                    handler.sendMessage(handler.obtainMessage(1));
//                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private String Dianliu = "/sys/class/misc/max17205/hardKeyboard";
    private String Dianliu2 = "/sys/class/misc/max17205/hardKeyboard1";


    private String readNode(String path) {
        try {
            BufferedReader CtrlReaderFile = new BufferedReader(new FileReader(
                    path));
            String buffer = CtrlReaderFile.readLine();
            // a=Integer.parseInt(buffer);
            CtrlReaderFile.close();
            return buffer;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return e.getMessage();
        }

    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch (what) {
                case 1:
                    List<Power> powers = MyApp.getDaoInstant().getPowerDao().loadAll();
                    mTv.setText("目前数据总数：" + powers.size());
                    break;
                case 2:
                    Toast.makeText(MainActivity.this, "生成完成", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };


    public static String getCurrentTimeMillis(String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        long currentTimeMillis = System.currentTimeMillis();
        Date date = new Date(currentTimeMillis);
        return dateFormat.format(date);
    }
}
