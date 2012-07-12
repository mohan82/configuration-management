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

import com.creative.dao.repository.GenericDao;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import static com.creative.dao.repository.TestUtil.*;
import static org.junit.Assert.*;

import java.util.Set;

import static com.creative.dao.entity.EntityTestFactory.*;

/**
 * Created with IntelliJ IDEA.
 * User: mohan
 * Date: 8/07/12
 * Time: 11:04 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoTestApplicationContext.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class PropertyIntegrationTest {
    private static final Logger logger = LoggerFactory.getLogger(PropertyIntegrationTest.class);
    private GenericDao genericDao;
    private EntityTestHelper entityTestHelper;

    public PropertyIntegrationTest() {
    }

    @Autowired
    public void init(@Qualifier("genericDao") GenericDao genericDao,
                     @Qualifier("entityTesHelper") EntityTestHelper entityTestHelper) {
        this.entityTestHelper = entityTestHelper;
        this.genericDao = genericDao;
    }

    @Before
    public void setUp() {
        Environment environment = createTestEnvironmentWithProperties();
        genericDao.saveOrUpdate(environment);
    }

    @Test
    public void testPropertiesUniqueness() {
        Set<Properties> propertiesSet = Sets.newHashSetWithExpectedSize(100);
        for (int i = 0; i < 100; i++) {
            Properties properties = new Properties(TEST_STRING, String.valueOf(TEST_INTEGER));
            File file = new File(TEST_STRING);
            Environment environment = new Environment(String.valueOf(TEST_INTEGER));
            file.setEnvironmentFk(environment);
            properties.setFileFk(file);
            propertiesSet.add(properties);
        }
        assertEquals(propertiesSet.size(), 1);
    }

    @Test
    public void testGetAllProperties() {

    }
}
