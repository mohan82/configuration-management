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

import java.nio.channels.UnsupportedAddressTypeException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mohan
 * Date: 7/07/12
 * Time: 11:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class EntityTestFactory {

    public static enum EnvironmentEnum {
        PROD,
        UAT,
        TEST,
        STAGING
    }

    public static final List<Environment> createEnvironments() {
        EnvironmentEnum[] enums = EnvironmentEnum.values();
        List<Environment> environmentList = new ArrayList<Environment>(enums.length);
        for (EnvironmentEnum environmentEnumE : enums) {
            environmentList.add(createEnvironment(environmentEnumE.name()));
        }
        return environmentList;
    }

    public static final Environment createEnvironment(String name) {
        return new Environment(name);

    }

    public static enum FileEnum {
        CONFIG_FILE,
        CODE_FILE,
        XML_FILE, CSV_FILE;
    }

    public static final List<File> createFiles() {
        FileEnum[] files = FileEnum.values();
        List<File> fileList = new ArrayList<File>(files.length);
        for (FileEnum fileEnum : files) {
            fileList.add(createFile(fileEnum.name()));

        }
        return fileList;
    }

    public static final File createFile(String name) {
        return new File(name);
    }
}




