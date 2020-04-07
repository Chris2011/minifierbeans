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
        id = "org.netbeans.util.source.minify.CSSMinify")
@ActionRegistration(iconBase = "org/netbeans/minifierbeans/compress.png",
        displayName = "#CTL_CSSMinify")
@ActionReferences({
    @ActionReference(path = "Loaders/text/css/Actions", position = 300, separatorBefore = 250, separatorAfter = 350)
})
@Messages("CTL_CSSMinify=Minify CSS")
public final class CSSMinify implements ActionListener {
    private final DataObject context;
    private final static RequestProcessor RP = new RequestProcessor("CSSMinify", 1, true);

    public CSSMinify(DataObject context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        execute(context, null, true);
    }

    public static void execute(final DataObject context, final String content, final boolean notify) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                cssMinify(context, content, notify);
            }
        };

        final RequestProcessor.Task theTask = RP.create(runnable);
        final ProgressHandle ph = ProgressHandleFactory.createHandle("Minifying CSS " + context.getPrimaryFile().getName(), theTask);

        theTask.addTaskListener(new TaskListener() {
            @Override
            public void taskFinished(org.openide.util.Task task) {
                ph.finish();
            }
        });

        ph.start();
        theTask.schedule(0);
    }

    private static void cssMinify(DataObject context, String content, boolean notify) {
        MinifyProperty minifyProperty = MinifyProperty.getInstance();
        MinifyUtil util = new MinifyUtil();

        try {
            FileObject file = context.getPrimaryFile();

            if (!util.isMinifiedFile(file.getName(), minifyProperty.getPreExtensionCSS())) {
                String inputFilePath = file.getPath();
                String outputFilePath;

                if (minifyProperty.isNewCSSFile() && minifyProperty.getPreExtensionCSS() != null && !minifyProperty.getPreExtensionCSS().trim().isEmpty()) {
                    outputFilePath = file.getParent().getPath() + File.separator + file.getName() + minifyProperty.getPreExtensionCSS() + "." + file.getExt();
                } else {
                    outputFilePath = inputFilePath;
                }

                MinifyFileResult minifyFileResult;

                if (content != null) {
                    minifyFileResult = util.compressContent(inputFilePath, content, "text/css", outputFilePath, minifyProperty);
                } else {
                    minifyFileResult = util.compress(inputFilePath, "text/css", outputFilePath, minifyProperty);
                }

                if (minifyProperty.isEnableOutputLogAlert() && notify) {
                    NotificationDisplayer.getDefault().notify("Successful CSS minification",
                            NotificationDisplayer.Priority.NORMAL.getIcon(), String.format(
                            "Input CSS Files Size: %s Bytes \n"
                            + "CSS Minified Completed Successfully\n"
                            + "After Minifying CSS Files Size: %s Bytes \n"
                            + "CSS Space Saved %s%%", minifyFileResult.getInputFileSize(), minifyFileResult.getOutputFileSize(), minifyFileResult.getSavedPercentage()), null);
                }
            }
        } catch (HeadlessException | IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
