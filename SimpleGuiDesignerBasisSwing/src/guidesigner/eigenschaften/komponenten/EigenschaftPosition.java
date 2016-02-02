package guidesigner.eigenschaften.komponenten;



public class EigenschaftPosition extends KomponentenEigenschaft {

	public EigenschaftPosition( Object wert, int primaererRang, int sekundaererRang) {
	super("Position", "java.awt.Point",
			"setLocation", "setzePosition", new String[] { "x", "y" }, new String[]{ "int", "int" }, wert, primaererRang, sekundaererRang);
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
