package SV;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import SV_support.Atom;
import SV_support.CmbMolecule;
import SV_support.CmbRule;
import SV_support.EAccepter;
import SV_support.EDonor;
import SV_support.EndCapping;
import SV_support.Molecule;
import SV_support.PiSpacer;

public class main {
	public static void main(String[] args) {
		main m = new main();
		try {
			m.loadMolecules();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void loadMolecules() throws IOException {
		String rootFile = "gjf_input";
		String[] foldName = { "e-donor", "e-acceptor", "pi-spacer", "end-capping" };
		String[] typeName = { "D", "A", "S", "C" };
		for (int i = 0; i < 4; i++) {
			int j = 1;
			while (true) {
				String path = rootFile + "/" + foldName[i] + "/" + typeName[i] + String.valueOf(j) + ".gjf";
				File f = new File(path);
				if (f.exists()) {
					System.out.println(path);
					this.loadMolFromGJF(f, typeName[i], i);
					j++;
				} else {
					break;
				}
			}
		}

	}

	public void loadMolFromGJF(File file, String type, int index) throws IOException {
		String str = "";
		FileInputStream out = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(out);
		int ch = 0;
		while ((ch = isr.read()) != -1) {
			if ((char) ch != '\r')
				str = str.concat(String.valueOf((char) ch));
		}
		isr.close();
		// decide molecule type
		Molecule m = null;
		switch (type) {
		case "A":
			m = new EAccepter();
			break;
		case "D":
			m = new EDonor();
			break;
		case "C":
			m = new EndCapping();
			break;
		case "S":
			m = new PiSpacer();
			break;
		}
		// matching name
		String temp = "<name>(\\S+)<\\\\name>";
		Pattern p = Pattern.compile(temp);
		Matcher matcher = p.matcher(str);
		while (matcher.find()) {
			m.name = matcher.group(1);
		}

		// matching symmetry character
		temp = "<s>(\\w+)<\\\\s>";
		p = Pattern.compile(temp);
		matcher = p.matcher(str);
		while (matcher.find()) {
			String isSyemmtry = matcher.group(1);
			if (isSyemmtry.equals("symmetry")) {
				m.isSymmetry = true;
			} else if (isSyemmtry.equals("asymmetry")) {
				m.isSymmetry = false;
			} else {
				// output error
			}
		}
		// matching atoms
		temp = "([a-z]|[A-Z])\\s+(-?\\d+(\\.\\d+)?)\\s+(-?\\d+(\\.\\d+)?)\\s+(-?\\d+(\\.\\d+)?)";
		p = Pattern.compile(temp);
		matcher = p.matcher(str);
		while (matcher.find()) {
			String e = matcher.group(1);
			double x = Double.parseDouble(matcher.group(2));
			double y = Double.parseDouble(matcher.group(3));
			double z = Double.parseDouble(matcher.group(4));
			Atom a = new Atom(e, x, y, z);
			m.atomList.add(a);
		}
		// matching left atom
		temp = "<l>(-?\\d+(\\.\\d+)?),(-?\\d+(\\.\\d+)?),(-?\\d+(\\.\\d+)?)<\\\\l>";
		p = Pattern.compile(temp);
		matcher = p.matcher(str);
		while (matcher.find()) {
			double x = Double.parseDouble(matcher.group(1));
			double y = Double.parseDouble(matcher.group(2));
			double z = Double.parseDouble(matcher.group(3));
			Atom atom = new Atom("H", x, y, z);
			for (Atom a : m.atomList) {
				if (a.equal(atom)) {
					m.leftAtom = a;
					break;
				}
			}
		}
		// matching right atom if exists
		temp = "<r>(-?\\d+(\\.\\d+)?),(-?\\d+(\\.\\d+)?),(-?\\d+(\\.\\d+)?)<\\\\r>";
		p = Pattern.compile(temp);
		matcher = p.matcher(str);
		while (matcher.find()) {
			double x = Double.parseDouble(matcher.group(1));
			double y = Double.parseDouble(matcher.group(2));
			double z = Double.parseDouble(matcher.group(3));
			Atom atom = new Atom("H", x, y, z);
			for (Atom a : m.atomList) {
				if (a.equal(atom)) {
					m.rightAtom = a;
					break;
				}
			}
		}
		this.export(m);
	}

	public void export(CmbMolecule cm) {
		String path = "output";
		File f = new File(path + cm.name + ".gjf");
	}

	public void export(Molecule cm) {
		String path = "output/";
		File f = new File(path + cm.name + ".gjf");
	}
}
