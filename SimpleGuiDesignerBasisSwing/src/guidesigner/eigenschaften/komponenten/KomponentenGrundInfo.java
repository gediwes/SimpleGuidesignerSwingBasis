package guidesigner.eigenschaften.komponenten;

import java.awt.Point;

import javax.swing.JPanel;

import guidesigner.oberflaeche.MovePanel;

public class KomponentenGrundInfo extends KomponentenBasisInfo{
	

	
	public KomponentenGrundInfo(JPanel mp, String komponententyp, String vname) {
		super(mp,komponententyp,vname);

		
		Object[] spw2 = new Object[2];
		spw2[0] = mp.getLocation().x;
		spw2[1] = mp.getLocation().y;
		addEigenschaft(new EigenschaftPosition(spw2,  5, 1));
//		addEigenschaft(new KomponentenEigenschaft("Position", "java.awt.Point",
//				"setLocation", "setzePosition", new String[] { "x", "y" }, new String[]{ "int", "int" }, spw2,  5, 1));

	}

	
@Override
	public boolean aendereEigenschaft(KomponentenEigenschaft eg, Object[] setterWerte) {
		JPanel jp = getPanel();
		if (!(jp instanceof MovePanel)) {
			return true;
		}
		MovePanel mp = (MovePanel) jp;

		if (eg.getEigenschaftsName().equals("Position")) {
			mp.deaktiviereAnfasser();			
			mp.setLocation(new Point((int) setterWerte[0], (int) setterWerte[1]));
			eg.setWert(new Object[]{(int) setterWerte[0], (int) setterWerte[1]});
			mp.revalidate();
		}
		return true;
	}

	

}
