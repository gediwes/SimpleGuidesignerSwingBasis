package guidesigner.eigenschaften.ereignisse.basisschleife;

import guidesigner.control.Controller;
import guidesigner.eigenschaften.GrundEigenschaft;

public class EreignisBearbeiteMethodenEigenschaft extends GrundEigenschaft{

	    String komponentenTyp="";String methodenName="";
	    /**
		 * @return the komponentenTyp
		 */
		public String getKomponentenTyp() {
			return komponentenTyp;
		}


		/**
		 * @param komponentenTyp the komponentenTyp to set
		 */
		public void setKomponentenTyp(String komponentenTyp) {
			this.komponentenTyp = komponentenTyp;
		}

		

		/**
		 * @return the methodenName
		 */
		public String getMethodenName() {
			return methodenName;
		}


		/**
		 * @param methodenName the methodenName to set
		 */
		public void setMethodenName(String methodenName) {
			this.methodenName = methodenName;
		}

        public boolean getGewaehlt(){
        	return (boolean)getWert();
        }
		
		
		public EreignisBearbeiteMethodenEigenschaft(String basisKlasse, String ereignisMethodenName, boolean standardVorgabe,
				int primaererRang, int sekundaererRang) {
			super(basisKlasse+ereignisMethodenName,standardVorgabe,primaererRang,sekundaererRang);
			this.komponentenTyp=basisKlasse;
			this.methodenName=ereignisMethodenName;	
			this.setKompatibleGUI(Controller.BASISGUISCHLEIFE);					
		}

		
		public String[] getSourceBearbeiteMethode(
				String variablenName) {
			String neu = variablenName.toUpperCase().substring(0, 1);
			if (variablenName.length()>1){
				neu+= variablenName.substring(1);
			}
			String ret[] = new String[3];
			ret[0]= "public void bearbeite"+neu+"("+komponentenTyp+" p"+komponentenTyp+"){";
			ret[1]=" //TODO";
			ret[2]= "}";
			return ret;
		}
		
		public String[] getSourceFuehreAusMethodenaufruf(
				String variablenName) {
			String neu = variablenName.toUpperCase().substring(0, 1);
			if (variablenName.length()>1){
				neu+= variablenName.substring(1);
			}
			String ret[] = new String[3];
			ret[0]= "if ("+variablenName+"."+methodenName+"()){";
			ret[1]= "  this.bearbeite"+neu+"("+variablenName+");";
			ret[2]= "}";
					return ret;
		}


}
