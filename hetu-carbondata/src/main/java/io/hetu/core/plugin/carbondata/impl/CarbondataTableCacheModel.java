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
package io.hetu.core.plugin.carbondata.impl;

import org.apache.carbondata.core.metadata.schema.table.CarbonTable;

/**
 * Caching metadata of CarbonData in Class CarbonTableReader
 * to speed up query
 */
public class CarbondataTableCacheModel
{
    private long lastUpdatedTime;

    private boolean isValid;

    private CarbonTable carbonTable;

    public CarbondataTableCacheModel(long lastUpdatedTime, CarbonTable carbonTable)
    {
        this.lastUpdatedTime = lastUpdatedTime;
        this.carbonTable = carbonTable;
        this.isValid = true;
    }

    public void setCurrentSchemaTime(long currentSchemaTime)
    {
        if (lastUpdatedTime != currentSchemaTime) {
            isValid = false;
        }
        this.lastUpdatedTime = currentSchemaTime;
    }

    public CarbonTable getCarbonTable()
    {
        return carbonTable;
    }

    public void setCarbonTable(CarbonTable carbonTable)
    {
        this.carbonTable = carbonTable;
        this.isValid = true;
    }

    public boolean isValid()
    {
        return isValid;
    }
}
