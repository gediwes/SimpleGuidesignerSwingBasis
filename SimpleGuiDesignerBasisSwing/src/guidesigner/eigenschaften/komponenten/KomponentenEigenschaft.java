package guidesigner.eigenschaften.komponenten;

import java.awt.Color;
import java.util.ArrayList;

import guidesigner.eigenschaften.GrundEigenschaft;

/**
 * Die Klasse Eigenschaft enthält Informationen zu einem Attribut der Komponente
 * Dazu gehören der Name der Eigenschaft, ihr Typ (Klasse oder einfacher
 * Datentyp) und die setzeMethode nebst Parametertyp<br>
 * Ferner kann über die Klasse Eigenschaft die entsprechende Komponente im
 * Designfenster beeinflusst werden. (Verzicht auf MVC) Das geschieht, weil die
 * SwingKomponente im Designfenster und die Swingkomponente zu der die
 * Eigenschaft gehört in der Regel gleich sind. Für Basiskomponenten erfolgen
 * zusätzlich Angaben bezgl der setzeMethode.
 * 
 * 
 * 
 * @author Georg Dick
 *
 */
public class KomponentenEigenschaft extends GrundEigenschaft {
	protected String[] setterParameterTypen;
	protected String[] setterParameterNamen;
	protected String setterNameBasis;
	protected String setterNameSwing;
	private Object standardwert = null;

	/**
	 * 
	 * @param eigenschaftsName
	 * @param eigenschaftsTyp
	 * @param setterNameSwing
	 * @param setterNameBasis
	 * @param setterParameterNamen
	 * @param setterParameterTypen
	 * @param wert
	 * @param primaererRang
	 * @param sekundaererRang
	 */
	public KomponentenEigenschaft(String eigenschaftsName, String eigenschaftsTyp, String setterNameSwing,
			String setterNameBasis, String[] setterParameterNamen, String[] setterParameterTypen, Object wert,
			int primaererRang, int sekundaererRang) {
		super(eigenschaftsName, wert, primaererRang, sekundaererRang, eigenschaftsTyp);

		this.setterNameSwing = setterNameSwing;
		this.setterNameBasis = setterNameBasis;
		this.setterParameterTypen = setterParameterTypen;
		this.setterParameterNamen = setterParameterNamen;
	}

	/**
	 * 
	 * @param eigenschaftsName
	 * @param eigenschaftsTyp
	 * @param setterNameSwing
	 * @param setterNameBasis
	 * @param setterParameterName
	 * @param setterParameterTyp
	 * @param wert
	 * @param primaererRang
	 * @param sekundaererRang
	 */
	public KomponentenEigenschaft(String eigenschaftsName, String eigenschaftsTyp, String setterNameSwing,
			String setterNameBasis, String setterParameterName, String setterParameterTyp, Object wert,
			int primaererRang, int sekundaererRang) {
		super(eigenschaftsName, wert, primaererRang, sekundaererRang, eigenschaftsTyp);
		this.setterNameSwing = setterNameSwing;
		this.setterNameBasis = setterNameBasis;
		this.setterParameterTypen = new String[1];
		this.setterParameterTypen[0] = setterParameterTyp;
		this.setterParameterNamen = new String[1];
		this.setterParameterNamen[0] = setterParameterName;

	}

	/**
	 * 
	 * @param eigenschaftsName
	 * @param eigenschaftsTyp
	 * @param setterNameSwing
	 * @param setterNameBasis
	 * @param setterParameterName
	 * @param setterParameterTyp
	 * @param wert
	 * @param primaererRang
	 * @param sekundaererRang
	 * @param standardwert
	 */
	public KomponentenEigenschaft(String eigenschaftsName, String eigenschaftsTyp, String setterNameSwing,
			String setterNameBasis, String setterParameterName, String setterParameterTyp, Object wert,
			int primaererRang, int sekundaererRang, Object standardwert) {
		this(eigenschaftsName, eigenschaftsTyp, setterNameSwing, setterNameBasis, setterParameterName,
				setterParameterTyp, wert, primaererRang, sekundaererRang);
		this.standardwert = standardwert;
	}

