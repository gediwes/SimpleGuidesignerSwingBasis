/**
 * 
 */
package guidesigner.eigenschaften.komponenten;

/**
 * @author Georg Dick
 * 
 */
public class EigenschaftText extends
		KomponentenEigenschaft {

	private String leer ="";
	
	


	public EigenschaftText(Object wert, int primaererRang, int sekundaererRang) {		
		super("Text", "String", "setText", "setzeText", "text", "String",wert,primaererRang, sekundaererRang);        
	}

	/**
	 * @see guidesigner.eigenschaften.komponenten.KomponentenEigenschaft#getSource(java.lang.String, boolean)
	 */
	@Override
	public String getSource(String vname, boolean istSwing) {
		if (wert==null || ((String)wert).equals(leer)){		
			return "";
		}
		if (istSwing) {
			return "    " + vname
					+ ".setText(\"" + ((String)wert) + "\");\n";		
				
			
		} else {			
				return "    " + vname
						+ ".setzeText(\"" + ((String)wert) + "\");\n";		
		}
	}
	
}
