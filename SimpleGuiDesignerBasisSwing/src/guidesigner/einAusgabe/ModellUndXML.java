package guidesigner.einAusgabe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import guidesigner.control.Controller;
import guidesigner.control.KomponentenInfoGrundeigenschaften;
import guidesigner.eigenschaften.GrundEigenschaft;
import guidesigner.eigenschaften.ereignisse.LauscherEigenschaft;
import guidesigner.eigenschaften.ereignisse.LauscherInfo;
import guidesigner.eigenschaften.ereignisse.basisschleife.BearbeiteMethodeFuerMausEigenschaft;
import guidesigner.eigenschaften.ereignisse.basisschleife.BearbeiteMethodeFuerTastaturEigenschaft;
import guidesigner.eigenschaften.ereignisse.basisschleife.EreignisBearbeiteMethodenEigenschaft;
import guidesigner.eigenschaften.komponenten.EigenschaftEintragFuerJComboBox;
import guidesigner.eigenschaften.komponenten.EigenschaftEintragFuerJList;
import guidesigner.eigenschaften.komponenten.EigenschaftHintergrundFarbe;
import guidesigner.eigenschaften.komponenten.EigenschaftTextGroesse;
import guidesigner.eigenschaften.komponenten.KomponentenBasisInfo;
import guidesigner.eigenschaften.komponenten.KomponentenEigenschaft;
import guidesigner.oberflaeche.DesignerFenster;

/**
 * @author Georg Dick
 * 
 */
public class ModellUndXML {
	final static JFileChooser fcxml = new JFileChooser();

	private String dateiSuffix = ".GUIXML";
	private static String xumldiagrammdateiname = "";

	private DesignerFenster fenster;

