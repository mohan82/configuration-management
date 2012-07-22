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

package com.creative.configuration.jsf;

import com.creative.configuration.service.ConfigManagementService;
import com.creative.dao.entity.Environment;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mohan
 * Date: 22/07/12
 * Time: 6:01 PM
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean(name = "environmentBean")
@ViewScoped
public class EnvironmentJSFBean implements Serializable {


    @ManagedProperty("#{configManagementService}")
    private transient ConfigManagementService configManagementService;
    public List<Environment> environmentList;

    public EnvironmentJSFBean() {

    }

    public ConfigManagementService getConfigManagementService() {
        return configManagementService;
    }

    public void setConfigManagementService(ConfigManagementService configManagementService) {
        this.configManagementService = configManagementService;
    }

    public List<Environment> getEnvironmentList() {
        return environmentList;
    }

    public void setEnvironmentList(List<Environment> environmentList) {
        this.environmentList = environmentList;
    }

    @PostConstruct
    public void init() {
        environmentList = configManagementService.getEnvironments();

    }

}
