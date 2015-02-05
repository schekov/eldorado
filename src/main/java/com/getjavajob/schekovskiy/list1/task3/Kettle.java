package com.getjavajob.schekovskiy.list1.task3;

import com.getjavajob.schekovskiy.list1.task3.exception.TemperatureValueException;
import com.getjavajob.schekovskiy.list1.task3.exception.WaterLevelException;

public interface Kettle {

    void turnOn() throws WaterLevelException, TemperatureValueException;

    void shutOff();

    void pourWater(int milliliters) throws WaterLevelException;

    void pourOutWater(int milliliters) throws WaterLevelException;

    void boil() throws WaterLevelException, TemperatureValueException;

    void warmWater(double temperature) throws WaterLevelException, TemperatureValueException;
}
