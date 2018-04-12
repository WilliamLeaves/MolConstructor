package SV_support;

public class PiSpacer extends Molecule {

	public MolType getType() {
		return MolType.S;
	}

	public double[] getLeftAtomAxis() {
		double[] axis = { this.leftAtom.innerX, this.leftAtom.innerY, this.leftAtom.innerZ };
		return axis;
	}

	public double[] getRightAtomAxis() {
		double[] axis = { this.rightAtom.innerX, this.rightAtom.innerY, this.rightAtom.innerZ };
		return axis;
	}

	@Override
	public Atom getLeft() {
		// TODO Auto-generated method stub
<<<<<<< HEAD
		PiSpacer m = new PiSpacer();
		m.image2DAddress = this.image2DAddress;
		m.index = this.index;
		m.isSymmetry = this.isSymmetry;
		for (Atom a : this.atomList) {
			m.atomList.add((Atom) a.clone());
			if (a.equal(this.leftAtom)) {
				m.leftAtom = a;
			}
			if (a.equal(this.rightAtom)) {
				m.rightAtom = a;
			}
		}
		return m;
=======
		return this.leftAtom;
	}

	@Override
	public Atom getRight() {
		// TODO Auto-generated method stub
		return this.rightAtom;
	}

	@Override
	public PiSpacer getClone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (PiSpacer) this.clone();
>>>>>>> parent of 130e820... 20180331-1
	}

}
