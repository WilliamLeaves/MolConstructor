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
		long st = System.currentTimeMillis();
		MoleculeDepot md = null;
		IOservice io = new IOservice();
		try {
			md = new MoleculeDepot(io.loadMolecules());
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] str = { "D", "A" };
		// String[] str={"A","D","A"};
		// String[] str = { "D", "A", "D" };
		// String[] str = { "D", "S", "A" };
		// String[] str = { "D", "S", "A", "S", "D" };
		// String[] str = { "A", "S", "D", "S", "A" };
		// String[] str = { "C", "S", "D", "S", "C" };
		// String[] str = { "C", "A", "S", "D", "S", "A", "C" };
		// String[] str = { "C", "D", "S", "A", "S", "D", "C" };
		//String[] str = { "C", "D", "S", "A", "C" };

		md.cmbRuleCreate(str, true);
		for (CmbMolecule cm : md.db_link.cmbList) {
			//io.export(cm);
		}
		long se = System.currentTimeMillis();
		System.out.println("run time: " + String.valueOf(se - st) + " millis");
	}
}
