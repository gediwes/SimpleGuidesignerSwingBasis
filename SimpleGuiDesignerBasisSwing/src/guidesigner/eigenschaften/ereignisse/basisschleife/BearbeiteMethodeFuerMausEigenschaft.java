package guidesigner.eigenschaften.ereignisse.basisschleife;

public class BearbeiteMethodeFuerMausEigenschaft extends EreignisBearbeiteMethodenEigenschaft {
  
	public BearbeiteMethodeFuerMausEigenschaft( String ereignisMethode, boolean vorgabe,
			int primaererRang, int sekundaererRang) {
		super("Maus", ereignisMethode, vorgabe, primaererRang, sekundaererRang);
		
	}
	
	
	
	
	
	
	public String[] getSourceBearbeiteMethode(
			String variablenName) {
	       switch(getMethodenName()){
	       case "istGedrueckt":
	    	   return getSourceBearbeiteIstGedruecktMethode(variablenName);
	       case "istRechtsGedrueckt":
	    	   return 	getSourceBearbeiteIstRechtsGedruecktMethode(variablenName);
	       case "wurdeGeklickt":
	    	   return getSourceBearbeiteWurdeGeklicktMethode(variablenName);
	       case "wurdeBewegt":
	    	   return getSourceBearbeiteWurdeBewegtMethode(variablenName);
	       }
	       return null;
	}
	public String[] getSourceFuehreAusMethodenaufruf(
			String variablenName) {
		 switch(getMethodenName()){
	       case "istGedrueckt":
	    	   return getSourceFuehreAusIstGedruecktMethodenaufruf(variablenName);
	       case "istRechtsGedrueckt":
	    	   return 	getSourceFuehreAusIstRechtsGedruecktMethodenaufruf(variablenName);
	       case "wurdeGeklickt":
	    	   return getSourceFuehreAusWurdeGeklicktMethodenaufruf(variablenName);
	       case "wurdeBewegt":
	    	   return getSourceFuehreAusWurdeBewegtMethodenaufruf(variablenName);
	       }
	       return null;
		
	}
	public String[] getSourceBearbeiteWurdeBewegtMethode(
			String variablenName) {
		String neu = variablenName.toUpperCase().substring(0, 1);
		if (variablenName.length()>1){
			neu+= variablenName.substring(1);
		}
		String ret[] = new String[3];
		ret[0]= "public void bearbeite"+neu+"WurdeBewegt(int nachX, int nachY){";
		ret[1]="   //TODO";
		ret[2]= "}";
		return ret;
	}
	
	public String[] getSourceFuehreAusWurdeBewegtMethodenaufruf(
			String variablenName) {
		String neu = variablenName.toUpperCase().substring(0, 1);
		if (variablenName.length()>1){
			neu+= variablenName.substring(1);
		}
		String ret[] = new String[3];
		ret[0]= "  if ("+variablenName+"."+getMethodenName()+"()){";
		ret[1]= "  this.bearbeite"+neu+"WurdeBewegt("+variablenName+".holeBewegung().getX(), "+variablenName+".holeBewegung().getX());";
		ret[2]= "  }";
		return ret;
	}
	public String[] getSourceBearbeiteWurdeGeklicktMethode(
			String variablenName) {
		String neu = variablenName.toUpperCase().substring(0, 1);
		if (variablenName.length()>1){
			neu+= variablenName.substring(1);
		}
		String ret[] = new String[3];
		ret[0]= "public void bearbeite"+neu+"WurdeGeklickt(int beiX, int beiY){";
		ret[1]="   //TODO";
		ret[2]= "}";
		return ret;
	}
	
	public String[] getSourceFuehreAusWurdeGeklicktMethodenaufruf(
			String variablenName) {
		String neu = variablenName.toUpperCase().substring(0, 1);
		if (variablenName.length()>1){
			neu+= variablenName.substring(1);
		}
		String ret[] = new String[3];
		ret[0]= "  if ("+variablenName+"."+getMethodenName()+"()){";
		ret[1]= "  this.bearbeite"+neu+"WurdeGeklickt("+variablenName+".holeKlick().getX(), "+variablenName+".holeKlick().getX());";
		ret[2]= "  }";
		return ret;
	}
	public String[] getSourceBearbeiteIstRechtsGedruecktMethode(
			String variablenName) {
		String neu = variablenName.toUpperCase().substring(0, 1);
		if (variablenName.length()>1){
			neu+= variablenName.substring(1);
		}
		String ret[] = new String[3];
		ret[0]= "public void bearbeite"+neu+"IstRechtsGedrueckt(int hPosition, int vPosition){";
		ret[1]="   //TODO";
		ret[2]= "}";
		return ret;
	}
	
	public String[] getSourceFuehreAusIstRechtsGedruecktMethodenaufruf(
			String variablenName) {
		String neu = variablenName.toUpperCase().substring(0, 1);
		if (variablenName.length()>1){
			neu+= variablenName.substring(1);
		}
		String ret[] = new String[3];
		ret[0]= "  if ("+variablenName+"."+getMethodenName()+"()){";
		ret[1]= "  this.bearbeite"+neu+"IstRechtsGedrueckt("+variablenName+".hPosition(), "+variablenName+".vPosition());";
		ret[2]= "  }";
		return ret;
	}
	public String[] getSourceBearbeiteIstGedruecktMethode(
			String variablenName) {
		String neu = variablenName.toUpperCase().substring(0, 1);
		if (variablenName.length()>1){
			neu+= variablenName.substring(1);
		}
		String ret[] = new String[3];
		ret[0]= "public void bearbeite"+neu+"IstGedrueckt(int hPosition, int vPosition){";
		ret[1]="   //TODO";
		ret[2]= "}";
		return ret;
	}
	
	public String[] getSourceFuehreAusIstGedruecktMethodenaufruf(
			String variablenName) {
		String neu = variablenName.toUpperCase().substring(0, 1);
		if (variablenName.length()>1){
			neu+= variablenName.substring(1);
		}
		String ret[] = new String[3];
		ret[0]= "  if ("+variablenName+"."+getMethodenName()+"()){";
		ret[1]= "  this.bearbeite"+neu+"IstGedrueckt("+variablenName+".hPosition(), "+variablenName+".vPosition());";
		ret[2]= "  }";
		return ret;
	}

}
