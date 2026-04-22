/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.resources;
import com.mycompany.models.Sensor;
import com.mycompany.models.Room;
import com.mycompany.models.SensorReading;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mskha
 */



public class SensorReadingResource {

    private static Map<String, List<SensorReading>> readingsMap = new HashMap<>();
    private String sensorId;

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<SensorReading> getReadings() {
        return readingsMap.getOrDefault(sensorId, new ArrayList<>());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReading(SensorReading reading) {
        readingsMap.putIfAbsent(sensorId, new ArrayList<>());
        readingsMap.get(sensorId).add(reading);
        for (Sensor s : SensorResource.getSensorsStatic()) {
            if (s.getId().equals(sensorId)) {
                s.setCurrentValue(reading.getValue());
            }
        }
        return Response.status(Response.Status.CREATED).entity(reading).build();
    }
}