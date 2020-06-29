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
package io.prestosql.tests.cassandra;

import com.google.common.collect.ImmutableList;
import io.prestosql.tempto.fulfillment.table.jdbc.RelationalDataSource;
import io.prestosql.tempto.internal.fulfillment.table.cassandra.CassandraTableDefinition;

import java.sql.Timestamp;
import java.util.List;

import static io.prestosql.tests.cassandra.TestConstants.CONNECTOR_NAME;
import static io.prestosql.tests.cassandra.TestConstants.KEY_SPACE;

public class MultiColumnKeyTableDefinition
{
    private MultiColumnKeyTableDefinition() {}

    private static final String MULTI_COLUMN_KEY_DDL =
            "CREATE TABLE %NAME% (" +
                    "user_id text, " +
                    "key text, " +
                    "updated_at timestamp, " +
                    "value text, " +
                    "PRIMARY KEY (user_id, key, updated_at));";
    private static final String MULTI_COLUMN_KEY_TABLE_NAME = "multicolumnkey";

    public static final CassandraTableDefinition CASSANDRA_MULTI_COLUMN_KEY;

    static {
        RelationalDataSource dataSource = () -> ImmutableList.<List<Object>>of(
                ImmutableList.of("Alice", "a1", Timestamp.valueOf("2015-01-01 01:01:01"), "Test value 1"),
                ImmutableList.of("Bob", "b1", Timestamp.valueOf("2014-02-02 03:04:05"), "Test value 2")
        ).iterator();
        CASSANDRA_MULTI_COLUMN_KEY = CassandraTableDefinition.cassandraBuilder(MULTI_COLUMN_KEY_TABLE_NAME)
                .withDatabase(CONNECTOR_NAME)
                .withSchema(KEY_SPACE)
                .setCreateTableDDLTemplate(MULTI_COLUMN_KEY_DDL)
                .setDataSource(dataSource)
                .build();
    }
}