	public KomponentenEigenschaft(String eigenschaftsName, String eigenschaftsTyp, Object wert, int primaererRang,
			int sekundaererRang) {
		super(eigenschaftsName, wert, primaererRang, sekundaererRang, eigenschaftsTyp);
	}

	public void setWertUeberStringFeld(String[] sf) {
		wert = setWertUeberStringFeld(sf, eigenschaftsTyp);
	}

	private Object setWertUeberString(String sf, String eigenschaftsTyp) {
		Object wert = null;
		if (sf == null) {
			return null;
		}
		if (eigenschaftsTyp.equals("int")) {
			wert = Integer.parseInt(sf);
		} else if (eigenschaftsTyp.equals("double")) {
			wert = Double.parseDouble(sf);
		} else if (eigenschaftsTyp.equals("boolean")) {
			wert = sf.equals("true");
		} else if (eigenschaftsTyp.equals("java.awt.Color") || eigenschaftsTyp.equals("Color")) {
			wert = new Color(Integer.parseInt(sf));
		} else if (eigenschaftsTyp.equals("java.awt.String") || eigenschaftsTyp.equals("String")) {
			wert = sf;
		} else {
			System.out.println("Unbekannter Typ "+eigenschaftsTyp +"in setWertUeberString in"+getClass());
		}
		return wert;
	}

	private Object setWertUeberStringFeld(String[] sf, String eigenschaftsTyp) {
		Object wert = null;
		if (sf == null) {
			return null;
		}
		if (sf.length == 1) {
			wert = setWertUeberString(sf[0], eigenschaftsTyp);
		} else if (eigenschaftsTyp.contains("Point") || eigenschaftsTyp.contains("Dimension")) {
			wert = new Object[] { Integer.parseInt(sf[0]), Integer.parseInt(sf[1]) };
		} else if (eigenschaftsTyp.equals("ColorInt")) {
			Object[] temp = new Object[2];
			temp[0] = new Color(Integer.parseInt(sf[0]));
			temp[1] = Integer.parseInt(sf[1]);
			wert = temp;
		} 
//		else if (eigenschaftsTyp.equals("Vector<String>")) {
//			Vector<String> als = new Vector();
//			for (int i = 0; i < sf.length; i++) {
//				als.add(sf[i]);
//			}
//			wert = als;
//		} 
		else {
			System.out.println("Unbekannter Typ "+eigenschaftsTyp +"in setWertUeberStringFeld in"+getClass());
		}
		return wert;
	}

	/**
	 * @return liefert setterParameterTypen
	 */
	public String[] getSetterParameterTypen() {
		return setterParameterTypen;
	}

	/**
	 * @param setterParameterTypen
	 *            setzt setterParameterTypen
	 */
	public void setSetterParameterTypen(String[] setterParameterTypen) {
		this.setterParameterTypen = setterParameterTypen;
	}

	/**
	 * @return liefert wert
	 */
	public Object getWert() {
		return wert;
	}

	/**
	 * @param wert
	 *            setzt wert
	 */
	public void setWert(Object wert) {
		this.wert = wert;
	}

	/**
	 * @return liefert eigenschaftsName
	 */
	public String getEigenschaftsName() {
		return eigenschaftsName;
	}

	/**
	 * @param eigenschaftsName
	 *            setzt eigenschaftsName
	 */
	public void setEigenschaftsName(String eigenschaftsName) {
		this.eigenschaftsName = eigenschaftsName;
	}

	/**
	 * @return liefert setterNameSwing
	 */
	public String getSetterNameSwing() {
		return setterNameSwing;
	}

	/**
	 * @param setterNameSwing
	 *            setzt setterNameSwing
	 */
	public void setSetterNameSwing(String setterNameSwing) {
		this.setterNameSwing = setterNameSwing;
	}

