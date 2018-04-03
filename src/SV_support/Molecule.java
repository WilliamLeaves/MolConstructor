package SV_support;

import java.util.ArrayList;

public abstract class Molecule implements Cloneable {
	public int index = 0;
	public String name = "Unnamed";
	public String image2DAddress = "";
	public ArrayList<Atom> atomList = new ArrayList<Atom>();
	public boolean isSymmetry = true;
	public Atom leftAtom;
	public Atom rightAtom;

	/**
	 * return the type of molecule:edonor gets D1,e_accepter gets A1,end_capping
	 * gets C1 and piSpacer gets S1
	 * 
	 * @return
	 */
	public abstract MolType getType();

	public Atom getLeft() {
		return this.leftAtom;
	}

	public Atom getRight() {
		return this.rightAtom;
	}

	public abstract Molecule getClone();

	/**
	 * make the terminal atom have max x-axis
	 */
	public void rotateToFit() {
		double N = 360;
		Atom t = this.leftAtom;
		Atom tn = this.getNearestAtom(t);

		double des = Double.MAX_VALUE;
		int nz = 0;
		for (int i = 0; i < N; i++) {
			t.clockWiseRotateZ(1 / N);
			tn.clockWiseRotateZ(1 / N);
			if (Math.abs(this.getAtomVector(t, tn)[1]) < des) {
				des = Math.abs(this.getAtomVector(t, tn)[1]);
				nz = i;
			}
		}
		des = Double.MAX_VALUE;
		int ny = 0;
		for (int i = 0; i < N; i++) {
			t.clockWiseRotateY(1 / N);
			tn.clockWiseRotateY(1 / N);
			if (Math.abs(this.getAtomVector(t, tn)[2]) < des) {
				des = Math.abs(this.getAtomVector(t, tn)[2]);
				ny = i;
			}
		}
		for (Atom a : this.atomList) {
			if (!a.equal(tn)) {
				a.clockWiseRotateZ(nz/N);
				a.clockWiseRotateY(ny/N);
			}
		}
		System.out.println(
				this.getAtomVector(t, tn)[0] + "," + this.getAtomVector(t, tn)[1] + "," + this.getAtomVector(t, tn)[2]);
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

	public double[] getAtomVector(Atom a, Atom b) {
		return new double[] { a.innerX - b.innerX, a.innerY - b.innerY, a.innerZ - b.innerZ };
	}
}
