package basisx.swing;

import java.awt.*; //import java.awt.image.BufferedImage;
import java.awt.image.BufferedImage; //
import java.util.Vector; //import javax.swing.JComponent;
//import javax.swing.JPanel;
//import basis.*;
import basisx.swing.PenPane;

/**
 * @author Georg Dick
 * @version 7.1.2010
 */
public class Pen {
	/** Stiftmodi als Klassenkonstante */
	public static final int NORMALMODUS = 0;
	public static final int RADIERMODUS = 1;
	public static final int WECHSELMODUS = 2;
	public static final int GESTRICHELT = 1;
	public static final int DURCHGEZOGEN = 0;

	
	public static final String DIALOG = "Dialog";
	public static final String ARIAL = "Arial";
	public static final String COMIC = "Comic Sans MS";
	public static final String HELVETICA = "Helvetica";
	public static final String LUCIDA = "Lucida Sans";
	public static final String TIMESROMAN = "Times New Roman";
	public static final String STANDARDSCHRIFTART = DIALOG;
	public static final int ITALIC = Font.ITALIC;
	public static final int BOLD = Font.BOLD;
	public static final int ITALICBOLD = Font.ITALIC + Font.BOLD;
	public static final int STANDARDSTIL = Font.PLAIN;
	public static final int STANDARDGROESSE = 12;
	public static final Font STANDARDFONT = new Font(STANDARDSCHRIFTART,
			STANDARDSTIL, STANDARDGROESSE);
	

	public final static int DURCHSICHTIG = 0;
    public final static int GEFUELLT = 1;
	
	private String aktuellerFont = STANDARDSCHRIFTART;
	private int schriftStil = STANDARDSTIL;
	private int schriftGroesse = STANDARDGROESSE;
	private Font schriftArt = STANDARDFONT;
	private Color farbe = Color.BLACK;
	private int linienBreite = 1;
	private int linienTyp = DURCHGEZOGEN;
	private double alphaWert = 1.0;

	private int muster = DURCHSICHTIG;
	private float[] dash;

	/**
	 * Ein Stift muss seinen Arbeitsbereich kennen. Aus ihm bezieht er u.a. den
	 * Grafikkontext. Der Arbeitsbereich kann eine Swingkomponente sein, ein
	 * BufferedImage oder ein PenPane. Ist der Arbeitsbereich ein PenListener, so wird dieser vom
	 * Stift automatisch ueber PenPainted-Events informiert.
	 */

	public static final int canvasIsSwing = 0;
	public static final int canvasIsBufferedImage = 1;
	public static final int canvasIsPenPane = 2;
	protected int arbeitsbereichsTyp = -1;
	protected Container kenntSwingkomponente;
	protected BufferedImage kenntbi;
	protected PenPane penPane;

	/**
	 * Stiftposition
	 */
	protected double stiftx = 0;
	protected double stifty = 0;
	/** Stiftzustand */
	protected boolean stiftOben = true;
	/** Stiftrichtung */
	protected double stiftRichtung = 0;
	/** StandardModus ist NORMALMODUS */
	protected int schreibModus = NORMALMODUS;
	protected Graphics2D gr;

	private boolean fontveraendert = false;

	private PenState merkeZustand;
	// Aufruf von Toolkit sync nach jeder Zeichenoperation
	// (verlangsamt, ist moeglicherweise unnoetig)
	private boolean toolkitsyncAn = false;
	/**
	 * erzeugt einen Stift fuer eine Swing-Komponente. Die Position ist (0,0),
	 * der Normalmodus ist eingestellt, die Farbe ist Farbe.SCHWARZ, die
	 * verwendeten Schriftattribute sind auf Standardwerte eingestellt (s.
	 * Klasse Schrift). Der Stift ist angehoben.
	 */
	public Pen(Object unterlage) {
		this.setCanvas(unterlage);
		this.merkeZustand();
	}
//	/**
//	 * erzeugt einen Stift fuer eine Swing-Komponente. Die Position ist (0,0),
//	 * der Normalmodus ist eingestellt, die Farbe ist Farbe.SCHWARZ, die
//	 * verwendeten Schriftattribute sind auf Standardwerte eingestellt (s.
//	 * Klasse Schrift). Der Stift ist angehoben.
//	 */
//	public Pen(Container swingKomponente) {
//		this.setCanvas(swingKomponente);
//		this.merkeZustand();
//	}
//
//	/**
//	 * erzeugt einen Stift. Die Position ist (0,0), der Normalmodus ist
//	 * eingestellt, die Farbe ist Farbe.SCHWARZ, die verwendeten
//	 * Schriftattribute sind auf Standardwerte eingestellt (s. Klasse Schrift).
//	 * Der Stift ist angehoben.
//	 */
//
//	public Pen(BufferedImage bi) {
//		this.setCanvas(bi);
//		this.merkeZustand();
//	}
//
//	/**
//	 * erzeugt einen Stift. Die Position ist (0,0), der Normalmodus ist
//	 * eingestellt, die Farbe ist Farbe.SCHWARZ, die verwendeten
//	 * Schriftattribute sind auf Standardwerte eingestellt (s. Klasse Schrift).
//	 * Der Stift ist angehoben.
//	 */
//
//	public Pen(PenPane pp) {
//		this.setCanvas(pp);
//		this.merkeZustand();
//	}

