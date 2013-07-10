/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.usersrs.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.jboss.as.quickstarts.usersrs.model.User;
import org.jboss.as.quickstarts.usersrs.model.UserDao;



/**
 * A JAX-RS resource for exposing REST endpoints for User manipulation
 */
@Path("/")
public class UserResource {

    @Inject
    private UserDao userDao;

    @POST
    @Path("users")
    public void createUser(@FormParam("fname") String fname, @FormParam("lname") String lname, @FormParam("id") Long id) {
    	User user = new User(fname, lname, id);
    	System.out.println("fname, lname, id:" +fname +" " +lname +" "+ id);
        userDao.createUser(user);
    }

    @DELETE
    @Path("users/{id}")
    public void deleteUserById(@PathParam("id") Long id) {
        User user = getUserById(id);

        userDao.deleteUser(user);
    }

    @GET
    @Path("users/{id}")
    // JSON: include "application/json" in the @Produces annotation to include json support
    @Produces({ "application/json" })
    //@Produces({ "application/xml" })
    public User getUserById(@PathParam("id") Long id) {
        return getUser(id);
    }


/*    @GET
    @Path("users")
    // JSON: include "application/json" in the @Produces annotation to include json support
    @Produces({ "application/json" })
    //@Produces({ "application/xml" })
    public List<User> getUsers() {
        return userDao.getAll();
    }*/

    @GET
    @Path("users")
    // JSON: include "application/json" in the @Produces annotation to include json support
    @Produces({ "application/json" })
    //@Produces({ "application/xml" })
    public Map<String, List<User>> getUsersMap() {
    	Map<String, List<User>> result = new HashMap<String, List<User>>();
    	result.put("users", userDao.getAll());
        return result;
    }

    // Utility Methods


    private User getUser(Long id) {
        for (User user : userDao.getAll())
            if (user.getId().equals(id))
                return user;

        throw new WebApplicationException(Response.Status.NOT_FOUND);
    }

}
