
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



public class TastaturInfo extends KomponentenGrundInfo {

	/**
	 * @param mp
	 * @param komponententyp
	 * @param vname
	 */
	public TastaturInfo(JPanel mp, String komponententyp, String vname) {
		super(mp, komponententyp, vname);
		eigenschaften.add(new BearbeiteMethodeFuerTastaturEigenschaft("wurdeGedrueckt", true, 10, 1));
		
	}
	@Override
	public boolean aendereEigenschaft(KomponentenEigenschaft eg, Object[] setterWerte) {
//		if (eg instanceof BearbeiteMethodeEigenschaft){
//			System.out.println("BearbeiteMethodeEigenschaft "+setterWerte[0]);
//		}
		super.aendereEigenschaft(eg, setterWerte);
		if (getPanel() instanceof MovePanel) {
			MovePanel mp = (MovePanel) getPanel();
				if (eg.getEigenschaftsName().equals("wurdeGedrueckt")
						
						) {
					
					eg.setWert(setterWerte[0]);
				}
			
			mp.revalidate();
		}
		return true;
	}
}
