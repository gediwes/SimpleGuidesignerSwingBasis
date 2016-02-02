package guidesigner.oberflaeche;

import java.awt.Point;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DropMode;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import guidesigner.control.Controller;
import guidesigner.eigenschaften.komponenten.KomponentenBasisInfo;
import guidesigner.eigenschaften.komponenten.KomponentenGrundInfo;


public class KomponentenBaum extends JTree {
	JTree baum;
	DefaultMutableTreeNode wurzel;
	DefaultTreeModel treeModel;
	private DesignerFenster kenntDesignFenster;

	class DropHandler extends DropTargetAdapter {
		@Override
		public void drop(DropTargetDropEvent dtde) {

			TreePath selectionPath = baum.getSelectionPath();
			TreePath sourcePath = selectionPath.getParentPath();

			DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectionPath
					.getLastPathComponent();

			Point dropLocation = dtde.getLocation();
			TreePath targetPath = baum.getClosestPathForLocation(
					dropLocation.x, dropLocation.y);

			// System.out.println("###################");
			// System.out.println("selectionPath: " + selectionPath);
			// System.out.println("srcPath: " + sourcePath);
			// System.out.println("targetPath: " + targetPath);
			// System.out.println("selectedNode: " + selectedNode);
			DefaultMutableTreeNode targetParentNode = (DefaultMutableTreeNode) targetPath
					.getLastPathComponent();
			if (isDropAllowed(sourcePath, targetPath, selectionPath,
					selectedNode, targetParentNode)) {
				// System.out.println("drop accept");
				DefaultMutableTreeNode sourceParentNode = (DefaultMutableTreeNode) sourcePath
						.getLastPathComponent();

				sourceParentNode.remove(selectedNode);
				targetParentNode.add(selectedNode);
				KomponentenBaum.this
						.ordneHierarchieUm(
								(KomponentenBasisInfo) sourceParentNode
										.getUserObject(),
								(KomponentenBasisInfo) selectedNode
										.getUserObject(),
								(KomponentenBasisInfo) targetParentNode
										.getUserObject());

				dtde.dropComplete(true);
				baum.updateUI();
			} else {
				//System.out.println("drop: reject");
				dtde.rejectDrop();
				dtde.dropComplete(false);
			}
		}

		private boolean isDropAllowed(TreePath sourcePath, TreePath targetPath,
				TreePath selectionPath, DefaultMutableTreeNode selectedNode,
				DefaultMutableTreeNode targetParentNode) {
			if (sourcePath == null) {
				return false;
			}
			if (targetPath.equals(selectionPath)) {
				return false;
			}
			if (targetPath.equals(sourcePath)) {
				return false;
			}
			if (selectedNode.isNodeDescendant(targetParentNode)) {
				return false;
			}
			return kenntDesignFenster.getController().versetzeUeberBaum((KomponentenGrundInfo) selectedNode.getUserObject(),
					(KomponentenGrundInfo) targetParentNode.getUserObject());
	
			
		}
	}

	

