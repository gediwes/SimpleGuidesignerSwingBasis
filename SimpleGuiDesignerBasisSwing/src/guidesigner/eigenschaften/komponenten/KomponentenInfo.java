/**
 * 
 */
package guidesigner.eigenschaften.komponenten;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import guidesigner.control.ZuordungBasisSwing;
import guidesigner.eigenschaften.ereignisse.LauscherEigenschaft;
import guidesigner.eigenschaften.ereignisse.basislauscher.LBKomponentenLauscherInfo;
import guidesigner.eigenschaften.ereignisse.basislauscher.LBMausLauscherErweitertLauscherInfo;
import guidesigner.eigenschaften.ereignisse.basislauscher.LBMausLauscherStandardLauscherInfo;
import guidesigner.eigenschaften.ereignisse.basislauscher.LBTastenLauscherInfo;
import guidesigner.eigenschaften.ereignisse.basisschleife.EreignisBearbeiteMethodenEigenschaft;
import guidesigner.eigenschaften.ereignisse.swinglistener.LComponentListenerInfo;
import guidesigner.eigenschaften.ereignisse.swinglistener.LKeyListenerInfo;
import guidesigner.eigenschaften.ereignisse.swinglistener.LMouseListenerInfo;
import guidesigner.eigenschaften.ereignisse.swinglistener.LMouseMotionListenerInfo;
import guidesigner.oberflaeche.MovePanel;

/**
 * @author Georg Dick
 *
 */
public class KomponentenInfo extends KomponentenGrundInfo {

	/**
	 * @param mp
	 * @param komponententyp
	 * @param vname
	 */
	public KomponentenInfo(JPanel mp, String komponententyp, String vname) {
		super(mp, komponententyp, vname);

//		String[] bh = { "breite", "hoehe" };
//		String[] intint = { "int", "int" };
		Object[] spw = new Object[2];
		spw[0] = mp.getSize().width;
		spw[1] = mp.getSize().height;
//		eigenschaften.add(new KomponentenEigenschaft("Größe", "java.awt.Dimension", "setSize", "setzeGroesse", bh,
//				intint, spw, 5, 2));
		eigenschaften.add(new EigenschaftGroesse( spw, 5, 2));
		if (mp instanceof MovePanel) {
			eigenschaften.add(new EigenschaftHintergrundFarbe("Hintergrundfarbe", "java.awt.Color", "setBackground",
					"setzeHintergrundFarbe", "farbe", "java.awt.Color",
					((MovePanel) mp).getAnzeigepanel().getComponent(0).getBackground(), 8, 1,
					((MovePanel) mp).getAnzeigepanel().getComponent(0).getBackground()));

			eigenschaften.add(new EigenschaftTextGroesse("Schriftgroesse",
					((MovePanel) mp).getInhaltsKomponente().getFont().getSize(), 11, 1));
			eigenschaften.add(new KomponentenEigenschaft("Benutzbar", "boolean", "setEnabled", "setzeBenutzbar",
					"benutzbar", "boolean", true, 9, 1, true));
			eigenschaften.add(new EigenschaftRand(11, 5));

		} else {
			eigenschaften.add(new EigenschaftHintergrundFarbe("Hintergrundfarbe", "java.awt.Color", "setBackground",
					"setzeHintergrundFarbe", "farbe", "java.awt.Color", mp.getBackground(), 8, 1, mp.getBackground()));
		}
		eigenschaften.add(new KomponentenEigenschaft("Sichtbar", "boolean", "setVisible", "setzeSichtbar", "sichtbar",
				"boolean", true, 9, 2, true));

		eigenschaften.add(new LauscherEigenschaft(new LComponentListenerInfo(), false, 12, 1));
		eigenschaften.add(new LauscherEigenschaft(new LMouseListenerInfo(), false, 12, 2));
		eigenschaften.add(new LauscherEigenschaft(new LMouseMotionListenerInfo(), false, 12, 3));
		eigenschaften.add(new LauscherEigenschaft(new LKeyListenerInfo(), false, 12, 4));
		eigenschaften.add(new LauscherEigenschaft(new LBKomponentenLauscherInfo(), false, 12, 1));
		eigenschaften.add(new LauscherEigenschaft(new LBMausLauscherStandardLauscherInfo(), false, 12, 2));
		eigenschaften.add(new LauscherEigenschaft(new LBMausLauscherErweitertLauscherInfo(), false, 12, 3));
		eigenschaften.add(new LauscherEigenschaft(new LBTastenLauscherInfo(), false, 12, 4));
	}

