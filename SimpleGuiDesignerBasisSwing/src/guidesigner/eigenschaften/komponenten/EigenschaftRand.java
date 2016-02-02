package guidesigner.eigenschaften.komponenten;

import java.awt.Color;

public class EigenschaftRand extends KomponentenEigenschaft {

	public EigenschaftRand( int primaererRang, int sekundaererRang) {
		super("Rand", "ColorInt", "setBorder", "setzeRand", new String[]{"farbe","breite"},
				new String[]{"java.awt.Color","int"}, new Object[] {Color.BLACK,0}, primaererRang, sekundaererRang);
		
	}
	
	
	@Override
	public String getSource(String vname, boolean istSwing) {
		Object[] obwert = (Object[])wert;
		int rbreite = (int)obwert[1];
		Color farbe = (Color)obwert[0];
		if (rbreite == 0){
			return "";
		}
		if (istSwing) {
			String src =  "    " + vname+ ".setBorder(new LineBorder(new Color("+farbe.getRGB()+"),"+ rbreite+"));\n";			
				return src;
				
			
		} else {			
				return "    " + vname
						+ ".setzeRand(new Color("+farbe.getRGB()+"),"+ rbreite+");\n";		
		}
	}
	
}