	public KomponentenBaum(DesignerFenster df) {
		
		kenntDesignFenster = df;
		baum = this;
		baum.setExpandsSelectedPaths(true);
		baum.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int selRow = baum.getRowForLocation(e.getX(), e.getY());
				TreePath selPath = baum.getPathForLocation(e.getX(), e.getY());
				if (selRow != -1) {					
					if (e.getClickCount() == 1 && e.getButton() == 3) {
						KomponentenBaum.this.bearbeiteRechtsKlick(selRow, selPath);
					} else if (e.getClickCount() == 1 && e.getButton() == 1) {
						KomponentenBaum.this.bearbeiteLinksKlick(selRow, selPath);
					} else if (e.getClickCount() == 2) {
						KomponentenBaum.this
								.bearbeiteDoppelKlick(selRow, selPath);
					}
				}
			}
		});
	}

	protected void bearbeiteDoppelKlick(int selRow, TreePath selPath) {
		// System.out.println("DKLick "+selPath);

	}

	protected void bearbeiteRechtsKlick(int selRow, TreePath selPath) {
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selPath
				.getLastPathComponent();
		KomponentenGrundInfo tmp = (KomponentenGrundInfo) selectedNode
				.getUserObject();
		kenntDesignFenster.aktiviereKomponentenAnfasser(tmp);
		//kenntDesignFenster.aktualisiereAuswahl(tmp);
		kenntDesignFenster.zeigeEditorZu(tmp);
		if ((tmp.getPanel()!= kenntDesignFenster.getFensterPanel())) {
			int n = JOptionPane.showConfirmDialog(baum, "<html>Komponente \""
					+ tmp.toString() + "\" l&ouml;schen?</html>","", JOptionPane.YES_NO_OPTION);
			// System.out.println("KLick  "+selPath);
			if (n == JOptionPane.OK_OPTION) {
				kenntDesignFenster.getController().loescheUeberBaum(tmp);			
			}
		}
	}
	protected void bearbeiteLinksKlick(int selRow, TreePath selPath) {
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selPath
				.getLastPathComponent();
		KomponentenBasisInfo tmp = (KomponentenBasisInfo) selectedNode
				.getUserObject();
		kenntDesignFenster.aktiviereKomponentenAnfasser(tmp);
		//kenntDesignFenster.aktualisiereAuswahl(tmp);
		kenntDesignFenster.zeigeEditorZu(tmp);

	}

	public void selektiere(KomponentenBasisInfo kvs){
		this.selektiere(wurzel,kvs);
	}
	protected boolean selektiere(DefaultMutableTreeNode w, KomponentenBasisInfo kvs){
		//rekursiv suchen und selektieren
//			DefaultMutableTreeNode merke = null;
			DefaultMutableTreeNode child;
			if (w.getUserObject() == kvs){
				baum.setSelectionPath(new TreePath(w.getPath()));
				return true;
			} else {
			for (int i = 0; i < w.getChildCount(); ++i) {
				child = (DefaultMutableTreeNode) w.getChildAt(i);
				if (selektiere(child,kvs)) {return true;}
			}
			return false;			
		}		
		
	}
	
	

	public void renoviereBaum(KomponentenBasisInfo f) {
		wurzel = new DefaultMutableTreeNode(f);
		treeModel = new DefaultTreeModel(wurzel);
		for (KomponentenBasisInfo k:f.getEingebetteteKomponenten()) {
			if(kenntDesignFenster.getController().getGuiTyp()!=Controller.BASISGUISCHLEIFE){
				if (!k.getKomponentenTyp().endsWith("Maus") &&!k.getKomponentenTyp().endsWith("Tastatur")){
					ergaenzeBaum(wurzel,k);
				}
			} else 
			{
			   ergaenzeBaum(wurzel,k);
			}
		}
		baum.setModel(treeModel);
		// baum.setEditable(true);
		baum.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		baum.setShowsRootHandles(true);
	}

	protected void ergaenzeBaum(DefaultMutableTreeNode knoten, KomponentenBasisInfo kinfo) {
		DefaultMutableTreeNode neu = new DefaultMutableTreeNode(kinfo);
		knoten.add(neu);
		for (KomponentenBasisInfo k:kinfo.getEingebetteteKomponenten()) {
			if(kenntDesignFenster.getController().getGuiTyp()!=Controller.BASISGUISCHLEIFE){
				if (!k.getKomponentenTyp().endsWith("Maus") &&!k.getKomponentenTyp().endsWith("Tastatur")){
					ergaenzeBaum(neu,k);
				}
			} else 
			{
			   ergaenzeBaum(neu,k);
			}
			
		}
	}
	
	public void zeigeAlles() {
		int row = 0;
		while (row < baum.getRowCount()) {
			baum.expandRow(row);
			row++;
		}
	}

	public void zeigeBisBlatt() {
		DefaultMutableTreeNode root;
		root = (DefaultMutableTreeNode) baum.getModel().getRoot();
		baum.scrollPathToVisible(new TreePath(root.getLastLeaf().getPath()));
	}

	public void fuegeInWurzelEin(Object o) {
		// this.collapseAll((JTree) bContainerHierarchie.getSwingComponent());
		// baum.setEditable(true);
		wurzel.add(new DefaultMutableTreeNode(o));
		treeModel.reload();
		this.zeigeAlles();
	}

	public void falteZusammen() {
		int row = 0;
		while (row < baum.getRowCount()) {
			baum.collapseRow(row);
			row++;
		}
	}

	/** Löscht den aktuellen Knoten */
	public void entferneAktuellenKnoten() {
		TreePath gewaehlt = baum.getSelectionPath();
		if (gewaehlt != null) {
			DefaultMutableTreeNode aktuellerKnoten = (DefaultMutableTreeNode) (gewaehlt
					.getLastPathComponent());
			MutableTreeNode vaterKnoten = (MutableTreeNode) (aktuellerKnoten
					.getParent());
			if (vaterKnoten != null) {
				treeModel.removeNodeFromParent(aktuellerKnoten);
				return;
			}
		}
		treeModel.reload();
	}

	/** Fügt Knoten an den aktuellen Knoten an */
	public void fuegeKnotenAn(Object inhaltNachfolger) {
		DefaultMutableTreeNode vaterKnoten = null;
		DefaultMutableTreeNode nachfolgeKnoten = new DefaultMutableTreeNode(
				inhaltNachfolger);
		TreePath pfadZurWurzel = baum.getSelectionPath();
		if (pfadZurWurzel == null) {
			vaterKnoten = wurzel;
		} else {
			vaterKnoten = (DefaultMutableTreeNode) (pfadZurWurzel
					.getLastPathComponent());
		}
		// if (vaterKnoten == null) {
		// vaterKnoten = wurzel;
		// }
		treeModel.insertNodeInto(nachfolgeKnoten, vaterKnoten, vaterKnoten
				.getChildCount());
		baum.scrollPathToVisible(new TreePath(nachfolgeKnoten.getPath()));
	}

	public void fuegeKnotenAn(DefaultMutableTreeNode vaterKnoten,
			Object inhaltNachfolger) {
		DefaultMutableTreeNode nachfolgeKnoten = new DefaultMutableTreeNode(
				inhaltNachfolger);
		if (vaterKnoten == null) {
			vaterKnoten = wurzel;
		}
		treeModel.insertNodeInto(nachfolgeKnoten, vaterKnoten, vaterKnoten
				.getChildCount());
		baum.scrollPathToVisible(new TreePath(nachfolgeKnoten.getPath()));
	}

	public void setzeDragNDrop() {
		baum.setDragEnabled(true);
		baum.setDropMode(DropMode.USE_SELECTION);
		baum.setDropTarget(new DropTarget(baum, TransferHandler.MOVE,
				new DropHandler()));
	}

	public void ordneHierarchieUm(KomponentenBasisInfo alterContainer,
			KomponentenBasisInfo verschobeneKomponente,
			KomponentenBasisInfo neuerContainer) {
		kenntDesignFenster.getController().verschiebeKomponenteVonNach(verschobeneKomponente,alterContainer,neuerContainer);
//		alterContainer.entferneNachfolger(verschobeneKomponente);		
//		neuerContainer.fuegeNachfolgerEin(verschobeneKomponente);
//		verschobeneKomponente.setVorgaengerUndBetteEin(neuerContainer);
		// verschobeneKomponente.getK().betteEinIn(neuerContainer.getK());
	}

	public DefaultMutableTreeNode getKnoten(KomponentenBasisInfo komponentenInfo) {
		return getKnoten(wurzel, komponentenInfo);
	}

	public DefaultMutableTreeNode getKnoten(DefaultMutableTreeNode w,
			KomponentenBasisInfo komponentenInfo) {
		DefaultMutableTreeNode merke = null;
		DefaultMutableTreeNode child;
		for (int i = 0; i < w.getChildCount(); ++i) {
			child = (DefaultMutableTreeNode) w.getChildAt(i);
			if (child.getUserObject() == komponentenInfo) {
				merke = child;
				break;
			}
			merke = this.getKnoten(child, komponentenInfo);
			if (merke != null) {
				break;
			}
		}

		return merke;
	}

}
