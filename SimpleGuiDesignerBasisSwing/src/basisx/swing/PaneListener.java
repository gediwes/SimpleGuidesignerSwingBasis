
package basisx.swing;

import java.util.*;
/**
 * CanvasLauscher 
 * Anmeldung/Abmeldung ueber setzeCanvasLauscher/entferneCanvasLauscher
 * bei Canvas-Objekten
 */
public interface PaneListener extends EventListener {
    /**
     * Antwort auf canvasGezeichnet-Ereignis fuer CanvasLauscher.
     * Das canvasGezeichnet-Ereignis wird ausgeloest, wenn der Canvas durch
     * Groessenaenderung, in den Vorder- oder Hintergrund-Stellen oder bei Aenderung der Sichtbarkeit neu dargestellt werden musste
     */
    public void paneChanged(BufferPane l);
}
