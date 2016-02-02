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
public class LBMausLauscherStandardLauscherInfo extends LauscherInfo    {
	public static Vector<String> llistener = new Vector<String> ();
	/**
	 * 
	 */
	public LBMausLauscherStandardLauscherInfo() {
		super(Controller.BASISGUILAUSCHER);
		this.setImportString("import basis.*;\n");
		this.setLauscherName("MausLauscherStandard");		
		String[] meth=new String[6];
		meth[0] ="  @Override\n  public void bearbeiteMausDruck(Object k, int x, int y)";
		meth[1] ="  @Override\n  public void bearbeiteMausDruckRechts(Object k, int x, int y)";
		meth[2] ="  @Override\n  public void bearbeiteMausLos(Object k,int x, int y)";
		meth[3] ="  @Override\n  public void bearbeiteMausLosRechts(Object k,int x, int y)";
		meth[4] ="  @Override\n  public void bearbeiteMausBewegt(Object k,int x, int y)";
		
		meth[5] ="  @Override\n  public void bearbeiteMausGezogen(Object k,int x, int y)";
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
