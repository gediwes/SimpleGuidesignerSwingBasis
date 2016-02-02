package guidesigner.oberflaeche;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JButton;
import javax.swing.JLabel;

public class SAnfasserSatz implements MouseMotionListener, MouseListener {

	private int parentwidth;
	private int parentheight;
	private int parentx;
	private int parenty;
	private int neux;
	private int neuy;
	private int altx;
	private int alty;
	int px;
	int py;
	int wx;
	int wy;
	private int rux;
	private int ruy;

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {

	}

	public void mouseMoved(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		parentwidth = wx = eingebetteteJComponent.getWidth();
		parentheight = wy = eingebetteteJComponent.getHeight();
		parentx = px = eingebetteteJComponent.getX();
		parenty = py = eingebetteteJComponent.getY();
		rux = px + wx;
		ruy = py + wy;
		altx = e.getXOnScreen();
		alty = e.getYOnScreen();
		if (!gehoertZuAnfasser8(e.getSource())) {
			// System.out.println(Integer.parseInt(((JButton) e.getSource())
			// .getName()));
			setzeCursorFuerAlle(Integer.parseInt(((JButton) e.getSource())
					.getName()));
		} else {
			setzeCursorFuerAlle(8);
		}
	}

	private boolean gehoertZuAnfasser8(Object source) {

		return source == anfasser[8] || (source instanceof JLabel);
	}

	public void mouseReleased(MouseEvent e) {
		setzeSichtbar(true);
		update();
		setzeCursorZurueck();
	}

	public void mouseDragged(MouseEvent e) {
//		Point alt = eingebetteteJComponent.getLocation();
		neux = e.getXOnScreen();
		neuy = e.getYOnScreen();
		if (e.getSource() != anfasser[8]) {

			switch (Integer.parseInt(((JButton) e.getSource()).getName())) {
			case 0: // '\0' links oben
				px = neux - altx + px;
				py = neuy - alty + py;
				wx = parentx + parentwidth - px;
				wy = parenty + parentheight - py;
				break;

			case 1: // '\001' mitte oben
				px = parentx;
				py = neuy - alty + py;
				wx = parentwidth;
				wy = parenty + parentheight - py;
				break;

			case 2: // '\002' rechts oben
				px = parentx;
				py = neuy - alty + py;
				wx = neux - altx + wx;
				wy = parenty + parentheight - py;
				break;

			case 3: // '\003' mitte links
				px = neux - altx + px;
				py = parenty;
				wx = parentx + parentwidth - px;
				wy = parentheight;
				break;

			case 4: // '\004' mitte rechts
				px = parentx;
				py = parenty;
				wx = neux - altx + wx;
				wy = parentheight;
				break;

			case 5: // '\005' links unten
				px = neux - altx + px;
				py = parenty;
				wx = parentx + parentwidth - px;
				wy = neuy - alty + wy;
				break;

			case 6: // '\006' mitte unten
				px = parentx;
				py = parenty;
				wx = parentwidth;
				wy = neuy - alty + wy;
				break;

			case 7: // '\007' rechts unten
				px = parentx;
				py = parenty;
				wx = neux - altx + wx;
				wy = neuy - alty + wy;

				break;
			}
			rux = px + wx;
			ruy = py + wy;
			// System.out.println(px + " " + py + " " + wx + " " + wy);
			if (wx > 0 && wy > 0) {
				eingebetteteJComponent.setBounds(getRasterPosX(px),
						getRasterPosY(py), getRasterPosX(rux)
								- getRasterPosX(px), getRasterPosY(ruy)
								- getRasterPosY(py));
			}

		} else {
			px = neux - altx + px;
			py = neuy - alty + py;
			// System.out.println(px+ "  px, py "+py);
			eingebetteteJComponent.setLocation(getRasterPosX(px),
					getRasterPosY(py));
			// anfasser[8].setLocation(getRasterPosX(px), getRasterPosY(py));
		}
		altx = neux;
		alty = neuy;
		update();
//		Point neu = eingebetteteJComponent.getLocation();

	}

	//

	private transient Vector anfasserLauscher;

