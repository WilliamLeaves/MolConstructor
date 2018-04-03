package test;

public class test {
	public double innerX, innerY;

	public static void main(String args[]) {
		test t = new test();
		t.innerX = 0;
		t.innerY = 1;
		for (int i = 0; i < 360; i++) {
			t.clockWiseRotateZ(1);
			System.out.println(i + ":" + t.innerX + "," + t.innerY);
		}
	}

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
}
