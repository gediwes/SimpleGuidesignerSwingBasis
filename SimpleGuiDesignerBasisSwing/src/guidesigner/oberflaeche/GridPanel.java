package guidesigner.oberflaeche;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Stroke;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class GridPanel extends JPanel {
	Stroke stroke;
	private int gridx;
	private int gridy;

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

	/**
	 * Create the panel.
	 */
	public GridPanel(int gridx, int gridy) {
		this.gridx = gridx;
		this.gridy = gridy;
		float[] dash = new float[2];
		dash[0] = 1f;// eins zeichnen
		dash[1] = 1f;// eins auslassen
		stroke = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1, dash, 0);
	}

	public void setzeGridx(int gridx) {
		this.gridx = gridx;
		repaint();
	}

	public void setzeGridy(int gridy) {

		this.gridy = gridy;
		repaint();
	}

	@Override
	public synchronized void paintComponent(Graphics g) {
		super.paintComponent(g);
		((Graphics2D) g).setStroke(stroke);
		g.setColor(new Color(0.9f, 0.9f, 0.9f));

		if (gridx > 0) {
			for (int i = 0; i < this.getWidth(); i += gridx) {
				g.drawLine(i, 0, i, this.getHeight());
			}
		}
		if (gridy > 0) {
			for (int i = 0; i < this.getHeight(); i += gridy) {
				g.drawLine(0, i, this.getWidth(), i);
			}
		}

	}

}
