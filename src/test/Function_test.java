package test;

import java.io.IOException;

import SV.IOservice;
import SV.MoleculeDepot;
import SV_support.CmbMolecule;

public class Function_test {
	/**
	 * io test
	 * 
	 * @param args
	 * @throws CloneNotSupportedException
	 * @throws NumberFormatException
	 */
	public static void main(String[] args) throws NumberFormatException, CloneNotSupportedException {
		MoleculeDepot md = null;
		IOservice io = new IOservice();
		try {
			md = new MoleculeDepot(io.loadMolecules());
		} catch (IOException e) {
			e.printStackTrace();
		}
		// String[] str = { "C","A","C" };
		// md.cmbRuleCreate(str);
		// for (CmbMolecule cm : md.db_link.cmbList) {
		// io.export(cm);
		// }
	}
}
