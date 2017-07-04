/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import interpreter.Interpreter;

/**
 *
 * @author alangonzalez
 */
public class DialogCreateEditRules extends javax.swing.JDialog {

	public static final String BTN_NEW = "Nueva regla";
	public static final String BTN_SAVE = "Guardar";
	public static final String BTN_EDIT = "Modificar";
	public static final String BTN_CANCEL = "Cancelar";
	public static final String BTN_DELETE = "Borrar";

	UIApp uiApp;

	boolean editMode = false;

	public DialogCreateEditRules(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		uiApp = (UIApp) parent;

		setViewStatus(false, false, true, false, false, BTN_NEW, BTN_CANCEL);
		
		listRules.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					
					int index = listRules.locationToIndex(e.getPoint());

			        if (index != -1 ) {
			        	txtRules.setText(fileToString(listRules.getSelectedValue()));
			        	txtRulesName.setText(listRules.getSelectedValue());
						setViewStatus(false, false, true, true, true, BTN_NEW, BTN_DELETE);
						editMode = false;
			        }
					
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		});
	}

	private static String fileToString(String fileName) throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String result = "";
		try {
			String line = br.readLine();
			while (line != null) {
				result += line + "\n";
				line = br.readLine();
			}
		} finally {
			br.close();
		}
		return result;
	}

	public void setViewStatus(boolean txtNameStatus, boolean txtRulesStatus, boolean btnNewSaveRuleStatus,
			boolean btnEditRuleStatus, boolean btnDeleteCancelStatus, String txtBtnNewSaveStatus,
			String txtBtnDeleteCancelStatus) {

		this.txtRulesName.setEnabled(txtNameStatus);
		this.txtRules.setEnabled(txtRulesStatus);

		this.btnNewSaveRule.setEnabled(btnNewSaveRuleStatus);
		this.btnEditRule.setEnabled(btnEditRuleStatus);
		this.btnDeleteCancelRule.setEnabled(btnDeleteCancelStatus);

		this.btnDeleteCancelRule.setText(txtBtnDeleteCancelStatus);
		this.btnNewSaveRule.setText(txtBtnNewSaveStatus);

	}

	private void btnEditRuleActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnEditRuleActionPerformed
		editMode = true;
		setViewStatus(true, true, true, false, true, BTN_SAVE, BTN_CANCEL);
	}

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
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
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap()
						.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 166,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
								jPanel1Layout.createSequentialGroup().addGap(18, 18, 18).addGroup(jPanel1Layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addGroup(jPanel1Layout.createSequentialGroup().addComponent(lblName)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(txtRulesName))
										.addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 502,
												javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(12, Short.MAX_VALUE))
								.addGroup(jPanel1Layout.createSequentialGroup()
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btnNewSaveRule, javax.swing.GroupLayout.PREFERRED_SIZE, 135,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addComponent(btnEditRule, javax.swing.GroupLayout.PREFERRED_SIZE, 135,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addComponent(btnDeleteCancelRule, javax.swing.GroupLayout.PREFERRED_SIZE, 135,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(45, 45, 45)))));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jPanel1Layout.createSequentialGroup()
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(lblName).addComponent(txtRulesName,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 234,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(btnNewSaveRule).addComponent(btnDeleteCancelRule)
										.addComponent(btnEditRule))
								.addGap(0, 6, Short.MAX_VALUE))
						.addComponent(jScrollPane1)).addContainerGap()));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addGap(15, 15, 15).addComponent(jPanel1,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap()));

		pack();
	}// </editor-fold>//GEN-END:initComponents
	
	public void deleteFile(String fileName){
		
		File f = new File(fileName);
		f.delete();
		
	}

	private void btnNewSaveRuleActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnNewSaveEditRuleActionPerformed

		switch (btnNewSaveRule.getText()) {
		case BTN_NEW:

			setViewStatus(true, true, true, false, true, BTN_SAVE, BTN_CANCEL);
			txtRules.setText("");
			txtRulesName.setText("");
			listRules.clearSelection();
			break;
		case BTN_SAVE:

			if (!editMode) {

				stringToFile("temp", txtRules.getText());

				String compileResultTemp = Interpreter.compileRules("temp");

				if (!compileResultTemp.equals("")) {
					JOptionPane.showMessageDialog(uiApp, "Ocurrio un error al generar reglas.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					
					stringToFile(txtRulesName.getText(), txtRules.getText());
					
					//dejo la regla que estaba antes
					if(uiApp.getSelectedRule() != null){
						System.out.println("selected rule was: "+uiApp.getSelectedRule().getText());
						Interpreter.compileRules(uiApp.getSelectedRule().getText()); 
					}
					///
					
					JCheckBoxMenuItem item = new JCheckBoxMenuItem(txtRulesName.getText());

					item.addActionListener(new JCheckBoxMenuItemListener(item, uiApp));

					uiApp.getBtnUseRule().add(item);
					uiApp.getRulesToUse().add(item);

					setViewStatus(false, false, true, false, false, BTN_NEW, BTN_CANCEL);
					refreshListRules();
					txtRules.setText("");
					txtRulesName.setText("");
					listRules.clearSelection();

				}
			} else {

				editMode = false;

				stringToFile("temp", txtRules.getText());

				String compileResultTemp = Interpreter.compileRules("temp");

				if (!compileResultTemp.equals("")) {
					JOptionPane.showMessageDialog(uiApp, "Ocurrio un error al generar reglas.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {

					/*for (JCheckBoxMenuItem i : uiApp.getRulesToUse()) {
						if (i.getText().equals(txtRulesName.getText())) {
							i.setSelected(true);
							uiApp.setSelectedRule(i);
						} else {
							i.setSelected(false);
						}
					}*/
					
					stringToFile(txtRulesName.getText(), txtRules.getText());
					
					//dejo la regla que estaba antes
					if(uiApp.getSelectedRule() != null){
						Interpreter.compileRules(uiApp.getSelectedRule().getText()); 
					}
					///
					
					setViewStatus(false, false, true, true, true, BTN_NEW, BTN_DELETE);
				}

			}

			break;
		case BTN_EDIT:

			editMode = true;
			setViewStatus(false, true, true, false, true, BTN_SAVE, BTN_CANCEL);

			break;

		default:
			break;
		}

	}// GEN-LAST:event_btnNewSaveEditRuleActionPerformed

	private void btnDeleteCancelRuleActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDeleteCancelRuleActionPerformed

		if (btnDeleteCancelRule.getText().equals(BTN_CANCEL)) {
			if (editMode) {
				setViewStatus(false, false, true, true, true, BTN_NEW, BTN_DELETE);
				editMode = false;
			} else {
				txtRules.setText("");
				txtRulesName.setText("");
				setViewStatus(false, false, true, false, false, BTN_NEW, BTN_CANCEL);
			}

		} else {// remove
			
			if(uiApp.getRulesToUse().get(listRules.getSelectedIndex()).getText().equals(uiApp.getSelectedRule().getText())){
				
				uiApp.setSelectedRule(null);
				uiApp.getRulesToUse().get(listRules.getSelectedIndex()).setSelected(false);
				uiApp.getTxtOutput().setText(uiApp.getTxtInput().getText());
			}
			uiApp.getBtnUseRule().remove(listRules.getSelectedIndex());
			uiApp.getRulesToUse().remove(listRules.getSelectedIndex());
			
			//uiApp.setSelectedRule(null);
			
			
			
			/*
			 *QUEDE EN VER COMO HACER PARA CUANDO ELIMINO UNA REGLA QUE ERA 
			 *LA QUE SE ESTABA USANDO, QUE PASA SI ELIMINO UNA QUE NO SE ESTABA
			 *USANDO, QUE PASA SI ELIMINO TODAS, CUAL QUEDA COMPILADA? ETC. 
			 */
			
			
			refreshListRules();
			
			txtRules.setText("");
			txtRulesName.setText("");
			listRules.clearSelection();
			
			
			
			setViewStatus(false, false, true, false, false, BTN_NEW, BTN_CANCEL);
		}
	}// GEN-LAST:event_btnDeleteCancelRuleActionPerformed

	public void refreshListRules(){
		
		String[] rulesToList = new String[uiApp.getRulesToUse().size()];
		int i = 0;
		for (JCheckBoxMenuItem it : uiApp.getRulesToUse()) {

			rulesToList[i] = it.getText();
			i++;
		}

		listRules.setListData(rulesToList);
		
	}
	
	public static void stringToFile(String name, String content) {
		FileWriter resultFile = null;
		PrintWriter pw = null;
		try {
			resultFile = new FileWriter(name);
			pw = new PrintWriter(resultFile);
			pw.println(content);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != resultFile)
					resultFile.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	public static void stringToFile(File file, String content) {
		FileWriter resultFile = null;
		PrintWriter pw = null;
		try {
			resultFile = new FileWriter(file);
			pw = new PrintWriter(resultFile);
			pw.println(content);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != resultFile)
					resultFile.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed" desc=" Look and feel setting
		// code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.
		 * html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(DialogCreateEditRules.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(DialogCreateEditRules.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(DialogCreateEditRules.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(DialogCreateEditRules.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>
		// </editor-fold>

		/* Create and display the dialog */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				DialogCreateEditRules dialog = new DialogCreateEditRules(new javax.swing.JFrame(), true);
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
	private javax.swing.JButton btnNewSaveRule;
	private javax.swing.JButton btnEditRule;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JLabel lblName;
	private javax.swing.JList<String> listRules;
	private javax.swing.JTextArea txtRules;
	private javax.swing.JTextField txtRulesName;
	// End of variables declaration//GEN-END:variables
}

class JCheckBoxMenuItemListener implements ActionListener {

	public JCheckBoxMenuItem item;
	public UIApp uiApp;

	public JCheckBoxMenuItemListener(JCheckBoxMenuItem item, UIApp uiApp) {
		this.item = item;
		this.uiApp = uiApp;
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {

		for (JCheckBoxMenuItem i : uiApp.getRulesToUse()) {
			i.setSelected(false);
		}
		item.setSelected(true);
		uiApp.setSelectedRule(item);
		
		//compilo nueva regla seleccionada
		Interpreter.compileRules(item.getText());
		
		//interpreto con nueva regla seleccionada
		uiApp.getTxtOutput().setText("");

		DialogCreateEditRules.stringToFile("textito", uiApp.getTxtInput().getText());
		uiApp.getTxtOutput().setText(Interpreter.execute("textito"));
		//
		
		System.out.println("toque " + uiApp.getSelectedRule().getText());
	}

}
