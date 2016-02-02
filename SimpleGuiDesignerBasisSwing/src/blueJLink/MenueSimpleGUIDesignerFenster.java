package blueJLink;

import bluej.extensions.*;
import guidesigner.control.Controller;

import javax.swing.*;

import java.awt.event.*;
import java.io.IOException;

public class MenueSimpleGUIDesignerFenster extends MenuGenerator {
	private BPackage curPackage;
	private BClass curClass;
	private BObject curObject;
	private BlueJ bluej;

	public MenueSimpleGUIDesignerFenster(BlueJ bluej) {
		super();
		this.bluej = bluej;
	}

	public JMenuItem getToolsMenuItem(BPackage aPackage) {
		return new JMenuItem(new SimpleAction("GUI Designer für Basis und Swing"));
	}

	// public JMenuItem getClassMenuItem(BClass aClass) {
	// return new JMenuItem(new SimpleAction("Click Class", "Class menu:"));
	// }
	//
	// public JMenuItem getObjectMenuItem(BObject anObject) {
	// return new JMenuItem(new SimpleAction("Click Object", "Object menu:"));
	// }

	// These methods will be called when
	// each of the different menus are about to be invoked.
	public void notifyPostToolsMenu(BPackage bp, JMenuItem jmi) {
		curPackage = bp;
		curClass = null;
		curObject = null;
	}

	// public void notifyPostClassMenu(BClass bc, JMenuItem jmi) {
	// System.out.println("Post on Class menu");
	// curPackage = null ; curClass = bc ; curObject = null;
	// }
	//
	// public void notifyPostObjectMenu(BObject bo, JMenuItem jmi) {
	// System.out.println("Post on Object menu");
	// curPackage = null ; curClass = null ; curObject = bo;
	// }
	//
	// A utility method which pops up a dialog detailing the objects
	// involved in the current (SimpleAction) menu invocation.
	private void showCurrentStatus(String header) {
		try {
			if (curObject != null)
				curClass = curObject.getBClass();
			if (curClass != null)
				curPackage = curClass.getPackage();

			String msg = header;
			if (curPackage != null)
				msg += "\nCurrent Package = " + curPackage;
			if (curClass != null)
				msg += "\nCurrent Class = " + curClass;
			if (curObject != null)
				msg += "\nCurrent Object = " + curObject;
		} catch (Exception exc) {
		}
	}

	// The nested class that instantiates the different (simple) menus.
	class SimpleAction extends AbstractAction {
		private String msgHeader;

		public SimpleAction(String menuName) {
			putValue(AbstractAction.NAME, menuName);

		}

		public void actionPerformed(ActionEvent anEvent) {

			new Thread() {
				public void run() {
					Controller c = new Controller(new BlueJProjektVerwalter(bluej));
				}
			}.start();
		}
	}

}
