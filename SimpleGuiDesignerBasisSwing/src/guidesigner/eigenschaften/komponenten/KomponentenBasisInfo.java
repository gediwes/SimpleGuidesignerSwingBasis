package guidesigner.eigenschaften.komponenten;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JPanel;

import guidesigner.control.Controller;
import guidesigner.eigenschaften.GrundEigenschaft;
import guidesigner.eigenschaften.ereignisse.basisschleife.EreignisBearbeiteMethodenEigenschaft;
import guidesigner.oberflaeche.MovePanel;

public class KomponentenBasisInfo {
	protected Vector<GrundEigenschaft> eigenschaften = new Vector<GrundEigenschaft>();
	
	
	/**
	 * @return the eigenschaften
	 */
	public Vector<GrundEigenschaft> getEigenschaften() {
		return eigenschaften;
	}

	public void addEigenschaft(GrundEigenschaft ge){
		
			for (GrundEigenschaft e : eigenschaften) {
				if (e.getEigenschaftsName().equals(
						ge.getEigenschaftsName())) {
					return;
				}
			}
			eigenschaften.add((KomponentenEigenschaft)ge);
			return;
		
	
	}
	
	Vector<KomponentenBasisInfo> eingebetteteKomponenten = new Vector<KomponentenBasisInfo>();
	String komponentenTyp;
	String variablenName;
	

	private JPanel movePanel;

	/**
	 * @return liefert eingebetteteKomponenten
	 */
	public Vector<KomponentenBasisInfo> getEingebetteteKomponenten() {
		return eingebetteteKomponenten;
	}

	public void addEingebetteteKomponente(KomponentenBasisInfo ki) {
		if (!eingebetteteKomponenten.contains(ki)) {
			eingebetteteKomponenten.add(ki);
		}
	}

	public void removeEingebetteteKomponente(KomponentenBasisInfo ki) {
		if (eingebetteteKomponenten.contains(ki)) {
			eingebetteteKomponenten.remove(ki);
		}

	}

	/**
	 * @return liefert movePanel
	 */
	public JPanel getPanel() {
		return movePanel;
	}

	/**
	 * @param movePanel
	 *            setzt movePanel
	 */
	public void setPanel(JPanel movePanel) {
		this.movePanel = movePanel;
	}

	public KomponentenBasisInfo(JPanel mp, String komponententyp, String vname) {
		movePanel = mp;
		komponentenTyp = komponententyp;
		this.variablenName = vname;
	}

	

	

	/**
	 * @return liefert komponentenTyp
	 */
	public String getKomponentenTyp() {
		return komponentenTyp;
	}

	/**
	 * @param komponentenTyp
	 *            setzt komponentenTyp
	 */
	public void setKomponentenTyp(String komponentenTyp) {
		this.komponentenTyp = komponentenTyp;
	}

	/**
	 * @return liefert variablenName
	 */
	public String getVariablenName() {
		return variablenName;
	}

	/**
	 * @param variablenName
	 *            setzt variablenName
	 */
	public void setVariablenName(String variablenName) {
		this.variablenName = variablenName;
	}

	public GrundEigenschaft getEigenschaft(String eigenschaftsname) {
		for (GrundEigenschaft e : eigenschaften) {
			if (e.getEigenschaftsName().equals(eigenschaftsname)) {
				return e;
			}
		}
		return null;
	}



	/**
	 * 
	 * @param eigenschaft
	 *            vorgabeeigenschaft, Achtung es werden Referenzen auf die neuen
	 *            Werte vermitteln, es erfolgt keine Kopie	
	 */
	public void updateEigenschaft(GrundEigenschaft eigenschaft) {
		for (GrundEigenschaft e : eigenschaften) {
			if (e.getEigenschaftsName().equals(
					eigenschaft.getEigenschaftsName())) {
				e.setWert(eigenschaft.getWert());
				// e.setSetterParameterwerte(eigenschaft.getSetterParameterwerte());
			}
		}		
	}
	
/** zentrale Methode für Anzeige im Baum */
	public String toString() {
		String tname = komponentenTyp;
		if (komponentenTyp.startsWith("javax.swing.")) {
			tname = komponentenTyp.substring(12);
		} else
		if (komponentenTyp.startsWith("basis.swing.")) {
			tname = komponentenTyp.substring(12);
		} else
		if (komponentenTyp.startsWith("basis.")) {
			tname = komponentenTyp.substring(6);
		}
		return variablenName + " : " + tname;
	}

	public boolean aendereEigenschaft(KomponentenEigenschaft eg, Object[] setterWerte) {
		
		return true;
	}

	/**
	 * @param e
	 * @return
	 */
	public String getSourceVonEigenschaften() {
		String src ="";
		
		for (GrundEigenschaft e: eigenschaften) {
			src+=e.getSource(variablenName, true);
		}
		return "";
	}

	public ArrayList<GrundEigenschaft> getEigenschaftenFuerGui(int guiTyp) {
		ArrayList<GrundEigenschaft> ake = new ArrayList<>();
		for (GrundEigenschaft ag : eigenschaften){
			if ((ag.getKompatibleGUI() & guiTyp) !=0 ){
				ake.add(ag);
			}
		}
		return ake;
	}
	public ArrayList<KomponentenEigenschaft> getKomponentenEigenschaften() {
		ArrayList<KomponentenEigenschaft> ake = new ArrayList<>();
		for (GrundEigenschaft ag : eigenschaften){
			if (ag instanceof KomponentenEigenschaft){
				ake.add((KomponentenEigenschaft)ag);
			}
		}
		return ake;
	}
	

	

}