	/**
	 * Der Stift wird angewiesen auf der Unterlage zu malen. Der Stift wird in
	 * seinen Standardzustand versetzt.
	 * 
	 * @param arbeitsbereich
	 *            Arbeitsbereich fuer den Stift
	 * 
	 */
	public void setCanvas(Object arbeitsbereich) {
		if (arbeitsbereich instanceof PenPane) {
			kenntSwingkomponente = null;
			kenntbi = null;
			penPane = (PenPane) arbeitsbereich;
			arbeitsbereichsTyp = canvasIsPenPane;
			this.setzeStandard();
		} else if (arbeitsbereich instanceof Container) {
			kenntSwingkomponente = (Container) arbeitsbereich;
			kenntbi = null;
			penPane = null;
			arbeitsbereichsTyp = canvasIsSwing;
			this.setzeStandard();
		} else if (arbeitsbereich instanceof BufferedImage) {
			kenntSwingkomponente = null;
			kenntbi = (BufferedImage) arbeitsbereich;
			penPane = null;
			arbeitsbereichsTyp = canvasIsBufferedImage;
			this.setzeStandard();
		}
		if (arbeitsbereich instanceof PenListener){
			if (penListeners!=null){
				penListeners.removeAllElements();// Es kann momentan nur einen PenListener geben
			}
			this.addPenListener((PenListener)arbeitsbereich);
		}
		

	}

	/**  */
	// protected void sync() {
	// if (kenntSwingkomponente != null) {
	// kenntSwingkomponente.repaint();
	// // this.syncanforderungvorhanden=true;
	// // if (!syncthreadstarted){
	// // this.startSyncThread();
	// // }
	// if (toolkitsyncAn) {
	// Toolkit.getDefaultToolkit().sync();
	// }
	//
	// } else {
	// if (stiftflaeche instanceof StiftFlaeche) {
	// ((StiftFlaeche) this.stiftflaeche).aktualisiereDarstellung();
	// }
	//
	// // System.out.println("aktu");
	// }
	// }
	protected void sync() {
		this.firePenPainted();
	}

	public void fuelle(Color fuellfarbe, Color randfarbe) {
		this
				.fuelleAn(this.hPosition(), this.vPosition(), fuellfarbe,
						randfarbe);
	}

	public void fuelleAn(double hPosition, double vPosition, Color fuellfarbe,
			Color randfarbe) {
		int x = (int) Math.round(hPosition);
		int y = (int) Math.round(vPosition);
		this.setzeFarbe(fuellfarbe);
		Graphics2D gr = this.grafikKontext();
		setGZustand(gr);
		int modus = schreibModus;
		this.normal();
		if (this.arbeitsbereichsTyp == canvasIsBufferedImage) {
			try {
				this.fill(x, y, fuellfarbe.getRGB(), randfarbe.getRGB(), gr,
						kenntbi);
				// this.fill(x,y,fuellfarbe,randfarbe);
			} catch (Exception e) {
			}
			schreibModus = modus;
			gr = null;
			this.sync();
		} else if (this.arbeitsbereichsTyp == canvasIsPenPane) {
			try {
				this.fill(x, y, fuellfarbe.getRGB(), randfarbe.getRGB(), gr,
						penPane.getBufferedImage());
				// this.fill(x,y,fuellfarbe,randfarbe);
			} catch (Exception e) {
			}
			schreibModus = modus;
			gr = null;
			this.sync();
		}
	}

	/**
	 * 
	 * @param px
	 *            Startpunkt fuer die Fuellung
	 * @param py
	 *            Startpunkt fuer die Fuellung
	 * @param fuellfarbe
	 * @param randfarbe
	 * @param gr
	 *            Graphicsobjekt
	 * @param bi
	 *            BufferedImage auf dem gemalt wird
	 */
	private void fill(int px, int py, int fuellfarbe, int randfarbe,
			Graphics2D gr, BufferedImage bi) {
		int x = px, y = py;
		int c = bi.getRGB(x, y);
		int xa, xe;
		boolean faerbung = false;
		while (c != randfarbe && c != fuellfarbe && x > 0) {
			faerbung = true;
			x--;
			c = bi.getRGB(x, y);
		}
		if (!faerbung) {
			return;
		}
		x++;
		xa = x;
		c = bi.getRGB(x, y);
		while (c != randfarbe && c != fuellfarbe && x < bi.getWidth() - 1) {
			x++;
			c = bi.getRGB(x, y);
		}
		xe = x - 1;
		gr.drawLine(xa, y, xe, y);
		x = xa;
		if (py - 1 > 0) {
			while (x <= xe) {
				c = bi.getRGB(x, py - 1);
				if (c != randfarbe && c != fuellfarbe) {
					fill(x, py - 1, fuellfarbe, randfarbe, gr, bi);
				}
				x++;
			}
		}
		x = xa;
		if (py + 1 < bi.getHeight()) {
			while (x <= xe) {
				c = bi.getRGB(x, py + 1);
				if (c != randfarbe && c != fuellfarbe) {
					fill(x, py + 1, fuellfarbe, randfarbe, gr, bi);
				}
				x++;
			}
		}

	}

