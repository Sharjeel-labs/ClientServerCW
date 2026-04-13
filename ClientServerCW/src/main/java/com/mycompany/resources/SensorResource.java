/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.resources;
import com.mycompany.models.Sensor;
import com.mycompany.exceptions.LinkedResourceNotFoundException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;
/**
 *
 * @author mskha
 */



@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {

    private static Map<String, Sensor> sensors = new HashMap<>();

    @GET
    public Collection<Sensor> getSensors(@QueryParam("type") String type) {

        if (type == null) {
            return sensors.values();
        }

        List<Sensor> result = new ArrayList<>();

        for (Sensor s : sensors.values()) {
            if (s.getType().equalsIgnoreCase(type)) {
                result.add(s);
            }
        }

        return result;
    }

    @POST
    public Sensor addSensor(Sensor sensor) {

        // check if room exists
        if (!SensorRoomResource.getRoomsMap().containsKey(sensor.getRoomId())) {
            throw new LinkedResourceNotFoundException("Room ID not found");
        }

        sensors.put(sensor.getId(), sensor);

        // add sensor to room
        SensorRoomResource.getRoomsMap()
                .get(sensor.getRoomId())
                .getSensorIds()
                .add(sensor.getId());

        return sensor;
    }

    @GET
    @Path("/{id}")
    public Sensor getSensor(@PathParam("id") String id) {
        return sensors.get(id);
    }

    // sub-resource
    @Path("/{id}/readings")
    public SensorReadingResource getReadings(@PathParam("id") String id) {
        return new SensorReadingResource(id);
    }

    // helper
    public static Map<String, Sensor> getSensorsMap() {
        return sensors;
    }
}