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
package org.jboss.as.quickstarts.usersrs.model;

import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Provides functionality for manipulation with tasks using the persistence context from {@link Resources}.
 *
 * @author Lukas Fryc
 * @author Oliver Kiss
 *
 */
@Stateful
public class UserDaoImpl implements UserDao {

    @Inject
    private EntityManager em;

    @Override
    public void createUser(User user) {
    	boolean ok = false;
        for (User allUser : getAll())
            if (allUser.getId().equals(user.getId()))
            	ok = true;
        if (!ok)
        	em.persist(user);
    }

    @Override
    public List<User> getAll() {
        TypedQuery<User> query = querySelectAllUsersFromUser();
        return query.getResultList();
    }

    @Override
    public void deleteUser(User user) {
        if (!em.contains(user)) {
            user = em.merge(user);
        }
        em.remove(user);
    }

    private TypedQuery<User> querySelectAllUsersFromUser() {
    	return em.createQuery("SELECT t FROM User t", User.class);
    }
}