package DB;

import java.util.ArrayList;

import SV_support.CmbMolecule;

public class DBLink {

	// stub field and method
	public ArrayList<CmbMolecule> cmbList = new ArrayList<CmbMolecule>();

	public void getCmbMol(CmbMolecule cm) {
		this.cmbList.add(cm);
	}

}
