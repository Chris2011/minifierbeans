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
package io.github.chris2011.netbeans.minifierbeans.css.ui.options;

import io.github.chris2011.netbeans.minifierbeans.css.CssOptionsModel;
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
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.netbeans.spi.options.OptionsPanelController;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;

final class CssOptionsPanelController extends OptionsPanelController implements MinificationOptionsKeywordsProvider,
        ActionListener, DocumentListener {
    private CssOptionsPanel panel;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private boolean changed;

    public CssOptionsPanelController() {
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
        return new HelpCtx("io.github.chris2011.netbeans.minifierbeans.css.ui.options.CssOptionsPanelController"); //NOI18N
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
        if (e.getSource() == panel.chbGenerateNewCssFile) {
            panel.txtCssPreExtension.setEnabled(panel.chbGenerateNewCssFile.isSelected());

            changed();
        } else if (e.getSource() == panel.btnHeaderContentHelp) {
            JOptionPane.showMessageDialog(null, "You can automatically add content to all your files.\nBe aware, that any code, that you write here, must be syntax correct.\nIf you just want to add text like copyright stuff, wrap it into comments.");
        } else {
            changed();
        }
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

    private CssOptionsPanel getPanel() {
        if (panel == null) {
            panel = new CssOptionsPanel();

            panel.chbMinifyCssOnSave.addActionListener(this);
            panel.chbGenerateNewCssFile.addActionListener(this);

            panel.txtCssPreExtension.setText(CssOptionsModel.getDefault().getCssPreExtensionOption());
            panel.txtCssPreExtension.getDocument().addDocumentListener(this);
            panel.txtHeaderEditorPaneCss.setText(CssOptionsModel.getDefault().getCssHeaderOption());
            panel.txtHeaderEditorPaneCss.getDocument().addDocumentListener(this);

            panel.btnHeaderContentHelp.addActionListener(this);

            panel.txtCssPreExtension.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent arg0) {
                }

                @Override
                public void focusLost(FocusEvent arg0) {
                    String text = panel.txtCssPreExtension.getText();

                    if (text == null || text.trim().isEmpty()) {
                        text = CssOptionsModel.getDefault().getCssPreExtensionOption();
                    } else {
                        text = text.trim();
                    }

                    panel.txtCssPreExtension.setText(text);
                }
            });

            panel.txtHeaderEditorPaneCss.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent fe) {
                }

                @Override
                public void focusLost(FocusEvent fe) {
                    String text = panel.txtHeaderEditorPaneCss.getText();

                    if (text == null || text.trim().isEmpty()) {
                        text = "";
                    } else {
                        text = text.trim();
                    }

                    panel.txtHeaderEditorPaneCss.setText(text);
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

        panel.chbMinifyCssOnSave.setSelected(CssOptionsModel.getDefault().getMinifyCssOnSaveOption());
        panel.chbGenerateNewCssFile.setSelected(CssOptionsModel.getDefault().getGenerateNewCssFileOption());

        panel.txtCssPreExtension.setText(CssOptionsModel.getDefault().getCssPreExtensionOption());
        panel.txtHeaderEditorPaneCss.setText(CssOptionsModel.getDefault().getCssHeaderOption());
    }

    private void store() {
        getPanel();

        CssOptionsModel.getDefault().setMinifyCssOnSaveOption(panel.chbMinifyCssOnSave.isSelected());
        CssOptionsModel.getDefault().setGenerateNewCssFileOption(panel.chbGenerateNewCssFile.isSelected());

        CssOptionsModel.getDefault().setCssPreExtensionOption(panel.txtCssPreExtension.getText());
        CssOptionsModel.getDefault().setCssHeaderOption(panel.txtHeaderEditorPaneCss.getText());
    }

    private void fireChanged() {
        changed = CssOptionsModel.getDefault().getMinifyCssOnSaveOption() != panel.chbMinifyCssOnSave.isSelected()
                || CssOptionsModel.getDefault().getGenerateNewCssFileOption() != panel.chbGenerateNewCssFile.isSelected()
                || !CssOptionsModel.getDefault().getCssPreExtensionOption().equals(panel.txtCssPreExtension.getText())
                || !CssOptionsModel.getDefault().getCssHeaderOption().equals(panel.txtHeaderEditorPaneCss.getText());
    }
}
