import java.util.*;

public class Recursion {
	public static void main(String[] args) {
		String[] maze = new String[] {
			"####### #",
			"#     # #",
			"# ##### #",
			"#       #",
			"# #######"
		};

		boolean[][] seen = new boolean[][] {
			new boolean[] { false, false, false, false, false, false, false, false, false },
			new boolean[] { false, false, false, false, false, false, false, false, false },
			new boolean[] { false, false, false, false, false, false, false, false, false },
			new boolean[] { false, false, false, false, false, false, false, false, false },
			new boolean[] { false, false, false, false, false, false, false, false, false }
		};

		boolean solved = mazeSolver(maze, "#", new Point(7, 0), new Point(1, 4), seen, new ArrayList<>());
		System.out.println(solved);
	}

	private static int addRecursive(int n) {
		// Pre: adding n + next call
		// Recurse: going into next call
		// Post: returning the result of the previous call

		return (n == 1) ? 1 : n + addRecursive(n - 1);
	}

	private static int addIterative(int n) {
		int sum = 0;
		for (int i = n; i > 0; i--) {
			sum += i;
		}
		return sum;
	}

	private static boolean mazeSolver(String[] maze, String wall, Point start, Point end, boolean[][] seen, List<Point> path) {
		Point current = start;

		// From each point, you can move up, right, bottom, left
		// Base Cases:
		// 1. Off the map
		if (current.x < 0 || current.x > maze[0].length() || current.y < 0 || current.y > maze.length) {
			return false;
		}
		// 2. Hit wall
		if (maze[current.y].charAt(current.x) == wall.charAt(0)) {
			return false;
		}
		// 3. Reaches solution
		if (current.equals(end)) {
			return true;
		}
		// 4. Already visited
		if (seen[current.y][current.x]) {
			return false;
		}

		// Pre: add to path and set node to visited
		path.add(current);
		seen[current.y][current.x] = true;
		// Recurse: check each direction until one works
		Direction[] directions = Direction.values();
		for (int i=0; i<directions.length; i++) {
			Direction dir = directions[i];
			int x = dir.movement[0];
			int y = dir.movement[1];
			boolean solved = mazeSolver(maze, wall, new Point(current.x + x, current.y + y), end, seen, path);			
			if (solved) {
				return true;
			}
		}
		// Post: seen
		path.removeFirst();
		return false;	
	}

	private static class Point {
		int x;
		int y;

		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}

			if (!(o instanceof Point)) {
				return false;
			}

			Point p = (Point) o;

			return this.x == p.x && this.y == p.y;
		}
	}

	private static enum Direction {
		TOP(new int[] { 0, -1 }), LEFT(new int[] { -1, 0 }), BOTTOM(new int[] { 0, 1 }), RIGHT(new int[] { 1, 0 });

		final int[] movement;

		Direction(int[] movement) {
			this.movement = movement;
		}
	}
}
