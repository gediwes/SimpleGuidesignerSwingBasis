package guidesigner.oberflaeche;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import guidesigner.control.Controller;
import guidesigner.eigenschaften.GrundEigenschaft;
import guidesigner.eigenschaften.komponenten.KomponentenBasisInfo;
import guidesigner.eigenschaften.komponenten.KomponentenEigenschaft;

public class KomponentenEigenschaftseditor implements ActionListener, CaretListener, ChangeListener, EditorInterface {

	// zugehoerige Komponente Name und Typ
	// name der Eigenschaft
	// setzeMethode
	// Parameterliste Name und Typ
	// Holemethoden (Typ folgt aus Parametern, Holemethoden entsprechen in Typ
	// und Reihenfolge den Parametern (!Einschraenkung!)

	KomponentenEigenschaft eigenschaft;

	/**
	 * @return liefert eigenschaft
	 */
	public GrundEigenschaft getEigenschaft() {
		return eigenschaft;
	}

	KomponentenBasisInfo zielKomponentenInfo;
	JPanel l;
	Color merkeColor;
	// private boolean merkeLoeschen;
	Controller controller;
	Component[] zf;
	private boolean ereignisseAus = false;

	public KomponentenEigenschaftseditor() {
	}

	public KomponentenEigenschaftseditor(Controller contro, KomponentenEigenschaft eg, KomponentenBasisInfo k, JPanel l,
			JPanel r) {
		controller = contro;
		this.eigenschaft = eg;
		this.zielKomponentenInfo = k;

		this.erzeugeEditorKomponentenEigenschaft((KomponentenEigenschaft) eigenschaft, l, r);

		this.aktualisiereEigenschaftsanzeige(eg);

	}

	public void erzeugeEditorKomponentenEigenschaft(KomponentenEigenschaft eigenschaft, JPanel l, JPanel r) {
		this.l = l;
		int zeilenhoehe = 25;
		int yOffset = 5;
		int y = l.getHeight();
		JLabel label = new JLabel();
		label.setText(eigenschaft.getEigenschaftsName());
		label.setBounds(5, y, 180, zeilenhoehe);
		l.add(label);
		zf = new Component[eigenschaft.getSetterParameterTypen().length];
		for (int i = 0; i < eigenschaft.getSetterParameterTypen().length; i++) {
			String parametertyp = eigenschaft.getSetterParameterTypen()[i];
			if (parametertyp.equals("int")) {
				zf[i] = new JTextField();
				zf[i].setBounds(5, y, 100, zeilenhoehe);
				r.add(zf[i]);
				this.setzeIntZahlenFeldEreignisbearbeitung((JTextField) zf[i], i);
				y += (zeilenhoehe + yOffset);
			}
			if (parametertyp.equals("double")) {
				zf[i] = new JTextField();
				zf[i].setBounds(5, y, 100, zeilenhoehe);
				r.add(zf[i]);
				this.setzeZahlenFeldEreignisbearbeitung((JTextField) zf[i], i);
				y += (zeilenhoehe + yOffset);
			}
			if (parametertyp.equals("String")) {
				zf[i] = new JTextField();
				zf[i].setBounds(5, y, 100, zeilenhoehe);
				r.add(zf[i]);
				this.setzeTextFeldEreignisbearbeitung((JTextField) zf[i], i);
				y += (zeilenhoehe + yOffset);
			}
			if (parametertyp.equals("boolean")) {
				zf[i] = new JCheckBox();
				zf[i].setBounds(5, y, 100, zeilenhoehe);
				r.add(zf[i]);
				((JCheckBox) zf[i]).setSelected(true);
				((JCheckBox) zf[i]).addChangeListener(this);
				y += (zeilenhoehe + yOffset);
			}
			if (parametertyp.equals("java.awt.Color")) {
				zf[i] = new JButton();
				((JButton) zf[i]).setText("Farbwahl");
				zf[i].setBounds(5, y, 100, zeilenhoehe);
				r.add(zf[i]);
				((JButton) zf[i]).addActionListener(this);
				y += (zeilenhoehe + yOffset);
			}
			if (parametertyp.equals("action")) {
				zf[i] = new JButton();
				((JButton) zf[i]).setText("Loeschen");
				zf[i].setBounds(5, y, 100, zeilenhoehe);
				r.add(zf[i]);
				((JButton) zf[i]).addActionListener(this);
				y += (zeilenhoehe + yOffset);
			}
			if (parametertyp.equals("Vector<String>")) {
				zf[i] = new JTextArea();
				JScrollPane sf = new JScrollPane(zf[i]);
				sf.setBounds(5, y, 100, 4 * zeilenhoehe);
				r.add(sf);
				((JTextArea) zf[i]).addCaretListener(this);
				y += (zeilenhoehe * 4 + yOffset);
			}
		}
		l.setPreferredSize(new Dimension(145, y + 2));
		l.setSize(l.getPreferredSize());
		l.revalidate();
		r.setPreferredSize(new Dimension(125, y + 2));
		r.setSize(r.getPreferredSize());
		r.revalidate();
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (ereignisseAus) {
			return;
		}
		if (arg0.getSource() instanceof JButton) {
			JButton k = ((JButton) arg0.getSource());
			if (k.getText().equals("Farbwahl")) {
				// this.erzeugeMethode();
				merkeColor = JColorChooser.showDialog(l, "Wähle Farbe", merkeColor);
				// if (eigenschaft.getSetterParameterNamen().length==1){
				// controller.aendereEigenschaftUeberEditor(zielKomponentenInfo,
				// eigenschaft, merkeColor);
				// } else {
				Object[] einzelwerte = new Object[eigenschaft.getSetterParameterTypen().length];
				for (int j = 0; j < eigenschaft.getSetterParameterTypen().length; j++) {
					if (eigenschaft.getSetterParameterTypen()[j].contains("Color")) {
						einzelwerte[j] = merkeColor;
					} else {
						einzelwerte[j] = getWert(eigenschaft.getSetterParameterTypen()[j], zf[j]);
					}
					System.out.println(einzelwerte[j]);
				}
				controller.aendereEigenschaftUeberEditor(zielKomponentenInfo, eigenschaft, einzelwerte);
			}
			// }

		}
	}

