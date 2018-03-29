package SV_support;

import java.util.ArrayList;

public abstract class Molecule {
	public String name = "Unnamed";
	public String image2DAddress = "";
	public ArrayList<Atom> atomList = new ArrayList<Atom>();
	public boolean isSymmetry = true;
	public Atom leftAtom;
	public Atom rightAtom;

	/**
	 * return the type of molecule:edonor gets D1,e_accepter gets A1,end_capping
	 * gets C1 and piSpacer gets S1
	 * 
	 * @return
	 */
	public abstract MolType getType();

	public abstract Atom getLeft();

	public abstract Atom getRight();
}
