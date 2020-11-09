package io.github.chris2011.netbeans.minifierbeans.html.ui.options;

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
                    preExtensionHTML.setEnabled(Boolean.TRUE);
                    if (minifyProperty.isBuildHTMLMinify() && minifyProperty.isNewHTMLFile()) {
                        projectOptionsPanel.skipPreExtensionHTML.setEnabled(Boolean.TRUE);
                        minifyProperty.setSkipPreExtensionHTML(Boolean.TRUE);
                        projectOptionsPanel.skipPreExtensionHTML.setSelected(Boolean.TRUE);
                    }

                } else {
                    minifyProperty.setNewHTMLFile(Boolean.FALSE);
                    preExtensionHTML.setEnabled(Boolean.FALSE);
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
        closureCompilerLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        newHTMLFile = new javax.swing.JCheckBox();
        preExtensionHTML = new javax.swing.JTextField();
        autoMinifyHTML = new javax.swing.JCheckBox();
        extLabel = new javax.swing.JLabel();
        htmlMinifierFlagsLabel = new javax.swing.JLabel();
        htmlMinifierFlagsTextField = new javax.swing.JTextField();
        htmlMinifierFlagsHintsLabel = new javax.swing.JLabel();
        headerLabelHTML = new javax.swing.JLabel();
        headerScrollPaneHTML = new javax.swing.JScrollPane();
        headerEditorPaneHTML = new javax.swing.JEditorPane();

        setPreferredSize(new java.awt.Dimension(768, 314));

        jPanel1.setPreferredSize(new java.awt.Dimension(0, 32));

        org.openide.awt.Mnemonics.setLocalizedText(closureCompilerLabel, org.openide.util.NbBundle.getMessage(HtmlOptionsPanel.class, "HtmlOptionsPanel.closureCompilerLabel.text")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(closureCompilerLabel)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(closureCompilerLabel)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jPanel2.setPreferredSize(new java.awt.Dimension(762, 86));

        newHTMLFile.setBackground(new java.awt.Color(255, 255, 255));
        org.openide.awt.Mnemonics.setLocalizedText(newHTMLFile, org.openide.util.NbBundle.getMessage(HtmlOptionsPanel.class, "HtmlOptionsPanel.newHTMLFile.text")); // NOI18N
        newHTMLFile.setContentAreaFilled(false);

        preExtensionHTML.setText(org.openide.util.NbBundle.getMessage(HtmlOptionsPanel.class, "HtmlOptionsPanel.preExtensionHTML.text")); // NOI18N

        autoMinifyHTML.setBackground(new java.awt.Color(255, 255, 255));
        org.openide.awt.Mnemonics.setLocalizedText(autoMinifyHTML, org.openide.util.NbBundle.getMessage(HtmlOptionsPanel.class, "HtmlOptionsPanel.autoMinifyHTML.text")); // NOI18N
        autoMinifyHTML.setContentAreaFilled(false);

        extLabel.setForeground(extLabel.getForeground().darker());
        org.openide.awt.Mnemonics.setLocalizedText(extLabel, org.openide.util.NbBundle.getMessage(HtmlOptionsPanel.class, "HtmlOptionsPanel.extLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(htmlMinifierFlagsLabel, org.openide.util.NbBundle.getMessage(HtmlOptionsPanel.class, "HtmlOptionsPanel.htmlMinifierFlagsLabel.text")); // NOI18N

        htmlMinifierFlagsTextField.setText(org.openide.util.NbBundle.getMessage(HtmlOptionsPanel.class, "HtmlOptionsPanel.htmlMinifierFlagsTextField.text")); // NOI18N

        htmlMinifierFlagsHintsLabel.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(htmlMinifierFlagsHintsLabel, org.openide.util.NbBundle.getMessage(HtmlOptionsPanel.class, "HtmlOptionsPanel.htmlMinifierFlagsHintsLabel.text")); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(autoMinifyHTML, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(newHTMLFile)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(preExtensionHTML, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(extLabel)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(htmlMinifierFlagsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(htmlMinifierFlagsHintsLabel)
                        .addGap(0, 165, Short.MAX_VALUE))
                    .addComponent(htmlMinifierFlagsTextField))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(htmlMinifierFlagsLabel)
                    .addComponent(htmlMinifierFlagsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(htmlMinifierFlagsHintsLabel)
                .addGap(18, 18, 18)
                .addComponent(autoMinifyHTML)
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newHTMLFile)
                    .addComponent(preExtensionHTML, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(extLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.openide.awt.Mnemonics.setLocalizedText(headerLabelHTML, org.openide.util.NbBundle.getMessage(HtmlOptionsPanel.class, "HtmlOptionsPanel.headerLabelHTML.text")); // NOI18N

        headerScrollPaneHTML.setViewportView(headerEditorPaneHTML);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 762, Short.MAX_VALUE)))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(headerScrollPaneHTML, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(headerLabelHTML))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(headerLabelHTML)
                .addGap(6, 6, 6)
                .addComponent(headerScrollPaneHTML, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
    private javax.swing.JLabel closureCompilerLabel;
    private javax.swing.JLabel extLabel;
    protected javax.swing.JEditorPane headerEditorPaneHTML;
    private javax.swing.JLabel headerLabelHTML;
    private javax.swing.JScrollPane headerScrollPaneHTML;
    private javax.swing.JLabel htmlMinifierFlagsHintsLabel;
    private javax.swing.JLabel htmlMinifierFlagsLabel;
    private javax.swing.JTextField htmlMinifierFlagsTextField;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    public javax.swing.JCheckBox newHTMLFile;
    public javax.swing.JTextField preExtensionHTML;
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
