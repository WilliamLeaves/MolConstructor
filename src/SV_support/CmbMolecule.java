package SV_support;

import java.util.ArrayList;

public class CmbMolecule {
	public CmbRule rule;
	public String name;
	public ArrayList<Atom> atomList = new ArrayList<Atom>();
	public Atom terminalAtom;

	public CmbMolecule() {

	}

	public CmbMolecule(CmbRule rule, Molecule mol) {
		this.rule = rule;
		this.name = mol.getType().toString() + String.valueOf(mol.index);
		for (Atom a : mol.atomList) {
			this.atomList.add(a);
		}
		this.terminalAtom = mol.getRight();
	}

	/**
	 * make terminal atom of the combination and its neighbor along the x-axis
	 */
	public void CmbMolRotateToFit() {

	}

	public CmbMolecule getClone() {
		CmbMolecule cm = new CmbMolecule();

		cm.rule = this.rule;
		cm.name = this.name;
		for (Atom a : this.atomList) {
			Atom atom = (Atom) a.clone();
			cm.atomList.add(atom);
			if (a.equal(this.terminalAtom)) {
				cm.terminalAtom = atom;
			}
		}

		return cm;
	}
}
