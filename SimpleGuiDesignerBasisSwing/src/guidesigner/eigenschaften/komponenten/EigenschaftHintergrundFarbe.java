/**
 * 
 */
package guidesigner.eigenschaften.komponenten;

import java.awt.Color;

/**
 * @author Georg Dick
 *
 */
public class EigenschaftHintergrundFarbe extends KomponentenEigenschaft {
	Color startWert;
	
	/**
	 * @param eigenschaftsName
	 * @param eigenschaftsTyp
	 * @param setterNameSwing
	 * @param setterNameBasis
	 * @param setterParameterName
	 * @param setterParameterTyp
	 * @param wert
	 * @param primaererRang
	 * @param sekundaererRang
	 * @param startWert
	 */
	public EigenschaftHintergrundFarbe(String eigenschaftsName,
			String eigenschaftsTyp, String setterNameSwing,
			String setterNameBasis, String setterParameterName,
			String setterParameterTyp, Object wert, int primaererRang,
			int sekundaererRang, Color startWert) {
		super(eigenschaftsName, eigenschaftsTyp, setterNameSwing,
				setterNameBasis, setterParameterName, setterParameterTyp, wert,
				primaererRang, sekundaererRang);
		this.startWert=startWert;
	}
	
	/**
	 * @see guidesigner.eigenschaften.komponenten.KomponentenEigenschaft#getSource(java.lang.String, boolean)
	 */
	@Override
	public String getSource(String vname, boolean istSwing) {
		if (startWert.equals(getWert())) {
			return "";
		}
		return super.getSource(vname, istSwing);
	}

	public boolean hintergrundFarbeGeaendert() {
		return !this.startWert.equals(wert);
	}
	
}
