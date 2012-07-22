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

package com.creative.configuration.service;

import com.creative.dao.entity.Environment;
import com.creative.dao.repository.GenericBatchDao;
import com.creative.dao.repository.GenericDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mohan
 * Date: 22/07/12
 * Time: 6:05 PM
 * To change this template use File | Settings | File Templates.
 */
@Service("configManagementService")
@Scope("prototype")
public class ConfigManagementServiceImpl implements ConfigManagementService {
    private GenericBatchDao genericBatchDao;
    private GenericDao genericDao;

    @Autowired
    public ConfigManagementServiceImpl(GenericBatchDao genericBatchDao, GenericDao genericDao) {
        this.genericBatchDao = genericBatchDao;
        this.genericDao = genericDao;

    }

    @Override
    @Transactional
    @Cacheable("environmentCache")
    public List<Environment> getEnvironments() {
        return genericDao.executeNamedQueryWithOutParams("Environment.findAll", Environment.class);
    }

    private void getThis() {
        genericBatchDao.executeDeleteBatch(null);
    }
}
