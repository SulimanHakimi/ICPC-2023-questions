import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

public class Sql {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		String query = getQuery(input);

		// get the line count
		int lineCount = Integer.parseInt(input.nextLine());

		// get the csv header
		String header = input.nextLine();

		process(query, lineCount, header, input, row -> System.out.print(row));

		input.close();
	}

	public static String getQuery(Scanner input) {
		List<String> lines = new ArrayList<String>();
		String line = "";

		while (true) {
			line = input.nextLine();
			lines.add(line);

			if (line.trim().endsWith(";")) {
				break;
			}
		}

		return String.join("\n", lines);
	}

	public static void process( //
			String query, Integer lineCount, String header, //
			Scanner input, Consumer<String> output) {
		query = query.trim();
		header = header.trim();

		// ignore the ;
		query = query.substring(0, query.length() - 1);

		// separate the select and where part
		int whIdx = query.indexOf("where");

		String selectPart = "";
		String wherePart = "";

		// there is no where clause
		if (whIdx < 0) {
			whIdx = query.length();
		} else {
			wherePart = query.substring(whIdx + 5); // 5 is the length("where")
		}

		selectPart = query.substring(6, whIdx); // 6 is the length("select")

		// map csv header column to their indices
		Map<String, Integer> columnIndices = new HashMap<String, Integer>();
		for (String column : header.split(",")) {
			columnIndices.put(column.toLowerCase(), columnIndices.size());
		}

		// array of selected columns
		// remove blank characters, and replace * (represent all columns) with header
		String[] columns = selectPart.replaceAll("\\s+", "").replaceAll("\\*", header).split(",");

		// print the output csv header
		output.accept(String.join(",", columns));
		lineCount--;

		// now is the time to turn to case-insensitivity
		selectPart = selectPart.toLowerCase();
		wherePart = wherePart.toLowerCase();
		for (int i = 0; i < columns.length; i++) {
			columns[i] = columns[i].toLowerCase();
		}

		// placeholder => column-value
		Map<String, String> placeholderToValues = new HashMap<String, String>();
		Criteria where = new Criteria(0) {
			@Override
			boolean evaluate(String[] values) {
				return true;
			}
		};

		if (!wherePart.isEmpty()) {
			// create a criteria tree and get the root
			where = createCriteriaTree( //
					replaceValuesWithPlaceholders(wherePart.trim(), placeholderToValues), //
					placeholderToValues, columnIndices);
		}

		// get each line
		while (lineCount-- > 0) {
			// if it passed the filter
			String row = filter(columns, input.nextLine(), where, columnIndices);

			if (row != null) {
				// print it out
				output.accept("\n");
				output.accept(row);
			}
		}
	}

	private static String filter(//
			// select columns from where
			String[] columns, String from, Criteria where,
			// used to map columns to csv columns
			Map<String, Integer> columnIndices) {
		String[] row = from.split(",");

		// run the criteria tree against the row
		if (where.evaluate(row)) {
			// and if it passed the check
			// create and return a row of only selected columns
			String[] result = new String[columns.length];
			int col = 0;

			for (String column : columns) {
				result[col++] = row[columnIndices.get(column)];
			}

			return String.join(",", result);
		} else {
			return null;
		}
	}

	private static void mergeCriterias(List<Criteria> where, int fromLevel) {
		// start from the deepest level moving upward to 0
		for (int checkingLevel = fromLevel; checkingLevel >= 0 && where.size() > 1; checkingLevel--) {
			// search for all criteria with the same level
			for (int i = 0; i < where.size(); i++) {
				Criteria condition = where.get(i);

				// merge only unchecked and/or
				if (condition.level == checkingLevel && condition instanceof LeftRight) {
					LeftRight andor = (LeftRight) condition;

					if (andor.left == null && andor.right == null) {
						// remove the merged ones
						andor.left = where.remove(--i);
						andor.right = where.remove(i + 1);
					}
				}
			}

			if (checkingLevel > 0) {
				// move one leve up
				for (Criteria condition : where) {
					if (condition.level == checkingLevel) {
						condition.level--;
					}
				}
			}
		}
	}

	private static Criteria createCriteriaTree(//
			String where, Map<String, String> values, Map<String, Integer> columnIndices) {
		List<Criteria> criterias = new ArrayList<Criteria>();
		int currentLevel = 0, deepestLevel = 0;

		for (String token : where
				// remove blank spaces
				.replaceAll("\\s+", "")
				// format and, or
				.replaceAll("and", " and ").replaceAll("or", " or ")
				// and parenthesis
				.replaceAll("\\(", "( ").replaceAll("\\)", " )")
				// then break it into tokens
				.split(" ")) {
			// create Criteria objects
			if (token.contains("=")) {
				Equality eq = new Equality(currentLevel);
				eq.index = columnIndices.get(token.substring(0, token.indexOf("=")).trim());
				eq.value = values.get(token.substring(token.indexOf("=") + 1).trim());

				criterias.add(eq);
			} else if (token.equals("and")) {
				criterias.add(new And(currentLevel));
			} else if (token.equals("or")) {
				criterias.add(new Or(currentLevel));
			} else
			// and associated precedence level
			if (token.equals("(")) {
				currentLevel++;
			} else if (token.equals(")")) {
				currentLevel--;
			}

			deepestLevel = Math.max(deepestLevel, currentLevel);
		}

		// finish by merging criterias into a tree
		mergeCriterias(criterias, deepestLevel);

		// and return the root
		return criterias.get(0);
	}

	private static String replaceValuesWithPlaceholders(String where, Map<String, String> values) {
		StringBuilder buffer = new StringBuilder(where);

		int from = -1;
		while ((from = buffer.indexOf("\"")) > -1) {
			int till = buffer.indexOf("\"", from + 1) + 1;

			// get the value ignoring double-quotations
			String value = buffer.substring(from + 1, till - 1);

			String placeholder = null;
			// ensure a unique placeholder
			while (placeholder == null || values.containsKey(placeholder)) {
				placeholder = String.valueOf((int) (Math.random() * 100_000_000));
			}

			values.put(placeholder, value);
			buffer.replace(from, till, placeholder);
		}

		// put the where in a pair of parenthesis
		// which is used to find the criteria precedence level
		if (buffer.charAt(0) != '(') {
			buffer.insert(0, "(");
			buffer.append(")");
		}

		return buffer.toString();
	}
}

abstract class Criteria {
	int level;

	public Criteria(int level) {
		this.level = level;
	}

	abstract boolean evaluate(String[] values);
}

class Equality extends Criteria {
	int index;
	String value;

	public Equality(int level) {
		super(level);
	}

	@Override
	boolean evaluate(String[] values) {
		// for equality check if the value of column is equal
		return values[index].equalsIgnoreCase(value);
	}

	@Override
	public String toString() {
		return index + "=" + value;
	}
}

abstract class LeftRight extends Criteria {
	Criteria left;
	Criteria right;

	public LeftRight(int level) {
		super(level);
	}
}

class And extends LeftRight {

	public And(int level) {
		super(level);
	}

	@Override
	boolean evaluate(String[] values) {
		// ignore the sides if they are null
		return (left == null || left.evaluate(values)) && (right == null || right.evaluate(values));
	}

	@Override
	public String toString() {
		return "and(" + left + "," + right + ")";
	}
}

class Or extends LeftRight {

	public Or(int level) {
		super(level);
	}

	@Override
	boolean evaluate(String[] values) {
		// ignore the sides if they are null
		return (left != null && left.evaluate(values)) || (right != null && right.evaluate(values));
	}

	@Override
	public String toString() {
		return "or(" + left + "," + right + ")";
	}
}