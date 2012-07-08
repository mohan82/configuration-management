/*
 * Copyright (c) 2012. Mohan Ambalavanan
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 *    Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *  limitations under the License
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creative.dao.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author mohan
 */
@Entity
@Table(name = "environment", catalog = "cm", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name"})})
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Environment.findAll", query = "SELECT e FROM Environment e"),
        @NamedQuery(name = "Environment.findByEnvironmentPk", query = "SELECT e FROM Environment e WHERE e.environmentPk = :environmentPk"),
        @NamedQuery(name = "Environment.findByName", query = "SELECT e FROM Environment e WHERE e.name = :name")})
public class Environment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "environment_pk", nullable = false)
    private Integer environmentPk;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 200)
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "environmentFk")
    private Collection<File> fileCollection;

    public Environment() {
    }


    public Environment(String name) {
        this.name = name;
    }

    public Environment(Integer environmentPk, String name) {
        this.environmentPk = environmentPk;
        this.name = name;
    }

    public Integer getEnvironmentPk() {
        return environmentPk;
    }

    public void setEnvironmentPk(Integer environmentPk) {
        this.environmentPk = environmentPk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public Collection<File> getFileCollection() {
        return fileCollection;
    }

    public void setFileCollection(Collection<File> fileCollection) {
        this.fileCollection = fileCollection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Environment that = (Environment) o;

        if (!environmentPk.equals(that.environmentPk)) return false;
        return name.equals(that.name);

    }

    @Override
    public int hashCode() {
        int result = environmentPk.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Environment{" +
                "name='" + name + '\'' +
                ", environmentPk=" + environmentPk +
                '}';
    }
}
