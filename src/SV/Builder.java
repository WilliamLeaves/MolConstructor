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
		// for (int i = 0; i < molList.length; i++) {
		// System.out.print(i + ":");
		// double d[] = molList[i].getAtomVector(molList[i].getLeft(),
		// molList[i].getNearestAtom(molList[i].getLeft()));
		// System.out.println(d[0] + "," + d[1] + "," + d[2]);
		// }

		// System.out.println(molList[0].atomList.size());
		for (int i = 1; i < molList.length; i++) {
			this.combine(molList[i], cm);

			cm.name = cm.name.concat("-" + molList[i].getType().toString() + String.valueOf(molList[i].index));
			// System.out.println(molList[i].atomList.size());
			// System.out.println(cm.atomList.size());

		}
		// for (Atom a : cm.atomList) {
		// System.out.println(a.element + "," + a.innerX + "," + a.innerY + "," +
		// a.innerZ);
		// }

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
		// System.out.println("*************mol");
		// for (Atom a : mol.atomList) {
		// System.out.println(a.element + "," + a.innerX + "," + a.innerY + "," +
		// a.innerZ);
		// }
		// System.out.println("**************cmol");
		// for (Atom a : cmol.atomList) {
		// System.out.println(a.element + "," + a.innerX + "," + a.innerY + "," +
		// a.innerZ);
		// }
		// double[] axis;
		// axis = aligment(cmol.terminalAtom, mol.getLeft());
		//
		// for (Atom a : mol.atomList) {
		// a.transCoordinate(axis);
		// cmol.atomList.add(a);
		// }
		// cmol.terminalAtom = mol.getRight();
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
		// System.out.println(m);
		double[] axis;
		axis = aligment(cmol.terminalAtom, mol.getNearestAtom(mol.getLeft()));
		// mol.atomList.remove(n);
		cmol.atomList.remove(m);

		for (Atom a : mol.atomList) {
			a.transCoordinate(axis);

		}
		// boolean isXRotateNeeded = false;
		// for (Atom a : mol.atomList) {
		// if (isXRotateNeeded) {
		// break;
		// }
		// for (Atom b : cmol.atomList) {
		// if (a.calDistance(b) < 2.25) {
		// isXRotateNeeded = true;
		// break;
		// }
		// }
		// }
		// if (isXRotateNeeded) {
		// System.out.println("Xrotate");
		// for (Atom a : mol.atomList) {
		// a.clockWiseRotateX(90);
		// }
		// }
		for (Atom a : mol.atomList) {
			if (mol.atomList.indexOf(a) != n)
				cmol.atomList.add(a);
		}
		
		if (mol.getRight() != null && rIndex != -1) {
			mol.rightAtom = mol.atomList.get(rIndex);
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
		// double bs = axis[0] * axis[0] + axis[1] * axis[1] + axis[2] * axis[2];
		// System.out.println(bs);
		// for (int i = 0; i < 3; i++) {
		// axis[i] *= 1.03;
		// }
		return axis;
	}
}
