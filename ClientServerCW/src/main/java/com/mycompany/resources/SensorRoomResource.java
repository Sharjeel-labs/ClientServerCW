/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.resources;
import com.mycompany.models.Room;
import com.mycompany.models.Sensor;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 *
 * @author mskha
 */

@Path("/rooms")
public class SensorRoomResource {

    private static Map<String, Room> rooms = new HashMap<>();
    private static Map<String, Sensor> sensors = new HashMap<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Room> getAllRooms() {
        return rooms.values();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addRoom(Room room) {
        rooms.put(room.getId(), room);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Room getRoom(@PathParam("id") String id) {
        return rooms.get(id);
    }

    @DELETE
    @Path("/{id}")
    public void deleteRoom(@PathParam("id") String id) {

        // simple check if sensors exist in room
        for (Sensor s : sensors.values()) {
            if (id.equals(s.getRoomId())) {
                throw new RuntimeException("Room has sensors, cannot delete");
            }
        }

        rooms.remove(id);
    }
}