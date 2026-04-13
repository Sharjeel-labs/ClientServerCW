/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.resources;
import com.mycompany.models.Room;
import com.mycompany.exceptions.RoomNotEmptyException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;
/**
 *
 * @author mskha
 */


@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorRoomResource {

    private static Map<String, Room> rooms = new HashMap<>();

    @GET
    public Collection<Room> getRooms() {
        return rooms.values();
    }

    @POST
    public Room addRoom(Room room) {
        rooms.put(room.getId(), room);
        return room;
    }

    @GET
    @Path("/{id}")
    public Room getRoom(@PathParam("id") String id) {
        return rooms.get(id);
    }

    @DELETE
    @Path("/{id}")
    public void deleteRoom(@PathParam("id") String id) {

        Room room = rooms.get(id);

        if (room != null && !room.getSensorIds().isEmpty()) {
            throw new RoomNotEmptyException("Room cannot be deleted, sensors still inside");
        }

        rooms.remove(id);
    }

    // helper (used by SensorResource)
    public static Map<String, Room> getRoomsMap() {
        return rooms;
    }
}