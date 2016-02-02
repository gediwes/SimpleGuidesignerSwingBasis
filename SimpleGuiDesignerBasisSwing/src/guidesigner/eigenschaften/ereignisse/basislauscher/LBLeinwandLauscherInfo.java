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
public class LBLeinwandLauscherInfo extends LauscherInfo    {
    public static Vector<String> llistener = new Vector<String> ();
	/**
	 * 
	 */
	public LBLeinwandLauscherInfo() {
		super(Controller.BASISGUILAUSCHER);
		this.setImportString("import basis.*;\n");
		this.setLauscherName("LeinwandLauscher");		
		String[] meth=new String[1]; 
		meth[0] ="  @Override\n  public void leinwandWurdeGezeichnet(LeinwandIF k)";
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
