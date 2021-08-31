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
package io.github.chris2011.netbeans.minifierbeans.util.source.minify;

import io.github.chris2011.netbeans.minifierbeans.folder.FolderOptionsModel;
import io.github.chris2011.netbeans.minifierbeans.util.FileUtils;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.awt.NotificationDisplayer;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.util.NbBundle.Messages;
import org.openide.util.RequestProcessor;
import org.openide.util.Task;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;

@ActionID(category = "Build",
    id = "org.netbeans.util.source.minify.Minify")
@ActionRegistration(iconBase = "io/github/chris2011/netbeans/minifierbeans/compress.png",
    displayName = "#CTL_Minify")
@ActionReferences({
    @ActionReference(path = "Loaders/folder/any/Actions", position = 300, separatorBefore = 250, separatorAfter = 350)
})
@Messages("CTL_Minify=Minify Web Resources")
public final class MinifyWebContent implements ActionListener {
    private final DataObject context;

    public MinifyWebContent(DataObject context) {
        this.context = context;
    }
    private final static RequestProcessor RP = new RequestProcessor("Minify", 1, true);

    @Override
    public void actionPerformed(ActionEvent ev) {
        Runnable runnable = () -> {
            minify();
        };
        final RequestProcessor.Task theTask = RP.create(runnable);
        final ProgressHandle ph = ProgressHandleFactory.createHandle("Minifying CSS, HTML, JavaScript, JSON and XML.", theTask);
        theTask.addTaskListener((Task task) -> {
            ph.finish();
        });
        ph.start();
        theTask.schedule(0);
    }

    public void minify() {
        InputOutput io = IOProvider.getDefault().getIO(Bundle.CTL_Minify(), false);
        MinifyUtil util = new MinifyUtil();

        try {
            long startTime = new Date().getTime();
            FileObject source = context.getPrimaryFile();
            FileObject target = null;

            if (FolderOptionsModel.getDefault().getCreateSeparateBuildFolderOption()) {
                File sourceFolder = FileUtil.toFile(source);
                File targetFolder = FileUtils.getTargetFolder(new File(sourceFolder.getParentFile().getPath() + File.separator + sourceFolder.getName() + "_minified"));
                target = FileUtil.toFileObject(targetFolder);
            } else {
                target = source;
            }

            MinifyResult minifyResult = util.minify(source, target);
            long endTime = new Date().getTime();
            long totalTime = endTime - startTime;

            minifyResult.setDirectories(minifyResult.getDirectories() + 1);
            float jsSpaceSaved = (1 - ((float) minifyResult.getOutputJsFilesSize() / (float) minifyResult.getInputJsFilesSize())) * 100;
            float cssSpaceSaved = (1 - ((float) minifyResult.getOutputCssFilesSize() / (float) minifyResult.getInputCssFilesSize())) * 100;
            float htmlSpaceSaved = (1 - ((float) minifyResult.getOutputHtmlFilesSize() / (float) minifyResult.getInputHtmlFilesSize())) * 100;
            float xmlSpaceSaved = (1 - ((float) minifyResult.getOutputXmlFilesSize() / (float) minifyResult.getInputXmlFilesSize())) * 100;
            float jsonSpaceSaved = (1 - ((float) minifyResult.getOutputJsonFilesSize() / (float) minifyResult.getInputJsonFilesSize())) * 100;

            String cssEval = "", jsEval = "", htmlEval = "", xmlEval = "", jsonEval = "";

            if (!Float.isNaN(jsSpaceSaved)) {
                jsEval = "Input JS Files Size :  " + minifyResult.getInputJsFilesSize() + " Bytes \n"
                        + "After Minifying JS Files Size :  " + minifyResult.getOutputJsFilesSize() + " Bytes \n"
                        + "JS Space Saved " + jsSpaceSaved + "% \n\n";
            }
            if (!Float.isNaN(cssSpaceSaved)) {
                cssEval = "Input CSS Files Size :  " + minifyResult.getInputCssFilesSize() + " Bytes \n"
                        + "After Minifying CSS Files Size :  " + minifyResult.getOutputCssFilesSize() + " Bytes \n"
                        + "CSS Space Saved " + cssSpaceSaved + "% \n\n";
            }
            if (!Float.isNaN(htmlSpaceSaved)) {
                htmlEval = "Input HTML Files Size :  " + minifyResult.getInputHtmlFilesSize() + " Bytes \n"
                        + "After Minifying HTML Files Size :  " + minifyResult.getOutputHtmlFilesSize() + " Bytes \n"
                        + "HTML Space Saved " + htmlSpaceSaved + "% \n\n";
            }
            if (!Float.isNaN(xmlSpaceSaved)) {
                xmlEval = "Input XML Files Size :  " + minifyResult.getInputXmlFilesSize() + " Bytes \n"
                        + "After Minifying XML Files Size :  " + minifyResult.getOutputXmlFilesSize() + " Bytes \n"
                        + "XML Space Saved " + xmlSpaceSaved + "% \n\n";
            }
            if (!Float.isNaN(jsonSpaceSaved)) {
                jsonEval = "Input JSON Files Size :  " + minifyResult.getInputJsonFilesSize() + " Bytes \n"
                        + "After Minifying JSON Files Size :  " + minifyResult.getOutputJsonFilesSize() + " Bytes \n"
                        + "JSON Space Saved " + jsonSpaceSaved + "% \n\n";
            }
            if (FolderOptionsModel.getDefault().getEnableOutputLogAlertOption()) {
                showNotification(minifyResult, jsEval, cssEval, htmlEval, xmlEval, jsonEval, totalTime);
            }
        } catch (HeadlessException | IOException ex) {
            io.getOut().println("Exception: " + ex.toString());
        }
    }

    private void showNotification(MinifyResult minifyResult, String jsEval, String cssEval, String htmlEval, String xmlEval, String jsonEval, long totalTime) throws HeadlessException {
        final String message = "%s Directories Found \n"
                + "%s JS Files Minified \n"
                + "%s CSS Files Minified \n"
                + "%s HTML Files Minified \n"
                + "%s XML Files Minified \n"
                + "%s JSON Files Minified \n\n"
                + "%s%s%s%s%s \n\n"
                + "Total Time - %d ms";

        NotificationDisplayer.getDefault().notify("Successful JS & CSS minification",
                NotificationDisplayer.Priority.NORMAL.getIcon(),
                String.format(message,
                        minifyResult.getDirectories(),
                        minifyResult.getJsFiles(),
                        minifyResult.getCssFiles(),
                        minifyResult.getHtmlFiles(),
                        minifyResult.getXmlFiles(),
                        minifyResult.getJsonFiles(),
                        jsEval,
                        cssEval,
                        htmlEval,
                        xmlEval,
                        jsonEval,
                        totalTime), null);
    }
}
