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
@Table(name = "properties_version", catalog = "cm")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "PropertiesVersion.findAll", query = "SELECT p FROM PropertiesVersion p"),
        @NamedQuery(name = "PropertiesVersion.findByPropertiesVersionPk", query = "SELECT p FROM PropertiesVersion p WHERE p.propertiesVersionPk = :propertiesVersionPk"),
        @NamedQuery(name = "PropertiesVersion.findByName", query = "SELECT p FROM PropertiesVersion p WHERE p.name = :name"),
        @NamedQuery(name = "PropertiesVersion.findByOldValue", query = "SELECT p FROM PropertiesVersion p WHERE p.oldValue = :oldValue"),
        @NamedQuery(name = "PropertiesVersion.findByNewValue", query = "SELECT p FROM PropertiesVersion p WHERE p.newValue = :newValue")})
public class PropertiesVersion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "properties_version_pk", nullable = false)
    private Integer propertiesVersionPk;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 200)
    private String name;
    @Basic(optional = false)
    @Column(name = "old_value", nullable = false, length = 200)
    private String oldValue;
    @Basic(optional = false)
    @Column(name = "new_value", nullable = false, length = 200)
    private String newValue;
    @OneToMany(mappedBy = "parentVersion")
    private Collection<PropertiesVersion> propertiesVersionCollection;
    @JoinColumn(name = "parent_version", referencedColumnName = "properties_version_pk")
    @ManyToOne
    private PropertiesVersion parentVersion;
    @JoinColumn(name = "properties_fk", referencedColumnName = "properties_pk", nullable = false)
    @ManyToOne(optional = false)
    private Properties propertiesFk;

    public PropertiesVersion() {
    }

    public PropertiesVersion(Integer propertiesVersionPk) {
        this.propertiesVersionPk = propertiesVersionPk;
    }

    public PropertiesVersion(Integer propertiesVersionPk, String name, String oldValue, String newValue) {
        this.propertiesVersionPk = propertiesVersionPk;
        this.name = name;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public Integer getPropertiesVersionPk() {
        return propertiesVersionPk;
    }

    public void setPropertiesVersionPk(Integer propertiesVersionPk) {
        this.propertiesVersionPk = propertiesVersionPk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    @XmlTransient
    public Collection<PropertiesVersion> getPropertiesVersionCollection() {
        return propertiesVersionCollection;
    }

    public void setPropertiesVersionCollection(Collection<PropertiesVersion> propertiesVersionCollection) {
        this.propertiesVersionCollection = propertiesVersionCollection;
    }

    public PropertiesVersion getParentVersion() {
        return parentVersion;
    }

    public void setParentVersion(PropertiesVersion parentVersion) {
        this.parentVersion = parentVersion;
    }

    public Properties getPropertiesFk() {
        return propertiesFk;
    }

    public void setPropertiesFk(Properties propertiesFk) {
        this.propertiesFk = propertiesFk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PropertiesVersion that = (PropertiesVersion) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (propertiesFk != null ? !propertiesFk.equals(that.propertiesFk) : that.propertiesFk != null) return false;
        if (propertiesVersionPk != null ? !propertiesVersionPk.equals(that.propertiesVersionPk) : that.propertiesVersionPk != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = propertiesVersionPk != null ? propertiesVersionPk.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (propertiesFk != null ? propertiesFk.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "com.creative.dao.entity.PropertiesVersion[ propertiesVersionPk=" + propertiesVersionPk + " ]";
    }

}
