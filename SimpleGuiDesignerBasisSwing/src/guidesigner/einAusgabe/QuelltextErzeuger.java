package guidesigner.einAusgabe;

import java.util.Vector;

import guidesigner.control.Controller;
import guidesigner.eigenschaften.GrundEigenschaft;
import guidesigner.eigenschaften.ereignisse.LauscherEigenschaft;
import guidesigner.eigenschaften.ereignisse.LauscherInfo;
import guidesigner.eigenschaften.ereignisse.basisschleife.EreignisBearbeiteMethodenEigenschaft;
import guidesigner.eigenschaften.komponenten.KomponentenBasisInfo;
import guidesigner.eigenschaften.komponenten.KomponentenEigenschaft;

public class QuelltextErzeuger {
	// private String implementierteInterfaces = "";
	private String erzeugung;
	private String deklaration = "";
	// private BlueJInterface blueJInterface;
	private String klassenname;
	private boolean sourceAlsApplication;
	private boolean istSwing;
	private int guiTyp;
	private String superString = "";

	/**
	 * 
	 */
	public String getKlassenSource(boolean sourceAlsApplication) {
		String src = "";

		src = "//Import\n" + getImport();
		src += "\npublic class " + klassenname;
		if (!sourceAlsApplication) {
			if (istSwing) {
				src += " extends JFrame";

			} else {
				src += " extends Fenster";
			}
		}
		String imp = getImplementierungen();
		if (imp != null && !imp.equals("")) {
			src += imp;
		}
		src += " {\n  //Deklaration\n" + getDeklaration();
		src += "\n  public " + klassenname + "() {\n" + superString + "    this.initGui();\n  }\n";
		src += "\n  public void initGui() {\n";
		src += getErzeugung() + "  }\n";
		src += getEreignisMethoden();
		if (this.guiTyp == Controller.BASISGUISCHLEIFE) {
			src += "\n  private boolean ende = false;";
			src += "\n  public void fuehreAus() {\n";
			src += "    while(!ende){\n";
			src += "      Hilfe.kurzePause();\n";
			src += fuehreAus;
			src += "    } //ende while\n";
			src += "    fenster.gibFrei();\n";
			src += "  } //ende fuehreAus()\n\n";
			src += bearbeiteMethoden;
			src += "\n  public static void main(String[] args){\n    new " + klassenname + "().fuehreAus();\n  }\n";
			src += "}";
		} else {
			src += "\n  public static void main(String[] args){\n    new " + klassenname + "();\n  }\n";
			src += "}";
		}
		return src;
	}

	public QuelltextErzeuger(KomponentenBasisInfo kinfo, boolean sourceAlsApplication, String klassenname, int guiTyp) {
		this.istSwing = guiTyp == Controller.SWINGGUI;
		this.guiTyp = guiTyp;
		this.sourceAlsApplication = sourceAlsApplication;
		this.klassenname = klassenname;
		// this.blueJInterface = blueJInterface;
		if (guiTyp == Controller.SWINGGUI) {
			erzeugung = this.erzeugeSwingText(kinfo, null, sourceAlsApplication);
		} else if (guiTyp == Controller.BASISGUILAUSCHER) {
			erzeugung = this.erzeugeBasisText(kinfo, null, sourceAlsApplication, true);
		} else {
			erzeugung = this.erzeugeBasisText(kinfo, null, sourceAlsApplication, false);
		}
		// if (blueJInterface != null) {
		// this.speichereText(blueJInterface);
		// }
	}

	private Vector<String> importe = new Vector<String>();
	private Vector<LauscherInfo> lauscher = new Vector<LauscherInfo>();

	public void resetLauscher() {
		for (LauscherInfo li : lauscher) {
			li.leereListener();
		}
	}

