
/**
 * @author Georg Dick
 *
 */ 
package guidesigner.eigenschaften.ereignisse.basisschleife;

import javax.swing.JPanel;

import guidesigner.eigenschaften.GrundEigenschaft;
import guidesigner.eigenschaften.komponenten.KomponentenEigenschaft;
import guidesigner.eigenschaften.komponenten.KomponentenGrundInfo;
import guidesigner.oberflaeche.MovePanel;



public class MausBasisInfo extends KomponentenGrundInfo {
	/**
	 * @param mp
	 * @param komponententyp
	 * @param vname
	 */
	public MausBasisInfo(JPanel mp, String komponententyp, String vname) {
		super(mp, komponententyp, vname);
		eigenschaften.add(new BearbeiteMethodeFuerMausEigenschaft("istGedrueckt", false, 10, 1));
		eigenschaften.add(new BearbeiteMethodeFuerMausEigenschaft("istRechtsGedrueckt", false, 10, 1));
		eigenschaften.add(new BearbeiteMethodeFuerMausEigenschaft("wurdeGeklickt", false, 10, 1));
		eigenschaften.add(new BearbeiteMethodeFuerMausEigenschaft("wurdeBewegt", false, 10, 1));
	}
	@Override
	public boolean aendereEigenschaft(KomponentenEigenschaft eg, Object[] setterWerte) {
//		if (eg instanceof BearbeiteMethodeEigenschaft){
//			System.out.println("BearbeiteMethodeEigenschaft "+setterWerte[0]);
//		}
		super.aendereEigenschaft(eg, setterWerte);
		if (getPanel() instanceof MovePanel) {
			MovePanel mp = (MovePanel) getPanel();
				if (eg.getEigenschaftsName().equals("istGedrueckt")||
						eg.getEigenschaftsName().equals("istRechtsGedrueckt")||
						eg.getEigenschaftsName().equals("wurdeGeklickt")||	
						eg.getEigenschaftsName().equals("wurdeBewegt")
						
						) {
					
					eg.setWert(setterWerte[0]);
				}
			
			mp.revalidate();
		}
		return true;
	}
}
