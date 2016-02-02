/**
 * 
 */
package guidesigner.eigenschaften.komponenten;

import java.util.ArrayList;
import java.util.Vector;

/**
 * @author Georg Dick
 * 
 */
public class EigenschaftEintragFuerJComboBox extends KomponentenEigenschaft {

	/**
	 * @param eigenschaftsName
	 * @param eigenschaftsTyp
	 * @param setterName
	 * @param setterParameterName
	 * @param setterParameterTyp
	 * @param wert
	 * @param primaererRang
	 * @param sekundaererRang
	 */

	public EigenschaftEintragFuerJComboBox(	Object wert, int primaererRang, int sekundaererRang) {
		super("EintraegeComboBox", "Vector<String>","setEintraege","","eintraege","Vector<String>", wert, primaererRang, sekundaererRang);
	}
	
	public void setWert(Object wert) {
		this.wert = wert;
	}
	
	
	/**
	 * @see guidesigner.eigenschaften.komponenten.KomponentenEigenschaft#getSource(java.lang.String, boolean)
	 */
	@Override
	public String getSource(String vname, boolean istSwing) {
		String insrc = "{\"\"}";
		if (wert != null) {
			String[] s = new String[((Vector<String>) wert).size()];			
			if (s.length > 0) {
				s = ((Vector<String>) wert).toArray(s);
				insrc = "{\"" + s[0] + "\"";
				for (int i = 1; i < s.length; i++) {
					insrc += ",\"" + s[i] + "\"";
				}
				insrc += "}";
			}
		}
		if (istSwing) {
		return "    "+vname
				+ ".setModel(new DefaultComboBoxModel(new String[]" + insrc
				+ "));\n";
		} else {
			return "    " + vname
					+ ".setzeInhalte(new String[]" + insrc+ ");\n";
		}
	}
	
	

}
