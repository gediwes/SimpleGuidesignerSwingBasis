/**
 * 
 */
package guidesigner.oberflaeche;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.parsers.ParserConfigurationException;

import blueJLink.BlueJExporter;
import blueJLink.BlueJInterface;
import bluej.extensions.BProject;
import bluej.extensions.BlueJ;
import bluej.extensions.ProjectNotOpenException;
import guidesigner.control.Controller;
import guidesigner.control.ExportzielWaehlenDialog;
import guidesigner.control.KlassennameUndOberklasse;
import guidesigner.control.Komponentenerzeuger;
import guidesigner.control.ZuordungBasisSwing;
import guidesigner.eigenschaften.komponenten.KomponentenBasisInfo;
import guidesigner.eigenschaften.komponenten.KomponentenGrundInfo;
import guidesigner.einAusgabe.Dateipfade;
import guidesigner.einAusgabe.ModellUndXML;
import guidesigner.einAusgabe.QuelltextAusgabeFenster;
import guidesigner.einAusgabe.QuelltextErzeuger;
import java.awt.SystemColor;
import java.awt.Rectangle;
import javax.swing.border.BevelBorder;
import java.awt.Insets;
import javax.swing.Box;
import javax.swing.UIManager;
import java.awt.Font;
import java.awt.Window.Type;

/**
 * @author Georg
 * 
 */
public class DesignerFenster extends JFrame implements ActionListener, MouseListener {

	private JPanel contentPane;
	private JToggleButton kStift;
	private JToggleButton kIgelStift;
	private JToggleButton kKnopf;
	private JToggleButton bFeld;
	private JToggleButton lbListBox;
	private JToggleButton laListAuswahl;
	private JToggleButton wBox;
	private JToggleButton tbTextBereich;
	private JToggleButton tbTextFeld;
	private JToggleButton zZahlenFeld;
	private JToggleButton rRollbalkenHorizontal;
	private JToggleButton lLeinwand;
	private JToggleButton gGrundflaeche;
	private JToggleButton sScrollFlaeche;
	private JToggleButton glRegler;
	// private JToggleButton stStift;
	private JToggleButton tTastatur;
	private JToggleButton mMaus;
	private JSlider rgridx;
	private JSlider rgridy;
	private JLabel bRasterx;
	private JLabel bRastery;
	private JMenuItem menuItemES, menuItemEL, menuItemQ, menuItemNE;
	// private JMenuItem menuItemQBlueJ;
	private JPanel lAuswahl;
	private JComboBox lAktiverEditor;
	private JLabel lblNewLabel;
	private JPanel LEigenschaften;

	private KomponentenBaum tree;
	DefaultMutableTreeNode wurzel;
	private JPanel lKomponentenAuswahl;
	private int rasterY;
	private int rasterX;
	private Controller controller;
	private JInternalFrame internalFrame;
	private int fensterbreite = 900;
	private int fensterhoehe = 750;

	// Programmzustände
	public final int WARTEAUFKLICKFUERNEUEKOMPONENTE = 0;
	public final int BEARBEITUNGVONKOMPONENTEN = 1;

	private int zustand = BEARBEITUNGVONKOMPONENTEN;
	private JPanel pFenster;

	private DefaultComboBoxModel<KomponentenBasisInfo> modEintraegeAuswahlliste = new DefaultComboBoxModel<KomponentenBasisInfo>();
	protected boolean lAktiverEditorAktiv;
	private JSplitPane splitPane_1;
	private JPanel rasterPanel;
	private BlueJInterface blueJInterface;
	// private JMenuItem mntmModellInProjekt_1;
	private String basisverzeichnis = "";
	private JMenuBar menuBar;
	// private boolean istSwing;
	private JCheckBox chckbxSwinggui;
	private JCheckBox chckbxBasisgui;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	// private JMenuItem mntmOffeneProjekte;
	private JMenu mnClassInProjekt;
	private JCheckBox chckbxBasisguiSchleife;
	private JMenu mnModellInProjekt;
	// private JMenuItem mntmOffeneProjekteFuerModell;
	private JMenu mnModellAusProjekt;
	// private JMenuItem mntmOffeneProjekteFuerModellImport;

	private JScrollPane sKomponentenAuswahl;

	/**
	 * @return liefert modEintraegeAuswahlliste
	 */
	public DefaultComboBoxModel<KomponentenBasisInfo> getModEintraegeAuswahlliste() {
		return modEintraegeAuswahlliste;
	}

	/**
	 * @param modEintraegeAuswahlliste
	 *            setzt modEintraegeAuswahlliste
	 */
	public void setModEintraegeAuswahlliste(DefaultComboBoxModel<KomponentenBasisInfo> modEintraegeAuswahlliste) {
		this.modEintraegeAuswahlliste = modEintraegeAuswahlliste;
	}

	/**
	 * @return liefert zustand
	 */
	public int getZustand() {
		return zustand;
	}

	/**
	 * @param zustand
	 *            setzt zustand
	 */
	public void setZustand(int zustand) {
		this.zustand = zustand;
		switch (zustand) {
		case WARTEAUFKLICKFUERNEUEKOMPONENTE:
			for (KomponentenBasisInfo ki : controller.getKomponenten()) {
				if (ki.getPanel() instanceof MovePanel)
					((MovePanel) ki.getPanel()).setBearbeitbar(false);

			}
			break;

		case BEARBEITUNGVONKOMPONENTEN:
			for (KomponentenBasisInfo ki : controller.getKomponenten()) {
				if (ki.getPanel() instanceof MovePanel)
					((MovePanel) ki.getPanel()).setBearbeitbar(true);
			}
			for (Component c : lKomponentenAuswahl.getComponents()) {
				if (c instanceof JToggleButton) {

					((JToggleButton) c).setSelected(false);

				}
			}
			break;
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DesignerFenster frame = new DesignerFenster(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DesignerFenster(Controller controller) {
		setTitle("Oberflächendesigner für Swing und Basis");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// if (blueJInterface != null) {
				if (e.getSource() == DesignerFenster.this) {
					if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(DesignerFenster.this, "Beenden ?",
							"Programmende bestätigen", JOptionPane.YES_NO_OPTION)) {
						dispose();
					}
				}

				// }
			}
		});
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		boolean istSwing = true;
		if (controller != null) {
			this.blueJInterface = controller.getBlueJInterface();
			istSwing = controller.isSwing();
		}

