package basisx.swing;

public interface PenListener {
	 /**
     * Antwort auf PenPainted -Ereignis fuer PenListener.
     * Das PenPainted-Ereignis wird ausgeloest, wenn mit dem Pen eine Zeichenoperation durchgefuehrt wurde.
     */
    public void penPainted(Object source);
}
