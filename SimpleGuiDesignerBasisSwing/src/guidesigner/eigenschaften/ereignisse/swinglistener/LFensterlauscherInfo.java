/**
 * 
 */
package guidesigner.eigenschaften.ereignisse.swinglistener;

import java.util.Vector;

import guidesigner.control.Controller;
import guidesigner.eigenschaften.ereignisse.LauscherInfo;



/**
 * @author Georg Dick
 *
 */
public class LFensterlauscherInfo extends LauscherInfo  {
    public static Vector<String> actionListener = new Vector<String> ();
	/**
	 * 
	 */
	public LFensterlauscherInfo() {
		super(Controller.SWINGGUI);
		this.setImportString("import java.awt.event.*;\n");
		this.setLauscherName("ActionListener");		
		String[] meth=new String[1]; meth[0] ="  @Override\n  public void actionPerformed(ActionEvent e)";
		this.setLauschermethoden(meth);		
	}
	
	@Override
	public void addListener(String listener) {
		if (actionListener.contains(listener)){
			return;
		}
		actionListener.add(listener);		
	}

	@Override
	public void leereListener() {
		actionListener = new Vector<String> ();
		
	}

	@Override
	public Vector<String> getListener() {
		return actionListener;
	}

	

}