	// Ein- Ausgabe
	public boolean speichereEntwurf(DesignerFenster designFensterSwing, File vorgabeDirectory) {
		this.fenster = designFensterSwing;
		File file;

		try {
			FileNameExtensionFilter filter = new FileNameExtensionFilter("GUI", "GUIXML");

			fcxml.setFileFilter(filter);
			if (vorgabeDirectory != null) {
				fcxml.setCurrentDirectory(vorgabeDirectory);
			} else {
				if (xumldiagrammdateiname.toLowerCase().endsWith(dateiSuffix.toLowerCase())) {
					File vorhanden = new File(xumldiagrammdateiname);
					fcxml.setSelectedFile(new File(xumldiagrammdateiname));
				}
			}
			int returnVal = fcxml.showSaveDialog(fenster);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				file = fcxml.getSelectedFile();
				if (!fcxml.getSelectedFile().getName().toLowerCase().endsWith(dateiSuffix.toLowerCase())) {
					file = new File(fcxml.getSelectedFile().getAbsolutePath() + dateiSuffix);
					xumldiagrammdateiname = fcxml.getSelectedFile().getAbsolutePath() + dateiSuffix;
				} else {
					xumldiagrammdateiname = fcxml.getSelectedFile().getAbsolutePath();
				}
				this.speichereInDatei(file);

			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;

	}

	public void speichereInDatei(File file) throws FileNotFoundException {
		KomponentenBasisInfo wurzel = fenster.getController().getKomponentenInfo(fenster.getFensterPanel());
		if (file.exists()) {
			int ok = JOptionPane.showConfirmDialog(fenster, "Die Datei ist vorhanden.\nSoll sie überschrieben werden?",
					"Datei vorhanden", JOptionPane.YES_NO_OPTION);
			if (ok != JOptionPane.OK_OPTION) {
				return;
			}
		} else {
			try {
				boolean b = file.createNewFile();
				if (!b) {
					JOptionPane.showMessageDialog(fenster, "Dateifehler");
					return;
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		Document document = null;
		DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
		TransformerFactory tfactory = TransformerFactory.newInstance();
		Transformer transformer;
		OutputStream tofile = new FileOutputStream(file);
		try {
			DocumentBuilder builder = dfactory.newDocumentBuilder();
			document = builder.newDocument();
			Element root = (Element) document.createElement("GUI");
			root.setAttribute("Version", "3");
			document.appendChild(root);
			// Save the notes belonging to the project

			Element modellroot = document.createElement("Oberflaeche");
			modellroot.setAttribute("guiTyp", fenster.getController().getGuiTyp() + "");
			root.appendChild(modellroot);
			modellroot.appendChild(getXMLVonKomponenteUndEingebettetenKomponenten(wurzel, null, document));
			// Dokument in Datei
			transformer = tfactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(tofile);
			transformer.transform(source, result);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}

	}

	public Element getXMLVonKomponenteUndEingebettetenKomponenten(KomponentenBasisInfo kinfo,
			KomponentenBasisInfo uebergeordneteKomponente, Document document) {
		Element elKomponente = document.createElement("Komponente");
		Element item = document.createElement("KomponentenTyp");
		item.appendChild(document.createTextNode(kinfo.getKomponentenTyp()));
		elKomponente.appendChild(item);
		item = document.createElement("VariablenName");
		item.appendChild(document.createTextNode(kinfo.getVariablenName()));
		elKomponente.appendChild(item);
		if (uebergeordneteKomponente != null) {
			item = document.createElement("VariablenNameUebergeordneteKomponente");
			item.appendChild(document.createTextNode(uebergeordneteKomponente.getVariablenName()));
			elKomponente.appendChild(item);
		}

		for (GrundEigenschaft e : kinfo.getEigenschaften()) {
			if (e instanceof EigenschaftHintergrundFarbe) {
				EigenschaftHintergrundFarbe ehf = (EigenschaftHintergrundFarbe) e;
				if (ehf.hintergrundFarbeGeaendert()) {
					elKomponente.appendChild(this.getXMLVonKomponentenEigenschaft(ehf, document));
				}
			} else {

				elKomponente.appendChild(this.getXMLVonEigenschaft(e, document));
			}
		}

		for (KomponentenBasisInfo ki : kinfo.getEingebetteteKomponenten()) {
			elKomponente.appendChild(getXMLVonKomponenteUndEingebettetenKomponenten(ki, kinfo, document));
		}
		return elKomponente;
	}

	private Node getXMLVonEigenschaft(GrundEigenschaft e, Document document) {
		if (e instanceof KomponentenEigenschaft) {
			return getXMLVonKomponentenEigenschaft((KomponentenEigenschaft) e, document);
		}
		if (e instanceof LauscherEigenschaft) {
			return getXMLVonLauscherEigenschaft((LauscherEigenschaft) e, document);
		}
		if (e instanceof EreignisBearbeiteMethodenEigenschaft) {
			return getXMLVonBearbeiteMethodenEigenschaft((EreignisBearbeiteMethodenEigenschaft) e, document);
		}
		return null;
	}

	/**
	 * @param e
	 * @param document
	 * @return
	 */
	private Node getXMLVonEintraegeComboBoxEigenschaftOderJList(KomponentenEigenschaft e, Document document) {
		Element eg = document.createElement("KomponentenEigenschaft");
		Element attr = document.createElement("EigenschaftsName");
		eg.appendChild(attr);
		Text tAttr = document.createTextNode(e.getEigenschaftsName());
		attr.appendChild(tAttr);
		attr = document.createElement("WertDerEigenschaft");
		Vector<String> als = (Vector<String>) e.getWert();
		String[] st = null;
		if (als.size() > 0) {
			st = new String[als.size()];
			st = als.toArray(st);
		}
		attr.appendChild(getXMLVonStringArray(st, document));
		eg.appendChild(attr);
		attr = document.createElement("PrimaererRang");
		eg.appendChild(attr);
		tAttr = document.createTextNode((int) e.getPrimaererRang() + "");
		attr.appendChild(tAttr);
		attr = document.createElement("SekundaererRang");
		eg.appendChild(attr);
		tAttr = document.createTextNode((int) e.getSekundaererRang() + "");
		attr.appendChild(tAttr);
		return eg;
	}

	/**
	 * @param e
	 * @param document
	 * @return
	 */
	private Node getXMLVonLauscherEigenschaft(LauscherEigenschaft e, Document document) {
		Element eg = document.createElement("LauscherEigenschaft");
		Element attr = document.createElement("LauscherInfoKlasse");
		eg.appendChild(attr);
		Text tAttr = document.createTextNode(((LauscherEigenschaft) e).getLauscherinfo().getClass().getName());
		attr.appendChild(tAttr);

		attr = document.createElement("Lauscherwert");
		eg.appendChild(attr);
		tAttr = document.createTextNode((boolean) e.getWert() + "");
		attr.appendChild(tAttr);

		attr = document.createElement("kompatibleGUI");
		eg.appendChild(attr);
		tAttr = document.createTextNode((int) e.getKompatibleGUI() + "");
		attr.appendChild(tAttr);

		attr = document.createElement("PrimaererRang");
		eg.appendChild(attr);
		tAttr = document.createTextNode((int) e.getPrimaererRang() + "");
		attr.appendChild(tAttr);
		attr = document.createElement("SekundaererRang");
		eg.appendChild(attr);
		tAttr = document.createTextNode((int) e.getSekundaererRang() + "");
		attr.appendChild(tAttr);
		return eg;

	}

	/**
	 * @param e
	 * @param document
	 * @return
	 */
	private Node getXMLVonKomponentenEigenschaft(KomponentenEigenschaft e, Document document) {
		if (e instanceof EigenschaftEintragFuerJComboBox) {
			return getXMLVonEintraegeComboBoxEigenschaftOderJList(e, document);
		}
		if (e instanceof EigenschaftEintragFuerJList) {
			return getXMLVonEintraegeComboBoxEigenschaftOderJList(e, document);
		}
		if (e instanceof EigenschaftTextGroesse) {
			return getXMLVonEigenschaftTextGroesse((EigenschaftTextGroesse) e, document);
		}

		Element eg = document.createElement("KomponentenEigenschaft");

		Element attr = document.createElement("EigenschaftsName");
		eg.appendChild(attr);
		Text tAttr = document.createTextNode(e.getEigenschaftsName());
		attr.appendChild(tAttr);
		
		attr = document.createElement("EigenschaftsTyp");
		eg.appendChild(attr);
		tAttr = document.createTextNode(e.getEigenschaftsTyp());
		attr.appendChild(tAttr);
		/*
		if (e.getSetterNameSwing() != null && !e.getSetterNameSwing().equals("")) {
			attr = document.createElement("SetzeMethodenNameSwing");
			eg.appendChild(attr);
			tAttr = document.createTextNode(e.getSetterNameSwing());
			attr.appendChild(tAttr);
		}
		if (e.getSetterNameBasis() != null && !e.getSetterNameBasis().equals("")) {
			attr = document.createElement("SetzeMethodenNameBasis");
			eg.appendChild(attr);
			tAttr = document.createTextNode(e.getSetterNameBasis());
			attr.appendChild(tAttr);
		}
		*//*
		if (e.getSetterParameterTypen() != null) {
			
			attr = document.createElement("SetterParameterTypen");
			eg.appendChild(attr);
			attr.appendChild(getXMLVonStringArray(e.getSetterParameterTypen(), document));
			if (e.getSetterParameterNamen() != null) {
				attr = document.createElement("SetterParameterNamen");
				attr.appendChild(getXMLVonStringArray(e.getSetterParameterNamen(), document));
				eg.appendChild(attr);
			}
			*/
			String[] wertAlsStringFeld = e.getStringFeldFuerEinfacheWerte();
			if (wertAlsStringFeld != null) {
				attr = document.createElement("WertDerEigenschaft");
				attr.appendChild(getXMLVonStringArray(wertAlsStringFeld, document));
				eg.appendChild(attr);
			}
			/*
		}
		*/
		attr = document.createElement("PrimaererRang");
		eg.appendChild(attr);
		tAttr = document.createTextNode((int) e.getPrimaererRang() + "");
		attr.appendChild(tAttr);
		attr = document.createElement("SekundaererRang");
		eg.appendChild(attr);
		tAttr = document.createTextNode((int) e.getSekundaererRang() + "");
		attr.appendChild(tAttr);
		return eg;

	}

	/**
	 * @param e
	 * @param document
	 * @return
	 */
	private Node getXMLVonEigenschaftTextGroesse(EigenschaftTextGroesse e, Document document) {
		Element eg = document.createElement("KomponentenEigenschaft");
		Element attr = document.createElement("EigenschaftsName");
		eg.appendChild(attr);
		Text tAttr = document.createTextNode("Schriftgroesse");
		attr.appendChild(tAttr);
		String[] wertAlsStringFeld = e.getStringFeldFuerEinfacheWerte();
		if (wertAlsStringFeld != null) {
			attr = document.createElement("WertDerEigenschaft");
			attr.appendChild(getXMLVonStringArray(wertAlsStringFeld, document));
			eg.appendChild(attr);
		}
		attr = document.createElement("Standardgroesse");
		eg.appendChild(attr);
		tAttr = document.createTextNode(e.getStandardgroesse() + "");
		attr.appendChild(tAttr);
		attr = document.createElement("PrimaererRang");
		eg.appendChild(attr);
		tAttr = document.createTextNode((int) e.getPrimaererRang() + "");
		attr.appendChild(tAttr);
		attr = document.createElement("SekundaererRang");
		eg.appendChild(attr);
		tAttr = document.createTextNode((int) e.getSekundaererRang() + "");
		attr.appendChild(tAttr);
		return eg;
	}

	/**
	 * @param e
	 * @param document
	 * @return
	 */
	private Node getXMLVonBearbeiteMethodenEigenschaft(EreignisBearbeiteMethodenEigenschaft e, Document document) {
		Element eg = document.createElement("BearbeiteEigenschaft");
		String bearbeiteEigenschaftKlasse = "BearbeiteMethodeEigenschaft";
		if (e instanceof BearbeiteMethodeFuerMausEigenschaft) {
			bearbeiteEigenschaftKlasse = "BearbeiteMethodeMausEigenschaft";
		} else if (e instanceof BearbeiteMethodeFuerTastaturEigenschaft) {
			bearbeiteEigenschaftKlasse = "BearbeiteMethodeTastaturEigenschaft";
		}
		Element attr = document.createElement("BearbeiteMethodenKlasse");
		Text tAttr = document.createTextNode(bearbeiteEigenschaftKlasse);
		attr.appendChild(tAttr);
		eg.appendChild(attr);

		attr = document.createElement("BearbeiteMethodeBezeichner");
		tAttr = document.createTextNode(((EreignisBearbeiteMethodenEigenschaft) e).getMethodenName());
		attr.appendChild(tAttr);
		eg.appendChild(attr);

		attr = document.createElement("KlasseZuDerBearbeiteMethodeGehoert");
		tAttr = document.createTextNode(((EreignisBearbeiteMethodenEigenschaft) e).getKomponentenTyp());
		attr.appendChild(tAttr);
		eg.appendChild(attr);

		attr = document.createElement("BearbeiteMethodeImplementieren");
		tAttr = document.createTextNode("" + (Boolean) ((EreignisBearbeiteMethodenEigenschaft) e).getWert());
		attr.appendChild(tAttr);
		eg.appendChild(attr);

		attr = document.createElement("PrimaererRang");
		eg.appendChild(attr);
		tAttr = document.createTextNode((int) e.getPrimaererRang() + "");
		attr.appendChild(tAttr);

		attr = document.createElement("SekundaererRang");
		eg.appendChild(attr);
		tAttr = document.createTextNode((int) e.getSekundaererRang() + "");
		attr.appendChild(tAttr);
		return eg;

	}

	public Element getXMLVonStringArray(String[] sf, Document document) {
		Element att = document.createElement("Stringarray");
		int laenge = sf == null ? 0 : sf.length;
		att.setAttribute("feldgroesse", laenge + "");
		Element attr;
		Text tAttr;
		for (int i = 0; i < laenge; i++) {
			attr = document.createElement("String");
			attr.setAttribute("nr", i + "");
			tAttr = document.createTextNode(sf[i]);
			attr.appendChild(tAttr);
			att.appendChild(attr);
		}
		return att;
	}

	public void ladeXMLEntwurf(DesignerFenster fenster, File vorgabeDirectory) throws ParserConfigurationException {
		this.fenster = fenster;
		File file;

		try {
			FileNameExtensionFilter filter = new FileNameExtensionFilter("GUI", "GUIXML");
			fcxml.setFileFilter(filter);
			if (vorgabeDirectory != null) {
				fcxml.setCurrentDirectory(vorgabeDirectory);
			} else {
				if (xumldiagrammdateiname.toLowerCase().endsWith(dateiSuffix.toLowerCase())) {
					File vorhanden = new File(xumldiagrammdateiname);
					fcxml.setSelectedFile(new File(xumldiagrammdateiname));
				}
			}
			int returnVal = fcxml.showOpenDialog(fenster);

			if (returnVal != JFileChooser.APPROVE_OPTION) {
				return;
			}
			file = fcxml.getSelectedFile();
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(fenster, "Dateifehler:\n" + exc.getMessage());
			exc.printStackTrace();
			return;
		}
		if (file.isDirectory()) {
			JOptionPane.showMessageDialog(fenster, "Dateifehler");
			return;
		}
		if (!file.canRead()) {
			JOptionPane.showMessageDialog(fenster, "Dateifehler");
			return;
		}
		xumldiagrammdateiname = file.getAbsolutePath();
		Document document;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		try {
			document = builder.parse(file);
			Element root = document.getDocumentElement();
			if (root == null) {
				JOptionPane.showMessageDialog(fenster, "Dateifehler");
				return;
			}
			NodeList childs = root.getChildNodes();
			for (int k = 0; k < childs.getLength(); k++) {
				if ("Oberflaeche".equals((childs.item(k)).getNodeName())) {

					int guiTyp = Integer.parseInt(((Element) (childs.item(k))).getAttribute("guiTyp"));

					NodeList ochilds = childs.item(k).getChildNodes();
					for (int kk = 0; kk < childs.getLength(); kk++) {
						if ("Komponente".equals((ochilds.item(kk)).getNodeName())) {
							erzeugeOberflaeche(ochilds.item(kk), fenster.getController(), guiTyp);
						}
					}
				}
			}

		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void erzeugeOberflaeche(Node firstChild, Controller controller, int guiTyp) {

		KomponentenInfoGrundeigenschaften kfenster = liesKomponente(firstChild);
		controller.erzeugeNeueOberflaeche(kfenster, guiTyp);
	}

	public KomponentenInfoGrundeigenschaften liesKomponente(Node item) {
		KomponentenInfoGrundeigenschaften kinfo = new KomponentenInfoGrundeigenschaften();
		// System.out.println(item.getNodeName());
		NodeList childs = item.getChildNodes();
		for (int k = 0; k < childs.getLength(); k++) {
			if ("KomponentenTyp".equals(childs.item(k).getNodeName())) {
				kinfo.setKomponentenTyp(liesText(childs.item(k)));
			}
			if ("VariablenName".equals(childs.item(k).getNodeName())) {
				kinfo.setVariablenName(liesText(childs.item(k)));
			}
			if ("VariablenNameUebergeordneteKomponente".equals(childs.item(k).getNodeName())) {
				kinfo.setVariablenNameUebergeordnet(liesText(childs.item(k)));
			}
			if ("KomponentenEigenschaft".equals(childs.item(k).getNodeName())) {
				kinfo.addEigenschaft(liesKomponentenEigenschaft((Element) childs.item(k)));
			}
			if ("LauscherEigenschaft".equals(childs.item(k).getNodeName())) {
				kinfo.addEigenschaft(liesLauscherEigenschaft((Element) childs.item(k)));
			}
			if ("BearbeiteEigenschaft".equals(childs.item(k).getNodeName())) {
				kinfo.addEigenschaft(liesBearbeiteEigenschaft((Element) childs.item(k)));
			}

		}
		for (int k = 0; k < childs.getLength(); k++) {
			if ("Komponente".equals(childs.item(k).getNodeName())) {
				KomponentenInfoGrundeigenschaften eingeb = liesKomponente((Element) childs.item(k));
				eingeb.setVariablenNameUebergeordnet(kinfo.getVariablenName());
				kinfo.addEingebetteteKomponente(eingeb);
			}
		}
		return kinfo;
	}

	private String liesText(Node element) {
		NodeList childs = element.getChildNodes();
		for (int k = 0; k < childs.getLength(); k++) {
			if (childs.item(k) instanceof Text) {
				return ((Text) childs.item(k)).getTextContent();
			}
		}
		return "";
	}

	private String[] liesTextFeld(Node element) {
		NodeList childs = element.getChildNodes();
		for (int kk = 0; kk < childs.getLength(); kk++) {
			if ("Stringarray".equals(childs.item(kk).getNodeName())) {
				int feldgroesse = Integer.parseInt(((Element) (childs.item(kk))).getAttribute("feldgroesse"));
				String[] strings = null;
				if (feldgroesse > 0) {
					NodeList subchilds = childs.item(kk).getChildNodes();
					strings = new String[feldgroesse];
					for (int k = 0; k < subchilds.getLength(); k++) {
						if ("String".equals(subchilds.item(k).getNodeName())) {
							int index = Integer.parseInt(((Element) subchilds.item(k)).getAttribute("nr"));
							strings[index] = liesText(subchilds.item(k));

						}
					}
				}
				return strings;
			}
		}
		return null;
	}

	private KomponentenEigenschaft liesKomponentenEigenschaft(Element item) {
		// System.out.println(item.getNodeName());
		KomponentenEigenschaft eg = null;
		String eigenschaftsName = null;
		String eigenschaftsTyp = null;
//		String setterNameSwing = null;
//		String setterNameBasis = null;
//		String[] setterParameterTypen = null;
//		String[] setterParameterNamen = null;
		int primaererRang = 100;
		int sekundaererRang = 100;
		String[] wertFeld = null;
		NodeList childs = item.getChildNodes();
		// System.out.println(childs.getLength());

		for (int k = 0; k < childs.getLength(); k++) {
			if ("EigenschaftsName".equals(childs.item(k).getNodeName())) {
				eigenschaftsName = liesText(childs.item(k));
				if (eigenschaftsName.equals("EintraegeComboBox")) {
					return liesEigenschaftEintraegeComboBoxOderJList(item, true);
				}
				if (eigenschaftsName.equals("EintraegeJList")) {
					return liesEigenschaftEintraegeComboBoxOderJList(item, false);
				}
				if (eigenschaftsName.equals("Schriftgroesse")) {
					return liesEigenschaftTextGroesse(item);
				}
			}
		}

		for (int k = 0; k < childs.getLength(); k++) {
			// System.out.println(childs.item(k).getNodeName());
			if ("EigenschaftsName".equals(childs.item(k).getNodeName())) {
				eigenschaftsName = liesText(childs.item(k));
				// System.out.println(eigenschaftsName);
			} else if ("EigenschaftsTyp".equals((childs.item(k)).getNodeName())) {
				eigenschaftsTyp = liesText(childs.item(k));
			} 
//			else if ("SetzeMethodenNameSwing".equals((childs.item(k)).getNodeName())) {
//				setterNameSwing = liesText(childs.item(k));
//			} else if ("SetzeMethodenNameBasis".equals((childs.item(k)).getNodeName())) {
//				setterNameBasis = liesText(childs.item(k));
//			} else if ("SetterParameterTypen".equals((childs.item(k)).getNodeName())) {
//				setterParameterTypen = liesTextFeld(childs.item(k));
//			} else if ("SetterParameterNamen".equals((childs.item(k)).getNodeName())) {
//				setterParameterNamen = liesTextFeld(childs.item(k));
//			} 
			else if ("WertDerEigenschaft".equals((childs.item(k)).getNodeName())) {
				wertFeld = liesTextFeld(childs.item(k));
			} else if ("PrimaererRang".equals((childs.item(k)).getNodeName())) {
				primaererRang = Integer.parseInt(liesText(childs.item(k)));
			} else if ("SekundaererRang".equals((childs.item(k)).getNodeName())) {
				sekundaererRang = Integer.parseInt(liesText(childs.item(k)));
			}
		}

		eg = new KomponentenEigenschaft(eigenschaftsName, eigenschaftsTyp, null, primaererRang, sekundaererRang);
		eg.setWertUeberStringFeld(wertFeld);
		return eg;
	}

	private KomponentenEigenschaft liesEigenschaftTextGroesse(Element item) {
		// System.out.println(item.getNodeName());
		KomponentenEigenschaft eg = null;
		String eigenschaftsName = null;
		int primaererRang = 100;
		int sekundaererRang = 100;
		int standardgroesse = 12;
		String[] wertFeld = null;
		NodeList childs = item.getChildNodes();
		for (int k = 0; k < childs.getLength(); k++) {
			if ("EigenschaftsName".equals(childs.item(k).getNodeName())) {
				eigenschaftsName = liesText(childs.item(k));
			} else if ("WertDerEigenschaft".equals((childs.item(k)).getNodeName())) {
				wertFeld = liesTextFeld(childs.item(k));
			} else if ("Standardgroesse".equals((childs.item(k)).getNodeName())) {
				standardgroesse = Integer.parseInt(liesText(childs.item(k)));
			} else if ("PrimaererRang".equals((childs.item(k)).getNodeName())) {
				primaererRang = Integer.parseInt(liesText(childs.item(k)));
			} else if ("SekundaererRang".equals((childs.item(k)).getNodeName())) {
				sekundaererRang = Integer.parseInt(liesText(childs.item(k)));
			}
		}
		eg = new EigenschaftTextGroesse(eigenschaftsName, standardgroesse, primaererRang, sekundaererRang);
		eg.setWertUeberStringFeld(wertFeld);
		return eg;
	}

	// private KomponentenEigenschaft liesEigenschaftEintraegeJList(Element
	// item) {
	// // System.out.println(item.getNodeName());
	// KomponentenEigenschaft eg = null;
	// Object wert = null;
	// int primaererRang = 100;
	// int sekundaererRang = 100;
	// NodeList childs = item.getChildNodes();
	// for (int k = 0; k < childs.getLength(); k++) {
	// if ("PrimaererRang".equals((childs.item(k)).getNodeName())) {
	// primaererRang = Integer.parseInt(liesText(childs.item(k)));
	// } else if ("SekundaererRang".equals(childs.item(k).getNodeName())) {
	// sekundaererRang = Integer.parseInt(liesText(childs.item(k)));
	// } else if ("WertDerEigenschaft".equals(childs.item(k).getNodeName())) {
	// wert = liesTextFeld(childs.item(k));
	// }
	// }
	// eg = new EigenschaftEintragFuerJList(wert, primaererRang,
	// sekundaererRang);
	// return eg;
	// }

	private KomponentenEigenschaft liesEigenschaftEintraegeComboBoxOderJList(Element item, boolean istComboBox) {
		// System.out.println(item.getNodeName());
		KomponentenEigenschaft eg = null;
		Vector<String> als = new Vector();
		int primaererRang = 100;
		int sekundaererRang = 100;
		NodeList childs = item.getChildNodes();
		for (int k = 0; k < childs.getLength(); k++) {
			if ("PrimaererRang".equals((childs.item(k)).getNodeName())) {
				primaererRang = Integer.parseInt(liesText(childs.item(k)));
			} else if ("SekundaererRang".equals(childs.item(k).getNodeName())) {
				sekundaererRang = Integer.parseInt(liesText(childs.item(k)));
			} else if ("WertDerEigenschaft".equals(childs.item(k).getNodeName())) {
				String[] stfeld = liesTextFeld(childs.item(k));
				if (stfeld != null) {
					for (String s : stfeld) {
						als.add(s);
					}
				}
			}
		}
		if (istComboBox) {
			eg = new EigenschaftEintragFuerJComboBox(als, primaererRang, sekundaererRang);
		} else {
			eg = new EigenschaftEintragFuerJList(als, primaererRang, sekundaererRang);
		}
		return eg;

	}

	private GrundEigenschaft liesBearbeiteEigenschaft(Element item) {
		// System.out.println(item.getNodeName());
		GrundEigenschaft eg = null;
		String eigenschaftsName = null;
		String eigenschaftsTyp = null;
		boolean wert = false;
		int primaererRang = 100;
		int sekundaererRang = 100;
		String bearbeiteMethode = null;
		String klasseZuBearbeiteMethode = null;

		String bearbeiteMethodenKlasse = null;
		// System.out.println(childs.getLength());
		NodeList childs = item.getChildNodes();
		for (int k = 0; k < childs.getLength(); k++) {
			// System.out.println(childs.item(k).getNodeName());

			if ("BearbeiteMethodeEigenschaft".equals(childs.item(k).getNodeName())
					|| "BearbeiteMethodeMausEigenschaft".equals(childs.item(k).getNodeName())
					|| "BearbeiteMethodeTastaturEigenschaft".equals(childs.item(k).getNodeName())

			) {
				bearbeiteMethodenKlasse = childs.item(k).getNodeName();
				NodeList subchilds = childs.item(k).getChildNodes();
				for (int sc = 0; sc < subchilds.getLength(); sc++) {
					if ("BearbeiteMethodeBezeichner".equals(subchilds.item(sc).getNodeName())) {
						bearbeiteMethode = liesText(subchilds.item(sc));
					} else if ("KlasseZuDerBearbeiteMethodeGehoert".equals(subchilds.item(sc).getNodeName())) {
						klasseZuBearbeiteMethode = liesText(subchilds.item(sc));
					}
				}
			}
			if ("BearbeiteMethodeBezeichner".equals(childs.item(k).getNodeName())) {
				bearbeiteMethode = liesText(childs.item(k));
			} else if ("BearbeiteMethodenKlasse".equals(childs.item(k).getNodeName())) {
				bearbeiteMethodenKlasse = liesText(childs.item(k));
			} else if ("BearbeiteMethodenKlasse".equals(childs.item(k).getNodeName())) {
				bearbeiteMethodenKlasse = liesText(childs.item(k));
			} else if ("KlasseZuDerBearbeiteMethodeGehoert".equals(childs.item(k).getNodeName())) {
				klasseZuBearbeiteMethode = liesText(childs.item(k));
				// System.out.println(eigenschaftsName);
			} else if ("EigenschaftsTyp".equals((childs.item(k)).getNodeName())) {
				eigenschaftsTyp = liesText(childs.item(k));
			} else if ("BearbeiteMethodeImplementieren".equals((childs.item(k)).getNodeName())) {
				wert = Boolean.parseBoolean(liesText(childs.item(k)));
			} else if ("PrimaererRang".equals((childs.item(k)).getNodeName())) {
				primaererRang = Integer.parseInt(liesText(childs.item(k)));
			} else if ("SekundaererRang".equals((childs.item(k)).getNodeName())) {
				sekundaererRang = Integer.parseInt(liesText(childs.item(k)));
			}
		}

		if (bearbeiteMethodenKlasse != null) {
			switch (bearbeiteMethodenKlasse) {
			case "BearbeiteMethodeEigenschaft":
				eg = new EreignisBearbeiteMethodenEigenschaft(klasseZuBearbeiteMethode, bearbeiteMethode, wert,
						primaererRang, sekundaererRang);
				break;
			case "BearbeiteMethodeMausEigenschaft":
				eg = new BearbeiteMethodeFuerMausEigenschaft(bearbeiteMethode, wert, primaererRang, sekundaererRang);
				break;
			case "BearbeiteMethodeTastaturEigenschaft":
				eg = new BearbeiteMethodeFuerTastaturEigenschaft(bearbeiteMethode, wert, primaererRang,
						sekundaererRang);
			}
			if (eg != null) {
				eg.setWert(wert);
			}
		}
		return eg;
	}

	private LauscherEigenschaft liesLauscherEigenschaft(Element item) {
		// System.out.println(item.getNodeName());
		LauscherEigenschaft eg = null;
		int primaererRang = 100;
		int sekundaererRang = 100;
		String lauscherInfoKlasse = null;
		boolean wert = false;
		int kompatibleGui = 0;
		NodeList childs = item.getChildNodes();
		// System.out.println(childs.getLength());

		for (int k = 0; k < childs.getLength(); k++) {
			// System.out.println(childs.item(k).getNodeName());
			if ("LauscherInfoKlasse".equals(childs.item(k).getNodeName())) {
				lauscherInfoKlasse = liesText(childs.item(k));
			} else if ("Lauscherwert".equals((childs.item(k)).getNodeName())) {
				wert = Boolean.parseBoolean(liesText(childs.item(k)));
			} else if ("kompatibleGUI".equals((childs.item(k)).getNodeName())) {
				kompatibleGui = Integer.parseInt(liesText(childs.item(k)));
			} else if ("PrimaererRang".equals((childs.item(k)).getNodeName())) {
				primaererRang = Integer.parseInt(liesText(childs.item(k)));
			} else if ("SekundaererRang".equals((childs.item(k)).getNodeName())) {
				sekundaererRang = Integer.parseInt(liesText(childs.item(k)));
			}
		}

		// LauscherInfo li =
		try {
			Class<?> lkasse = Class.forName(lauscherInfoKlasse);
			LauscherInfo li = (LauscherInfo) lkasse.getConstructor().newInstance();
			eg = new LauscherEigenschaft(li, wert, primaererRang, sekundaererRang);
			eg.setWert(wert);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		return eg;
	}

}
