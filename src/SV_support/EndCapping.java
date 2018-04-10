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
	public EndCapping getClone() {
		// TODO Auto-generated method stub
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
	}

	public Atom getRight() {
		return this.rightAtom;
	}

}
