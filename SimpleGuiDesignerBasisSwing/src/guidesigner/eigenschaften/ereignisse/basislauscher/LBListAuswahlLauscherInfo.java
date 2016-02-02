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
public class LBListAuswahlLauscherInfo extends LauscherInfo    {
	public static Vector<String> llistener = new Vector<String> ();
	/**
	 * 
	 */
	public LBListAuswahlLauscherInfo() {
		super(Controller.BASISGUILAUSCHER);
		this.setImportString("import basis.*;\n");
		this.setLauscherName("ListAuswahlLauscher");		
		String[] meth=new String[2];
		meth[0] ="  @Override\n  public void bearbeiteAuswahl(ListAuswahl k)";
		meth[1] ="  @Override\n  public void bearbeiteDoppelklick(ListAuswahl k)";
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