	/**
	 * @return liefert setterNameBasis
	 */
	public String getSetterNameBasis() {
		return setterNameBasis;
	}

	/**
	 * @param setterNameBasis
	 *            setzt setterNameBasis
	 */
	public void setSetterNameBasis(String setterNameBasis) {
		this.setterNameBasis = setterNameBasis;
	}

	/**
	 * @return liefert setterParameterTyp
	 */
	public String[] getSetterParameterTyp() {
		return setterParameterTypen;
	}

	/**
	 * @param setterParameterTyp
	 *            setzt setterParameterTyp
	 */
	public void setSetterParameterTyp(String[] setterParameterTyp) {
		this.setterParameterTypen = setterParameterTyp;
	}

	/**
	 * @return liefert setterParameterNamen
	 */
	public String[] getSetterParameterNamen() {
		return setterParameterNamen;
	}

	/**
	 * @param setterParameterNamen
	 *            setzt setterParameterNamen
	 */
	public void setSetterParameterNamen(String[] setterParameterName) {
		this.setterParameterNamen = setterParameterName;
	}

	/**
	 * 
	 * @return liefert den Wert als Stringfeld codiert
	 */

	public String[] getStringFeldFuerEinfacheWerte() {
		if (wert == null) {
			return null;
		}
		Object[] wertfeld = getWertAlsArray();		
		String[] sf = new String[wertfeld.length];
		
		for (int i=0;i<wertfeld.length;i++){
			sf[i] = getStringZuObjectFuerEinfachenTyp(wertfeld[i]);			
		}
		return sf;
	}
	
	

	public String getSource(String vname, boolean istSwing) {
		// Für einfache Datentypen Vergleich auf Standardwert
		String wertString = getStringZuObjectFuerEinfachenTyp(wert);
		String standardwertstring = getStringZuObjectFuerEinfachenTyp(standardwert);

		if (wertString != null && standardwertstring != null && wertString.equals(standardwertstring)) {
			return "";
		}

		String sn = null;
		if (istSwing) {
			sn = setterNameSwing;
		} else {
			sn = setterNameBasis;
		}
		if (sn == null || sn.equals("")) {
			return "";
		}
		String src = vname + "." + sn + "(";
		if (getWert() instanceof Object[]) {
			for (Object o : (Object[]) getWert()) {
				src += sourceZuObject(o);
				src += ",";
			}
		} else {
			src += sourceZuObject(getWert());
			src += ",";
		}
		if (src.endsWith(",")) {
			src = src.substring(0, src.length() - 1);
			src += ");\n";
		}
		return "    " + src;
	}

	private String getStringZuObjectFuerEinfachenTyp(Object o) {
		String src = null;
		if (o == null) {
			return src;
		}
		if (o instanceof java.lang.Integer) {
			src = (int) o + "";
		} else if (o instanceof java.lang.Double) {
			src = (double) o + "";
		} else if (o instanceof java.awt.Color) {
			src = ((Color) o).getRGB() + "";
		} else if (o instanceof java.lang.String) {
			src = (String) o;
		} else if (o instanceof java.lang.Boolean) {
			src = (boolean) o + "";
		} 
		return src;
	}

	public String sourceZuObject(Object o) {
		String src = null;
		if (o instanceof java.lang.Integer) {
			src = (int) o + "";
		} else if (o instanceof java.lang.Double) {
			src = (double) o + "";
		} else if (o instanceof java.awt.Color) {
			src = "new Color(" + ((Color) o).getRGB() + ")";
		} else if (o instanceof java.lang.String) {
			src = "\"" + (String) o + "\"";
		} else if (o instanceof java.lang.Boolean) {
			src = (boolean) o + "";
		} else {
			System.out.println("unbekannter Typ in sourceZuObject  in " + getClass());
		}
		return src;
	}

	public Object[] getWertAlsArray() {
		if (wert instanceof Object[]) {
			return (Object[]) wert;
		}
		Object[] temp = new Object[1];
		temp[0] = wert;
		return temp;
	}

}
