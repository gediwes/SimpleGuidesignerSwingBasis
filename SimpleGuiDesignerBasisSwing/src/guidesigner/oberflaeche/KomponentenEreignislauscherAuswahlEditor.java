package guidesigner.oberflaeche;

import java.awt.Dimension;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import guidesigner.control.Controller;
import guidesigner.eigenschaften.GrundEigenschaft;
import guidesigner.eigenschaften.ereignisse.LauscherEigenschaft;
import guidesigner.eigenschaften.komponenten.KomponentenBasisInfo;

public class KomponentenEreignislauscherAuswahlEditor implements ChangeListener, EditorInterface {

	GrundEigenschaft eigenschaft;

	/**
	 * @return liefert eigenschaft
	 */
	public GrundEigenschaft getEigenschaft() {
		return eigenschaft;
	}

	KomponentenBasisInfo zielKomponentenInfo;
	JPanel l;

	// private boolean merkeLoeschen;
	Controller controller;
	JCheckBox zf;
	private boolean ereignisseAus = false;

	public KomponentenEreignislauscherAuswahlEditor() {
	}

	public KomponentenEreignislauscherAuswahlEditor(Controller contro, GrundEigenschaft eg, KomponentenBasisInfo k, JPanel l,
			JPanel r) {
		controller = contro;
		this.eigenschaft = eg;
		this.zielKomponentenInfo = k;
		this.erzeugeEditorBoolean(l, r);
		this.aktualisiereEigenschaftsanzeige(eg);

	}

	private void erzeugeEditorBoolean(JPanel l, JPanel r) {
		this.l = l;
		int zeilenhoehe = 25;
		int yOffset = 5;
		int y = l.getHeight();
		JLabel label = new JLabel();
		label.setText(eigenschaft.getEigenschaftsName());
		label.setBounds(5, y, 180, zeilenhoehe);
		l.add(label);

		zf = new JCheckBox();
		zf.setBounds(5, y, 100, zeilenhoehe);
		r.add(zf);
		zf.setSelected(true);
		zf.addChangeListener(this);
		y += (zeilenhoehe + yOffset);

		l.setPreferredSize(new Dimension(145, y + 2));
		l.setSize(l.getPreferredSize());
		l.revalidate();
		r.setPreferredSize(new Dimension(125, y + 2));
		r.setSize(r.getPreferredSize());
		r.revalidate();
	}

	public Object getWert(String typ, JCheckBox zf) {
		return zf.isSelected();
	}

	/**
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	@Override
	public void stateChanged(ChangeEvent arg0) { 		 
		eigenschaft.setWert(zf.isSelected());
	}

	/**
	 * @param peigenschaft
	 */
	public void aktualisiereEigenschaftsanzeige(GrundEigenschaft peigenschaft) {
		// ausschalten Ereignisse
		ereignisseAus = true;
		zf.setSelected((boolean) peigenschaft.getWert().toString().equals("true"));
		// einschalten Ereignisse
		ereignisseAus = false;
		
	}

}
