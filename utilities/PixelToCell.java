import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class PixelToCell {

	public static void main(String[] args) {
		File f = new File("D:\\Documents\\GitHub\\gwagigle\\coordinates.txt");
		File output = new File("D:\\Documents\\GitHub\\gwagigle\\output.txt");
		try {
			output.createNewFile();
		} catch (IOException e1) {
			System.exit(0);
		}
		try (BufferedReader br = new BufferedReader(new FileReader(f)); PrintWriter pw = new PrintWriter(output)) {
			while (br.ready()) {
				String s = br.readLine();
				String[] t = s.split("\\s");
				if (t.length <= 1)
					continue;

				String out = "";

				out += t[0] + "\t";
				out += numToText((Integer.parseInt(t[1]) / 3) + 1) + " " + (Integer.parseInt(t[2]) / 2 + 3);

				pw.println(out);
				pw.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String numToText(int n) {
		String res = "";
		res += (char) (n % 26 + 'A');
		n /= 26;
		while (n > 0) {
			res = (char) (n % 26 + 'A' - 1) + res;
			n /= 26;
		}

		return res;
	}

}