	private MovePanel eingebetteteJComponent;
	private JComponent anfasser[];
	private int rasterx = 5;
	private int rastery = 5;

	

	/**
	 * @param bewegPanel
	 * @param transparenzSchichtBewegung
	 */
	public SAnfasserSatz(MovePanel bewegPanel, JLabel transparenzSchichtBewegung) {
		eingebetteteJComponent = bewegPanel;
		this.erzeugeAnfasser(transparenzSchichtBewegung);
		this.betteAnfasserInParentderEingebettetenKomponenteEin();
		// anfasser[8].addMouseListener(this);
		// anfasser[8].addMouseMotionListener(this);
		update();
		this.setzeCursorZurueck();
	}

	public void betteAnfasserInParentderEingebettetenKomponenteEin() {
		if (eingebetteteJComponent != null) {
			for (int i = 0; i < 8; i++) {
				eingebetteteJComponent.getParent().add(anfasser[i]);
			}
		}

	}

	private void erzeugeAnfasser(JLabel anfasser8) {
		anfasser = new JComponent[9];

		for (int i = 0; i < 8; i++) {
			anfasser[i] = new JButton();
			anfasser[i].setMaximumSize(new Dimension(6, 6));
			anfasser[i].setMinimumSize(new Dimension(6, 6));
			anfasser[i].setSize(6, 6);
			anfasser[i].setLocation(-10, -10);
			// anfasser[i].setRolloverEnabled(false);
			anfasser[i].setBackground(Color.BLACK);
			// anfasser[i].setName((new StringBuilder(String.valueOf(i)))
			// .toString());
			anfasser[i].setName(i + "");
			anfasser[i].addMouseListener(this);
			anfasser[i].addMouseMotionListener(this);
			if (eingebetteteJComponent != null)
				eingebetteteJComponent.getParent().add(anfasser[i]);
		}
		anfasser[8] = anfasser8;
	}

