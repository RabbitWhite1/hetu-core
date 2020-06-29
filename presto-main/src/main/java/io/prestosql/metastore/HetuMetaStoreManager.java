/*
 * Copyright (C) 2018-2020. Huawei Technologies Co., Ltd. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.prestosql.metastore;

import com.google.common.collect.ImmutableMap;
import io.airlift.log.Logger;
import io.prestosql.spi.classloader.ThreadContextClassLoader;
import io.prestosql.spi.metastore.HetuMetaStoreFactory;
import io.prestosql.spi.metastore.HetuMetastore;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.google.common.base.Preconditions.checkState;
import static io.prestosql.util.PropertiesUtil.loadProperties;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * HetuMetaStoreManager manages the lifecycle of HetuMetaStores
 *
 * @since 2020-04-26
 */
public class HetuMetaStoreManager
{
    private static final Logger LOG = Logger.get(HetuMetaStoreManager.class);
    // properties type name
    private static final File HETUMETASTORE_CONFIG_FILE = new File("etc/hetu-metastore.properties");
    private static final String HETU_METASTORE_TYPE_PROPERTY_NAME = "hetu.metastore.type";
    // properties default type value
    private static final String HETU_METASTORE_TYPE_DEFAULT_VALUE = "jdbc";

    private final Map<String, HetuMetaStoreFactory> hetuMetastoreFactories = new ConcurrentHashMap<>();
    private HetuMetastore hetuMetastore;
    private String hetuMetastoreType;

    public void addHetuMetaStoreFactory(HetuMetaStoreFactory hetuMetaStoreFactory)
    {
        requireNonNull(hetuMetaStoreFactory, "hetuMetaStoreFactory is null");

        if (hetuMetastoreFactories.putIfAbsent(hetuMetaStoreFactory.getName(), hetuMetaStoreFactory) != null) {
            throw new IllegalArgumentException(format("HetuMetastore '%s' is already registered", hetuMetaStoreFactory.getName()));
        }
    }

    public void loadHetuMetatstore()
            throws Exception
    {
        LOG.info("-- Loading Hetu Metastore --");
        if (HETUMETASTORE_CONFIG_FILE.exists()) {
            // load configuration
            Map<String, String> config = new HashMap<>(loadProperties(HETUMETASTORE_CONFIG_FILE));
            // create hetu metastore
            hetuMetastoreType = config.getOrDefault(HETU_METASTORE_TYPE_PROPERTY_NAME, HETU_METASTORE_TYPE_DEFAULT_VALUE);
            config.remove(HETU_METASTORE_TYPE_PROPERTY_NAME);
            HetuMetaStoreFactory hetuMetaStoreFactory = hetuMetastoreFactories.get(hetuMetastoreType);
            checkState(hetuMetaStoreFactory != null, "hetuMetaStoreFactory %s is not registered", hetuMetaStoreFactory);
            try (ThreadContextClassLoader ignored = new ThreadContextClassLoader(HetuMetaStoreFactory.class.getClassLoader())) {
                hetuMetastore = hetuMetaStoreFactory.create(hetuMetastoreType, ImmutableMap.copyOf(config));
            }

            LOG.info("-- Loaded Hetu Metastore %s --", hetuMetastoreType);
        }
    }

    public HetuMetastore getHetuMetastore()
    {
        return hetuMetastore;
    }
}
