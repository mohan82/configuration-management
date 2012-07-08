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

import java.util.List;
import javax.inject.Inject;
import org.hibernate.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: mohan
 * Date: 7/07/12
 * Time: 7:31 PM
 *
 */

/***
 * A Simple Generic Batch Util class which provides
 * batch insert/update/delete  batch operations for
 * any hibernate classes
 */
@Repository("genericBatchDao")
@Scope("prototype")
public class GenericBatchDaoImpl implements GenericBatchDao {
    private static final int DEF_BATCH_SIZE = 30;
    private SessionFactory sessionFactory;
    private final Logger logger = LoggerFactory.getLogger(GenericBatchDaoImpl.class);

    public GenericBatchDaoImpl(){}
    @Inject
    public GenericBatchDaoImpl(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private static enum BatchType {

        BATCH_INSERT, BATCH_DELETE, BATCH_INSERT_OR_UPDATE
    }

    @Override
    public <T> int executeInsertBatch(List<T> list) {
        return executeBatch(BatchType.BATCH_INSERT, list);
    }

    @Override
    public <T> int executesaveOrUpdateBatch(List<T> list) {
        return executeBatch(BatchType.BATCH_INSERT_OR_UPDATE, list);
    }


    @Override
    public <T> int executeDeleteBatch(List<T> list) {
        return executeBatch(BatchType.BATCH_DELETE, list);

    }

    @Override
    public int executeUpdate(Query query) {
        Session session = sessionFactory.getCurrentSession();
        session.setCacheMode(CacheMode.IGNORE);
        session.setFlushMode(FlushMode.MANUAL);
        int rows = query.executeUpdate();
        session.flush();

        logger.info("Updated rows  " + rows + " for " + query.getQueryString());
        return rows;
    }

    private <T> int executeBatch(BatchType batchType, List<T> list) {
        Session session = sessionFactory.getCurrentSession();
        session.setCacheMode(CacheMode.IGNORE);
        session.setFlushMode(FlushMode.MANUAL);
        logger.info("Executing  Batch of size :" + list.size()
                + " given batch size is:" +DEF_BATCH_SIZE
        );

        for (int i = 0; i < list.size(); i++) {
            switch (batchType) {
                case BATCH_INSERT:
                    session.save(list.get(i));
                    break;
                case BATCH_DELETE:
                    session.delete(list.get(i));
                    break;
                case BATCH_INSERT_OR_UPDATE:
                    session.saveOrUpdate(list.get(i));
                default:
                    // nothing;
            }
            if (i > 0 && i % DEF_BATCH_SIZE == 0) {
                logger.info("Flushing and clearing the cache"
                        + " after row number :" + i);
                session.flush();
                session.clear();
            }

        }
        session.flush();
        return list.size();
    }

}