	private void setzeCursorFuerAlle(int annr) {
		if (eingebetteteJComponent != null) {
			try {
				if (annr != 8) {
					for (int i = 0; i < 8; i++) {
						if (i != annr)
							anfasser[i].setCursor(anfasser[annr].getCursor());
					}
					eingebetteteJComponent
							.setCursor(anfasser[annr].getCursor());
					eingebetteteJComponent.getParent().setCursor(
							anfasser[annr].getCursor());
				} else {
					Cursor c = new Cursor(Cursor.MOVE_CURSOR);
					for (int i = 0; i < 8; i++) {
						anfasser[i].setCursor(c);
					}
					eingebetteteJComponent.setCursor(c);
					eingebetteteJComponent.getParent().setCursor(c);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void setzeCursorZurueck() {
		try {
			if (eingebetteteJComponent != null) {
				anfasser[0].setCursor(new Cursor(6));
				anfasser[1].setCursor(new Cursor(8));
				anfasser[2].setCursor(new Cursor(7));
				anfasser[3].setCursor(new Cursor(10));
				anfasser[4].setCursor(new Cursor(11));
				anfasser[5].setCursor(new Cursor(4));
				anfasser[6].setCursor(new Cursor(9));
				anfasser[7].setCursor(new Cursor(5));
				anfasser[8].setCursor(new Cursor(Cursor.MOVE_CURSOR));
				for (Component c : anfasser[8].getComponents()) {
					c.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
				eingebetteteJComponent.getParent().setCursor(
						new Cursor(Cursor.DEFAULT_CURSOR));
			} else {
				// System.out.print("Kein Parent");
			}
		} catch (Exception e) {

		}

	}

	public void setzeSichtbar(boolean visible) {
		for (int i = 0; i < 8; i++) {
			anfasser[i].setVisible(visible);
			if (!visible) {
				while (objectInFeld(anfasser[8].getMouseListeners(), this)) {
					anfasser[8].removeMouseListener(this);

				}
				while (objectInFeld(anfasser[8].getMouseMotionListeners(), this)) {
					anfasser[8].removeMouseMotionListener(this);
				}
				anfasser[8].setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

			} else {
				if (!objectInFeld(anfasser[8].getMouseListeners(), this)) {
					anfasser[8].addMouseListener(this);
				}
				if (!objectInFeld(anfasser[8].getMouseMotionListeners(), this)) {
					anfasser[8].addMouseMotionListener(this);
				}
				anfasser[8].setCursor(new Cursor(Cursor.MOVE_CURSOR));
				for (Component c : anfasser[8].getComponents()) {
					c.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}

				eingebetteteJComponent.getParent().setCursor(
						new Cursor(Cursor.DEFAULT_CURSOR));
			}
		}
		update();
	}

	private boolean objectInFeld(Object[] marr, Object m) {
		for (Object ml : marr) {
			if (m == ml) {
				return true;
			}
		}
		return false;
	}

	public boolean istSichtbar() {
		return anfasser[0].isVisible();
	}

	private void update() {
		try {
			if (eingebetteteJComponent != null) {
				anfasser[0].setLocation(eingebetteteJComponent.getX() - 6,
						eingebetteteJComponent.getY() - 6);
				anfasser[1].setLocation(
						(eingebetteteJComponent.getX() + eingebetteteJComponent
								.getWidth() / 2) - 3, eingebetteteJComponent
								.getY() - 6);
				anfasser[2].setLocation(eingebetteteJComponent.getX()
						+ eingebetteteJComponent.getWidth(),
						eingebetteteJComponent.getY() - 6);
				anfasser[3].setLocation(eingebetteteJComponent.getX() - 6,
						(eingebetteteJComponent.getY() + eingebetteteJComponent
								.getHeight() / 2) - 3);
				anfasser[4].setLocation(eingebetteteJComponent.getX()
						+ eingebetteteJComponent.getWidth(),
						(eingebetteteJComponent.getY() + eingebetteteJComponent
								.getHeight() / 2) - 3);
				anfasser[5].setLocation(
						eingebetteteJComponent.getX() - 6,
						eingebetteteJComponent.getY()
								+ eingebetteteJComponent.getHeight());
				anfasser[6].setLocation(
						(eingebetteteJComponent.getX() - 3)
								+ eingebetteteJComponent.getWidth() / 2,
						eingebetteteJComponent.getY()
								+ eingebetteteJComponent.getHeight());
				anfasser[7].setLocation(
						eingebetteteJComponent.getX()
								+ eingebetteteJComponent.getWidth(),
						eingebetteteJComponent.getY()
								+ eingebetteteJComponent.getHeight());
				eingebetteteJComponent.revalidate();
				eingebetteteJComponent.repaint();
			}
		} catch (Exception e) {

		}

	}

	private int getRasterPosX(int pos) {
		rasterx = eingebetteteJComponent.getController().getGUI().getRasterX();
		return rasterx == 0 ? pos : pos - pos % rasterx;
	}

	private int getRasterPosY(int pos) {
		rastery = eingebetteteJComponent.getController().getGUI().getRasterY();
		return rastery == 0 ? pos : pos - pos % rastery;
	}

	/**
	 * entfernt den AnfasserSatz von der Komponente. Der Anfassersatz ist danach
	 * nicht mehr verfügbar.
	 */
	public void gibfrei() {
		this.setzeSichtbar(false);
		for (int i = 0; i <= 7; i++) {
			anfasser[i].removeMouseListener(this);
			anfasser[i].removeMouseMotionListener(this);
			anfasser[i].getParent().remove(anfasser[i]);
		}
		anfasser[8].removeMouseListener(this);
		anfasser[8].removeMouseMotionListener(this);
		anfasser[8].setCursor(Cursor.getDefaultCursor());
		this.eingebetteteJComponent = null;
	}

	

	public void entferneAnfasser() {
		anfasserLauscher = null;
		for (int i = 0; i < 8; i++) {
			MouseListener[] ml = anfasser[i].getMouseListeners();
			for (int j = 0; j < ml.length; j++) {
				anfasser[i].removeMouseListener(ml[j]);
			}
			Container p = anfasser[i].getParent();
			if (p != null) {
				p.remove(anfasser[i]);
			}

		}
	}

	

}
