/**
 * 
 */
package guidesigner.control;

import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import basisx.swing.BufferedCanvas;
import basisx.swing.JNumberField;
import guidesigner.eigenschaften.ereignisse.basisschleife.MausBasisInfo;
import guidesigner.eigenschaften.ereignisse.basisschleife.TastaturInfo;
import guidesigner.eigenschaften.komponenten.BufferedCanvasInfo;
import guidesigner.eigenschaften.komponenten.JButtonInfo;
import guidesigner.eigenschaften.komponenten.JCheckBoxInfo;
import guidesigner.eigenschaften.komponenten.JComboBoxInfo;
import guidesigner.eigenschaften.komponenten.JFrameInfo;
import guidesigner.eigenschaften.komponenten.JLabelInfo;
import guidesigner.eigenschaften.komponenten.JListInfo;
import guidesigner.eigenschaften.komponenten.JNumberFieldInfo;
import guidesigner.eigenschaften.komponenten.JPanelInfo;
import guidesigner.eigenschaften.komponenten.JScrollBarInfo;
import guidesigner.eigenschaften.komponenten.JScrollPaneInfo;
import guidesigner.eigenschaften.komponenten.JSliderInfo;
import guidesigner.eigenschaften.komponenten.JTextAreaInfo;
import guidesigner.eigenschaften.komponenten.JTextFieldInfo;
import guidesigner.eigenschaften.komponenten.KomponentenBasisInfo;
import guidesigner.eigenschaften.komponenten.KomponentenGrundInfo;
import guidesigner.eigenschaften.komponenten.PenInfo;
import guidesigner.eigenschaften.komponenten.TurtleInfo;
import guidesigner.oberflaeche.DesignerFenster;
import guidesigner.oberflaeche.MovePanel;

/**
 * @author Georg Dick
 * 
 */
public class Komponentenerzeuger {
	private Controller controller;

	/**
	 * @param komponentenTyp
	 * @param x
	 * @param y
	 * @param umgebendeKomponente
	 */
	public JPanel erzeugeKomponenteOhneRegistrierung(DesignerFenster gui,
			Controller controller, String komptyp, int x, int y,
			JPanel umgebendeKomponente) {
		JComponent parent;
		String swinganalogkomptyp = ZuordungBasisSwing.getUebersetzungBasisSwing(
				komptyp, true);
		if (umgebendeKomponente instanceof MovePanel) {
			parent = ((MovePanel) umgebendeKomponente)
					.getTransparenzSchichtBewegung();
		} else {
			parent = gui.getFensterPanel();
		}
		MovePanel mp = new MovePanel(controller);
		mp.setBounds(x, y, 100, 50);
		JComponent neu = null;
		if (swinganalogkomptyp.equals("javax.swing.JButton")) {
			neu = new JButton("", null);
		} else if (swinganalogkomptyp.equals("javax.swing.JLabel")) {
			neu = new JLabel();
		} else if (swinganalogkomptyp.equals("javax.swing.JTextField")) {
			neu = new JTextField();
			((JTextField)neu).setEditable(false);
		} else if (swinganalogkomptyp.equals("basis.swing.JNumberField")) {
			neu = new JNumberField();
			((JNumberField)neu).setEditable(false);
		} else if (swinganalogkomptyp.equals("javax.swing.JCheckBox")) {
			neu = new JCheckBox("");
		} else if (swinganalogkomptyp.equals("javax.swing.JScrollBar")) {
			neu = new JScrollBar();
		} else if (swinganalogkomptyp.equals("javax.swing.JSlider")) {
			neu = new JSlider();
		} else if (swinganalogkomptyp.equals("javax.swing.JTextArea")) {
			neu = new JTextArea();
			((JTextArea)neu).setEditable(false);
		} else if (swinganalogkomptyp.equals("javax.swing.JScrollPane")) {
			neu = new JScrollPane();
		} else if (swinganalogkomptyp.equals("javax.swing.JComboBox")) {
			neu = new JComboBox();
		} else if (swinganalogkomptyp.equals("javax.swing.JList")) {
			neu = new JList<>();
		} else if (swinganalogkomptyp.equals("basis.swing.BufferedCanvas")) {
			neu = new BufferedCanvas();
			}else if (swinganalogkomptyp.equals("javax.swing.JPanel")) {
				neu = new JPanel();
			
		} else if (swinganalogkomptyp.equals("basis.swing.Turtle")) {
			neu = new BufferedCanvas();
			((BufferedCanvas)neu).setBufferedImage("/basis/images/Turtle.gif");
			mp.setSize(((BufferedCanvas)neu).getBufferedImage().getWidth(),((BufferedCanvas)neu).getBufferedImage().getHeight());
			mp.sperreAnfasser();
		} else if (swinganalogkomptyp.equals("basis.swing.Pen")) {
			neu = new BufferedCanvas();
			((BufferedCanvas)neu).setBufferedImage("/basis/images/Pen.gif");
			mp.setSize(((BufferedCanvas)neu).getBufferedImage().getWidth(),((BufferedCanvas)neu).getBufferedImage().getHeight());
			mp.sperreAnfasser();
		}else if (swinganalogkomptyp.equals("basis.Maus")) {
			neu = new BufferedCanvas();
			((BufferedCanvas)neu).setBufferedImage("/basis/images/Maus.gif");
			mp.setSize(((BufferedCanvas)neu).getBufferedImage().getWidth(),((BufferedCanvas)neu).getBufferedImage().getHeight());
			mp.sperreAnfasser();
		} else if (swinganalogkomptyp.equals("basis.Tastatur")) {
			neu = new BufferedCanvas();
			((BufferedCanvas)neu).setBufferedImage("/basis/images/Tastatur.gif");
			mp.setSize(((BufferedCanvas)neu).getBufferedImage().getWidth(),((BufferedCanvas)neu).getBufferedImage().getHeight());
			mp.sperreAnfasser();
		}
		if (neu != null) {
			mp.setInhaltsKomponente(neu);
			parent.add(mp);
			return mp;
		}
		return null;
	}

