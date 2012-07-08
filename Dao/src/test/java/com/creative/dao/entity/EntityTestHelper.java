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

package com.creative.dao.entity;

/**
 * Created with IntelliJ IDEA.
 * User: mohan
 * Date: 8/07/12
 * Time: 9:44 PM
 * To change this template use File | Settings | File Templates.
 */

import com.creative.dao.exceptions.IncorrectResultException;
import com.creative.dao.repository.GenericBatchDao;
import com.creative.dao.repository.GenericDao;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Common Entity Helper methods
 */
@Component("entityTesHelper")
@Scope("prototype")
public class EntityTestHelper {
    private GenericDao genericDao;
    private GenericBatchDao genericBatchDao;

    @Autowired
    public EntityTestHelper(@Qualifier("genericDao") GenericDao genericDao,
                            @Qualifier("genericBatchDao") GenericBatchDao genericBatchDao) {
        this.genericBatchDao = genericBatchDao;
        this.genericDao = genericDao;
    }

    public Environment getEnvironmentByName(String environmentName) throws IncorrectResultException {
        Query querybyName = genericDao.getNamedQuery("Environment.findByName").setString("name", environmentName);
        return genericDao.findUniqueObject(querybyName, Environment.class);
    }

    public List<Environment> getAllEnvironments() {
        return genericDao.executeNamedQueryWithOutParams("Environment.findAll", Environment.class);
    }

}
