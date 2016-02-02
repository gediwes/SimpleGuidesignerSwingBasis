package guidesigner.eigenschaften.ereignisse.swinglistener;

import java.util.Vector;

import guidesigner.control.Controller;
import guidesigner.eigenschaften.ereignisse.LauscherInfo;

public class LKeyListenerInfo extends LauscherInfo  {
	 public static Vector<String> keyListener = new Vector<String> ();
		public LKeyListenerInfo() {
			super(Controller.SWINGGUI);
			this.setImportString("import java.awt.event.*;\n");
			this.setLauscherName("KeyListener");		
			String[] meth=new String[3];
			meth[0] ="  @Override\n  public void keyPressed(KeyEvent e)";
			meth[1] ="  @Override\n  public void keyReleased(KeyEvent e)";
			meth[2] ="  @Override\n  public void keyTyped(KeyEvent e)";
			this.setLauschermethoden(meth);		
		}

		@Override
		public void addListener(String listener) {
			if (keyListener.contains(listener)){
				return;
			}
			keyListener.add(listener);	
		}

		@Override
		public void leereListener() {
			keyListener = new Vector<String> ();
		}
		@Override
		public Vector<String> getListener() {
			return keyListener;
		}
	
}
