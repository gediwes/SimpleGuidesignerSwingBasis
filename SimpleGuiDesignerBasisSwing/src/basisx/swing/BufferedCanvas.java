package basisx.swing;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

//import basis.Farbe;
//import basis.vw.*;

/**
 * Mai 2011 Ein BufferedCanvas ist eine von JPanel abgeleitete Komponente auf
 * der Bilder oder Zeichnungen dargestellt werden koennen. Die Bilder werden auf
 * einem Hintergrundpuffer erzeugt. Wenn die Komponente dargestellt wird, wird
 * dieser Puffer sichtbar. Der BufferedCanvas unterstuetzt die Darstellung von
 * Bildern mit Transparenz und kann auch selbst mit unterschiedlichem
 * Transparenzgrad ueber eine andere Komponente gelegt werden. Zur
 * Ereignisbearbeitung: Ob der BufferedCanvas durch Ueberdecken mit einem
 * Fenster, Groessenveraenderung oder Aehnliches vom System neu gezeichnet wurde,
 * kann einem angemeldeten BufferedCanvasListener signalisiert werden.
 * 
 * @see addBufferedCanvasListener(BufferedCanvasListener l)
 * @see removeBufferedCanvasListener(BufferedCanvasListener l)
 * @see BufferedCanvasListener
 */
