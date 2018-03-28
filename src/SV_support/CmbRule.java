package SV_support;

import java.util.ArrayList;

public class CmbRule {
	public boolean isPermit;
	public ArrayList<MolType> typeList;

	public CmbRule(String[] str) {
		this.isPermit = true;
		for (int i = 0; i < str.length; i++) {
			if (str[i].equals("A")) {
				this.typeList.add(MolType.A);
			} else if (str[i].equals("S")) {
				this.typeList.add(MolType.S);
			} else if (str[i].equals("D")) {
				this.typeList.add(MolType.D);
			} else if (str[i].equals("C")) {
				this.typeList.add(MolType.C);
			} else {
				this.isPermit = false;
				return;
			}
			if (i == 0 || i == str.length - 1) {
				if (str[i].equals("C"))
					this.isPermit = false;
			}
		}
	}
}
