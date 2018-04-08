package SV_support;

public class Atom implements Cloneable {
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

	public Object clone() {
		Atom a = null;
		try {
			a = (Atom) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return a;
	}

	public double calDistance(Atom atom) {
		return Math.pow(this.innerX - atom.innerX, 2) + Math.pow(this.innerY - atom.innerY, 2)
				+ Math.pow(this.innerZ - atom.innerZ, 2);
	}

	public void mirror() {
		this.clockWiseRotateY(180);
		this.clockWiseRotateZ(180);
	}

	// rotate the Atom by theta degree along the Z-axis
	public void clockWiseRotateZ(double theta) {
		if (theta < -360 || theta > 360) {
			return;
		} else {
			double p = (theta / 360) * 2 * Math.PI;
			double x = this.innerX * Math.cos(p) + this.innerY * Math.sin(p);
			double y = this.innerY * Math.cos(p) - this.innerX * Math.sin(p);
			this.innerX = x;
			this.innerY = y;
		}
	}

	// rotate the Atom by theta degree along the Y-axis
	public void clockWiseRotateY(double theta) {
		if (theta < -360 || theta > 360) {
			return;
		} else {
			double p = (theta / 360) * 2 * Math.PI;
			double x = this.innerX * Math.cos(p) + this.innerZ * Math.sin(p);
			double z = this.innerZ * Math.cos(p) - this.innerX * Math.sin(p);
			this.innerX = x;
			this.innerZ = z;
		}
	}

	// rotate the Atom by theta degree along the X-axis
	public void clockWiseRotateX(double theta) {
		if (theta < -360 || theta > 360) {
			return;
		} else {
			double p = (theta / 360) * 2 * Math.PI;
			double y = this.innerY * Math.cos(p) + this.innerZ * Math.sin(p);
			double z = this.innerZ * Math.cos(p) - this.innerY * Math.sin(p);
			this.innerY = y;
			this.innerZ = z;
		}
	}
}