	/**
	 * versetzt den Stift zur die angegebene Position. Ist der Stift abgesenkt
	 * wird gezeichnet. Modus und Farbe werden beruecksichtigt. Der Stift aendert
	 * seine Position auf den Endpunkt der Bewegung. Seine Ausrichtung wird
	 * nicht veraendert
	 */
	public void bewegeBis(double px, double py) {
		// Vektor2D v = new Vektor2D();
		// v.setzeDxUndDy(px - stiftx, py - stifty);
		// this.stiftRichtung = v.getRichtung();
		if (!stiftOben) {
			this.linie(stiftx, stifty, px, py);
			this.sync();
		}
		stiftx = px;
		stifty = py;
	}

	/**
	 * bewegt den Stift auf die angegebene Position. Ist der Stift abgesenkt
	 * wird gezeichnet. Modus und Farbe werden beruecksichtigt. Der Stift aendert
	 * seine Position auf den Endpunkt der Bewegung. Seine Ausrichtung wird der
	 * Bewegung angepasst
	 */
	public void bewegeAuf(double px, double py) {
		Vektor2D v = new Vektor2D();
		v.setzeDxUndDy(px - stiftx, py - stifty);
		this.stiftRichtung = v.getRichtung();
		if (!stiftOben) {
			this.linie(stiftx, stifty, px, py);
			this.sync();
		}
		stiftx = px;
		stifty = py;
	}

	/**  */
	public Graphics2D grafikKontext() {
		if (kenntbi != null) {
			return (Graphics2D) kenntbi.getGraphics();
		}
		if (kenntSwingkomponente != null) {
			return (Graphics2D) this.kenntSwingkomponente.getGraphics();
		}
		if (penPane != null) {
			return (Graphics2D) this.penPane.getBufferedImageGraphics();
		}
		return null;
	}

	/**
	 * bewegt den Stift um die angegebene Entfernung in Pixeln. Ist der Stift
	 * abgesenkt wird gezeichnet. Ausrichtung, Modus und Farbe werden
	 * beruecksichtigt. Der Stift aendert seine Position auf den Endpunkt der
	 * Bewegung.
	 */
	public void bewegeUm(double pl) {
		double w;
		double x, y;
		w = stiftRichtung * Math.PI / 180;
		x = stiftx + pl * Math.cos(w);
		y = stifty - pl * Math.sin(w);
		if (!stiftOben) {
			linie(stiftx, stifty, x, y);
			this.sync();
		}
		stiftx = x;
		stifty = y;

	}

	/**
	 * bewegt den Stift in Richtung des Vektors um die Entfernung des Vektors in
	 * Pixeln. Ist der Stift abgesenkt wird gezeichnet. Ausrichtung, Modus und
	 * Farbe werden beruecksichtigt. Der Stift aendert seine Position auf den
	 * Endpunkt der Bewegung. Die Ausrichtung entspricht der des Vektors.
	 */
	public void bewegeUm(Vektor2D v) {
		stiftRichtung = v.getRichtung();
		this.bewegeUm(v.getLaenge());
	}

	/**
	 * aendert die Ausrichtung des Stiftes. Der Stift wird auf den gegebenen
	 * Winkel ausgerichtet.
	 */
	public void dreheBis(double w) {
		stiftRichtung = w;
		while (stiftRichtung < 0) {
			stiftRichtung = stiftRichtung + 360;
		}
		while (stiftRichtung >= 360) {
			stiftRichtung = stiftRichtung - 360;
		}
	}

	/**
	 * legt fest ob und in welchem Grad die Stiftfarbe transparent gezeichnet
	 * wird
	 * 
	 * @param alpha
	 *            Grad zwischen 0.0 und 1.0
	 */
	public void setzeTransparenzGrad(double alpha) {
		this.alphaWert = alpha;
	}

	/**
	 * 
	 * @return liefert den Grad der Transparenz mit der der Stift zeichnet
	 */
	public double transparenzGrad() {
		return alphaWert;
	}

	/**
	 * aendert die Ausrichtung des Stiftes. Der Stift wird auf den gegebenen
	 * Punkt(x,y) ausgerichtet.
	 */
	public void dreheInRichtung(double x, double y) {
		Vektor2D v = new Vektor2D(stiftx, stifty, x, y);
		stiftRichtung = v.getRichtung();
	}

	/**
	 * aendert die Ausrichtungdes Stiftes um den angegebene Winkel. Positive
	 * Werte fuehren zu Aenderungen gegen den Uhrzeigersinn, negative zu
	 * Aenderuungen im Uhrzeigersinn
	 */
	public void dreheUm(double w) {
		stiftRichtung = stiftRichtung + w;
		while (stiftRichtung < 0) {
			stiftRichtung = stiftRichtung + 360;
		}
		while (stiftRichtung >= 360) {
			stiftRichtung = stiftRichtung - 360;
		}
	}

	/** hebt den Stift an */
	public void hoch() {
		stiftOben = true;
	}

	/** senkt den Stift ab */
	public void runter() {
		stiftOben = false;
	}

	/** setzt den Stift auf den Normalmodus */
	public void normal() {
		schreibModus = NORMALMODUS;
	}

