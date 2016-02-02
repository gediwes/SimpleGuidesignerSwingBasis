package guidesigner.eigenschaften.ereignisse.swinglistener;

import java.util.Vector;

import guidesigner.control.Controller;
import guidesigner.eigenschaften.ereignisse.LauscherInfo;


public class LCaretListenerInfo extends LauscherInfo {

	 public static Vector<String> caretListener = new Vector<String> ();
		/**
		 * 
		 */
		public LCaretListenerInfo() {
			super(Controller.SWINGGUI);
			this.setImportString("import javax.swing.event.*;\n");
			this.setLauscherName("CaretListener");		
			String[] meth=new String[1]; 
			meth[0] ="  @Override\n  public void caretUpdate(CaretEvent e)";
			this.setLauschermethoden(meth);		
		}
		
		@Override
		public void addListener(String listener) {
			if (caretListener.contains(listener)){
				return;
			}
			caretListener.add(listener);		
		}

		@Override
		public void leereListener() {
			caretListener = new Vector<String> ();			
		}
	
		public Vector<String> getListener(){
			return caretListener;
		}

}
