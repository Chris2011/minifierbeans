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
package io.github.chris2011.netbeans.minifierbeans.xml.ui.options;

import io.github.chris2011.netbeans.minifierbeans.ui.options.MinificationOptionsKeywordsProvider;
import io.github.chris2011.netbeans.minifierbeans.xml.XmlOptionsModel;
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

public final class XmlOptionsPanelController extends OptionsPanelController implements MinificationOptionsKeywordsProvider,
        ActionListener, DocumentListener {

    private XmlOptionsPanel panel;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private boolean changed;

    public XmlOptionsPanelController() {
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
        return new HelpCtx("io.github.chris2011.netbeans.minifierbeans.xml.ui.options.XmlOptionsPanelController"); //NOI18N
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
        if (e.getSource() == panel.chbGenerateNewXmlFile) {
            panel.txtXmlPreExtension.setEnabled(panel.chbGenerateNewXmlFile.isSelected());
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

    private XmlOptionsPanel getPanel() {
        if (panel == null) {
            panel = new XmlOptionsPanel();

            panel.txtXmlMinifierFlags.setText(XmlOptionsModel.getDefault().getXmlMinifierFlagsOption());
            panel.txtXmlMinifierFlags.getDocument().addDocumentListener(this);

            panel.chbMinifyOnSave.addActionListener(this);
            panel.chbGenerateNewXmlFile.addActionListener(this);

            panel.txtXmlPreExtension.setText(XmlOptionsModel.getDefault().getXmlPreExtensionOption());
            panel.txtXmlPreExtension.getDocument().addDocumentListener(this);
            panel.txtHeaderEditorPaneXml.setText(XmlOptionsModel.getDefault().getXmlHeaderOption());
            panel.txtHeaderEditorPaneXml.getDocument().addDocumentListener(this);
            
            panel.txtXmlPreExtension.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent arg0) {
                }

                @Override
                public void focusLost(FocusEvent arg0) {
                    String text = panel.txtXmlPreExtension.getText();

                    if (text == null || text.trim().isEmpty()) {
                        text = XmlOptionsModel.getDefault().getXmlPreExtensionOption();
                    } else {
                        text = text.trim();
                    }

                    panel.txtXmlPreExtension.setText(text);
                }
            });

            panel.txtHeaderEditorPaneXml.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent fe) {
                }

                @Override
                public void focusLost(FocusEvent fe) {
                    String text = panel.txtHeaderEditorPaneXml.getText();

                    if (text == null || text.trim().isEmpty()) {
                        text = "";
                    } else {
                        text = text.trim();
                    }

                    panel.txtHeaderEditorPaneXml.setText(text);
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

        panel.txtXmlMinifierFlags.setText(XmlOptionsModel.getDefault().getXmlMinifierFlagsOption());

        panel.chbMinifyOnSave.setSelected(XmlOptionsModel.getDefault().getMinifyXmlOnSaveOption());
        panel.chbGenerateNewXmlFile.setSelected(XmlOptionsModel.getDefault().getGenerateNewXmlFileOption());

        panel.txtXmlPreExtension.setText(XmlOptionsModel.getDefault().getXmlPreExtensionOption());
        panel.txtHeaderEditorPaneXml.setText(XmlOptionsModel.getDefault().getXmlHeaderOption());
    }

    private void store() {
        getPanel();

        XmlOptionsModel.getDefault().setXmlMinifierFlags(panel.txtXmlMinifierFlags.getText());

        XmlOptionsModel.getDefault().setMinifyXmlOnSaveOption(panel.chbMinifyOnSave.isSelected());
        XmlOptionsModel.getDefault().setGenerateNewXmlFileOption(panel.chbGenerateNewXmlFile.isSelected());

        XmlOptionsModel.getDefault().setXmlPreExtensionOption(panel.txtXmlPreExtension.getText());
        XmlOptionsModel.getDefault().setXmlHeaderOption(panel.txtHeaderEditorPaneXml.getText());
    }

    private void fireChanged() {
        changed = !XmlOptionsModel.getDefault().getXmlMinifierFlagsOption().equals(panel.txtXmlMinifierFlags.getText())
                || XmlOptionsModel.getDefault().getMinifyXmlOnSaveOption() != panel.chbMinifyOnSave.isSelected()
                || XmlOptionsModel.getDefault().getGenerateNewXmlFileOption() != panel.chbGenerateNewXmlFile.isSelected()
                || !XmlOptionsModel.getDefault().getXmlPreExtensionOption().equals(panel.txtXmlPreExtension.getText())
                || !XmlOptionsModel.getDefault().getXmlHeaderOption().equals(panel.txtHeaderEditorPaneXml.getText());
    }
}
