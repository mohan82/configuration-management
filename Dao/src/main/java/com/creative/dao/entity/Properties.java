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
 *
 * @author mohan
 */
@Entity
@Table(name = "properties", catalog = "cm",uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Properties.findAll", query = "SELECT p FROM Properties p"),
    @NamedQuery(name = "Properties.findByPropertiesPk", query = "SELECT p FROM Properties p WHERE p.propertiesPk = :propertiesPk"),
    @NamedQuery(name = "Properties.findByName", query = "SELECT p FROM Properties p WHERE p.name = :name"),
    @NamedQuery(name = "Properties.findByValue", query = "SELECT p FROM Properties p WHERE p.value = :value")})
public class Properties implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "properties_pk", nullable = false)
    private Integer propertiesPk;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 200)
    private String name;
    @Basic(optional = false)
    @Column(name = "value", nullable = false, length = 200)
    private String value;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "propertiesFk")
    private Collection<PropertiesVersion> propertiesVersionCollection;
    @JoinColumn(name = "file_fk", referencedColumnName = "file_pk", nullable = false)
    @ManyToOne(optional = false)
    private File fileFk;

    public Properties() {
    }

    public Properties(Integer propertiesPk) {
        this.propertiesPk = propertiesPk;
    }

    public Properties(Integer propertiesPk, String name, String value) {
        this.propertiesPk = propertiesPk;
        this.name = name;
        this.value = value;
    }

    public Integer getPropertiesPk() {
        return propertiesPk;
    }

    public void setPropertiesPk(Integer propertiesPk) {
        this.propertiesPk = propertiesPk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @XmlTransient
    public Collection<PropertiesVersion> getPropertiesVersionCollection() {
        return propertiesVersionCollection;
    }

    public void setPropertiesVersionCollection(Collection<PropertiesVersion> propertiesVersionCollection) {
        this.propertiesVersionCollection = propertiesVersionCollection;
    }

    public File getFileFk() {
        return fileFk;
    }

    public void setFileFk(File fileFk) {
        this.fileFk = fileFk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (propertiesPk != null ? propertiesPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Properties)) {
            return false;
        }
        Properties other = (Properties) object;
        return !((this.propertiesPk == null && other.propertiesPk != null) || (this.propertiesPk != null && !this.propertiesPk.equals(other.propertiesPk)));
    }

    @Override
    public String toString() {
        return "com.creative.dao.entity.Properties[ propertiesPk=" + propertiesPk + " ]";
    }
    
}
