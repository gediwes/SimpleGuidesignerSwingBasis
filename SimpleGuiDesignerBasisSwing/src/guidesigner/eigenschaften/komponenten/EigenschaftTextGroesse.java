/**
 * 
 */
package guidesigner.eigenschaften.komponenten;

/**
 * @author Georg Dick
 * 
 */
public class EigenschaftTextGroesse extends
		KomponentenEigenschaft {

	private int standardgroesse;
	
	/**
	 * @return the standardgroesse
	 */
	public int getStandardgroesse() {
		return standardgroesse;
	}

	/**
	 * @param standardgroesse the standardgroesse to set
	 */
	public void setStandardgroesse(int standardgroesse) {
		this.standardgroesse = standardgroesse;
	}

	public EigenschaftTextGroesse(String eigenschaftsname, Object standardwert, int primaererRang, int sekundaererRang) {
		super(eigenschaftsname, "int", "setFont", "setzeSchriftGroesse",
				"groesse", "int", standardwert, primaererRang,
				sekundaererRang);
         this.standardgroesse=(int)standardwert;
	}

	/**
	 * @see guidesigner.eigenschaften.komponenten.KomponentenEigenschaft#getSource(java.lang.String, boolean)
	 */
	@Override
	public String getSource(String vname, boolean istSwing) {
		if ((int)wert == standardgroesse){
			return "";
		}
		if (istSwing) {
			String src = "    String name" + vname+ " = " + vname+ ".getFont().getName();\n";
			src+=        "    int stil" + vname+ " = " + vname+ ".getFont().getStyle();\n";
			src+=        "    " + vname+ ".setFont(new Font(name" + vname+ ", stil" + vname+ ", "+(int)wert+"));\n";			
				return src;
				
			
		} else {			
				return "    " + vname
						+ ".setzeSchriftGroesse("+(int)wert+");\n";			
		}
	}

}
