package SV_support;

public class EndCapping extends Molecule {
	public Atom terminalAtom;
	public boolean isSymmetry = false;

	public MolType getType() {
		return MolType.C;
	}

	public double[] getTerminalAtomAxis() {
		double[] axis = { this.terminalAtom.innerX, this.terminalAtom.innerY, this.terminalAtom.innerZ };
		return axis;
	}

	@Override
	public Atom getLeft() {
		// TODO Auto-generated method stub
		return this.terminalAtom;
	}

	@Override
	public Atom getRight() {
		// TODO Auto-generated method stub
		return this.terminalAtom;
	}

}
