package guidesigner.oberflaeche;

import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import guidesigner.control.Controller;
import guidesigner.eigenschaften.komponenten.KomponentenBasisInfo;
import guidesigner.eigenschaften.komponenten.KomponentenGrundInfo;

public class KomponentenInfoVariablennameEditor {

	KomponentenBasisInfo zielKomponentenInfo;
	JPanel l;
	Controller controller;
	JTextField zf;
	private boolean ereignisseAus = false;

	

	public KomponentenInfoVariablennameEditor(Controller contro,
			KomponentenBasisInfo k, JPanel l, JPanel r) {
		controller = contro;
		this.zielKomponentenInfo = k;
		this.erzeugeEditor(l,r);
	}

	private void erzeugeEditor(JPanel l,JPanel r) {
		this.l = l;
		int zeilenhoehe = 25;
		int yOffset = 5;
		int y = l.getHeight();
		JLabel label = new JLabel();
		label.setText("Name");
		label.setBounds(5, y, 180, zeilenhoehe);
		l.add(label);
		zf = new JTextField();
		zf.setText(this.zielKomponentenInfo.getVariablenName());
		zf.setBounds(5, y, 100, zeilenhoehe);
		r.add(zf);
		this.setzeTextFeldEreignisbearbeitung(zf);
		y += (zeilenhoehe + yOffset);
		l.setPreferredSize(new Dimension(145, y + 2));
		l.setSize(l.getPreferredSize());
		l.revalidate();
		r.setPreferredSize(new Dimension(125, y + 2));
		r.setSize(r.getPreferredSize());
		r.revalidate();
	}

	

	public void setzeTextFeldEreignisbearbeitung(final JTextField tf) {
		try {

			tf.addKeyListener(new KeyAdapter() {

				public void keyReleased(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						tf.transferFocus();
						bearbeiteReturnWurdeGedrueckt();
					}

				}

			});
			tf.addFocusListener(new FocusListener() {

				@Override
				public void focusGained(FocusEvent arg0) {
					

				}

				@Override
				public void focusLost(FocusEvent arg0) {
					bearbeiteFokusVerloren();
				}

			});

		} catch (Exception e) {

		}

	}

	protected void bearbeiteFokusVerloren() {
		if (ereignisseAus) {
			return;
		}
		controller.aendereVariablennameUeberEditor(zielKomponentenInfo,
				zf.getText());
	}

	protected void bearbeiteReturnWurdeGedrueckt() {
		if (ereignisseAus) {
			return;
		}
		controller.aendereVariablennameUeberEditor(zielKomponentenInfo,
				zf.getText());
	}

	/**
	 * @param eigenschaft
	 */
	public void aktualisiereName(String name) {
		// ausschalten Ereignisse
		ereignisseAus = true;
		zf.setText(name);
		// einschalten Ereignisse
		ereignisseAus = false;

	}

}
