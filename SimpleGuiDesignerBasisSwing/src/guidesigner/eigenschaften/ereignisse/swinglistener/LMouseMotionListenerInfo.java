package guidesigner.eigenschaften.ereignisse.swinglistener;


import java.util.Vector;

import guidesigner.control.Controller;
import guidesigner.eigenschaften.ereignisse.LauscherInfo;

public class LMouseMotionListenerInfo extends LauscherInfo{
	public static Vector<String> mouseMotionListener = new Vector<String> ();
	public LMouseMotionListenerInfo() {
		super(Controller.SWINGGUI);
		this.setImportString("import java.awt.event.*;\n");
		this.setLauscherName("MouseMotionListener");		
		String[] meth=new String[2];
		meth[0] ="  @Override\n  public void mouseDragged(MouseEvent e)";
		meth[1] ="  @Override\n  public void mouseMoved(MouseEvent e)";
		this.setLauschermethoden(meth);		
	}

	@Override
	public void addListener(String listener) {
		if (mouseMotionListener.contains(listener)){
			return;
		}
		mouseMotionListener.add(listener);	
	}

	@Override
	public void leereListener() {
		mouseMotionListener = new Vector<String> ();
	}

	@Override
	public Vector<String> getListener() {
		return mouseMotionListener;
	}

}
