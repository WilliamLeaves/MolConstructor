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
	public Atom getLeft() {
		// TODO Auto-generated method stub
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
	}

}
