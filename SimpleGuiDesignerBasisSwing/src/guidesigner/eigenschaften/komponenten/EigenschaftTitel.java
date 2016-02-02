/**
 * 
 */
package guidesigner.eigenschaften.komponenten;

/**
 * @author Georg Dick
 * 
 */
public class EigenschaftTitel extends
		KomponentenEigenschaft {

	private String leer ="";
	
	


	public EigenschaftTitel(Object wert, int primaererRang, int sekundaererRang) {		
		super("Titel", "String", "setTitle", "setzeTitel", "text", "String",wert,primaererRang, sekundaererRang);        
	}

	
	@Override
	public String getSource(String vname, boolean istSwing) {
		
			return "";
		
	}
	
}
