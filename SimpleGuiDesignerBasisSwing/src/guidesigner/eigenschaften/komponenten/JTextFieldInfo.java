package guidesigner.eigenschaften.komponenten;

import javax.swing.JTextField;

import guidesigner.eigenschaften.GrundEigenschaft;
import guidesigner.eigenschaften.ereignisse.LauscherEigenschaft;
import guidesigner.eigenschaften.ereignisse.basislauscher.LBTextFeldLauscherInfo;
import guidesigner.eigenschaften.ereignisse.basisschleife.EreignisBearbeiteMethodenEigenschaft;
import guidesigner.eigenschaften.ereignisse.swinglistener.LActionListenerInfo;
import guidesigner.eigenschaften.ereignisse.swinglistener.LCaretListenerInfo;
import guidesigner.eigenschaften.ereignisse.swinglistener.LInputMethodListenerInfo;
import guidesigner.oberflaeche.MovePanel;

public class JTextFieldInfo extends KomponentenInfo {
	public JTextFieldInfo(MovePanel mp, String komponententyp, String vname) {
		super(mp, komponententyp, vname);
		String text = ((JTextField) mp.getInhaltsKomponente()).getText();
		eigenschaften.add(new EigenschaftText( text, 2, 1));
		eigenschaften.add(new KomponentenEigenschaft("Editierbar", "boolean", "setEditable",
				"setzeEditierbar", "editierbar", "boolean", true, 9, 3, true));
//		Localisation.swingEnglish.put("Text", "Text");
		eigenschaften.add(new LauscherEigenschaft(new LCaretListenerInfo(),
				true, 10, 1));
		eigenschaften.add(new LauscherEigenschaft(
				new LInputMethodListenerInfo(), true, 10, 2));
		eigenschaften.add(new LauscherEigenschaft(new LActionListenerInfo(),
				true, 10, 3));
		eigenschaften.add(new LauscherEigenschaft(new LBTextFeldLauscherInfo(),
				true, 10, 1));
		eigenschaften.add(new EreignisBearbeiteMethodenEigenschaft("TextFeld", "returnWurdeGedrueckt", false, 12, 1));
		eigenschaften.add(new EreignisBearbeiteMethodenEigenschaft("TextFeld", "textWurdeGeaendert", false, 12, 1));
		eigenschaften.add(new EreignisBearbeiteMethodenEigenschaft("TextFeld", "fokusVerloren", false, 12, 1));
		
	}

	@Override
	public boolean aendereEigenschaft(KomponentenEigenschaft eg, Object[] setterWerte) {
		super.aendereEigenschaft(eg, setterWerte);
		if (getPanel() instanceof MovePanel) {
			MovePanel mp = (MovePanel) getPanel();
			if (eg.getEigenschaftsName().equals("Editierbar")) {
				eg.setWert((boolean)setterWerte[0]);
			}
			if (eg.getEigenschaftsName().equals("Text")) {
				mp.deaktiviereAnfasser();
				((JTextField) mp.getInhaltsKomponente())
						.setText((String) setterWerte[0]);
				eg.setWert(setterWerte[0]);
			}

			mp.revalidate();

		}
		return true;
	}

}
