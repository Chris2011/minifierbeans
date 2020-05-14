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
package org.netbeans.minifierbeans.util.source.minify;

import java.awt.HeadlessException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.netbeans.minifierbeans.ui.MinifyProperty;
import org.openide.loaders.DataObject;

import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.awt.NotificationDisplayer;
import org.openide.filesystems.FileObject;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.openide.util.RequestProcessor;
import org.openide.util.TaskListener;

@ActionID(category = "Build",
        id = "org.netbeans.util.source.minify.HTMLMinify")
@ActionRegistration(iconBase = "org/netbeans/minifierbeans/util/source/minify/compress.png",
        displayName = "#CTL_HTMLMinify")
@ActionReferences({
    @ActionReference(path = "Loaders/text/html/Actions", position = 300, separatorBefore = 250, separatorAfter = 350)
})
@Messages("CTL_HTMLMinify=Minify HTML")
public final class HTMLMinify implements ActionListener {
    private final DataObject context;

    public HTMLMinify(DataObject context) {
        this.context = context;
    }
    private final static RequestProcessor RP = new RequestProcessor("HTMLMinify", 1, true);

    @Override
    public void actionPerformed(ActionEvent ev) {
        execute(context, null, true);
    }

    public static void execute(final DataObject context, final String content, final boolean notify) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                htmlMinify(context, content, notify);
            }
        };
        final RequestProcessor.Task theTask = RP.create(runnable);
        final ProgressHandle ph = ProgressHandleFactory.createHandle("Minifying HTML " + context.getPrimaryFile().getName(), theTask);
        theTask.addTaskListener(new TaskListener() {
            @Override
            public void taskFinished(org.openide.util.Task task) {
                ph.finish();
            }
        });
        ph.start();
        theTask.schedule(0);
    }

    private static void htmlMinify(DataObject context, String content, boolean notify) {
        MinifyProperty minifyProperty = MinifyProperty.getInstance();
        MinifyUtil util = new MinifyUtil();

        try {
            FileObject file = context.getPrimaryFile();
            if (!util.isMinifiedFile(file.getName(), minifyProperty.getPreExtensionHTML())) {
                String inputFilePath = file.getPath();
                String outputFilePath;

                if (minifyProperty.isNewHTMLFile() && minifyProperty.getPreExtensionHTML() != null && !minifyProperty.getPreExtensionHTML().trim().isEmpty()) {
                    outputFilePath = file.getParent().getPath() + File.separator + file.getName() + minifyProperty.getPreExtensionHTML() + "." + file.getExt();
                } else {
                    outputFilePath = inputFilePath;
                }

                MinifyFileResult minifyFileResult;
                if (content != null) {
                    minifyFileResult = util.compressContent(inputFilePath, content, "text/html", outputFilePath, minifyProperty);
                } else {
                    minifyFileResult = util.compress(inputFilePath, "text/html", outputFilePath, minifyProperty);
                }
                if (minifyProperty.isEnableOutputLogAlert() && notify) {
                    NotificationDisplayer.getDefault().notify("Successful HTML minification", NotificationDisplayer.Priority.NORMAL.getIcon(), String.format("Input HTML Files Size: %s Bytes \n"
                            + "After Minifying HTML Files Size: %s Bytes \n"
                            + "HTML Space Saved %s%%", minifyFileResult.getInputFileSize(), minifyFileResult.getOutputFileSize(), minifyFileResult.getSavedPercentage()), null);
                }
            }
        } catch (HeadlessException | IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
