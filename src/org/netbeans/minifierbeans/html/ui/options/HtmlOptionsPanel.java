package org.netbeans.minifierbeans.html.ui.options;

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
import org.netbeans.minifierbeans.project.ui.options.ProjectOptionsPanel;
import org.netbeans.minifierbeans.ui.MinifyProperty;
import org.openide.util.ChangeSupport;

public final class HtmlOptionsPanel extends JPanel implements ChangeListener {
    private MinifyProperty minifyProperty;

    private static final long serialVersionUID = 1L;
    private final ChangeSupport changeSupport = new ChangeSupport(this);

    public HtmlOptionsPanel() {
        assert EventQueue.isDispatchThread();

        initComponents();

        init();
    }

    private void init() {
        final ProjectOptionsPanel projectOptionsPanel = new ProjectOptionsPanel();

        minifyProperty = MinifyProperty.getInstance();

        newHTMLFile.setSelected(minifyProperty.isNewHTMLFile());
        preExtensionHTML.setEnabled(minifyProperty.isNewHTMLFile());
        preExtensionHTML_Label.setEnabled(minifyProperty.isNewHTMLFile());
        projectOptionsPanel.skipPreExtensionHTML.setEnabled(minifyProperty.isNewHTMLFile());
        this.preExtensionHTML.setText(minifyProperty.getPreExtensionHTML());
        autoMinifyHTML.setSelected(minifyProperty.isAutoMinifyHTML());
        headerEditorPaneHTML.setText(minifyProperty.getHeaderHTML());
        headerEditorPaneHTML.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                String text = headerEditorPaneHTML.getText();
                if (text == null || text.trim().isEmpty()) {
                    text = "";
                    headerEditorPaneHTML.setText("");
                } else {
                    text = text.trim();
                }
                minifyProperty.setHeaderHTML(text);
            }
        });
        this.autoMinifyHTML.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setAutoMinifyHTML(Boolean.TRUE);
                } else {
                    minifyProperty.setAutoMinifyHTML(Boolean.FALSE);
                }
            }
        });
        this.newHTMLFile.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setNewHTMLFile(Boolean.TRUE);
                    minifyProperty.setPreExtensionHTML(".min");
                    preExtensionHTML.setText(".min");
                    minifyProperty.setSeparatorHTML('.');
                    preExtensionHTML.setEnabled(Boolean.TRUE);
                    preExtensionHTML_Label.setEnabled(Boolean.TRUE);
                    if (minifyProperty.isBuildHTMLMinify() && minifyProperty.isNewHTMLFile()) {
                        projectOptionsPanel.skipPreExtensionHTML.setEnabled(Boolean.TRUE);
                        minifyProperty.setSkipPreExtensionHTML(Boolean.TRUE);
                        projectOptionsPanel.skipPreExtensionHTML.setSelected(Boolean.TRUE);
                    }

                } else {
                    minifyProperty.setNewHTMLFile(Boolean.FALSE);
                    preExtensionHTML.setEnabled(Boolean.FALSE);
                    preExtensionHTML_Label.setEnabled(Boolean.FALSE);
                    projectOptionsPanel.skipPreExtensionHTML.setEnabled(Boolean.FALSE);
                    minifyProperty.setSkipPreExtensionHTML(Boolean.FALSE);
                    projectOptionsPanel.skipPreExtensionHTML.setSelected(Boolean.FALSE);

                }
            }
        });

        this.preExtensionHTML.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                String text = preExtensionHTML.getText();
                if (text == null || text.trim().isEmpty()) {
                    text = ".min";
                    preExtensionHTML.setText(text);
                } else {
                    text = text.trim();
                }
                minifyProperty.setPreExtensionHTML(text);
            }
        });

        if (minifyProperty.isBuildInternalCSSMinify()) {
            this.buildInternalCSSMinify.setSelected(Boolean.TRUE);
        }

        if (minifyProperty.isBuildInternalJSMinify()) {
            this.buildInternalJSMinify.setSelected(Boolean.TRUE);
        }
        this.buildInternalCSSMinify.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setBuildInternalCSSMinify(Boolean.TRUE);
                } else {
                    minifyProperty.setBuildInternalCSSMinify(Boolean.FALSE);
                }
            }
        });
        this.buildInternalJSMinify.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setBuildInternalJSMinify(Boolean.TRUE);
                } else {
                    minifyProperty.setBuildInternalJSMinify(Boolean.FALSE);
                }
                //minifyPropertyController.writeMinifyProperty(minifyProperty);
            }
        });
    }

    public static HtmlOptionsPanel create() {
        HtmlOptionsPanel panel = new HtmlOptionsPanel();

        return panel;
    }

    public void addChangeListener(ChangeListener listener) {
        changeSupport.addChangeListener(listener);
    }

    public void removeChangeListener(ChangeListener listener) {
        changeSupport.removeChangeListener(listener);
    }

