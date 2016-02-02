/**
 * 
 */
package guidesigner.control;

/**
 * @author Georg Dick
 *
 */
public class KlassennameUndOberklasse {
	String klassenname;
	boolean fensteralsoberklasse;
	/**
	 * @param klassenname
	 * @param fensteralsoberklasse
	 */
	public KlassennameUndOberklasse(String klassenname, boolean fensteralsoberklasse) {
		super();
		this.klassenname = klassenname;
		this.fensteralsoberklasse = fensteralsoberklasse;
	}
	/**
	 * @return liefert klassenname
	 */
	public String getKlassenname() {
		return klassenname;
	}
	/**
	 * @return liefert fensteralsoberklasse
	 */
	public boolean isFensteralsoberklasse() {
		return fensteralsoberklasse;
	}
	
	

}
