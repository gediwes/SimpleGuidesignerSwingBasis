package guidesigner.eigenschaften.ereignisse.swinglistener;

import java.util.Vector;

import guidesigner.control.Controller;
import guidesigner.eigenschaften.ereignisse.LauscherInfo;

public class LInputMethodListenerInfo extends LauscherInfo {
	 public static Vector<String> inputMethodListener = new Vector<String> ();
		/**
		 * 
		 */
		public LInputMethodListenerInfo() {
			super(Controller.SWINGGUI);
			this.setImportString("import java.awt.event.*;\n");
			this.setLauscherName("InputMethodListener");		
			String[] meth=new String[2]; 
			meth[0] ="  @Override\n  public void caretPositionChanged(InputMethodEvent e)";
			meth[1] ="  @Override\n  public void inputMethodTextChanged(InputMethodEvent e)";
			this.setLauschermethoden(meth);		
		}
		
		@Override
		public void addListener(String listener) {
			if (inputMethodListener.contains(listener)){
				return;
			}
			inputMethodListener.add(listener);		
		}

		@Override
		public void leereListener() {
			inputMethodListener = new Vector<String> ();
	}

		@Override
		public Vector<String> getListener() {
			return inputMethodListener;
		}
}
