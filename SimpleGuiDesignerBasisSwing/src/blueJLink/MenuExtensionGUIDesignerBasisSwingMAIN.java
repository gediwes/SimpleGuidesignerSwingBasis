package blueJLink;

import java.awt.EventQueue;

import bluej.extensions.BlueJ;
import bluej.extensions.Extension;
import guidesigner.control.Controller;

public class MenuExtensionGUIDesignerBasisSwingMAIN extends Extension {


	@Override
	public String getName() {
		return "GUIDesignerBasisSwing";
	}

	@Override
	public String getVersion() {
		return "V1";
	}
	@Override
	public boolean isCompatible() {
		return true;
	}

	@Override
	public void startup(BlueJ bluej) {		
		MenueSimpleGUIDesignerFenster myMenus = new MenueSimpleGUIDesignerFenster(bluej);
		bluej.setMenuGenerator(myMenus);
	}

	public static void main(String[] s) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Controller.main(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
