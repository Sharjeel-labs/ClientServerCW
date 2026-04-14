/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.resources;
import com.mycompany.models.Sensor;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;
/**
 *
 * @author mskha
 */


@Path("/sensors")
public class SensorResource {

    private static Map<String, Sensor> sensors = new HashMap<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Sensor> getSensors(@QueryParam("type") String type) {

        if (type == null) {
            return sensors.values();
        }

        List<Sensor> filtered = new ArrayList<>();

        for (Sensor s : sensors.values()) {
            if (s.getType() != null && s.getType().equalsIgnoreCase(type)) {
                filtered.add(s);
            }
        }

        return filtered;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addSensor(Sensor sensor) {
        sensors.put(sensor.getId(), sensor);
    }

    // SUB-RESOURCE LOCATOR
    @Path("/{id}/readings")
    public SensorReadingResource getReadingResource(@PathParam("id") String id) {
        return new SensorReadingResource(id, sensors);
    }
}