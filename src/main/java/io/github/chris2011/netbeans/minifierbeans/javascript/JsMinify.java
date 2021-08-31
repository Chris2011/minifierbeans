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
package io.github.chris2011.netbeans.minifierbeans.javascript;

import io.github.chris2011.netbeans.minifierbeans.folder.FolderOptionsModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import org.netbeans.api.progress.ProgressHandle;
import io.github.chris2011.netbeans.minifierbeans.util.FileUtils;
import io.github.chris2011.netbeans.minifierbeans.util.source.minify.MinifyFileResult;
import io.github.chris2011.netbeans.minifierbeans.util.source.minify.MinifyUtil;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.swing.JOptionPane;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
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
import org.openide.windows.TopComponent;

@ActionID(category = "Build",
        id = "org.netbeans.util.source.minify.JSMinify")
@ActionRegistration(iconBase = "io/github/chris2011/netbeans/minifierbeans/util/source/minify/compress.png",
        displayName = "#CTL_JSMinify")
@ActionReferences({
    @ActionReference(path = "Loaders/text/javascript/Actions", position = 200, separatorBefore = 150, separatorAfter = 250)
})
@Messages("CTL_JSMinify=Minify JS")
public final class JsMinify implements ActionListener {

    private final DataObject context;

    public JsMinify(DataObject context) {
        this.context = context;
    }
    private final static RequestProcessor RP = new RequestProcessor("JSMinify", 1, true);

    @Override
    public void actionPerformed(ActionEvent ev) {
        execute(context, null, true);
    }

    public static void execute(final DataObject context, final String content, final boolean notify) {
        Runnable runnable = () -> {
            jsMinify(context, content, notify);
        };

        final RequestProcessor.Task theTask = RP.create(runnable);
        final ProgressHandle ph = ProgressHandle.createHandle("Minifying JS " + context.getPrimaryFile().getName(), theTask);

        theTask.addTaskListener((org.openide.util.Task task) -> {
            ph.finish();
        });

        ph.start();
        theTask.schedule(0);
    }

    private static void jsMinify(DataObject context, String content, boolean notify) {
        Project project = TopComponent.getRegistry().getActivated().getLookup().lookup(Project.class);
        FileObject file = context.getPrimaryFile();

        JsOptionsModel jsOptionsModel = JsOptionsModel.getDefault();
        FolderOptionsModel folderOptionsModel = FolderOptionsModel.getDefault();

        MinifyUtil util = new MinifyUtil();
        MinifyFileResult minifyFileResult = new MinifyFileResult();

        if (!util.isMinifiedFile(file.getName(), jsOptionsModel.getJsPreExtensionOption())) {
            String inputFilePath = file.getPath();
            String outputFilePath;

            if (jsOptionsModel.getGenerateNewJsFileOption() && jsOptionsModel.getJsPreExtensionOption() != null && !jsOptionsModel.getJsPreExtensionOption().trim().isEmpty()) {
                outputFilePath = file.getParent().getPath() + "/" + file.getName() + jsOptionsModel.getJsPreExtensionOption() + "." + file.getExt();
            } else {
                outputFilePath = inputFilePath;
            }

            File inputFile = new File(inputFilePath);
            File outputFile = new File(outputFilePath);
            minifyFileResult.setInputFileSize(inputFile.length());

            StringWriter outputWriter = new StringWriter();

            outputWriter.flush();

            if (project == null) {
                project = FileOwnerQuery.getOwner(file);
            }

            GoogleClosureCompilerCliExecutable googleClosureCompilerCliExecutable = GoogleClosureCompilerCliExecutable.getDefault(project);
            Future<Integer> task = googleClosureCompilerCliExecutable.generate(inputFile, outputFile, jsOptionsModel.getClosureCompilerFlagsOption());

            try {
                task.get(1, TimeUnit.MINUTES);

                minifyFileResult.setOutputFileSize(outputFile.length());

                if (folderOptionsModel.getEnableOutputLogAlertOption() && notify) {
                    NotificationDisplayer.getDefault().notify("Successful JS minification",
                            NotificationDisplayer.Priority.NORMAL.getIcon(), String.format(
                            "Input JS Files Size: %s Bytes \n"
                            + "JS Minified Completed Successfully\n"
                            + "After Minifying JS Files Size: %s Bytes \n"
                            + "JS Space Saved %s%%", minifyFileResult.getInputFileSize(), minifyFileResult.getOutputFileSize(), minifyFileResult.getSavedPercentage()), null);
                }
            } catch (NullPointerException ex) {
                ex.printStackTrace();

                NotificationDisplayer.getDefault().notify("Error on CLI execution", NotificationDisplayer.Priority.NORMAL.getIcon(), "Something went wrong. Please click this link to download and extract the binaries again.", new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        RP.post(() -> {
                            try {
                                Path customPackagesFolder = Paths.get(System.getProperty("user.home") + "/.netbeans/minifierbeans/custom-packages");

                                if (Files.exists(customPackagesFolder) && FileUtils.deleteDirectory(customPackagesFolder.toFile())) {
                                    FileUtils.downloadFile(System.getProperty("user.home"));
                                }
                            } catch (IOException ex1) {
                                ex1.printStackTrace();
                            }
                        });
                    }
                });
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException | TimeoutException ex) {
                Exceptions.printStackTrace(ex);
            }

//                if (content != null) {
//                    minifyFileResult = util.compressContent(inputFilePath, content, "text/javascript", outputFilePath, minifyProperty);
//                } else {
//                    minifyFileResult = util.compress(inputFilePath, "text/javascript", outputFilePath, minifyProperty);
//                }
        }
    }
}
