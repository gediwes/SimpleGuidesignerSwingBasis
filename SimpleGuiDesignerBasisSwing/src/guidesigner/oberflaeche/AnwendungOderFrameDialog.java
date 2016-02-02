/**
 * 
 */
package guidesigner.oberflaeche;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import guidesigner.control.Bezeichnertester;
import guidesigner.control.KlassennameUndOberklasse;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * @author Georg Dick
 *
 */
public class AnwendungOderFrameDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rdbtnKlasseAlsUnterklasse;
	private JRadioButton rdbtnKlasseAlsAnwendung;
	protected boolean ok=false;
	private JTextField txtKlassea;
	protected String klassenname;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AnwendungOderFrameDialog dialog = new AnwendungOderFrameDialog(100,100);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AnwendungOderFrameDialog(int x, int y) {
		setResizable(false);
		setModal(true);
		setBounds(x, y, 529, 278);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		 GridBagLayout gbl_contentPanel = new GridBagLayout();
		 gbl_contentPanel.columnWeights = new double[]{0.0, 1.0};
		 gbl_contentPanel.rowWeights = new double[]{1.0, 1.0, 1.0};
		 contentPanel.setLayout(gbl_contentPanel);
		 		  //		Localisation.setUebersetzung("Klassenname", "Classname", "englisch");
		 		  //		JLabel lblKlassenname = new JLabel(Localisation.getUebersetzung("Klassenname"));
		 		  		JLabel lblKlassenname = new JLabel("Klassenname");
		 		  		GridBagConstraints gbc_lblKlassenname = new GridBagConstraints();
		 		  		gbc_lblKlassenname.insets = new Insets(0, 0, 5, 5);
		 		  		gbc_lblKlassenname.anchor = GridBagConstraints.EAST;
		 		  		gbc_lblKlassenname.fill = GridBagConstraints.BOTH;
		 		  		gbc_lblKlassenname.gridx = 0;
		 		  		gbc_lblKlassenname.gridy = 0;
		 		  		contentPanel.add(lblKlassenname, gbc_lblKlassenname);
		 		  		 
		 		  		 txtKlassea = new JTextField();
		 		  		 txtKlassea.addFocusListener(new FocusAdapter() {
		 		  		 	@Override
		 		  		 	public void focusGained(FocusEvent arg0) {
		 		  		 	txtKlassea.selectAll();
		 		  		 	}
		 		  		 });
		 		  		 txtKlassea.setText("KlasseA");
		 		  		 GridBagConstraints gbc_txtKlassea = new GridBagConstraints();
		 		  		 gbc_txtKlassea.anchor = GridBagConstraints.EAST;
		 		  		 gbc_txtKlassea.fill = GridBagConstraints.BOTH;
		 		  		 gbc_txtKlassea.insets = new Insets(0, 10, 5, 0);
		 		  		 gbc_txtKlassea.gridx = 1;
		 		  		 gbc_txtKlassea.gridy = 0;
		 		  		 contentPanel.add(txtKlassea, gbc_txtKlassea);
		 		  		 txtKlassea.setColumns(10);
		 		  //		Localisation.setUebersetzung("Klasse als Unterklasse von Fenster implementieren", "implement the new class as extension of JFrame", "englisch");
		 		  		 rdbtnKlasseAlsUnterklasse = new JRadioButton("Klasse als Unterklasse von Fenster implementieren");
		 		  		 buttonGroup.add(rdbtnKlasseAlsUnterklasse);
		 		  		 GridBagConstraints gbc_rdbtnKlasseAlsUnterklasse = new GridBagConstraints();
		 		  		 gbc_rdbtnKlasseAlsUnterklasse.insets = new Insets(0, 0, 5, 0);
		 		  		 gbc_rdbtnKlasseAlsUnterklasse.anchor = GridBagConstraints.EAST;
		 		  		 gbc_rdbtnKlasseAlsUnterklasse.fill = GridBagConstraints.BOTH;
		 		  		 gbc_rdbtnKlasseAlsUnterklasse.gridwidth = 2;
		 		  		 gbc_rdbtnKlasseAlsUnterklasse.gridx = 0;
		 		  		 gbc_rdbtnKlasseAlsUnterklasse.gridy = 1;
		 		  		 contentPanel.add(rdbtnKlasseAlsUnterklasse, gbc_rdbtnKlasseAlsUnterklasse);
		 		  //		Localisation.setUebersetzung("Klasse als Anwendung mit zugehörigem Fenster implementieren", "implement the new class as application using a JFrame", "englisch");
		 		  		  rdbtnKlasseAlsAnwendung = new JRadioButton("Klasse als Anwendung mit zugehörigem Fenster implementieren");
		 		  		  rdbtnKlasseAlsAnwendung.setSelected(true);
		 		  		  buttonGroup.add(rdbtnKlasseAlsAnwendung);
		 		  		  GridBagConstraints gbc_rdbtnKlasseAlsAnwendung = new GridBagConstraints();
		 		  		  gbc_rdbtnKlasseAlsAnwendung.insets = new Insets(0, 0, 5, 0);
		 		  		  gbc_rdbtnKlasseAlsAnwendung.anchor = GridBagConstraints.EAST;
		 		  		  gbc_rdbtnKlasseAlsAnwendung.fill = GridBagConstraints.BOTH;
		 		  		  gbc_rdbtnKlasseAlsAnwendung.gridwidth = 2;
		 		  		  gbc_rdbtnKlasseAlsAnwendung.gridx = 0;
		 		  		  gbc_rdbtnKlasseAlsAnwendung.gridy = 2;
		 		  		  contentPanel.add(rdbtnKlasseAlsAnwendung, gbc_rdbtnKlasseAlsAnwendung);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {						
						if (!Bezeichnertester.bezeichnerOK(txtKlassea.getText())){
							//Localisation.setUebersetzung("Der Klassenbezeichner ist unzulässig", "classname is not correct", "englisch");
							JOptionPane.showMessageDialog(AnwendungOderFrameDialog.this,txtKlassea.getText()+": Der Klassenbezeichner ist unzulässig");
							return;
						}
						klassenname = txtKlassea.getText();
						ok = true;
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				//Localisation.setUebersetzung("Abbruch", "Cancel", "englisch");
				JButton cancelButton = new JButton("Abbruch");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ok = false;
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Abbruch");
				buttonPane.add(cancelButton);
			}
		}
		setVisible(true);
	
		
		
	}
	
	public static KlassennameUndOberklasse  getIstKlasseAnwendungByDialog(int x, int y) {
		AnwendungOderFrameDialog dialog = new AnwendungOderFrameDialog(x,y);
		
		if (!dialog.ok()) {
			return null;
		}
		boolean istKlasseAnwendung= dialog.rdbtnKlasseAlsAnwendung.isSelected() ;
		KlassennameUndOberklasse ko = new KlassennameUndOberklasse(dialog.klassenname, istKlasseAnwendung);		
		dialog.dispose();
		return ko;
	}

	/**
	 * @return
	 */
	private boolean ok() {
		// TODO Auto-generated method stub
		return ok;
	}
}
