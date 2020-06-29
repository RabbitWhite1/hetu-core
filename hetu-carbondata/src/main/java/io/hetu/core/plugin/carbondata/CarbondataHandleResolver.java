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

package io.hetu.core.plugin.carbondata;

import io.prestosql.plugin.hive.HiveHandleResolver;
import io.prestosql.spi.connector.ConnectorDeleteAsInsertTableHandle;
import io.prestosql.spi.connector.ConnectorInsertTableHandle;
import io.prestosql.spi.connector.ConnectorOutputTableHandle;
import io.prestosql.spi.connector.ConnectorUpdateTableHandle;

public class CarbondataHandleResolver
        extends HiveHandleResolver
{
    @Override
    public Class<? extends ConnectorInsertTableHandle> getInsertTableHandleClass()
    {
        return CarbondataInsertTableHandle.class;
    }

    @Override
    public Class<? extends ConnectorUpdateTableHandle> getUpdateTableHandleClass()
    {
        return CarbondataUpdateTableHandle.class;
    }

    @Override
    public Class<? extends ConnectorDeleteAsInsertTableHandle> getDeleteAsInsertTableHandleClass()
    {
        return CarbonDeleteAsInsertTableHandle.class;
    }

    public Class<? extends ConnectorOutputTableHandle> getOutputTableHandleClass()
    {
        return CarbondataOutputTableHandle.class;
    }
}