	public String getMethodenZuLauscher(LauscherInfo linfo) {
		if (linfo.getListener().isEmpty()) {
			return "";
		}
		// erstmal switch;
		String mrumpf = "";
		for (String kinfo : linfo.getListener()) {
			if (!mrumpf.equals("")) {
				mrumpf += " else ";
			} else {
				mrumpf = "    ";
			}
			if (linfo.getKompatibleGui() == Controller.BASISGUILAUSCHER) {
				mrumpf += "if (k ==" + kinfo + "){\n      //TODO\n    }";
			} else {
				mrumpf += "if (e.getSource()==" + kinfo + "){\n      //TODO\n    }";
			}
		}
		mrumpf += "\n";
		String methString = "";
		for (String s : linfo.getLauschermethoden()) {
			methString += s + "{\n" + mrumpf + "  }\n";
		}
		return methString;
	}

	public String getEreignisMethoden() {
		if (lauscher.isEmpty()) {
			return "";
		}
		String emeth = "";
		for (LauscherInfo li : lauscher) {
			emeth += "\n" + getMethodenZuLauscher(li);
		}
		return emeth;
	}

	public String getImplementierungen() {
		if (lauscher.isEmpty()) {
			return "";
		}
		String is = " implements ";
		for (LauscherInfo imp : lauscher) {
			if (!is.endsWith(" ")) {
				is += ", ";
			}
			is += imp.getLauscherName();
		}
		return is;
	}

	public String getImporte() {
		if (importe.isEmpty()) {
			return "";
		}
		String is = "";
		for (String imp : importe) {
			is += imp;
		}
		return is;
	}

	private boolean scrollpanetempverwendet = false;

	private void ergaenzeDeklarationen(String typ, String dek) {

		if (dek.equals("scrollpanetemp")) {
			if (scrollpanetempverwendet) {
				return;
			}
			scrollpanetempverwendet = true;
		}
		if (typ.startsWith("basis.swing")) {
			ergaenzeImportString("import basis.swing.*;\n");
			typ = typ.substring(12);
		} else if (typ.startsWith("javax.swing.")) {
			ergaenzeImportString("import javax.swing.*;\n");
			typ = typ.substring(12);
		} else if (typ.startsWith("basis.")) {
			ergaenzeImportString("import basis.*;\n");
			typ = typ.substring(6);
		}
		if ((typ.endsWith("JFrame") || typ.endsWith("Fenster")) && !this.sourceAlsApplication) {
			return;
		}
		deklaration += "  private " + typ + " " + dek + ";\n";
	}

	public boolean importStringVorhanden(String imps) {
		for (String s : importe) {
			if (s.equals(imps)) {
				return true;
			}
		}
		return false;
	}

	public boolean interfaceVorhanden(LauscherInfo imps) {
		for (LauscherInfo s : lauscher) {
			if (s.getLauscherName().equals(imps.getLauscherName())) {
				return true;
			}
		}
		return false;
	}

	private void ergaenzeImportString(String imps) {
		if (!importStringVorhanden(imps)) {
			importe.add(imps);
		}

	}

	public String kuerze(String typ) {
		if (typ.startsWith("basis.swing.")) {
			return typ.substring(12);
		} else if (typ.startsWith("javax.swing.")) {
			return typ.substring(12);
		} else if (typ.startsWith("basis.")) {
			return typ.substring(6);
		}
		return typ;
	}

	public String getDeklaration() {
		return deklaration;
	}

	public String getImport() {
		String imp = "";
		for (String is : importe) {
			imp += is;
		}
		return imp;
	}

	/**
	 * @return
	 */
	public String getErzeugung() {
		return erzeugung;
	}

