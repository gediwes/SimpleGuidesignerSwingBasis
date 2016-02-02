package guidesigner.oberflaeche;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import guidesigner.control.Controller;
import guidesigner.eigenschaften.komponenten.KomponentenBasisInfo;
import guidesigner.eigenschaften.komponenten.KomponentenGrundInfo;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Georg Dick
 */
public class MovePanel extends JPanel {

	private SAnfasserSatz anfs;
	private boolean bearbeitbar = true;

	/**
	 * entfernt Anfasser, nur beim Abbau der Komponenten sinnvoll
	 */
	public void entferneAnfasser() {
		if (anfs != null) {
			anfs.entferneAnfasser();
		}
		anfs = null;
	}

	/**
	 * @return liefert bearbeitbar
	 */
	public boolean isBearbeitbar() {
		return bearbeitbar;
	}

	/**
	 * @param bearbeitbar
	 *            setzt bearbeitbar
	 */
	public void setBearbeitbar(boolean bearbeitbar) {
		this.bearbeitbar = bearbeitbar;
		if (!bearbeitbar) {
			deaktiviereAnfasser();
			// for (java.awt.event.MouseListener
			// ml:transparenzSchichtBewegung.getMouseListeners()) {
			// System.out.println("gefunden " + ml);
			// }
			//
			// while
			// (objectInFeld(transparenzSchichtBewegung.getMouseListeners(),
			// mouseReaktion)) {
			// transparenzSchichtBewegung.removeMouseListener(mouseReaktion);
			// }
			// while (objectInFeld(
			// transparenzSchichtBewegung.getMouseMotionListeners(),
			// mouseReaktion)) {
			// transparenzSchichtBewegung
			// .removeMouseMotionListener(mouseReaktion);
			// }

		}
		// else {
		// if (!objectInFeld(transparenzSchichtBewegung.getMouseListeners(),
		// mouseReaktion)) {
		// transparenzSchichtBewegung.addMouseListener(mouseReaktion);
		// }
		// if (!objectInFeld(
		// transparenzSchichtBewegung.getMouseMotionListeners(),
		// mouseReaktion)) {
		// transparenzSchichtBewegung
		// .addMouseMotionListener(mouseReaktion);
		// }
		// }
	}

	private boolean objectInFeld(Object[] marr, Object m) {
		for (Object ml : marr) {
			if (m == ml) {
				return true;
			}
		}
		return false;
	}

	private boolean anfasseraktiv = false;
	private JLabel transparenzSchichtBewegung;
	private JLayeredPane layeredPane;
	protected JPanel anzeigepanel;
	protected JComponent inhaltsKomponente;
	private Controller controller;

	/**
	 * @return liefert inhaltsKomponente
	 */
	public JComponent getInhaltsKomponente() {
		return inhaltsKomponente;
	}

	/**
	 * @param inhaltsKomponente
	 *            setzt inhaltsKomponente
	 */
	public void setInhaltsKomponente(JComponent inhaltsKomponente) {
		this.inhaltsKomponente = inhaltsKomponente;
		anzeigepanel.removeAll();
		anzeigepanel.add(inhaltsKomponente, BorderLayout.CENTER);
	}

	/**
	 * @return liefert transparenzSchichtBewegung
	 */
	public JLabel getTransparenzSchichtBewegung() {
		return transparenzSchichtBewegung;
	}

	public void ueberdeckeMitTransparenzschichtFuerBewegung(boolean ueberdecken) {
		if (ueberdecken) {
			layeredPane.moveToFront(transparenzSchichtBewegung);
		} else {
			layeredPane.moveToFront(anzeigepanel);
		}
	}

	/**
	 * @return liefert anfasseraktiv
	 */
	public boolean isAnfasseraktiv() {
		return anfasseraktiv;
	}

	/**
	 * @return liefert layeredPane
	 */
	public JLayeredPane getLayeredPane() {
		return layeredPane;
	}

	/**
	 * @return liefert anzeigepanel
	 */
	public JPanel getAnzeigepanel() {
		return anzeigepanel;
	}

	public MovePanel(Controller controller) {
		super();
		this.controller = controller;
		layeredPane = new JLayeredPane();
		this.setLayout(new BorderLayout(0, 0));
		add(layeredPane, BorderLayout.CENTER);
		anzeigepanel = new JPanel();
		anzeigepanel.setLayout(new BorderLayout());
		layeredPane.add(anzeigepanel, 10);
		transparenzSchichtBewegung = new JLabel();
		transparenzSchichtBewegung.setLayout(null);
		layeredPane.add(transparenzSchichtBewegung);
		setBorder(new LineBorder(new Color(0, 0, 255), 3));
		beweglicheKomponente = this;
		transparenzSchichtBewegung.addMouseListener(mouseReaktion);
		transparenzSchichtBewegung.addMouseMotionListener(mouseReaktion);
		ueberdeckeMitTransparenzschichtFuerBewegung(true);
		JButton test = new JButton("test");
		setInhaltsKomponente(test);
		anfassergesperrt = false;

	}

	/**
	 * @return liefert controller
	 */
	public Controller getController() {
		return controller;
	}

	@Override
	public void paintComponent(Graphics g) {
		//
		super.paintComponent(g);
		anzeigepanel.setSize(getLayeredPane().getWidth(), getLayeredPane().getHeight());
		transparenzSchichtBewegung.setSize(layeredPane.getWidth(), layeredPane.getHeight());
		anzeigepanel.revalidate();

	}

	private void setzeRand() {
		this.setBorder(BorderFactory.createLineBorder(Color.blue, 4));
	}

