package SV;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class main {
	public static void main(String[] args) {
		main m = new main();
		try {
			m.loadMolecules();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
					this.loadMolFromGJF(f);
					j++;
				} else {
					break;
				}
			}
		}

	}

	public void loadMolFromGJF(File file) throws IOException {
		String str = "";
		FileInputStream out = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(out);
		int ch = 0;
		while ((ch = isr.read()) != -1) {
			if ((char) ch != '\r')
				str = str.concat(String.valueOf((char) ch));
		}
		isr.close();
		//System.out.println(str);
	}
}
