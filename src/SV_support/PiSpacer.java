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
		return this.leftAtom;
	}

	@Override
	public Atom getRight() {
		// TODO Auto-generated method stub
		return this.rightAtom;
	}
}
