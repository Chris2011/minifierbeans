/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package io.github.chris2011.netbeans.minifierbeans.xml.ui.options;

import org.netbeans.spi.options.AdvancedOption;
import org.netbeans.spi.options.OptionsPanelController;
import org.openide.util.NbBundle;

public final class XmlAdvancedOption extends AdvancedOption {
    @Override
    public String getDisplayName() {
        return NbBundle.getMessage(XmlAdvancedOption.class, "AdvancedOption_DisplayName_Xml"); // NOI18N
    }

    @Override
    public String getTooltip() {
        return NbBundle.getMessage(XmlAdvancedOption.class, "AdvancedOption_Tooltip_Xml"); // NOI18N
    }

    @Override
    public OptionsPanelController create() {
        return new XmlOptionsPanelController();
    }
}