	@Override
	public boolean aendereEigenschaft(KomponentenEigenschaft eg, Object[] setterWerte) {
		JPanel jp = getPanel();
		// if (eig instanceof LauscherEigenschaft
		// || eig instanceof A_BearbeiteMethodenEigenschaft){
		// eig.setWert((boolean)setterWerte[0]);
		// //System.out.println("LE BM"+(boolean)setterWerte[0]);
		// return true;
		// }
		// if (eig instanceof KomponentenEigenschaft){
		// KomponentenEigenschaft eg =(KomponentenEigenschaft)eig;
		if (eg.getEigenschaftsName().equals("Sichtbar")) {
			eg.setWert((boolean) setterWerte[0]);
		}
		if (eg.getEigenschaftsName().equals("Benutzbar")) {
			eg.setWert((boolean) setterWerte[0]);
		}
		if (!(jp instanceof MovePanel)) {
			// Fenster
			if (eg.getEigenschaftsName().equals("Größe")) {
				if ((int) setterWerte[0]<5000 && (int) setterWerte[1] <5000){
				 jp.setSize(new Dimension((int) setterWerte[0], (int) setterWerte[1]));
				}
				eg.setWert(new Object[]{(int) setterWerte[0], (int) setterWerte[1]});
			}
			if (eg.getEigenschaftsName().equals("Position")) {
				eg.setWert(new Object[]{(int) setterWerte[0], (int) setterWerte[1]});
			}
			if (eg.getEigenschaftsName().equals("Hintergrundfarbe")) {
				jp.setBackground((Color) setterWerte[0]);
				eg.setWert((Color) setterWerte[0]);
			}
			jp.revalidate();
			return true;
		}
		MovePanel mp = (MovePanel) jp;
		if (eg.getEigenschaftsName().equals("Größe")) {
			mp.deaktiviereAnfasser();
			mp.setSize(new Dimension((int) setterWerte[0], (int) setterWerte[1]));
			eg.setWert(new Object[]{(int) setterWerte[0], (int) setterWerte[1]});

		}
		if (eg.getEigenschaftsName().equals("Position")) {
			mp.deaktiviereAnfasser();
			mp.setLocation(new Point((int) setterWerte[0], (int) setterWerte[1]));
			eg.setWert(new Object[]{(int) setterWerte[0], (int) setterWerte[1]});

		}
		if (eg.getEigenschaftsName().equals("Hintergrundfarbe")) {
			mp.deaktiviereAnfasser();
			mp.getInhaltsKomponente().setBackground((Color) setterWerte[0]);
			eg.setWert((Color) setterWerte[0]);
		}
		if (eg.getEigenschaftsName().equals("Schriftgroesse")) {
			mp.deaktiviereAnfasser();
			Container meineKomponente = ((Container) mp.getInhaltsKomponente());
			String name = meineKomponente.getFont().getName();
			int stil = meineKomponente.getFont().getStyle();
			meineKomponente.setFont(new Font(name, stil, (int) setterWerte[0]));
			eg.setWert(setterWerte[0]);
		}
		if (eg.getEigenschaftsName().equals("Rand")) {
			
			int rbreite = (int) setterWerte[1];
			if (rbreite > 0) {
				mp.deaktiviereAnfasser();
				Container meineKomponente = ((Container) mp.getInhaltsKomponente());
				if (meineKomponente instanceof JComponent) {
					((JComponent) meineKomponente).setBorder(new LineBorder((Color) setterWerte[0], rbreite));
				}
				eg.setWert(new Object[] {setterWerte[0],setterWerte[1]});
			} else {
				mp.deaktiviereAnfasser();
				Container meineKomponente = ((Container) mp.getInhaltsKomponente());
				if (meineKomponente instanceof JComponent) {
					((JComponent) meineKomponente).setBorder(null);
				}
				eg.setWert(new Object[] {setterWerte[0],setterWerte[1]});
			}

			
		}
		mp.revalidate();
		return true;
		// }
		// return false;
	}

}