	public void erzeugeKomponente(DesignerFenster gui,
			Controller controller, String komptyp, int x, int y,
			JPanel umgebendeKomponente) {
		this.controller = controller;
		JComponent parent;
		MovePanel mp = new MovePanel(controller);
		mp.setVisible(false);
		mp.setBounds(x, y, 80, 30);
		JComponent neu = null;
		if (umgebendeKomponente instanceof MovePanel) {
			parent = ((MovePanel) umgebendeKomponente)
					.getTransparenzSchichtBewegung();
		} else {
			parent = gui.getFensterPanel();
		}
		String neuName = "", text;
		String swinganalogkomptyp = ZuordungBasisSwing.getUebersetzungBasisSwing(
				komptyp, true); 
		String varName="", praefix="";
		if (swinganalogkomptyp.equals("javax.swing.JButton")) {
			varName = "Button"; praefix = "b";
			if (!controller.isSwing()){
				varName = "Knopf"; praefix = "k";
			}
			text = this.erzeugeText(parent, varName,
					"Bitte Knopfaufschrift eingeben");
			if (text != null) {
				neuName = this.erzeugeNamen(parent, praefix + text,
						"Bitte Variablenbezeichner eingeben");
				if (neuName != null) {
					neu = new JButton(text, null);
				}
			}
		} else if (swinganalogkomptyp.equals("javax.swing.JLabel")) {
			varName = "Label"; praefix = "l";
			if (!controller.isSwing()){
				varName = "Beschriftungsfeld"; praefix = "bf";
			}
			text = this.erzeugeText(parent, varName, "Bitte Text eingeben");
			if (text != null) {
				neuName = this.erzeugeNamen(parent, praefix + text,
						"Bitte Variablenbezeichner eingeben");
				if (neuName != null) {
					neu = new JLabel(text);
				}
			}
		} else if (swinganalogkomptyp.equals("javax.swing.JTextField")) {
			varName = "textField"; praefix = "t";
			if (!controller.isSwing()){
				varName = "textFeld"; praefix = "t";
			}
			neuName = this.erzeugeNamen(parent, varName,
					"Bitte Variablenbezeichner eingeben");
			if (neuName != null) {
				neu = new JTextField();
				((JTextField)neu).setEditable(false);
			}
		} else if (swinganalogkomptyp.equals("basis.swing.JNumberField")) {
			varName = "numberField"; praefix = "n";
			if (!controller.isSwing()){
				varName = "zahlenfeld"; praefix = "z";
			}
			neuName = this.erzeugeNamen(parent, varName,
					"Bitte Variablenbezeichner eingeben");
			if (neuName != null) {
				neu = new JNumberField();
				((JNumberField)neu).setEditable(false);
			}
		} else if (swinganalogkomptyp.equals("javax.swing.JCheckBox")) {
			varName = "CheckBox"; praefix = "cb";
			if (!controller.isSwing()){
				varName = "WahlBox"; praefix = "wb";
			}
			text = this.erzeugeText(parent, varName, "Bitte Text eingeben");
			if (text != null) {
				neuName = this.erzeugeNamen(parent, praefix+text,
						"Bitte Variablenbezeichner eingeben");
				if (neuName != null) {
					neu = new JCheckBox(text);
				}
			}
		} else if (swinganalogkomptyp.equals("javax.swing.JScrollBar")) {
			varName = "ScrollBar"; praefix = "sc";
			if (!controller.isSwing()){
				varName = "rollbalken"; praefix = "rb";
			}
			neuName = this.erzeugeNamen(parent, varName,
					"Bitte Variablenbezeichner eingeben");
			if (neuName != null) {
				neu = new JScrollBar();
			}
		} else if (swinganalogkomptyp.equals("javax.swing.JSlider")) {
			varName = "slider"; praefix = "";
			if (!controller.isSwing()){
				varName = "regler"; praefix = "";
			}
			neuName = this.erzeugeNamen(parent, varName,
					"Bitte Variablenbezeichner eingeben");
			if (neuName != null) {
				neu = new JSlider();
			}
		} else if (swinganalogkomptyp.equals("javax.swing.JTextArea")) {
			varName = "textArea"; praefix = "";
			if (!controller.isSwing()){
				varName = "textbereich"; praefix = "";
			}
			neuName = this.erzeugeNamen(parent, varName,
					"Bitte Variablenbezeichner eingeben");
			
			if (neuName != null) {
				neu = new JTextArea();

				((JTextArea)neu).setEditable(false);
			}
		} else if (swinganalogkomptyp.equals("javax.swing.JScrollPane")) {
			varName = "scrollPane"; praefix = "";
			if (!controller.isSwing()){
				varName = "scrollflaeche"; praefix = "";
			}
			neuName = this.erzeugeNamen(parent, varName,
					"Bitte Variablenbezeichner eingeben");
			
			if (neuName != null) {
				neu = new JScrollPane();
			}
		} else if (swinganalogkomptyp.equals("javax.swing.JComboBox")) {
			varName = "comboBox"; praefix = "";
			if (!controller.isSwing()){
				varName = "listBox"; praefix = "";
			}
			neuName = this.erzeugeNamen(parent, varName,
					"Bitte Variablenbezeichner eingeben");
			
			if (neuName != null) {
				neu = new JComboBox();
				((JComboBox)neu).setModel(new DefaultComboBoxModel(new String[] {"a", "b"}));
				
				
			}
		} else if (swinganalogkomptyp.equals("javax.swing.JList")) {
			varName = "list"; praefix = "";
			if (!controller.isSwing()){
				varName = "listAuswahl"; praefix = "";
			}
			neuName = this.erzeugeNamen(parent, varName,
					"Bitte Variablenbezeichner eingeben");
			
			if (neuName != null) {
				neu = new JList();
				((JList)neu).setModel(new AbstractListModel() {
					String[] values = new String[] {"a", "b"};
					public int getSize() {
						return values.length;
					}
					public Object getElementAt(int index) {
						return values[index];
					}
				});
			}
		}
		 else if (swinganalogkomptyp.equals("javax.swing.JPanel")) {
			 varName = "panel"; praefix = "";
				if (!controller.isSwing()){
					varName = "grundflaeche"; praefix = "";
				}
				neuName = this.erzeugeNamen(parent, varName,
						"Bitte Variablenbezeichner eingeben");
				
				if (neuName != null) {
					neu = new JPanel();
				}
			}
		 else if (swinganalogkomptyp.equals("basis.swing.BufferedCanvas")) {
			 varName = "bufferedCanvas"; praefix = "";
				if (!controller.isSwing()){
					varName = "leinwand"; praefix = "";
				}
				neuName = this.erzeugeNamen(parent, varName,
						"Bitte Variablenbezeichner eingeben");
				
				if (neuName != null) {
					neu = new JPanel();
				}
			}
		 else if (swinganalogkomptyp.equals("basis.swing.Pen")) {
			 varName = "pen"; praefix = "";
				if (!controller.isSwing()){
					varName = "stift"; praefix = "";
				}
				neuName = this.erzeugeNamen(parent, varName,
						"Bitte Variablenbezeichner eingeben");
				if (neuName != null) {
					neu = new BufferedCanvas();
					((BufferedCanvas)neu).setBufferedImage("/basis/images/Pen.gif");
					mp.setSize(((BufferedCanvas)neu).getBufferedImage().getWidth(),((BufferedCanvas)neu).getBufferedImage().getHeight());
					mp.sperreAnfasser();
				}
			}
		 else if (swinganalogkomptyp.equals("basis.swing.Turtle")) {
			 varName = "turtle"; praefix = "";
				if (!controller.isSwing()){
					varName = "igelStift"; praefix = "";
				}
				neuName = this.erzeugeNamen(parent, varName,
						"Bitte Variablenbezeichner eingeben");
				if (neuName != null) {
					neu = new BufferedCanvas();
					((BufferedCanvas)neu).setBufferedImage("/basis/images/Turtle.gif");		
					mp.setSize(((BufferedCanvas)neu).getBufferedImage().getWidth(),((BufferedCanvas)neu).getBufferedImage().getHeight());
					mp.sperreAnfasser();
				}
			} else if (swinganalogkomptyp.equals("basis.Maus")) {
				 varName = "maus"; praefix = "";
					if (!controller.isSwing()){
						varName = "maus"; praefix = "";
					}
					neuName = this.erzeugeNamen(parent, varName,
							"Bitte Variablenbezeichner eingeben");
					if (neuName != null) {
						neu = new BufferedCanvas();
						((BufferedCanvas)neu).setBufferedImage("/basis/images/Maus.gif");
						mp.setSize(((BufferedCanvas)neu).getBufferedImage().getWidth(),((BufferedCanvas)neu).getBufferedImage().getHeight());
						mp.sperreAnfasser();
					}
				}
			else if (swinganalogkomptyp.equals("basis.Tastatur")) {
				 varName = "tastatur"; praefix = "";
					if (!controller.isSwing()){
						varName = "tastatur"; praefix = "";
					}
					neuName = this.erzeugeNamen(parent, varName,
							"Bitte Variablenbezeichner eingeben");
					if (neuName != null) {
						neu = new BufferedCanvas();
						((BufferedCanvas)neu).setBufferedImage("/basis/images/Tastatur.gif");
						mp.setSize(((BufferedCanvas)neu).getBufferedImage().getWidth(),((BufferedCanvas)neu).getBufferedImage().getHeight());
						mp.sperreAnfasser();
					}
				}
		if (neu != null) {
			mp.setInhaltsKomponente(neu);
			parent.add(mp);
			mp.setVisible(true);
			controller.registriereKomponente(mp, komptyp, neuName,
					umgebendeKomponente);
			controller.aktiviereAnzeigeUeberMovePanel(mp);
		} else {
			mp = null;
		}

		
	}

