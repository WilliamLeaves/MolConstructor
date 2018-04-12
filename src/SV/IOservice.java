package SV;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import SV_support.Atom;
import SV_support.CmbMolecule;
import SV_support.EAccepter;
import SV_support.EDonor;
import SV_support.EndCapping;
import SV_support.Molecule;
import SV_support.PiSpacer;

public class IOservice {
	public ArrayList<Molecule> loadMolecules() throws IOException {

		ArrayList<Molecule> molList = new ArrayList<Molecule>();

//		String rootFile = "gjf_input";
//		String[] foldName = { "e-donor", "e-acceptor", "pi-spacer", "end-capping" };
//		String[] typeName = { "D", "A", "S", "C" };
//		for (int i = 0; i < 4; i++) {
//			int j = 1;
//			while (true) {
//				String path = rootFile + "/" + foldName[i] + "/" + typeName[i] + String.valueOf(j) + ".gjf";
//				File f = new File(path);
//				if (f.exists()) {
//					System.out.println(path);
//					molList.add(this.loadMolFromGJF(f, typeName[i], j));
//					j++;
//				} else {
//					break;
//				}
//			}
//		}
		String path = "gjf_input/e-donor/D1.gjf";
		this.loadMolFromGJF(new File(path), "D", 1);
		return molList;
	}

	public Molecule loadMolFromGJF(File file, String type, int index) throws IOException {
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
			double y = Double.parseDouble(matcher.group(4));
			double z = Double.parseDouble(matcher.group(6));
			// System.out.println("(" + e + "," + x + "," + y + "," + z + ")");
			Atom a = new Atom(e, x, y, z);
			m.atomList.add(a);
		}
		// matching left atom
		temp = "<l>(-?\\d+(\\.\\d+)?),(-?\\d+(\\.\\d+)?),(-?\\d+(\\.\\d+)?)<\\\\l>";
		p = Pattern.compile(temp);
		matcher = p.matcher(str);
		while (matcher.find()) {
			double x = Double.parseDouble(matcher.group(1));
			double y = Double.parseDouble(matcher.group(3));
			double z = Double.parseDouble(matcher.group(5));
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
			double y = Double.parseDouble(matcher.group(3));
			double z = Double.parseDouble(matcher.group(5));
			Atom atom = new Atom("H", x, y, z);
			for (Atom a : m.atomList) {
				if (a.equal(atom)) {
					m.rightAtom = a;
					break;
				}
			}
		}
		m.index = index;
		m.rotateToFit();
		this.export(m);
		return m;
	}

	public void export(CmbMolecule cm) {
		String path = "output/";
		try {
			// System.out.println(cm.name);
			String n = cm.name;
			n = n.replace(":", "~");
			FileWriter fw = new FileWriter(path + n + ".gjf", true);
			BufferedWriter bw = new BufferedWriter(fw);
			// change to output format
			{
				bw.write("%chk=C:\\Users\\William\\Documents\\chem3DFile\\e-donor\\D1.chk\n");
				bw.write("# opt hf/6-311g\n\n");
				bw.write("Title Card Required\n");
				bw.write("<name>" + cm.name + "<\\name>\n");
				bw.write("<cr>" + cm.rule.printRule() + "<\\cr>\n\n");
				bw.write("0 1\n");
				for (Atom a : cm.atomList) {
					bw.write(" " + a.element + "\t" + a.innerX + "\t" + a.innerY + "\t" + a.innerZ + "\n");
				}
				// System.out.println(cm.name + " complete");
			}
			bw.close();
			fw.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void export(Molecule cm) {
		String path = "output/";
		try {
			// System.out.println(cm.name);
			String n = cm.name;
			n = n.replace(":", "~");
			FileWriter fw = new FileWriter(path + n + ".gjf", true);
			BufferedWriter bw = new BufferedWriter(fw);

			// change to output format
			{
				bw.write("%chk=C:\\Users\\William\\Documents\\chem3DFile\\e-donor\\D1.chk\n");
				bw.write("# opt hf/6-311g\n\n");
				bw.write("Title Card Required\n");
				bw.write("<name>" + cm.name + "<\\name>\n\n");
				// bw.write("<cr>" + cm.rule.printRule() + "<\\cr>\r\n\n");
				bw.write("0 1\n");
				for (Atom a : cm.atomList) {
					bw.write(" " + a.element + "\t" + a.innerX + "\t" + a.innerY + "\t" + a.innerZ + "\n");
				}
				// System.out.println(cm.name + " complete");
			}
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
