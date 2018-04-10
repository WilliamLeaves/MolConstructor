package SV;

import java.util.ArrayList;

import DB.DBLink;
import SV_support.CmbMolecule;
import SV_support.CmbRule;
import SV_support.EAccepter;
import SV_support.EDonor;
import SV_support.EndCapping;
import SV_support.MolType;
import SV_support.Molecule;
import SV_support.PiSpacer;

public class MoleculeDepot {
	public Builder builder;
	public ArrayList<EAccepter> aList;
	public ArrayList<EDonor> dList;
	public ArrayList<PiSpacer> sList;
	public ArrayList<EndCapping> cList;

	public ArrayList<CmbRule> ruleList;
	public ArrayList<String> cmbMolNameList;

	public DBLink db_link;

	public MoleculeDepot(ArrayList<Molecule> molList) {
		this.db_link = new DBLink();
		this.aList = new ArrayList<EAccepter>();
		this.dList = new ArrayList<EDonor>();
		this.sList = new ArrayList<PiSpacer>();
		this.cList = new ArrayList<EndCapping>();
		this.loadList(molList);

		this.ruleList = new ArrayList<CmbRule>();
		this.cmbMolNameList = new ArrayList<String>();

		this.builder = new Builder();
	}

	/***
	 * load the info of molecules and combination molecules and combination rules
	 * from the dataBase
	 */
	private void loadList(ArrayList<Molecule> molList) {
		for (Molecule mol : molList) {
			if (mol.getType().equals(MolType.A)) {
				this.aList.add((EAccepter) mol);
			} else if (mol.getType().equals(MolType.D)) {
				this.dList.add((EDonor) mol);
			} else if (mol.getType().equals(MolType.S)) {
				this.sList.add((PiSpacer) mol);
			} else if (mol.getType().equals(MolType.C)) {
				this.cList.add((EndCapping) mol);
			}
		}
	}

	public boolean cmbRuleCreate(String[] str, boolean mustReduplicate)
			throws NumberFormatException, CloneNotSupportedException {
		CmbRule rule = new CmbRule(str, mustReduplicate);
		if (rule.isPermit) {
			for (CmbRule cr : this.ruleList) {
				if (cr.typeList.size() != rule.typeList.size()) {
					continue;
				} else {
					boolean b = true;
					for (int i = 0; i < cr.typeList.size(); i++) {
						if (!cr.typeList.get(i).equals(rule.typeList.get(i))) {
							b = false;
						}
					}
					if (b == true) {
						return false;
					}
				}
			}
			this.ruleList.add(rule);
			// automatically build combination molecules according with the rule
			ArrayList<String[]> strListArray = this.molListCreate(rule);
			for (int i = 0; i < strListArray.size(); i++) {
				String[] strList = strListArray.get(i);
				Molecule[] molList = new Molecule[strList.length];
				for (int j = 0; j < molList.length; j++) {
					Molecule mol = null;
					if (rule.typeList.get(j).equals(MolType.A)) {
						mol = this.aList.get(Integer.valueOf(strList[j])).getClone();
					} else if (rule.typeList.get(j).equals(MolType.D)) {
						mol = this.dList.get(Integer.valueOf(strList[j])).getClone();
					} else if (rule.typeList.get(j).equals(MolType.S)) {
						mol = this.sList.get(Integer.valueOf(strList[j])).getClone();
					} else if (rule.typeList.get(j).equals(MolType.C)) {
						if (j != molList.length - 1) {
							mol = this.cList.get(Integer.valueOf(strList[j])).getClone();
						} else {
							mol = this.cList.get(Integer.valueOf(strList[j])).getMirrorClone();
						}

					}
					molList[j] = mol;
				}
				CmbMolecule cm = this.builder.bulidCmbMol(rule, molList);
				// database changes;
				{
					this.db_link.getCmbMol(cm);
				}
			}
		}
		return rule.isPermit;
	}

