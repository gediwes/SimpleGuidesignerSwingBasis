package guidesigner.oberflaeche;

import guidesigner.eigenschaften.GrundEigenschaft;

public interface EditorInterface {

	GrundEigenschaft getEigenschaft();

	void aktualisiereEigenschaftsanzeige(GrundEigenschaft eigenschaft);

}