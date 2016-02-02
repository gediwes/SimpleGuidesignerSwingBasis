package guidesigner.eigenschaften.komponenten;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import guidesigner.eigenschaften.GrundEigenschaft;
import guidesigner.eigenschaften.ereignisse.LauscherEigenschaft;
import guidesigner.eigenschaften.ereignisse.basislauscher.LBFensterLauscherInfo;
import guidesigner.oberflaeche.MovePanel;

public class JFrameInfo extends KomponentenInfo {
	public JFrameInfo(JPanel mp, String komponententyp, String vname) {
		super(mp, komponententyp, vname);
		// Localisation.swingEnglish.put("Titel","Title");
		eigenschaften
				.add(new EigenschaftTitel("Titel", 1, 1));
		eigenschaften.add(new LauscherEigenschaft(new LBFensterLauscherInfo(), false, 12, 1));
		// Localisation.basisDeutsch.put("Titel","Titel");
		// Localisation.swingDeutsch.put("Titel","Titel");
		// Localisation.swingEnglish.put("titel","title");
		// Localisation.basisDeutsch.put("titel","titel");
		// Localisation.swingDeutsch.put("titel","title");

	}

	@Override
	public boolean aendereEigenschaft(KomponentenEigenschaft eg, Object[] setterWerte) {
		// JOptionPane.showMessageDialog(null, ""+eg.getEigenschaftsName());
		super.aendereEigenschaft(eg, setterWerte);
		JPanel jp = getPanel();
		if (!(jp instanceof MovePanel)) {
			// Fenster
			if (eg.getEigenschaftsName().equals("Größe")) {
				jp.setSize(new Dimension((int) setterWerte[0], (int) setterWerte[1]));
				jp.getParent().setPreferredSize(jp.getSize());
				eg.setWert(new Object[]{(int) setterWerte[0], (int) setterWerte[1]});
			}
			if (eg.getEigenschaftsName().equals("Hintergrundfarbe")) {
				jp.setBackground((Color) setterWerte[0]);
				eg.setWert((Color) setterWerte[0]);
			}

			if (eg.getEigenschaftsName().equals("Titel")) {
				eg.setWert(setterWerte[0]);
			}

			jp.revalidate();
			return true;
		}

		return true;
	}

}
