import java.util.Scanner;

public class BnkQ {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		System.out.print(process(input));

		input.close();
	}

	private static int[] split(String line) {
		return new int[] { //
				Integer.parseInt(line.substring(0, line.indexOf(" ")).trim()),
				Integer.parseInt(line.substring(line.indexOf(" ") + 1).trim()) //
		};
	}

	public static String process(Scanner input) {
		// test case count
		int tsCount = Integer.parseInt(input.nextLine().trim());
		String result = "";

		while (tsCount-- > 0) {
			// counter and customer count
			int[] ccCount = split(input.nextLine());

			Queue[] queues = new Queue[ccCount[0]];
			int count = ccCount[1];

			Queue lastAppendQueue = null;

			outer: while (count-- > 0) {
				// delay weight
				int[] dw = split(input.nextLine());

				Queue shortestQueue = null;

				for (int i = 0; i < queues.length; i++) {
					Queue queue = queues[i];

					if (queue == null) {
						queues[i] = new Queue();
						queue = queues[i];

						queue.delay = dw[0] + (lastAppendQueue == null ? 0 : lastAppendQueue.delay);
						queue.weight = dw[1];
						queue.updateFinish();

						lastAppendQueue = queue;

						debug(lastAppendQueue);

						continue outer;
					} else if (shortestQueue == null) {
						shortestQueue = queue;
					} else if (queue.finish < shortestQueue.finish) {
						shortestQueue = queue;
					}
				}

				int arrivedAt = lastAppendQueue.delay + dw[0];
				if (arrivedAt < shortestQueue.finish) {
					arrivedAt = shortestQueue.finish;
				}

				shortestQueue.delay = arrivedAt;
				shortestQueue.weight = dw[1];
				shortestQueue.updateFinish();

				debug(shortestQueue);

				lastAppendQueue = shortestQueue;
			}

			int lastCustomerFinish = -1;
			for (Queue queue : queues) {
				lastCustomerFinish = Math.max(queue.finish, lastCustomerFinish);
			}

			result += lastCustomerFinish + (tsCount > 0 ? "\n" : "");
		}

		return result;
	}

	private static void debug(Queue last) {
		// System.out.println((" ".repeat(last.delay)) + ("#".repeat(last.weight)));
	}
}

class Queue {

	int delay;
	int weight;
	int finish;

	public void updateFinish() {
		this.finish = this.delay + this.weight;
	}
}