		// JOptionPane.showMessageDialog(this,
		// this.blueJInterface.getBlueJ().getCurrentPackage()==null);
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.controller = controller;
		int bildschirmbreite = Toolkit.getDefaultToolkit().getScreenSize().width;
		int bildschirmhoehe = Toolkit.getDefaultToolkit().getScreenSize().height;
		if (bildschirmbreite < fensterbreite) {
			fensterbreite = bildschirmbreite;
		}
		if (bildschirmhoehe < fensterhoehe) {
			fensterhoehe = bildschirmhoehe;
		}

		setBounds((bildschirmbreite - fensterbreite) / 2, (bildschirmhoehe - fensterhoehe) / 2, 1011, 749);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		splitPane_1 = new JSplitPane();
		splitPane_1.setAutoscrolls(true);
		splitPane_1.setContinuousLayout(true);
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		contentPane.add(splitPane_1, BorderLayout.WEST);
		sKomponentenAuswahl = new JScrollPane();
		sKomponentenAuswahl.setPreferredSize(new Dimension(180, 200));
		// contentPane.add(sKomponentenAuswahl, BorderLayout.WEST);
		splitPane_1.setLeftComponent(sKomponentenAuswahl);
		lKomponentenAuswahl = new JPanel();
		lKomponentenAuswahl.setLayout(new GridLayout(0, 1));
		sKomponentenAuswahl.setViewportView(lKomponentenAuswahl);

		splitPane_2 = new JSplitPane();
		splitPane_2.setResizeWeight(0.3);
		splitPane_2.setContinuousLayout(true);
		contentPane.add(splitPane_2, BorderLayout.CENTER);

		setInternalFrame(
				new JInternalFrame(ZuordungBasisSwing.getUebersetzungSwingBasis("javax.swing.JFrame", istSwing)));
//		getInternalFrame().setMaximizable(true);
//		getInternalFrame().setIconifiable(true);
//
//		getInternalFrame().getContentPane().setLayout(null);
		JScrollPane scFrame = new JScrollPane();
		JPanel basis = new JPanel();
        basis.setLayout(null);
		// pFenster = new JPanel();
		pFenster = new GridPanel(10, 10);
		basis.add(pFenster);
		scFrame.setViewportView(basis);
		getInternalFrame().getContentPane().add(scFrame, BorderLayout.CENTER);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setContinuousLayout(true);
		splitPane_2.setRightComponent(splitPane);
		splitPane.setPreferredSize(new Dimension(300, 200));
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		JPanel panelRechtsOben = new JPanel();
		panelRechtsOben.setPreferredSize(new Dimension(300, 200));
		panelRechtsOben.setLayout(new BorderLayout(0, 0));
		splitPane.setLeftComponent(panelRechtsOben);
		JScrollPane scrollPane_1 = new JScrollPane();
		panelRechtsOben.add(scrollPane_1, BorderLayout.CENTER);
		// scrollPane_1.setPreferredSize(new Dimension(180,200));
		lAuswahl = new JPanel();
		lAuswahl.setBackground(Color.LIGHT_GRAY);
		panelRechtsOben.add(lAuswahl, BorderLayout.NORTH);
		lAuswahl.setLayout(new BorderLayout(0, 0));

