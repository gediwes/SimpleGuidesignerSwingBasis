/**
 * 
 */
package guidesigner.control;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.w3c.dom.Element;

import javax.swing.JLayeredPane;

import blueJLink.BlueJInterface;
import guidesigner.eigenschaften.GrundEigenschaft;
import guidesigner.eigenschaften.ereignisse.LauscherEigenschaft;
import guidesigner.eigenschaften.komponenten.JFrameInfo;
import guidesigner.eigenschaften.komponenten.KomponentenBasisInfo;
import guidesigner.eigenschaften.komponenten.KomponentenEigenschaft;
import guidesigner.oberflaeche.DesignerFenster;
import guidesigner.oberflaeche.KomponentenEditor;
import guidesigner.oberflaeche.MovePanel;

/**
 * @author Georg Dick
 * 
 */
public class Controller implements ComponentListener {
	private Vector<KomponentenBasisInfo> komponenten = new Vector<KomponentenBasisInfo>();
	Vector<KomponentenEditor> komponenteneditor = new Vector<KomponentenEditor>();
	public static final int SWINGGUI = 1;
	public static final int BASISGUILAUSCHER = 2;
	public static final int BASISGUISCHLEIFE = 4;
	DesignerFenster gui;
	private BlueJInterface blueJInterface;
	private int guiTyp = Controller.BASISGUILAUSCHER; 
   public boolean isSwing(){
	   return this.guiTyp == Controller.SWINGGUI;
   }
	
   public void setVisibleMausUndTastaturInGUI(boolean visible){
	   for (KomponentenBasisInfo kbi:getKomponenten()){
		   if (kbi.getKomponentenTyp().endsWith("Maus")||
				   kbi.getKomponentenTyp().endsWith("Tastatur")){
			   JPanel panel=kbi.getPanel();
			   if (panel!=null){
				   panel.setVisible(visible);
				   if (!visible){gui.entferneAusAuswahlliste(kbi);}
				   else {
					  if( gui.getModEintraegeAuswahlliste().getIndexOf(kbi)<0)
					   gui.getModEintraegeAuswahlliste().addElement(kbi);
				   }
				   
			   }
			   
		   }
			   
	   }
	   
   }
   
   
	/**
	 * 
	 */
	public Controller(BlueJInterface bjiface) {
		this.guiTyp=Controller.BASISGUISCHLEIFE;
		this.blueJInterface = bjiface;
		gui = new DesignerFenster(this);
		gui.erzeugeFenster(this);
		if (guiTyp==Controller.SWINGGUI) {
			this.stelleGUIAufSwing();
		} else if (guiTyp==Controller.BASISGUILAUSCHER) {
			this.stelleGUIAufBasis();
		} else {
			this.stelleGUIAufBasisMitSchleife();
		}	
		gui.setVisible(true);
		//gui.test();
		gui.revalidate();
//		gui.zeigeEditor(
//				getEditorZuKomponentenInfo(getKomponentenInfo(gui
//				.getFensterPanel())));
		
		
	}

	/**
	 * @return liefert blueJInterface
	 */
	public BlueJInterface getBlueJInterface() {
		return blueJInterface;
	}

	public KomponentenBasisInfo registriereKomponente(JPanel mp,
			String komponententyp, String vname, JPanel umgebendeKomponente) {
		if (getKomponentenInfo(mp) != null) {
			return null;
		}
		KomponentenBasisInfo neu = Komponentenerzeuger
				.erzeugeKomponentenInfo(mp, komponententyp, vname);
		getKomponenten().add(neu);
		KomponentenEditor kedi = new KomponentenEditor(neu, this, this.guiTyp);
		komponenteneditor.add(kedi);
		mp.addComponentListener(this);
		if (mp instanceof MovePanel) {
			((MovePanel) mp).getTransparenzSchichtBewegung().addMouseListener(
					gui);

			KomponentenBasisInfo parentInfo = getKomponentenInfo(umgebendeKomponente);
			if (parentInfo != null) {
				parentInfo.addEingebetteteKomponente(neu);
			}
		}
		// Baum aktualisieren
		// Auswahlliste aktualisieren

		if (gui != null) {
			gui.getModEintraegeAuswahlliste().addElement(neu);
			gui.aktualisiereAuswahl();
			gui.aktualisiereBaum(neu);
		}
		return neu;
	}

