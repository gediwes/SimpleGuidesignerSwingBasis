package guidesigner.eigenschaften.ereignisse.swinglistener;

import java.util.Vector;

import guidesigner.control.Controller;
import guidesigner.eigenschaften.ereignisse.LauscherInfo;

public class LMouseListenerInfo extends LauscherInfo {
	 public static Vector<String> mouseListener = new Vector<String> ();
		public LMouseListenerInfo() {
			super(Controller.SWINGGUI);
			this.setImportString("import java.awt.event.*;\n");
			this.setLauscherName("MouseListener");		
			String[] meth=new String[5];
			meth[0] ="  @Override\n  public void mouseClicked(MouseEvent e)";
			meth[1] ="  @Override\n  public void mouseEntered(MouseEvent e)";
			meth[2] ="  @Override\n  public void mouseExited(MouseEvent e)";
			meth[3] ="  @Override\n  public void mousePressed(MouseEvent e)";
			meth[4] ="  @Override\n  public void mouseReleased(MouseEvent e)";
			this.setLauschermethoden(meth);		
		}

		@Override
		public void addListener(String listener) {
			if (mouseListener.contains(listener)){
				return;
			}
			mouseListener.add(listener);	
		}

		@Override
		public void leereListener() {
			mouseListener = new Vector<String> ();
		}

		@Override
		public Vector<String> getListener() {
			return mouseListener;
		}
}
