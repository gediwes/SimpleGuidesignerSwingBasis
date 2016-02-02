package guidesigner.eigenschaften.komponenten;

import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import guidesigner.eigenschaften.ereignisse.LauscherEigenschaft;
import guidesigner.eigenschaften.ereignisse.basislauscher.LBListBoxLauscherInfo;
import guidesigner.eigenschaften.ereignisse.basisschleife.EreignisBearbeiteMethodenEigenschaft;
import guidesigner.eigenschaften.ereignisse.swinglistener.LActionListenerInfo;
import guidesigner.eigenschaften.ereignisse.swinglistener.LItemListenerInfo;
import guidesigner.oberflaeche.MovePanel;

public class JComboBoxInfo extends KomponentenInfo {
	public JComboBoxInfo(MovePanel mp, String komponententyp, String vname) {
		super(mp, komponententyp, vname);
		Vector<String> eintraege = new Vector();
		eintraege.add("a");
		eintraege.add("b");
		
		eigenschaften.add(new EigenschaftEintragFuerJComboBox( eintraege, 2, 1));
		eigenschaften.add(new LauscherEigenschaft(new LActionListenerInfo(), true, 12, 1));
		eigenschaften.add(new LauscherEigenschaft(new LItemListenerInfo(), false, 12, 2));
		eigenschaften.add(new LauscherEigenschaft(new LBListBoxLauscherInfo(), false, 12, 1));
		eigenschaften.add(new EreignisBearbeiteMethodenEigenschaft("ListBox", "wurdeGewaehlt", true, 12, 1));

	}

	@Override
	public boolean aendereEigenschaft(KomponentenEigenschaft eg, Object[] setterWerte) {
		super.aendereEigenschaft(eg, setterWerte);
		if (getPanel() instanceof MovePanel) {
			MovePanel mp = (MovePanel) getPanel();
			if (eg.getEigenschaftsName().equals("EintraegeComboBox")) {
				mp.deaktiviereAnfasser();
				if (setterWerte != null && setterWerte[0] != null) {
					Vector<String> als = (Vector<String>) setterWerte[0];
					String[] s = new String[als.size()];
					s = als.toArray(s);
					((JComboBox) mp.getInhaltsKomponente()).setModel(new DefaultComboBoxModel(s));
					eg.setWert(als);
				}				
				mp.revalidate();
			}
		}
		return true;
	}

}