	public String erzeugeNamen(JComponent parent, String vorschlag,
			String dialogText) {
		// Bezeichnereigenschaft ueberpruefen
		// Leerzeichen und Sonderzeichen durch Unterstriche ersetzen
		String v = "";
		for (int i = 0; i < vorschlag.length(); i++) {
			if (erlaubtesZeichen(vorschlag.charAt(i), i)) {
				v = v + vorschlag.charAt(i);
			} else {
				if (vorschlag.charAt(i) == ' ') {
					v = v + '_';
				}
			}
		}
		vorschlag = v;

		if (controller.nameVorhanden(vorschlag) || nameIstReserviert(vorschlag)) {
			int zaehler = 0;
			while (controller.nameVorhanden(vorschlag + zaehler)) {
				zaehler++;
			}
			vorschlag = vorschlag + zaehler;
		}
		String neu = (String) JOptionPane.showInputDialog(parent, dialogText
				+ "\nBei leerer  oder unzulaessiger Eingabe: " + vorschlag,"Variablenbezeichner",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                vorschlag);
		if (controller.nameVorhanden(neu) || nameIstReserviert(neu)){
			return vorschlag;
		}
		if (neu == null) {
			return null;
		}
		if (neu.equals("")) {
			return vorschlag;
		}
		for (int i = 0; i < neu.length(); i++) {
			if (!erlaubtesZeichen(neu.charAt(i), i)) {
				return vorschlag;
			}
		}
		return neu;
	}