	public String erzeugeSwingText(KomponentenBasisInfo kinfo, KomponentenBasisInfo uebergeordneteKomponente,
			boolean sourceAlsApplication) {
		if (kinfo.getKomponentenTyp().endsWith("Maus") || kinfo.getKomponentenTyp().endsWith("Tastatur")) {
			return "";
		}
		if (kinfo.getKomponentenTyp().endsWith("Pen") || kinfo.getKomponentenTyp().endsWith("Turtle")) {
			return this.erzeugeSwingTextFuerPens(kinfo, uebergeordneteKomponente, sourceAlsApplication);
		}

		String src = "";
		String l4 = "    ";
		String altername = "";
		if (kinfo.getKomponentenTyp().endsWith("JFrame")) {
			String titel = "";
			KomponentenEigenschaft eig = (KomponentenEigenschaft) kinfo.getEigenschaft("Titel");
			titel = "\"" + (String) eig.getWert() + "\"";
			if (!sourceAlsApplication) {
				altername = kinfo.getVariablenName();
				kinfo.setVariablenName("this");
				superString = l4 + "super(" + titel + ");\n";
			} else {
				src = l4 + kinfo.getVariablenName() + " = new " + kuerze(kinfo.getKomponentenTyp()) + "(" + titel
						+ ");\n";
			}
		} else {
			String beschriftung = "";
			KomponentenEigenschaft eig = (KomponentenEigenschaft) kinfo.getEigenschaft("Beschriftung");
			if (eig!=null){
			 beschriftung = "\"" + (String) eig.getWert() + "\"";
			}
			src = l4 + kinfo.getVariablenName() + " = new " + kuerze(kinfo.getKomponentenTyp()) + "(" + beschriftung
						+ ");\n";
			
		}
		if (kinfo.getKomponentenTyp().endsWith("JFrame")) {
			src += l4 + kinfo.getVariablenName() + ".setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);\n";
		}
		src += l4 + kinfo.getVariablenName() + ".setLayout(null);\n";
		ergaenzeDeklarationen(kinfo.getKomponentenTyp(), kinfo.getVariablenName());
		// Sonderfall scrollbar!
		boolean inJScrollPaneEinbetten = false;
		if (kinfo.getEigenschaft("Scrollbar") != null) {
			inJScrollPaneEinbetten = (boolean) kinfo.getEigenschaft("Scrollbar").getWert();
		}
		if (inJScrollPaneEinbetten) {
			ergaenzeDeklarationen("JScrollPane", "scrollpanetemp");
			src += l4 + "scrollpanetemp = new JScrollPane(" + kinfo.getVariablenName() + ");\n";
			for (KomponentenEigenschaft e : kinfo.getKomponentenEigenschaften()) {
				if (e.getEigenschaftsName().equals("Größe") || e.getEigenschaftsName().equals("Position")) {
					src += e.getSource("scrollpanetemp", true);
				} else {
					src += e.getSource(kinfo.getVariablenName(), true);
				}
				if (e.getEigenschaftsTyp().endsWith("Color")) {
					ergaenzeImportString("import java.awt.*;");
				}
			}
			for (GrundEigenschaft e : kinfo.getEigenschaftenFuerGui(Controller.SWINGGUI)) {
				if (e instanceof LauscherEigenschaft) {
					LauscherEigenschaft le = (LauscherEigenschaft) e;
					if ((boolean) e.getWert()) {
						src += le.getSourceAddLauscher(kinfo.getVariablenName(), true);
						ergaenzeImportString(le);
						ergaenzeImplementierteInterfaces(le);
						ergaenzeLauscherVektoren(kinfo, le);
					}
				}
			}
			if (uebergeordneteKomponente != null) {
				src += l4 + uebergeordneteKomponente.getVariablenName() + ".add(scrollpanetemp);\n";
			}

		} else {
			for (KomponentenEigenschaft e : kinfo.getKomponentenEigenschaften()) {
				if (kinfo.getKomponentenTyp().endsWith("JFrame") && e.getEigenschaftsName().equals("Sichtbar")) {

				} else if (kinfo.getKomponentenTyp().endsWith("JFrame") && e.getEigenschaftsName().equals("Größe")) {
					String fname = kinfo.getVariablenName();
					String sbreite = (int) e.getWertAlsArray()[0] + "";
					String shoehe = (int) e.getWertAlsArray()[1] + "";
					src += l4 + fname + ".getContentPane().setPreferredSize(new Dimension(" + sbreite + "," + shoehe
							+ "));\n" + l4 + fname + ".pack();\n" + l4 + "Dimension dimFenster = new Dimension("
							+ sbreite + " + " + fname + ".getInsets().left\n" + l4 + "+ " + fname
							+ ".getInsets().right, " + shoehe + " + " + fname + ".getInsets().top + " + fname
							+ ".getInsets().bottom);\n" + l4 + "" + fname + ".setSize(dimFenster);\n";
				} else {
					src += e.getSource(kinfo.getVariablenName(), true);
					if (e.getEigenschaftsTyp().endsWith("Color")) {
						ergaenzeImportString("import java.awt.*;\n");
					}
				}
			}
			for (GrundEigenschaft e : kinfo.getEigenschaftenFuerGui(Controller.SWINGGUI)) {
				if (e instanceof LauscherEigenschaft) {
					LauscherEigenschaft le = (LauscherEigenschaft) e;
					if ((boolean) e.getWert()) {
						src += le.getSourceAddLauscher(kinfo.getVariablenName(), true);
						ergaenzeImportString(le);
						ergaenzeImplementierteInterfaces(le);
						ergaenzeLauscherVektoren(kinfo, le);
					}
				}
			}
			if (uebergeordneteKomponente != null) {
				src += l4 + uebergeordneteKomponente.getVariablenName() + ".add(" + kinfo.getVariablenName() + ");\n";
			}
		}

		for (KomponentenBasisInfo ki : kinfo.getEingebetteteKomponenten()) {
			src += erzeugeSwingText(ki, kinfo, sourceAlsApplication);
		}
		if (kinfo.getKomponentenTyp().endsWith("JFrame")) {
			src += l4 + kinfo.getVariablenName() + ".setVisible(true);\n";
			src += l4 + kinfo.getVariablenName() + ".repaint();\n";
			// kinfo.setVariablenName(altername);

		}
		if (!altername.equals("")) {
			kinfo.setVariablenName(altername);
		}
		return src;
	}

