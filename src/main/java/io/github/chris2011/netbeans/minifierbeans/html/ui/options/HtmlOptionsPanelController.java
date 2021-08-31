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
package io.github.chris2011.netbeans.minifierbeans.html.ui.options;

import io.github.chris2011.netbeans.minifierbeans.html.HtmlOptionsModel;
import io.github.chris2011.netbeans.minifierbeans.ui.options.MinificationOptionsKeywordsProvider;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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

public final class HtmlOptionsPanelController extends OptionsPanelController implements MinificationOptionsKeywordsProvider,
        ActionListener, DocumentListener {
    private HtmlOptionsPanel panel;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private boolean changed;

    public HtmlOptionsPanelController() {
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
        return new HelpCtx("io.github.chris2011.netbeans.minifierbeans.html.ui.options.HtmlOptionsPanelController"); //NOI18N
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
        if (e.getSource() == panel.chbGenerateNewHtmlFile) {
            panel.txtHtmlPreExtension.setEnabled(panel.chbGenerateNewHtmlFile.isSelected());
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

    private HtmlOptionsPanel getPanel() {
        if (panel == null) {
            panel = new HtmlOptionsPanel();
            
            panel.txtHtmlMinifierFlags.setText(HtmlOptionsModel.getDefault().getHtmlMinifierFlagsOption());
            panel.txtHtmlMinifierFlags.getDocument().addDocumentListener(this);

            panel.chbMinifyHtmlOnSave.addActionListener(this);
            panel.chbGenerateNewHtmlFile.addActionListener(this);

            panel.txtHtmlPreExtension.setText(HtmlOptionsModel.getDefault().getHtmlPreExtensionOption());
            panel.txtHtmlPreExtension.getDocument().addDocumentListener(this);
            panel.txtHeaderEditorPaneHtml.setText(HtmlOptionsModel.getDefault().getHtmlHeaderOption());
            panel.txtHeaderEditorPaneHtml.getDocument().addDocumentListener(this);
            
            panel.txtHtmlPreExtension.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent arg0) {
                }

                @Override
                public void focusLost(FocusEvent arg0) {
                    String text = panel.txtHtmlPreExtension.getText();

                    if (text == null || text.trim().isEmpty()) {
                        text = HtmlOptionsModel.getDefault().getHtmlPreExtensionOption();
                    } else {
                        text = text.trim();
                    }

                    panel.txtHtmlPreExtension.setText(text);
                }
            });

            panel.txtHeaderEditorPaneHtml.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent fe) {
                }

                @Override
                public void focusLost(FocusEvent fe) {
                    String text = panel.txtHeaderEditorPaneHtml.getText();

                    if (text == null || text.trim().isEmpty()) {
                        text = "";
                    } else {
                        text = text.trim();
                    }

                    panel.txtHeaderEditorPaneHtml.setText(text);
                }
            });
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
        
        panel.txtHtmlMinifierFlags.setText(HtmlOptionsModel.getDefault().getHtmlMinifierFlagsOption());

        panel.chbMinifyHtmlOnSave.setSelected(HtmlOptionsModel.getDefault().getMinifyHtmlOnSaveOption());
        panel.chbGenerateNewHtmlFile.setSelected(HtmlOptionsModel.getDefault().getGenerateNewHtmlFileOption());

        panel.txtHtmlPreExtension.setText(HtmlOptionsModel.getDefault().getHtmlPreExtensionOption());
        panel.txtHeaderEditorPaneHtml.setText(HtmlOptionsModel.getDefault().getHtmlHeaderOption());
    }

    private void store() {
        getPanel();

        HtmlOptionsModel.getDefault().setHtmlMinifierFlagsOption(panel.txtHtmlMinifierFlags.getText());
        
        HtmlOptionsModel.getDefault().setMinifyHtmlOnSaveOption(panel.chbMinifyHtmlOnSave.isSelected());
        HtmlOptionsModel.getDefault().setGenerateNewHtmlFileOption(panel.chbGenerateNewHtmlFile.isSelected());

        HtmlOptionsModel.getDefault().setHtmlPreExtensionOption(panel.txtHtmlPreExtension.getText());
        HtmlOptionsModel.getDefault().setHtmlHeaderOption(panel.txtHeaderEditorPaneHtml.getText());
    }

    private void fireChanged() {
        changed = !HtmlOptionsModel.getDefault().getHtmlMinifierFlagsOption().equals(panel.txtHtmlMinifierFlags.getText())
                || HtmlOptionsModel.getDefault().getMinifyHtmlOnSaveOption() != panel.chbMinifyHtmlOnSave.isSelected()
                || HtmlOptionsModel.getDefault().getGenerateNewHtmlFileOption() != panel.chbGenerateNewHtmlFile.isSelected()
                || !HtmlOptionsModel.getDefault().getHtmlPreExtensionOption().equals(panel.txtHtmlPreExtension.getText())
                || !HtmlOptionsModel.getDefault().getHtmlHeaderOption().equals(panel.txtHeaderEditorPaneHtml.getText());
    }
}
