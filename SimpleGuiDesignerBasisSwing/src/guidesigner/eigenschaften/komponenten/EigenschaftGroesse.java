package guidesigner.eigenschaften.komponenten;



public class EigenschaftGroesse extends KomponentenEigenschaft {

	public EigenschaftGroesse( Object wert, int primaererRang, int sekundaererRang) {
	super("Größe", "java.awt.Dimension", "setSize", "setzeGroesse", new String[] { "breite", "hoehe" },
			new String[] { "int", "int" }, wert, primaererRang, sekundaererRang);
	}

	/* (non-Javadoc)
	 * @see guidesigner.eigenschaften.komponenten.KomponentenEigenschaft#getSource(java.lang.String, boolean)
	 */
	@Override
	public String getSource(String vname, boolean istSwing) {
		if (!istSwing){
			return "";
		}
		return super.getSource(vname, istSwing);
	}
	


	
	
}