public class BufferedCanvas extends JPanel implements Serializable, PenPane,
		PenListener {
	protected boolean nochnichtsichtbargewesen = true;
	protected boolean leinwandneugezeichnet;

	private Color zhintergrundfarbe = Color.WHITE;
	private float alphawert = 1.0f;

	protected Toolkit toolkit;

	private BufferedImage visibleImg;

	private transient Vector canvasListeners;
//	private boolean visiminvalid = true;;

	/**
	 * erzeugt einen Canvas mit 10 Pixel Breite und Hoehe und der Unterstuetzung
	 * von Transparenz
	 */
	public BufferedCanvas() {
		this(10, 10, true);
	}

	/**
	 * erzeugt einen Canvas mit den angegebenen Dimensionen und einem
	 * BufferedImage welches auf Wunsch Transparenz unterstuetzt.
	 * 
	 */
	public BufferedCanvas(double b, double h, boolean transparent) {
		super();
		this.setLayout(null);
		this.setFocusable(true);
		this.setOpaque(!transparent);
		try {

			if (transparent) {
				// visibleImg =
				// GraphicsEnvironment.getLocalGraphicsEnvironment()
				// .getDefaultScreenDevice().getDefaultConfiguration()
				// .createCompatibleImage((int) b, (int) h,
				// Transparency.BITMASK);
				visibleImg = new BufferedImage((int) b, (int) h,
						BufferedImage.TYPE_INT_ARGB);
				// zhintergrundfarbe = Farbe.DURCHSICHTIG;
			} else {
				visibleImg = GraphicsEnvironment.getLocalGraphicsEnvironment()
						.getDefaultScreenDevice().getDefaultConfiguration()
						.createCompatibleImage((int) b, (int) h,
								Transparency.OPAQUE);

			}
			Graphics gh = visibleImg.getGraphics();
			gh.setColor(zhintergrundfarbe);
			gh.fillRect(0, 0, (int) b, (int) h);
			toolkit = Toolkit.getDefaultToolkit();
			this.setPreferredSize(new Dimension((int) b, (int) h));
			this.repaint();
			this.sync();

		} catch (Exception e) {
			System.out.println(" BufferedCanvas konnte nicht erstellt werden");
		}
	}

	public void setAlpha(float a) {
		alphawert = a;
		this.repaint();
	}

	public float getAlpha() {
		return alphawert;
	}

	// public void update(Graphics g) {
	// this.paint(g);
	// }

	// public void paintBereich(int lx, int ly, int wi, int he, Graphics g){
	// g.clipRect(lx,ly,wi,he);
	// g.drawImage(img, lx, ly, lx+wi, ly+he,lx, ly, lx+wi, ly+he, this);
	// System.out.println("1");
	//		
	// }

	/** */
	public synchronized void paintComponent(Graphics g) {
//        visiminvalid  =true;
//        System.out.println("v");
		super.paintComponent(g);
		BufferedImage bufferedImage;

		int breite = Math.max(this.getSize().width, visibleImg.getWidth());
		int hoehe = Math.max(this.getSize().height, visibleImg.getHeight());
		if (this.getSize().width > visibleImg.getWidth()
				|| this.getSize().height > visibleImg.getHeight()) {

			if (!this.isOpaque()) {
				// bufferedImage = GraphicsEnvironment
				// .getLocalGraphicsEnvironment()
				// .getDefaultScreenDevice().getDefaultConfiguration()
				// .createCompatibleImage(breite, hoehe,
				// Transparency.BITMASK);

				bufferedImage = new BufferedImage(breite, hoehe,
						BufferedImage.TYPE_INT_ARGB);
			} else {
				bufferedImage = GraphicsEnvironment
						.getLocalGraphicsEnvironment().getDefaultScreenDevice()
						.getDefaultConfiguration().createCompatibleImage(
								breite, hoehe, Transparency.OPAQUE);
			}
			Graphics2D gh = (Graphics2D) bufferedImage.getGraphics();

			gh.setColor(zhintergrundfarbe);
			gh.fillRect(0, 0, breite, hoehe);
			
			gh.drawImage(visibleImg, 0, 0, this);
			
			
			visibleImg = bufferedImage;
		}
		Composite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				alphawert);
		((Graphics2D) g).setComposite(alpha);
		g.drawImage(visibleImg, 0, 0, this);
		// }
		this.sync();//
//		visiminvalid = false;
		this.fireCanvasGezeichnet(this);

	}

	/** setzt die Hintergrundfarbe für den Canvas */
	public void setBackground(Color c) {
		//		
		super.setBackground(c);
		zhintergrundfarbe = c;
		if (visibleImg != null) {
			try {
				int breite = Math.max(this.getSize().width, visibleImg
						.getWidth());
				int hoehe = Math.max(this.getSize().height, visibleImg
						.getHeight());
				if (this.getSize().width > visibleImg.getWidth()
						|| this.getSize().height > visibleImg.getHeight()) {
					if (!this.isOpaque()) {
						// visibleImg =
						// GraphicsEnvironment.getLocalGraphicsEnvironment()
						// .getDefaultScreenDevice()
						// .getDefaultConfiguration()
						// .createCompatibleImage(breite, hoehe,
						// Transparency.BITMASK);
						visibleImg = new BufferedImage(breite, hoehe,
								BufferedImage.TYPE_INT_ARGB);
					} else {
						visibleImg = GraphicsEnvironment
								.getLocalGraphicsEnvironment()
								.getDefaultScreenDevice()
								.getDefaultConfiguration()
								.createCompatibleImage(breite, hoehe,
										Transparency.OPAQUE);
					}

				}
				Graphics2D grh = (Graphics2D) visibleImg.getGraphics();
				grh.setColor(zhintergrundfarbe);
				grh.setComposite(AlphaComposite.Src);
				grh.fillRect(0, 0, breite, hoehe);
				Graphics gr = this.getGraphics();
				gr.drawImage(visibleImg, 0, 0, this);
				this.repaint();
				this.sync();
			} catch (Exception e) {

			}
		}
	}

	

	/**  */
	protected void fireCanvasGezeichnet(BufferedCanvas e) {
		if (canvasListeners != null) {
			Vector listeners = canvasListeners;
			int count = listeners.size();
			for (int i = 0; i < count; i++) {
				((BufferedCanvasListener) listeners.elementAt(i))
						.bufferedCanvasRepainted(e);
			}
		}
	}

	/** registriert einen BufferedCanvasListener */
	public synchronized void addBufferedCanvasListener(BufferedCanvasListener l) {
		Vector v = canvasListeners == null ? new Vector(2)
				: (Vector) canvasListeners.clone();
		if (!v.contains(l)) {
			v.addElement(l);
			canvasListeners = v;
		}
	}

	/** entfernt einen BufferedCanvasListener */
	public synchronized void removeBufferedCanvasListener(
			BufferedCanvasListener l) {
		if (canvasListeners != null && canvasListeners.contains(l)) {
			Vector v = (Vector) canvasListeners.clone();
			v.removeElement(l);
			canvasListeners = v;
		}
	}

	/**  */
	public void sync() {
		if (toolkit == null) {
			toolkit = Toolkit.getDefaultToolkit();
		}
		toolkit.sync();
	}

	public BufferedImage getBufferedImage() {
		return visibleImg;
	}

	public Graphics getBufferedImageGraphics() {
//		System.out.print(visiminvalid);
//		while (visiminvalid){}
//		System.out.print(visiminvalid);
		return visibleImg.getGraphics();
	}

	private BufferedImage loadImageFile(String path) {
		BufferedImage source = null, neu = null;
		File f = null;
		String pathalternative = "";
		if (path.startsWith("/")) {
			pathalternative = path.substring(1);
		} else {
			pathalternative = "/" + path;
		}
		URL pic_url = getClass().getResource(path);
		// Mischmasch wegen Pfadirritationen bei Verwendung einer
		// Entwicklungsumgebung
//		System.out.println("1:" + (pic_url != null ? pic_url.getPath() : ""));
		if (pic_url == null) {
			pic_url = getClass().getResource(pathalternative);
		}
		if (pic_url == null) {
//			System.out.println("2a:picurl = null");
			f = new File(path);
//			System.out.println("2b:File existiert:" + f.exists());
			if (!f.exists()) {
				f = new File(pathalternative);
			}
		}
		try {
			if (f == null) {
//				System.out.println("3a");
				source = ImageIO.read(pic_url);
				// pfad2Bild = pic_url.getPath();
//				System.out.println("3b");

			} else {
//				System.out.println("4a" + f.getAbsolutePath());
				source = ImageIO.read(f);
				// pfad2Bild = f.getAbsolutePath();
//				System.out.println("4b");
			}
			neu = new BufferedImage(source.getWidth(), source.getHeight(),
					BufferedImage.TYPE_INT_ARGB);
			Graphics g = neu.getGraphics();
			g.drawImage(source, 0, 0, null);
		} catch (IOException e) {
			System.out.println("Fehler " + e);
		}

		// System.out.println("P "+pfad2Bild);
		// return source;
		return neu;
	}

	public boolean setImageIcon(String pfad) {
		try {

			ImageIcon ii = new ImageIcon(getClass().getResource(pfad));
			this.setImageIcon(ii);
		} catch (Exception e) {
			return false;
		}
		return true;
		// try {
		// ImageIcon ii = new ImageIcon(getClass().getResource(pfad));
		// int breite = ii.getIconWidth();
		// int hoehe = ii.getIconHeight();
		// if (!this.isOpaque()) {
		// visibleImg = new BufferedImage(breite, hoehe,
		// BufferedImage.TYPE_INT_ARGB);
		// // visibleImg = GraphicsEnvironment.getLocalGraphicsEnvironment()
		// // .getDefaultScreenDevice().getDefaultConfiguration()
		// // .createCompatibleImage(breite, hoehe,
		// // Transparency.BITMASK);
		// } else {
		// visibleImg = GraphicsEnvironment.getLocalGraphicsEnvironment()
		// .getDefaultScreenDevice().getDefaultConfiguration()
		// .createCompatibleImage(breite, hoehe,
		// Transparency.OPAQUE);
		// }
		//
		// Graphics2D graph = (Graphics2D) visibleImg.getGraphics();
		// graph.drawImage(ii.getImage(), 0, 0, this);
		//
		// } catch (Exception e) {
		// return false;
		// }
		// this.repaint();
		// return true;
	}

	public void setImageIcon(ImageIcon ii) {
		try {
			int breite = ii.getIconWidth();
			int hoehe = ii.getIconHeight();
			if (!this.isOpaque()) {
				visibleImg = new BufferedImage(breite, hoehe,
						BufferedImage.TYPE_INT_ARGB);
				// visibleImg =
				// GraphicsEnvironment.getLocalGraphicsEnvironment()
				// .getDefaultScreenDevice().getDefaultConfiguration()
				// .createCompatibleImage(breite, hoehe,
				// Transparency.BITMASK);
			} else {
				visibleImg = GraphicsEnvironment.getLocalGraphicsEnvironment()
						.getDefaultScreenDevice().getDefaultConfiguration()
						.createCompatibleImage(breite, hoehe,
								Transparency.OPAQUE);
			}

			Graphics2D graph = (Graphics2D) visibleImg.getGraphics();
			graph.drawImage(ii.getImage(), 0, 0, this);
		} catch (Exception e) {
			System.out.println("Laden des Bildes fehlgeschlagen");
		}
		this.repaint();
		this.sync();
	}

	public boolean setBufferedImage(String path) {
		BufferedImage bi = this.loadImageFile(path);
		if (bi == null) {
			return false;
		}
		int breite = bi.getWidth();
		int hoehe = bi.getHeight();
		if (!this.isOpaque()) {
			visibleImg = new BufferedImage(breite, hoehe,
					BufferedImage.TYPE_INT_ARGB);
			// visibleImg = GraphicsEnvironment.getLocalGraphicsEnvironment()
			// .getDefaultScreenDevice().getDefaultConfiguration()
			// .createCompatibleImage(breite, hoehe,
			// Transparency.BITMASK);
		} else {
			visibleImg = GraphicsEnvironment.getLocalGraphicsEnvironment()
					.getDefaultScreenDevice().getDefaultConfiguration()
					.createCompatibleImage(breite, hoehe, Transparency.OPAQUE);
		}

		Graphics2D graph = (Graphics2D) visibleImg.getGraphics();
		graph.drawImage(bi, 0, 0, this);

		this.repaint();
		this.sync();
		return true;
	}

	public void setBufferedImage(BufferedImage bimg) {
		visibleImg = bimg;
		this.setPreferredSize(new Dimension(bimg.getWidth(), bimg.getHeight()));
		this.repaint();
	}

	public Picture getCrop(int x, int y, int breite, int hoehe) {
		if (x < 0) {
			x = 0;
		}
		if (y < 0) {
			y = 0;
		}
		if (x > visibleImg.getWidth() || y > visibleImg.getHeight()) {
			BufferedImage crop = new BufferedImage(1, 1,
					BufferedImage.TYPE_INT_ARGB);
			Picture cropPicture = new Picture(crop);
			return cropPicture;
		}
		if (breite + x >= visibleImg.getWidth()) {
			breite = visibleImg.getWidth() - x;
		}
		if (hoehe + y >= visibleImg.getHeight()) {
			hoehe = visibleImg.getHeight() - y;
		}
		BufferedImage crop = new BufferedImage(breite, hoehe,
				BufferedImage.TYPE_INT_ARGB);
		crop.getGraphics().drawImage(
				visibleImg.getSubimage(x, y, breite, hoehe), 0, 0, null);
		Picture cropPicture = new Picture(crop);
		return cropPicture;
	}

	@Override
	public Color getFillColor() {
		return this.zhintergrundfarbe;
	}

	@Override
	public void penPainted(Object pen) {
		this.repaint();
	}

	public Pen getPen() {
		return new Pen((Container) this);
	}
}