	public void entferneKomponente(KomponentenBasisInfo weg) {
		if (weg == null) {
			return;
		}
		if (weg.getPanel() == gui.getFensterPanel()) {
			return;
		}
		// Fenstereditor sichtbar machen
		gui.zeigeEditor(getEditorZuKomponentenInfo(getKomponentenInfo(gui
				.getFensterPanel())));
		// Alle eingebetteten Komponenten entfernen (rekursiver Aufruf!)
		while (!weg.getEingebetteteKomponenten().isEmpty()) {
			entferneKomponente(weg.getEingebetteteKomponenten().firstElement());
			// System.out.println("Eingebettet weg");
		}
		((MovePanel) weg.getPanel()).entferneAnfasser();
		Container parent = weg.getPanel().getParent();// ist die
														// Transparentschicht
		// Aus dem parent-Container entfernen
		parent.remove(weg.getPanel());
		// Component-Mouse-Listener entfernen
		ComponentListener[] cl = weg.getPanel().getComponentListeners();
		for (ComponentListener col : cl) {
			weg.getPanel().removeComponentListener(col);
		}
		JLabel tr = ((MovePanel) weg.getPanel())
				.getTransparenzSchichtBewegung();
		MouseListener[] ml = tr.getMouseListeners();
		for (MouseListener mol : ml) {
			tr.removeMouseListener(mol);
		}
		// aus Vektoren entfernen
		KomponentenEditor kedi = getKomponentenEditor(weg);
		getKomponenten().remove(weg);
		// aus KomponentenInfo des Containers entfernen
		JPanel pan;
		if (parent instanceof JLabel) {

			pan = (JPanel) ((JLayeredPane) ((JLabel) parent).getParent())
					.getParent();
		} else {
			// Fenster ist Container
			pan = gui.getFensterPanel();
		}

		getKomponentenInfo(pan).getEingebetteteKomponenten().remove(weg);
		// editor entfernen
		komponenteneditor.remove(kedi);
		// Baum aktualisieren
		gui.aktualisiereBaum();
		// Auswahlbox aktualisieren
		gui.entferneAusAuswahlliste(weg);
		gui.revalidate();
	}
	
	public boolean aendereEigenschaftUeberEditor(KomponentenBasisInfo ki,
			KomponentenEigenschaft eg, Object[] setterWerte) {
		return ki.aendereEigenschaft(eg, setterWerte);
	}

	
	public boolean aendereLauscherUeberEditor(KomponentenBasisInfo ki,
			LauscherEigenschaft la, boolean aktiv) {
		return false;
	}


	public boolean aendereEigenschaftUeberMovePanel(KomponentenBasisInfo ki,
			KomponentenEigenschaft eg, Object[] setterWerte) {
		ki.aendereEigenschaft(eg, setterWerte);
		return true;
	}

	public boolean aendereEigenschaftUeberBaum(KomponentenBasisInfo ki,
			KomponentenEigenschaft eg, Object[] setterWerte) {
		ki.aendereEigenschaft(eg, setterWerte);
		gui.zeigeEditor(getEditorZuKomponentenInfo(ki));
		return true;
	}

//	/**
//	 * @param zielKomponentenInfo
//	 * @param eg
//	 * @param merkeColor
//	 */
//	public boolean aendereEigenschaftUeberEditor(
//			KomponentenBasisInfo zielKomponentenInfo, KomponentenEigenschaft eg,
//			Object neuWert) {
////		Object[] neu = new Object[1];
////		neu[0] = neuWert;
//		this.aendereEigenschaftUeberEditor(zielKomponentenInfo, eg, neuWert);
//		return false;
//	}

