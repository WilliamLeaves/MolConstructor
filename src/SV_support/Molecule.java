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
		// save the info of the left and right
		int l = 0, r = 0;
		for (Atom a : this.atomList) {
			if (a.equal(this.leftAtom)) {
				l = this.atomList.indexOf(a);
			}
			if (this.rightAtom != null && a.equal(rightAtom)) {
				r = this.atomList.indexOf(a);
			}
		}
		// choose left and its nearest atom
		double N = 360;
		Atom t = this.atomList.get(l);
		Atom tn = this.getNearestAtom(t);

		// rotate along Z and get the degree
		double des = Double.MAX_VALUE;
		int nz = 0;
		for (int i = 0; i < N; i++) {
			t.clockWiseRotateZ(360 / N);
			tn.clockWiseRotateZ(360 / N);
			if (Math.abs(this.getAtomVector(t, tn)[1]) < des && this.getAtomVector(t, tn)[0] < 0) {
				des = Math.abs(this.getAtomVector(t, tn)[1]);
				nz = i;
			}
			// System.out.println("Zrotate" + i + ":" + this.getAtomVector(t, tn)[0] + "," +
			// this.getAtomVector(t, tn)[1]
			// + "," + this.getAtomVector(t, tn)[2]);
		}

		for (Atom a : this.atomList) {
			a.clockWiseRotateZ(360 * (nz + 1) / N);
		}
		this.leftAtom = (Atom) this.atomList.get(l).clone();
		// rotate along Y and get the degree
		des = Double.MAX_VALUE;
		int ny = 0;
		for (int i = 0; i < N; i++) {
			t.clockWiseRotateY(360 / N);
			tn.clockWiseRotateY(360 / N);
			if (Math.abs(this.getAtomVector(t, tn)[2]) < des && this.getAtomVector(t, tn)[0] < 0) {
				des = Math.abs(this.getAtomVector(t, tn)[2]);
				ny = i;
			}
			// System.out.println("Yrotate" + i + ":" + this.getAtomVector(t, tn)[0] + "," +
			// this.getAtomVector(t, tn)[1]
			// + "," + this.getAtomVector(t, tn)[2]);
		}

		for (Atom a : this.atomList) {
			a.clockWiseRotateY(360 * (ny + 1) / N);

			// System.out.println("Yrotate" + i + ":" + this.getAtomVector(t, tn)[0] + "," +
			// this.getAtomVector(t, tn)[1]
			// + "," + this.getAtomVector(t, tn)[2]);
		}

		// boolean isXRotateNeeded = false;
		// for (Atom a : this.atomList) {
		// if (a.innerX < this.getLeft().innerX) {
		// isXRotateNeeded = true;
		// break;
		// }
		// }
		// if (isXRotateNeeded) {
		// for (Atom a : this.atomList) {
		// a.clockWiseRotateX(90);
		// }
		// System.out.println(this.name + "_Xrotate");
		// }
		// set the left and right
		this.leftAtom = (Atom) this.atomList.get(l).clone();
		if (this.rightAtom != null) {
			this.rightAtom = (Atom) this.atomList.get(r).clone();
		}

		// check
		// t = this.leftAtom;
		// tn = this.getNearestAtom(t);
		// if (Math.abs(this.getAtomVector(t, tn)[1]) < 0.01 &&
		// Math.abs(this.getAtomVector(t, tn)[2]) < 0.01) {
		// System.out.println("fine");
		// }else {
		// System.out.println("error");
		// }

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
