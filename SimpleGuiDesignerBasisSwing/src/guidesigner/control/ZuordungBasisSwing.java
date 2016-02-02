/**
 * 
 */
package guidesigner.control;

import java.util.Hashtable;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

/**
 * @author Georg
 * 
 */
public class ZuordungBasisSwing {
	private static Hashtable<String, String> basisSwing = new Hashtable<String, String>();
	private static Hashtable<String, String> swingBasis = new Hashtable<String, String>();
    private static boolean uebersetzungenInitialisiert = false;

	/**
	 * @param basis
	 * @param swing
	 */
	private static void setUebersetzungBasisSwing(String basis, String swing) {
		basisSwing.put(basis, swing);
		swingBasis.put(swing,basis );
	}

	/**
	 * 
	 */
	public static String getUebersetzungBasisSwing(String basis, boolean istSwing) {
		if (!ZuordungBasisSwing.uebersetzungenInitialisiert){
			ZuordungBasisSwing.setzeBasisSwingAnalogien();
		}
		if (istSwing) {
			String eng = basisSwing.get(basis);
			if (eng != null) {
				return eng;
			}
		}
		return basis;
	}
	/**
	 * 
	 */
	public static String getUebersetzungSwingBasis(String swing, boolean istSwing) {
		if (!ZuordungBasisSwing.uebersetzungenInitialisiert){
			ZuordungBasisSwing.setzeBasisSwingAnalogien();
		}
		if (!istSwing) {
			String eng = swingBasis.get(swing);
			if (eng != null) {
				return eng;
			}
		}
		return swing;
	}
	
	private static void setzeBasisSwingAnalogien() {
		ZuordungBasisSwing.setUebersetzungBasisSwing("basis.Knopf",
				"javax.swing.JButton");
		ZuordungBasisSwing.setUebersetzungBasisSwing("basis.BeschriftungsFeld",
				"javax.swing.JLabel");
		ZuordungBasisSwing.setUebersetzungBasisSwing("basis.ListBox",
				"javax.swing.JComboBox");
		ZuordungBasisSwing.setUebersetzungBasisSwing("basis.ListAuswahl",
				"javax.swing.JList");
		ZuordungBasisSwing.setUebersetzungBasisSwing("basis.WahlBox",
				"javax.swing.JCheckBox");
		ZuordungBasisSwing.setUebersetzungBasisSwing("basis.TextBereich",
				"javax.swing.JTextArea");
		ZuordungBasisSwing.setUebersetzungBasisSwing("basis.TextFeld",
				"javax.swing.JTextField");
		ZuordungBasisSwing.setUebersetzungBasisSwing("basis.ZahlenFeld",
				"basis.swing.JNumberField");
		ZuordungBasisSwing.setUebersetzungBasisSwing("basis.Rollbalken",
				"javax.swing.JScrollBar");
		ZuordungBasisSwing.setUebersetzungBasisSwing("basis.Leinwand",
				"basis.swing.BufferedCanvas");
		ZuordungBasisSwing.setUebersetzungBasisSwing("basis.GrundFlaeche",
				"javax.swing.JPanel");
		ZuordungBasisSwing.setUebersetzungBasisSwing("basis.ScrollFlaeche",
				"javax.swing.JScrollPane");
		ZuordungBasisSwing.setUebersetzungBasisSwing("basis.Regler",
				"javax.swing.JSlider");
		ZuordungBasisSwing.setUebersetzungBasisSwing("basis.Fenster",
				"javax.swing.JFrame");
		ZuordungBasisSwing.setUebersetzungBasisSwing("basis.Stift",
				"basis.swing.Pen");
		ZuordungBasisSwing.setUebersetzungBasisSwing("basis.IgelStift",
				"basis.swing.Turtle");
		ZuordungBasisSwing.setUebersetzungBasisSwing("basis.Maus",
				"basis.Maus");
		ZuordungBasisSwing.setUebersetzungBasisSwing("basis.Tastatur",
				"basis.Tastatur");
		ZuordungBasisSwing.setUebersetzungBasisSwing("TextBereich", "JTextArea");
		
		ZuordungBasisSwing.setUebersetzungBasisSwing("Maus", "Maus");
		ZuordungBasisSwing.setUebersetzungBasisSwing("Tastatur", "Tastatur");
		ZuordungBasisSwing.setUebersetzungBasisSwing("Stift", "Pen");
		ZuordungBasisSwing.setUebersetzungBasisSwing("IgelStift", "Turtle");
		ZuordungBasisSwing.setUebersetzungBasisSwing("Knopf", "JButton");
		ZuordungBasisSwing.setUebersetzungBasisSwing("BeschriftungsFeld", "JLabel");
		ZuordungBasisSwing.setUebersetzungBasisSwing("ListBox", "JComboBox");
		ZuordungBasisSwing.setUebersetzungBasisSwing("WahlBox", "JCheckBox");
		ZuordungBasisSwing.setUebersetzungBasisSwing("ListAuswahl", "JList");
		ZuordungBasisSwing.setUebersetzungBasisSwing("GrundFlaeche", "JPanel");
		ZuordungBasisSwing.setUebersetzungBasisSwing("TextFeld", "JTextField");
		ZuordungBasisSwing.setUebersetzungBasisSwing("ScrollFlaeche", "JScrollPane");
		ZuordungBasisSwing.setUebersetzungBasisSwing("Rollbalken", "JScrollBar");
		ZuordungBasisSwing.setUebersetzungBasisSwing("Regler", "JSlider");
		ZuordungBasisSwing.setUebersetzungBasisSwing("Leinwand", "BufferedCanvas");
		ZuordungBasisSwing.setUebersetzungBasisSwing("ZahlenFeld", "JNumberField");
		ZuordungBasisSwing.uebersetzungenInitialisiert=true;
		
	}
}
