package guidesigner.eigenschaften.komponenten;

import java.util.Vector;

import javax.swing.AbstractListModel;
import javax.swing.JList;
import javax.swing.JPanel;

import guidesigner.eigenschaften.ereignisse.LauscherEigenschaft;
import guidesigner.eigenschaften.ereignisse.basislauscher.LBListAuswahlLauscherInfo;
import guidesigner.eigenschaften.ereignisse.basisschleife.EreignisBearbeiteMethodenEigenschaft;
import guidesigner.oberflaeche.MovePanel;

public class JListInfo extends KomponentenInfo {

	/**
	 * @param mp
	 * @param komponententyp
	 * @param vname
	 */
	public JListInfo(JPanel mp, String komponententyp, String vname) {
		super(mp, komponententyp, vname);
		Vector<String> eintraege = new Vector();
		eintraege.add("a");
		eintraege.add("b");
	
		eigenschaften.add(new EigenschaftEintragFuerJList(eintraege, 2, 1));
		eigenschaften.add(new KomponentenEigenschaft("Scrollbar", "boolean", "", "", "", "boolean", true, 6, 1));
		eigenschaften.add(new LauscherEigenschaft(new LBListAuswahlLauscherInfo(), true, 10, 1));
		eigenschaften.add(new EreignisBearbeiteMethodenEigenschaft("ListAuswahl", "wurdeGewaehlt", true, 12, 1));

	}

	@Override
	public boolean aendereEigenschaft(KomponentenEigenschaft eg, Object[] setterWerte) {
		super.aendereEigenschaft(eg, setterWerte);
		if (getPanel() instanceof MovePanel) {
			MovePanel mp = (MovePanel) getPanel();
			if (eg.getEigenschaftsName().equals("Scrollbar")) {
				eg.setWert(setterWerte[0]);
			}
			if (eg.getEigenschaftsName().equals("EintraegeJList")) {
				mp.deaktiviereAnfasser();
				if (setterWerte != null && setterWerte[0] != null) {
					Vector<String> als = (Vector<String>) setterWerte[0];

					if (als.size() > 0) {
						String[] s = new String[als.size()];
						s = als.toArray(s);
						String[] sta = s;
						((JList) mp.getInhaltsKomponente()).setModel(new AbstractListModel() {
							String[] values = sta;
							public int getSize() {
								return values.length;
							}
							public Object getElementAt(int index) {
								return values[index];
							}
						});
					} else {
						((JList) mp.getInhaltsKomponente()).setModel(new AbstractListModel() {
							public int getSize() {
								return 0;
							}

							public Object getElementAt(int index) {
								return null;
							}
						});
					}
					eg.setWert(als);
				}
				mp.revalidate();
			}
		}

		return true;
	}

}
