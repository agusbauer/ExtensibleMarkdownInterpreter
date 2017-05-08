
package ui;

import java.awt.Desktop;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;

import interpreter.Interpreter;

/**
 *
 * @author alangonzalez
 */
public class UIApp extends javax.swing.JFrame {

	DialogCreateEditRules dialogCreateRules;
	public static File txtFile = null;
	public static File txtRules = null;
	public LinkedList<JCheckBoxMenuItem> rulesToUse;
	public JCheckBoxMenuItem selectedRule = null;

	public UIApp() throws IOException {

		initComponents();
		this.setLocationRelativeTo(null);
		dialogCreateRules = new DialogCreateEditRules(this, true);

		rulesToUse = new LinkedList<JCheckBoxMenuItem>();
		//JCheckBoxMenuItem defaultRules = new JCheckBoxMenuItem("Reglas HTML");
		//rulesToUse.add(defaultRules);
		
		//btnUseRule.add(defaultRules);

		txtInput.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent evt) {

				txtOutput.setText("");

				DialogCreateEditRules.stringToFile("original-text", txtInput.getText());
				if(selectedRule != null){
					txtOutput.setText(Interpreter.execute("original-text"));
				}else{
					txtOutput.setText(txtInput.getText());
				}
				
			}

		});
		
		File dir = new File(".");
		File [] files = dir.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.startsWith("rule");
			}
		});

		for (File file : files) {
		    //System.out.println(xmlfile);
			String compileResult = Interpreter.compileRules(file.getName());

			if (compileResult.equals("")) {
				JCheckBoxMenuItem item = new JCheckBoxMenuItem(file.getName());

				item.addActionListener(new JCheckBoxMenuItemListener(item, this));
				getBtnUseRule().add(item);
				getRulesToUse().add(item);
				dialogCreateRules.refreshListRules();
				
			}
		}
		
		File htmlFile = new File("result.html");
		Desktop.getDesktop().browse(htmlFile.toURI());
	}

	private void btnCreateRulesActionPerformed(java.awt.event.ActionEvent evt) {

		dialogCreateRules.setLocationRelativeTo(null);
		dialogCreateRules.setVisible(true);
	}

	private void btnLoadTextActionPerformed(java.awt.event.ActionEvent evt) {

		JFileChooser chooser = new JFileChooser();
		chooser.setApproveButtonText("Abrir texto");
		chooser.showOpenDialog(null);

		if (chooser.getSelectedFile() != null) {
			txtFile = chooser.getSelectedFile();
			try {
				txtInput.setText(fileToString(txtFile.getAbsolutePath()));
				txtOutput.setText("");

				DialogCreateEditRules.stringToFile("textito", txtInput.getText());
				txtOutput.setText(Interpreter.execute("textito"));
				
			} catch (IOException ex) {
				Logger.getLogger(UIApp.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	private void btnSaveTextActionPerformed(java.awt.event.ActionEvent evt) {

		JFileChooser chooser = new JFileChooser(); 
	    chooser.setCurrentDirectory(new java.io.File("."));
	    chooser.setDialogTitle("Selecciona donde guardar texto");
	   // chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    ;
	    chooser.setAcceptAllFileFilterUsed(false);
	      
	    if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) { 
	      System.out.println(chooser.getSelectedFile());
	      File f = new File(chooser.getSelectedFile()+".txt");
	      DialogCreateEditRules.stringToFile(f, txtInput.getText());
	     }
	}
	
	private void btnSaveTranslatedTextActionPerformed(java.awt.event.ActionEvent evt) {

		JFileChooser chooser = new JFileChooser(); 
	    chooser.setCurrentDirectory(new java.io.File("."));
	    chooser.setDialogTitle("Selecciona donde guardar traduccion");
	   // chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    
	    chooser.setAcceptAllFileFilterUsed(false);
	      
	    if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) { 
	      System.out.println(chooser.getSelectedFile());
	      File f = new File(chooser.getSelectedFile()+".txt");
	      DialogCreateEditRules.stringToFile(f, txtOutput.getText());
	     }
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

	public static void main(String args[]) {

		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(UIApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(UIApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(UIApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(UIApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new UIApp().setVisible(true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	public JTextArea getTxtInput() {
		return (JTextArea) txtInput;
	}

	public JTextArea getTxtOutput() {
		return (JTextArea) txtOutput;
	}
	
	public JMenu getBtnUseRule() {
		return btnUseRule;
	}

	public LinkedList<JCheckBoxMenuItem> getRulesToUse() {
		return rulesToUse;
	}

	public JCheckBoxMenuItem getSelectedRule() {
		return selectedRule;
	}

	public void setSelectedRule(JCheckBoxMenuItem n) {
		this.selectedRule = n;
	}
	
	@SuppressWarnings("unchecked")
	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		jScrollPane2 = new javax.swing.JScrollPane();
		txtInput = new javax.swing.JTextArea();
		jPanel2 = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		txtOutput = new javax.swing.JTextArea();
		jPanel3 = new javax.swing.JPanel();
		btnSaveTranslation = new javax.swing.JButton();
		btnCleanText = new javax.swing.JButton();
		jMenuBar1 = new javax.swing.JMenuBar();
		jMenu1 = new javax.swing.JMenu();
		btnCreateRules = new javax.swing.JMenuItem();
		btnUseRule = new javax.swing.JMenu();
		jMenu2 = new javax.swing.JMenu();
		btnLoadText = new javax.swing.JMenuItem();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Texto"));
		jPanel1.setPreferredSize(new java.awt.Dimension(320, 288));

		((JTextArea) txtInput).setColumns(20);
		((JTextArea) txtInput).setRows(5);
		jScrollPane2.setViewportView(txtInput);

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(
				jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout
						.createSequentialGroup().addGap(15, 15, 15).addComponent(jScrollPane2).addGap(15, 15, 15)));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
								jPanel1Layout.createSequentialGroup()
										.addGap(16, 16, 16).addComponent(jScrollPane2,
												javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
								.addGap(14, 14, 14)));

		jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Traduccion"));

		((JTextArea) txtOutput).setColumns(20);
		((JTextArea) txtOutput).setRows(5);
		jScrollPane1.setViewportView(txtOutput);

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup().addGap(15, 15, 15)
						.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
						.addGap(15, 15, 15)));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
						.addGap(16, 16, 16).addComponent(jScrollPane1).addGap(14, 14, 14)));

		jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

		btnSaveTranslation.setText("Guardar traduccion");
		btnSaveTranslation.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnSaveTranslatedTextActionPerformed(evt);
			}
		});

		btnCleanText.setText("Guardar texto");
		btnCleanText.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnSaveTextActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout
				.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jPanel3Layout.createSequentialGroup().addGap(163, 163, 163)
								.addComponent(btnCleanText, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
								.addGap(18, 18, 18).addComponent(btnSaveTranslation,
										javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
						.addGap(169, 169, 169)));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
						jPanel3Layout.createSequentialGroup().addContainerGap()
								.addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(btnSaveTranslation, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnCleanText, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGap(17, 17, 17)));

		jMenu1.setText("Reglas");

		btnCreateRules.setText("Gestionar reglas");
		btnCreateRules.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnCreateRulesActionPerformed(evt);
			}
		});
		jMenu1.add(btnCreateRules);

		btnUseRule.setText("Usar regla...");
		jMenu1.add(btnUseRule);

		jMenuBar1.add(jMenu1);

		jMenu2.setText("Texto");

		btnLoadText.setText("Cargar texto");
		btnLoadText.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnLoadTextActionPerformed(evt);
			}
		});
		jMenu2.add(btnLoadText);

		jMenuBar1.add(jMenu2);

		setJMenuBar(jMenuBar1);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addGap(14, 14, 14)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGap(15, 15, 15))
						.addGroup(layout.createSequentialGroup()
								.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
								.addGap(26, 26, 26)
								.addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(19, 19, 19)))));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup().addGap(15, 15, 15)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 289,
												Short.MAX_VALUE)
										.addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(14, 14, 14)));

		pack();
	}
	
	private javax.swing.JButton btnCleanText;
	private javax.swing.JMenuItem btnCreateRules;
	private javax.swing.JMenuItem btnLoadText;
	private javax.swing.JButton btnSaveTranslation;
	private javax.swing.JMenu btnUseRule;
	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenu jMenu2;
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private JTextComponent txtInput;
	private JTextComponent txtOutput;

}
