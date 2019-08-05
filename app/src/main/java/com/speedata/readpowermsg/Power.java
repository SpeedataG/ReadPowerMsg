package com.speedata.readpowermsg;

import com.speedata.libutils.excel.Excel;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 张明_ on 2018/5/2.
 * Email 741183142@qq.com
 */
@Entity
public class Power {

    @Excel(ignore = false, name = "收集时间")
    @Property(nameInDb = "collectTime")
    @Id(autoincrement = false)
    String collectTime;

    @Excel(ignore = false, name = "Level")
    @Property(nameInDb = "Level")
    int level;

    @Excel(ignore = false, name = "Voltage")
    @Property(nameInDb = "Voltage")
    int voltage;


    @Excel(ignore = false, name = "Electric")
    @Property(nameInDb = "Electric")
    String electric;

    @Excel(ignore = false, name = "AverageElectric")
    @Property(nameInDb = "AverageElectric")
    String averageElectric;

    @Generated(hash = 1574276400)
    public Power(String collectTime, int level, int voltage, String electric,
            String averageElectric) {
        this.collectTime = collectTime;
        this.level = level;
        this.voltage = voltage;
        this.electric = electric;
        this.averageElectric = averageElectric;
    }

    @Generated(hash = 1900443045)
    public Power() {
    }

    public String getCollectTime() {
        return this.collectTime;
    }

    public void setCollectTime(String collectTime) {
        this.collectTime = collectTime;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getVoltage() {
        return this.voltage;
    }

    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }

    public String getElectric() {
        return this.electric;
    }

    public void setElectric(String electric) {
        this.electric = electric;
    }

    public String getAverageElectric() {
        return this.averageElectric;
    }

    public void setAverageElectric(String averageElectric) {
        this.averageElectric = averageElectric;
    }

   




}
