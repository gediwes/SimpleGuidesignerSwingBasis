package guidesigner.eigenschaften.ereignisse.swinglistener;

import java.util.Vector;

import guidesigner.control.Controller;
import guidesigner.eigenschaften.ereignisse.LauscherInfo;

public class LAdjustmentListenerInfo extends LauscherInfo{
	 public static Vector<String> adjustmentListener = new Vector<String> ();
		/**
		 * 
		 */
		public LAdjustmentListenerInfo() {
			super(Controller.SWINGGUI);
			this.setImportString("import java.awt.event.*;\n");
			this.setLauscherName("AdjustmentListener");		
			String[] meth=new String[1]; 
			meth[0] ="  @Override\n  public void adjustmentValueChanged(AdjustmentEvent e)";
			this.setLauschermethoden(meth);		
		}
		
		@Override
		public void addListener(String listener) {
			if (adjustmentListener.contains(listener)){
				return;
			}
			adjustmentListener.add(listener);		
		}

		@Override
		public void leereListener() {
			adjustmentListener = new Vector<String> ();
			
		}
		@Override
		public Vector<String> getListener() {
			// TODO Auto-generated method stub
			return adjustmentListener;
		}
		

}
