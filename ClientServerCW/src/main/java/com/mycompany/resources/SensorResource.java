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

    // simple in-memory list
    private static List<Sensor> sensors = new ArrayList<>();

    // reuse rooms list from Room resource (simple approach)
    private static List<Room> rooms = SensorRoomResource.getRoomsStatic();

    // GET all sensors (with optional filter)
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

    // POST create sensor
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addSensor(Sensor sensor) {

        // check if room exists
        boolean roomExists = false;

        for (Room r : rooms) {
            if (r.getId().equals(sensor.getRoomId())) {
                roomExists = true;

                // link sensor to room
                r.getSensorIds().add(sensor.getId());
                break;
            }
        }

        if (!roomExists) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Room does not exist")
                    .build();
        }

        sensors.add(sensor);

        return Response.status(Response.Status.CREATED)
                .entity(sensor)
                .build();
    }
}