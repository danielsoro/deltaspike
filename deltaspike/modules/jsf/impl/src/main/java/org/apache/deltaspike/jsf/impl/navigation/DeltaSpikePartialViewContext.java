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
package org.apache.deltaspike.jsf.impl.navigation;

import javax.faces.context.PartialResponseWriter;
import javax.faces.context.PartialViewContext;
import javax.faces.context.PartialViewContextWrapper;

public class DeltaSpikePartialViewContext extends PartialViewContextWrapper
{
    private final PartialViewContext wrapped;
    private PartialResponseWriter writer = null;

    public DeltaSpikePartialViewContext(PartialViewContext wrapped)
    {
        this.wrapped = wrapped;
    }

    @Override
    public PartialResponseWriter getPartialResponseWriter()
    {
        if (writer == null)
        {
            PartialResponseWriter parentWriter = getWrapped().getPartialResponseWriter();
            writer = new DeltaSpikePartialResponseWriter(parentWriter);
        }

        return writer;
    }

    
    @Override
    public PartialViewContext getWrapped()
    {
        return this.wrapped;
    }
}