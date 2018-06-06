import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class CountDoor {

	public static void main(String[] args) {
		int[] count = new int[5];

		File f = new File("totalRoomToLibWhenIdle.txt");
		try (BufferedReader br = new BufferedReader(new FileReader(f))) {
			String s;
			while (br.ready()) {
				s = br.readLine();
				count[Integer.parseInt(s.split("\\s")[1]) % 5]++;
			}
		} catch (Exception e) {
		}
		System.out.println(count[1] + " " + count[2] + " " + count[3] + " " + count[4] + " " + count[0]);

		count = new int[5];
		f = new File("totalRoomToLibWhenBusy.txt");
		try (BufferedReader br = new BufferedReader(new FileReader(f))) {
			String s;
			while (br.ready()) {
				s = br.readLine();
				count[Integer.parseInt(s.split("\\s")[1]) % 5]++;
			}
		} catch (Exception e) {
		}
		System.out.println(count[1] + " " + count[2] + " " + count[3] + " " + count[4] + " " + count[0]);
	}

}
