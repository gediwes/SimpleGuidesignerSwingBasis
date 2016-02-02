/**
 * 
 */
package guidesigner.eigenschaften.ereignisse;

import java.util.Vector;

/**
 * @author Georg Dick
 *
 */
public abstract class LauscherInfo {
	
	
	int kompatibleGui;
	/**
	 * @return the kompatibleGui
	 */
	public int getKompatibleGui() {
		return kompatibleGui;
	}
	public LauscherInfo(int kompatibleGui) {
		this.kompatibleGui=kompatibleGui;
		
	}
	
	String lauscherName="";
	String[] lauschermethoden;
	String importString;
	/**
	 * @return liefert lauscherName
	 */
	public String getLauscherName() {
		return lauscherName;
	}
	/**
	 * @param lauscherName setzt lauscherName
	 */
	public void setLauscherName(String lauscherName) {
		this.lauscherName = lauscherName;
	}
	
	/**
	 * @return liefert lauschermethoden
	 */
	public String[] getLauschermethoden() {
		return lauschermethoden;
	}
	/**
	 * @param lauschermethoden setzt lauschermethoden
	 */
	public void setLauschermethoden(String[] lauschermethoden) {
		this.lauschermethoden = lauschermethoden;
	}
	/**
	 * @return liefert importString
	 */
	public String getImportString() {
		return importString;
	}
	/**
	 * @param importString setzt importString
	 */
	public void setImportString(String importString) {
		this.importString = importString;
	}
	
    public abstract void addListener(String vname);
    public abstract void leereListener();
    public abstract Vector<String> getListener();
	
    
}