	/**
	 * create lots of string lists(aim at molecule's index) by the rule
	 * 
	 * @param rule
	 * @return
	 */
	private ArrayList<String[]> molListCreate(CmbRule rule) {
		ArrayList<String[]> charList = new ArrayList<String[]>();
		if (rule.mustReduplicate) {
			int allSize = 1;
			int depth = rule.typeList.size();
			if (rule.typeList.contains(MolType.A)) {
				allSize *= this.getSize(MolType.A);
			}
			if (rule.typeList.contains(MolType.S)) {
				allSize *= this.getSize(MolType.S);
			}
			if (rule.typeList.contains(MolType.C)) {
				allSize *= this.getSize(MolType.C);
			}
			if (rule.typeList.contains(MolType.D)) {
				allSize *= this.getSize(MolType.D);
			}
			for (int i = 0; i < allSize; i++) {
				String[] str = new String[depth];
				charList.add(str);
			}
			int repeatTime = 1;
			if (rule.typeList.contains(MolType.A)) {
				for (int i = 0; i < charList.size(); i++) {
					for (int j = 0; j < depth; j++) {
						if (rule.typeList.get(j).equals(MolType.A)) {
							int ci = (i % (this.getSize(MolType.A) * repeatTime)) / repeatTime;
							String c = String.valueOf(ci);
							charList.get(i)[j] = c;
						}
					}
				}
				repeatTime *= this.getSize(MolType.A);
			}
			if (rule.typeList.contains(MolType.C)) {
				for (int i = 0; i < charList.size(); i++) {
					for (int j = 0; j < depth; j++) {
						if (rule.typeList.get(j).equals(MolType.C)) {
							int ci = (i % (this.getSize(MolType.C) * repeatTime)) / repeatTime;
							String c = String.valueOf(ci);
							charList.get(i)[j] = c;
						}
					}
				}
				repeatTime *= this.getSize(MolType.C);
			}
			if (rule.typeList.contains(MolType.S)) {
				for (int i = 0; i < charList.size(); i++) {
					for (int j = 0; j < depth; j++) {
						if (rule.typeList.get(j).equals(MolType.S)) {
							int ci = (i % (this.getSize(MolType.S) * repeatTime)) / repeatTime;
							String c = String.valueOf(ci);
							charList.get(i)[j] = c;
						}
					}
				}
				repeatTime *= this.getSize(MolType.S);
			}
			if (rule.typeList.contains(MolType.D)) {
				for (int i = 0; i < charList.size(); i++) {
					for (int j = 0; j < depth; j++) {
						if (rule.typeList.get(j).equals(MolType.D)) {
							int ci = (i % (this.getSize(MolType.D) * repeatTime)) / repeatTime;
							String c = String.valueOf(ci);
							charList.get(i)[j] = c;
						}
					}
				}
				repeatTime *= this.getSize(MolType.D);
			}
			System.out.println("");
		} else {
			int allSize = 1;
			int[] size = new int[rule.typeList.size()];
			int depth = rule.typeList.size();
			for (int i = 0; i < depth; i++) {
				size[i] = getSize(rule.typeList.get(i));
				allSize *= size[i];
			}
			for (int i = 0; i < allSize; i++) {
				String[] str = new String[depth];
				charList.add(str);
			}
			int repeatTime = 1;
			for (int i = 0; i < depth; i++) {
				for (int j = 0; j < charList.size(); j++) {
					int ci = (j % (repeatTime * size[depth - i - 1])) / repeatTime;
					String c = String.valueOf(ci);
					charList.get(j)[depth - i - 1] = c;
				}
				repeatTime *= size[depth - i - 1];
			}
		}
		return charList;
	}

	/**
	 * get molecule number from its type
	 * 
	 * @param type
	 * @return
	 */
	private int getSize(MolType type) {
		if (type.equals(MolType.A)) {
			return this.aList.size();
		} else if (type.equals(MolType.S)) {
			return this.sList.size();
		} else if (type.equals(MolType.C)) {
			return this.cList.size();
		} else if (type.equals(MolType.D)) {
			return this.dList.size();
		}
		return 0;
	}

}
