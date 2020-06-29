/*
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
package io.prestosql.plugin.hive.metastore;

import com.google.common.collect.ImmutableMap;
import org.testng.annotations.Test;

import java.util.Map;

import static io.airlift.configuration.testing.ConfigAssertions.assertFullMapping;
import static io.airlift.configuration.testing.ConfigAssertions.assertRecordedDefaults;
import static io.airlift.configuration.testing.ConfigAssertions.recordDefaults;

public class TestMetastoreConfig
{
    @Test
    public void testDefaults()
    {
        assertRecordedDefaults(recordDefaults(MetastoreConfig.class)
                .setMetastoreType("thrift")
                .setThriftMetastoreImp("")
                .setMetastoreClientFactoryImp(""));
    }

    @Test
    public void testExplicitPropertyMappings()
    {
        Map<String, String> properties = new ImmutableMap.Builder<String, String>()
                .put("hive.metastore", "foo")
                .put("hive.metastore.client-factory-imp", "packageName.testClient")
                .put("hive.metastore.thrift-imp", "packageName.testThrift")
                .build();

        MetastoreConfig expected = new MetastoreConfig()
                .setMetastoreType("foo")
                .setMetastoreClientFactoryImp("packageName.testClient")
                .setThriftMetastoreImp("packageName.testThrift");

        assertFullMapping(properties, expected);
    }
}
