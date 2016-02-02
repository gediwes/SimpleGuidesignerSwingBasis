package guidesigner.eigenschaften.komponenten;

import javax.swing.JScrollBar;
import javax.swing.JSlider;

import guidesigner.eigenschaften.ereignisse.LauscherEigenschaft;
import guidesigner.eigenschaften.ereignisse.basislauscher.LBRollbalkenLauscherInfo;
import guidesigner.eigenschaften.ereignisse.basisschleife.EreignisBearbeiteMethodenEigenschaft;
import guidesigner.eigenschaften.ereignisse.swinglistener.LAdjustmentListenerInfo;
import guidesigner.oberflaeche.MovePanel;

public class JScrollBarInfo extends KomponentenInfo {
	public JScrollBarInfo(MovePanel mp, String komponententyp, String vname) {
		super(mp, komponententyp, vname);
		 int minwert = ((JScrollBar) mp.getInhaltsKomponente()).getMinimum();
		 int maxwert = ((JScrollBar) mp.getInhaltsKomponente()).getMaximum();
		 int wert = ((JScrollBar) mp.getInhaltsKomponente()).getValue();
		// int orientierung = ((JScrollBar) mp.getInhaltsKomponente())
		// .getOrientation();
		
		 eigenschaften
				.add(new KomponentenEigenschaft("Minimum", "int", "setMinimum", "setzeMinimum", "minwert", "int", minwert, 4, 1));
		 eigenschaften
				.add(new KomponentenEigenschaft("Maximum", "int", "setMaximum", "setzeMaximum", "maxwert", "int", maxwert, 4, 2));
		 eigenschaften.add(new KomponentenEigenschaft("Wert", "int", "setValue", "setzeWert", "wert", "int", wert, 4, 3));
		
//		eigenschaften.add(new Eigenschaft("Minimum", "int", "setMinimum",
//				"setzeMinimum", "minwert", "int", 0, 4, 1));
//		eigenschaften.add(new Eigenschaft("Maximum", "int", "setMaximum",
//				"setzeMaximum", "maxwert", "int", 100, 4, 2));
//		eigenschaften.add(new Eigenschaft("Wert", "int", "setValue", "setzeWert",
//				"wert", "int", 50, 4, 3));
		 eigenschaften.add(new EigenschaftOrientierungFuerJSliderUndJScrollBar(
				"Orientierung horizontal", "boolean", "setOrientation",
				"orientierung", "boolean", true, 4, 4));
		 eigenschaften.add(new LauscherEigenschaft(
				new LAdjustmentListenerInfo(), true, 12, 1));
		eigenschaften.add(new LauscherEigenschaft(new LBRollbalkenLauscherInfo(),
				true, 12, 1));
		eigenschaften.add(new EreignisBearbeiteMethodenEigenschaft("Rollbalken", "wurdeBewegt", true, 12, 1));
		

	}

	@Override
	public boolean aendereEigenschaft(KomponentenEigenschaft eg, Object[] setterWerte) {
		// System.out.println("eg.getEigenschaftsName()");
		super.aendereEigenschaft(eg, setterWerte);
		if (getPanel() instanceof MovePanel) {
			MovePanel mp = (MovePanel) getPanel();

			if (eg.getEigenschaftsName().equals("Orientierung horizontal")) {
				if (mp.getInhaltsKomponente() instanceof JScrollBar) {
					if ((boolean) setterWerte[0]) {
						((JScrollBar) mp.getInhaltsKomponente())
								.setOrientation(JScrollBar.HORIZONTAL);
					} else {
						((JScrollBar) mp.getInhaltsKomponente())
								.setOrientation(JScrollBar.VERTICAL);
					}
				}
				if (mp.getInhaltsKomponente() instanceof JSlider) {
					if ((boolean) setterWerte[0]) {
						((JSlider) mp.getInhaltsKomponente())
								.setOrientation(JScrollBar.HORIZONTAL);
					} else {
						((JSlider) mp.getInhaltsKomponente())
								.setOrientation(JScrollBar.VERTICAL);
					}
				}
				eg.setWert(setterWerte[0]);
			}

			if (eg.getEigenschaftsName().equals("Minimum")) {
				if (mp.getInhaltsKomponente() instanceof JScrollBar) {
					((JScrollBar) mp.getInhaltsKomponente())
							.setMinimum((int) setterWerte[0]);
				}
				if (mp.getInhaltsKomponente() instanceof JSlider) {
					((JSlider) mp.getInhaltsKomponente())
							.setMinimum((int) setterWerte[0]);
				}
				eg.setWert(setterWerte[0]);
			}
			if (eg.getEigenschaftsName().equals("Maximum")) {
				if (mp.getInhaltsKomponente() instanceof JScrollBar) {
					((JScrollBar) mp.getInhaltsKomponente())
							.setMaximum((int) setterWerte[0]);
				}
				if (mp.getInhaltsKomponente() instanceof JSlider) {
					((JSlider) mp.getInhaltsKomponente())
							.setMaximum((int) setterWerte[0]);
				}
				eg.setWert(setterWerte[0]);
			}
			if (eg.getEigenschaftsName().equals("Wert")) {
				if (mp.getInhaltsKomponente() instanceof JScrollBar) {
					((JScrollBar) mp.getInhaltsKomponente())
							.setValue((int) setterWerte[0]);
				}
				if (mp.getInhaltsKomponente() instanceof JSlider) {
					((JSlider) mp.getInhaltsKomponente())
							.setValue((int) setterWerte[0]);

				}
				eg.setWert(setterWerte[0]);

			}
			mp.revalidate();
		}
		return true;
	}
}
