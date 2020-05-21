package study;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_9376_탈옥 {
	static class Pos {
		int x, y, door;

		public Pos(int x, int y, int door) {
			super();
			this.x = x;
			this.y = y;
			this.door = door;
		}

		@Override
		public String toString() {
			return "Pos [x=" + x + ", y=" + y + ", door=" + door + "]";
		}

	}

	static int[] dx = { -1, 0, 1, 0 };
	static int[] dy = { 0, 1, 0, -1 };
	static char[][] map = new char[105][105];
	static int h, w;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {
			st = new StringTokenizer(br.readLine());
			h = Integer.parseInt(st.nextToken()) + 2;
			w = Integer.parseInt(st.nextToken()) + 2;

			List<Pos> player = new ArrayList<BOJ_9376_탈옥.Pos>();
			player.add(new Pos(0, 0, 0));
			for (int i = 1; i < h - 1; i++) {
				map[i][0] = '.';
				map[i][h - 1] = '.';
				String input = br.readLine();
				for (int j = 1; j < w - 1; j++) {
					map[i][j] = input.charAt(j - 1);
					// System.out.println(i+", "+j);
					// System.out.println(Arrays.toString(map[i]));
					if (map[i][j] == '$') {
						player.add(new Pos(i, j, 0));
					}
				}

			}

			for (int j = 0; j < w; j++) {
				map[0][j] = map[h - 1][j] = '.';
			}
			int[][] dist1 = bfs(player.get(0));
			int[][] dist2 = bfs(player.get(1));
			int[][] dist3 = bfs(player.get(2));

			int ans = Integer.MAX_VALUE;
			int sum = 0;
			for (int i = 0; i < h; i++) {
				for (int j = 0; j < w; j++) {
					if (map[i][j] == '*')
						continue;
					sum = dist1[i][j] + dist2[i][j] + dist3[i][j];
					if (map[i][j] == '#')
						sum -= 2;
					ans = Math.min(ans, sum);
				}
			}
			System.out.print(ans + "\n");
		}
	}

	static int[][] bfs(Pos start) {
		int[][] visit = new int[h][w]; // 몇 개의 문을 열었는지 기록
		Queue<Pos> q = new LinkedList<Pos>();
		memset(visit, -1);
		visit[start.x][start.y] = 0;
		q.offer(start);

		while (!q.isEmpty()) {
			Pos now = q.poll();
			int x = now.x;
			int y = now.y;
			int door = now.door;
			//System.out.println(x + ", " + y);
			for (int i = 0; i < 4; i++) {
				int nx = x + dx[i];
				int ny = y + dy[i];
				int ndoor = door;
				if (nx < 0 || nx > h - 1 || ny < 0 || ny > w - 1)
					continue;
				if (map[nx][ny] == '*')
					continue;
				if (map[nx][ny] == '#') { // 문일 경우
					ndoor++;
				}
				if (visit[nx][ny] == -1 || visit[nx][ny] > ndoor) {
					visit[nx][ny] = ndoor;
					q.offer(new Pos(nx, ny, ndoor));
				}
			}
		}

		return visit;
	}

	static void memset(int[][] arr, int val) {
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				arr[i][j] = val;
			}
		}
	}
}
