package com.getjavajob.schekovskiy.list1.task3;

import com.getjavajob.schekovskiy.list1.task3.exception.TemperatureValueException;
import com.getjavajob.schekovskiy.list1.task3.exception.WaterLevelException;
import org.junit.Before;
import org.junit.Test;

import java.util.Timer;
import java.util.TimerTask;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertFalse;

public class KettleImplTest {

    private static final int VOLUME = 2000;
    private static final int POWER = 2000;
    private static final int WATER_MILLILITEERS = 1000;
    private static KettleImpl kettle;

    @Before
    public void setUp() throws WaterLevelException {
        kettle = new KettleImpl(VOLUME, POWER);
        kettle.setTimeSpeed(10);
        kettle.pourWater(WATER_MILLILITEERS);
    }

    @Test
    public void testTurnOn() throws Exception {
        Thread turnOn = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    kettle.turnOn();
                    assertTrue(kettle.isOn());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Test
    public void testShutOff() throws Exception {
        Thread turnOn = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    kettle.turnOn();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        turnOn.start();

        final long timeToShutOff = 5000;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                kettle.shutOff();
                assertFalse(kettle.isOn());
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, timeToShutOff);

    }

    @Test
    public void testPourWater() throws Exception {
        kettle.pourWater(200);
    }

    @Test(expected = WaterLevelException.class)
    public void testToPourWaterOnException1() throws Exception {
        kettle.pourWater(3000);
    }

    @Test
    public void testPourOutWater() throws Exception {
        kettle.pourOutWater(1000);
    }

    @Test(expected = WaterLevelException.class)
    public void testToPourOutWaterOnException() throws Exception {
        kettle.pourOutWater(2000);
    }

    @Test
    public void testWarmWater() throws Exception {
        kettle.warmWater(80);
    }

    @Test(expected = TemperatureValueException.class)
    public void testWarmWaterOnException() throws Exception {
        kettle.warmWater(200);
    }
}
