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
package org.netbeans.util.source.minify;

import java.awt.HeadlessException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import org.netbeans.api.progress.ProgressHandle;
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
        id = "org.netbeans.util.source.minify.JSMinify")
@ActionRegistration(iconBase = "org/netbeans/util/source/minify/compress.png",
        displayName = "#CTL_JSMinify")
@ActionReferences({
    @ActionReference(path = "Loaders/text/javascript/Actions", position = 200, separatorBefore = 150, separatorAfter = 250)
})
@Messages("CTL_JSMinify=Minify JS")
public final class JSMinify implements ActionListener {
    private final DataObject context;

    public JSMinify(DataObject context) {
        this.context = context;
    }
    private final static RequestProcessor RP = new RequestProcessor("JSMinify", 1, true);

    @Override
    public void actionPerformed(ActionEvent ev) {
        execute(context, null, true);
    }

    public static void execute(final DataObject context, final String content, final boolean notify) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                jsMinify(context, content, notify);
            }
        };

        final RequestProcessor.Task theTask = RP.create(runnable);
        final ProgressHandle ph = ProgressHandle.createHandle("Minifying JS " + context.getPrimaryFile().getName(), theTask);

        theTask.addTaskListener(new TaskListener() {
            @Override
            public void taskFinished(org.openide.util.Task task) {
                ph.finish();
            }
        });

        ph.start();
        theTask.schedule(0);
    }

    private static void jsMinify(DataObject context, String content, boolean notify) {
        MinifyProperty minifyProperty = MinifyProperty.getInstance();
        MinifyUtil util = new MinifyUtil();

        try {
            FileObject file = context.getPrimaryFile();
            if (!util.isMinifiedFile(file.getName(), minifyProperty.getPreExtensionJS(), minifyProperty.getSeparatorJS().toString())) {
                String inputFilePath = file.getPath();
                String outputFilePath;

                if (minifyProperty.isNewJSFile() && minifyProperty.getPreExtensionJS() != null && !minifyProperty.getPreExtensionJS().trim().isEmpty()) {
                    outputFilePath = file.getParent().getPath() + File.separator + file.getName() + minifyProperty.getSeparatorJS() + minifyProperty.getPreExtensionJS() + "." + file.getExt();
                } else {
                    outputFilePath = inputFilePath;
                }

                MinifyFileResult minifyFileResult;

                if (content != null) {
                    minifyFileResult = util.compressContent(inputFilePath, content, "text/javascript", outputFilePath, minifyProperty);
                } else {
                    minifyFileResult = util.compress(inputFilePath, "text/javascript", outputFilePath, minifyProperty);
                }

                if (minifyProperty.isEnableOutputLogAlert() && notify) {
                    NotificationDisplayer.getDefault().notify("Successful JS minification", NotificationDisplayer.Priority.NORMAL.getIcon(), String.format("Input JS Files Size: %s Bytes \n"
                            + "After Minifying JS Files Size: %s Bytes \n"
                            + "JS Space Saved %s%%", minifyFileResult.getInputFileSize(), minifyFileResult.getOutputFileSize(), minifyFileResult.getSavedPercentage()), null);
                }
            }
        } catch (HeadlessException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
