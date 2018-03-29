package SV;

import SV_support.Atom;
import SV_support.CmbMolecule;
import SV_support.CmbRule;
import SV_support.Molecule;

public class Builder {
	/**
	 * form a new combination molecule by its name list and combination rule
	 * 
	 * @param cr
	 * @param molList
	 * @return
	 */
	public CmbMolecule bulidCmbMol(CmbRule cr, Molecule[] molList) {
		CmbMolecule cm = new CmbMolecule(cr, molList[0]);
		for (int i = 1; i < molList.length; i++) {
			this.combine(molList[i], cm);
			cm.name = cm.name.concat("-" + molList[i].name);
		}
		return cm;
	}

	/**
	 * to add a molecule into a half-finished combination molecule,remove the H atom
	 * and recalculate the coordinates
	 * 
	 * if isPositive is true ,means the molecule remove the left side H atom £¬vice
	 * versa
	 * 
	 * @param mol
	 * @param cmol
	 * @param isPositive
	 */
	private void combine(Molecule mol, CmbMolecule cmol) {
		double[] axis;

		axis = aligment(cmol.terminalAtom, mol.getLeft());
		mol.atomList.remove(mol.getLeft());

		for (Atom a : mol.atomList) {
			a.transCoordinate(axis);
			cmol.atomList.add(a);
		}
	}

	/**
	 * to calculate a axis deviation when two H atom overlapping left atom always
	 * from the combination
	 * 
	 * @param left
	 * @param right
	 * @return
	 */
	private double[] aligment(Atom left, Atom right) {
		double[] axis = new double[3];
		axis[0] = left.innerX - right.innerX;
		axis[1] = left.innerX - right.innerY;
		axis[2] = left.innerZ - right.innerZ;
		return axis;
	}
}
