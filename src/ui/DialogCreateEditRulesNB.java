/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author alangonzalez
 */
public class DialogCreateEditRulesNB extends javax.swing.JDialog {

    public static final String BTN_NEW = "Nueva regla";
    public static final String BTN_SAVE = "Guardar";
    public static final String BTN_EDIT = "Modificar";
    public static final String BTN_CANCEL = "Cancelar";
    public static final String BTN_DELETE = "Borrar";

    public DialogCreateEditRulesNB(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        listRules.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                //seleccionar archivo correcto y cargar data

                txtRulesName.setText(listRules.getSelectedValue());
                setViewState(true, false, false, true, true, BTN_EDIT, BTN_DELETE);

            }
        });
    }

    public void setViewState(boolean lblNameState, boolean txtNameState, boolean txtRulesState, boolean btnNewSaveRuleState, boolean btnEditCancelRuleState, String txtBtnNewSaveState, String txtBtnEditCancelState) {

        this.lblName.setEnabled(lblNameState);
        this.txtRulesName.setEnabled(txtNameState);
        this.txtRules.setEnabled(txtRulesState);
        this.btnNewSaveRule.setEnabled(btnNewSaveRuleState);
        this.btnDeleteCancelRule.setEnabled(btnEditCancelRuleState);
        this.btnDeleteCancelRule.setText(txtBtnEditCancelState);
        this.btnNewSaveRule.setText(txtBtnNewSaveState);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listRules = new javax.swing.JList<>();
        lblName = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtRules = new javax.swing.JTextArea();
        txtRulesName = new javax.swing.JTextField();
        btnNewSaveRule = new javax.swing.JButton();
        btnDeleteCancelRule = new javax.swing.JButton();
        btnEditRule = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Reglas"));

        jScrollPane1.setViewportView(listRules);

        lblName.setText("Nombre");
        lblName.setEnabled(false);

        txtRules.setColumns(20);
        txtRules.setRows(5);
        txtRules.setEnabled(false);
        jScrollPane2.setViewportView(txtRules);

        txtRulesName.setEnabled(false);

        btnNewSaveRule.setText("Nueva regla");
        btnNewSaveRule.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewSaveRuleActionPerformed(evt);
            }
        });

        btnDeleteCancelRule.setText("Cancelar");
        btnDeleteCancelRule.setEnabled(false);
        btnDeleteCancelRule.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteCancelRuleActionPerformed(evt);
            }
        });

        btnEditRule.setText("Modificar");
        btnEditRule.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditRuleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtRulesName))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(12, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNewSaveRule, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditRule, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDeleteCancelRule, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblName)
                            .addComponent(txtRulesName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNewSaveRule)
                            .addComponent(btnDeleteCancelRule)
                            .addComponent(btnEditRule))
                        .addGap(0, 6, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }

    private void btnNewSaveRuleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewSaveRuleActionPerformed

        switch (btnNewSaveRule.getText()) {
            case BTN_NEW:

                setViewState(true, true, true, true, true, BTN_SAVE, BTN_CANCEL);
                txtRules.setText("");
                txtRulesName.setText("");
                listRules.clearSelection();
                break;
            case BTN_SAVE:

                //guardar regla compilar etc.
                //agregar a la lista
                //si esta todo ok:
                setViewState(false, false, false, true, false, BTN_NEW, BTN_CANCEL);

               /* String d[] = new String[lista.size() + 2];
                int i = 0;
                while (iter.hasNext()) {
                    Arancel a = iter.next();
                    d[i] = a.getString("nombre");
                    i++;
                }

                listRules.setListData(listData);*/
                txtRules.setText("");
                txtRulesName.setText("");
                listRules.clearSelection();

                break;
            case BTN_EDIT:

                setViewState(true, true, true, true, true, BTN_SAVE, BTN_CANCEL);
                break;

            default:
                break;
        }

    }//GEN-LAST:event_btnNewSaveRuleActionPerformed

    private void btnDeleteCancelRuleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteCancelRuleActionPerformed

        if (btnDeleteCancelRule.getText().equals(BTN_CANCEL)) {
            setViewState(true, false, false, true, false, BTN_NEW, BTN_CANCEL);
        } else {// remove 
            listRules.remove(listRules.getSelectedIndex());
            setViewState(true, false, false, true, false, BTN_NEW, BTN_CANCEL);
        }
    }//GEN-LAST:event_btnDeleteCancelRuleActionPerformed

    private void btnEditRuleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditRuleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditRuleActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DialogCreateEditRulesNB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogCreateEditRulesNB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogCreateEditRulesNB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogCreateEditRulesNB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogCreateEditRulesNB dialog = new DialogCreateEditRulesNB(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDeleteCancelRule;
    private javax.swing.JButton btnEditRule;
    private javax.swing.JButton btnNewSaveRule;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblName;
    private javax.swing.JList<String> listRules;
    private javax.swing.JTextArea txtRules;
    private javax.swing.JTextField txtRulesName;
    // End of variables declaration//GEN-END:variables
}