	public String erzeugeSwingTextFuerPens(KomponentenBasisInfo kinfo, KomponentenBasisInfo uebergeordneteKomponente,
			boolean sourceAlsApplication) {
		String src = "";
		String l4 = "    ";

		src = l4 + kinfo.getVariablenName() + " = new " + kuerze(kinfo.getKomponentenTyp()) + "(";
		if (!sourceAlsApplication && (uebergeordneteKomponente == null
				|| uebergeordneteKomponente.getKomponentenTyp().endsWith("JFrame"))) {
			src += "this.getContentPane()";
		} else if (sourceAlsApplication && (uebergeordneteKomponente == null
				|| uebergeordneteKomponente.getKomponentenTyp().endsWith("JFrame"))) {
			src += uebergeordneteKomponente.getVariablenName() + ".getContentPane()";
		} else if (uebergeordneteKomponente != null) {
			src += uebergeordneteKomponente.getVariablenName();
		}

		src += ");\n";

		String pos = null;

		for (KomponentenEigenschaft e : kinfo.getKomponentenEigenschaften()) {

			if (e.getEigenschaftsName().equals("Position")) {
				if ((int) e.getWertAlsArray()[0] != 0 || (int) e.getWertAlsArray()[1] != 0) {
					pos = (int) e.getWertAlsArray()[0] + "," + (int) e.getWertAlsArray()[1];
				}
			}

		}
		if (pos != null) {
			src += l4 + kinfo.getVariablenName() + ".penUp();\n";
			src += l4 + kinfo.getVariablenName() + ".moveTo(" + pos + ");\n";
		}

		ergaenzeDeklarationen(kinfo.getKomponentenTyp(), kinfo.getVariablenName());

		for (KomponentenEigenschaft e : kinfo.getKomponentenEigenschaften()) {
			if (!e.getEigenschaftsName().equals("Position")) {
				src += e.getSource(kinfo.getVariablenName(), true);
			}
			if (e.getEigenschaftsTyp().endsWith("Color")) {
				ergaenzeImportString("import java.awt.*;\n");
			}
		}

		return src;
	}

