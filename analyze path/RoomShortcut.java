import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RoomShortcut {

	public static final int stairToBlock = 148;

	public static HashMap<Integer, HashMap<Integer, Integer>> roomToStairMap = new HashMap<>();
	public static HashMap<Integer, HashMap<Integer, Integer>> stairToDoorMap = new HashMap<>();
	public static HashMap<Integer, HashMap<Integer, Integer>> floor1ToDoorMap = new HashMap<>();
	public static List<Integer> roomNumbers = new ArrayList<>();

	static {
		roomNumbers = Arrays.asList(new Integer[] { 100027515, 100027517, 100027511, 100027530, 100027532, 100027518,
				100026514, 100026512, 100026510, 100026508, 100026506, 100026504, 100026502, 100026515, 100026513,
				100026511, 100026509, 100026507, 100026505, 100025533, 100027419, 100027422, 100027425, 100026421,
				100026419, 100025433, 100027321, 100027323, 100027332, 100027334, 100027336, 100027316, 100026315,
				100026311, 100026318, 100026307, 100026305, 100026314, 100026312, 100026310, 100026308, 100025315,
				100025309, 100025314, 100027240, 100027218, 100026223, 100026217, 100026211, 100025217, 100027119,
				100027118, 100027117, 100027113, 100027111, 100027109, 100027107, 100027105, 100027144, 100027114,
				100027112, 100027108, 100027104, 100026149, 100026119, 100026117, 100026110, 100026108, 100026106,
				100025113 });
	}

	public static void main(String[] args) {
		File stairs = new File("stairWithDoor.txt");
		File floor1 = new File("roomAtFloor1.txt");
		File rooms = new File("roomWithStair.txt");
		File roomShortcut = new File("totalRoomShortcut.txt");

		try (BufferedReader br = new BufferedReader(new FileReader(rooms))) {
			String[] s = br.readLine().split("\\s");
			int roomNum = Integer.parseInt(s[0]);
			int stairNum = Integer.parseInt(s[1]);

			for (int i = 0; i < roomNum; i++) {
				HashMap<Integer, Integer> h = new HashMap<>();
				int n = Integer.parseInt(br.readLine());
				for (int j = 0; j < stairNum; j++) {
					s = br.readLine().split("\\s");
					h.put(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
				}
				roomToStairMap.put(n, h);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try (BufferedReader br = new BufferedReader(new FileReader(stairs))) {
			String[] s = br.readLine().split("\\s");
			int stairNum = Integer.parseInt(s[0]);
			int doorNum = Integer.parseInt(s[1]);

			for (int i = 0; i < stairNum; i++) {
				HashMap<Integer, Integer> h = new HashMap<>();
				int n = Integer.parseInt(br.readLine());
				for (int j = 0; j < doorNum; j++) {
					s = br.readLine().split("\\s");
					h.put(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
				}
				stairToDoorMap.put(n, h);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try (BufferedReader br = new BufferedReader(new FileReader(floor1))) {
			String[] s = br.readLine().split("\\s");
			int roomNum = Integer.parseInt(s[0]);
			int stairNum = Integer.parseInt(s[1]);

			for (int i = 0; i < roomNum; i++) {
				HashMap<Integer, Integer> h = new HashMap<>();
				int n = Integer.parseInt(br.readLine());
				for (int j = 0; j < stairNum; j++) {
					s = br.readLine().split("\\s");
					h.put(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
				}
				floor1ToDoorMap.put(n, h);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try (PrintWriter pw = new PrintWriter(roomShortcut)) {
			pw.println(roomNumbers.size() + " " + 5);
			for (Integer i : roomNumbers) {
				HashMap<Integer, Integer> h = find(i);
				pw.println(i);
				for (Integer j : h.keySet()) {
					pw.println(j + " " + h.get(j));
				}
				pw.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static HashMap<Integer, Integer> find(int roomNum) {
		HashMap<Integer, Integer> h = new HashMap<>();
		int a;
		switch (a = (roomNum / 100) % 10) {
		case 1:
			int min = Integer.MAX_VALUE;
			int temp = 0;
			for (int i = 1; i <= 4; i++) {
				temp = floor1ToDoorMap.get(roomNum).get(300000000 + i);
				h.put(300000000 + i, temp);
			}
			for (int i = 3; i <= 4; i++) {
				temp = roomToStairMap.get(roomNum).get(200100000 + i);
				temp += stairToBlock;
				temp += stairToDoorMap.get(200100000 + i).get(300000005);
				min = Math.min(min, temp);
			}
			h.put(300000005, min);
			break;
		case 2:
		case 3:
		case 4:
		case 5:
			int minimum = Integer.MAX_VALUE;
			int tmp = 0;
			for (int i = 1; i <= 5; i++) {
				minimum = Integer.MAX_VALUE;
				for (int j = 1; j <= 5; j++) {
					if ((j + 1) / 2 == 2 && i == 5)
						continue;
					tmp = roomToStairMap.get(roomNum).get(200100000 + j);
					tmp += ((a - 1) + i / 5) * stairToBlock;
					tmp += stairToDoorMap.get(200100000 + j).get(300000000 + i);
					minimum = Integer.min(minimum, tmp);
				}
				h.put(300000000 + i, minimum);
			}
			break;
		default:
			break;
		}

		return h;
	}

}