	/** setzt den Stift auf den Radiermodus */
	public void radiere() {
		schreibModus = RADIERMODUS;
	}

	/** setzt den Stift auf den Wechslemodus */
	public void wechsle() {
		schreibModus = WECHSELMODUS;
	}

	/**
	 * Bestimmt die Stiftfarbe. In der Klasse Farbe werden Konstante und eine
	 * Funktion zur Farbwahl angeboten
	 */
	public void setzeFarbe(Color farbe) {
		this.farbe = farbe;
	}

	/** Festlegung von Lininebreiten. */
	public void setzeLinienBreite(int pb) {
		linienBreite = pb;
	}

	/**
	 * Festlegung von Lininetypen.
	 * 
	 * @param lt
	 *            Gestrichelt mit 10 zu 2 Pixeln oder Durchgezogen
	 */
	public void setzeLinienTyp(int lt) {
		dash = new float[2];
		dash[0] = 10;
		dash[1] = 2;
		linienTyp = lt;
	}

	/**
	 * bestimmt ein Strichmuster
	 * 
	 * @param solid
	 *            Anzahl gezeichneter Pixel
	 * @param durchsichtig
	 *            Anzahl nicht gezeichneter Pixel
	 */
	public void setzeLinienTyp(float solid, float durchsichtig) {
		dash = new float[2];
		dash[0] = solid;
		dash[1] = durchsichtig;
		linienTyp = GESTRICHELT;
	}

	/**
	 * bestimmt das Fuellmuster fuer Kreis und Rechteck. Fuer die Muster stehen in
	 * der Klasse Muster Konstanten zur Verfuegung
	 */
	public void setzeFuellMuster(int muster) {
		this.muster = muster;
	}

	/** setzt den Stiftzustand auf Standardwerte zurueck */
	public void setzeStandard() {
		stiftx = 0;
		stifty = 0; // Stiftposition
		stiftOben = true; // Stiftzustand
		stiftRichtung = 0; // Stiftrichtung
		schreibModus = NORMALMODUS; // Normalmodus
		aktuellerFont = STANDARDSCHRIFTART;
		schriftStil =STANDARDSTIL;
		schriftGroesse = STANDARDGROESSE;
		schriftArt = STANDARDFONT;
		farbe = Color.black;
		linienBreite = 1;
		muster = DURCHSICHTIG;
		linienTyp = 0;
	}

	/**
	 * bestimmt Schriftart, Schriftstil und Schriftgroesse. Die Klasse Schrift
	 * stellt passende Konstanten zur Verfuegung
	 */
	public void setzeSchrift(String art, int stil, int groesse) {
		aktuellerFont = art;
		schriftGroesse = groesse;
		schriftStil = stil;
	}

	/**
	 * bestimmt die Schriftart. Die moeglichen Schriftarten sind in der Klasse
	 * Schrift als Konstanten festgelegt
	 */
	public void setzeSchriftArt(String schriftart) {
		aktuellerFont = schriftart;
		schriftArt = new Font(aktuellerFont, schriftStil, schriftGroesse);
		fontveraendert = true;
	}

	/**
	 * bestimmt die Schriftart.
	 */
	public void setzeSchriftArt(Font fschriftart) {
		aktuellerFont = fschriftart.getFontName();
		schriftArt = fschriftart;
		fontveraendert = true;
	}

	/** legt die Schriftgroesse in Pixeln fest */
	public void setzeSchriftGroesse(int schriftgroesse) {
		schriftGroesse = schriftgroesse;
		schriftArt = new Font(aktuellerFont, schriftStil, schriftGroesse);
		// zschriftart = zschriftart.deriveFont((float)schriftgroesse);
		fontveraendert = true;
	}

	/**
	 * bestimmt den Schriftstil. Die moeglichen Stile sind in der Klasse Schrift
	 * als Konstanten festgelegt
	 */
	public void setzeSchriftStil(int schriftstil) {
		schriftStil = schriftstil;
		schriftArt = null;
		schriftArt = new Font(aktuellerFont, schriftStil, schriftGroesse);
		fontveraendert = true;
	}

	/**
	 * schreibt den Text t an die aktuelle Stiftposition in horizontaler
	 * Richtung. Anschliessend steht der Stift hinter dem Text.
	 */
	public void schreibe(char t) {
		this.schreibe(t + "");
	}

	/**
	 * schreibt den Text t an die aktuelle Stiftposition in horizontaler
	 * Richtung. Anschliessend steht der Stift hinter dem Text.
	 */
	public void schreibeText(String t) {
		this.schreibe(t);
	}

	/**
	 * schreibt den Text t an die aktuelle Stiftposition in horizontaler
	 * Richtung. Anschliessend steht der Stift hinter dem Text.
	 * 
	 */
	public void schreibeText(char t) {
		this.schreibe(t);
	}

	/**
	 * schreibt die Zahl z an die aktuelle Stiftpositionin horizontaler
	 * Richtung. Anschliessend steht der Stift hinter der Zahl
	 */
	public void schreibeZahl(double z) {
		this.schreibe(z + "");
	}

	/**
	 * schreibt die Zahl z an die aktuelle Stiftposition in horizontaler
	 * Richtung. Anschliessend steht der Stift hinter der Zahl
	 */
	public void schreibeZahl(int z) {
		this.schreibe(z + "");
	}

