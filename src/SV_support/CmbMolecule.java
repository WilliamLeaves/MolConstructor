package SV_support;

import java.util.ArrayList;

public class CmbMolecule {
	public CmbRule rule;
	public String name;
	public ArrayList<Atom> atomList;
	public Atom terminalAtom;

	public CmbMolecule(CmbRule rule, Molecule mol) {
		this.rule = rule;
		this.name = mol.name;
		for (Atom a : mol.atomList) {
			this.atomList.add(a);
		}
		this.terminalAtom = mol.getLeft();
	}
}
