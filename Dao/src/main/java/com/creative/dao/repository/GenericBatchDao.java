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

import org.hibernate.Query;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mohan
 * Date: 7/07/12
 * Time: 11:05 PM
 * To change this template use File | Settings | File Templates.
 */
public interface GenericBatchDao {
    <T> int executeInsertBatch(List<T> list);

    <T> int executesaveOrUpdateBatch(List<T> list);

    <T> int executeDeleteBatch(List<T> list);

    int executeUpdate(Query query);
}