	public void setzeIntZahlenFeldEreignisbearbeitung(final JTextField tf, final int i) {
		tf.setDocument(new PlainDocument() {
			@Override
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

				String currentText = getText(0, getLength());
				String beforeOffset = currentText.substring(0, offs);
				String afterOffset = currentText.substring(offs, currentText.length());
				String proposedResult = beforeOffset + str + afterOffset;
				if (!(istGanzZahl(proposedResult) || proposedResult.equals("") || proposedResult.equals("-"))) {
					return;
				}
				super.insertString(offs, str, a);
			}

		});
		try {

			tf.getDocument().addDocumentListener(new DocumentListener() {

				@Override
				public void changedUpdate(DocumentEvent e) {
					// bearbeiteIntZahlWurdeGeaendert(e, i);
					eigenschaftUeberEditorAendernlassen();
				}

				@Override
				public void insertUpdate(DocumentEvent e) {
					// bearbeiteIntZahlWurdeGeaendert(e, i);
					eigenschaftUeberEditorAendernlassen();
				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					// bearbeiteIntZahlWurdeGeaendert(e, i);
					eigenschaftUeberEditorAendernlassen();
				}

			});
			tf.addKeyListener(new KeyAdapter() {

				public void keyReleased(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						tf.transferFocus();
						// bearbeiteReturnWurdeGedruecktNachIntZahl(e, i);
						eigenschaftUeberEditorAendernlassen();
					}

				}

			});
			tf.addFocusListener(new FocusListener() {

				@Override
				public void focusGained(FocusEvent arg0) {
				}

				@Override
				public void focusLost(FocusEvent arg0) {
					// bearbeiteFokusVerlorenNachIntZahl(arg0, i);
					eigenschaftUeberEditorAendernlassen();
				}
			});

		} catch (Exception e) {

		}

	}

	/**
	 * @param arg0
	 * @param i
	 */
	private void bearbeiteFokusVerlorenNachIntZahl(FocusEvent arg0, int i) {
		eigenschaftUeberEditorAendernlassen();

	}

	/**
	 * @param e
	 * @param i
	 */
	private void bearbeiteReturnWurdeGedruecktNachIntZahl(KeyEvent e, int i) {
		eigenschaftUeberEditorAendernlassen();
	}

	/**
	 * @param e
	 * @param i
	 */
	private void bearbeiteIntZahlWurdeGeaendert(DocumentEvent e, int i) {
		eigenschaftUeberEditorAendernlassen();
		// if (ereignisseAus) {
		// return;
		// }
		// Object[] setterparameter = new
		// Object[eigenschaft.getSetterParameterTypen().length];
		// for (int j = 0; j < eigenschaft.getSetterParameterTypen().length;
		// j++) {
		// setterparameter[j] =
		// getWert(eigenschaft.getSetterParameterTypen()[j], zf[j]);
		// System.out.println(setterparameter[j]);
		// }
		// controller.aendereEigenschaftUeberEditor(zielKomponentenInfo,
		// eigenschaft, setterparameter);

	}

	public void setzeZahlenFeldEreignisbearbeitung(final JTextField tf, final int i) {

		tf.setDocument(new PlainDocument() {
			@Override
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

				String currentText = getText(0, getLength());
				String beforeOffset = currentText.substring(0, offs);
				String afterOffset = currentText.substring(offs, currentText.length());
				String proposedResult = beforeOffset + str + afterOffset;
				if (!(istZahl(proposedResult) || proposedResult.equals("") || proposedResult.equals("-"))) {
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
		try {
			/*
			 * tf.getDocument().addDocumentListener(new DocumentListener() {
			 * 
			 * @Override public void changedUpdate(DocumentEvent e) {
			 * bearbeiteZahlWurdeGeaendert(e, i);
			 * 
			 * }
			 * 
			 * @Override public void insertUpdate(DocumentEvent e) {
			 * bearbeiteZahlWurdeGeaendert(e, i);
			 * 
			 * }
			 * 
			 * @Override public void removeUpdate(DocumentEvent e) {
			 * bearbeiteZahlWurdeGeaendert(e, i);
			 * 
			 * }
			 * 
			 * });
			 */
			tf.addKeyListener(new KeyAdapter() {

				public void keyReleased(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						tf.transferFocus();
						// bearbeiteReturnWurdeGedruecktNachZahl(e, i);
						eigenschaftUeberEditorAendernlassen();
					}

				}

			});
			tf.addFocusListener(new FocusListener() {

				@Override
				public void focusGained(FocusEvent arg0) {
				}

				@Override
				public void focusLost(FocusEvent arg0) {
					// bearbeiteFokusVerlorenNachZahl(arg0, i);
					eigenschaftUeberEditorAendernlassen();
				}
			});

		} catch (Exception e) {

		}

	}

	/**
	 * @param arg0
	 * @param i
	 */
	private void bearbeiteFokusVerlorenNachZahl(FocusEvent arg0, int i) {
		eigenschaftUeberEditorAendernlassen();
	}

	private void eigenschaftUeberEditorAendernlassen() {
		if (ereignisseAus) {
			return;
		}

		Object[] einzelwerte = new Object[eigenschaft.getSetterParameterTypen().length];
		for (int j = 0; j < eigenschaft.getSetterParameterTypen().length; j++) {
			if (!eigenschaft.getSetterParameterTyp()[j].contains("Color")) {
				// Color wird nicht über Eingabefeld geändert
				einzelwerte[j] = getWert(eigenschaft.getSetterParameterTypen()[j], zf[j]);
			} else {
				einzelwerte[j] = eigenschaft.getWertAlsArray()[j];
			}

			System.out.println(einzelwerte[j]);
		}
		controller.aendereEigenschaftUeberEditor(zielKomponentenInfo, eigenschaft, einzelwerte);
		/*
		 * vielleicht etwas simpler so:
		 * 
		 * Object[] setterparameter = eigenschaft.getSetterParameterwerte(); for
		 * (int j = 0; j < eigenschaft.getSetterParameterTypen().length; j++) {
		 * if (!eigenschaft.getSetterParameterTyp()[j].contains("Color")){ //
		 * Color wird nicht über Eingabefeld geändert
		 * 
		 * setterparameter[j] =
		 * getWert(eigenschaft.getSetterParameterTypen()[j], zf[j]); }
		 * 
		 * System.out.println(setterparameter[j]); }
		 * controller.aendereEigenschaftUeberEditor(zielKomponentenInfo,
		 * eigenschaft, setterparameter);
		 * 
		 */
	}

	/**
	 * @param e
	 * @param i
	 */
	private void bearbeiteReturnWurdeGedruecktNachZahl(KeyEvent e, int i) {
		eigenschaftUeberEditorAendernlassen();
		// if (ereignisseAus) {
		// return;
		// }
		// Object[] setterparameter = new
		// Object[eigenschaft.setterParameterTypen.length];
		// for (int j = 0; j < eigenschaft.setterParameterTypen.length; j++) {
		// setterparameter[j] = getWert(eigenschaft.setterParameterTypen[j],
		// zf[j]);
		// }
		// controller.aendereEigenschaftUeberEditor(zielKomponentenInfo,
		// eigenschaft, setterparameter);

	}

	// /**
	// * @param e
	// * @param i
	// */
	// protected void bearbeiteZahlWurdeGeaendert(DocumentEvent e, int i) {
	// // TODO Auto-generated method stub
	//
	// }

	private void setzeTextFeldEreignisbearbeitung(final JTextField tf, final int i) {
		try {

			tf.getDocument().addDocumentListener(new DocumentListener() {

				@Override
				public void changedUpdate(DocumentEvent e) {
					// bearbeiteTextWurdeGeaendert(e, i);
					eigenschaftUeberEditorAendernlassen();
				}

				@Override
				public void insertUpdate(DocumentEvent e) {
					// bearbeiteTextWurdeGeaendert(e, i);
					eigenschaftUeberEditorAendernlassen();

				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					// bearbeiteTextWurdeGeaendert(e, i);
					eigenschaftUeberEditorAendernlassen();
				}

			});
			tf.addKeyListener(new KeyAdapter() {

				public void keyReleased(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						tf.transferFocus();
						// bearbeiteReturnWurdeGedrueckt(e, i);
						eigenschaftUeberEditorAendernlassen();
					}

				}

			});
			tf.addFocusListener(new FocusListener() {

				@Override
				public void focusGained(FocusEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void focusLost(FocusEvent arg0) {
					// bearbeiteFokusVerloren(arg0, i);
					eigenschaftUeberEditorAendernlassen();
				}

			});

		} catch (Exception e) {

		}

	}

	/**
	 * @param arg0
	 * @param i
	 */
	private void bearbeiteFokusVerloren(FocusEvent arg0, int i) {
		eigenschaftUeberEditorAendernlassen();
		// if (ereignisseAus) {
		// return;
		// }
		// Object[] setterparameter = new
		// Object[eigenschaft.setterParameterTypen.length];
		// for (int j = 0; j < eigenschaft.setterParameterTypen.length; j++) {
		// setterparameter[j] = getWert(eigenschaft.setterParameterTypen[j],
		// zf[j]);
		// }
		// controller.aendereEigenschaftUeberEditor(zielKomponentenInfo,
		// eigenschaft, setterparameter);
	}

	public Object getWert(String typ, Component zf) {
		if (typ.equals("int")) {
			String t = "" + ((JTextField) zf).getText();// ""+ verhindert
														// ausgabe null
			if (t.equals("") || t.equals("-") || t.equals("+")) {
				return 0;
			}
			return Integer.parseInt(((JTextField) zf).getText());
		} else if (typ.equals("double")) {
			String t = "" + ((JTextField) zf).getText();
			if (t.equals("") || t.equals("-") || t.equals("+")) {
				return 0;
			}
			return Double.parseDouble(((JTextField) zf).getText());
		} else if (typ.equals("String")) {
			return ((JTextField) zf).getText();
		} else if (typ.equals("Vector<String>")) {
			String[] s = ((JTextArea) zf).getText().split("\n");
			Vector<String> als = new Vector<>();
			for (String st : s) {
				als.add(st);
			}
			return als;
		} else if (typ.equals("boolean")) {
			return ((JCheckBox) zf).isSelected();
		}
		return null;
	}

	/**
	 * @param e
	 * @param i
	 */
	private void bearbeiteReturnWurdeGedrueckt(KeyEvent e, int i) {
		eigenschaftUeberEditorAendernlassen();
		// if (ereignisseAus) {
		// return;
		// }
		// Object[] setterparameter = new
		// Object[eigenschaft.setterParameterTypen.length];
		// for (int j = 0; j < eigenschaft.setterParameterTypen.length; j++) {
		// setterparameter[j] = getWert(eigenschaft.setterParameterTypen[j],
		// zf[j]);
		// }
		// controller.aendereEigenschaftUeberEditor(zielKomponentenInfo,
		// eigenschaft, setterparameter);
	}

	/**
	 * @param e
	 * @param i
	 */
	private void bearbeiteTextWurdeGeaendert(DocumentEvent e, int i) {
		eigenschaftUeberEditorAendernlassen();
		// if (ereignisseAus) {
		// return;
		// }
		// Object[] setterparameter = new
		// Object[eigenschaft.setterParameterTypen.length];
		// for (int j = 0; j < eigenschaft.setterParameterTypen.length; j++) {
		// setterparameter[j] = getWert(eigenschaft.setterParameterTypen[j],
		// zf[j]);
		// }
		// controller.aendereEigenschaftUeberEditor(zielKomponentenInfo,
		// eigenschaft, setterparameter);
	}

	/**
	 * @see javax.swing.event.CaretListener#caretUpdate(javax.swing.event.CaretEvent)
	 */
	@Override
	public void caretUpdate(CaretEvent arg0) {
		eigenschaftUeberEditorAendernlassen();
		// if (ereignisseAus) {
		// return;
		// }
		// Object[] setterparameter = new
		// Object[eigenschaft.setterParameterTypen.length];
		// for (int j = 0; j < eigenschaft.setterParameterTypen.length; j++) {
		// setterparameter[j] = getWert(eigenschaft.setterParameterTypen[j],
		// zf[j]);
		// }
		// controller.aendereEigenschaftUeberEditor(zielKomponentenInfo,
		// eigenschaft, setterparameter);

	}

	/**
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	@Override
	public void stateChanged(ChangeEvent arg0) {
		eigenschaftUeberEditorAendernlassen();
		// if (ereignisseAus) {
		// return;
		// }
		// Object[] setterparameter = new
		// Object[eigenschaft.setterParameterTypen.length];
		// for (int j = 0; j < eigenschaft.setterParameterTypen.length; j++) {
		// setterparameter[j] = getWert(eigenschaft.setterParameterTypen[j],
		// zf[j]);
		// }
		// controller.aendereEigenschaftUeberEditor(zielKomponentenInfo,
		// eigenschaft, setterparameter);

	}

	/**
	 * @param eigenschaft
	 */
	public void aktualisiereEigenschaftsanzeige(GrundEigenschaft eige) {
		// ausschalten Ereignisse
		ereignisseAus = true;
		if (eige instanceof KomponentenEigenschaft) {
			KomponentenEigenschaft eigenschaft = (KomponentenEigenschaft) eige;

			Object[] einzelwerte = eigenschaft.getWertAlsArray();
			if (einzelwerte == null) {
				System.out.println("null");
			}
			for (int i = 0; i < eigenschaft.getSetterParameterTypen().length; i++) {
				String parametertyp = eigenschaft.getSetterParameterTypen()[i];
				if (parametertyp.equals("int")) {
					((JTextField) zf[i]).setText("" + (int) einzelwerte[i]);
				}
				if (parametertyp.equals("double")) {
					((JTextField) zf[i]).setText("" + (double) einzelwerte[i]);
				}
				if (parametertyp.equals("String")) {
					((JTextField) zf[i]).setText((String) einzelwerte[i]);
				}
				if (parametertyp.equals("boolean")) {
					((JCheckBox) zf[i]).setSelected((boolean) einzelwerte[i].toString().equals("true"));
				}
				if (parametertyp.equals("java.awt.Color")) {

				}
				if (parametertyp.equals("action")) {

				}
				if (parametertyp.equals("Vector<String>")) {
//					System.out.println("Vector<String> wird geändert");
					if (einzelwerte[i] == null) {
						((JTextArea) zf[i]).setText("");

					} else {
						Vector<String> als = (Vector<String>) einzelwerte[i];
						if (als.size() == 0) {
							((JTextArea) zf[i]).setText("");

						} else {
							String[] s = new String[als.size()];
							s = als.toArray(s);
							String eint = "";
							for (int l = 0; l < s.length; l++) {
								eint += s[l];
								if (l < s.length - 1) {
									eint += "\n";
								}
							}
							((JTextArea) zf[i]).setText(eint);
						}
					}
				}
			}

		}
		// einschalten Ereignisse
		ereignisseAus = false;

	}

	/**
	 * stellt fest, ob ein String eine Zahl darstellt
	 */
	public static boolean istZahl(String s) {
		try {
			Double.valueOf(s).doubleValue();
			return true;
		} catch (Exception nfe) {
			return false;
		}
	}

	public static boolean istGanzZahl(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (Exception nfe) {
			return false;
		}
	}
}
