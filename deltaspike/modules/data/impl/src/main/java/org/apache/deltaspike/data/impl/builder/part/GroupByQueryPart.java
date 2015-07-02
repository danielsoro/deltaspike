/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.deltaspike.data.impl.builder.part;

import org.apache.deltaspike.data.impl.builder.QueryBuilder;
import org.apache.deltaspike.data.impl.builder.QueryBuilderContext;
import org.apache.deltaspike.data.impl.meta.RepositoryComponent;
import org.apache.deltaspike.data.impl.util.QueryUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static org.apache.deltaspike.data.impl.util.QueryUtils.splitByKeyword;
import static org.apache.deltaspike.data.impl.util.QueryUtils.uncapitalize;


class GroupByQueryPart extends BasePropertyQueryPart
{
    private List<String> attributes = new ArrayList<String>();

    @Override
    protected QueryPart build(String queryPart, String method, RepositoryComponent repo)
    {
        if (queryPart.contains("And"))
        {
            String[] andParts = splitByKeyword(queryPart, "And");
            for (String and : andParts)
            {
                split(and, "And", attributes);
            }
            return this;
        }
        attributes.add(uncapitalize(queryPart));
        return this;
    }

    @Override
    protected QueryPart buildQuery(QueryBuilderContext ctx)
    {
        ctx.append(" group by ");
        for (Iterator<String> it = attributes.iterator(); it.hasNext();)
        {
            String entityPrefix = QueryBuilder.ENTITY_NAME + ".";
            ctx.append(entityPrefix).append(rewriteSeparator(it.next()));
            if (it.hasNext())
            {
                ctx.append(", ");
            }
        }
        buildQueryForChildren(ctx);
        return this;
    }

    private void split(String queryPart, String keyword, Collection<String> result)
    {
        for (String part : splitByKeyword(queryPart, keyword))
        {
            result.add(uncapitalize(part));
        }
    }

}
