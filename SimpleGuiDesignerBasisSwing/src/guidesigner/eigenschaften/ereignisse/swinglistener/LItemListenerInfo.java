package guidesigner.eigenschaften.ereignisse.swinglistener;


import java.util.Vector;

import guidesigner.control.Controller;
import guidesigner.eigenschaften.ereignisse.LauscherInfo;

public class LItemListenerInfo extends LauscherInfo{
	public static Vector<String> itemListener = new Vector<String> ();
	/**
	 * 
	 */
	public LItemListenerInfo() {
		super(Controller.SWINGGUI);
		this.setImportString("import java.awt.event.*;\n");
		this.setLauscherName("ItemListener");		
		String[] meth=new String[1]; 
		meth[0] ="  @Override\n  public void itemStateChanged(ItemEvent e)";
		this.setLauschermethoden(meth);		
	}
	
	@Override
	public void addListener(String listener) {
		if (itemListener.contains(listener)){
			return;
		}
		itemListener.add(listener);		
	}

	@Override
	public void leereListener() {
		itemListener = new Vector<String> ();
}
	@Override
	public Vector<String> getListener() {
		return itemListener;
	}
}
