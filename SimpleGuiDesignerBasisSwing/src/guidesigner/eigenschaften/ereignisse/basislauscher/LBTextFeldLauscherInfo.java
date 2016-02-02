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
public class LBTextFeldLauscherInfo extends LauscherInfo  {
	public static Vector<String> llistener = new Vector<String> ();
	/**
	 * 
	 */
	public LBTextFeldLauscherInfo() {
		super(Controller.BASISGUILAUSCHER);
		this.setImportString("import basis.*;\n");
		this.setLauscherName("TextFeldLauscher");		
		String[] meth=new String[3]; 
		meth[0] ="  @Override\n  public void bearbeiteReturnGedrueckt(TextFeld k)";
		meth[1] ="  @Override\n  public void bearbeiteTextVeraenderung(TextFeld k)";
		meth[2] ="  @Override\n  public void bearbeiteFokusVerloren(TextFeld k)";
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
