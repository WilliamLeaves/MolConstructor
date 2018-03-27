package SV_support;

public class EndCapping extends Molecule {
	public Atom terminalAtom;

	public String getType() {
		return this.getClass().getName();
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
