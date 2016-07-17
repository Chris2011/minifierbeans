/**
 * Copyright [2013] Gaurav Gupta
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.netbeans.minify.task;

import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.api.editor.mimelookup.MimeRegistrations;
import org.netbeans.spi.editor.document.OnSaveTask;

@MimeRegistrations({
    @MimeRegistration(mimeType = "text/html", service = OnSaveTask.Factory.class),
    @MimeRegistration(mimeType = "text/javascript", service = OnSaveTask.Factory.class),
    @MimeRegistration(mimeType = "text/css", service = OnSaveTask.Factory.class),
    @MimeRegistration(mimeType = "text/x-json", service = OnSaveTask.Factory.class),
    @MimeRegistration(mimeType = "text/xml-mime", service = OnSaveTask.Factory.class)
})
    public  class EditorSaveTaskFactory implements OnSaveTask.Factory {

        @Override
        public OnSaveTask createTask(OnSaveTask.Context cntxt) {
            return new EditorSaveTask(cntxt);
        }

    }