	public boolean nameIstReserviert(String neu) {
		for (int i = 0; i < Controller.javaKeysMitDatentypen.length; i++) {
			if (neu.equals(Controller.javaKeysMitDatentypen[i])) {
				return true;
			}
		}
		return false;
	}

	public boolean nameOK(String neu) {
		if (controller.nameVorhanden(neu)) {
			return false;
		}
		for (int i = 0; i < Controller.javaKeysMitDatentypen.length; i++) {
			if (neu.equals(Controller.javaKeysMitDatentypen[i])) {
				return false;
			}
		}
		for (int i = 0; i < neu.length(); i++) {
			if (!erlaubtesZeichen(neu.charAt(i), i)) {
				return false;
			}
		}
		return true;
	}

	private boolean erlaubtesZeichen(char c, int anStelle) {
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')
				|| (c >= '0' && c <= '9' && anStelle > 0) || (c == '_');

	}

	public String erzeugeText(JComponent parent, String vorschlag,
			String dialogText) {
		String neu = JOptionPane.showInputDialog(parent, dialogText
				+ " (Bei leerer Eingabe: " + vorschlag + " )");
		if (neu == null) {
			return null;
		}
		if (neu.equals("")) {
			return vorschlag;
		}
		return neu;
	}

	/**
	 * 
	 * @param komponentenTyp
	 * @param string
	 */
	public static boolean passtSwingKomponenteInKomponente(
			String komponentenTyp, String umgebung) {
		// TODO Test ob umgebende Komponente nur eine Komponente aufnehmen kann
		// und diese schon belegt ist

		komponentenTyp = ZuordungBasisSwing.getUebersetzungBasisSwing(komponentenTyp,
				true);
		umgebung = ZuordungBasisSwing.getUebersetzungBasisSwing(umgebung, true);
		if (komponentenTyp.equals("javax.swing.JButton")) {
			return umgebung.equals("javax.swing.JFrame")
					|| umgebung.equals("javax.swing.JLabel")
					|| umgebung.equals("javax.swing.JPanel")
					|| umgebung.equals("javax.swing.JButton")
					|| umgebung.equals("basis.swing.BufferedCanvas");
		}

		if (komponentenTyp.equals("javax.swing.JLabel")) {
			return umgebung.equals("javax.swing.JFrame")
					|| umgebung.equals("javax.swing.JLabel")
					|| umgebung.equals("javax.swing.JPanel")
					|| umgebung.equals("javax.swing.JScrollPane")
					|| umgebung.equals("javax.swing.JButton")
					|| umgebung.equals("basis.swing.BufferedCanvas");
		}

		if (komponentenTyp.equals("javax.swing.JList")) {
			return umgebung.equals("javax.swing.JFrame")
					|| umgebung.equals("javax.swing.JLabel")
					|| umgebung.equals("javax.swing.JPanel")
					|| umgebung.equals("javax.swing.JScrollPane")
					|| umgebung.equals("javax.swing.JButton")
					|| umgebung.equals("basis.swing.BufferedCanvas");
		}

		if (komponentenTyp.equals("javax.swing.JComboBox")) {
			return umgebung.equals("javax.swing.JFrame")
					|| umgebung.equals("javax.swing.JLabel")
					|| umgebung.equals("javax.swing.JPanel")
					|| umgebung.equals("javax.swing.JScrollPane")
					|| umgebung.equals("javax.swing.JButton")
					|| umgebung.equals("basis.swing.BufferedCanvas");
		}

		if (komponentenTyp.equals("javax.swing.JCheckBox")) {
			return umgebung.equals("javax.swing.JFrame")
					|| umgebung.equals("javax.swing.JLabel")
					|| umgebung.equals("javax.swing.JPanel")
					|| umgebung.equals("javax.swing.JButton")
					|| umgebung.equals("basis.swing.BufferedCanvas");
		}

		if (komponentenTyp.equals("javax.swing.JTextArea")) {
			return umgebung.equals("javax.swing.JFrame")
					|| umgebung.equals("javax.swing.JLabel")
					|| umgebung.equals("javax.swing.JPanel")
					|| umgebung.equals("javax.swing.JScrollPane")
					|| umgebung.equals("javax.swing.JButton")
					|| umgebung.equals("basis.swing.BufferedCanvas");
		}

		if (komponentenTyp.equals("javax.swing.JTextField")) {
			return umgebung.equals("javax.swing.JFrame")
					|| umgebung.equals("javax.swing.JLabel")
					|| umgebung.equals("javax.swing.JPanel")
					|| umgebung.equals("javax.swing.JScrollPane")
					|| umgebung.equals("javax.swing.JButton")
					|| umgebung.equals("basis.swing.BufferedCanvas");
		}

		if (komponentenTyp.equals("basis.swing.JNumberField")) {
			return umgebung.equals("javax.swing.JFrame")
					|| umgebung.equals("javax.swing.JLabel")
					|| umgebung.equals("javax.swing.JPanel")
					|| umgebung.equals("javax.swing.JScrollPane")
					|| umgebung.equals("javax.swing.JButton")
					|| umgebung.equals("basis.swing.BufferedCanvas");
		}

		if (komponentenTyp.equals("javax.swing.JScrollBar")) {
			return umgebung.equals("javax.swing.JFrame")
					|| umgebung.equals("javax.swing.JLabel")
					|| umgebung.equals("javax.swing.JPanel")
					|| umgebung.equals("javax.swing.JButton")
					|| umgebung.equals("basis.swing.BufferedCanvas");
		}

		if (komponentenTyp.equals("javax.swing.JScrollPane")) {
			return umgebung.equals("javax.swing.JFrame")
					|| umgebung.equals("javax.swing.JLabel")
					|| umgebung.equals("javax.swing.JPanel")
					|| umgebung.equals("javax.swing.JScrollPane")
					|| umgebung.equals("javax.swing.JButton")
					|| umgebung.equals("basis.swing.BufferedCanvas");
		}

		if (komponentenTyp.equals("javax.swing.JSlider")) {
			return umgebung.equals("javax.swing.JFrame")
					|| umgebung.equals("javax.swing.JLabel")
					|| umgebung.equals("javax.swing.JPanel")
					|| umgebung.equals("javax.swing.JButton")
					|| umgebung.equals("basis.swing.BufferedCanvas");
		}
		if (komponentenTyp.equals("javax.swing.JComboBox")) {
			return umgebung.equals("javax.swing.JFrame")
					|| umgebung.equals("javax.swing.JLabel")
					|| umgebung.equals("javax.swing.JPanel")
					|| umgebung.equals("javax.swing.JButton")
					|| umgebung.equals("basis.swing.BufferedCanvas");
		}
		if (komponentenTyp.equals("basis.swing.BufferedCanvas")) {
			return umgebung.equals("javax.swing.JFrame")
					|| umgebung.equals("javax.swing.JLabel")
					|| umgebung.equals("javax.swing.JPanel")
					|| umgebung.equals("javax.swing.JButton")
					|| umgebung.equals("javax.swing.JScrollPane")
					|| umgebung.equals("basis.swing.BufferedCanvas");
		}
		if (komponentenTyp.equals("javax.swing.JPanel")) {
			return umgebung.equals("javax.swing.JFrame")
					|| umgebung.equals("javax.swing.JLabel")
					|| umgebung.equals("javax.swing.JPanel")
					|| umgebung.equals("javax.swing.JButton")
					|| umgebung.equals("javax.swing.JScrollPane")
					|| umgebung.equals("basis.swing.BufferedCanvas");
		}
		if (komponentenTyp.equals("basis.swing.Pen")||komponentenTyp.equals("basis.swing.Turtle")) {
			return umgebung.equals("javax.swing.JFrame")
					|| umgebung.equals("javax.swing.JLabel")
					|| umgebung.equals("javax.swing.JPanel")
					|| umgebung.equals("basis.swing.BufferedCanvas");
		}
		if (komponentenTyp.endsWith("Tastatur")) {
			return umgebung.equals("javax.swing.JFrame");
		}
		if (komponentenTyp.endsWith("Maus")) {
			return !(umgebung.endsWith("Maus") ||
					umgebung.endsWith("Tastatur") ||
					umgebung.endsWith("Pen")||
					umgebung.endsWith("Turtle")
					);
		}
		return false;

	}

