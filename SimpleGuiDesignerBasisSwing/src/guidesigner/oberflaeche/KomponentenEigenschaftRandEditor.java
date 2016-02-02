package guidesigner.oberflaeche;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import guidesigner.control.Controller;
import guidesigner.eigenschaften.komponenten.KomponentenBasisInfo;
import guidesigner.eigenschaften.komponenten.KomponentenEigenschaft;

public class KomponentenEigenschaftRandEditor extends KomponentenEigenschaftseditor {
	
	public KomponentenEigenschaftRandEditor(Controller contro, KomponentenEigenschaft eg, KomponentenBasisInfo k,
			JPanel l, JPanel r) {
		controller = contro;
		this.eigenschaft = eg;
		this.zielKomponentenInfo = k;
		this.erzeugeEditorKomponentenEigenschaft((KomponentenEigenschaft) eigenschaft, l, r);
		this.aktualisiereEigenschaftsanzeige(eg);
	}
	
    public  void erzeugeEditorKomponentenEigenschaft(KomponentenEigenschaft eigenschaft, JPanel l, JPanel r) {
			this.l = l;
			int zeilenhoehe = 25;
			int yOffset = 5;
			int y = l.getHeight();
			JLabel label = new JLabel();
			label.setText(eigenschaft.getEigenschaftsName());
			label.setBounds(5, y, 180, zeilenhoehe);
			l.add(label);
			zf = new Component[eigenschaft.getSetterParameterTypen().length];
			for (int i = 0; i < eigenschaft.getSetterParameterTypen().length; i++) {
				String parametertyp = eigenschaft.getSetterParameterTypen()[i];
				if (parametertyp.equals("int")) {
					zf[i] = new JTextField();
					zf[i].setBounds(5, y, 100, zeilenhoehe);
					r.add(zf[i]);
					this.setzeIntZahlenFeldEreignisbearbeitung((JTextField) zf[i], i);
					y += (zeilenhoehe + yOffset);
				}				
				if (parametertyp.equals("java.awt.Color")) {
					zf[i] = new JButton();
					((JButton) zf[i]).setText("Randfarbe");
					zf[i].setBounds(5, y, 100, zeilenhoehe);
					r.add(zf[i]);
					((JButton) zf[i]).addActionListener(this);
					y += (zeilenhoehe + yOffset);
				}				
			}
			l.setPreferredSize(new Dimension(145, y + 2));
			l.setSize(l.getPreferredSize());
			l.revalidate();
			r.setPreferredSize(new Dimension(125, y + 2));
			r.setSize(r.getPreferredSize());
			r.revalidate();
		}

}
