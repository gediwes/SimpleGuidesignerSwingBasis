package guidesigner.eigenschaften.ereignisse.swinglistener;

import java.util.Vector;

import guidesigner.control.Controller;
import guidesigner.eigenschaften.ereignisse.LauscherInfo;

public class LComponentListenerInfo extends LauscherInfo {
	 public static Vector<String> componentListener = new Vector<String> ();
	public LComponentListenerInfo() {
		super(Controller.SWINGGUI);
		this.setImportString("import java.awt.event.*;\n");
		this.setLauscherName("ComponentListener");		
		String[] meth=new String[4];
		meth[0] ="  @Override\n  public void componentHidden(ComponentEvent e)";
		meth[1] ="  @Override\n  public void componentMoved(ComponentEvent e)";
		meth[2] ="  @Override\n  public void componentResized(ComponentEvent e)";
		meth[3] ="  @Override\n  public void componentShown(ComponentEvent e)";
		this.setLauschermethoden(meth);		
	}

	@Override
	public void addListener(String listener) {
		if (componentListener.contains(listener)){
			return;
		}
		componentListener.add(listener);	
	}

	@Override
	public void leereListener() {
		componentListener = new Vector<String> ();
	}
	@Override
	public Vector<String> getListener() {
		return componentListener;
	}
}
