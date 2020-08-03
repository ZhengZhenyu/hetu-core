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
package io.hetu.core.sql.migration.tool;

import io.hetu.core.migration.source.impala.ImpalaSqlParser;
import io.hetu.core.sql.migration.parser.ImpalaParser;
import org.codehaus.jettison.json.JSONObject;

public class ImpalaSqlConverter
        extends SqlSyntaxConverter
{
    private ImpalaParser impalaParser;
    private ConvertionOptions convertionOptions;
    private static ImpalaSqlConverter hiveSqlConverter;

    private ImpalaSqlConverter(ConvertionOptions convertionOptions)
    {
        this.impalaParser = new ImpalaParser();
        this.convertionOptions = convertionOptions;
    }

    public static synchronized ImpalaSqlConverter getImpalaSqlConverter(ConvertionOptions convertionOptions)
    {
        if (hiveSqlConverter == null) {
            hiveSqlConverter = new ImpalaSqlConverter(convertionOptions);
        }

        return hiveSqlConverter;
    }

    @Override
    public JSONObject convert(String sql)
    {
        return impalaParser.invokeParser(sql, ImpalaSqlParser::singleStatement, convertionOptions);
    }
}