	public String erzeugeBasisText(KomponentenBasisInfo kinfo, KomponentenBasisInfo uebergeordneteKomponente,
			boolean sourceAlsApplication, boolean lauscherGewuenscht) {
		if (lauscherGewuenscht
				&& (kinfo.getKomponentenTyp().contains("Maus") || kinfo.getKomponentenTyp().contains("Tastatur"))) {
			return "";
		}
		if (kinfo.getKomponentenTyp().contains("Stift")) {
			return this.erzeugeBasisTextFuerStift(kinfo, uebergeordneteKomponente, sourceAlsApplication);
		}

		KomponentenEigenschaft eig = null;
		String src = "";
		String altername = "";
		String l4 = "    ";
		superString = "";
		if (kinfo.getKomponentenTyp().contains("Fenster")) {
			String initStr = "";
			String titel = "";
			eig = (KomponentenEigenschaft) kinfo.getEigenschaft("Titel");
			titel = (String) eig.getWert();
			initStr = "(\"" + titel + "\"";
			eig = (KomponentenEigenschaft) kinfo.getEigenschaft("Position");
			int x = (int) eig.getWertAlsArray()[0];
			int y = (int) eig.getWertAlsArray()[1];
			if (x != 0 || y != 0) {
				initStr += "," + x + "," + y;
			}
			eig = (KomponentenEigenschaft) kinfo.getEigenschaft("Größe");
			String b = "", h = "";
			b = (int) eig.getWertAlsArray()[0] + "";
			h = (int) eig.getWertAlsArray()[1] + "";
			initStr += "," + b + "," + h + ");\n";
			if (!sourceAlsApplication) {
				altername = kinfo.getVariablenName();
				kinfo.setVariablenName("this");
				superString = l4 + "super" + initStr;
			} else {
				src = l4 + kinfo.getVariablenName() + " = new " + kuerze(kinfo.getKomponentenTyp()) + initStr;
			}
		} else if (kinfo.getKomponentenTyp().contains("Maus") || kinfo.getKomponentenTyp().contains("Tastatur")) {
			src = l4 + kinfo.getVariablenName() + " = new " + kuerze(kinfo.getKomponentenTyp()) + "(";
			src += ");\n";

		} else if (kinfo.getKomponentenTyp().contains("Regler") || kinfo.getKomponentenTyp().contains("Rollbalken")) {
			src = l4 + kinfo.getVariablenName() + " = new " + kuerze(kinfo.getKomponentenTyp()) + "(";

			String horizontal = "", x = "", y = "", b = "", h = "";

			for (KomponentenEigenschaft e : kinfo.getKomponentenEigenschaften()) {
				if (e.getEigenschaftsName().equals("Orientierung horizontal")) {
					horizontal = (boolean) e.getWert() + "";
				}
				if (e.getEigenschaftsName().equals("Position")) {
					x = (int) e.getWertAlsArray()[0] + "";
					y = (int) e.getWertAlsArray()[1] + "";
				}
				if (e.getEigenschaftsName().equals("Größe")) {
					b = (int) e.getWertAlsArray()[0] + "";
					h = (int) e.getWertAlsArray()[1] + "";
				}

			}

			src += horizontal + "," + x + "," + y + "," + b + "," + h;
			src += ");\n";

		} else {
			src = l4 + kinfo.getVariablenName() + " = new " + kuerze(kinfo.getKomponentenTyp()) + "(";
			String beschriftung = null;
			String x = "", y = "", b = "", h = "";

			for (KomponentenEigenschaft e : kinfo.getKomponentenEigenschaften()) {
				if (e.getEigenschaftsName().equals("Beschriftung")) {
					beschriftung = (String) e.getWert();
				}
				if (e.getEigenschaftsName().equals("Position")) {
					x = (int) e.getWertAlsArray()[0] + "";
					y = (int) e.getWertAlsArray()[1] + "";
				}
				if (e.getEigenschaftsName().equals("Größe")) {
					b = (int) e.getWertAlsArray()[0] + "";
					h = (int) e.getWertAlsArray()[1] + "";
				}

			}
			if (beschriftung != null) {
				src += "\"" + beschriftung + "\",";
			}
			src += x + "," + y + "," + b + "," + h;
			src += ");\n";

		}

		ergaenzeDeklarationen(kinfo.getKomponentenTyp(), kinfo.getVariablenName());

		for (GrundEigenschaft e : kinfo.getEigenschaften()) {
			// if (istWederGroesseNochPosition(e, kinfo)) {
			src += e.getSource(kinfo.getVariablenName(), false);
			// }
			if (e.getEigenschaftsTyp().contains("Color")) {
				ergaenzeImportString("import java.awt.*;\n");
			}
		}
		if (lauscherGewuenscht) {
			for (GrundEigenschaft e : kinfo.getEigenschaftenFuerGui(Controller.BASISGUILAUSCHER)) {
				if (e instanceof LauscherEigenschaft) {
					LauscherEigenschaft le = (LauscherEigenschaft) e;
					if ((boolean) e.getWert()) {
						src += le.getSourceAddLauscher(kinfo.getVariablenName(), false);
						ergaenzeImportString(le);
						ergaenzeImplementierteInterfaces(le);
						ergaenzeLauscherVektoren(kinfo, le);
					}
				}
			}
		} else {
			// fuehreAus() und Ereignismethoden

			for (GrundEigenschaft e : kinfo.getEigenschaftenFuerGui(Controller.BASISGUISCHLEIFE)) {

				if (e instanceof EreignisBearbeiteMethodenEigenschaft) {
					EreignisBearbeiteMethodenEigenschaft le = (EreignisBearbeiteMethodenEigenschaft) e;
					if ((boolean) e.getWert()) {
						ergaenzeFuehreAus(le.getSourceFuehreAusMethodenaufruf(kinfo.getVariablenName()));
						ergaenzeBearbeiteMethoden(le.getSourceBearbeiteMethode(kinfo.getVariablenName()));
					}
				}

			}

		}
		if (uebergeordneteKomponente != null && !uebergeordneteKomponente.getKomponentenTyp().endsWith("Fenster")) {
			// System.out.println(kinfo.getKomponentenTyp());
			if (kinfo.getKomponentenTyp().endsWith("Maus")) {

				src += l4 + kinfo.getVariablenName() + ".setzeKomponente(" + uebergeordneteKomponente.getVariablenName()
						+ ");\n";
			} else {

				src += l4 + uebergeordneteKomponente.getVariablenName() + ".betteEin(" + kinfo.getVariablenName()
						+ ");\n";
			}
		}

		for (KomponentenBasisInfo ki : kinfo.getEingebetteteKomponenten()) {
			src += erzeugeBasisText(ki, kinfo, sourceAlsApplication, lauscherGewuenscht);
		}
		if (!altername.equals("")) {
			kinfo.setVariablenName(altername);
		}
		return src;
	}

