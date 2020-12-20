package io.github.chris2011.netbeans.minifierbeans.xml.ui.options;

import java.awt.EventQueue;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import io.github.chris2011.netbeans.minifierbeans.project.ui.options.ProjectOptionsPanel;
import io.github.chris2011.netbeans.minifierbeans.ui.MinifyProperty;
import org.openide.util.ChangeSupport;

public final class XmlOptionsPanel extends JPanel implements ChangeListener {

    private MinifyProperty minifyProperty;

    private static final long serialVersionUID = 1L;
    private final ChangeSupport changeSupport = new ChangeSupport(this);

    public XmlOptionsPanel() {
        assert EventQueue.isDispatchThread();

        initComponents();

        init();
    }

    private void init() {
        final ProjectOptionsPanel projectOptionsPanel = new ProjectOptionsPanel();

        minifyProperty = MinifyProperty.getInstance();

        newXMLFile.setSelected(minifyProperty.isNewXMLFile());
        preExtensionXML.setEnabled(minifyProperty.isNewXMLFile());

        projectOptionsPanel.skipPreExtensionXML.setEnabled(minifyProperty.isNewXMLFile());
        minifyProperty.setSkipPreExtensionXML(minifyProperty.isNewXMLFile());
        preExtensionXML.setText(minifyProperty.getPreExtensionXML());

        autoMinifyXML.setSelected(minifyProperty.isAutoMinifyXML());

        xmlMinifierFlagsTextField.setText(minifyProperty.getCompilerFlagsXML());

        xmlMinifierFlagsTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent arg0) {

            }

