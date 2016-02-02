/**
 * 
 */
package guidesigner.eigenschaften.komponenten;

/**
 * @author Georg Dick
 * 
 */
public class EigenschaftOrientierungFuerJSliderUndJScrollBar extends
		KomponentenEigenschaft {

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
	public EigenschaftOrientierungFuerJSliderUndJScrollBar(
			String eigenschaftsName, String eigenschaftsTyp, String setterName,
			String setterParameterName, String setterParameterTyp, Object wert,
			int primaererRang, int sekundaererRang) {
		super(eigenschaftsName, eigenschaftsTyp, setterName, "",
				setterParameterName, setterParameterTyp, wert, primaererRang,
				sekundaererRang);

	}

	/**
	 * @see guidesigner.eigenschaften.komponenten.KomponentenEigenschaft#getSource(java.lang.String, boolean)
	 */
	@Override
	public String getSource(String vname, boolean istSwing) {
		if (istSwing) {
			if ((boolean) wert) {
				return "    " + vname
						+ ".setOrientation(JScrollBar.HORIZONTAL);\n";
			} else {
				return "    " + vname
						+ ".setOrientation(JScrollBar.VERTICAL);\n";
			}
		} 
		return "";
	}

}
