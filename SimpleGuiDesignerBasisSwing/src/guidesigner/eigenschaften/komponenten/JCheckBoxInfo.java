package guidesigner.eigenschaften.komponenten;

import javax.swing.JCheckBox;

import guidesigner.eigenschaften.GrundEigenschaft;
import guidesigner.eigenschaften.ereignisse.LauscherEigenschaft;
import guidesigner.eigenschaften.ereignisse.basislauscher.LBWahlBoxLauscherInfo;
import guidesigner.eigenschaften.ereignisse.basisschleife.EreignisBearbeiteMethodenEigenschaft;
import guidesigner.eigenschaften.ereignisse.swinglistener.LActionListenerInfo;
import guidesigner.eigenschaften.ereignisse.swinglistener.LChangeListenerInfo;
import guidesigner.oberflaeche.MovePanel;

public class JCheckBoxInfo extends KomponentenInfo {
	public JCheckBoxInfo(MovePanel mp, String komponententyp, String vname) {
		super(mp, komponententyp, vname);
		String text = ((JCheckBox) mp.getInhaltsKomponente()).getText();
		eigenschaften.add(new EigenschaftBeschriftung(text, 2, 1));
		// Localisation.swingEnglish.put("Text","Text");
		// Localisation.basisDeutsch.put("Text","Text");
		// //Localisation.swingDeutsch.put("Text","Text");
		// //Localisation.swingEnglish.put("text","text");
		// Localisation.basisDeutsch.put("text","text");
		// Localisation.swingDeutsch.put("text","text");

		eigenschaften.add(new KomponentenEigenschaft("Selektiert", "boolean",
				"setSelected", "setzeZustand", "selected", "boolean", true, 5,
				1));
		// Localisation.swingEnglish.put("Selektiert","Selected");
		// Localisation.basisDeutsch.put("Selektiert","Selektiert");
		// Localisation.swingDeutsch.put("Selektiert","Selektiert");
		// Localisation.swingEnglish.put("selektiert","selected");
		// Localisation.basisDeutsch.put("selektiert","selektiert");
		// Localisation.swingDeutsch.put("selektiert","selektiert");
		eigenschaften.add(new LauscherEigenschaft(new LActionListenerInfo(),
				true, 12, 1));
		eigenschaften.add(new LauscherEigenschaft(new LChangeListenerInfo(),
				false, 12, 2));
		eigenschaften.add(new LauscherEigenschaft(new LBWahlBoxLauscherInfo(),
				true, 12, 1));
		eigenschaften.add(new EreignisBearbeiteMethodenEigenschaft("WahlBox", "wurdeGeaendert", true, 12, 1));
	}

	@Override
	public boolean aendereEigenschaft(KomponentenEigenschaft eg, Object[] setterWerte) {
		super.aendereEigenschaft(eg, setterWerte);
		if (getPanel() instanceof MovePanel) {
			MovePanel mp = (MovePanel) getPanel();
			if (eg.getEigenschaftsName().equals("Selektiert")) {
				mp.deaktiviereAnfasser();
				((JCheckBox) mp.getInhaltsKomponente())
						.setSelected((boolean) setterWerte[0]);
				eg.setWert(setterWerte[0]);
				
			}
			if (eg.getEigenschaftsName().equals("Beschriftung")) {
				mp.deaktiviereAnfasser();
				((JCheckBox) mp.getInhaltsKomponente())
						.setText((String) setterWerte[0]);
				eg.setWert(setterWerte[0]);
			}

			mp.revalidate();

		}
		return true;
	}

}
