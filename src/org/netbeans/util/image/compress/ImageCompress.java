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

@ActionID(category = "Build",
        id = "org.netbeans.util.image.compress.ImageCompress")
@ActionRegistration(displayName = "#CTL_ImageCompress")
@ActionReferences({
    @ActionReference(path = "Loaders/image/png-gif-jpeg-bmp/Actions", position = 300, separatorBefore = 250, separatorAfter = 350)
})
@Messages("CTL_ImageCompress=Compress")
public final class ImageCompress implements ActionListener {

    private final DataObject context;

    public ImageCompress(DataObject context) {
        this.context = context;
    }
    private final static RequestProcessor RP = new RequestProcessor("ImageCompress", 1, true);

    @Override
    public void actionPerformed(ActionEvent ev) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                compress();
            }
        };
        final RequestProcessor.Task theTask = RP.create(runnable);
        final ProgressHandle ph = ProgressHandleFactory.createHandle("Compressing Image " + context.getPrimaryFile().getName(), theTask);
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

    void compress() {
        ImageUtil imageUtil = new ImageUtil();
        FileObject file = context.getPrimaryFile();
        imageUtil.compress(file.getPath(), file.getParent().getPath() + File.separator + file.getName() + "-min." + file.getExt(), file.getExt());
    }
}
