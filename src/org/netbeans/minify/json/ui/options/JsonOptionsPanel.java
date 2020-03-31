package org.netbeans.minify.json.ui.options;

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
import org.netbeans.minify.project.ui.options.ProjectOptionsPanel;
import org.netbeans.minify.ui.MinifyProperty;
import org.openide.util.ChangeSupport;

public final class JsonOptionsPanel extends JPanel implements ChangeListener {
    private MinifyProperty minifyProperty;

    private static final long serialVersionUID = 1L;
    private final ChangeSupport changeSupport = new ChangeSupport(this);

    public JsonOptionsPanel() {
        assert EventQueue.isDispatchThread();

        initComponents();

        init();
    }

    private void init() {
        final ProjectOptionsPanel projectOptionsPanel = new ProjectOptionsPanel();

        minifyProperty = MinifyProperty.getInstance();

        newJSONFile.setSelected(minifyProperty.isNewJSONFile());
        preExtensionJSON.setEnabled(minifyProperty.isNewJSONFile());
        preExtensionJSON_Label.setEnabled(minifyProperty.isNewJSONFile());
        projectOptionsPanel.skipPreExtensionJSON.setEnabled(minifyProperty.isNewJSONFile());
        this.preExtensionJSON.setText(minifyProperty.getPreExtensionJSON());
        autoMinifyJSON.setSelected(minifyProperty.isAutoMinifyJSON());
        headerEditorPaneJSON.setText(minifyProperty.getHeaderJSON());

        headerEditorPaneJSON.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                String text = headerEditorPaneJSON.getText();
                if (text == null || text.trim().isEmpty()) {
                    text = "";
                    headerEditorPaneJSON.setText("");
                } else {
                    text = text.trim();
                }
                minifyProperty.setHeaderJSON(text);
            }
        });
        this.autoMinifyJSON.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setAutoMinifyJSON(Boolean.TRUE);
                } else {
                    minifyProperty.setAutoMinifyJSON(Boolean.FALSE);
                }
            }
        });

        this.newJSONFile.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setNewJSONFile(Boolean.TRUE);
                    minifyProperty.setPreExtensionJSON(".min");
                    preExtensionJSON.setText(".min");
                    minifyProperty.setSeparatorJSON('.');
                    preExtensionJSON.setEnabled(Boolean.TRUE);
                    preExtensionJSON_Label.setEnabled(Boolean.TRUE);
                    if (minifyProperty.isBuildJSONMinify() && minifyProperty.isNewJSONFile()) {
                        projectOptionsPanel.skipPreExtensionJSON.setEnabled(Boolean.TRUE);
                        minifyProperty.setSkipPreExtensionJSON(Boolean.TRUE);
                        projectOptionsPanel.skipPreExtensionJSON.setSelected(Boolean.TRUE);
                    }

                } else {
                    minifyProperty.setNewJSONFile(Boolean.FALSE);
                    preExtensionJSON.setEnabled(Boolean.FALSE);
                    preExtensionJSON_Label.setEnabled(Boolean.FALSE);
                    projectOptionsPanel.skipPreExtensionJSON.setEnabled(Boolean.FALSE);
                    minifyProperty.setSkipPreExtensionJSON(Boolean.FALSE);
                    projectOptionsPanel.skipPreExtensionJSON.setSelected(Boolean.FALSE);
                }
            }
        });

        this.preExtensionJSON.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                String text = preExtensionJSON.getText();
                if (text == null || text.trim().isEmpty()) {
                    text = ".min";
                    preExtensionJSON.setText(text);
                } else {
                    text = text.trim();
                }
                minifyProperty.setPreExtensionJSON(text);
            }
        });
    }

    public static JsonOptionsPanel create() {
        JsonOptionsPanel panel = new JsonOptionsPanel();

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
        jPanel2 = new javax.swing.JPanel();
        newJSONFile = new javax.swing.JCheckBox();
        preExtensionJSON_Label = new javax.swing.JLabel();
        preExtensionJSON = new javax.swing.JTextField();
        autoMinifyJSON = new javax.swing.JCheckBox();
        extLabel = new javax.swing.JLabel();
        headerScrollPaneJSON = new javax.swing.JScrollPane();
        headerEditorPaneJSON = new javax.swing.JEditorPane();
        headerLabelJSON = new javax.swing.JLabel();

        setName(""); // NOI18N

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

        newJSONFile.setBackground(new java.awt.Color(255, 255, 255));
        org.openide.awt.Mnemonics.setLocalizedText(newJSONFile, org.openide.util.NbBundle.getMessage(JsonOptionsPanel.class, "JsonOptionsPanel.newJSONFile.text")); // NOI18N
        newJSONFile.setToolTipText(org.openide.util.NbBundle.getMessage(JsonOptionsPanel.class, "JsonOptionsPanel.newJSONFile.toolTipText")); // NOI18N
        newJSONFile.setOpaque(false);

        org.openide.awt.Mnemonics.setLocalizedText(preExtensionJSON_Label, org.openide.util.NbBundle.getMessage(JsonOptionsPanel.class, "JsonOptionsPanel.preExtensionJSON_Label.text")); // NOI18N

        preExtensionJSON.setText(org.openide.util.NbBundle.getMessage(JsonOptionsPanel.class, "JsonOptionsPanel.preExtensionJSON.text")); // NOI18N

        autoMinifyJSON.setBackground(new java.awt.Color(255, 255, 255));
        org.openide.awt.Mnemonics.setLocalizedText(autoMinifyJSON, org.openide.util.NbBundle.getMessage(JsonOptionsPanel.class, "JsonOptionsPanel.autoMinifyJSON.text")); // NOI18N
        autoMinifyJSON.setOpaque(false);

        extLabel.setForeground(extLabel.getForeground().darker());
        org.openide.awt.Mnemonics.setLocalizedText(extLabel, org.openide.util.NbBundle.getMessage(JsonOptionsPanel.class, "JsonOptionsPanel.extLabel.text")); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(newJSONFile)
                    .addComponent(autoMinifyJSON, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(preExtensionJSON_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(preExtensionJSON, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(extLabel)))
                .addGap(0, 0, 0))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(autoMinifyJSON)
                .addGap(6, 6, 6)
                .addComponent(newJSONFile)
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(extLabel)
                        .addComponent(preExtensionJSON, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(preExtensionJSON_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6))
        );

        headerEditorPaneJSON.setToolTipText(org.openide.util.NbBundle.getMessage(JsonOptionsPanel.class, "JsonOptionsPanel.headerEditorPaneJSON.toolTipText")); // NOI18N
        headerScrollPaneJSON.setViewportView(headerEditorPaneJSON);

        org.openide.awt.Mnemonics.setLocalizedText(headerLabelJSON, org.openide.util.NbBundle.getMessage(JsonOptionsPanel.class, "JsonOptionsPanel.headerLabelJSON.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 756, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(headerLabelJSON)
                            .addComponent(headerScrollPaneJSON, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)))
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
                .addComponent(headerLabelJSON)
                .addGap(6, 6, 6)
                .addComponent(headerScrollPaneJSON, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );
    }// </editor-fold>//GEN-END:initComponents

    boolean valid() {
        // TODO check whether form is consistent and complete
        return true;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JCheckBox autoMinifyJSON;
    private javax.swing.JLabel extLabel;
    protected javax.swing.JEditorPane headerEditorPaneJSON;
    private javax.swing.JLabel headerLabelJSON;
    private javax.swing.JScrollPane headerScrollPaneJSON;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    public javax.swing.JCheckBox newJSONFile;
    public javax.swing.JTextField preExtensionJSON;
    public javax.swing.JLabel preExtensionJSON_Label;
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
