package net.sf.repairslab.ui;

import java.awt.Rectangle;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import net.sf.repairslab.control.CommonMetodBin;
import net.sf.repairslab.ui.messages.Messages;
import net.sf.repairslab.util.JDBCComboBoxModel;
import net.sf.repairslab.util.ui.cmb.TypeCmb;

import org.apache.log4j.Logger;

public class VcDlgInsertModello extends JDialog {
	
	static private Logger  logger = Logger.getLogger(CommonMetodBin.class.getName());

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JComboBox cmbTipoAppa = null;
	private String marca = null;
	private String tipoAppa = null;
	private JLabel lblTipoAppa = null;
	private JLabel lblMarca = null;
	private JComboBox cmbMarca = null;
	private JLabel lblModello = null;
	private JTextField txfModello = null;
	private JButton btnOk = null;
	private JButton btnCanc = null;
	private JLabel lbDescMod = null;
	private JTextField txfDescMod = null;
	private VcPnlApparecchio parent = null;
	private Connection con = null;

	/**
	 * @param owner
	 */
	public VcDlgInsertModello(JDialog owner,VcPnlApparecchio parent,String marca,String tipoAppa,Connection con) {
		super(owner,true);
		logger.debug("VcDlgInsertModello constructor..."); //$NON-NLS-1$
		this.marca = marca;
		this.tipoAppa = tipoAppa;
		this.parent = parent;
		this.con = con;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(370, 233);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setTitle(Messages.getString("VcDlgInsertModello.titleInsModel")); //$NON-NLS-1$
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			lbDescMod = new JLabel();
			lbDescMod.setBounds(new Rectangle(2, 121, 135, 16));
			lbDescMod.setHorizontalAlignment(SwingConstants.RIGHT);
			lbDescMod.setText(Messages.getString("VcDlgInsertModello.lblDesc")); //$NON-NLS-1$
			lblModello = new JLabel();
			lblModello.setBounds(new Rectangle(3, 92, 135, 16));
			lblModello.setHorizontalAlignment(SwingConstants.RIGHT);
			lblModello.setText(Messages.getString("VcDlgInsertModello.lblModel")); //$NON-NLS-1$
			lblMarca = new JLabel();
			lblMarca.setBounds(new Rectangle(3, 62, 135, 16));
			lblMarca.setHorizontalAlignment(SwingConstants.RIGHT);
			lblMarca.setText(Messages.getString("VcDlgInsertModello.lblBrand")); //$NON-NLS-1$
			lblTipoAppa = new JLabel();
			lblTipoAppa.setBounds(new Rectangle(4, 31, 135, 16));
			lblTipoAppa.setHorizontalAlignment(SwingConstants.RIGHT);
			lblTipoAppa.setText(Messages.getString("VcDlgInsertModello.lblEqpType")); //$NON-NLS-1$
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getCmbTipoAppa(), null);
			jContentPane.add(lblTipoAppa, null);
			jContentPane.add(lblMarca, null);
			jContentPane.add(getCmbMarca(), null);
			jContentPane.add(lblModello, null);
			jContentPane.add(getTxfModello(), null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnCanc(), null);
			jContentPane.add(lbDescMod, null);
			jContentPane.add(getTxfDescMod(), null);
			jContentPane.add(cmbTipoAppa, null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes cmbTipoAppa	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCmbTipoAppa() {
		if (cmbTipoAppa == null) {
			cmbTipoAppa = new JComboBox();
			cmbTipoAppa.setBounds(new Rectangle(145, 27, 190, 25));
			String qry = "select id,nome,flagAttivo from tipoapparecchiature"; //$NON-NLS-1$
			cmbTipoAppa.setModel(new JDBCComboBoxModel(con,qry,tipoAppa,"S")); //$NON-NLS-1$
		}
		return cmbTipoAppa;
	}

	/**
	 * This method initializes cmbMarca	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCmbMarca() {
		if (cmbMarca == null) {
			cmbMarca = new JComboBox();
			cmbMarca.setBounds(new Rectangle(145, 58, 190, 25));
			String qry = "select id,nome,flagAttivo from marchi"; //$NON-NLS-1$
			cmbMarca.setModel(new JDBCComboBoxModel(con,qry,marca,"S")); //$NON-NLS-1$
		}
		return cmbMarca;
	}

	/**
	 * This method initializes txfModello	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxfModello() {
		if (txfModello == null) {
			txfModello = new JTextField();
			txfModello.setBounds(new Rectangle(145, 89, 190, 25));
		}
		return txfModello;
	}

	/**
	 * This method initializes btnOk	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setIcon(new ImageIcon(getClass().getResource("/net/sf/repairslab/ui/img/button_ok.png"))); //$NON-NLS-1$
			btnOk.setBounds(new Rectangle(95, 163, 85, 25));
			btnOk.setText(Messages.getString("VcDlgInsertModello.btnOk")); //$NON-NLS-1$
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					inserisci();
				}
			});
		}
		return btnOk;
	}
	
	private void inserisci(){
		try {
			logger.debug("Inserisci..."); //$NON-NLS-1$
			String modello = getTxfModello().getText();
			String descModello = getTxfDescMod().getText();
			int idMarca = 0;
			int idAppa = 0;
			try{
				idMarca = Integer.parseInt(((TypeCmb)getCmbMarca().getSelectedItem()).getValue());
			}catch(NullPointerException e){}
			try{
				idAppa = Integer.parseInt(((TypeCmb)getCmbTipoAppa().getSelectedItem()).getValue());
			}catch(NullPointerException e){}
			
			if (modello == null || modello.equalsIgnoreCase("")){ //$NON-NLS-1$
				JOptionPane.showMessageDialog(getParent(),
						Messages.getString("VcDlgInsertModello.msgErrModel"), //$NON-NLS-1$
						Messages.getString("VcDlgInsertModello.msgTitleInfo"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
			}else if(idMarca == 0 || idAppa == 0){
				JOptionPane.showMessageDialog(getParent(),
						Messages.getString("VcDlgInsertModello.msgErrEqpBrand"), //$NON-NLS-1$
						Messages.getString("VcDlgInsertModello.msgTitleInfo"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
			}else{
				//verifica esistenza modello
				int id = 0;
				Statement smtpMod = con.createStatement();
				String qryMod = "select id from modelli " + //$NON-NLS-1$
						"where idMarchi = "+idMarca+" " + //$NON-NLS-1$ //$NON-NLS-2$
						"and idTipoApp = "+idAppa+" " + //$NON-NLS-1$ //$NON-NLS-2$
						"and nome = '"+modello+"'"; //$NON-NLS-1$ //$NON-NLS-2$
				ResultSet rsMod = smtpMod.executeQuery(qryMod);
				while (rsMod.next()) {
					id = rsMod.getInt(1);
				}
				rsMod.close();
				smtpMod.close();
				
				if (id > 0){
					JOptionPane.showMessageDialog(getParent(),
							Messages.getString("VcDlgInsertModello.msgErrModelExist"), //$NON-NLS-1$
							Messages.getString("VcDlgInsertModello.msgTitleInfo"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
					parent.inserisciNuovoModello(idAppa,idMarca,id,modello);
					this.setVisible(false);
					this.dispose();
				}else{
					//reperimento id
					Statement smtpId = con.createStatement();
					String qryId = "select max(id) from modelli"; //$NON-NLS-1$
					ResultSet rsId = smtpId.executeQuery(qryId);
					while (rsId.next()) {
						id = rsId.getInt(1);
					}
					rsId.close();
					smtpId.close();
					id++;
					
					//inserimento
					Statement smtpIns = con.createStatement();
					String ins = "insert into modelli " + //$NON-NLS-1$
							"(id,nome,descModello,idMarchi,idTipoApp,flagAttivo) " + //$NON-NLS-1$
							"values("+id+",'"+modello+"','"+descModello+"',"+idMarca+","+idAppa+",'S') "; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
					//System.out.println(ins);
					smtpIns.executeUpdate(ins);
					smtpIns.close();
					parent.inserisciNuovoModello(idAppa,idMarca,id,modello);
					this.setVisible(false);
					this.dispose();
				}
			}
		} catch (SQLException e) {
			logger.error("Exception in Inserisci \n"+e+"\n"); //$NON-NLS-1$ //$NON-NLS-2$
			//e.printStackTrace();
		}

	}

	/**
	 * This method initializes btnCanc	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnCanc() {
		if (btnCanc == null) {
			btnCanc = new JButton();
			btnCanc.setBounds(new Rectangle(189, 163, 85, 25));
			btnCanc.setText(Messages.getString("VcDlgInsertModello.btnCanc")); //$NON-NLS-1$
			btnCanc.setIcon(new ImageIcon(getClass().getResource("/net/sf/repairslab/ui/img/button_cancel.png"))); //$NON-NLS-1$
			btnCanc.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setVisible(false);
					dispose();
				}
			});
		}
		return btnCanc;
	}

	/**
	 * This method initializes txfDescMod	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxfDescMod() {
		if (txfDescMod == null) {
			txfDescMod = new JTextField();
			txfDescMod.setBounds(new Rectangle(145, 120, 190, 25));
		}
		return txfDescMod;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
