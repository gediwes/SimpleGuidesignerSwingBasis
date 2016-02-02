package guidesigner.eigenschaften.komponenten;

import javax.swing.JTextArea;

import guidesigner.eigenschaften.GrundEigenschaft;
import guidesigner.eigenschaften.ereignisse.LauscherEigenschaft;
import guidesigner.eigenschaften.ereignisse.basislauscher.LBTextBereichLauscherInfo;
import guidesigner.eigenschaften.ereignisse.swinglistener.LCaretListenerInfo;
import guidesigner.oberflaeche.MovePanel;



public class JTextAreaInfo extends KomponentenInfo {
	public JTextAreaInfo(MovePanel mp, String komponententyp, String vname) {
		super(mp, komponententyp, vname);
		String text = ((JTextArea) mp.getInhaltsKomponente()).getText();
		eigenschaften.add(new EigenschaftText(text, 2, 1));
		eigenschaften.add(new KomponentenEigenschaft("Editierbar", "boolean", "setEditable",
				"setzeEditierbar", "editierbar", "boolean", true, 9, 3,true));
//		Localisation.swingEnglish.put("Text","Text");		
		eigenschaften.add(new KomponentenEigenschaft(
				"Scrollbar", "boolean", "", "", "", "boolean",true, 6, 1));
		eigenschaften.add(new LauscherEigenschaft(new LCaretListenerInfo(),true,12,1));
//		Localisation.swingEnglish.put("Scrollbar","Scrollable");
		eigenschaften.add(new LauscherEigenschaft(new LBTextBereichLauscherInfo(),true,12,1));
	}
	@Override
	public boolean aendereEigenschaft(KomponentenEigenschaft eg, Object[] setterWerte) {
		super.aendereEigenschaft(eg, setterWerte);
		if (getPanel() instanceof MovePanel) {
			MovePanel mp = (MovePanel) getPanel();
			if (eg.getEigenschaftsName().equals("Editierbar")) {
				eg.setWert((boolean)setterWerte[0]);
			}
			if (eg.getEigenschaftsName().equals("Scrollbar")) {
				eg.setWert(setterWerte[0]);
			}
			if (eg.getEigenschaftsName().equals("Text")) {
				mp.deaktiviereAnfasser();
				((JTextArea) mp.getInhaltsKomponente())
						.setText((String) setterWerte[0]);
				eg.setWert(setterWerte[0]);
			}	
//				Class<?> parameterType = String.class;
//				if (eg.getEigenschaftsTyp().equals("int")) {
//					parameterType = int.class;
//				} else if (eg.getEigenschaftsTyp().equals("double")) {
//					parameterType = double.class;
//				} else if (eg.getEigenschaftsTyp().equals("boolean")) {
//					parameterType = boolean.class;
//				}
//				Object parameter = setterWerte[0];
//				try {
//					Method setterMethod = mp.getInhaltsKomponente().getClass()
//							.getMethod(eg.getSetterName(), parameterType);
//					setterMethod.invoke(mp.getInhaltsKomponente(), parameter);
//					eg.setWert(setterWerte[0]);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}

				mp.revalidate();
			
		}
		return true;
	}

		
}
