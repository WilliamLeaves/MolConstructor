package SV_support;

import java.util.ArrayList;

public abstract class Molecule {
	public String name;
	public String image2DAddress;
	public ArrayList<Atom> atomList;
	public boolean isSymmetry;

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
