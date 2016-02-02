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
public class LBListBoxLauscherInfo extends LauscherInfo    {
	public static Vector<String> llistener = new Vector<String> ();
	/**
	 * 
	 */
	public LBListBoxLauscherInfo() {
		super(Controller.BASISGUILAUSCHER);
		this.setImportString("import basis.*;\n");
		this.setLauscherName("ListBoxLauscher");		
		String[] meth=new String[1]; meth[0] ="  @Override\n  public void bearbeiteListBoxAuswahl(ListBox k)";
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
