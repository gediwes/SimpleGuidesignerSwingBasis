/**
 * 
 */
package guidesigner.eigenschaften.komponenten;

/**
 * @author Georg Dick
 * 
 */
public class EigenschaftInvertierungFuerJSlider extends
		KomponentenEigenschaft {

	
	public EigenschaftInvertierungFuerJSlider(
			String eigenschaftsName,  Object wert,
			int primaererRang, int sekundaererRang) {
		super(eigenschaftsName, "boolean", "setInverted", "setzeInvertierteSkala",
				"invertiert","boolean", wert, primaererRang,
				sekundaererRang);

	}

	/**
	 * @see guidesigner.eigenschaften.komponenten.KomponentenEigenschaft#getSource(java.lang.String, boolean)
	 */
	@Override
	public String getSource(String vname, boolean istSwing) {
		if (!(boolean) wert){
			return "";
		}
		if (istSwing) {
			
				return "    " + vname
						+ ".setInverted(true);\n";
			 
		} else {
			
				return "    " + vname
						+ ".setzeInvertierteSkala(true);\n";
			
		}
	}

}
