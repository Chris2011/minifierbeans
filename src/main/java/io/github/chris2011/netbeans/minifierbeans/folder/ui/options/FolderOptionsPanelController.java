/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package io.github.chris2011.netbeans.minifierbeans.folder.ui.options;

import io.github.chris2011.netbeans.minifierbeans.folder.FolderOptionsModel;
import io.github.chris2011.netbeans.minifierbeans.html.HtmlOptionsModel;
import io.github.chris2011.netbeans.minifierbeans.ui.options.MinificationOptionsKeywordsProvider;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JComponent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.netbeans.spi.options.OptionsPanelController;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;

public final class FolderOptionsPanelController extends OptionsPanelController implements MinificationOptionsKeywordsProvider,
        ActionListener, DocumentListener {
    private FolderOptionsPanel panel;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private boolean changed;

    public FolderOptionsPanelController() {
    }

    @Override
    public void update() {
        load();
        changed = false;
    }

    @Override
    public void applyChanges() {
        if (!validateFields()) {
            return;
        }

        store();
        changed = false;
    }

    @Override
    public void cancel() {
        // need not do anything special, if no changes have been persisted yet
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public boolean isChanged() {
        return changed;
    }

    @Override
    public HelpCtx getHelpCtx() {
        return new HelpCtx("io.github.chris2011.netbeans.minifierbeans.folder.ui.options.FolderOptionsPanelController"); //NOI18N
    }

    @Override
    public JComponent getComponent(Lookup masterLookup) {
        return getPanel();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener l) {
        pcs.removePropertyChangeListener(l);
    }

    @Override
    public boolean acceptKeywords(List<String> keywords) {
        Set<String> allKeywords = new HashSet<>(panel.getKeywords());
        allKeywords.retainAll(keywords);
        return !allKeywords.isEmpty();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panel.chbEnableShortKeyAction) {
            panel.chbEnableShortKeyActionConfirmBox.setEnabled(panel.chbEnableShortKeyAction.isSelected());
            panel.chbEnableShortKeyActionConfirmBox.setSelected(panel.chbEnableShortKeyAction.isSelected());
        }
        
        changed();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        changed();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        changed();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }

    private Boolean validateFields() {
        return true;
    }

    private FolderOptionsPanel getPanel() {
        if (panel == null) {
            panel = new FolderOptionsPanel();

            panel.chbCreateSeparateBuildFolder.addActionListener(this);

            panel.txtExtensionToMinifyRegex.setText(FolderOptionsModel.getDefault().getFileExtensionsRegexOption());
            panel.txtExtensionToMinifyRegex.getDocument().addDocumentListener(this);
            panel.chbMinifyAllFiles.addActionListener(this);

            panel.chbAddLogToFile.addActionListener(this);
            panel.chbEnableOutputLogAlert.addActionListener(this);

            panel.chbEnableShortKeyAction.addActionListener(this);
            panel.chbEnableShortKeyActionConfirmBox.addActionListener(this);
        }

        return panel;
    }

    private void changed() {
        fireChanged();

        if (!changed) {
            pcs.firePropertyChange(OptionsPanelController.PROP_CHANGED, false, true);
        }

        pcs.firePropertyChange(OptionsPanelController.PROP_VALID, null, null);
    }

    private void load() {
        getPanel();

        panel.chbCreateSeparateBuildFolder.setSelected(FolderOptionsModel.getDefault().getCreateSeparateBuildFolderOption());

        panel.txtExtensionToMinifyRegex.setText(FolderOptionsModel.getDefault().getFileExtensionsRegexOption());
        panel.chbMinifyAllFiles.setSelected(FolderOptionsModel.getDefault().getMinifyAllFilesOption());

        panel.chbAddLogToFile.setSelected(FolderOptionsModel.getDefault().getAddLogToFileOption());
        panel.chbEnableOutputLogAlert.setSelected(FolderOptionsModel.getDefault().getEnableOutputLogAlertOption());

        panel.chbEnableShortKeyAction.setSelected(FolderOptionsModel.getDefault().getEnableShortKeyActionOption());
        panel.chbEnableShortKeyActionConfirmBox.setSelected(FolderOptionsModel.getDefault().getEnableShortKeyActionConfirmBoxOption());
    }

    private void store() {
        getPanel();

        FolderOptionsModel.getDefault().setCreateSeparateBuildFolderOption(panel.chbCreateSeparateBuildFolder.isSelected());

        FolderOptionsModel.getDefault().setFileExtensionsRegexOption(panel.txtExtensionToMinifyRegex.getText());
        FolderOptionsModel.getDefault().setMinifyAllFilesOption(panel.chbMinifyAllFiles.isSelected());

        FolderOptionsModel.getDefault().setAddLogToFileOption(panel.chbAddLogToFile.isSelected());
        FolderOptionsModel.getDefault().setEnableOutputLogAlertOption(panel.chbEnableOutputLogAlert.isSelected());

        FolderOptionsModel.getDefault().setEnableShortKeyActionOption(panel.chbEnableShortKeyAction.isSelected());
        FolderOptionsModel.getDefault().setEnableShortKeyActionConfirmBoxOption(panel.chbEnableShortKeyActionConfirmBox.isSelected());
    }

    private void fireChanged() {
        changed = FolderOptionsModel.getDefault().getCreateSeparateBuildFolderOption() != panel.chbCreateSeparateBuildFolder.isSelected()
                || !FolderOptionsModel.getDefault().getFileExtensionsRegexOption().equals(panel.txtExtensionToMinifyRegex.getText())
                || FolderOptionsModel.getDefault().getMinifyAllFilesOption() != panel.chbMinifyAllFiles.isSelected()
                || FolderOptionsModel.getDefault().getAddLogToFileOption() != panel.chbAddLogToFile.isSelected()
                || FolderOptionsModel.getDefault().getEnableOutputLogAlertOption() != panel.chbEnableOutputLogAlert.isSelected()
                || FolderOptionsModel.getDefault().getEnableShortKeyActionOption() != panel.chbEnableShortKeyAction.isSelected()
                || FolderOptionsModel.getDefault().getEnableShortKeyActionConfirmBoxOption() != panel.chbEnableShortKeyActionConfirmBox.isSelected();
    }
}
