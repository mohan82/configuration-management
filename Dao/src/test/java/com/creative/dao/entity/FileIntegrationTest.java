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
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.hibernate.Query;
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

import java.util.List;
import java.util.Set;

import static com.creative.dao.entity.EntityTestFactory.EnvironmentEnum.TEST;
import static com.creative.dao.entity.EntityTestFactory.FileEnum;
import static com.creative.dao.entity.EntityTestFactory.createEnvironment;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: mohan
 * Date: 8/07/12
 * Time: 8:12 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoTestApplicationContext.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class FileIntegrationTest {
    private GenericDao genericDao;
    private EntityTestHelper entityTestHelper;
    private static final Logger logger = LoggerFactory.getLogger(FileIntegrationTest.class);

    public FileIntegrationTest() {

    }

    @Autowired
    public void init(@Qualifier("genericDao") GenericDao genericDao,
                     @Qualifier("entityTesHelper") EntityTestHelper entityTestHelper) {
        this.entityTestHelper = entityTestHelper;
        this.genericDao = genericDao;
    }

    @Before
    public void setup() {
        logger.info("Setting up Create Environment");
        genericDao.saveOrUpdate(createEnvironment(TEST.name()));
        logger.info(" End Setting up Create Environment");

    }


    @Test
    public void testGetFiles() throws IncorrectResultException {
        logger.info("Test Get All Files");
        List<File> fileList = genericDao.executeNamedQueryWithOutParams("File.findAll", File.class);
        testAllFiles(fileList);
        logger.info(" End Test Get All Files");
    }

    @Test
    public void testFileNamedQueryByPk() throws IncorrectResultException {
        Environment testEnvironment = entityTestHelper.getEnvironmentByName(TEST.name());
        File testFile = testEnvironment.getFileCollection().iterator().next();
        Query query = genericDao.getNamedQuery("File.findByFilePk").setInteger("filePk", testFile.getFilePk());
        File retrievedTestFile = genericDao.findUniqueObject(query, File.class);
        testFile(retrievedTestFile);
    }

    @Test
    public void testFileNamedQueryByName() throws IncorrectResultException {
        Environment testEnvironment = entityTestHelper.getEnvironmentByName(TEST.name());
        File testFile = testEnvironment.getFileCollection().iterator().next();
        Query query = genericDao.getNamedQuery("File.findByName").setString("name", testFile.getName());
        File retrievedTestFile = genericDao.findUniqueObject(query, File.class);
        testFile(retrievedTestFile);
    }


    @Test
    public void deleteFile() {
        logger.info("Deleting File");
        List<File> fileList = genericDao.executeNamedQueryWithOutParams("File.findAll", File.class);
        int expectedFileList = fileList.size() - 1;
        File file = fileList.get(0);
        file.getEnvironmentFk().getFileCollection().remove(file);
        genericDao.deleteObject(file);
        List<File> afterDeletedFileList = genericDao.executeNamedQueryWithOutParams("File.findAll", File.class);
        assertEquals(expectedFileList, afterDeletedFileList.size());
        logger.info("End Deleting File");
    }

    @Test
    public void testUniqueness() {
        Set<File> fileSet = Sets.newHashSetWithExpectedSize(100);
        for (int i = 0; i < 100; i++) {
            File file = new File(TEST.name());
            Environment environment = new Environment(TEST.name());
            file.setEnvironmentFk(environment);
            fileSet.add(file);
        }
        assertEquals(fileSet.size(), 1);
    }


    //Helper methods

    public void testAllFiles(List<File> fileList) {
        assertEquals(fileList.size(), EntityTestFactory.FileEnum.values().length);
        for (File file : fileList) {
            testFile(file);
        }
    }

    public void testFile(File file) {
        Integer filePk = file.getFilePk();
        String fileName = file.getName();
        assertTrue("FilePk cannot be null and FilePK should be >0", filePk != null && filePk > 0);
        boolean foundFileName = false;
        for (FileEnum fileEnum : FileEnum.values()) {
            if (!foundFileName) {
                if (fileEnum.name().equals(fileName)) {
                    foundFileName = true;
                }
            }
        }
        assertTrue("FileName:" + fileName + "should exist in " + FileEnum.values(), foundFileName);
    }
}
