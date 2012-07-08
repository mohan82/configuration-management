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

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;

/**
 * @author mohan
 */
@Entity
@Table(name = "file", catalog = "cm", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name"})})
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "File.findAll", query = "SELECT f FROM File f"),
        @NamedQuery(name = "File.findByFilePk", query = "SELECT f FROM File f WHERE f.filePk = :filePk"),
        @NamedQuery(name = "File.findByName", query = "SELECT f FROM File f WHERE f.name = :name")})
public class File implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "file_pk", nullable = false)
    private Integer filePk;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 200)
    private String name;
    @JoinColumn(name = "environment_fk", referencedColumnName = "environment_pk", nullable = false)
    @ManyToOne(optional = false)
    private Environment environmentFk;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fileFk")
    private Collection<Properties> propertiesCollection;

    public File() {
    }

    public File(String name) {
        this.name = name;
    }

    public File(Integer filePk, String name) {
        this.filePk = filePk;
        this.name = name;
    }

    public Integer getFilePk() {
        return filePk;
    }

    public void setFilePk(Integer filePk) {
        this.filePk = filePk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Environment getEnvironmentFk() {
        return environmentFk;
    }

    public void setEnvironmentFk(Environment environmentFk) {
        this.environmentFk = environmentFk;
    }

    @XmlTransient
    public Collection<Properties> getPropertiesCollection() {
        return propertiesCollection;
    }

    public void setPropertiesCollection(Collection<Properties> propertiesCollection) {
        this.propertiesCollection = propertiesCollection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        File file = (File) o;

        if (environmentFk != null ? !environmentFk.equals(file.environmentFk) : file.environmentFk != null)
            return false;
        if (name != null ? !name.equals(file.name) : file.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (environmentFk != null ? environmentFk.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "com.creative.dao.entity.File[ filePk=" + filePk + " ]";
    }

}
