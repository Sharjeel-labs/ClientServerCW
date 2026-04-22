/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.clientservercw.resources;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author mskha
 */



@ApplicationPath("/api/v1")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {

        Set<Class<?>> resources = new HashSet<>();

        resources.add(com.mycompany.resources.SensorRoomResource.class);
        resources.add(com.mycompany.resources.SensorResource.class);
        resources.add(com.mycompany.resources.DiscoveryResource.class);
        resources.add(com.mycompany.mappers.RoomNotEmptyExceptionMapper.class);
        resources.add(com.mycompany.mappers.SensorUnavailableExceptionMapper.class);
        resources.add(com.mycompany.mappers.LinkedResourceNotFoundExceptionMapper.class);
        resources.add(org.glassfish.jersey.jackson.JacksonFeature.class);

        return resources;
    }
}