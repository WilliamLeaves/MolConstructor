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
		double[] ali = { this.getLeft().innerX, this.getLeft().innerY, this.getLeft().innerZ };
		for (Atom a : this.atomList) {
			a.innerX -= ali[0];
			a.innerY -= ali[1];
			a.innerZ -= ali[2];
		}
		// choose left and its nearest atom
		double N = 360;
		Atom t = (Atom) this.leftAtom.clone();
		Atom tn = (Atom) this.getNearestAtom(t).clone();

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
			// System.out.println(i + ":" + String.valueOf(this.getAtomVector(t, tn)[1]));
		}
		for (Atom a : this.atomList) {
			a.clockWiseRotateZ(360 * (nz + 1) / N);
		}
		// System.out.println("after rotate Z des Y " + nz + ":"
		// + String.valueOf(this.getAtomVector(this.getLeft(),
		// this.getNearestAtom(this.getLeft()))[1]));
		// System.out.println("after rotate Z des Z " + nz + ":"
		// + String.valueOf(this.getAtomVector(this.getLeft(),
		// this.getNearestAtom(this.getLeft()))[2]));
		// rotate along Y and get the degree

		t = (Atom) this.leftAtom.clone();
		tn = (Atom) this.getNearestAtom(this.getLeft()).clone();
		des = Double.MAX_VALUE;
		int ny = 0;
		for (int i = 0; i < N; i++) {
			t.clockWiseRotateY(360 / N);
			tn.clockWiseRotateY(360 / N);
			if (Math.abs(this.getAtomVector(t, tn)[2]) < des && this.getAtomVector(t, tn)[0] < 0) {
				des = Math.abs(this.getAtomVector(t, tn)[2]);
				ny = i;
			}
			// System.out.println(i + ":" + String.valueOf(this.getAtomVector(t, tn)[2]));
		}

		for (Atom a : this.atomList) {
			a.clockWiseRotateY(360 * (ny + 1) / N);
		}
		// System.out.println("after rotate Y des Y " + ny + ":"
		// + String.valueOf(this.getAtomVector(this.getLeft(),
		// this.getNearestAtom(this.getLeft()))[1]));
		// System.out.println("after rotate Y des Z " + ny + ":"
		// + String.valueOf(this.getAtomVector(this.getLeft(),
		// this.getNearestAtom(this.getLeft()))[2]));
		// double[] aliLeft = { this.getLeft().innerX, this.getLeft().innerY,
		// this.getLeft().innerZ };
		// double[] aliN = { this.getNearestAtom(leftAtom).innerX,
		// this.getNearestAtom(leftAtom).innerY,
		// this.getNearestAtom(leftAtom).innerZ };
		// System.out.println("desX:" + String.valueOf(aliLeft[0] - aliN[0]));
		// System.out.println("desY:" + String.valueOf(aliLeft[1] - aliN[1]));
		// System.out.println("desZ:" + String.valueOf(aliLeft[2] - aliN[2]));

		// choose the second nearest atom from the left atom ,make the vector in X-Y
		// plane ,so that the main plane of the molecule can be parallel with the X-Y
		// plane
		int[] disList = new int[this.atomList.size()];
		for (int i = 0; i < disList.length; i++) {
			disList[i] = i;
		}
		for (int i = 0; i < disList.length; i++) {
			for (int j = i + 1; j < disList.length; j++) {
				if (this.atomList.get(disList[i]).calDistance(this.leftAtom) > this.atomList.get(disList[j])
						.calDistance(this.leftAtom)) {
					int temp = disList[i];
					disList[i] = disList[j];
					disList[j] = temp;
				}
			}
		}

		t = (Atom) this.getLeft().clone();
		tn = (Atom) this.atomList.get(disList[2]).clone();
		des = Double.MAX_VALUE;
		int nx = 0;
		for (int i = 0; i < N; i++) {
			t.clockWiseRotateX(360 / N);
			tn.clockWiseRotateX(360 / N);
			if (Math.abs(this.getAtomVector(t, tn)[2]) < des && this.getAtomVector(t, tn)[0] < 0) {
				des = Math.abs(this.getAtomVector(t, tn)[2]);
				nx = i;
			}
			// System.out.println(i + ":" + this.getAtomVector(t, tn)[2]);
		}
		for (Atom a : this.atomList) {
			a.clockWiseRotateX(360 * (nx + 1) / N);
		}
		// System.out.println("after rotate X des Y " + nx + ":"
		// + String.valueOf(this.getAtomVector(this.getLeft(),
		// this.atomList.get(disList[2]))[1]));
		// System.out.println("after rotate X des Z " + nx + ":"
		// + String.valueOf(this.getAtomVector(this.getLeft(),
		// this.atomList.get(disList[2]))[2]));
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

}
