package guidesigner.oberflaeche;

import java.awt.Color;
import java.awt.Container;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;

import guidesigner.control.Controller;
import guidesigner.eigenschaften.GrundEigenschaft;
import guidesigner.eigenschaften.ereignisse.LauscherEigenschaft;
import guidesigner.eigenschaften.komponenten.KomponentenBasisInfo;
import guidesigner.eigenschaften.komponenten.KomponentenEigenschaft;

public class KomponentenEditor {
	KomponentenBasisInfo ki;
	EditorInterface[] kied;
	private KomponentenInfoVariablennameEditor vared;
	JPanel panel;
	private Controller controller;
	private JScrollPane scrollflaeche;
	private int guiTyp;

	/**
	 * @return liefert scrollflaeche
	 */
	public JScrollPane getEditor() {
		return scrollflaeche;
	}

	public KomponentenEditor(KomponentenBasisInfo ki, Controller controller, int guiTyp) {
		this.guiTyp = guiTyp;
		this.controller = controller;
		this.ki = ki;

		createEditor();
	}

	public void setzeSwing(int guiTyp) {
		if (this.guiTyp != guiTyp) {
			this.guiTyp = guiTyp;
			if (scrollflaeche.isShowing()) {
				scrollflaeche.setVisible(false);
				Container parent = scrollflaeche.getParent();
				scrollflaeche.getParent().remove(scrollflaeche);
				createEditor();
				parent.add(scrollflaeche);
				parent.revalidate();
			} else {
				createEditor();
			}

		}
	}

	

	private void createEditor() {
		panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		JLabel bKlassenname;
		panel.setLayout(new BorderLayout(0, 0));
		bKlassenname = new JLabel(ki.getKomponentenTyp());
		bKlassenname.setBackground(Color.LIGHT_GRAY);
		bKlassenname.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(bKlassenname, BorderLayout.NORTH);
		// }
		panel.setSize(300, bKlassenname.getHeight() + 5);
		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerSize(2);
		splitPane.setResizeWeight(0.6);
		splitPane.setContinuousLayout(true);
		panel.add(splitPane);

		JPanel panellinks = new JPanel();
		splitPane.setLeftComponent(panellinks);

		JPanel panelrechts = new JPanel();
		splitPane.setRightComponent(panelrechts);
		panelrechts.setLayout(null);
		// Variablenname
		vared = new KomponentenInfoVariablennameEditor(controller, ki, panellinks, panelrechts);
		panellinks.setLayout(null);
		// Sortieren
		//
		ArrayList<GrundEigenschaft> kompeig = ki.getEigenschaftenFuerGui(guiTyp);
		GrundEigenschaft[] eg = new GrundEigenschaft[kompeig.size()];
		for (GrundEigenschaft es : kompeig) {

			int ii = 0;
			for (int i = 0; i < kompeig.size(); i++) {
				GrundEigenschaft ei = kompeig.get(i);
				eg[ii] = ei;
				ii++;

			}
		}

		for (int j = 0; j < eg.length - 1; j++) {
			double minp = eg[j].getPrimaererRang();
			double mins = eg[j].getSekundaererRang();
			int mini = j;
			for (int i = j + 1; i < eg.length; i++) {
				if (minp > eg[i].getPrimaererRang()
						|| (minp == eg[i].getPrimaererRang() && mins > eg[i].getSekundaererRang())) {
					minp = eg[i].getPrimaererRang();
					mins = eg[i].getSekundaererRang();
					mini = i;
				}
			}
			if (mini != j) {
				GrundEigenschaft hilf = eg[j];
				eg[j] = eg[mini];
				eg[mini] = hilf;
			}
		}

		kied = new EditorInterface[eg.length];
		for (int i = 0; i < eg.length; i++) {
			if (eg[i] instanceof KomponentenEigenschaft) {
				kied[i] = new KomponentenEigenschaftseditor(controller, (KomponentenEigenschaft) eg[i], ki,
						panellinks, panelrechts);
			} else {
				kied[i] = new KomponentenEreignislauscherAuswahlEditor(controller, eg[i], ki, panellinks, panelrechts);

			}
		}
		JScrollPane scrollflaeche = new JScrollPane(panel);

		scrollflaeche.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollflaeche.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.scrollflaeche = scrollflaeche;

	}

	
	public KomponentenBasisInfo komponentenInfo() {
		return ki;
	}

	public void aktualisiereName(String name) {
		vared.aktualisiereName(name);
	}

	public void aktualisiereAlleEigenschaften() {
		for (GrundEigenschaft e : ki.getEigenschaften()) {
			aktualisiereEigenschaft(e);
		}
	}

	/**
	 * @param eigenschaft
	 */
	public void aktualisiereEigenschaft(GrundEigenschaft eigenschaft) {
		for (EditorInterface kedi : kied) {
			if (kedi.getEigenschaft().getEigenschaftsName().equals(eigenschaft.getEigenschaftsName())) {
				kedi.aktualisiereEigenschaftsanzeige(eigenschaft);
			}
		}

	}
}
