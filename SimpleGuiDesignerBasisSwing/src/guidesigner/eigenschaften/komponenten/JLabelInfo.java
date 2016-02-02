package guidesigner.eigenschaften.komponenten;

import java.lang.reflect.Method;

import javax.swing.JLabel;

import guidesigner.eigenschaften.GrundEigenschaft;
import guidesigner.oberflaeche.MovePanel;



public class JLabelInfo extends KomponentenInfo{
	public JLabelInfo(MovePanel mp, String komponententyp, String vname) {
		super(mp, komponententyp, vname);
		String text = ((JLabel) mp.getInhaltsKomponente()).getText();
	
		eigenschaften.add(new EigenschaftBeschriftung(text, 2, 1));

	}
	@Override
	public boolean aendereEigenschaft(KomponentenEigenschaft eg, Object[] setterWerte) {
		super.aendereEigenschaft(eg, setterWerte);
		if (getPanel() instanceof MovePanel) {
			MovePanel mp = (MovePanel) getPanel();
			if (eg.getEigenschaftsName().equals("Beschriftung")) {
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
					Method setterMethod = mp.getInhaltsKomponente().getClass()
							.getMethod(eg.getSetterNameSwing(), parameterType);
					setterMethod.invoke(mp.getInhaltsKomponente(), parameter);
					eg.setWert(setterWerte[0]);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mp.revalidate();
		}
		return true;
	}
	

}
