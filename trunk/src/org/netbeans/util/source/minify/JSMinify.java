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

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JOptionPane;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.netbeans.minify.ui.MinifyProperty;
import org.openide.loaders.DataObject;

import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.filesystems.FileObject;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.openide.util.RequestProcessor;
import org.openide.util.TaskListener;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;

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
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                jsMinify();
            }
        };
        final RequestProcessor.Task theTask = RP.create(runnable);
        final ProgressHandle ph = ProgressHandleFactory.createHandle("Minifying JS " + context.getPrimaryFile().getName(), theTask);
        theTask.addTaskListener(new TaskListener() {
            @Override
            public void taskFinished(org.openide.util.Task task) {
                //JOptionPane.showMessageDialog(null, "Image Compressed Successfully");
                ph.finish();
            }
        });
        ph.start();
        theTask.schedule(0);
    }

    public void jsMinify() {
        MinifyProperty minifyProperty = MinifyProperty.getInstance();
        MinifyUtil util = new MinifyUtil();
        try {
            FileObject file = context.getPrimaryFile();
            String inputFilePath = file.getPath();
            String outputFilePath;

            if (minifyProperty.isNewJSFile() && minifyProperty.getPreExtensionJS() != null && !minifyProperty.getPreExtensionJS().trim().isEmpty()) {
                outputFilePath = file.getParent().getPath() + File.separator + file.getName() + minifyProperty.getSeparatorJS() + minifyProperty.getPreExtensionJS() + "." + file.getExt();
            } else {
                outputFilePath = inputFilePath;
            }

            MinifyFileResult minifyFileResult = util.compressJavaScript(inputFilePath, outputFilePath, minifyProperty);

            if (minifyProperty.isEnableOutputLogAlert()) {
                JOptionPane.showMessageDialog(null, "JS Minified Completed Successfully\n"
                        + "Input JS Files Size : " + minifyFileResult.getInputFileSize() + "Bytes \n"
                        + "After Minifying JS Files Size : " + minifyFileResult.getOutputFileSize() + "Bytes \n"
                        + "JS Space Saved " + minifyFileResult.getSavedPercentage() + "%");
            }
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