	public void aktiviereAnfasser() {
		if (!anfassergesperrt) {
			this.anfasseraktiv = true;
			if (anfs == null) {
				anfs = new SAnfasserSatz(this, this.transparenzSchichtBewegung);
			}
			anfs.setzeSichtbar(true);
			controller.reagiereAufAnfasserAktivierungVon(this);
			// ueberdeckeMitTransparenzschichtFuerBewegung(true);
		}
	}

	public void deaktiviereAnfasser() {
		// ueberdeckeMitTransparenzschichtFuerBewegung(false);
		if (anfs != null) {
			anfs.setzeSichtbar(false);
		}
		this.anfasseraktiv = false;
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	public int getRasterPosX(int pos) {
		int rasterx = controller.getGUI().getRasterX();
		return rasterx == 0 ? pos : pos - pos % rasterx;
	}

	public int getRasterPosY(int pos) {
		int rastery = controller.getGUI().getRasterY();
		return rastery == 0 ? pos : pos - pos % rastery;
	}

	/**
	 * 
	 */
	public void wechsleAnfasserAktivitaet() {
		if (isAnfasseraktiv()) {
			this.deaktiviereAnfasser();
		} else {
			this.aktiviereAnfasser();
		}

	}

	MouseAdapter mouseReaktion = new MouseAdapter() {
		@Override
		public void mouseReleased(MouseEvent e) {
			if (bearbeitbar) {
				reagiereAufMausReleased(e);
			}

		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (bearbeitbar) {
				reagiereAufMausPressed(e);
			}

		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (bearbeitbar) {
				reagiereAufMausClicked(e);
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (bearbeitbar) {
				reagiereAufMausDragged(e);
			}
		}
	};

	private boolean unten = false;
	private int aktx;
	private int akty;
	private int altx = 0;
	private int alty = 0;
	private java.awt.Point mp;
	private Component beweglicheKomponente;
	private boolean anfassergesperrt;

	/**
	 * @param e
	 */
	public void reagiereAufMausReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (!e.isMetaDown()) {
				unten = false;
			}
		}
	}

	public void reagiereAufMausDragged(MouseEvent e) {

		if (!isAnfasseraktiv()) {
			if (unten) {
				mp = beweglicheKomponente.getParent().getLocationOnScreen();
				aktx = e.getXOnScreen() - mp.x;
				akty = e.getYOnScreen() - mp.y;
				if (altx != aktx || alty != akty) {
					int merkex = getLocation().x + (aktx - altx);
					int merkey = getLocation().y + (akty - alty);
					int neux = getRasterPosX(getLocation().x + (aktx - altx));
					int neuy = getRasterPosY(getLocation().y + (akty - alty));
					altx = neux - merkex + aktx;
					alty = neuy - merkey + akty;
					setLocation(neux, neuy);
				}
			}
		} else {
			unten = false;
		}

	}

	/**
	 * @param e
	 */
	public void reagiereAufMausClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (e.getClickCount() == 1) {
				this.wechsleAnfasserAktivitaet();
				if (isAnfasseraktiv()) {
					controller.aktiviereAnzeigeUeberMovePanel(this);
				}
			}
		}
		if (e.getButton() == MouseEvent.BUTTON3) {
			KomponentenBasisInfo tmp = controller.getKomponentenInfo(this);
			int ok = JOptionPane.showConfirmDialog(this,
					"<html>Komponente \"" + tmp.toString() + "\" l&ouml;schen?</html>", "", JOptionPane.YES_NO_OPTION);
			if (ok == JOptionPane.OK_OPTION) {
				controller.loescheUeberBaum(tmp);
			}
		}
	}

	/**
	 * @param e
	 */
	public void reagiereAufMausPressed(MouseEvent e) {
		controller.reagiereAufMousePressedVon(this);
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (!e.isMetaDown() && !isAnfasseraktiv()) {
				unten = true;
				mp = beweglicheKomponente.getParent().getLocationOnScreen();
				altx = e.getXOnScreen() - mp.x;
				alty = e.getYOnScreen() - mp.y;
			}
		}
	}

	public void setBeweglicheKomponente(Component movable) {
		this.beweglicheKomponente = movable;
	}

	public MouseAdapter getStandardMouseListener() {
		return mouseReaktion;
	}

	/**
	 * @param e
	 * 
	 */
	public void reagiereAufMausClickedAufBewegungsSchicht(MouseEvent e) {
		this.wechsleAnfasserAktivitaet();
	}

	/**
	 * @return
	 */
	public JComponent getJComponent() {

		return null;
	}

	/**
	 * 
	 */
	public void versetzeAnfasser() {
		if (anfs != null) {
			anfs.betteAnfasserInParentderEingebettetenKomponenteEin();
		}
	}

	public void sperreAnfasser() {
		anfassergesperrt = true;
	}

	public void entsperreAnfasser() {
		anfassergesperrt = false;

	}

	int maxWidth = 4000;
	int maxHeight = 3000;

	@Override
	public void setSize(Dimension d) {
		if (d.height > maxHeight) {
			d = new Dimension(d.width, maxHeight);
		}
		if (d.width > maxWidth) {
			d = new Dimension(maxWidth, d.height);
		}
		super.setSize(d);
	}

	@Override
	public void setSize(int w, int h) {
		if (w > maxWidth) {
			w = maxWidth;
		}
		if (h > maxHeight) {
			h = maxHeight;
		}
		super.setSize(w, h);

	}

	@Override
	public void setBounds(int x, int y, int w, int h) {
		if (w > maxWidth) {
			w = maxWidth;
		}
		if (h > maxHeight) {
			h = maxHeight;
		}
		super.setBounds(x, y, w, h);
	}

}
