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

import com.creative.dao.exceptions.IdNotFoundException;
import com.creative.dao.exceptions.IncorrectResultException;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static com.creative.dao.repository.TestUtil.*;

/**
 * Created with IntelliJ IDEA.
 * User: mohan
 * Date: 7/07/12
 * Time: 8:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class GenericDaoImplTest {
    private GenericDaoImpl genericDao;
    private SessionFactory sessionFactory;
    private Query query;

    private Session session;

    @Before
    public void setUp() throws Exception {
        sessionFactory = mock(SessionFactory.class);
        session = mock(Session.class);
        query = mock(Query.class);
        genericDao = new GenericDaoImpl(sessionFactory);


    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSaveOrUpdateStringData() throws Exception {
        for (String testStringData : TEST_STRING_DATAS) {
            mockCurrentSession(sessionFactory, session);
            genericDao.saveOrUpdate(testStringData);
            verify(session).saveOrUpdate(testStringData);
        }
    }

    @Test
    public void testSaveOrUpdateIntegerData() throws Exception {
        for (Integer testIntegerData : TEST_INTEGER_DATAS) {
            mockCurrentSession(sessionFactory, session);
            genericDao.saveOrUpdate(testIntegerData);
            verify(session).saveOrUpdate(testIntegerData);
        }
    }

    @Test
    public void testExecuteNamedQuery() throws Exception {
        mockCurrentSession(sessionFactory, session);
        when(session.getNamedQuery(TEST_STRING)).thenReturn(query);
        mockTestIntegerList(query);
        List<Integer> integerList = genericDao.executeNamedQuery(TEST_STRING, Integer.class);
        assertEquals(TEST_INTEGER_LIST, integerList);
        verify(query).list();
        verify(session).getNamedQuery(TEST_STRING);
        verify(sessionFactory).getCurrentSession();
    }

    @Test
    public void testExecuteQuery() throws Exception {
        mockQueryWithIntegerList();
        List<Integer> integerList = genericDao.executeQuery(TEST_STRING, Integer.class);
        assertEquals(TEST_INTEGER_LIST, integerList);
        verifyExecuteQuery();

    }

    @Test
    public void testFindUniqueObject() throws Exception {
        mockQuery();
        when(query.list()).thenReturn(Collections.singletonList(TEST_STRING));
        String test = genericDao.findUniqueObject(TEST_STRING,String.class);
        assertEquals(TEST_STRING,test);
        verifyExecuteQuery();
    }

    @Test(expected = IncorrectResultException.class)
    public void testFindUniqueObjectWithMoreThanOneResult() throws Exception {
        mockQueryWithIntegerList();
        genericDao.findUniqueObject(TEST_STRING, Integer.class);

    }

    @Test(expected = IncorrectResultException.class)
    public void testFindUniqueObjectWithNull() throws Exception {
        mockCurrentSession(sessionFactory, session);
        when(session.createQuery(TEST_STRING)).thenReturn(query);
        when(query.list()).thenReturn(null);
        genericDao.findUniqueObject(TEST_STRING, Integer.class);

    }

    @Test(expected = IncorrectResultException.class)
    public void testFindUniqueObjectWithEmptyList() throws Exception {
        mockCurrentSession(sessionFactory, session);
        when(session.createQuery(TEST_STRING)).thenReturn(query);
        when(query.list()).thenReturn(Collections.EMPTY_LIST);
        genericDao.findUniqueObject(TEST_STRING, Integer.class);

    }

    @Test
    public void testFindByID() throws Exception {
        mockCurrentSession(sessionFactory,session);
        when(session.load(String.class,TEST_INTEGER)).thenReturn(TEST_STRING);
        String test = genericDao.findByID(TEST_INTEGER,String.class);
        assertEquals(test,TEST_STRING);
        verify(sessionFactory).getCurrentSession();
        verify(session).load(String.class,TEST_INTEGER);

    }

    @Test(expected = IdNotFoundException.class)
    public void testFindByIDExpection() throws Exception {
        mockCurrentSession(sessionFactory,session);
        when(session.load(String.class,TEST_INTEGER)).thenThrow(HibernateException.class);
        genericDao.findByID(TEST_INTEGER,String.class);
    }

    //helper methods move them to
    //separate class if required elsewhere

    public void mockQuery() {
        mockCurrentSession(sessionFactory, session);
        when(session.createQuery(TEST_STRING)).thenReturn(query);

    }


    public void mockQueryWithIntegerList() {
        mockQuery();
        mockTestIntegerList(query);

    }
    public void verifyExecuteQuery() {
        verify(query).list();
        verify(session).createQuery(TEST_STRING);
        verify(sessionFactory).getCurrentSession();

    }


}
