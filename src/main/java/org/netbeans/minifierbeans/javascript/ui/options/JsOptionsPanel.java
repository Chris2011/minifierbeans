package org.netbeans.minifierbeans.javascript.ui.options;

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

public final class JsOptionsPanel extends JPanel implements ChangeListener {
    private MinifyProperty minifyProperty;

    private static final long serialVersionUID = 1L;
    private final ChangeSupport changeSupport = new ChangeSupport(this);

    public JsOptionsPanel() {
        assert EventQueue.isDispatchThread();

        initComponents();

        init();
    }

    private void init() {
        final ProjectOptionsPanel projectOptionsPanel = new ProjectOptionsPanel();

        minifyProperty = MinifyProperty.getInstance();

        newJSFile.setSelected(minifyProperty.isNewJSFile());
        preExtensionJS.setEnabled(minifyProperty.isNewJSFile());
        preExtensionJS_Label.setEnabled(minifyProperty.isNewJSFile());

        projectOptionsPanel.skipPreExtensionJS.setEnabled(minifyProperty.isNewJSFile());
        minifyProperty.setSkipPreExtensionJS(minifyProperty.isNewJSFile());
        preExtensionJS.setText(minifyProperty.getPreExtensionJS());

        if (minifyProperty.isJsObfuscate()) {
            this.jsObfuscate.setSelected(Boolean.TRUE);
        }

        autoMinifyJS.setSelected(minifyProperty.isAutoMinifyJS());
        headerEditorPaneJS.setText(minifyProperty.getHeaderJS());

        headerEditorPaneJS.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                String text = headerEditorPaneJS.getText();
                if (text == null || text.trim().isEmpty()) {
                    text = "";
                    headerEditorPaneJS.setText("");
                } else {
                    text = text.trim();
                }
                minifyProperty.setHeaderJS(text);
            }
        });

        this.autoMinifyJS.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setAutoMinifyJS(Boolean.TRUE);
                } else {
                    minifyProperty.setAutoMinifyJS(Boolean.FALSE);
                }
            }
        });

        this.newJSFile.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setNewJSFile(Boolean.TRUE);
                    minifyProperty.setPreExtensionJS(".min");
                    preExtensionJS.setText(".min");

                    preExtensionJS.setEnabled(Boolean.TRUE);
                    preExtensionJS_Label.setEnabled(Boolean.TRUE);

                    if (minifyProperty.isBuildJSMinify() && minifyProperty.isNewJSFile()) {
                        projectOptionsPanel.skipPreExtensionJS.setEnabled(true);
                        minifyProperty.setSkipPreExtensionJS(true);
                        projectOptionsPanel.skipPreExtensionJS.setSelected(true);
                    }
                } else {
                    minifyProperty.setNewJSFile(Boolean.FALSE);
                    preExtensionJS.setEnabled(Boolean.FALSE);
                    preExtensionJS_Label.setEnabled(Boolean.FALSE);

                    projectOptionsPanel.skipPreExtensionJS.setEnabled(false);

                    minifyProperty.setSkipPreExtensionJS(Boolean.FALSE);
                    projectOptionsPanel.skipPreExtensionJS.setSelected(false);
                }
            }
        });

        this.preExtensionJS.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                String text = preExtensionJS.getText();
                if (text == null || text.trim().isEmpty()) {
                    text = ".min";
                    preExtensionJS.setText(text);
                } else {
                    text = text.trim();
                }
                minifyProperty.setPreExtensionJS(text);
            }

        });

