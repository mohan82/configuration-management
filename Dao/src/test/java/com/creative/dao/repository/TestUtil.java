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

package com.creative.dao.repository;

import java.util.Arrays;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: mohan
 * Date: 7/07/12
 * Time: 8:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestUtil {
    public static final String TEST_STRING_DATAS[] =
            new String[]{"TEST_DATA1", "TEST_Ssdssd", "123", "33434$%"};
    public static final Integer[] TEST_INTEGER_DATAS = new Integer[]{
            121212, 12121434, 124324, 65656556
    };
    public static final String TEST_STRING = "TEST_DATA";
    public static final int TEST_INTEGER = 1234;
    public static final List<String> TEST_STRING_LIST = Arrays.asList(TEST_STRING_DATAS);
    public static final List<Integer> TEST_INTEGER_LIST = Arrays.asList(TEST_INTEGER_DATAS);

    public static class HibernateParam {
        public SessionFactory sessionFactory;
        public Session session;
        public Query query;

        public HibernateParam() {
            this.sessionFactory = sessionFactory;
            this.session = session;
            this.query = query;
        }
    }

    public static void mockHibernateParam(HibernateParam param) {
        param.sessionFactory = mock(SessionFactory.class);
        param.session = mock(Session.class);
        param.query = mock(Query.class);
    }

    public static void mockCurrentSession(SessionFactory sessionFactory, Session session) {
        when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    public static void mockTestIntegerList(Query query) {
        when(query.list()).thenReturn(TEST_INTEGER_LIST);
    }

    private TestUtil() {
    }
}
