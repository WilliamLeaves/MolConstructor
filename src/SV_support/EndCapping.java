package SV_support;

public class EndCapping extends Molecule {
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
		}
		m.leftAtom = (Atom) this.leftAtom.clone();
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
			atom.mirror();
			m.atomList.add(atom);
		}
		Atom atom = (Atom) this.leftAtom.clone();
		atom.mirror();
		m.leftAtom = atom;
		return m;
	}

}
