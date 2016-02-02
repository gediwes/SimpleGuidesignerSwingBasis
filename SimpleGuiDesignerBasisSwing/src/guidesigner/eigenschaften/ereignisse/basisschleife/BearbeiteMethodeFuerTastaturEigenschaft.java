package guidesigner.eigenschaften.ereignisse.basisschleife;

public class BearbeiteMethodeFuerTastaturEigenschaft extends EreignisBearbeiteMethodenEigenschaft{

	

	public BearbeiteMethodeFuerTastaturEigenschaft(String ereignisMethode, boolean wert, int primaererRang,
			int sekundaererRang) {
		super("Tastatur", ereignisMethode, wert, primaererRang, sekundaererRang);
		
	}

	public String[] getSourceBearbeiteMethode(
			String variablenName) {
	       switch(getMethodenName()){
	       case "wurdeGedrueckt":
	    	   return getSourceBearbeiteWurdeGedruecktMethode(variablenName);	      
	       }
	       return null;
	}
	public String[] getSourceFuehreAusMethodenaufruf(
			String variablenName) {
		 switch(getMethodenName()){
	       case "wurdeGedrueckt":
	    	   return getSourceFuehreAusWurdeGedruecktMethodenaufruf(variablenName);	      
	       }
	       return null;
		
	}
	public String[] getSourceBearbeiteWurdeGedruecktMethode(
			String variablenName) {
		String neu = variablenName.toUpperCase().substring(0, 1);
		if (variablenName.length()>1){
			neu+= variablenName.substring(1);
		}
		String ret[] = new String[3];
		ret[0]= "public void bearbeite"+neu+"WurdeGedrueckt(char zeichen){";
		ret[1]="   //TODO";
		ret[2]= "}";
		return ret;
	}
	
	public String[] getSourceFuehreAusWurdeGedruecktMethodenaufruf(
			String variablenName) {
		String neu = variablenName.toUpperCase().substring(0, 1);
		if (variablenName.length()>1){
			neu+= variablenName.substring(1);
		}
		String ret[] = new String[3];
		ret[0]= "  if ("+variablenName+"."+getMethodenName()+"()){";
		ret[1]= "  this.bearbeite"+neu+"WurdeGedrueckt("+variablenName+".holeZeichen());";
		ret[2]= "  }";
		return ret;
	}
}
