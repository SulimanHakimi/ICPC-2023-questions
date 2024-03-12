import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Dup {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		System.out.print(process(input));

		input.close();
	}

	public static String process(Scanner input) {
		String[] iwh = input.nextLine().split(" ");

		int imageCount = Integer.parseInt(iwh[0]);
//		int imageWidth = Integer.parseInt(iwh[1]);
//		int imageHeight = Integer.parseInt(iwh[2]);

		Map<String, List<String>> images = new HashMap<String, List<String>>();

		String sep = "-----";

		Set<String> dupset = new HashSet<String>();

		while (imageCount-- > 0) {
			String fileName = input.nextLine();
			StringBuilder image = new StringBuilder();

			String tmp = "";
			while (!sep.equals(tmp = input.nextLine())) {
				image.append(tmp);
				image.append("\n");
			}

			String original = toString(rotateImage(toMatrix(image.toString())));

			List<String> list = images.get(original);

			if (list == null) {
				images.put(original, list = new ArrayList<>());
			} else {
				dupset.add(fileName);
				dupset.addAll(list);
			}

			list.add(fileName);
		}

		List<String> duplicates = new ArrayList<>(dupset);
		duplicates.sort((f1, f2) -> f1.compareTo(f2));

		return String.join("\n", duplicates);
	}

	private static Character[][] toMatrix(String string) {
		String[] rows = string.split("\n");
		Character[][] matrix = new Character[rows.length][];

		string = string.replaceAll("\n", "");

		for (int i = 0, offset = 0; i < rows.length; i++) {
			String[] cols = rows[i].split("");
			matrix[i] = new Character[cols.length];

			for (int j = 0; j < cols.length; j++) {
				matrix[i][j] = string.charAt(offset++);
			}
		}

		return matrix;
	}

	private static String toString(Character[][] imageMatrix) {
		StringBuilder string = new StringBuilder();

		for (Character[] h : imageMatrix) {
			for (int w : h) {
				string.append(w);
			}
			string.append("\n");
		}

		return string.toString().trim();
	}

	private static Character[][] rotateImage(Character[][] matrix) {
		int rotationDegree = 0;
		if (matrix[0][0] == '+') {
			// top left
			rotationDegree = 90;
		} else if (matrix[0][matrix[0].length - 1] == '+') {
			// top right
			rotationDegree = 180;
		} else if (matrix[matrix.length - 1][0] == '+') {
			// bottom left
			rotationDegree = 0;
		} else if (matrix[matrix.length - 1][matrix[0].length - 1] == '+') {
			// bottom right
			rotationDegree = 270;
		}

		while (rotationDegree > 0) {
			matrix = rotateImage90Degree(matrix);
			rotationDegree -= 90;
		}

		return matrix;
	}

	private static Character[][] rotateImage90Degree(Character[][] old) {
		Character[][] uew = new Character[old[0].length][old.length];
		String string = toString(old).replaceAll("\n", "");
		int offset = 0;

		for (int w = 0; w < uew[0].length; w++) {
			for (int h = uew.length - 1; h >= 0; h--) {
				uew[h][w] = string.charAt(offset++);
			}
		}

		return uew;
	}
}
