package SV_support;

public class EndCapping extends Molecule {
	public Atom rightAtom = leftAtom;

	public EndCapping() {
		super();
		this.rightAtom = this.leftAtom;
	}

	public boolean isSymmetry = false;

	public MolType getType() {
		return MolType.C;
	}

	public double[] getTerminalAtomAxis() {
		double[] axis = { this.leftAtom.innerX, this.leftAtom.innerY, this.leftAtom.innerZ };
		return axis;
	}

	@Override
	public Atom getLeft() {
		// TODO Auto-generated method stub
<<<<<<< HEAD
		EndCapping m = new EndCapping();
		m.image2DAddress = this.image2DAddress;
		m.index = this.index;
		m.isSymmetry = this.isSymmetry;
		for (Atom a : this.atomList) {
			m.atomList.add((Atom) a.clone());
			if (a.equal(this.leftAtom)) {
				m.leftAtom = a;
				m.rightAtom = a;
			}
		}
		return m;
	}

	public Molecule getMirrorClone() {
		// TODO Auto-generated method stub
		EndCapping m = new EndCapping();
		m.image2DAddress = this.image2DAddress;
		m.index = this.index;
		m.isSymmetry = this.isSymmetry;
		for (Atom a : this.atomList) {
			Atom atom = (Atom) a.clone();
			if (a.equal(this.leftAtom)) {
				m.leftAtom = atom;
				m.rightAtom = atom;
			}
			atom.mirror();
			m.atomList.add(atom);

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
	public EndCapping getClone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (EndCapping) this.clone();
>>>>>>> parent of 130e820... 20180331-1
	}

	public Atom getRight() {
		return this.rightAtom;
	}

}
