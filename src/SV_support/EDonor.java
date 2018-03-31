package SV_support;

public class EDonor extends Molecule {

	public MolType getType() {
		return MolType.D;
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
	public EDonor getClone() {
		// TODO Auto-generated method stub
		EDonor m = new EDonor();
		m.image2DAddress = this.image2DAddress;
		m.index = this.index;
		m.isSymmetry = this.isSymmetry;
		for (Atom a : this.atomList) {
			m.atomList.add((Atom) a.clone());
		}
		m.leftAtom = (Atom) this.leftAtom.clone();
		m.rightAtom = (Atom) this.rightAtom.clone();
		return m;
	}
}
