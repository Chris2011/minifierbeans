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
package org.netbeans.util.image.compress;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.util.NbBundle.Messages;
import org.openide.util.RequestProcessor;
import org.openide.util.TaskListener;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;

@ActionID(category = "Build",
        id = "org.netbeans.util.image.compress.Base64Decode")
@ActionRegistration(displayName = "#CTL_Base64Decode")
@ActionReferences({
    @ActionReference(path = "Loaders/content/unknown/Actions", position = 300, separatorBefore = 250, separatorAfter = 350)
})
@Messages("CTL_Base64Decode=Base64 Decode")
public final class Base64Decode implements ActionListener {

    private final DataObject context;

    public Base64Decode(DataObject context) {
        this.context = context;
    }
    private final static RequestProcessor RP = new RequestProcessor("Base64Decode", 1, true);

    public void actionPerformed(ActionEvent ev) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                decode();
            }
        };
        final RequestProcessor.Task theTask = RP.create(runnable);
        final ProgressHandle ph = ProgressHandleFactory.createHandle("Base64 Decoding Image " + context.getPrimaryFile().getName(), theTask);
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

    void decode() {
        InputOutput io = IOProvider.getDefault().getIO(Bundle.CTL_Base64Encode(), false);
        ImageUtil imageUtil = new ImageUtil();
        try {
            FileObject file = context.getPrimaryFile();
            if (file.getExt().equalsIgnoreCase("ENCODE")) {
                File newFile = new File(file.getPath());
                String fileType = file.getName().substring(file.getName().lastIndexOf('.') + 1);
                imageUtil.decodeToImage(FileUtils.readFileToString(newFile), file.getParent().getPath() + File.separator + file.getName(), fileType);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid file to decode", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } catch (IOException ex) {
            io.getOut().println("Exception: " + ex.toString());
        }
    }
}
