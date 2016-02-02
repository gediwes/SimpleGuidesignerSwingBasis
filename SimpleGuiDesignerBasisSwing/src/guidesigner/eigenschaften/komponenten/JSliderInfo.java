package guidesigner.eigenschaften.komponenten;

import java.util.Dictionary;

import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JScrollBar;
import javax.swing.JSlider;

import guidesigner.eigenschaften.GrundEigenschaft;
import guidesigner.eigenschaften.ereignisse.LauscherEigenschaft;
import guidesigner.eigenschaften.ereignisse.basislauscher.LBReglerLauscherInfo;
import guidesigner.eigenschaften.ereignisse.basisschleife.EreignisBearbeiteMethodenEigenschaft;
import guidesigner.eigenschaften.ereignisse.swinglistener.LChangeListenerInfo;
import guidesigner.oberflaeche.MovePanel;

public class JSliderInfo extends KomponentenInfo {
	public JSliderInfo(MovePanel mp, String komponententyp, String vname) {
		super(mp, komponententyp, vname);

		int maxtick = ((JSlider) mp.getInhaltsKomponente()).getMajorTickSpacing();
		int mintick = ((JSlider) mp.getInhaltsKomponente()).getMinorTickSpacing();
		boolean paintticks = ((JSlider) mp.getInhaltsKomponente()).getPaintTicks();
		boolean painttrack = ((JSlider) mp.getInhaltsKomponente()).getPaintTrack();
		boolean snap = ((JSlider) mp.getInhaltsKomponente()).getSnapToTicks();
		boolean paintlabels = ((JSlider) mp.getInhaltsKomponente()).getPaintLabels();
		int wert = ((JSlider) mp.getInhaltsKomponente()).getValue();
		int minwert = ((JSlider) mp.getInhaltsKomponente()).getMinimum();
		int maxwert = ((JSlider) mp.getInhaltsKomponente()).getMaximum();
		eigenschaften.add(new EigenschaftOrientierungFuerJSliderUndJScrollBar(
				"Orientierung horizontal", "boolean", "setOrientation",
				"orientierung", "boolean", true, 6, 4));
		eigenschaften
				.add(new KomponentenEigenschaft("Minimum", "int", "setMinimum", "setzeMinimum", "minwert", "int", minwert, 6, 1));
		eigenschaften
				.add(new KomponentenEigenschaft("Maximum", "int", "setMaximum", "setzeMaximum", "maxwert", "int", maxwert, 6, 2));
		eigenschaften.add(new KomponentenEigenschaft("Wert", "int", "setValue", "setzeWert", "wert", "int", wert, 6, 3));
		eigenschaften.add(new KomponentenEigenschaft("Skala beschriften", "boolean", "setPaintLabels",
				"setzeSkalenBeschriftungSichtbar", "beschriften", "boolean", paintlabels, 6, 5));
		eigenschaften.add(new KomponentenEigenschaft("Skala zeigen", "boolean", "setPaintTicks", "setzeSkalenStricheSichtbar",
				"zeigen", "boolean", paintticks, 6, 6));
		eigenschaften.add(new KomponentenEigenschaft("Minimaler Wertabstand", "int", "setMinorTickSpacing",
				"setzeMinimalenWertAbstand", "minabstand", "int", mintick, 6, 7));
		eigenschaften.add(new KomponentenEigenschaft("Hauptwertabstand", "int", "setMajorTickSpacing", "setzeHauptWertAbstand",
				"maxabstand", "int", maxtick, 6, 8));
		eigenschaften.add(new KomponentenEigenschaft("Bereich zeigen", "boolean", "setPaintTrack", "setzeBereichZeigen", "zeigen",
				"boolean", painttrack, 6, 10));
		eigenschaften.add(new KomponentenEigenschaft("Einrasten", "boolean", "setSnapToTicks", "rasteEin", "rasten", "boolean",
				snap, 6, 11));
		eigenschaften.add(new EigenschaftInvertierungFuerJSlider("Invertiere Skala",false, 6, 12));
		eigenschaften.add(new LauscherEigenschaft(new LChangeListenerInfo(), true, 12, 1));
		eigenschaften.add(new LauscherEigenschaft(new LBReglerLauscherInfo(), true, 12, 1));
		eigenschaften.add(new EreignisBearbeiteMethodenEigenschaft("Regler", "wurdeBewegt", true, 12, 1));
	}

	@Override
	public boolean aendereEigenschaft(KomponentenEigenschaft eg, Object[] setterWerte) {
		super.aendereEigenschaft(eg, setterWerte);
		if (getPanel() != null && getPanel() instanceof MovePanel) {
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
			if (eg.getEigenschaftsName().equals("Invertiere Skala")) {
				mp.deaktiviereAnfasser();
				
				((JSlider) mp.getInhaltsKomponente()).setInverted((boolean) setterWerte[0]);
				eg.setWert(setterWerte[0]);
			}
			if (eg.getEigenschaftsName().equals("Skala beschriften")) {
				mp.deaktiviereAnfasser();
	//			DefaultBoundedRangeModel model;
//				model = new DefaultBoundedRangeModel(arg0, arg1, arg2, arg3);
	//			JSlider js;
//				js.setModel(model);
//				js.getLabelTable()
			//	Dictionary d;
//				js.setLabelTable(labels);
	//			Dictionary d;
				
				
				((JSlider) mp.getInhaltsKomponente()).setPaintLabels((boolean) setterWerte[0]);
				eg.setWert(setterWerte[0]);
			}

			if (eg.getEigenschaftsName().equals("Skala zeigen")) {
				mp.deaktiviereAnfasser();
				((JSlider) mp.getInhaltsKomponente()).setPaintTicks((boolean) setterWerte[0]);
				eg.setWert(setterWerte[0]);
			}
			if (eg.getEigenschaftsName().equals("Bereich zeigen")) {
				mp.deaktiviereAnfasser();
				((JSlider) mp.getInhaltsKomponente()).setPaintTrack((boolean) setterWerte[0]);
				eg.setWert(setterWerte[0]);
			}
			if (eg.getEigenschaftsName().equals("Einrasten")) {
				mp.deaktiviereAnfasser();
				((JSlider) mp.getInhaltsKomponente()).setSnapToTicks((boolean) setterWerte[0]);
				eg.setWert(setterWerte[0]);
			}
			if (eg.getEigenschaftsName().equals("Minimaler Wertabstand")) {
				mp.deaktiviereAnfasser();
				((JSlider) mp.getInhaltsKomponente()).setMinorTickSpacing((int) setterWerte[0]);
				eg.setWert(setterWerte[0]);
			}
			if (eg.getEigenschaftsName().equals("Hauptwertabstand")) {
				mp.deaktiviereAnfasser();
				((JSlider) mp.getInhaltsKomponente()).setMajorTickSpacing((int) setterWerte[0]);
				eg.setWert(setterWerte[0]);
			}
			if (eg.getEigenschaftsName().equals("Minimum")) {
				((JSlider) mp.getInhaltsKomponente()).setMinimum((int) setterWerte[0]);
				eg.setWert(setterWerte[0]);
			}
			if (eg.getEigenschaftsName().equals("Maximum")) {
				((JSlider) mp.getInhaltsKomponente()).setMaximum((int) setterWerte[0]);
				eg.setWert(setterWerte[0]);
			}
			if (eg.getEigenschaftsName().equals("Wert")) {
				((JSlider) mp.getInhaltsKomponente()).setValue((int) setterWerte[0]);
				eg.setWert(setterWerte[0]);

			}
			mp.revalidate();
		}
		return true;
	}

}
