package io.github.chris2011.netbeans.minifierbeans.util.source.minify.shortkey;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import io.github.chris2011.netbeans.minifierbeans.css.CssMinify;
import io.github.chris2011.netbeans.minifierbeans.html.HtmlMinify;
import io.github.chris2011.netbeans.minifierbeans.javascript.JsMinify;
import io.github.chris2011.netbeans.minifierbeans.folder.FolderOptionsModel;
import io.github.chris2011.netbeans.minifierbeans.util.source.minify.MinifyWebContent;
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
        FolderOptionsModel folderOptionsModel = FolderOptionsModel.getDefault();
        DataObject dob = TopComponent.getRegistry().getActivated().getLookup().lookup(DataObject.class);

        if (dob != null && folderOptionsModel.getEnableShortKeyActionOption()) {
            FileObject fileObject = dob.getPrimaryFile();
            boolean allowAction = false;

            if (folderOptionsModel.getEnableShortKeyActionConfirmBoxOption()) {
                NotifyDescriptor.Confirmation nd
                        = new NotifyDescriptor.Confirmation(
                                "Are you sure to minify " + fileObject.getNameExt() + " ?",
                                "Minify " + fileObject.getNameExt(),
                                NotifyDescriptor.YES_NO_OPTION);
                nd.setOptions(new Object[]{
                    NotifyDescriptor.YES_OPTION,
                    NotifyDescriptor.NO_OPTION});
                if (DialogDisplayer.getDefault().notify(nd).equals(NotifyDescriptor.YES_OPTION)) {
                    allowAction = true;
                }
            }

            if (allowAction) {
                if (fileObject.isFolder()) {
                    targetAction = new MinifyWebContent(dob);
                } else {
                    if (fileObject.getExt().equalsIgnoreCase("js")) {
                        targetAction = new JsMinify(dob);
                    } else if (fileObject.getExt().equalsIgnoreCase("css")) {
                        targetAction = new CssMinify(dob);
                    } else if (fileObject.getExt().equalsIgnoreCase("html") || fileObject.getExt().equalsIgnoreCase("htm")) {
                        targetAction = new HtmlMinify(dob);
                    }
                }
                targetAction.actionPerformed(e);
            }
        }
    }
}
