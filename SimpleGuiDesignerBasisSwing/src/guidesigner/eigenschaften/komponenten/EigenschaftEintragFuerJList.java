/**
 * 
 */
package guidesigner.eigenschaften.komponenten;

import java.util.Vector;

import javax.swing.AbstractListModel;

/**
 * @author Georg Dick
 * 
 */
public class EigenschaftEintragFuerJList extends KomponentenEigenschaft {
	

	/**
	 * 
	 * @param wert
	 * @param primaererRang
	 * @param sekundaererRang
	 */
	public EigenschaftEintragFuerJList(	Object wert, int primaererRang, int sekundaererRang) {
		super("EintraegeJList", "Vector<String>","setEintraege","","eintraege","Vector<String>", wert, primaererRang, sekundaererRang);
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
			return "    " + vname + ".setModel(new AbstractListModel(){\n"
				  + "      String[] values = new String[]" + insrc	+ ";\n"
				  + "      public int getSize() {\n"+ 
					"        return values.length;\n"
				  + "      }\n"
				  + "      public Object getElementAt(int index) {\n"
				  + "        return values[index];\n"
				  + "      }\n"
				  + "	});\n";
		} else {
			return "    " + vname + ".setzeInhalte(new String[]" + insrc + ");\n";
		}
	}
	

}
