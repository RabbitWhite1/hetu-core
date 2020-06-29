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

package io.hetu.core.hive.dynamicfunctions.type;

import io.prestosql.spi.type.ArrayType;
import io.prestosql.spi.type.StandardTypes;
import io.prestosql.spi.type.Type;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static io.hetu.core.hive.dynamicfunctions.utils.HetuTypeUtil.getType;

public class ArrayParametricType
        implements ParametricType
{
    @Override
    public String getName()
    {
        return StandardTypes.ARRAY;
    }

    @Override
    public Type createType(List<String> params)
    {
        checkArgument(params.size() == 1, "Array type expects exactly one type as a parameter, got %s", params);
        return new ArrayType(getType(params.get(0)));
    }
}
