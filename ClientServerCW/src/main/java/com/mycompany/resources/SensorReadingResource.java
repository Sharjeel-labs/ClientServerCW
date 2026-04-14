/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.resources;
import com.mycompany.models.Sensor;
import com.mycompany.models.SensorReading;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 *
 * @author mskha
 */



public class SensorReadingResource {

    private String sensorId;
    private Map<String, Sensor> sensors;

    private static Map<String, List<SensorReading>> readings = new HashMap<>();

    public SensorReadingResource(String sensorId, Map<String, Sensor> sensors) {
        this.sensorId = sensorId;
        this.sensors = sensors;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<SensorReading> getReadings() {
        return readings.getOrDefault(sensorId, new ArrayList<>());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addReading(SensorReading reading) {

        readings.putIfAbsent(sensorId, new ArrayList<>());
        readings.get(sensorId).add(reading);

        // update sensor current value (important for marks)
        Sensor sensor = sensors.get(sensorId);
        if (sensor != null) {
            sensor.setCurrentValue(reading.getValue());
        }
    }
}