	/**
	 * zeichnet einen Kreis. Die Stiftposition bestimmt den Mittelpunkt. Der
	 * Radius wird ueber den Parameter festgelegt, Stiftposition und -richtung
	 * werden nicht beeinflusst. Es wird auch gezeichnet, wenn der Stift
	 * angehoben ist. Gesetzte Fuellmuster werden beruecksichtigt
	 */
	public void zeichneKreis(double pradius) {
		int x1, y1, d;
		x1 = (int) Math.round(stiftx - pradius);
		y1 = (int) Math.round(stifty - pradius);
		d = (int) Math.round(2 * pradius);
		gr = this.grafikKontext();
		if (gr != null) {
			setGZustand(gr);
			if (muster == DURCHSICHTIG) {
				gr.drawOval(x1, y1, d, d);
			} else {
				if (muster ==  GEFUELLT) {
					gr.fillOval(x1, y1, d, d);
				}
			}
		}
		this.sync();
		gr = null;
	}

	/**
	 * zeichnet ein Rechteck. Der Stift beginnt in Bewegungsrichtung mit der
	 * Seitenlaenge Breite (erster Parameter) und dreht dann jeweils nach rechts
	 * Breite und Hoehe werden ueber die Parameter bestimmt. Stiftposition und
	 * -richtung werden nicht beeinflusst. Es wird auch gezeichnet, wenn der
	 * Stift angehoben ist. Gesetzte Fuellmuster werden beruecksichtigt.
	 */
	public void zeichneRechteck(double pbreite, double phoehe) {
		if (stiftRichtung == 0) {
			int x1, y1, x2, y2;
			x1 = (int) Math.round(stiftx);
			x2 = (int) Math.round(pbreite) - 1;
			y1 = (int) Math.round(stifty);
			y2 = (int) Math.round(phoehe) - 1;
			gr = this.grafikKontext();
			if (gr != null) {
				setGZustand(gr);
				if (muster ==  DURCHSICHTIG) {
					gr.drawRect(x1, y1, x2, y2);
				} else {
					if (muster ==  GEFUELLT) {
						gr.fillRect(x1, y1, x2 + 1, y2 + 1);
						/* fillrect zeichnet kleinere Rechtecke als drawrect ! */
					}
				}
			}
			this.sync();
			gr = null;
			return;
		}
		double[] x = new double[4];
		double[] y = new double[4];
		x[0] = stiftx;
		y[0] = stifty;
		Vektor2D v = new Vektor2D(stiftRichtung, pbreite);
		x[1] = stiftx + v.getDx();
		y[1] = stifty + v.getDy();
		v = new Vektor2D(stiftRichtung - 90, phoehe);
		x[2] = x[1] + v.getDx();
		y[2] = y[1] + v.getDy();
		v = new Vektor2D(stiftRichtung - 180, pbreite);
		x[3] = x[2] + v.getDx();
		y[3] = y[2] + v.getDy();
		this.polygon(x, y);

	}

	/**
	 * zeichnet einen Kreis mit Mittelpunkt (x,y) und vorgegebenem Radius.
	 * Stiftposition ist anschliessend der Mittelpunkt des Kreises. Die
	 * Stiftrichtung wird nicht beeinflusst. Die Bewegung des Stiftes zum
	 * Mittelpunkt wird nicht gezeichnet. Es wird auch gezeichnet, wenn der
	 * Stift angehoben ist. Gesetzte Fuellmuster werden beruecksichtigt
	 */
	public void kreis(double x, double y, double radius) {
		int x1, y1, d;
		x1 = (int) Math.round(x - radius);
		y1 = (int) Math.round(y - radius);
		d = (int) Math.round(2 * radius);
		gr = this.grafikKontext();
		if (gr != null) {
			setGZustand(gr);
			if (muster ==  DURCHSICHTIG) {
				gr.drawOval(x1, y1, d, d);
			} else {
				if (muster ==  GEFUELLT) {
					gr.fillOval(x1, y1, d, d);
				}
			}
		}
		this.sync();
		gr = null;
		stiftx = x;
		stifty = y;
	}

	/**
	 * zeichnet ein achsenparalleles Rechteck mit der linken oberen Ecke (x,y),
	 * vorgegebener Breite und Hoehe. Stiftposition und -richtung werden nicht
	 * beeinflusst. Es wird auch gezeichnet, wenn der Stift angehoben ist
	 * Gesetzte Fuellmuster werden beruecksichtigt
	 */
	public void rechteck(double x, double y, double breite, double hoehe) {
		int x1, y1, x2, y2;
		x1 = (int) Math.round(x);
		x2 = (int) Math.round(breite) - 1;
		y1 = (int) Math.round(y);
		y2 = (int) Math.round(hoehe) - 1;
		gr = this.grafikKontext();
		if (gr != null) {
			setGZustand(gr);
			if (muster == DURCHSICHTIG) {
				gr.drawRect(x1, y1, x2, y2);
			} else {
				if (muster == GEFUELLT) {
					gr.fillRect(x1, y1, x2 + 1, y2 + 1);

					/* fillrect zeichnet kleinere Rechtecke als drawrect ! */
				}
			}
		}
		this.sync();
		gr = null;
	}

