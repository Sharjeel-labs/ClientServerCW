/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.resources;
import com.mycompany.models.Sensor;
import com.mycompany.models.SensorReading;
import com.mycompany.exceptions.SensorUnavailableException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;
/**
 *
 * @author mskha
 */


@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource {

    private static Map<String, List<SensorReading>> readings = new HashMap<>();

    private String sensorId;

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    @GET
    public List<SensorReading> getAllReadings() {
        return readings.getOrDefault(sensorId, new ArrayList<>());
    }

    @POST
    public SensorReading addReading(SensorReading reading) {

        Sensor sensor = SensorResource.getSensorsMap().get(sensorId);

        if (sensor.getStatus().equalsIgnoreCase("MAINTENANCE")) {
            throw new SensorUnavailableException("Sensor is under maintenance");
        }

        readings.putIfAbsent(sensorId, new ArrayList<>());
        readings.get(sensorId).add(reading);

        // update current value
        sensor.setCurrentValue(reading.getValue());

        return reading;
    }
}