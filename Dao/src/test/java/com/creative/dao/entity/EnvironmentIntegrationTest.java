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

import com.creative.dao.exceptions.IncorrectResultException;
import com.creative.dao.repository.GenericDao;
import org.hibernate.Query;
import org.junit.After;
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

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.creative.dao.entity.EntityTestFactory.EnvironmentEnum.TEST;
import static com.creative.dao.entity.EntityTestFactory.createEnvironment;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created with IntelliJ IDEA.
 * User: mohan
 * Date: 7/07/12
 * Time: 11:01 PM
 * To change this template use File | Settings | File Templates.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoTestApplicationContext.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class EnvironmentIntegrationTest {
    private static final Logger logger = LoggerFactory.getLogger(EnvironmentIntegrationTest.class);
    private GenericDao genericDao;
    private EntityTestHelper entityTestHelper;


    public EnvironmentIntegrationTest() {

    }

    @Autowired
    public void init(@Qualifier("genericDao") GenericDao genericDao,
                     @Qualifier("entityTesHelper") EntityTestHelper entityTestHelper) {
        this.genericDao = genericDao;
        this.entityTestHelper = entityTestHelper;
    }

    @Before
    public void setup() {
        logger.info("Setting up Create Environment");
        genericDao.saveOrUpdate(createEnvironment(TEST.name()));
        logger.info(" End Setting up Create Environment");

    }

    @After
    public void tearDown() {

    }

    @Test
    public void testGetEnvironment() throws IncorrectResultException {
        logger.info("Test Get All Environments");
        List<Environment> environmentList = entityTestHelper.getAllEnvironments();
        testEnvironment(environmentList);
        logger.info("Test Get Environment By PK ");
        Query querybyPK = genericDao.getNamedQuery("Environment.findByEnvironmentPk").setInteger("environmentPk", environmentList.get(0).getEnvironmentPk());
        testEnvironment(genericDao.executeQuery(querybyPK, Environment.class));
        logger.info("Test Get Environment By Name ");
        testEnvironment(Collections.singletonList(entityTestHelper.getEnvironmentByName(environmentList.get(0).getName())));
    }

    @Test
    public void deleteEnvironment() {
        logger.info("Deleting Environment");
        List<Environment> environmentList = entityTestHelper.getAllEnvironments();
        genericDao.deleteObject(environmentList.get(0));
        logger.info("End Deleting Environment");
        List<Environment> environmentListTest = entityTestHelper.getAllEnvironments();
        assertTrue(environmentListTest.isEmpty());


    }

    @Test
    public void testUniqueness() {
        Set<Environment> environmentSet = new HashSet<Environment>();
        for (int i = 0; i < 100; i++) {
            environmentSet.add(new Environment(TEST.name()));
        }
        assertEquals(environmentSet.size(), 1);
    }

    public void testEnvironment(List<Environment> environmentList) {
        assertEquals(environmentList.size(), 1);
        for (Environment environment : environmentList) {
            Integer environmentPK = environment.getEnvironmentPk();
            assertTrue("Environment PK cannot be Null and must be >0", (environmentPK != null && environmentPK > 0));
            assertEquals(environment.getName(), TEST.name());
        }
    }


}
