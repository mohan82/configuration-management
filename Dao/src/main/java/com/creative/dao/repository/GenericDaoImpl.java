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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creative.dao.repository;

import com.creative.dao.exceptions.IdNotFoundException;
import com.creative.dao.exceptions.IncorrectResultException;
import java.util.List;
import javax.inject.Inject;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

/**
 * @author mohan
 */
@Repository("genericDao")
@Scope("prototype")
/***
 * Generic dao impl class for generic CRUD operations
 */
public class GenericDaoImpl implements GenericDao {

    private SessionFactory sessionFactory;

    public GenericDaoImpl() {
    }

    /**
     * @param sessionFactory
     */
    @Inject
    public GenericDaoImpl(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Saves or updates of a single object or a collection based on hibernate saveOrUpdate
     * might throw hibernate exception.
     * NOTE: Use BatchImpl for bulk operations
     *
     * @param t
     * @param <T>
     */
    @Override
    public <T> void saveOrUpdate(T t) {
        sessionFactory.getCurrentSession().saveOrUpdate(t);
    }

    /**
     *
     * @param t
     * @param <T>
     */
    @Override
    public <T> void deleteObject(T t) {
        sessionFactory.getCurrentSession().delete(t);
    }

    /**
     * Executes the named query and assumes the named query is
     * loaded in the sessionfactory container, if not will
     * throw hibernate custom exceptions and returns a single item
     * or list.
     *
     * @param nameQuery
     * @param clazz
     * @param <T>
     * @return List<T>
     */
    @Override
    public <T> List<T> executeNamedQueryWithOutParams(String nameQuery, Class<T> clazz) {
        return (List<T>) sessionFactory.getCurrentSession().getNamedQuery(nameQuery).list();
    }

    @Override
    public Query getNamedQuery(String nameQuery) {
        return sessionFactory.getCurrentSession().getNamedQuery(nameQuery);
    }

    /**
     * Executes the named query and returns the list of given class might throw hibernate execption
     *
     * @param query
     * @param clazz
     * @param <T>
     * @return
     */
    @Override
    public <T> List<T> executeQuery(String query, Class<T> clazz) {
        return (List<T>) sessionFactory.getCurrentSession().createQuery(query).list();
    }

    @Override
    public <T> List<T> executeQuery(Query query, Class<T> clazz) {
        return (List<T>) query.list();
    }

    /**
     * Executes given hibernate query and finds a unique
     * entity object if unique object is not found then
     * return incorrect result exception
     *
     * @param query
     * @param clazz
     * @param <T>
     * @return
     * @throws IncorrectResultException
     */
    @Override
    public <T> T findUniqueObject(String query, Class<T> clazz) throws IncorrectResultException {
        List<T> list = executeQuery(query, clazz);
        if (CollectionUtils.isEmpty(list)) {
            throw new IncorrectResultException("Result set is empty for given query :" + query);
        } else if (list.size() > 1) {
            throw new IncorrectResultException("Retrieved more than one value for the given query :" + query);
        } else {
            return list.get(0);
        }
    }

    /**
     * Loads the given entity object by id on nonexisent throws
     * EntityNotFound Exception
     *
     * @param id
     * @param clazz
     * @param <T>
     * @return
     * @throws IdNotFoundException
     */
    @Override
    public <T> T findByID(Integer id, Class<T> clazz) throws IdNotFoundException {
        try {
            return (T) sessionFactory.getCurrentSession().load(clazz, id);
        } catch (HibernateException e) {
            throw new IdNotFoundException("Cannot load given id :" + id + " for given class :" + clazz, e);
        }

    }
}