	String bearbeiteMethoden = "";
	String l2 = "  ";

	private void ergaenzeBearbeiteMethoden(String[] sourceBearbeiteMethode) {
		bearbeiteMethoden += l2 + sourceBearbeiteMethode[0] + "\n";
		bearbeiteMethoden += l2 + l2 + sourceBearbeiteMethode[1] + "\n";
		bearbeiteMethoden += l2 + sourceBearbeiteMethode[2] + "\n";
	}

	String fuehreAus = "";

	private void ergaenzeFuehreAus(String[] sourceFuehreAusMethodenaufruf) {
		fuehreAus += l2 + l2 + sourceFuehreAusMethodenaufruf[0] + "\n";
		fuehreAus += l2 + l2 + l2 + sourceFuehreAusMethodenaufruf[1] + "\n";
		fuehreAus += l2 + l2 + sourceFuehreAusMethodenaufruf[2] + "\n";
	}

	public String erzeugeBasisTextFuerStift(KomponentenBasisInfo kinfo, KomponentenBasisInfo uebergeordneteKomponente,
			boolean sourceAlsApplication) {
		String src = "";
		String l4 = "    ";

		src = l4 + kinfo.getVariablenName() + " = new " + kuerze(kinfo.getKomponentenTyp()) + "(";

		if (uebergeordneteKomponente != null && !uebergeordneteKomponente.getKomponentenTyp().endsWith("Fenster")) {
			// System.out.println(kinfo.getKomponentenTyp());
			src += uebergeordneteKomponente.getVariablenName();
		}
		src += ");\n";

		String pos = null;

		for (KomponentenEigenschaft e : kinfo.getKomponentenEigenschaften()) {

			if (e.getEigenschaftsName().equals("Position")) {
				if ((int) e.getWertAlsArray()[0] != 0 || (int) e.getWertAlsArray()[1] != 0) {
					pos = (int) e.getWertAlsArray()[0] + "," + (int) e.getWertAlsArray()[1];
				}
			}

		}
		if (pos != null) {
			src += l4 + kinfo.getVariablenName() + ".hoch();\n";
			src += l4 + kinfo.getVariablenName() + ".bewegeBis(" + pos + ");\n";
		}
		ergaenzeDeklarationen(kinfo.getKomponentenTyp(), kinfo.getVariablenName());
		//
		// for (GrundEigenschaft e : kinfo.getEigenschaften()) {
		// if (istWederGroesseNochPosition(e, kinfo)) {
		// src += e.getSource(kinfo.getVariablenName(), false);
		// }
		// if (e.getEigenschaftsTyp().endsWith("Color")) {
		// ergaenzeImportString("import java.awt.*;\n");
		// }
		// }

		// for (KomponentenGrundInfo ki : kinfo.getEingebetteteKomponenten()) {
		// src += erzeugeBasisText(ki, kinfo, sourceAlsApplication);
		// }

		return src;
	}

