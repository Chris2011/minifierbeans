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
package io.github.chris2011.netbeans.minifierbeans.json;

import io.github.chris2011.netbeans.minifierbeans.folder.FolderOptionsModel;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import io.github.chris2011.netbeans.minifierbeans.util.source.minify.MinifyFileResult;
import io.github.chris2011.netbeans.minifierbeans.util.source.minify.MinifyUtil;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.awt.NotificationDisplayer;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.openide.util.RequestProcessor;

@ActionID(category = "Build",
    id = "org.netbeans.util.source.minify.JSONMinify")
@ActionRegistration(iconBase = "io/github/chris2011/netbeans/minifierbeans/util/source/minify/compress.png",
    displayName = "#CTL_JSONMinify")
@ActionReferences({
    @ActionReference(path = "Loaders/text/x-json/Actions", position = 300, separatorBefore = 250, separatorAfter = 350)
})
@Messages("CTL_JSONMinify=Minify JSON")
public final class JsonMinify implements ActionListener {

    private final DataObject context;

    public JsonMinify(DataObject context) {
        this.context = context;
    }
    private final static RequestProcessor RP = new RequestProcessor("JSONMinify", 1, true);

    @Override
    public void actionPerformed(ActionEvent ev) {
        execute(context, null, true);
    }

    public static void execute(final DataObject context, final String content, final boolean notify) {
        Runnable runnable = () -> {
            jsonMinify(context, content, notify);
        };
        final RequestProcessor.Task theTask = RP.create(runnable);
        final ProgressHandle ph = ProgressHandleFactory.createHandle("Minifying JSON " + context.getPrimaryFile().getName(), theTask);
        theTask.addTaskListener((org.openide.util.Task task) -> {
            ph.finish();
        });
        ph.start();
        theTask.schedule(0);
    }

    private static void jsonMinify(DataObject context, String content, boolean notify) {
        JsonOptionsModel jsonOptionsModel = JsonOptionsModel.getDefault();
        FolderOptionsModel folderOptionsModel = FolderOptionsModel.getDefault();

        MinifyUtil util = new MinifyUtil();

        try {
            FileObject file = context.getPrimaryFile();
            if (!util.isMinifiedFile(file.getName(), jsonOptionsModel.getJsonPreExtensionOption())) {
                String inputFilePath = file.getPath();
                String outputFilePath;

                if (jsonOptionsModel.getGenerateNewJsonFileOption() && jsonOptionsModel.getJsonPreExtensionOption() != null && !jsonOptionsModel.getJsonPreExtensionOption().trim().isEmpty()) {
                    outputFilePath = file.getParent().getPath() + File.separator + file.getName() + jsonOptionsModel.getJsonPreExtensionOption() + "." + file.getExt();
                } else {
                    outputFilePath = inputFilePath;
                }

                MinifyFileResult minifyFileResult;
                if (content != null) {
                    minifyFileResult = util.compressContent(inputFilePath, content, "text/x-json", outputFilePath);
                } else {
                    minifyFileResult = util.compress(inputFilePath, "text/x-json", outputFilePath);
                }
                if (folderOptionsModel.getEnableOutputLogAlertOption() && notify) {
                    NotificationDisplayer.getDefault().notify("Successful JSON minification", NotificationDisplayer.Priority.NORMAL.getIcon(), String.format("Input JSON Files Size: %s Bytes \n"
                            + "After Minifying JSON Files Size:  %s Bytes \n"
                            + "JSON Space Saved %s%%", minifyFileResult.getInputFileSize(), minifyFileResult.getOutputFileSize(), minifyFileResult.getSavedPercentage()), null);
                }
            }
        } catch (HeadlessException | IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
