package basisx.swing;

//import java.awt.event.*;
//import java.awt.*;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;



/* letzte Aenderung 6.8.2011 */
/**
 * Zahlenfelder sind spezielle TextFelder. Sie lassen nur den Eintrag von Zahlen
 * zu
 */
public class JNumberField extends JTextField {
	public boolean fehler = false;

	/**
	 * erzeugt auf einem Fenster ein ZahlenFeld mit linker oberer Ecke (x,y)
	 * vorgegebener Breite und Hoehe, das Fenster muss vorher erzeugt sein
	 */
	public JNumberField() {
		super();
		erzeugeZahlenDokument();
		// this.documentListenerAustauschen();
	}

	/**
	 * 
	 */
	private class DokuLis implements DocumentListener {
		@Override
		public void changedUpdate(DocumentEvent e) {
			JNumberField.this.caupdate();
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			JNumberField.this.caupdate();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			JNumberField.this.caupdate();
		}
	}

	protected DocumentListener dokuListener;

	/**
	 * stellt fest, ob ein String eine Zahl darstellt
	 */
	private boolean istZahl(String s) {
		try {
			Double.valueOf(s).doubleValue();
			return true;
		} catch (Exception nfe) {
			return false;
		}
	}

	private void erzeugeZahlenDokument() {
		try {
			this.getDocument().removeDocumentListener(dokuListener);
		} catch (Exception e) {
		}

		this.setDocument(new PlainDocument() {
			@Override
			public void insertString(int offs, String str, AttributeSet a)
					throws BadLocationException {

				String currentText = getText(0, getLength());
				String beforeOffset = currentText.substring(0, offs);
				String afterOffset = currentText.substring(offs, currentText
						.length());
				String proposedResult = beforeOffset + str + afterOffset;
				if (!(istZahl(proposedResult) || proposedResult.equals("") || proposedResult
						.equals("-"))) {
					return;
				}
				// for(int i=0;i<proposedResult.length();i++)
				// {
				// if(!Character.isDigit(proposedResult.charAt(i)))
				// {
				// return;
				// }
				// }
				super.insertString(offs, str, a);
			}

		});
		this.getDocument().addDocumentListener(dokuListener);
	}

	/** belegt das Feld mit der Zahl z */
	public void setNumber(double z) {
		try {
			if (z == (int) z) {
				supersetzeText(new Integer((int) z).toString());
			} else {
				supersetzeText("" + z);
			}
		} catch (Exception e) {
			this.setText("" + z);
		}
	}

	/**
	 * belegt das Feld mit der Zahl z
	 * 
	 * 
	 */
	public void setNumber(int z) {
		supersetzeText("" + z);
	}

	/** schreibt den Text s in das Feld, falls dieser eine Zahl darstellt */
	@Override
	public void setText(String s) {
		if (istZahl(s) || s.equals("") || s.equals("-")) {
			supersetzeText(s);
		}
	}

	/**
	 * wandelt einen String in eine Zahl, falls der String eine Zahl darstellt,
	 * sonst liefert sie den Wert 0.0
	 */
	private double zahlVon(String s) {
		try {
			return Double.valueOf(s).doubleValue();
		} catch (Exception nfe) {
			return 0.;
		}
	}

	/** liefert die Zahl im Feld, eine Null, falls das Feld leer ist */
	public double getDouble() {
		if (this.getText().equals("-") || this.getText().equals("")) {
			return 0.0;
		}
		return zahlVon(this.getText());
	}

	/**
	 * wandelt einen String in eine ganze Zahl, falls der String eine solche
	 * darstellt, sonst den Wert 0
	 */
	private int ganzZahlVon(String s) {
		try {
			return Integer.parseInt(s);
		} catch (Exception nfe) {
			return 0;
		}
	}

	/** liefert die Zahl im Feld, eine Null, falls das Feld leer ist */
	public int getInteger() {
		if (this.getText().equals("-") || this.getText().equals("")) {
			return 0;
		}
		return ganzZahlVon(this.getText());
	}

	/** fuegt Text an den bestehenden Text an */

	public void append(String s) {
		if (this.getText().equals("")) {
			this.setText(s);
		} else
			this.setText(this.getText() + s);
	}

	/**
	 * haengt Text an den bestehenden an
	 * 
	 */
	public void append(char s) {
		this.append(s + "");
	}

	private boolean textok = true;

	private void supersetText(String s) {
		super.setText(s);
	}

	/** ersetzt den Inhalt durch den Text des Parameters */

	private void supersetzeText(final String s) {
		
		textok = false;

		this.getDocument().removeDocumentListener(dokuListener);
		// WorkAround gegen gelegentliches Einfrieren

		try {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					// this.validate();
					JNumberField.this.supersetText(s);
					textok = true;
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.getDocument().addDocumentListener(dokuListener);
	}

	/**
	 * 
	 * 
	 */
	private void caupdate() {
		this.getParent().validate();
	}

}
