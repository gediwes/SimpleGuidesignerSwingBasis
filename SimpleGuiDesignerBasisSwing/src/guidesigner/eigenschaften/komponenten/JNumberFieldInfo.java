package guidesigner.eigenschaften.komponenten;

import java.lang.reflect.Method;

import basisx.swing.JNumberField;
import guidesigner.eigenschaften.ereignisse.basisschleife.EreignisBearbeiteMethodenEigenschaft;
import guidesigner.oberflaeche.MovePanel;

public class JNumberFieldInfo extends KomponentenInfo {

	public JNumberFieldInfo(MovePanel mp, String komponententyp, String vname) {
		super(mp, komponententyp, vname);
		double d = ((JNumberField) mp.getInhaltsKomponente()).getDouble();
		int i = ((JNumberField) mp.getInhaltsKomponente()).getInteger();
		// eigenschaften.add(new Eigenschaft(
		// "Ganzzahl", "int", "setNumber", "zahl", "int", "getInteger",i, 2,
		// 1));
		eigenschaften.add(new KomponentenEigenschaft("Editierbar", "boolean", "setEditable", "setzeEditierbar",
				"editierbar", "boolean", true, 9, 3, true));
		eigenschaften.add(new KomponentenEigenschaft("Dezimalzahl", "double", "setNumber", "setzeZahl", "zahl",
				"double", d, 2, 1));
		eigenschaften.add(new EreignisBearbeiteMethodenEigenschaft("ZahlenFeld", "returnWurdeGedrueckt", false, 12, 1));
		eigenschaften.add(new EreignisBearbeiteMethodenEigenschaft("ZahlenFeld", "textWurdeGeaendert", false, 12, 1));
		eigenschaften.add(new EreignisBearbeiteMethodenEigenschaft("ZahlenFeld", "fokusVerloren", false, 12, 1));

		// lauscher.add(new LauscherInfo("java.awt.event.ActionListener"));
	}

	@Override
	public boolean aendereEigenschaft(KomponentenEigenschaft eg, Object[] setterWerte) {
		super.aendereEigenschaft(eg, setterWerte);
		if (getPanel() instanceof MovePanel) {
			MovePanel mp = (MovePanel) getPanel();
			if (eg.getEigenschaftsName().equals("Editierbar")) {
				eg.setWert((boolean) setterWerte[0]);
			}
			if (eg.getEigenschaftsName().equals("Dezimalzahl")) {
				mp.deaktiviereAnfasser();
				Class<?> parameterType = String.class;
				if (eg.getEigenschaftsTyp().equals("int")) {
					parameterType = int.class;
				} else if (eg.getEigenschaftsTyp().equals("double")) {
					parameterType = double.class;
				} else if (eg.getEigenschaftsTyp().equals("boolean")) {
					parameterType = boolean.class;
				}

				Object parameter = setterWerte[0];
				try {
					Method setterMethod = mp.getInhaltsKomponente().getClass().getMethod(eg.getSetterNameSwing(),
							parameterType);
					setterMethod.invoke(mp.getInhaltsKomponente(), parameter);
					eg.setWert(setterWerte[0]);
				} catch (Exception e) {
					e.printStackTrace();
				}
				mp.revalidate();
			}
		}

		return true;
	}

}