	/**
	 * zeichnet eine Strecke vom aktuellen Standort des Stiftes bis zum Punkt
	 * (x,y). Der Stift steht anschliessend bei (x,y) und in seiner
	 * Bewegungsrichtung. Es wird auch gezeichnet, wenn der Stift angehoben ist.
	 * 
	 * 
	 */
	public void zeichneLinie(double x, double y) {
		Vektor2D v = new Vektor2D();
		v.setzeDxUndDy(x - stiftx, y - stifty);
		stiftRichtung = v.getRichtung();
		this.linie(stiftx, stifty, x, y);
	}

	/**
	 * zeichnet eine Strecke mit den Endpunkte (x1,y1) und (x2,y2). Es wird auch
	 * gezeichnet, wenn der Stift angehoben ist. StiftPosition und StiftRichtung
	 * werden nicht veraendert.
	 * 
	 */
	public void linie(double x1, double y1, double x2, double y2) {
		int ix1, ix2, iy1, iy2;
		ix1 = (int) Math.round(x1);
		iy1 = (int) Math.round(y1);
		ix2 = (int) Math.round(x2);
		iy2 = (int) Math.round(y2);
		gr = this.grafikKontext();
		setGZustand(gr);
		if (gr != null) {
			gr.drawLine(ix1, iy1, ix2, iy2);
		}
		gr = null;
		this.sync();
		// Vektor2D v = new Vektor2D();
		// v.setzeDxUndDy(x2 - x1, y2 - y1);
		// stiftRichtung = v.getRichtung();
		// // System.out.println(zwinkel);
		// stiftx = x2;
		// stifty = y2;
	}

	/**
	 * liefert die Ausrichtung des Stiftes (nach rechts Null Grad, Winkelzunahme
	 * gegen den Uhrzeigersinn
	 */
	public double winkel() {
		return stiftRichtung;
	}

	/** liefert die Farbe des Stiftes */
	public Color farbe() {
		return farbe;
	}

	/** liefert den Wert wahr, wenn der Stift angehoben ist */
	public boolean istOben() {
		return stiftOben;
	}

	/** liefert die Breite des Textes s in Pixeln */
	public int textBreite(String s) {
		gr = this.grafikKontext();
		setGZustand(gr);
		if (gr != null) {
			try {
				return gr.getFontMetrics().stringWidth(s);
			} catch (Exception e) {
			}
		}
		return 0;
	}

	/** liefert die Hoehe der eingestellten Schriftart in Pixeln */
	public int textHoehe() {
		gr = this.grafikKontext();
		setGZustand(gr);
		if (gr != null) {
			try {
				return gr.getFontMetrics().getHeight();
			} catch (Exception e) {
			}
		}
		return 0;
	}

	/** liefert die Fuellmusterkonstante */
	public int fuellMuster() {
		return muster;
	}

	/** gibt alle Ressourcen frei. Der Stift ist danach nicht mehr verwendbar */
	public void gibFrei() {
		schriftArt = null;
		farbe = null;

	}

	/**
	 * schreibt den Text t an die aktuelle Stiftposition in horizontaler
	 * Richtung. Anschliessend steht der Stift hinter dem Text. *
	 */
	public void schreibe(String ps) {
		// new Thread() {
		// public void run() {
		int zx = (int) Math.round(stiftx);
		int zy = (int) Math.round(stifty);
		int dx = 0;
		gr = Pen.this.grafikKontext();

		setGZustand(gr);
		if (gr != null) {
			gr.drawString(ps, zx, zy);
			try {
				dx = gr.getFontMetrics().stringWidth(ps);
			} catch (Exception e) {
			}
		}
		stiftx = stiftx + dx;
		stiftRichtung = 0;
		gr = null;
		Pen.this.sync();
		// }
		// }.start();

	}

	/**  */
	protected void setGZustand(Graphics2D g) {
		if ((g != null)) {
			if (schreibModus == NORMALMODUS) {
				g.setPaintMode();
				if (this.farbe.equals(new Color (255,255,255,0))) {
					g.setComposite(AlphaComposite.Src);
				} else {
					AlphaComposite ac1 = AlphaComposite.getInstance(
							AlphaComposite.SRC_OVER, (float) alphaWert);
					g.setComposite(ac1);
				}
				g.setColor(farbe);
			} else if (schreibModus == RADIERMODUS) {
				g.setPaintMode();
				g.setComposite(AlphaComposite.Src);
				// System.out.println(hgColor().equals(Farbe.DURCHSICHTIG));
				g.setColor(hgColor());

			} else {
				g.setColor(farbe);
				g.setXORMode(this.hgColor());
			}
			if (fontveraendert || (schriftArt != g.getFont())) {
				// Font f = new Font("Dialog", Font.PLAIN, 18);
				// g.setFont(f);
				g.setFont(schriftArt);
				fontveraendert = false;

			}
			Stroke stroke;
			if (linienTyp == DURCHGEZOGEN) {
				stroke = new BasicStroke(linienBreite);

			} else {

				stroke = new BasicStroke(linienBreite, BasicStroke.CAP_BUTT,
						BasicStroke.JOIN_MITER, 1, dash, 0);
			}
			((Graphics2D) g).setStroke(stroke);

		} else { /* System.out.println("Kein g"); */
		}
	}

