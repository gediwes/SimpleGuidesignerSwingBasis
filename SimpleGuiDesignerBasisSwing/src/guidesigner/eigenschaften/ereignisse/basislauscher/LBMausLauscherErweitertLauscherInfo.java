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
public class LBMausLauscherErweitertLauscherInfo extends LauscherInfo  {
	public static Vector<String> llistener = new Vector<String> ();
	/**
	 * 
	 */
	public LBMausLauscherErweitertLauscherInfo() {
		super(Controller.BASISGUILAUSCHER);
		this.setImportString("import basis.*;\n");
		this.setLauscherName("MausLauscherErweitert");		
		String[] meth=new String[5];
		meth[0] ="  @Override\n  public void bearbeiteMausKlick(Object k, int x, int y)";
		meth[1] ="  @Override\n  public void bearbeiteMausKlickRechts(Object k, int x, int y)";
		meth[2] ="  @Override\n  public void bearbeiteDoppelKlick(Object k,int x, int y)";
		meth[3] ="  @Override\n  public void bearbeiteMausHinein(Object k,int x, int y)";
		meth[4] ="  @Override\n  public void bearbeiteMausHeraus(Object k,int x, int y)";
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