//        this.separatorJS.addFocusListener(new FocusListener() {
//            @Override
//            public void focusGained(FocusEvent fe) {
//            }
//
//            @Override
//            public void focusLost(FocusEvent fe) {
//                String text = separatorJS.getText();
//                if (text == null || text.trim().isEmpty()) {
//                    text = ".";
//                } else {
//                    text = String.valueOf(text.trim().charAt(0));
//                }
//                if (text.equals("<") || text.equals(">")
//                        || text.equals(":") || text.equals("/")
//                        || text.equals("\\") || text.equals("|")
//                        || text.equals("?") || text.equals("*")) {
//                    text = ".";
//                }
//                separatorJS.setText(text);
//                minifyProperty.setSeparatorJS(text.charAt(0));
//            }
//        });
        this.jsObfuscate.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setJsObfuscate(Boolean.TRUE);
                } else {
                    minifyProperty.setJsObfuscate(Boolean.FALSE);
                }
            }
        });
    }

    public static JsOptionsPanel create() {
        JsOptionsPanel panel = new JsOptionsPanel();

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
        newJSFile = new javax.swing.JCheckBox();
        preExtensionJS_Label = new javax.swing.JLabel();
        preExtensionJS = new javax.swing.JTextField();
        autoMinifyJS = new javax.swing.JCheckBox();
        extLabel = new javax.swing.JLabel();
        jsObfuscate = new javax.swing.JCheckBox();
        headerLabelJS = new javax.swing.JLabel();
        headerScrollPaneJS = new javax.swing.JScrollPane();
        headerEditorPaneJS = new javax.swing.JEditorPane();

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

        newJSFile.setBackground(new java.awt.Color(255, 255, 255));
        org.openide.awt.Mnemonics.setLocalizedText(newJSFile, org.openide.util.NbBundle.getMessage(JsOptionsPanel.class, "JsOptionsPanel.newJSFile.text")); // NOI18N
        newJSFile.setToolTipText(org.openide.util.NbBundle.getMessage(JsOptionsPanel.class, "JsOptionsPanel.newJSFile.toolTipText")); // NOI18N
        newJSFile.setOpaque(false);

        org.openide.awt.Mnemonics.setLocalizedText(preExtensionJS_Label, org.openide.util.NbBundle.getMessage(JsOptionsPanel.class, "JsOptionsPanel.preExtensionJS_Label.text")); // NOI18N

        preExtensionJS.setText(org.openide.util.NbBundle.getMessage(JsOptionsPanel.class, "JsOptionsPanel.preExtensionJS.text")); // NOI18N

        autoMinifyJS.setBackground(new java.awt.Color(255, 255, 255));
        org.openide.awt.Mnemonics.setLocalizedText(autoMinifyJS, org.openide.util.NbBundle.getMessage(JsOptionsPanel.class, "JsOptionsPanel.autoMinifyJS.text")); // NOI18N
        autoMinifyJS.setOpaque(false);

        extLabel.setForeground(extLabel.getForeground().darker());
        org.openide.awt.Mnemonics.setLocalizedText(extLabel, org.openide.util.NbBundle.getMessage(JsOptionsPanel.class, "JsOptionsPanel.extLabel.text")); // NOI18N

        jsObfuscate.setBackground(new java.awt.Color(255, 255, 255));
        org.openide.awt.Mnemonics.setLocalizedText(jsObfuscate, org.openide.util.NbBundle.getMessage(JsOptionsPanel.class, "JsOptionsPanel.jsObfuscate.text")); // NOI18N
        jsObfuscate.setOpaque(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(newJSFile)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(autoMinifyJS, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(jsObfuscate, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(preExtensionJS_Label)
                        .addGap(6, 6, 6)
                        .addComponent(preExtensionJS, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(extLabel)))
                .addGap(6, 6, 6))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(autoMinifyJS)
                    .addComponent(jsObfuscate))
                .addGap(6, 6, 6)
                .addComponent(newJSFile)
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(preExtensionJS_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(extLabel)
                    .addComponent(preExtensionJS, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6))
        );

        org.openide.awt.Mnemonics.setLocalizedText(headerLabelJS, org.openide.util.NbBundle.getMessage(JsOptionsPanel.class, "JsOptionsPanel.headerLabelJS.text")); // NOI18N

        headerEditorPaneJS.setToolTipText(org.openide.util.NbBundle.getMessage(JsOptionsPanel.class, "JsOptionsPanel.headerEditorPaneJS.toolTipText")); // NOI18N
        headerScrollPaneJS.setViewportView(headerEditorPaneJS);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(headerLabelJS)
                    .addComponent(headerScrollPaneJS, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6))
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(headerLabelJS)
                .addGap(6, 6, 6)
                .addComponent(headerScrollPaneJS, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
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
    public javax.swing.JCheckBox autoMinifyJS;
    private javax.swing.JLabel extLabel;
    protected javax.swing.JEditorPane headerEditorPaneJS;
    private javax.swing.JLabel headerLabelJS;
    private javax.swing.JScrollPane headerScrollPaneJS;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    public javax.swing.JCheckBox jsObfuscate;
    public javax.swing.JCheckBox newJSFile;
    public javax.swing.JTextField preExtensionJS;
    public javax.swing.JLabel preExtensionJS_Label;
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
