package guidesigner.control;

import java.util.Vector;

import guidesigner.eigenschaften.GrundEigenschaft;


public class KomponentenInfoGrundeigenschaften {
	Vector<GrundEigenschaft> eigenschaften = new Vector<GrundEigenschaft>();
	Vector<KomponentenInfoGrundeigenschaften> eingebetteteKomponenten = new Vector<KomponentenInfoGrundeigenschaften>();
    String komponentenTyp;
	String variablenName;
	private String vUebergeordnet;
	/**
	 * @return liefert eingebetteteKomponenten
	 */
	public Vector<KomponentenInfoGrundeigenschaften> getEingebetteteKomponenten() {
		return eingebetteteKomponenten;
	}

	public void addEingebetteteKomponente(KomponentenInfoGrundeigenschaften ki) {
		if (!eingebetteteKomponenten.contains(ki)) {
			eingebetteteKomponenten.add(ki);
		}
	}


	public KomponentenInfoGrundeigenschaften(String komponententyp, String vname, String vUebergeordnet) {
		
		komponentenTyp = komponententyp;
		this.variablenName = vname;
		this.vUebergeordnet = vUebergeordnet;
		
	}

	/**
	 * 
	 */
	public KomponentenInfoGrundeigenschaften() {
		// TODO Auto-generated constructor stub
	}

	
	
	/**
	 * @return liefert eigenschaften
	 */
	public Vector<GrundEigenschaft> getEigenschaften() {
		return eigenschaften;
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

	public void addEigenschaft(GrundEigenschaft eigenschaft) {
		for (GrundEigenschaft e : eigenschaften) {
			if (e.getEigenschaftsName().equals(
					eigenschaft.getEigenschaftsName())) {
				return;
			}
		}
		eigenschaften.add(eigenschaft);
	}

//	public GrundEigenschaft updateEigenschaftsWert(GrundEigenschaft eigenschaft,
//			Object neuWert) {
//		for (GrundEigenschaft e : eigenschaften) {
//			if (e.getEigenschaftsName().equals(
//					eigenschaft.getEigenschaftsName())) {
//				e.setWert(neuWert);
//				return e;
//			}
//		}
//		return null;
//	}

	/**
	 * @param liesText
	 */
	public void setVariablenNameUebergeordnet(String vnameu) {
		this.vUebergeordnet=vnameu;
		
	}


}
