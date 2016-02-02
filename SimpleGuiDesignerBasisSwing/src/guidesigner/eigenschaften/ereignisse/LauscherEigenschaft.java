package guidesigner.eigenschaften.ereignisse;

import guidesigner.eigenschaften.GrundEigenschaft;

public class LauscherEigenschaft extends GrundEigenschaft {
	
	private LauscherInfo lauscherinfo;

	public LauscherInfo getLauscherinfo() {
		return lauscherinfo;
	}

	/**
	 * @param lauscher
	 * @param wert
	 * @param primaererRang
	 * @param sekundaererRang
	 */
	public LauscherEigenschaft(LauscherInfo lauscher, Object wert,
			int primaererRang, int sekundaererRang) {
		super(lauscher.lauscherName,  wert, primaererRang, sekundaererRang);
		this.lauscherinfo = lauscher;
		this.setKompatibleGUI(lauscher.getKompatibleGui());
	}

	

	public String getSourceAddLauscher(
			String variablenName, boolean istSwing) {
		if (istSwing) {
		return "    "+variablenName+".add"+getEigenschaftsName()+"(this);\n";}
		return "    "+variablenName+".setze"+getEigenschaftsName()+"(this);\n";
	}

	
   


	

}
