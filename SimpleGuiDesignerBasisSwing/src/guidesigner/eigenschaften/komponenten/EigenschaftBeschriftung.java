/**
 * 
 */
package guidesigner.eigenschaften.komponenten;

/**
 * @author Georg Dick
 * 
 */
public class EigenschaftBeschriftung extends
		KomponentenEigenschaft {

	private String leer ="";
	
	


	public EigenschaftBeschriftung(Object wert, int primaererRang, int sekundaererRang) {		
		super("Beschriftung", "String", "setText", "setzeText", "text", "String",wert,primaererRang, sekundaererRang);        
	}


	@Override
	public String getSource(String vname, boolean istSwing) {
		
			return "";
		
	}
	
}
