/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.resources;
import com.mycompany.models.Sensor;
import com.mycompany.models.Room;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mskha
 */





@Path("/sensors")
public class SensorResource {

    private static List<Sensor> sensors = new ArrayList<>();
    private static List<Room> rooms = SensorRoomResource.getRoomsStatic();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sensor> getSensors(@QueryParam("type") String type) {
        if (type == null) {
            return sensors;
        }
        List<Sensor> filtered = new ArrayList<>();
        for (Sensor s : sensors) {
            if (s.getType().equalsIgnoreCase(type)) {
                filtered.add(s);
            }
        }
        return filtered;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSensor(Sensor sensor) {
        boolean roomExists = false;
        for (Room r : rooms) {
            if (r.getId().equals(sensor.getRoomId())) {
                roomExists = true;
                r.getSensorIds().add(sensor.getId());
                break;
            }
        }
        if (!roomExists) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Room does not exist\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        sensors.add(sensor);
        return Response.status(Response.Status.CREATED).entity(sensor).build();
    }

    @Path("/{sensorId}/readings")
    public SensorReadingResource getReadings(@PathParam("sensorId") String sensorId) {
        return new SensorReadingResource(sensorId);
    }

    public static List<Sensor> getSensorsStatic() {
        return sensors;
    }
}