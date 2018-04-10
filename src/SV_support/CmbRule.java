package SV_support;

import java.util.ArrayList;

public class CmbRule implements Cloneable {
	public boolean isPermit;
	public boolean mustReduplicate;
	public ArrayList<MolType> typeList = new ArrayList<MolType>();

	public CmbRule(String[] str, boolean mustReduplicate) {
		this.mustReduplicate = mustReduplicate;
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
			// if (i == 0 || i == str.length - 1) {
			// if (!str[i].equals("C"))
			// this.isPermit = false;
			// }
		}
	}

	/**
	 * print itself
	 * 
	 * @return
	 */
	public String printRule() {
		String str = "";
		for (int i = 0; i < this.typeList.size(); i++) {
			String type = this.typeList.get(i).toString();
			str = str.concat(type);
			if (i != this.typeList.size() - 1) {
				str = str.concat("-");
			}
		}
		return str;
	}
}