	public static KomponentenGrundInfo erzeugeKomponentenInfo(JPanel mp,
			String komponententyp, String vname) {
		KomponentenGrundInfo neu;
		String swinganalogon = ZuordungBasisSwing.getUebersetzungBasisSwing(
				komponententyp, true);
		if (swinganalogon.equals("javax.swing.JFrame")) {
			neu = new JFrameInfo(mp, komponententyp, vname);
		} else if (swinganalogon.equals("javax.swing.JButton")) {
			neu = new JButtonInfo((MovePanel) mp, komponententyp, vname);
		} else if (swinganalogon.equals("javax.swing.JLabel")) {
			neu = new JLabelInfo((MovePanel) mp, komponententyp, vname);
		} else if (swinganalogon.equals("javax.swing.JTextField")) {
			neu = new JTextFieldInfo((MovePanel) mp, komponententyp, vname);
		} else if (swinganalogon.equals("basis.swing.JNumberField")) {
			neu = new JNumberFieldInfo((MovePanel) mp, komponententyp, vname);
		} else if (swinganalogon.equals("javax.swing.JCheckBox")) {
			neu = new JCheckBoxInfo((MovePanel) mp, komponententyp, vname);
		} else if (swinganalogon.equals("javax.swing.JScrollBar")) {
			neu = new JScrollBarInfo((MovePanel) mp, komponententyp, vname);
		} else if (swinganalogon.equals("javax.swing.JSlider")) {
			neu = new JSliderInfo((MovePanel) mp, komponententyp, vname);
		} else if (swinganalogon.equals("javax.swing.JTextArea")) {
			neu = new JTextAreaInfo((MovePanel) mp, komponententyp, vname);
		} else if (swinganalogon.equals("javax.swing.JComboBox")) {
			neu = new JComboBoxInfo((MovePanel) mp, komponententyp, vname);
		}  else if (swinganalogon.equals("javax.swing.JList")) {
			neu = new JListInfo((MovePanel) mp, komponententyp, vname);
		} else if (swinganalogon.equals("javax.swing.JPanel")) {
			neu = new JPanelInfo((MovePanel) mp, komponententyp, vname);
		} else if (swinganalogon.equals("basis.swing.BufferedCanvas")) {
			neu = new BufferedCanvasInfo((MovePanel) mp, komponententyp, vname);
		} else if (swinganalogon.equals("basis.swing.Turtle")) {
			neu = new TurtleInfo((MovePanel) mp, komponententyp, vname);
		} else if (swinganalogon.equals("basis.swing.Pen")) {
			neu = new PenInfo((MovePanel) mp, komponententyp, vname);
		} else if (swinganalogon.equals("javax.swing.JScrollPane")) {
			neu = new JScrollPaneInfo((MovePanel) mp, komponententyp, vname);
		} else if (swinganalogon.equals("basis.Maus")) {
			neu = new MausBasisInfo((MovePanel) mp, komponententyp, vname);
		} else if (swinganalogon.equals("basis.Tastatur")) {
			neu = new TastaturInfo((MovePanel) mp, komponententyp, vname);
		} else {
			neu = new KomponentenGrundInfo(mp, komponententyp, vname);
		}

		return neu;
	}

	/**
	 * @param komponententyp
	 * @param typUmgebendeKomponente
	 * @param umgebendeKomponente
	 * @return
	 */
	public static boolean passtKomponentenTypInJScrollPaneFallsDiesDerUmgebendeTypIst(
			Controller controller, String komponententyp,
			String typUmgebendeKomponente, JPanel umgebendeKomponente) {
		typUmgebendeKomponente = ZuordungBasisSwing.getUebersetzungBasisSwing(
				typUmgebendeKomponente, true);
		if (typUmgebendeKomponente.equals("javax.swing.JScrollPane")) {
			for (KomponentenBasisInfo kbi: controller.getKomponentenInfo(umgebendeKomponente)
			.getEingebetteteKomponenten()){
				if (!kbi.getKomponentenTyp().endsWith("Maus")){
					return false;
				}
			}
		}
		return true;
	}

}
