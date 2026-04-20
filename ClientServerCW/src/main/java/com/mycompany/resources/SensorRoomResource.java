/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package com.mycompany.resources;

import com.mycompany.exceptions.RoomNotEmptyException;
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


@Path("/rooms")
public class SensorRoomResource {

    private static List<Room> rooms = new ArrayList<>();

    static {
        rooms.add(new Room("LIB-301", "Library Room", 50));
        rooms.add(new Room("LAB-101", "Computer Lab", 30));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Room> getRooms() {
        return rooms;
    }

    @GET
    @Path("/{roomId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoomById(@PathParam("roomId") String roomId) {

        for (Room r : rooms) {
            if (r.getId().equals(roomId)) {
                return Response.ok(r).build();
            }
        }

        return Response.status(Response.Status.NOT_FOUND)
                .entity("Room not found")
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addRoom(Room room) {
        rooms.add(room);
        return Response.status(Response.Status.CREATED).entity(room).build();
    }

    @DELETE
    @Path("/{roomId}")
    public Response deleteRoom(@PathParam("roomId") String roomId) {

        for (Room r : rooms) {
            if (r.getId().equals(roomId)) {

                // correct place to throw exception
                if (!r.getSensorIds().isEmpty()) {
                    throw new RoomNotEmptyException("Room has sensors, cannot delete");
                }

                rooms.remove(r);
                return Response.ok("Room deleted").build();
            }
        }

        // correct behaviour if room not found
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Room not found")
                .build();
    }

    public static List<Room> getRoomsStatic() {
        return rooms;
    }
}