	/**
	 * @param zielKomponentenInfo
	 * @param text
	 */
	public boolean aendereVariablennameUeberEditor(
			KomponentenBasisInfo zielKomponentenInfo, String name) {
		if (name.equals(zielKomponentenInfo.getVariablenName())) {
			return true;
		}
		String okMessage = nameOK(name);
		if (okMessage.equals("")) {
			zielKomponentenInfo.setVariablenName(name);
			gui.aktualisiereBaum(zielKomponentenInfo);
			gui.aktualisiereAuswahl(zielKomponentenInfo);
			return true;
		} else {
			getKomponentenEditor(zielKomponentenInfo).aktualisiereName(
					zielKomponentenInfo.getVariablenName());
			JOptionPane.showMessageDialog(gui, "Der Variablenname \"" + name
					+ "\" ist unzulässig:\n" + okMessage);
			return false;
		}
	}

	static String[] javaKeysMitDatentypen = { "abstract", "assert", "boolean",
			"break", "byte", "case", "catch", "char", "class", "const",
			"continue", "default", "do", "double", "else", "enum", "extends",
			"false", "final", "finally", "float", "for", "goto", "if",
			"implements", "import", "instanceof", "int", "interface", "long",
			"native", "new", "null", "package", "private", "protected",
			"public", "return", "short", "static", "strictfp", "super",
			"switch", "synchronized", "this", "throw", "throws", "transient",
			"true", "try", "void", "volatile", "while" };

	/**
	 * @param name
	 * @param variablenName
	 * @return
	 */
	private String nameOK(String name) {
		if (nameVorhanden(name)) {
			return "Er ist bereits vorhanden.";
		}
		for (int i = 0; i < javaKeysMitDatentypen.length; i++) {
			if (name.equals(javaKeysMitDatentypen[i])) {
				return "Er ist ein reserviertes Schlüsselwort.";
			}
		}
		for (int i = 0; i < name.length(); i++) {
			if (!erlaubtesZeichen(name.charAt(i), i)) {
				return "Er enthält ein unzulässiges Zeichen: '"
						+ name.charAt(i) + "'.";
			}
		}
		return "";
	}

	private boolean erlaubtesZeichen(char c, int anStelle) {
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')
				|| (c >= '0' && c <= '9' && anStelle > 0) || (c == '_');
	}

