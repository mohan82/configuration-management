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

import com.creative.dao.repository.TestUtil;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.Random;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: mohan
 * Date: 7/07/12
 * Time: 11:31 PM
 * To change this template use File | Settings | File Templates.
 */
public final class EntityTestFactory {


    private EntityTestFactory() {
    }

    public static enum EnvironmentEnum {
        PROD,
        UAT,
        TEST,
        STAGING
    }


    public static Set<Environment> createEnvironments() {
        EnvironmentEnum[] enums = EnvironmentEnum.values();
        Set<Environment> environmentSet = Sets.newHashSetWithExpectedSize(enums.length);
        for (EnvironmentEnum environmentEnumE : enums) {
            environmentSet.add(createEnvironment(environmentEnumE.name()));
        }
        return environmentSet;
    }

    public static Environment createTestEnvironmentWithProperties() {

        Environment environment = createEnvironment(EnvironmentEnum.TEST.name());
        return addFilesToEnvironmentWithProperties(environment);
    }


    public static Environment createEnvironment(String name) {
        Environment environment = new Environment(name);
        return addFilesToEnvironment(environment);
    }

    public static enum FileEnum {
        CONFIG_FILE,
        CODE_FILE,
        XML_FILE, CSV_FILE
    }

    public static File createFile(String name, Environment environment) {
        File file = new File(name);
        file.setEnvironmentFk(environment);
        return file;
    }


    public static Environment addFilesToEnvironment(Environment environment) {
        FileEnum[] files = FileEnum.values();
        Set<File> fileSet = Sets.newHashSetWithExpectedSize(files.length);
        for (FileEnum fileEnum : files) {
            fileSet.add(createFile(fileEnum.name(), environment));
        }
        environment.setFileCollection(fileSet);
        return environment;
    }

    public static Environment addFilesToEnvironmentWithProperties(Environment environment) {
        addFilesToEnvironment(environment);
        Collection<File> fileCollection = environment.getFileCollection();
        for (File file : fileCollection) {
            createTestProperties(file);
        }
        return environment;

    }

    private static enum RandomParm {
        MIN(1),
        MAX(100),
        SIZE(100);
        private int val;

        private RandomParm(int val) {
            this.val = val;
        }

        public int val() {
            return val;
        }
    }

    public static Set<Properties> createTestProperties(File file) {
        Set<Properties> testPropertiesSet = Sets.newHashSet();
        Random random = new Random();

        for (int i = RandomParm.MIN.val(); i <= RandomParm.MAX.val(); i++) {
            int randomNum = random.nextInt((RandomParm.MAX.val() - RandomParm.MIN.val()) + 1) + RandomParm.MIN.val();
            Preconditions.checkArgument(RandomParm.MIN.val() <= randomNum,
                    "Random Number should be greater than min-" + randomNum);
            Preconditions.checkArgument(randomNum <= RandomParm.MAX.val(),
                    "Random Number should be less than max-" + randomNum);
            Properties properties = new Properties((TestUtil.TEST_STRING + randomNum),
                    String.valueOf(randomNum));
            properties.setFileFk(file);
            testPropertiesSet.add(properties);
        }
        file.setPropertiesCollection(testPropertiesSet);
        return testPropertiesSet;
    }
}




