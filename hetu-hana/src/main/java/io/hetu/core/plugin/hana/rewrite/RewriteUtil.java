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
package io.hetu.core.plugin.hana.rewrite;

import com.google.common.base.Joiner;
import io.prestosql.spi.sql.expression.QualifiedName;
import io.prestosql.spi.sql.expression.Selection;
import io.prestosql.spi.type.TimeZoneKey;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

/**
 * util helper class for rewrite handle
 *
 * @since 2019-09-10
 */

public class RewriteUtil
{
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss.SSS");

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    /**
     * interal function prefix name
     */
    public static final String LITERAL_FUNCNAME_PREFIX = "$literal$";

    private RewriteUtil()
    {
    }

    /**
     * expression list
     *
     * @param expressions expression lists
     * @return sql statement
     */
    public static final String joinExpressions(List<String> expressions)
    {
        return Joiner.on(", ").join(expressions);
    }

    /**
     * formate identifier
     *
     * @param qualifiedNames qualified names
     * @param identifier identifier
     * @return sql statement
     */
    public static final String formatIdentifier(Optional<Map<String, Selection>> qualifiedNames, String identifier)
    {
        if (qualifiedNames.isPresent()) {
            return qualifiedNames.get().get(identifier).getExpression();
        }
        return identifier;
    }

    /**
     * formate qualified name
     *
     * @param name qualified name
     * @return sql statement
     */
    public static final String formatQualifiedName(QualifiedName name)
    {
        return name.getParts().stream().map(identifier -> formatIdentifier(Optional.empty(), identifier)).collect(joining("."));
    }

    /**
     *  print time with HH:mm:ss.SSS formater
     *
     * @param value data type TIME inner value
     * @return  time literal value
     */
    public static String printTimeWithoutTimeZone(long value)
    {
        return Instant.ofEpochMilli(value).atZone(ZoneId.of(TimeZoneKey.UTC_KEY.getId())).format(TIME_FORMATTER);
    }

    /**
     *  print time with HH:mm:ss.SSS formater
     *
     * @param timeZoneKey to convert timezone
     * @param value  data type TIME inner value
     * @return  time literal value
     */
    public static String printTimeWithoutTimeZone(TimeZoneKey timeZoneKey, long value)
    {
        return Instant.ofEpochMilli(value).atZone(ZoneId.of(timeZoneKey.getId())).format(TIME_FORMATTER);
    }

    /**
     *  print timestamp with YYYY-MM-dd HH:mm:ss.SSS formater
     *
     * @param timestamp  data type TIMESTAMP inner value
     * @return timestamp literal value
     */
    public static String printTimestampWithoutTimeZone(long timestamp)
    {
        return Instant.ofEpochMilli(timestamp).atZone(ZoneId.of(TimeZoneKey.UTC_KEY.getId())).format(TIMESTAMP_FORMATTER);
    }

    /**
     * print timestamp with YYYY-MM-dd HH:mm:ss.SSS formater
     *
     * @param timeZoneKey  to convert timezone
     * @param timestamp  data type TIMESTAMP inner value
     * @return timestamp literal value
     */
    public static String printTimestampWithoutTimeZone(TimeZoneKey timeZoneKey, long timestamp)
    {
        return Instant.ofEpochMilli(timestamp).atZone(ZoneId.of(timeZoneKey.getId())).format(TIMESTAMP_FORMATTER);
    }
}
