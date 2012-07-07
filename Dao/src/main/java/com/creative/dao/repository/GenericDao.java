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
import org.hibernate.Query;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mohan
 * Date: 7/07/12
 * Time: 11:05 PM
 * To change this template use File | Settings | File Templates.
 */
public interface GenericDao {
    <T> void saveOrUpdate(T t);

    <T> void deleteObject(T t);

    <T> List<T> executeNamedQueryWithOutParams(String nameQuery, Class<T> clazz);

    Query getNamedQuery(String nameQuery);

    <T> List<T> executeQuery(Query query, Class<T> clazz);

    <T> List<T> executeQuery(String query, Class<T> clazz);

    <T> T findUniqueObject(String query, Class<T> clazz) throws IncorrectResultException;

    <T> T findByID(Integer id, Class<T> clazz) throws IdNotFoundException;
}