	private boolean istWederGroesseNochPosition(GrundEigenschaft e, KomponentenBasisInfo kinfo) {
		return !(e.getEigenschaftsName().equals("Position") || e.getEigenschaftsName().equals("Größe"));
	}

	private void ergaenzeImplementierteInterfaces(LauscherEigenschaft le) {
		ergaenzeImplementierteInterfaces(le.getLauscherinfo());
	}

	private void ergaenzeImplementierteInterfaces(LauscherInfo imps) {
		if (!interfaceVorhanden(imps)) {
			lauscher.add(imps);
		}

	}

	private void ergaenzeLauscherVektoren(KomponentenBasisInfo kinfo, LauscherEigenschaft le) {
		le.getLauscherinfo().addListener(kinfo.getVariablenName());
	}

	private void ergaenzeImportString(LauscherEigenschaft le) {
		String imps = le.getLauscherinfo().getImportString();
		ergaenzeImportString(imps);
	}

	public String erzeugeStartKlasseKlassisch(String cname) {
		String a = "";
		a = a + "public class Start" + cname + "{\n";
		a = a + "  public static void main(String[] s){\n";
		a = a + cname + " anw = new " + cname + "();\n";
		a = a + "    anw.fuehreAus();\n";
		a = a + "  }//main\n";
		a = a + "}//Ende Startklasse \n";
		return a;
	}

	public String erzeugeStartKlasseLauscher(String cname) {
		String a = "";
		a = a + "public class Start" + cname + "{\n";
		a = a + "  public static void main(String[] s){\n";
		a = a + cname + " anw = new " + cname + "();\n";
		a = a + "  }//main\n";
		a = a + "}//Ende Startklasse \n";
		return a;
	}

}
