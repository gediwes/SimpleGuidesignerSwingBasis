package guidesigner.eigenschaften.ereignisse.swinglistener;

import java.util.Vector;

import guidesigner.control.Controller;
import guidesigner.eigenschaften.ereignisse.LauscherInfo;

public class LChangeListenerInfo extends LauscherInfo{
	 public static Vector<String> changeListener = new Vector<String> ();
		/**
		 * 
		 */
		public LChangeListenerInfo() {
			super(Controller.SWINGGUI);
			this.setImportString("import javax.swing.event.*;\n");
			this.setLauscherName("ChangeListener");		
			String[] meth=new String[1]; 
			meth[0] ="  @Override\n  public void stateChanged(ChangeEvent e)";
			this.setLauschermethoden(meth);		
		}
		
		@Override
		public void addListener(String listener) {
			if (changeListener.contains(listener)){
				return;
			}
			changeListener.add(listener);		
		}

		@Override
		public void leereListener() {
			changeListener = new Vector<String> ();
	    }

		@Override
		public Vector<String> getListener() {
			return changeListener;
		}

}
