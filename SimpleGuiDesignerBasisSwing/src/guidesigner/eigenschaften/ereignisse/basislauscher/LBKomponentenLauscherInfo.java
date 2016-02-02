/**
 * 
 */
package guidesigner.eigenschaften.ereignisse.basislauscher;

import java.util.Vector;

import guidesigner.control.Controller;
import guidesigner.eigenschaften.ereignisse.LauscherInfo;





/**
 * @author Georg Dick
 *
 */
public class LBKomponentenLauscherInfo extends LauscherInfo    {
	public static Vector<String> llistener = new Vector<String> ();
	/**
	 * 
	 */
	public LBKomponentenLauscherInfo() {
		super(Controller.BASISGUILAUSCHER);
		this.setImportString("import basis.*;\n");
		this.setLauscherName("KomponentenLauscher");		
		String[] meth=new String[1]; 
		meth[0] ="  @Override\n  public void bearbeiteKomponentenVeraenderung(Komponente k)";
		this.setLauschermethoden(meth);		
	}
	
	@Override
	public void addListener(String listener) {
		if (llistener.contains(listener)){
			return;
		}
		llistener.add(listener);		
	}


	@Override
	public void leereListener() {
		llistener = new Vector<String> ();
		
	}

	@Override
	public Vector<String> getListener() {
		return llistener;
	}



	

}
