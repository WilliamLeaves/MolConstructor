package SV;

import java.util.ArrayList;

import DB.DBLink;
import SV_support.CmbRule;
import SV_support.MolType;
import SV_support.Molecule;

public class MoleculeDepot {
	Builder builder;
	ArrayList<Molecule> molList;
	ArrayList<CmbRule> ruleList;
	ArrayList<String> cmbMolNameList;

	public DBLink db_link;

	/***
	 * load the info of molecules and combination molecules and combination rules
	 * from the dataBase
	 */
	public void loadList() {

	}

	public boolean cmbRuleCreate(String[] str) {
		CmbRule rule = new CmbRule(str);
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
		}
		// automatically build combination molecules according with the rule
		{

		}
		// database changes;
		{

		}
		return rule.isPermit;
	}

	
}
