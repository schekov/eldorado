package com.getjavajob.schekovskiy.list1.task3;

import com.getjavajob.schekovskiy.list1.task3.exception.TemperatureValueException;
import com.getjavajob.schekovskiy.list1.task3.exception.WaterLevelException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class KettleImpl implements Kettle {

    private static final int DEFAULT_TIME_SPEED = 1000; // 1000 - normal
    private static final int WATER_SPECIFIC_HEAT = 4200;
    private static final int MIN_LIMIT_TEMPERATURE = 1;
    private static final int MAX_LIMIT_TEMPERATURE = 120;
    public static final int DEFAULT_CURRENT_TEMPERATURE = 20;
    public static final int DEFAULT_BOIL_TEMPERATURE = 100;

    private int timeSpeed;
    private int volume;
    private int power;
    private double temperature = DEFAULT_BOIL_TEMPERATURE;
    public double currTemperature = DEFAULT_CURRENT_TEMPERATURE;
    private double tempPerSec;
    private int currWaterAmount;
    private volatile boolean isOn;

    public KettleImpl(int volumeMillis, int powerWatt) {
        power = powerWatt;
        volume = volumeMillis;
        timeSpeed = DEFAULT_TIME_SPEED;
    }

    public boolean isOn() {
        return isOn;
    }

    public int getTimeSpeed() {
        return timeSpeed;
    }

    public void setTimeSpeed(int timeSpeed) {
        this.timeSpeed = timeSpeed;
    }

    public double getCurrTemperature() {
        return currTemperature;
    }

    public void setCurrTemperature(double currTemperature) {
        this.currTemperature = currTemperature;
    }

    @Override
    public void turnOn() throws WaterLevelException, TemperatureValueException {
        if (currWaterAmount == 0) {
            throw new WaterLevelException("is not enough water 0/" + volume);
        }
        isOn = true;
        boil();
    }

    @Override
    public void shutOff() {
        this.isOn = false;
    }

    @Override
    public void pourWater(int millis) throws WaterLevelException {
        int waterAmount = millis + currWaterAmount;
        if (waterAmount > volume) {
            throw new WaterLevelException("can't to pour water more than " + volume + " millis");
        }
        currWaterAmount = waterAmount;
    }

    @Override
    public void pourOutWater(int milliliters) throws WaterLevelException {
        int waterAmount = currWaterAmount - milliliters;
        if ((waterAmount) < 0) {
            throw new WaterLevelException("the amount of water can't be less than zero");
        }
        currWaterAmount = waterAmount;
    }

    @Override
    public void boil() throws TemperatureValueException {
        warmWater(temperature);
    }

    @Override
    public void warmWater(double temperature) throws TemperatureValueException {
        if (temperature > MAX_LIMIT_TEMPERATURE || temperature < MIN_LIMIT_TEMPERATURE) {
            throw new TemperatureValueException("temperature cant be less than " + MIN_LIMIT_TEMPERATURE + " or be more than " + MAX_LIMIT_TEMPERATURE);
        }
        tempPerSec = getTemperaturePerSecond();
        System.out.println("\nEstimated time to boil: " + getTimeToBoil() + " sec");
        System.out.println("Time speed: " + timeSpeed + " per 1000 real milliseconds");
        System.out.println(getCurTime() + ": warming up began");
        while (isOn && currTemperature <= temperature) {
            try {
                TimeUnit.MILLISECONDS.sleep(timeSpeed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String outTemperature = String.format("%.1f", currTemperature);
            System.out.print("\r" + outTemperature + "° ");
            this.currTemperature += tempPerSec;
        }
        System.out.println("\n" + getCurTime() + ": done, water is warmed up");
        this.temperature = temperature;
    }

    private String getCurTime() {
        DateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return format.format(date);
    }

    private double getTemperaturePerSecond() {
        double volumLiters = volume / 1000;
        return power / (volumLiters * WATER_SPECIFIC_HEAT);
    }

    private long getTimeToBoil() {
        return (long) ((temperature - currTemperature) / getTemperaturePerSecond());
    }
}
