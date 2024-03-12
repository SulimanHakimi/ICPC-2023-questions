import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Ltbl {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		System.out.print(process(input));

		input.close();
	}

	public static String process(Scanner input) {
		// test case count
		int tsCount = Integer.parseInt(input.nextLine().trim());
		List<String> result = new ArrayList<String>();

		while (tsCount-- > 0) {
			int mchCount = Integer.parseInt(input.nextLine().trim());

			Map<String, Team> teams = new HashMap<String, Team>();

			while (mchCount-- > 0) {
				String match = input.nextLine();

				int from = match.indexOf("[");
				int mid = match.indexOf("-", from);
				int till = match.lastIndexOf("]");

				String team1Name = match.substring(0, from).trim();
				String team2Name = match.substring(till + 1).trim();

				int team1Goals = Integer.parseInt(match.substring(from + 1, mid).trim());
				int team2Goals = Integer.parseInt(match.substring(mid + 1, till).trim());

				Team team1 = teams.get(team1Name);
				if (team1 == null) {
					teams.put(team1Name, team1 = new Team());
					team1.name = team1Name;
				}

				Team team2 = teams.get(team2Name);
				if (team2 == null) {
					teams.put(team2Name, team2 = new Team());
					team2.name = team2Name;
				}

				team1.scored += team1Goals;
				team2.received += team1Goals;

				team2.scored += team2Goals;
				team1.received += team2Goals;

				if (team1Goals > team2Goals) {
					team1.win++;
					team2.loss++;
				} else if (team1Goals < team2Goals) {
					team2.win++;
					team1.loss++;
				} else {
					team1.draw++;
					team2.draw++;
				}

				team1.update();
				team2.update();
			}

			List<Team> teamsList = new ArrayList<Team>(teams.values());
			teamsList.sort(new Comparator<Team>() {
				@Override
				public int compare(Team o1, Team o2) {
					int diff = o2.points - o1.points;
					if (diff != 0) {
						return diff;
					}

					diff = o2.win - o1.win;
					if (diff != 0) {
						return diff;
					}

					diff = o2.draw - o1.draw;
					if (diff != 0) {
						return diff;
					}

					diff = o1.loss - o2.loss;
					if (diff != 0) {
						return diff;
					}

					diff = o2.scored - o1.scored;
					if (diff != 0) {
						return diff;
					}

					diff = o1.received - o2.received;
					if (diff != 0) {
						return diff;
					}

					return o1.name.compareTo(o2.name);
				}
			});

			List<String> strings = new ArrayList<String>();
			for (Team team : teamsList)
				strings.add(team.toString());

			result.add(String.join("\n", strings));
		}

		return String.join("\n\n", result);
	}
}

class Team {
	String name;
	int win;
	int draw;
	int loss;
	int scored;
	int received;
	int points;

	void update() {
		this.points = (win * 3) + (draw);
	}

	@Override
	public String toString() {
		return String.format("%s,%d,%d,%d,%d,%d,%d", name, points, win, draw, loss, scored, received);
	}
}