	private Color hgColor() {
		switch (arbeitsbereichsTyp) {
		case canvasIsBufferedImage:
			return Color.white;
		case canvasIsPenPane:
			return penPane.getFillColor();
		case canvasIsSwing:
			return kenntSwingkomponente.getBackground();
		}

		return new Color (255,255,255,0);
	}

	/**
	 * zeichnet einen Kreisbogen. Der Mittelpunkt des Bogens ergibt sich aus der
	 * Stiftposition. Der Radius ueber radius. Der Bogen beginnt vom Mittelpunkt
	 * aus gesehen mit dem Winkel des Stiftes und ueberstreicht einen Winkel
	 * entsprechend der Groesse drehwinkel. Je nach Vorzeichen diese Parameters
	 * erfolgt eine Linksdrehung (+) bzw. eine Rechtsdrehung(-). Der Stift steht
	 * anschliessend in der Mitte und ist auf das Ende des Bogens ausgerichtet.
	 * Fuellmuster werden bei der Zeichnung beruecksichtigt. Es entsteht
	 * gegebenenfalls ein Kreissegment. Die Zeichnung erfolgt auch bei
	 * angehobeneme Stift.
	 * 
	 * @param radius
	 * @param drehwinkel
	 */
	public void zeichneKreisBogen(double radius, double drehwinkel) {
		// stiftRichtung += drehwinkel;
		this.kreisBogen(stiftx, stifty, radius, stiftRichtung, drehwinkel);
		stiftRichtung += drehwinkel;
	}

	/**
	 * zeichnet einen Kreisbogen. Der Mittelpunkt des Bogens ergibt sich ueber
	 * die Parameter mx und my. Der Radius ueber radius. Der Bogen beginnt vom
	 * Mittelpunkt aus gesehen mit dem Winkel startwinkel und ueberstreicht einen
	 * Winkel entsprechend der Groesse drehwinkel. Je nach Vorzeichen diese
	 * Parameters erfolgt eine Linksdrehung (+) bzw. eine Rechtsdrehung(-)
	 * Fuellmuster werden bei der Zeichnung beruecksichtigt. Es entsteht
	 * gegebenenfalls ein Kreissegment. Die Zeichnung erfolgt auch bei
	 * angehobeneme Stift. Die Bewegung des Stiftes zum Mittelpunkt wird dagegen
	 * nicht gezeichnet.
	 * 
	 * @param mx
	 * @param my
	 * @param radius
	 * @param startwinkel
	 * @param drehwinkel
	 */
	public void kreisBogen(double mx, double my, double radius,
			double startwinkel, double drehwinkel) {

		int imx = (int) Math.round(mx);
		int imy = (int) Math.round(my);
		int iradius = (int) Math.round(radius);
		int istartwinkel = (int) Math.round(startwinkel);
		int idrehwinkel = (int) Math.round(drehwinkel);

		gr = Pen.this.grafikKontext();
		setGZustand(gr);
		if (gr != null) {
			setGZustand(gr);
			if (muster == DURCHSICHTIG) {
				gr.drawArc(imx - iradius, imy - iradius, 2 * iradius,
						2 * iradius, istartwinkel, idrehwinkel);
			} else {
				if (muster == GEFUELLT) {
					gr.fillArc(imx - iradius, imy - iradius, 2 * iradius,
							2 * iradius, istartwinkel, idrehwinkel);
				}
			}
		}
		this.sync();
		gr = null;
	}

	/**
	 * Zeichnet einen geschlossenen Streckenzug. Der Anfangpunkt ist die
	 * aktuelle Stiftkoordinate. Im Felde xy werden Aenderungen (Vektoren) der
	 * jeweils aktuellen Position uebergeben. Befindet sich der Stift auf
	 * Position (u/v) dann wird (u+xy[i].getDx(),u+xy[i].getDy()) zur neuen
	 * Position. Das Feld xy muss mindestens zwei Vektoren enthalten. Die
	 * Stiftposition nach erfolgter Zeichnung ist der Anfangs(und Endpunkt). Der
	 * Stift richtet sich nach der ersten Strecke aus. Fuellmuster werden
	 * beruecksichtigt.
	 * 
	 * @param xy
	 *            Feld fuer Verschiebungsvektoren
	 * 
	 */
	public void zeichnePolygon(Vektor2D[] xy) {
		if (xy.length > 1) {
			double[] u = new double[xy.length + 1];
			double[] v = new double[xy.length + 1];
			u[0] = stiftx;
			u[1] = stifty;
			for (int i = 1; i < u.length; i++) {
				u[i] = u[i - 1] + xy[i - 1].getDx();
				v[i] = v[i - 1] + xy[i - 1].getDy();
			}
			this.polygon(u, v);
			stiftRichtung = xy[0].getRichtung();
		}
	}

