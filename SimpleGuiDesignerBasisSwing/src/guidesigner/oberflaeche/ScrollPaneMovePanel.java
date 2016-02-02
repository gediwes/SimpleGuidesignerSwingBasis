/**
 * 
 */
package guidesigner.oberflaeche;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

import guidesigner.control.Controller;

/**
 * @author Georg Dick
 *
 */
public class ScrollPaneMovePanel extends MovePanel{

	private JScrollPane jsp;

	/**
	 * @param controller
	 */
	public ScrollPaneMovePanel(Controller controller) {
		super(controller);
		jsp = new JScrollPane();
		this.getAnzeigepanel().add(jsp);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);		
	}

	/**
	 * @see guidesigner.oberflaeche.MovePanel#setInhaltsKomponente(javax.swing.JComponent)
	 */
	@Override
	public void setInhaltsKomponente(JComponent inhaltsKomponente) {
		this.inhaltsKomponente = inhaltsKomponente;
		anzeigepanel.remove(jsp);
		jsp = new JScrollPane(inhaltsKomponente);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		anzeigepanel.add(jsp, BorderLayout.CENTER);
	}
	
}
