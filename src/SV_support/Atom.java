package SV_support;

public class Atom {
	public String element;
	public double innerX;
	public double innerY;
	public double innerZ;

	public Atom(String e, double x, double y, double z) {
		this.innerX = x;
		this.innerY = y;
		this.innerZ = z;
		this.element = e;
	}

	public void transCoordinate(double[] axisList) {
		if (axisList.length != 3) {
			return;
		} else {
			this.innerX += axisList[0];
			this.innerY += axisList[1];
			this.innerZ += axisList[2];
		}
	}

	public boolean equal(Atom a) {
		if (a.innerX == this.innerX && a.innerY == this.innerY && a.innerZ == this.innerZ) {
			return true;
		}
		return false;
	}
}