	public boolean nameVorhanden(String name) {
		for (KomponentenBasisInfo ki : getKomponenten()) {
			if (ki.getVariablenName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see java.awt.event.ComponentListener#componentHidden(java.awt.event.ComponentEvent)
	 */
	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * @see java.awt.event.ComponentListener#componentMoved(java.awt.event.ComponentEvent)
	 */
	@Override
	public void componentMoved(ComponentEvent e) {
		if (e.getSource() instanceof MovePanel) {
			KomponentenBasisInfo kinfo = getKomponentenInfo((MovePanel) e
					.getSource());
			GrundEigenschaft eg = kinfo.getEigenschaft("Position");
			Point p = ((MovePanel) e.getSource()).getLocation();
			eg.setWert(new Object[]{p.x,p.y});
			aktualisiereEditor(kinfo, kinfo.getEigenschaft("Position"));
		}
	}

	/**
	 * @param kinfo
	 * @param eigenschaft
	 */
	private void aktualisiereEditor(KomponentenBasisInfo kinfo,
			GrundEigenschaft eigenschaft) {
		KomponentenEditor edi = getKomponentenEditor(kinfo);
		edi.aktualisiereEigenschaft(eigenschaft);
	}

	/**
	 * @param source
	 * @return
	 */
	public KomponentenBasisInfo getKomponentenInfo(JPanel source) {
		for (KomponentenBasisInfo ki : getKomponenten()) {
			if (ki.getPanel() == source) {
				return ki;
			}
		}
		return null;
	}

	/**
	 * @see java.awt.event.ComponentListener#componentResized(java.awt.event.ComponentEvent)
	 */
	@Override
	public void componentResized(ComponentEvent e) {
		if (e.getSource() instanceof MovePanel) {
			KomponentenBasisInfo kinfo = getKomponentenInfo((MovePanel) e
					.getSource());
			GrundEigenschaft eg = kinfo.getEigenschaft("Größe");
			Dimension p = ((MovePanel) e.getSource()).getSize();
			eg.setWert(new Object[]{p.width, p.height});
			

			// Object[] sp = new Object[2];
			// sp[0] = p.width;
			// sp[1] = p.height;
			// eg.setSetterParameterwerte(sp);
			aktualisiereEditor(kinfo, kinfo.getEigenschaft("Größe"));
		}
	}

	/**
	 * @see java.awt.event.ComponentListener#componentShown(java.awt.event.ComponentEvent)
	 */
	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param bewegPanel
	 */
	public void aktiviere(MovePanel bewegPanel) {
		if (getKomponentenInfo(bewegPanel) == null) {
			return;
		}
		bewegPanel.aktiviereAnfasser();
		KomponentenEditor edi = getKomponentenEditor(getKomponentenInfo(bewegPanel));
		gui.zeigeEditor(edi.getEditor());
	}

	/**
	 * @param komponentenInfo
	 * @return
	 */
	private KomponentenEditor getKomponentenEditor(
			KomponentenBasisInfo komponentenInfo) {
		for (KomponentenEditor edi : komponenteneditor) {
			if (edi.komponentenInfo() == komponentenInfo) {
				return edi;
			}
		}
		return null;
	}

	public static void main(String[] s) {
		new Controller(null);
	}

	/**
	 * 
	 * @param komponentenTyp
	 * @param string
	 */
	public boolean passtKomponentenTypInKomponentenTyp(String komponentenTyp,
			String umgebung) {
		// TODO Test ob umgebende Komponente nur eine Komponente aufnehmen kann
		// und diese schon belegt ist
		
		
		
		
		return Komponentenerzeuger.passtSwingKomponenteInKomponente(
				komponentenTyp, umgebung);

	}

	/**
	 * @param panel
	 */
	public void aktiviereAnzeigeUeberMovePanel(JPanel panel) {
		if (getKomponentenInfo(panel) == null) {
			return;
		}
		KomponentenEditor edi = getKomponentenEditor(getKomponentenInfo(panel));
		gui.zeigeEditor(edi.getEditor());
		gui.zeigeImBaum(getKomponentenInfo(panel));
		gui.waehleAusAnzeige(getKomponentenInfo(panel));
	}

	/**
	 * @param kinfo
	 * @return
	 */
	public JScrollPane getEditorZuKomponentenInfo(KomponentenBasisInfo kinfo) {
		for (KomponentenEditor kedi : komponenteneditor) {
			if (kedi.komponentenInfo() == kinfo) {
				return kedi.getEditor();
			}
		}
		return null;
	}

	/**
	 * @param movePanel
	 */
	public void reagiereAufAnfasserAktivierungVon(MovePanel movePanel) {
		aktiviereAnzeigeUeberMovePanel(movePanel);
		// TODO Andere deaktivieren gegebenenfalls Anzeigen von Liste, Editor
		// und Baum anpassen

	}

	/**
	 * @param movePanel
	 */
	public void reagiereAufMousePressedVon(MovePanel movePanel) {
		aktiviereAnzeigeUeberMovePanel(movePanel);
		gui.deaktiviereAnfasserFuerAlleAusser(movePanel);

	}

	/**
	 * @param tmp
	 */
	public void loescheUeberBaum(KomponentenBasisInfo weg) {
		this.entferneKomponente(weg);

	}

	/**
	 * @param userObject
	 * @param object
	 * @return
	 */
	public boolean versetzeUeberBaum(KomponentenBasisInfo moveKomponente,
			KomponentenBasisInfo umgebung) {
		
		if (umgebung.getKomponentenTyp().endsWith("JScrollPane")||
				umgebung.getKomponentenTyp().endsWith("ScrollFlaeche")){
			if (!umgebung.getEingebetteteKomponenten().isEmpty()){
				JOptionPane
				.showMessageDialog(
						this.getGUI(),
						"In der "+umgebung.getKomponentenTyp()+" ist bereits eine Komponente enthalten\n"
								+ "Diese muss erst entfernt werden, bevor eine andere eingebettet werden kann");
				return false;
			}
			
		}
				
		boolean passt= passtKomponentenTypInKomponentenTyp(
				moveKomponente.getKomponentenTyp(),
				umgebung.getKomponentenTyp());
		if (!passt){
		JOptionPane.showMessageDialog(this.getGUI(), moveKomponente.getKomponentenTyp()
				+ " kann nicht in " + umgebung.getKomponentenTyp()
				+ " eingebettet werden");}
		return passt;
		
	}

	/**
	 * @param verschobeneKomponente
	 * @param alterContainer
	 * @param neuerContainer
	 */
	public void verschiebeKomponenteVonNach(
			KomponentenBasisInfo verschobeneKomponente,
			KomponentenBasisInfo alterContainer,
			KomponentenBasisInfo neuerContainer) {
		alterContainer.removeEingebetteteKomponente(verschobeneKomponente);
		neuerContainer.addEingebetteteKomponente(verschobeneKomponente);
		// verschobeneKomponente.getPanel().getParent().remove(verschobeneKomponente.getPanel());
		// unnötig geschieht automatisch
		MovePanel mp = (MovePanel) verschobeneKomponente.getPanel();
		mp.deaktiviereAnfasser();
		if (neuerContainer.getPanel() == gui.getFensterPanel()) {
			gui.getFensterPanel().add(verschobeneKomponente.getPanel());
		} else {
			((MovePanel) neuerContainer.getPanel())
					.getTransparenzSchichtBewegung().add(mp);
		}
		mp.versetzeAnfasser();
		GrundEigenschaft eg = verschobeneKomponente.getEigenschaft("Position");
		mp.setLocation(10, 10);
		eg.setWert(new Object[]{10, 10});
		getKomponentenEditor(verschobeneKomponente).aktualisiereEigenschaft(eg);
		gui.zeigeEditor(getEditorZuKomponentenInfo(verschobeneKomponente));
		gui.revalidate();
	}

	public void entferneOberflaeche() {
		KomponentenBasisInfo kfenster = getKomponentenInfo(gui
				.getFensterPanel());
		while (!kfenster.getEingebetteteKomponenten().isEmpty()) {
			entferneKomponente(kfenster.getEingebetteteKomponenten()
					.firstElement());
		}			
		if (kfenster instanceof JFrameInfo){
			JFrameInfo jinfo = (JFrameInfo)kfenster;
		    this.aendereVariablennameUeberEditor(jinfo,"fenster");
			getKomponentenEditor(jinfo).aktualisiereName("fenster");
			for (KomponentenEigenschaft eg:jinfo.getKomponentenEigenschaften()){
				if (eg.getEigenschaftsName().equals("Größe")) {
					jinfo.aendereEigenschaft(eg, new Integer[]{600,500});					
				}
				if (eg.getEigenschaftsName().equals("Hintergrundfarbe")) {
					jinfo.aendereEigenschaft(eg, new Color[]{Color.WHITE});
				}

				if (eg.getEigenschaftsName().equals("Titel")) {
					jinfo.aendereEigenschaft(eg,new String[]{"Titel"});
				}				
			}
			
			getKomponentenEditor(jinfo).aktualisiereAlleEigenschaften();
			synchronisiereEigenschaftFuerPanel(jinfo);
			jinfo.getPanel().revalidate();
		}
		
	}

	/**
	 * @param kfenster
	 * @param istSwing TODO
	 */
	public void erzeugeNeueOberflaeche(KomponentenInfoGrundeigenschaften kfenster, int guiTyp ) {
		entferneOberflaeche();
		if (guiTyp==Controller.SWINGGUI) {
			this.stelleGUIAufSwing();
		} else if (guiTyp==Controller.BASISGUILAUSCHER) {
			this.stelleGUIAufBasis();
		} else {
			this.stelleGUIAufBasisMitSchleife();
		}		
		// KomponentenInfo fuer Fenster aktualisieren;
		
		// TODO 
		//nur für einen Test
		// this.registriereKomponente(gui.getFensterPanel(),"javax.swing.JFrame","fenster",null);
		
		KomponentenBasisInfo kfensterinfo = getKomponentenInfo(gui
				.getFensterPanel());	
		
		for (GrundEigenschaft e : kfenster.eigenschaften) {	
			kfensterinfo.updateEigenschaft(e);
		}
		getKomponentenEditor(kfensterinfo).aktualisiereAlleEigenschaften();
		synchronisiereEigenschaftFuerPanel(kfensterinfo);
		kfensterinfo.getPanel().getParent().setPreferredSize(kfensterinfo.getPanel().getSize());
		kfensterinfo.getPanel().revalidate();

		
		
		for (KomponentenInfoGrundeigenschaften kginfoembedded : kfenster.eingebetteteKomponenten) {
			erzeugeNeueKomponente(kginfoembedded, gui.getFensterPanel());
		}
		gui.revalidate();

	}

	public void erzeugeNeueKomponente(KomponentenInfoGrundeigenschaften kginfo,
			JPanel umgebendeKomponente) {
		JPanel neupanel = gui.erzeugeKomponenteOhneRegistrierung(
				kginfo.komponentenTyp, 0, 0, umgebendeKomponente);
		KomponentenBasisInfo kinfo = registriereKomponente(neupanel,
				kginfo.komponentenTyp, kginfo.getVariablenName(),
				umgebendeKomponente);
		for (GrundEigenschaft e : kginfo.eigenschaften) {
			kinfo.updateEigenschaft(e);
		}
		getKomponentenEditor(kinfo).aktualisiereAlleEigenschaften();
		synchronisiereEigenschaftFuerPanel(kinfo);
		kinfo.getPanel().revalidate();

		for (KomponentenInfoGrundeigenschaften kginfoembedded : kginfo.eingebetteteKomponenten) {
			erzeugeNeueKomponente(kginfoembedded, neupanel);
		}

	}

	/**
	 * @param kinfo
	 */
	private void synchronisiereEigenschaftFuerPanel(KomponentenBasisInfo kinfo) {

		for (GrundEigenschaft e : kinfo.getEigenschaften()) {
			if (e instanceof KomponentenEigenschaft){
			this.aendereEigenschaftUeberEditor(kinfo, ((KomponentenEigenschaft)e),
					((KomponentenEigenschaft)e).getWertAlsArray());
			}
		}

	}

	/**
	 * @return
	 */
	public DesignerFenster getGUI() {
		return gui;
	}

	/**
	 * @param komponententyp
	 * @param typUmgebendeKomponente
	 * @param umgebendeKomponente
	 * @return
	 */
	public boolean passtKomponentenTypInJScrollPaneFallsDiesDerUmgebendeTypIst(String komponententyp,
			String typUmgebendeKomponente, JPanel umgebendeKomponente) {
		if (komponententyp.endsWith("Maus")){
			return true;
		}
		return Komponentenerzeuger.passtKomponentenTypInJScrollPaneFallsDiesDerUmgebendeTypIst(this,
				komponententyp, typUmgebendeKomponente, umgebendeKomponente);
	}

	/**
	 * 
	 */
	public void stelleGUIAufSwing() {
		this.guiTyp=Controller.SWINGGUI;
		gui.setzeKomponentenauswahlAuf(this.guiTyp);
		gui.setzeToggleTexteBasisOderSwing(true);
		gui.getInternalFrame().setTitle(ZuordungBasisSwing.getUebersetzungSwingBasis("javax.swing.JFrame",true));
		gui.setCheckBoxSwingBasisBasis(guiTyp);
		for (KomponentenBasisInfo ke :getKomponenten()) {
			String komptyp = ZuordungBasisSwing.getUebersetzungBasisSwing(ke.getKomponentenTyp(),true);
			ke.setKomponentenTyp(komptyp);
//			System.out.println("Swing: "+komptyp);
		}
		for (KomponentenEditor ke :komponenteneditor) {
			ke.setzeSwing(this.guiTyp);
		}
		setVisibleMausUndTastaturInGUI(false);
		gui.aktualisiereBaum();
		gui.aktualisiereAuswahl(getKomponenten().firstElement());
	
	}

	/**
	 * 
	 */
	public void stelleGUIAufBasis() {
		this.guiTyp=Controller.BASISGUILAUSCHER;
		gui.setzeKomponentenauswahlAuf(this.guiTyp);
		gui.setzeToggleTexteBasisOderSwing(false);
		gui.setCheckBoxSwingBasisBasis(guiTyp);
		gui.getInternalFrame().setTitle(ZuordungBasisSwing.getUebersetzungSwingBasis("javax.swing.JFrame",false));		
		for (KomponentenBasisInfo ke :getKomponenten()) {
			String komptyp = ZuordungBasisSwing.getUebersetzungSwingBasis(ke.getKomponentenTyp(),false);
			ke.setKomponentenTyp(komptyp);
			//System.out.println("Basis: "+komptyp);
		}
		for (KomponentenEditor ke :komponenteneditor) {
			ke.setzeSwing(this.guiTyp);
		}
		setVisibleMausUndTastaturInGUI(false);
		gui.aktualisiereBaum();
		gui.aktualisiereAuswahl(getKomponenten().firstElement());
	}
	public void stelleGUIAufBasisMitSchleife() {
		this.guiTyp=Controller.BASISGUISCHLEIFE;
		gui.setzeKomponentenauswahlAuf(this.guiTyp);
		gui.setzeToggleTexteBasisOderSwing(false);
		gui.setCheckBoxSwingBasisBasis(guiTyp);
		gui.getInternalFrame().setTitle(ZuordungBasisSwing.getUebersetzungSwingBasis("javax.swing.JFrame",false));		
		for (KomponentenBasisInfo ke :getKomponenten()) {
			String komptyp = ZuordungBasisSwing.getUebersetzungSwingBasis(ke.getKomponentenTyp(),false);
			ke.setKomponentenTyp(komptyp);
			//System.out.println("Basis: "+komptyp);
		}
		for (KomponentenEditor ke :komponenteneditor) {
			ke.setzeSwing(this.guiTyp);
		}
		setVisibleMausUndTastaturInGUI(true);
		gui.aktualisiereBaum();
		gui.aktualisiereAuswahl(getKomponenten().firstElement());
	}
	/**
	 * @return the guiTyp
	 */
	public int getGuiTyp() {
		return guiTyp;
	}

	/**
	 * @param guiTyp the guiTyp to set
	 */
	public void setGuiTyp(int guiTyp) {
		this.guiTyp = guiTyp;
	}

	public Vector<KomponentenBasisInfo> getKomponenten() {
		return komponenten;
	}

	public void setKomponenten(Vector<KomponentenBasisInfo> komponenten) {
		this.komponenten = komponenten;
	}

	

}