//    public String getCssNanoCli() {
//        return cssNanoCliPathTextField.getText();
//    }
//
//    public void setCssNanoCli(String cssNanoCli) {
//        cssNanoCliPathTextField.setText(cssNanoCli);
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        newHTMLFile = new javax.swing.JCheckBox();
        preExtensionHTML_Label = new javax.swing.JLabel();
        preExtensionHTML = new javax.swing.JTextField();
        autoMinifyHTML = new javax.swing.JCheckBox();
        extLabel = new javax.swing.JLabel();
        buildInternalJSMinify = new javax.swing.JCheckBox();
        buildInternalCSSMinify = new javax.swing.JCheckBox();
        headerLabelHTML = new javax.swing.JLabel();
        headerScrollPaneHTML = new javax.swing.JScrollPane();
        headerEditorPaneHTML = new javax.swing.JEditorPane();

        setPreferredSize(new java.awt.Dimension(768, 314));

        jPanel1.setPreferredSize(new java.awt.Dimension(0, 32));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );

        jPanel2.setPreferredSize(new java.awt.Dimension(762, 86));

        newHTMLFile.setBackground(new java.awt.Color(255, 255, 255));
        org.openide.awt.Mnemonics.setLocalizedText(newHTMLFile, org.openide.util.NbBundle.getMessage(HtmlOptionsPanel.class, "HtmlOptionsPanel.newHTMLFile.text")); // NOI18N
        newHTMLFile.setToolTipText(org.openide.util.NbBundle.getMessage(HtmlOptionsPanel.class, "HtmlOptionsPanel.newHTMLFile.toolTipText")); // NOI18N
        newHTMLFile.setOpaque(false);

        org.openide.awt.Mnemonics.setLocalizedText(preExtensionHTML_Label, org.openide.util.NbBundle.getMessage(HtmlOptionsPanel.class, "HtmlOptionsPanel.preExtensionHTML_Label.text")); // NOI18N

        preExtensionHTML.setText(org.openide.util.NbBundle.getMessage(HtmlOptionsPanel.class, "HtmlOptionsPanel.preExtensionHTML.text")); // NOI18N

        autoMinifyHTML.setBackground(new java.awt.Color(255, 255, 255));
        org.openide.awt.Mnemonics.setLocalizedText(autoMinifyHTML, org.openide.util.NbBundle.getMessage(HtmlOptionsPanel.class, "HtmlOptionsPanel.autoMinifyHTML.text")); // NOI18N
        autoMinifyHTML.setOpaque(false);

        extLabel.setForeground(extLabel.getForeground().darker());
        org.openide.awt.Mnemonics.setLocalizedText(extLabel, org.openide.util.NbBundle.getMessage(HtmlOptionsPanel.class, "HtmlOptionsPanel.extLabel.text")); // NOI18N

        buildInternalJSMinify.setBackground(new java.awt.Color(255, 255, 255));
        org.openide.awt.Mnemonics.setLocalizedText(buildInternalJSMinify, org.openide.util.NbBundle.getMessage(HtmlOptionsPanel.class, "HtmlOptionsPanel.buildInternalJSMinify.text")); // NOI18N
        buildInternalJSMinify.setOpaque(false);

        buildInternalCSSMinify.setBackground(new java.awt.Color(255, 255, 255));
        org.openide.awt.Mnemonics.setLocalizedText(buildInternalCSSMinify, org.openide.util.NbBundle.getMessage(HtmlOptionsPanel.class, "HtmlOptionsPanel.buildInternalCSSMinify.text")); // NOI18N
        buildInternalCSSMinify.setOpaque(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(preExtensionHTML_Label)
                        .addGap(6, 6, 6)
                        .addComponent(preExtensionHTML, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(extLabel))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(autoMinifyHTML, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(newHTMLFile))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buildInternalJSMinify)
                            .addComponent(buildInternalCSSMinify, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(autoMinifyHTML)
                    .addComponent(buildInternalJSMinify))
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newHTMLFile)
                    .addComponent(buildInternalCSSMinify))
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(preExtensionHTML_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(preExtensionHTML, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(extLabel))
                .addGap(6, 6, 6))
        );

        org.openide.awt.Mnemonics.setLocalizedText(headerLabelHTML, org.openide.util.NbBundle.getMessage(HtmlOptionsPanel.class, "HtmlOptionsPanel.headerLabelHTML.text")); // NOI18N

        headerEditorPaneHTML.setToolTipText(org.openide.util.NbBundle.getMessage(HtmlOptionsPanel.class, "HtmlOptionsPanel.headerEditorPaneHTML.toolTipText")); // NOI18N
        headerScrollPaneHTML.setViewportView(headerEditorPaneHTML);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(headerScrollPaneHTML, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 756, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 756, Short.MAX_VALUE))
                .addGap(6, 6, 6))
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(headerLabelHTML)
                .addGap(6, 6, 6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(headerLabelHTML)
                .addGap(6, 6, 6)
                .addComponent(headerScrollPaneHTML, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );
    }// </editor-fold>//GEN-END:initComponents

//    void load() {
//        String ngCli = NbPreferences.forModule(CssOptionsPanel.class).get("ngCliExecutableLocation", "");
//        cssNanoCliPathTextField.setText(ngCli);
//    }
//    void store() {
//        NbPreferences.forModule(CssOptionsPanel.class).put("ngCliExecutableLocation", cssNanoCliPathTextField.getText());
//    }
    boolean valid() {
        // TODO check whether form is consistent and complete
        return true;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JCheckBox autoMinifyHTML;
    public javax.swing.JCheckBox buildInternalCSSMinify;
    public javax.swing.JCheckBox buildInternalJSMinify;
    private javax.swing.JLabel extLabel;
    protected javax.swing.JEditorPane headerEditorPaneHTML;
    private javax.swing.JLabel headerLabelHTML;
    private javax.swing.JScrollPane headerScrollPaneHTML;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    public javax.swing.JCheckBox newHTMLFile;
    public javax.swing.JTextField preExtensionHTML;
    public javax.swing.JLabel preExtensionHTML_Label;
    // End of variables declaration//GEN-END:variables

    void fireChange() {
        changeSupport.fireChange();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        fireChange();
    }

    private final class DefaultDocumentListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            processUpdate();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            processUpdate();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            processUpdate();
        }

        private void processUpdate() {
            fireChange();
        }

    }
}