            @Override
            public void focusLost(FocusEvent arg0) {
                String text = xmlMinifierFlagsTextField.getText();

                if (text == null || text.trim().isEmpty()) {
                    text = "";
                    xmlMinifierFlagsTextField.setText("");
                } else {
                    text = text.trim();
                }

                minifyProperty.setCompilerFlagsXML(text);
            }
        });

        headerEditorPaneXML.setText(minifyProperty.getHeaderXML());
        headerEditorPaneXML.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                String text = headerEditorPaneXML.getText();
                if (text == null || text.trim().isEmpty()) {
                    text = "";
                    headerEditorPaneXML.setText("");
                } else {
                    text = text.trim();
                }
                minifyProperty.setHeaderXML(text);
            }
        });
        this.autoMinifyXML.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setAutoMinifyXML(Boolean.TRUE);
                } else {
                    minifyProperty.setAutoMinifyXML(Boolean.FALSE);
                }
            }
        });

        this.newXMLFile.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setNewXMLFile(Boolean.TRUE);
                    minifyProperty.setPreExtensionXML(".min");
                    preExtensionXML.setText(".min");
                    preExtensionXML.setEnabled(Boolean.TRUE);
                    if (minifyProperty.isBuildXMLMinify() && minifyProperty.isNewXMLFile()) {
                        projectOptionsPanel.skipPreExtensionXML.setEnabled(Boolean.TRUE);
                        minifyProperty.setSkipPreExtensionXML(Boolean.TRUE);
                        projectOptionsPanel.skipPreExtensionXML.setSelected(Boolean.TRUE);
                    }

                } else {
                    minifyProperty.setNewXMLFile(Boolean.FALSE);
                    preExtensionXML.setEnabled(Boolean.FALSE);
                    projectOptionsPanel.skipPreExtensionXML.setEnabled(Boolean.FALSE);
                    minifyProperty.setSkipPreExtensionXML(Boolean.FALSE);
                    projectOptionsPanel.skipPreExtensionXML.setSelected(Boolean.FALSE);
                }
            }
        });

        this.preExtensionXML.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                String text = preExtensionXML.getText();
                if (text == null || text.trim().isEmpty()) {
                    text = ".min";
                    preExtensionXML.setText(text);
                } else {
                    text = text.trim();
                }
                minifyProperty.setPreExtensionXML(text);
            }
        });
    }

    public static XmlOptionsPanel create() {
        XmlOptionsPanel panel = new XmlOptionsPanel();

        return panel;
    }

    public void addChangeListener(ChangeListener listener) {
        changeSupport.addChangeListener(listener);
    }

    public void removeChangeListener(ChangeListener listener) {
        changeSupport.removeChangeListener(listener);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        xmlMinifierLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        newXMLFile = new javax.swing.JCheckBox();
        preExtensionXML = new javax.swing.JTextField();
        autoMinifyXML = new javax.swing.JCheckBox();
        extLabel = new javax.swing.JLabel();
        xmlMinifierFlagsLabel = new javax.swing.JLabel();
        xmlMinifierFlagsTextField = new javax.swing.JTextField();
        xmlMinifierArgumentsHintsLabel = new javax.swing.JLabel();
        headerLabelXML = new javax.swing.JLabel();
        headerScrollPaneXML = new javax.swing.JScrollPane();
        headerEditorPaneXML = new javax.swing.JEditorPane();

        org.openide.awt.Mnemonics.setLocalizedText(xmlMinifierLabel, org.openide.util.NbBundle.getMessage(XmlOptionsPanel.class, "XmlOptionsPanel.xmlMinifierLabel.text")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(xmlMinifierLabel)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(xmlMinifierLabel)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jPanel2.setPreferredSize(new java.awt.Dimension(762, 86));

        newXMLFile.setBackground(new java.awt.Color(255, 255, 255));
        org.openide.awt.Mnemonics.setLocalizedText(newXMLFile, org.openide.util.NbBundle.getMessage(XmlOptionsPanel.class, "XmlOptionsPanel.newXMLFile.text")); // NOI18N
        newXMLFile.setContentAreaFilled(false);

        preExtensionXML.setText(org.openide.util.NbBundle.getMessage(XmlOptionsPanel.class, "XmlOptionsPanel.preExtensionXML.text")); // NOI18N

        autoMinifyXML.setBackground(new java.awt.Color(255, 255, 255));
        org.openide.awt.Mnemonics.setLocalizedText(autoMinifyXML, org.openide.util.NbBundle.getMessage(XmlOptionsPanel.class, "XmlOptionsPanel.autoMinifyXML.text")); // NOI18N
        autoMinifyXML.setContentAreaFilled(false);

        extLabel.setForeground(extLabel.getForeground().darker());
        org.openide.awt.Mnemonics.setLocalizedText(extLabel, org.openide.util.NbBundle.getMessage(XmlOptionsPanel.class, "XmlOptionsPanel.extLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(xmlMinifierFlagsLabel, org.openide.util.NbBundle.getMessage(XmlOptionsPanel.class, "XmlOptionsPanel.xmlMinifierFlagsLabel.text")); // NOI18N

        xmlMinifierFlagsTextField.setText(org.openide.util.NbBundle.getMessage(XmlOptionsPanel.class, "XmlOptionsPanel.xmlMinifierFlagsTextField.text")); // NOI18N

        xmlMinifierArgumentsHintsLabel.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(xmlMinifierArgumentsHintsLabel, org.openide.util.NbBundle.getMessage(XmlOptionsPanel.class, "XmlOptionsPanel.xmlMinifierArgumentsHintsLabel.text")); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(newXMLFile)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(preExtensionXML, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(extLabel))
                    .addComponent(autoMinifyXML, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(xmlMinifierFlagsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(xmlMinifierArgumentsHintsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(xmlMinifierFlagsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 707, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(xmlMinifierFlagsLabel)
                    .addComponent(xmlMinifierFlagsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(xmlMinifierArgumentsHintsLabel)
                .addGap(18, 18, 18)
                .addComponent(autoMinifyXML)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newXMLFile)
                    .addComponent(extLabel)
                    .addComponent(preExtensionXML, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.openide.awt.Mnemonics.setLocalizedText(headerLabelXML, org.openide.util.NbBundle.getMessage(XmlOptionsPanel.class, "XmlOptionsPanel.headerLabelXML.text")); // NOI18N

        headerScrollPaneXML.setViewportView(headerEditorPaneXML);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 794, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 8, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(headerScrollPaneXML, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(headerLabelXML))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(headerLabelXML)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(headerScrollPaneXML, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel2.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(XmlOptionsPanel.class, "XmlOptionsPanel.jPanel2.AccessibleContext.accessibleName")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents

    void load() {
        minifyProperty.load();
    }

    void store() {
        minifyProperty.store();
    }

    boolean valid() {
        // TODO check whether form is consistent and complete
        return true;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JCheckBox autoMinifyXML;
    private javax.swing.JLabel extLabel;
    protected javax.swing.JEditorPane headerEditorPaneXML;
    private javax.swing.JLabel headerLabelXML;
    private javax.swing.JScrollPane headerScrollPaneXML;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    public javax.swing.JCheckBox newXMLFile;
    public javax.swing.JTextField preExtensionXML;
    private javax.swing.JLabel xmlMinifierArgumentsHintsLabel;
    private javax.swing.JLabel xmlMinifierFlagsLabel;
    private javax.swing.JTextField xmlMinifierFlagsTextField;
    private javax.swing.JLabel xmlMinifierLabel;
    // End of variables declaration//GEN-END:variables

    void fireChange() {
        changeSupport.fireChange();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        fireChange();
    }
}
