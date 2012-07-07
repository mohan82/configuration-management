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
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mohan
 * Date: 7/07/12
 * Time: 11:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class EntityTestFactory {

    public static enum TestEnvironment {
        PROD,
        UAT,
        TEST,
        STAGING
    }
    public static final List<Environment> createEnvironments() {
       String[] environments = {"Test","Prod","UAT"};
      List<Environment>environmentList = new ArrayList<Environment>(environments.length);
      for(String environment:environments)
      {
          environmentList.add(createEnvironment(environment));
      }
        return environmentList;
    }
    public static final Environment createEnvironment(String name){
        return  new Environment(name);

    }
}
