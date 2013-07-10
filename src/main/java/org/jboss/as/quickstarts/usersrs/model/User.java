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

// JSON: uncomment to include json support (note json is not part of the JAX-RS standard)

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.io.StringReader;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * User's task entity which is marked up with JPA annotations and JAXB for serializing XML
 * (and JSON if required)
 *
 * @author Oliver Kiss and others
 */
@Entity
@XmlRootElement(name = "user")
@Table(name="USER_")
public class User implements Serializable {

    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int key;
    private Long id;
    private String fname;
    private String lname;

    public User() {
    }

    public User(String firstName, String lastName, Long id) {
        //super();
        this.fname = firstName;
        this.lname = lastName;
        this.id = id;
    }
    

	@Column(name = "FIRSTNAME") 
	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	@Column(name = "LASTNAME") 
	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	@Column(name = "ID") 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	
    public static User stringToTask(String content) {
        return JAXB.unmarshal(new StringReader(content), User.class);
    }


}
