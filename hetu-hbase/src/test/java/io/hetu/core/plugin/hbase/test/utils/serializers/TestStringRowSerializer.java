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
package io.hetu.core.plugin.hbase.test.utils.serializers;

import io.hetu.core.plugin.hbase.utils.serializers.StringRowSerializer;

/**
 * TestStringRowSerializer
 *
 * @since 2020-03-20
 */
public class TestStringRowSerializer
        extends AbstractTestHBaseRowSerializer
{
    /**
     * StringRowSerializer PSERIALIZER
     */
    public static final StringRowSerializer PSERIALIZER = new StringRowSerializer();

    /**
     * TestStringRowSerializer
     */
    public TestStringRowSerializer()
    {
        super(PSERIALIZER);
    }
}
