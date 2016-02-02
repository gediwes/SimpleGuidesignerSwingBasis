package guidesigner.eigenschaften;

import guidesigner.control.Controller;

public class GrundEigenschaft {
	protected int primaererRang = 100;
	protected int sekundaererRang = 100;
	protected Object wert;
	protected String eigenschaftsName;
	protected String eigenschaftsTyp;
	
	public GrundEigenschaft(String eigenschaftsName,Object wert,int primaererRang, int sekundaererRang, String eigenschaftsTyp ) {		
		this.wert = wert;
		this.primaererRang = primaererRang;
		this.sekundaererRang = sekundaererRang;		
		this.eigenschaftsName = eigenschaftsName;
		this.eigenschaftsTyp=eigenschaftsTyp;
	}
	public GrundEigenschaft(String eigenschaftsName,Object wert,int primaererRang, int sekundaererRang ) {		
		this.wert = wert;
		this.primaererRang = primaererRang;
		this.sekundaererRang = sekundaererRang;		
		this.eigenschaftsName = eigenschaftsName;
		this.eigenschaftsTyp="boolean";
	}
	/**
	 * @return the eigenschaftsName
	 */
	public String getEigenschaftsName() {
		return eigenschaftsName;
	}

	/**
	 * @param eigenschaftsName the eigenschaftsName to set
	 */
	public void setEigenschaftsName(String eigenschaftsName) {
		this.eigenschaftsName = eigenschaftsName;
	}

	protected int kompatibleGUI=Controller.BASISGUILAUSCHER+Controller.BASISGUISCHLEIFE+
			Controller.SWINGGUI;
	/**
	 * @return the kompatibleGUI
	 */
	public int getKompatibleGUI() {
		return kompatibleGUI;
	}

	/**
	 * @param kompatibleGUI the kompatibleGUI to set
	 */
	public void setKompatibleGUI(int kompatibleGUI) {
		this.kompatibleGUI = kompatibleGUI;
	}


	
	/**
	 * @return liefert eigenschaftsTyp
	 */
	public String getEigenschaftsTyp() {
		return eigenschaftsTyp;
	}

	/**
	 * @param eigenschaftsTyp
	 *            setzt eigenschaftsTyp
	 */
	public void setEigenschaftsTyp(String eigenschaftsTyp) {
		this.eigenschaftsTyp = eigenschaftsTyp;
	}


	/**
	 * @return the primaererRang
	 */
	public int getPrimaererRang() {
		return primaererRang;
	}



	/**
	 * @param primaererRang the primaererRang to set
	 */
	public void setPrimaererRang(int primaererRang) {
		this.primaererRang = primaererRang;
	}



	/**
	 * @return the sekundaererRang
	 */
	public int getSekundaererRang() {
		return sekundaererRang;
	}



	/**
	 * @param sekundaererRang the sekundaererRang to set
	 */
	public void setSekundaererRang(int sekundaererRang) {
		this.sekundaererRang = sekundaererRang;
	}



	/**
	 * @return the wert
	 */
	public Object getWert() {
		return wert;
	}



	/**
	 * @param wert the wert to set
	 */
	public void setWert(Object wert) {
		this.wert = wert;
	}




	public String getSource(String vname, boolean istSwing) {
			return "";
		}

}