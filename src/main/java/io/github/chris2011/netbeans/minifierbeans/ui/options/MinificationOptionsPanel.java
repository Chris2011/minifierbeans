package io.github.chris2011.netbeans.minifierbeans.ui.options;

import java.awt.BorderLayout;
import java.util.HashMap;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import org.netbeans.spi.options.OptionsPanelController;

@OptionsPanelController.Keywords(keywords = {"HTML5", "minification", "#Keywords_MinificationOptions"}, location = "Html5", tabTitle = "Minification")
public final class MinificationOptionsPanel extends JPanel {
    public HashMap<String, JComponent> optionsPanels = new HashMap<>();

    public MinificationOptionsPanel() {
        initComponents();

        optionsList.addListSelectionListener((javax.swing.event.ListSelectionEvent evt) -> {
            optionsListValueChanged(evt);
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contentPanel = new javax.swing.JPanel();
        optionsListScrollPane = new javax.swing.JScrollPane();
        optionsList = new javax.swing.JList();

        contentPanel.setLayout(new java.awt.BorderLayout());

        optionsList.setModel(new DefaultListModel());
        optionsListScrollPane.setViewportView(optionsList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(optionsListScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                    .addComponent(optionsListScrollPane))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    public void addPanel(String name, JComponent component) {
        if (optionsPanels.containsKey(name)) {
            selectCategory(name);
        } else {
            ((DefaultListModel) optionsList.getModel()).addElement(name);
            optionsPanels.put(name, component);
        }
    }

    public void selectCategory(String name) {
        optionsList.setSelectedValue(name, true);
    }

    private void optionsListValueChanged(ListSelectionEvent evt) {
        contentPanel.setVisible(false);
        contentPanel.removeAll();
        contentPanel.add(optionsPanels.get((String) optionsList.getSelectedValue()), BorderLayout.CENTER);
        contentPanel.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel contentPanel;
    private javax.swing.JList optionsList;
    private javax.swing.JScrollPane optionsListScrollPane;
    // End of variables declaration//GEN-END:variables
}
