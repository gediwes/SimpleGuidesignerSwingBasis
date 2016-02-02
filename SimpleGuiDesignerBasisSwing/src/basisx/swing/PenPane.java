package basisx.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public interface PenPane {
	Graphics getBufferedImageGraphics();
	BufferedImage getBufferedImage();
	Color getFillColor();
	Pen getPen();
}
