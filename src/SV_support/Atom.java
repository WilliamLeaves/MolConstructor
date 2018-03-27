package SV_support;

public class Atom {
	public Element element;
	public double innerX;
	public double innerY;
	public double innerZ;

	public Atom(Element e, double x, double y, double z) {
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
}
