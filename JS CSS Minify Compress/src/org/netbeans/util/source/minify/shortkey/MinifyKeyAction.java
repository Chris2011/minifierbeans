/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.util.source.minify.shortkey;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.netbeans.minify.ui.MinifyProperty;
import org.netbeans.util.source.minify.CSSMinify;
import org.netbeans.util.source.minify.HTMLMinify;
import org.netbeans.util.source.minify.JSMinify;
import org.netbeans.util.source.minify.Minify;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

@ActionID(
        category = "Build",
        id = "org.netbeans.util.source.minify.shortkey.MinifyKeyAction")
@ActionRegistration(
        displayName = "#CTL_MinifyKeyAction")
@ActionReference(path = "Shortcuts", name = "O-M")
@Messages("CTL_MinifyKeyAction=Minify Key Action")
public final class MinifyKeyAction implements ActionListener {

    ActionListener targetAction;

    @Override
    public void actionPerformed(ActionEvent e) {
//        Project project = TopComponent.getRegistry().getActivated().getLookup().lookup(Project.class);
//        if (project != null) {
//            System.out.println("project : " + project);
//        }
        MinifyProperty minifyProperty = MinifyProperty.getInstance();
        DataObject dob = TopComponent.getRegistry().getActivated().getLookup().lookup(DataObject.class);
        if (dob != null && minifyProperty.isEnableShortKeyAction()) {
            FileObject fileObject = dob.getPrimaryFile();//       p = FileOwnerQuery.getOwner(fo);
            boolean allowAction = true;
            if (minifyProperty.isEnableShortKeyActionConfirmBox()) {
                NotifyDescriptor.Confirmation nd =
                        new NotifyDescriptor.Confirmation(
                        "Are you sure to minify " + fileObject.getNameExt() + " ?",
                        "Minify " + fileObject.getNameExt(),
                        NotifyDescriptor.YES_NO_OPTION);
                nd.setOptions(new Object[]{
                    NotifyDescriptor.YES_OPTION,
                    NotifyDescriptor.NO_OPTION});
                if (DialogDisplayer.getDefault().notify(nd).equals(NotifyDescriptor.NO_OPTION)) {
                    allowAction = false;
                }
            }
            if (allowAction) {
                if (fileObject.isFolder()) {
                    targetAction = new Minify(dob);
                } else {
                    if (fileObject.getExt().equalsIgnoreCase("js")) {
                        targetAction = new JSMinify(dob);
                    } else if (fileObject.getExt().equalsIgnoreCase("css")) {
                        targetAction = new CSSMinify(dob);
                    } else if (fileObject.getExt().equalsIgnoreCase("html") || fileObject.getExt().equalsIgnoreCase("htm")) {
                        targetAction = new HTMLMinify(dob);
                    }
                }
                targetAction.actionPerformed(e);
            }
        }
    }
}