	/**
	 * Zeichnet einen geschlossenen Streckenzug. Der Anfangpunkt ist die
	 * aktuelle Stiftkoordinate. In den Feldern x und y werden Aenderungen
	 * (Vektoren) der jeweils aktuellen Position uebergeben. Befindet sich der
	 * Stift auf Position (u/v) dann wird (u+x[i],u+y[i]) zur neuen Position.
	 * Die Feldlaengen von x und y muessen uebereinstimmen und mindestens zwei
	 * Vektoren muessen gegeben sein. Die Stiftposition nach erfolgter Zeichnung
	 * ist der Anfangs(und Endpunkt). Der Stift richtet sich nach der ersten
	 * Strecke aus. Fuellmuster werden beruecksichtigt.
	 * 
	 * @param x
	 *            Feld fuer x-Koordinaten der Verschiebungsvektoren
	 * @param y
	 *            Feld fuer y-Koordinaten der Verschiebungsvektoren
	 */
	public void zeichnePolygon(double[] x, double[] y) {
		if (x.length > 1 && x.length == y.length) {
			double[] u = new double[x.length + 1];
			double[] v = new double[x.length + 1];
			u[0] = stiftx;
			v[0] = stifty;
			for (int i = 1; i < u.length; i++) {
				u[i] = u[i - 1] + x[i - 1];
				v[i] = v[i - 1] + y[i - 1];
			}
			this.polygon(u, v);

		}
	}

	/**
	 * Zeichnet einen geschlossenen Streckenzug mit den den Eckpunkten, die in
	 * den Feldern x und y uebergeben werden. Die Feldlaengen muessen
	 * uebereinstimmen und mindestens drei Punkte muessen gegeben sein. Fuellmuster
	 * werden beruecksichtigt Die Bewegung zum Startpunkt des Polygons wird nicht
	 * gezeichnet. Stiftposition und -richtung werden nicht beeinflusst
	 * 
	 * @param x
	 *            Feld fuer x-Koordinaten
	 * @param y
	 *            Feld fuer y-Koordinaten
	 */
	public void polygon(double[] x, double[] y) {
		// System.out.println("poly");
		if (x.length > 2 && x.length == y.length) {
			int[] xi;
			int[] yi;
			xi = new int[x.length];
			yi = new int[y.length];
			for (int i = 0; i < xi.length; i++) {
				xi[i] = (int) Math.round(x[i]);
				yi[i] = (int) Math.round(y[i]);
			}

			gr = Pen.this.grafikKontext();
			setGZustand(gr);
			int n = x.length;
			if (gr != null) {
				setGZustand(gr);
				if (muster == DURCHSICHTIG) {
					gr.drawPolygon(xi, yi, n);
				} else {
					if (muster == GEFUELLT) {
						gr.fillPolygon(xi, yi, n);
					}
				}
			}
			this.sync();
			gr = null;
		}
	}

	/**
	 * 
	 * @return aktuelle vertikale Koordinate der Stiftposition
	 */
	public double vPosition() {
		return stifty;
	}

	/**
	 * 
	 * @return aktuelle horizontale Koordinate der Stiftposition
	 */
	public double hPosition() {
		return stiftx;
	}

	/**
	 * Setzt einen Stiftzustand (Position, Linienbreite etc.
	 */
	public void setzeZustand(PenState zustand) {
		aktuellerFont = zustand.getAktuellfont();
		schriftStil = zustand.getSchriftstil();
		schriftGroesse = zustand.getSchriftgroesse();
		schriftArt = zustand.getSchriftart();
		farbe = zustand.getFarbe();
		linienBreite = zustand.getLinienbreite();
		stiftx = zustand.getStiftx();
		muster = zustand.getZmuster();
		stifty = zustand.getStifty();
		stiftOben = zustand.isHoch();
		stiftRichtung = zustand.getWinkel();
		schreibModus = zustand.getSchreibmodus();
	}

	/**
	 * Liefert den aktuellen Stiftzustand (Position, Linienbreite etc.
	 */
	public PenState holeZustand() {
		return new PenState(aktuellerFont, schriftStil, schriftGroesse,
				schriftArt, farbe, linienBreite, stiftx, stifty, muster,
				stiftOben, stiftRichtung, schreibModus);
	}

	/**
	 * Speichert den aktuellen Stiftzustand (Position, Linienbreite etc.
	 */
	public void merkeZustand() {
		merkeZustand = this.holeZustand();
	}

	/**
	 * Setzt den gespeicherten Stiftzustand zurueck. Es wird nicht gezeichnet.
	 */
	public void restauriereZustand() {
		this.setzeZustand(merkeZustand);
	}

	/**
	 * 
	 * @return liefert die eingestellte Linienbreite
	 */
	public int linienBreite() {
		return this.linienBreite;
	}

	private Vector<PenListener> penListeners;

	/** registriert einen PenListener */
	public synchronized void addPenListener(PenListener l) {
		if (penListeners == null) {
			penListeners = new Vector<PenListener>();
		}
		if (!penListeners.contains(l)) {
			penListeners.addElement(l);
		}
	}

	/** entfernt einen PenListener */
	public synchronized void removePenListener(PenListener l) {
		if (penListeners != null && penListeners.contains(l)) {
			penListeners.removeElement(l);
		}
	}

	/**  */
	protected void firePenPainted() {
		if (penListeners != null) {
			for (PenListener pl : penListeners) {
				pl.penPainted(this);
			}

		}
	}
}
