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
			if (a.equal(mol.getRight())) {
				this.terminalAtom = a;
				//System.out.println("right");
			}
		}
		this.rotateToFit();
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

	public Atom getNearestAtom(Atom atom) {
		Atom am = atom;
		double ds = Double.MAX_VALUE;
		for (Atom a : this.atomList) {
			if (!a.equal(atom) && !a.element.equals("H")) {
				if (atom.calDistance(a) < ds) {
					am = a;
					ds = atom.calDistance(a);
				}
			}
		}
		return am;
	}

	public double[] getcenter() {
		double[] axis = { 0, 0, 0 };
		for (Atom a : this.atomList) {
			axis[0] += a.innerX;
			axis[1] += a.innerY;
			axis[2] += a.innerZ;
		}
		axis[0] /= this.atomList.size();
		axis[1] /= this.atomList.size();
		axis[2] /= this.atomList.size();
		return axis;
	}

	/**
	 * make the terminal atom have the max x-axis
	 * 
	 * @return
	 */
	public void rotateToFit() {
		double[] ali = { this.terminalAtom.innerX, this.terminalAtom.innerY, this.terminalAtom.innerZ };
		for (Atom a : this.atomList) {
			a.innerX -= ali[0];
			a.innerY -= ali[1];
			a.innerZ -= ali[2];
		}
		// choose left and its nearest atom
		double N = 360;
		Atom t = (Atom) this.terminalAtom.clone();
		Atom tn = (Atom) this.getNearestAtom(t).clone();

		// rotate along Z and get the degree
		double des = Double.MAX_VALUE;
		int nz = 0;
		for (int i = 0; i < N; i++) {
			t.clockWiseRotateZ(360 / N);
			tn.clockWiseRotateZ(360 / N);
			if (Math.abs(this.getAtomVector(t, tn)[1]) < des && this.getAtomVector(t, tn)[0] > 0) {
				des = Math.abs(this.getAtomVector(t, tn)[1]);
				nz = i;
			}
		}
		for (Atom a : this.atomList) {
			a.clockWiseRotateZ(360 * (nz + 1) / N);
		}

		t = (Atom) this.terminalAtom.clone();
		tn = (Atom) this.getNearestAtom(t).clone();
		des = Double.MAX_VALUE;
		int ny = 0;
		for (int i = 0; i < N; i++) {
			t.clockWiseRotateY(360 / N);
			tn.clockWiseRotateY(360 / N);
			if (Math.abs(this.getAtomVector(t, tn)[2]) < des && this.getAtomVector(t, tn)[0] > 0) {
				des = Math.abs(this.getAtomVector(t, tn)[2]);
				ny = i;
			}
		}

		for (Atom a : this.atomList) {
			a.clockWiseRotateY(360 * (ny + 1) / N);
		}

		int[] disList = new int[this.atomList.size()];
		for (int i = 0; i < disList.length; i++) {
			disList[i] = i;
		}
		for (int i = 0; i < disList.length; i++) {
			for (int j = i + 1; j < disList.length; j++) {
				if (this.atomList.get(disList[i]).calDistance(this.terminalAtom) > this.atomList.get(disList[j])
						.calDistance(this.terminalAtom)) {
					int temp = disList[i];
					disList[i] = disList[j];
					disList[j] = temp;
				}
			}
		}
		t = (Atom) this.terminalAtom.clone();
		tn = (Atom) this.atomList.get(disList[2]).clone();
		des = Double.MAX_VALUE;
		int nx = 0;
		for (int i = 0; i < N; i++) {
			t.clockWiseRotateX(360 / N);
			tn.clockWiseRotateX(360 / N);
			if (Math.abs(this.getAtomVector(t, tn)[2]) < des && this.getAtomVector(t, tn)[0] > 0) {
				des = Math.abs(this.getAtomVector(t, tn)[2]);
				nx = i;
			}
		}
		for (Atom a : this.atomList) {
			a.clockWiseRotateX(360 * (nx + 1) / N);
		}
	}

	public double[] getAtomVector(Atom a, Atom b) {
		return new double[] { a.innerX - b.innerX, a.innerY - b.innerY, a.innerZ - b.innerZ };
	}
}
