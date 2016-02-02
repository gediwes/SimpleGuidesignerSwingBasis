package guidesigner.eigenschaften.komponenten;

import java.awt.Container;
import java.awt.Font;

import javax.swing.JButton;

import guidesigner.control.ZuordungBasisSwing;
import guidesigner.eigenschaften.GrundEigenschaft;
import guidesigner.eigenschaften.ereignisse.LauscherEigenschaft;
import guidesigner.eigenschaften.ereignisse.basislauscher.LBKnopfLauscherInfo;
import guidesigner.eigenschaften.ereignisse.basisschleife.EreignisBearbeiteMethodenEigenschaft;
import guidesigner.eigenschaften.ereignisse.swinglistener.LActionListenerInfo;
import guidesigner.oberflaeche.MovePanel;

public class JButtonInfo extends KomponentenInfo {

	public JButtonInfo(MovePanel mp, String komponententyp, String vname) {
		super(mp, komponententyp, vname);
		String text = ((JButton) mp.getInhaltsKomponente()).getText();
		
		eigenschaften.add(new EigenschaftBeschriftung(text, 2, 1));
		
		//Localisation.swingEnglish.put("Text","Text");	
		eigenschaften.add(new LauscherEigenschaft(new LActionListenerInfo(),true,12,1));
		eigenschaften.add(new LauscherEigenschaft(new LBKnopfLauscherInfo(),true,12,1));
		eigenschaften.add(new EreignisBearbeiteMethodenEigenschaft("Knopf", "wurdeGedrueckt", true, 12, 1));
	}
	
	@Override
	public boolean aendereEigenschaft(KomponentenEigenschaft eg, Object[] setterWerte) {
//		if (eg instanceof BearbeiteMethodeEigenschaft){
//			System.out.println("BearbeiteMethodeEigenschaft "+setterWerte[0]);
//		}
		super.aendereEigenschaft(eg, setterWerte);
		if (getPanel() instanceof MovePanel) {
			MovePanel mp = (MovePanel) getPanel();
				if (eg.getEigenschaftsName().equals("Beschriftung")) {
					mp.deaktiviereAnfasser();
					((JButton) mp.getInhaltsKomponente())
							.setText((String) setterWerte[0]);
					eg.setWert(setterWerte[0]);
				}
				
			mp.revalidate();
		}
		return true;
	}
	
	
}
