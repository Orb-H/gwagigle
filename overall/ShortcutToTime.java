import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;

public class ShortcutToTime {

	public static final double BlockToTime = 0.1;
	public static HashMap<Integer, HashMap<Integer, Integer>> rcut = new HashMap<>();
	public static HashMap<Integer, HashMap<Boolean, Double>> dcut = new HashMap<>();// True for busy

	public static void main(String[] args) {
		File shortcut = new File("totalRoomShortcut.txt");
		File doorTime = new File("totalDoorShortcut.txt");
		File idleTime = new File("totalRoomToLibWhenIdle.txt");
		File busyTime = new File("totalRoomToLibWhenbusy.txt");

		try (BufferedReader br = new BufferedReader(new FileReader(shortcut))) {
			String[] s = br.readLine().split("\\s");
			int roomNum = Integer.parseInt(s[0]);
			int doorNum = Integer.parseInt(s[1]);

			for (int i = 0; i < roomNum; i++) {
				HashMap<Integer, Integer> h = new HashMap<>();
				int n = Integer.parseInt(br.readLine());
				for (int j = 0; j < doorNum; j++) {
					s = br.readLine().split("\\s");
					h.put(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
				}
				rcut.put(n, h);
			}
		} catch (Exception e) {
		}

		try (BufferedReader br = new BufferedReader(new FileReader(doorTime))) {
			String[] s = br.readLine().split("\\s");
			int doorNum = Integer.parseInt(s[0]);
			int roadNum = Integer.parseInt(s[1]);

			for (int i = 0; i < doorNum; i++) {
				HashMap<Boolean, Double> h = new HashMap<>();
				int n = Integer.parseInt(br.readLine());
				for (int j = 0; j < roadNum; j++) {
					s = br.readLine().split("\\s");
					h.put(Integer.parseInt(s[0]) == 1, Double.parseDouble(s[1]));
				}
				dcut.put(n, h);
			}
		} catch (Exception e) {
		}

		try (PrintWriter pw = new PrintWriter(idleTime)) {
			for (Integer i : rcut.keySet()) {
				int door = 1;
				double time = 0.0;
				double minimum = Double.MAX_VALUE;
				for (int j = 1; j <= 5; j++) {
					time = rcut.get(i).get(300000000 + j) * BlockToTime;
					time += dcut.get(300000000 + j).get(false);
					if (time < minimum) {
						door = j;
						minimum = time;
					}
				}
				pw.println(i + " " + door + " " + minimum);
				pw.flush();
			}
		} catch (Exception e) {
		}

		try (PrintWriter pw = new PrintWriter(busyTime)) {
			for (Integer i : rcut.keySet()) {
				int door = 1;
				double time = 0.0;
				double minimum = Double.MAX_VALUE;
				for (int j = 1; j <= 5; j++) {
					time = rcut.get(i).get(300000000 + j) * BlockToTime;
					time += dcut.get(300000000 + j).get(true);
					if (time < minimum) {
						door = j;
						minimum = time;
					}
				}
				pw.println(i + " " + door + " " + minimum);
				pw.flush();
			}
		} catch (Exception e) {
		}
	}

}
