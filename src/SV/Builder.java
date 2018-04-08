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
		for (int i = 0; i < molList.length; i++) {
			System.out.print(cr.typeList.get(i).toString() + molList[i].index + "-");
		}
		System.out.println();
		for (int i = 1; i < molList.length; i++) {
			this.combine(molList[i], cm);

			cm.name = cm.name.concat("-" + molList[i].getType().toString() + String.valueOf(molList[i].index));
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
	 */
	private void combine(Molecule mol, CmbMolecule cmol) {
		boolean isComplete = false;
		int num = 0;
		int split = 4;
		for (int i = 0; i < split; i++) {
			Molecule mol_clone = mol.getClone();
			CmbMolecule cmol_clone = cmol.getClone();
			int n = -1, m = -1, rIndex = -1;
			for (Atom a : mol_clone.atomList) {
				if (a.equal(mol_clone.getLeft())) {
					n = mol_clone.atomList.indexOf(a);
				}
				if (mol_clone.getRight() != null && a.equal(mol_clone.getRight())) {
					rIndex = mol_clone.atomList.indexOf(a);
				}
			}
			for (Atom a : cmol_clone.atomList) {
				if (a.equal(cmol_clone.terminalAtom)) {
					m = cmol_clone.atomList.indexOf(a);
				}
			}

			for (Atom a : mol_clone.atomList) {
				a.clockWiseRotateX(360 * i / split);
			}

			double[] axis;
			axis = aligment(cmol_clone.terminalAtom, mol_clone.getNearestAtom(mol_clone.getLeft()));
			cmol_clone.atomList.remove(m);
			mol_clone.atomList.remove(n);
			for (Atom a : mol_clone.atomList) {
				a.transCoordinate(axis);

			}
			if (this.isNotOverlap(cmol_clone, mol_clone)) {
				num = i;
				isComplete = true;
				break;
			}
		}
		if (isComplete == false) {
			System.out.println("method overlap error");
		}
		int n = -1, m = -1, rIndex = -1;
		for (Atom a : mol.atomList) {
			if (a.equal(mol.getLeft())) {
				n = mol.atomList.indexOf(a);
			}
			if (mol.getRight() != null && a.equal(mol.getRight())) {
				rIndex = mol.atomList.indexOf(a);
			}
		}
		for (Atom a : cmol.atomList) {
			if (a.equal(cmol.terminalAtom)) {
				m = cmol.atomList.indexOf(a);
			}
		}
		for (Atom a : mol.atomList) {
			a.clockWiseRotateX(360 * num / split);
		}
		System.out.println("X rotated " + 360 * num / split + " degree");
		double[] axis;
		axis = aligment(cmol.terminalAtom, mol.getNearestAtom(mol.getLeft()));
		cmol.atomList.remove(m);

		for (Atom a : mol.atomList) {
			a.transCoordinate(axis);

		}
		for (Atom a : mol.atomList) {
			if (mol.atomList.indexOf(a) != n)
				cmol.atomList.add(a);
		}

		if (mol.getRight() != null && rIndex != -1) {
			cmol.terminalAtom = mol.getRight();
			System.out.println("terminal atom changed");
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
		axis[0] = left.innerX - right.innerX + 0.3;
		axis[1] = left.innerY - right.innerY;
		axis[2] = left.innerZ - right.innerZ;
		return axis;
	}

	public boolean isNotOverlap(CmbMolecule cmol, Molecule mol) {
		for (Atom a : cmol.atomList) {
			for (Atom b : mol.atomList) {
				double dissqrt = a.calDistance(b);
				// System.out.println(dissqrt);
				if (dissqrt <1.75) {
					if (!a.equal(cmol.terminalAtom) || !b.equal(mol.leftAtom)) {
						return false;
					}
				}
			}
		}
		return true;
	}
}