		lblNewLabel = new JLabel("Element auswählen  ");
		lblNewLabel.setBackground(Color.LIGHT_GRAY);
		lAuswahl.add(lblNewLabel, BorderLayout.WEST);
		lAktiverEditor = new JComboBox();
		lAktiverEditor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (lAktiverEditorAktiv) {
					bearbeiteAuswahlKomponenteUeberEditor();
				}
			}
		});
		lAuswahl.add(lAktiverEditor, BorderLayout.CENTER);

		JSeparator separator = new JSeparator();
		lAuswahl.add(separator, BorderLayout.SOUTH);

		LEigenschaften = new JPanel();
		scrollPane_1.setViewportView(LEigenschaften);
		LEigenschaften.setLayout(new BorderLayout(0, 0));

		JScrollPane sContainerHierarchie = new JScrollPane();
		splitPane.setRightComponent(sContainerHierarchie);

		tree = new KomponentenBaum(this);
		sContainerHierarchie.setViewportView(tree);
		splitPane.setDividerLocation(400);
		splitPane_2.setDividerLocation(500);

		tree.setzeDragNDrop();
		getInternalFrame().setVisible(true);
		lAktiverEditorAktiv = true;
		// lKomponentenAuswahl.add(tTastatur);

		tbTextBereich = new JToggleButton(ZuordungBasisSwing.getUebersetzungBasisSwing("TextBereich", istSwing));
		tbTextBereich.setIcon(new ImageIcon(this.getClass().getResource("/basis/gui/images/TextBereich.gif")));
		tbTextBereich.setHorizontalAlignment(JToggleButton.LEFT);
		tbTextBereich.addActionListener(this);

		mMaus = new JToggleButton(ZuordungBasisSwing.getUebersetzungBasisSwing("Maus", istSwing));
		mMaus.setIcon(new ImageIcon(this.getClass().getResource("/basis/images/Maus.gif")));
		mMaus.addActionListener(this);
		mMaus.setHorizontalAlignment(JToggleButton.LEFT);

		lKomponentenAuswahl.add(mMaus);
		if (controller.getGuiTyp() != Controller.BASISGUISCHLEIFE) {
			lKomponentenAuswahl.remove(mMaus);
		}

		tTastatur = new JToggleButton(ZuordungBasisSwing.getUebersetzungBasisSwing("Tastatur", istSwing));
		tTastatur.setIcon(new ImageIcon(this.getClass().getResource("/basis/images/Tastatur.gif")));
		tTastatur.addActionListener(this);
		tTastatur.setHorizontalAlignment(JToggleButton.LEFT);
		lKomponentenAuswahl.add(tTastatur);

		if (controller.getGuiTyp() != Controller.BASISGUISCHLEIFE) {
			lKomponentenAuswahl.remove(tTastatur);
		}

		kStift = new JToggleButton(ZuordungBasisSwing.getUebersetzungBasisSwing("Stift", istSwing));
		kStift.setIcon(new ImageIcon(this.getClass().getResource("/basis/gui/images/Pen.gif")));
		kStift.addActionListener(this);
		kStift.setHorizontalAlignment(JToggleButton.LEFT);
		lKomponentenAuswahl.add(kStift);

		kIgelStift = new JToggleButton(ZuordungBasisSwing.getUebersetzungBasisSwing("IgelStift", istSwing));
		kIgelStift.setIcon(new ImageIcon(this.getClass().getResource("/basis/gui/images/Turtle.gif")));
		kIgelStift.addActionListener(this);
		kIgelStift.setHorizontalAlignment(JToggleButton.LEFT);
		lKomponentenAuswahl.add(kIgelStift);

		kKnopf = new JToggleButton(ZuordungBasisSwing.getUebersetzungBasisSwing("Knopf", istSwing));
		kKnopf.setIcon(new ImageIcon(this.getClass().getResource("/basis/gui/images/Knopf.gif")));
		kKnopf.addActionListener(this);

		kKnopf.setHorizontalAlignment(JToggleButton.LEFT);
		lKomponentenAuswahl.add(kKnopf);

		bFeld = new JToggleButton(ZuordungBasisSwing.getUebersetzungBasisSwing("BeschriftungsFeld", istSwing));
		bFeld.setIcon(new ImageIcon(this.getClass().getResource("/basis/gui/images/BeschriftungsFeld.gif")));
		bFeld.setHorizontalAlignment(JToggleButton.LEFT);
		bFeld.addActionListener(this);
		lKomponentenAuswahl.add(bFeld);

		lbListBox = new JToggleButton(ZuordungBasisSwing.getUebersetzungBasisSwing("ListBox", istSwing));
		lbListBox.setIcon(new ImageIcon(this.getClass().getResource("/basis/gui/images/ListAuswahl.gif")));
		lbListBox.setHorizontalAlignment(JToggleButton.LEFT);
		lbListBox.addActionListener(this);
		lKomponentenAuswahl.add(lbListBox);
		// wWahlBoxGruppe = new JToggleButton("WahlBoxGruppenFlaeche");
		// // wWahlBoxGruppe.setIcon(new
		// //
		// ImageIcon(this.getClass().getResource("/basis/gui/images/RadioButton.gif")));
		// wWahlBoxGruppe.setHorizontalAlignment(JToggleButton.LEFT);
		// wWahlBoxGruppe.addActionListener(this);

		wBox = new JToggleButton(ZuordungBasisSwing.getUebersetzungBasisSwing("WahlBox", istSwing));
		wBox.setIcon(new ImageIcon(this.getClass().getResource("/basis/gui/images/RadioButton.gif")));
		wBox.setHorizontalAlignment(JToggleButton.LEFT);
		wBox.addActionListener(this);

		laListAuswahl = new JToggleButton(ZuordungBasisSwing.getUebersetzungBasisSwing("ListAuswahl", istSwing));
		laListAuswahl.setIcon(new ImageIcon(this.getClass().getResource("/basis/gui/images/ListBox.gif")));
		laListAuswahl.setHorizontalAlignment(JToggleButton.LEFT);
		laListAuswahl.addActionListener(this);
		lKomponentenAuswahl.add(laListAuswahl);
		lKomponentenAuswahl.add(wBox);
		// lKomponentenAuswahl.add(wWahlBoxGruppe);
		lKomponentenAuswahl.add(tbTextBereich);

		gGrundflaeche = new JToggleButton(ZuordungBasisSwing.getUebersetzungBasisSwing("GrundFlaeche", istSwing));
		gGrundflaeche.setIcon(new ImageIcon(this.getClass().getResource("/basis/gui/images/Grundflaeche.gif")));
		gGrundflaeche.addActionListener(this);

		tbTextFeld = new JToggleButton(ZuordungBasisSwing.getUebersetzungBasisSwing("TextFeld", istSwing));
		tbTextFeld.setIcon(new ImageIcon(this.getClass().getResource("/basis/gui/images/TextFeld.gif")));
		tbTextFeld.setHorizontalAlignment(JToggleButton.LEFT);
		tbTextFeld.addActionListener(this);

		sScrollFlaeche = new JToggleButton(ZuordungBasisSwing.getUebersetzungBasisSwing("ScrollFlaeche", istSwing));
		sScrollFlaeche.setIcon(new ImageIcon(this.getClass().getResource("/basis/gui/images/ScrollFlaeche.gif")));
		sScrollFlaeche.setHorizontalAlignment(JToggleButton.LEFT);
		sScrollFlaeche.addActionListener(this);
		lKomponentenAuswahl.add(sScrollFlaeche);
		lKomponentenAuswahl.add(tbTextFeld);

		rRollbalkenHorizontal = new JToggleButton(ZuordungBasisSwing.getUebersetzungBasisSwing("Rollbalken", istSwing));
		rRollbalkenHorizontal.setIcon(new ImageIcon(this.getClass().getResource("/basis/gui/images/RollBalken.gif")));
		rRollbalkenHorizontal.setHorizontalAlignment(JToggleButton.LEFT);
		rRollbalkenHorizontal.addActionListener(this);
		lKomponentenAuswahl.add(rRollbalkenHorizontal);

		glRegler = new JToggleButton(ZuordungBasisSwing.getUebersetzungBasisSwing("Regler", istSwing));
		glRegler.setIcon(new ImageIcon(this.getClass().getResource("/basis/gui/images/Regler.gif")));
		glRegler.setHorizontalAlignment(JToggleButton.LEFT);
		glRegler.addActionListener(this);
		lKomponentenAuswahl.add(glRegler);

		lLeinwand = new JToggleButton(ZuordungBasisSwing.getUebersetzungBasisSwing("Leinwand", istSwing));
		lLeinwand.setHorizontalAlignment(SwingConstants.LEFT);
		lLeinwand.setIcon(new ImageIcon(this.getClass().getResource("/basis/gui/images/Leinwand.gif")));
		// lLeinwand.setHorizontalAlignment(JToggleButton.LEFT);
		lLeinwand.addActionListener(this);

		zZahlenFeld = new JToggleButton(ZuordungBasisSwing.getUebersetzungBasisSwing("ZahlenFeld", istSwing));
		zZahlenFeld.setIcon(new ImageIcon(this.getClass().getResource("/basis/gui/images/ZahlenFeld.gif")));
		zZahlenFeld.setHorizontalAlignment(JToggleButton.LEFT);
		zZahlenFeld.addActionListener(this);
		lKomponentenAuswahl.add(zZahlenFeld);
		lKomponentenAuswahl.add(lLeinwand);
		gGrundflaeche.setHorizontalAlignment(JToggleButton.LEFT);
		lKomponentenAuswahl.add(gGrundflaeche);
		rasterPanel = new JPanel();
		rasterPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		rasterPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		rasterPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		splitPane_1.setRightComponent(rasterPanel);
		bRasterx = new JLabel("Raster horizontal");
		bRasterx.setSize(new Dimension(0, 25));
		bRasterx.setPreferredSize(new Dimension(82, 25));
		bRasterx.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		bRasterx.setVerticalAlignment(SwingConstants.TOP);
		bRasterx.setHorizontalAlignment(SwingConstants.LEFT);
		rgridx = new JSlider();
		rgridx.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				bearbeiteReglerBewegung(rgridx);
			}
		});
		rgridx.setValue(10);
		rgridx.setMaximum(30);
		rgridx.setMinorTickSpacing(5);
		rgridx.setMajorTickSpacing(10);
		rgridx.setSnapToTicks(true);
		rgridx.setPaintTicks(true);
		rgridx.setPaintLabels(true);
		bRastery = new JLabel("Raster vertikal");
		bRastery.setMinimumSize(new Dimension(70, 25));
		bRastery.setHorizontalAlignment(SwingConstants.LEFT);
		rgridy = new JSlider();
		rgridy.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				bearbeiteReglerBewegung(rgridy);
			}
		});
		rgridy.setMinorTickSpacing(5);
		rgridy.setMajorTickSpacing(10);
		rgridy.setMaximum(30);
		rgridy.setValue(10);
		rgridy.setSnapToTicks(true);
		rgridy.setPaintTicks(true);
		rgridy.setPaintLabels(true);
		rasterPanel.setLayout(new BoxLayout(rasterPanel, BoxLayout.Y_AXIS));
		rasterPanel.add(bRasterx);
		rasterPanel.add(rgridx);
		rasterPanel.add(bRastery);
		rasterPanel.add(rgridy);
		splitPane_1.setDividerLocation(500);
		erzeugeMenue();
		for (Component component : lKomponentenAuswahl.getComponents()) {
			if (component instanceof JToggleButton) {
				component.setPreferredSize(new Dimension(100, 25));
			}

		}

	}

	/**
	 * @param bjiface
	 */
	public void erzeugeMenue() {

		// Menueelemente
		JMenu mnDatei;

		// Erzeugen der Menueleiste
		menuBar = new JMenuBar();

		// Erstes Hauptmenue: Datei
		mnDatei = new JMenu("Datei");
		mnDatei.setBackground(SystemColor.control);

		mnDatei.setMnemonic(KeyEvent.VK_D);
		mnDatei.getAccessibleContext().setAccessibleDescription("Dateimenue");
		menuBar.add(mnDatei);

		// Menueeintraege
		menuItemNE = new JMenuItem("Neuer Entwurf", KeyEvent.VK_N);
		menuItemNE.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
		menuItemNE.getAccessibleContext().setAccessibleDescription("Neuer Entwurf");
		mnDatei.add(menuItemNE);
		menuItemNE.addActionListener(this);
		menuItemES = new JMenuItem("Entwurf speichern", KeyEvent.VK_S);
		menuItemES.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
		menuItemES.getAccessibleContext().setAccessibleDescription("Entwurf speichern");
		mnDatei.add(menuItemES);
		menuItemES.addActionListener(this);

		menuItemEL = new JMenuItem("Entwurf laden", KeyEvent.VK_L);
		menuItemEL.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
		menuItemEL.getAccessibleContext().setAccessibleDescription("");
		mnDatei.add(menuItemEL);
		menuItemEL.addActionListener(this);

		menuItemQ = new JMenuItem("Quelltext generieren und speichern", KeyEvent.VK_Q);
		menuItemQ.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.ALT_MASK));
		menuItemQ.getAccessibleContext().setAccessibleDescription("");
		mnDatei.add(menuItemQ);

		mntmQuelltextErzeugenUnd = new JMenuItem("Quelltext erzeugen und in Fenster anzeigen",KeyEvent.VK_E);
		mntmQuelltextErzeugenUnd.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
		mntmQuelltextErzeugenUnd.addActionListener(this);
		mnDatei.add(mntmQuelltextErzeugenUnd);
		menuItemQ.addActionListener(this);

		this.setJMenuBar(menuBar);

		mnBluej = new JMenu("BlueJ");
		mnBluej.setFont(new Font("Segoe UI", Font.BOLD, 18));
		mnBluej.setForeground(SystemColor.textHighlight);
		mnBluej.setBackground(SystemColor.control);
		mnBluej.addMenuListener(new MenuListener() {
			public void menuCanceled(MenuEvent e) {
			}

			public void menuDeselected(MenuEvent e) {
			}

			public void menuSelected(MenuEvent e) {
				bjpitemModellExport = erzeugeMenuMitOffenenProjekten(mnModellInProjekt);
				bjpitemClassExport = erzeugeMenuMitOffenenProjekten(mnClassInProjekt);
				bjpitemModellImport = erzeugeMenuMitOffenenProjekten(mnModellAusProjekt);

			}
		});
		mnBluej.setVisible(blueJInterface != null);
		
		verticalStrut = Box.createVerticalStrut(20);
		verticalStrut.setMaximumSize(new Dimension(20, 20));
		verticalStrut.setMinimumSize(new Dimension(5, 5));
		verticalStrut.setPreferredSize(new Dimension(10, 5));
		menuBar.add(verticalStrut);
		
		menuBar.add(mnBluej);

		JMenuItem mntmNeuesProjekt = new JMenuItem("Neues Projekt");
		mntmNeuesProjekt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				blueJInterface.erzeugeProjekt();
				// setzeMenuetextFuerBluejExport();
			}
		});
		mnBluej.add(mntmNeuesProjekt);

		JMenuItem mntmProjektffnen = new JMenuItem("Projekt öffnen");
		mntmProjektffnen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				blueJInterface.oeffneProjekt();
				// setzeMenuetextFuerBluejExport();
			}
		});
		mnBluej.add(mntmProjektffnen);
		mnClassInProjekt = new JMenu("Quelltext generieren und speichern");
		mnBluej.add(mnClassInProjekt);
		mnModellInProjekt = new JMenu("GUI_Beschreibung in Projektordner exportieren");
		mnBluej.add(mnModellInProjekt);
		mnModellAusProjekt = new JMenu("GUI_Beschreibung aus Projektordner importieren");
		mnBluej.add(mnModellAusProjekt);
		chckbxSwinggui = new JCheckBox("Swing-GUI");
		chckbxSwinggui.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (chckbxSwinggui.isSelected()) {
					controller.stelleGUIAufSwing();
				}
			}
		});
		
		verticalStrut_1 = Box.createVerticalStrut(20);
		verticalStrut_1.setMaximumSize(new Dimension(20, 20));
		menuBar.add(verticalStrut_1);
		buttonGroup.add(chckbxSwinggui);
		menuBar.add(chckbxSwinggui);

		chckbxBasisgui = new JCheckBox("Basis-GUI mit Lauscher");
		chckbxBasisgui.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxBasisgui.isSelected()) {
					controller.stelleGUIAufBasis();
				}
			}
		});
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		verticalStrut_2.setMaximumSize(new Dimension(20, 20));
		menuBar.add(verticalStrut_2);
		buttonGroup.add(chckbxBasisgui);
		menuBar.add(chckbxBasisgui);

		chckbxBasisguiSchleife = new JCheckBox("Basis-GUI mit Schleife ");
		chckbxBasisguiSchleife.setSelected(true);
		chckbxBasisguiSchleife.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxBasisguiSchleife.isSelected()) {
					controller.stelleGUIAufBasisMitSchleife();
				}
			}
		});
		
		Component verticalStrut_3 = Box.createVerticalStrut(20);
		verticalStrut_3.setMaximumSize(new Dimension(20, 20));
		menuBar.add(verticalStrut_3);
		buttonGroup.add(chckbxBasisguiSchleife);
		menuBar.add(chckbxBasisguiSchleife);

		this.repaint();

	}

	public void setzeToggleTexteBasisOderSwing(boolean istSwing) {
		kStift.setText(ZuordungBasisSwing.getUebersetzungBasisSwing("Stift", istSwing));
		kIgelStift.setText(ZuordungBasisSwing.getUebersetzungBasisSwing("IgelStift", istSwing));
		tbTextBereich.setText(ZuordungBasisSwing.getUebersetzungBasisSwing("TextBereich", istSwing));
		kKnopf.setText(ZuordungBasisSwing.getUebersetzungBasisSwing("Knopf", istSwing));
		bFeld.setText(ZuordungBasisSwing.getUebersetzungBasisSwing("BeschriftungsFeld", istSwing));
		lbListBox.setText(ZuordungBasisSwing.getUebersetzungBasisSwing("ListBox", istSwing));
		wBox.setText(ZuordungBasisSwing.getUebersetzungBasisSwing("WahlBox", istSwing));
		laListAuswahl.setText(ZuordungBasisSwing.getUebersetzungBasisSwing("ListAuswahl", istSwing));
		gGrundflaeche.setText(ZuordungBasisSwing.getUebersetzungBasisSwing("GrundFlaeche", istSwing));
		tbTextFeld.setText(ZuordungBasisSwing.getUebersetzungBasisSwing("TextFeld", istSwing));
		sScrollFlaeche.setText(ZuordungBasisSwing.getUebersetzungBasisSwing("ScrollFlaeche", istSwing));
		rRollbalkenHorizontal.setText(ZuordungBasisSwing.getUebersetzungBasisSwing("Rollbalken", istSwing));
		glRegler.setText(ZuordungBasisSwing.getUebersetzungBasisSwing("Regler", istSwing));
		lLeinwand.setText(ZuordungBasisSwing.getUebersetzungBasisSwing("Leinwand", istSwing));
		zZahlenFeld.setText(ZuordungBasisSwing.getUebersetzungBasisSwing("ZahlenFeld", istSwing));
		mMaus.setText(ZuordungBasisSwing.getUebersetzungBasisSwing("Maus", istSwing));
		tTastatur.setText(ZuordungBasisSwing.getUebersetzungBasisSwing("Tastatur", istSwing));
	}

	BProject[] aktuelleBlueJProjekte;
	JMenuItem[] bjpitemModellExport;
	JMenuItem[] bjpitemClassExport;
	JMenuItem[] bjpitemModellImport;
	private JMenuItem mntmQuelltextErzeugenUnd;
	private JSplitPane splitPane_2;
	private JMenu mnBluej;
	private JSeparator separator_1;
	private Component verticalStrut;
	private Component verticalStrut_1;

	private JMenuItem[] erzeugeMenuMitOffenenProjekten(final JMenuItem oben) {
		JMenuItem[] bjpitem = null;
		oben.removeAll();
		if (blueJInterface != null) {
			aktuelleBlueJProjekte = blueJInterface.getBlueJProjekte();
			if (aktuelleBlueJProjekte == null || aktuelleBlueJProjekte.length == 0) {
				return null;
			}
			JMenuItem erstes = new JMenuItem("Offene Projekte");
			erstes.setEnabled(false);
			oben.add(erstes);
			bjpitem = new JMenuItem[aktuelleBlueJProjekte.length];
			for (int i = 0; i < aktuelleBlueJProjekte.length; i++) {
				try {
					bjpitem[i] = new JMenuItem(aktuelleBlueJProjekte[i].getName());
				} catch (ProjectNotOpenException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				bjpitem[i].addActionListener(this);
				oben.add(bjpitem[i]);
			}
		}
		return bjpitem;
	}

	/**
	 * @param controller
	 */
	public void erzeugeFenster(Controller controller) {
		pFenster.setLayout(null);
		pFenster.setBounds(0, 0, 600, 500);
		pFenster.getParent().setPreferredSize(new Dimension(600,500));
		pFenster.setBackground(Color.WHITE);
		controller.registriereKomponente(pFenster,
				ZuordungBasisSwing.getUebersetzungSwingBasis("javax.swing.JFrame", controller.isSwing()), "fenster",
				null);
		pFenster.addMouseListener(this);

	}

	/**
	 * 
	 */
	protected void bearbeiteAuswahlKomponenteUeberEditor() {
		waehleKomponenteUeberAuswahl((KomponentenGrundInfo) lAktiverEditor.getSelectedItem());
	}

	/**
	 * @param selectedItem
	 */
	private void waehleKomponenteUeberAuswahl(KomponentenGrundInfo kinfo) {
		// System.out.println(kinfo);
		this.zeigeEditor(controller.getEditorZuKomponentenInfo(kinfo));
		this.tree.selektiere(kinfo);
		if (zustand == BEARBEITUNGVONKOMPONENTEN) {
			this.aktiviereKomponentenAnfasser(kinfo);
		}
	}

	public void zeigeEditor(JScrollPane editor) {
		if (editor == null) {
			return;
		}

		// System.out.println("edi");
		if (LEigenschaften.getComponentCount() > 0 && LEigenschaften.getComponent(0) == editor) {
			// Wechsle nicht erforderlich, es gibt sowieso nur maximal eine
			// eingebettete Komponente
			return;
		}
		LEigenschaften.removeAll();
		LEigenschaften.add(editor, BorderLayout.CENTER);
		LEigenschaften.revalidate();
		LEigenschaften.repaint();
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() instanceof JMenuItem) {
			bearbeiteJMenuItem((JMenuItem) arg0.getSource());
		}
		if (arg0.getSource() instanceof JToggleButton) {
			bearbeiteJToggleButton(arg0);
		}
	}

	/**
	 * @param source
	 */
	private void bearbeiteJMenuItem(JMenuItem source) {
		if (source == menuItemNE) {
			controller.entferneOberflaeche();
		}
		if (source == menuItemES) {
			ModellUndXML mixml = new ModellUndXML();
			mixml.speichereEntwurf(this, null);
		}
		if (source == menuItemEL) {
			ModellUndXML mixml = new ModellUndXML();
			try {
				mixml.ladeXMLEntwurf(this, null);
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
		}
		if (source == menuItemQ) {
			basisverzeichnis = ExportzielWaehlenDialog.getExportielDurchDialog(basisverzeichnis,
					getLocationOnScreen().x + 10, getLocationOnScreen().y);
			if (basisverzeichnis != null) {

				KlassennameUndOberklasse auswahl = AnwendungOderFrameDialog
						.getIstKlasseAnwendungByDialog(getLocationOnScreen().x, getLocationOnScreen().y);
				if (auswahl == null) {
					return;
				}
				boolean sourceAlsApplication = auswahl.isFensteralsoberklasse();

				QuelltextErzeuger qte = new QuelltextErzeuger(controller.getKomponentenInfo(getFensterPanel()),
						auswahl.isFensteralsoberklasse(), auswahl.getKlassenname(), controller.getGuiTyp());
				String text = qte.getKlassenSource(auswahl.isFensteralsoberklasse());
//				System.out.println(text);

				new Dateipfade().erzeugeKlassendatei(basisverzeichnis, "", auswahl.getKlassenname(), text, true, this);

			}

		}
		if (source == mntmQuelltextErzeugenUnd) {
			KlassennameUndOberklasse auswahl = AnwendungOderFrameDialog
					.getIstKlasseAnwendungByDialog(getLocationOnScreen().x, getLocationOnScreen().y);
			if (auswahl == null) {
				return;
			}
			QuelltextErzeuger qte = new QuelltextErzeuger(controller.getKomponentenInfo(getFensterPanel()),
					auswahl.isFensteralsoberklasse(), auswahl.getKlassenname(), controller.getGuiTyp());
			String text = qte.getKlassenSource(auswahl.isFensteralsoberklasse());
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						QuelltextAusgabeFenster frame = new QuelltextAusgabeFenster(text,
								DesignerFenster.this.getBounds().x, DesignerFenster.this.getBounds().y);
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

		}

		JMenuItem mi = source;

		if (blueJInterface != null) {
			BProject aktuellesProjekt = null;
			for (BProject bp : aktuelleBlueJProjekte) {
				try {
					if (bp.getName().equals(mi.getText())) {
						aktuellesProjekt = bp;
						break;
					}
				} catch (ProjectNotOpenException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			// JOptionPane.showMessageDialog(null,"Export"+abp);

			if (aktuellesProjekt != null) {
				for (JMenuItem item : bjpitemClassExport) {
					if (mi == item) {
						exportiereKlasseNachBlueJ(aktuellesProjekt);
						break;
					}
				}
				for (JMenuItem item : bjpitemModellImport) {
					if (mi == item) {
						importiereModellbeschreibungAusBlueJ(aktuellesProjekt);
						break;
					}
				}
				for (JMenuItem item : bjpitemModellExport) {
					if (mi == item) {
						exportiereModellbeschreibungNachBlueJ(aktuellesProjekt);
						break;
					}
				}
			}
		}

	}

	/**
	 * @param arg0
	 */
	public void bearbeiteJToggleButton(ActionEvent arg0) {
		for (Component c : lKomponentenAuswahl.getComponents()) {
			if (c instanceof JToggleButton) {
				if (arg0.getSource() != c) {
					((JToggleButton) c).setSelected(false);
				}
			}
		}
		for (Component c : lKomponentenAuswahl.getComponents()) {
			if (c instanceof JToggleButton) {
				if (arg0.getSource() == c && ((JToggleButton) c).isSelected()) {
					setZustand(WARTEAUFKLICKFUERNEUEKOMPONENTE);
					return;
				}
			}

		}
		setZustand(BEARBEITUNGVONKOMPONENTEN);
	}

	/**
	 * @param tmp
	 */
	public void aktiviereKomponentenAnfasser(KomponentenBasisInfo kinfo) {
		if (zustand == BEARBEITUNGVONKOMPONENTEN) {
			if (kinfo.getPanel() instanceof MovePanel) {
				MovePanel mp = (MovePanel) kinfo.getPanel();
				mp.aktiviereAnfasser();
				deaktiviereAnfasserFuerAlleAusser(mp);
			} else {
				deaktiviereAnfasserFuerAlle();
			}
		}
	}

	/**
	 * 
	 */
	private void deaktiviereAnfasserFuerAlle() {
		for (KomponentenBasisInfo kinfo : controller.getKomponenten()) {
			JPanel p = kinfo.getPanel();
			if (p instanceof MovePanel) {
				((MovePanel) p).deaktiviereAnfasser();
			}
		}
	}

	public void bearbeiteReglerBewegung(JSlider r) {
		if (r == rgridx) {
			setzeRasterX(rgridx.getValue());
			if (pFenster != null) {
				((GridPanel) pFenster).setzeGridx(rgridx.getValue());
			}
		} else if (r == rgridy) {
			setzeRasterY(rgridy.getValue());

			if (pFenster != null) {
				((GridPanel) pFenster).setzeGridy(rgridy.getValue());
			}
		}
	}

	/**
	 * @param value
	 */
	private void setzeRasterY(int value) {
		rasterY = value;
	}

	/**
	 * @param value
	 */
	private void setzeRasterX(int value) {
		rasterX = value;
	}

	/**
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		JPanel umgebendeKomponente = null;
		if (zustand == WARTEAUFKLICKFUERNEUEKOMPONENTE) {
			if (e.getSource() instanceof JLabel) {
				JLabel temp = (JLabel) e.getSource();
				if (temp.getParent() instanceof JLayeredPane) {
					if (((JLayeredPane) temp.getParent()).getParent() instanceof MovePanel) {
						umgebendeKomponente = (MovePanel) ((JLayeredPane) temp.getParent()).getParent();
					}
				}
			} else if (e.getSource() == pFenster) {
				umgebendeKomponente = pFenster;
			}
			if (umgebendeKomponente != null) {
				String komponententyp = getKomponentenTyp(controller.isSwing());
				String typUmgebendeKomponente = getTypUmgebendeKomponente(umgebendeKomponente);
				if (controller.passtKomponentenTypInKomponentenTyp(komponententyp, typUmgebendeKomponente)) {
					if (controller.passtKomponentenTypInJScrollPaneFallsDiesDerUmgebendeTypIst(komponententyp,
							typUmgebendeKomponente, umgebendeKomponente)) {
						// Neue Komponente erzeugen und registrieren
						// this.erzeugeKomponente(komponententyp, e.getX(),
						// e.getY(), umgebendeKomponente);
						this.erzeugeKomponente(komponententyp, getRasterPosX(e.getX()), getRasterPosY(e.getY()),
								umgebendeKomponente);
						revalidate();
						this.setZustand(BEARBEITUNGVONKOMPONENTEN);
					} else {
						JOptionPane.showMessageDialog(this, "In der ScrollPane ist bereits eine Komponente enthalten\n"
								+ "Diese muss erst entfernt werden, bevor eine andere eingebettet werden kann");
					}

				} else {
					JOptionPane.showMessageDialog(this,
							komponententyp + " kann nicht in " + typUmgebendeKomponente + " eingebettet werden");
				}
			}
		}

	}

	/**
	 * @param umgebendeKomponente
	 * @return
	 */
	private String getTypUmgebendeKomponente(JComponent umgebendeKomponente) {
		if (umgebendeKomponente == pFenster) {
			return ZuordungBasisSwing.getUebersetzungSwingBasis("javax.swing.JFrame", controller.isSwing());
		} else {
			for (KomponentenBasisInfo kinfo : controller.getKomponenten()) {
				if (kinfo.getPanel() == umgebendeKomponente) {
					return kinfo.getKomponentenTyp();
				}
			}
		}
		return "";
	}

	private String getKomponentenTyp(boolean istSwing) {
		if (kKnopf.isSelected()) {
			return ZuordungBasisSwing.getUebersetzungSwingBasis("javax.swing.JButton", istSwing);
		}
		if (bFeld.isSelected()) {
			return ZuordungBasisSwing.getUebersetzungSwingBasis("javax.swing.JLabel", istSwing);
		}
		if (lbListBox.isSelected()) {
			return ZuordungBasisSwing.getUebersetzungSwingBasis("javax.swing.JComboBox", istSwing);
		}
		if (laListAuswahl.isSelected()) {
			return ZuordungBasisSwing.getUebersetzungSwingBasis("javax.swing.JList", istSwing);
		}
		if (wBox.isSelected()) {
			return ZuordungBasisSwing.getUebersetzungSwingBasis("javax.swing.JCheckBox", istSwing);
		}
		if (tbTextBereich.isSelected()) {
			return ZuordungBasisSwing.getUebersetzungSwingBasis("javax.swing.JTextArea", istSwing);
		}
		if (tbTextFeld.isSelected()) {
			return ZuordungBasisSwing.getUebersetzungSwingBasis("javax.swing.JTextField", istSwing);
		}
		if (zZahlenFeld.isSelected()) {
			return ZuordungBasisSwing.getUebersetzungSwingBasis("basis.swing.JNumberField", istSwing);
		}
		if (rRollbalkenHorizontal.isSelected()) {
			return ZuordungBasisSwing.getUebersetzungSwingBasis("javax.swing.JScrollBar", istSwing);
		}
		if (lLeinwand.isSelected()) {
			return ZuordungBasisSwing.getUebersetzungSwingBasis("basis.swing.BufferedCanvas", istSwing);
		}
		if (gGrundflaeche.isSelected()) {
			return ZuordungBasisSwing.getUebersetzungSwingBasis("javax.swing.JPanel", istSwing);
		}
		if (sScrollFlaeche.isSelected()) {
			return ZuordungBasisSwing.getUebersetzungSwingBasis("javax.swing.JScrollPane", istSwing);
		}
		if (glRegler.isSelected()) {
			return ZuordungBasisSwing.getUebersetzungSwingBasis("javax.swing.JSlider", istSwing);
		}
		if (kStift.isSelected()) {
			return ZuordungBasisSwing.getUebersetzungSwingBasis("basis.swing.Pen", istSwing);
		}
		if (kIgelStift.isSelected()) {
			return ZuordungBasisSwing.getUebersetzungSwingBasis("basis.swing.Turtle", istSwing);
		}
		if (tTastatur.isSelected()) {
			return "basis.Tastatur";
		}
		if (mMaus.isSelected()) {
			return "basis.Maus";
		}
		// if (wWahlBoxGruppe.isSelected()) {
		// return "";
		// }
		return "";
	}

	public void erzeugeKomponente(String komptyp, int x, int y, JPanel umgebendeKomponente) {
		new Komponentenerzeuger().erzeugeKomponente(this, controller, komptyp, x, y, umgebendeKomponente);
	}

	/**
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	/**
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	/**
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {
		if (zustand == BEARBEITUNGVONKOMPONENTEN) {
			// Editor anzeigen
			if (arg0.getSource() == pFenster) {
				controller.aktiviereAnzeigeUeberMovePanel(pFenster);
				revalidate();
			}
			//
		}

	}

	/**
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	public void aktualisiereAuswahl() {
		lAktiverEditor.setModel(modEintraegeAuswahlliste);
	}

	public void aktualisiereAuswahl(KomponentenBasisInfo kInfo) {
		lAktiverEditor.setModel(modEintraegeAuswahlliste);
		lAktiverEditor.setSelectedItem(kInfo);
		lAktiverEditor.repaint();
	}

	/**
	 * @param movePanel
	 */
	public void deaktiviereAnfasserFuerAlleAusser(MovePanel movePanel) {
		for (KomponentenBasisInfo kinfo : controller.getKomponenten()) {
			if (kinfo.getPanel() != movePanel) {
				JPanel p = kinfo.getPanel();
				if (p instanceof MovePanel) {
					((MovePanel) p).deaktiviereAnfasser();
				}
			}
		}
	}

	/**
	 * @return
	 */
	public JPanel getFensterPanel() {
		return pFenster;
	}

	/**
	 * @return controller
	 */
	public Controller getController() {
		return controller;
	}

	/**
	 * @param neu
	 */
	public void aktualisiereBaum(KomponentenBasisInfo neu) {
		tree.renoviereBaum(controller.getKomponentenInfo(pFenster));
		tree.selektiere(neu);
	}

	/**
	 * 
	 */
	public void aktualisiereBaum() {
		tree.renoviereBaum(controller.getKomponentenInfo(pFenster));
		tree.selektiere(controller.getKomponentenInfo(pFenster));
	}

	/**
	 * @param weg
	 */
	public void entferneAusAuswahlliste(KomponentenBasisInfo weg) {
		modEintraegeAuswahlliste.removeElement(weg);
		aktualisiereAuswahl();
	}

	/**
	 * @param komponentenTyp
	 * @param x
	 * @param y
	 * @param umgebendeKomponente
	 */
	public JPanel erzeugeKomponenteOhneRegistrierung(String komptyp, int x, int y, JPanel umgebendeKomponente) {
		return new Komponentenerzeuger().erzeugeKomponenteOhneRegistrierung(this, controller, komptyp, x, y,
				umgebendeKomponente);
	}

	/**
	 * @param komponentenInfo
	 */
	public void zeigeImBaum(KomponentenBasisInfo komponentenInfo) {
		tree.selektiere(komponentenInfo);
	}

	/**
	 * @param komponentenInfo
	 */
	public void waehleAusAnzeige(KomponentenBasisInfo komponentenInfo) {
		lAktiverEditorAktiv = false;
		lAktiverEditor.setSelectedItem(komponentenInfo);
		lAktiverEditorAktiv = true;
	}

	/**
	 * @param tmp
	 */
	public void zeigeEditorZu(KomponentenBasisInfo komponentenInfo) {
		zeigeEditor(controller.getEditorZuKomponentenInfo(komponentenInfo));
	}

	public int getRasterPosX(int pos) {
		return rasterX == 0 ? pos : pos - pos % rasterX;
	}

	public int getRasterPosY(int pos) {
		return rasterY == 0 ? pos : pos - pos % rasterY;
	}

	/**
	 * @return
	 */
	public int getRasterX() {
		return rasterX;
	}

	/**
	 * @return
	 */
	public int getRasterY() {
		return rasterY;
	}

	public void importiereModellbeschreibungAusBlueJ(BProject aktuellesBlueJProjekt) {
		ModellUndXML mixml = new ModellUndXML();
		try {
			try {
				mixml.ladeXMLEntwurf(this, aktuellesBlueJProjekt.getDir());
			} catch (ProjectNotOpenException e) {
				e.printStackTrace();
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

	}

	public void exportiereModellbeschreibungNachBlueJ(BProject aktuellesBlueJProjekt) {
		ModellUndXML mixml = new ModellUndXML();
		try {
			mixml.speichereEntwurf(this, aktuellesBlueJProjekt.getDir());
		} catch (ProjectNotOpenException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public void exportiereKlasseNachBlueJ(BProject aktuellesBlueJProjekt) {

		if (blueJInterface == null) {
			JOptionPane.showMessageDialog(this, "Fehler: Es ist kein Zugriff auf BlueJ möglich");
			return;
		}
		BlueJ bluej = blueJInterface.getBlueJ();
		// BProject aktuellesBlueJProjekt = blueJInterface.getBlueJProjekt();
		if (bluej == null) {
			JOptionPane.showMessageDialog(this, "Fehler: Es ist kein Zugriff auf BlueJ möglich");
			return;
		}
		if (aktuellesBlueJProjekt == null) {
			try {
				aktuellesBlueJProjekt = bluej.getCurrentPackage().getProject();
			} catch (ProjectNotOpenException e) {
				JOptionPane.showMessageDialog(this, "Bitte erst ein BlueJ-Projekt auswählen und öffnen");
				return;
			}
		}

		if (bluej != null) {
			try {
				bluej.openProject(aktuellesBlueJProjekt.getDir());
			} catch (ProjectNotOpenException e) {
				JOptionPane.showMessageDialog(this, "Bitte erst ein BlueJ-Projekt auswählen und öffnen");
				return;
			}

			KlassennameUndOberklasse auswahl = AnwendungOderFrameDialog
					.getIstKlasseAnwendungByDialog(getLocationOnScreen().x, getLocationOnScreen().y);
			if (auswahl == null) {
				return;
			}
			QuelltextErzeuger qt = new QuelltextErzeuger(controller.getKomponentenInfo(getFensterPanel()),
					auswahl.isFensteralsoberklasse(), auswahl.getKlassenname(), controller.getGuiTyp());
			new BlueJExporter(aktuellesBlueJProjekt, auswahl.getKlassenname(),
					qt.getKlassenSource(auswahl.isFensteralsoberklasse()));

		}

	}

	/**
	 * @return liefert internalFrame
	 */
	public JInternalFrame getInternalFrame() {
		return internalFrame;
	}

	/**
	 * @param internalFrame
	 *            setzt internalFrame
	 */
	public void setInternalFrame(JInternalFrame internalFrame) {
		this.internalFrame = internalFrame;
		splitPane_2.setLeftComponent(internalFrame);
	}

	/**
	 * @param b
	 */
	public void setCheckBoxSwingBasisBasis(int guiTyp) {
		if (guiTyp == Controller.SWINGGUI) {
			chckbxSwinggui.setSelected(true);
		} else if (guiTyp == Controller.BASISGUILAUSCHER) {
			chckbxBasisgui.setSelected(true);
		} else {
			chckbxBasisguiSchleife.setSelected(true);
		}
	}

	public void setzeKomponentenauswahlAuf(int guiTyp) {
		boolean mausda = false, tastaturda = false;
		for (Component cp : lKomponentenAuswahl.getComponents()) {
			if (cp == tTastatur) {
				tastaturda = true;
			}
			if (cp == mMaus) {
				mausda = true;
			}
		}
		if (guiTyp == Controller.BASISGUILAUSCHER || guiTyp == Controller.SWINGGUI) {

			if (tastaturda) {
				lKomponentenAuswahl.remove(tTastatur);
			}
			if (mausda) {
				lKomponentenAuswahl.remove(mMaus);
			}
			for (Component component : lKomponentenAuswahl.getComponents()) {
				if (component instanceof JToggleButton) {
					component.setPreferredSize(new Dimension(100, 25));
				}

			}
			lKomponentenAuswahl.revalidate();

		}
		if (guiTyp == Controller.BASISGUISCHLEIFE) {
			if (!tastaturda) {
				lKomponentenAuswahl.add(tTastatur, 0);

			}
			if (!mausda) {
				lKomponentenAuswahl.add(mMaus, 0);
			}

			for (Component component : lKomponentenAuswahl.getComponents()) {
				if (component instanceof JToggleButton) {
					component.setPreferredSize(new Dimension(100, 25));
				}

			}
			lKomponentenAuswahl.revalidate();
		}
	}
}
