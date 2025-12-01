import java.util.*;

public class GraphSearch {
    // 8 node: a1..a8, index 0..7
    private static final int N = 8;
    private static List<Integer>[] adj = new ArrayList[N];

    static {
        for (int i = 0; i < N; i++) adj[i] = new ArrayList<>();
        // graf tak berarah sesuai deskripsi:
        addEdge(0, 1); // a1-a2
        addEdge(0, 2); // a1-a3
        addEdge(1, 3); // a2-a4
        addEdge(1, 4); // a2-a5
        addEdge(2, 5); // a3-a6
        addEdge(3, 6); // a4-a7
        addEdge(4, 7); // a5-a8
    }

    private static void addEdge(int u, int v) {
        adj[u].add(v);
        adj[v].add(u);
    }

    // nilai di node i adalah i+1
    private static int valueOf(int nodeIndex) {
        return nodeIndex + 1;
    }

    // ---------- DFS ----------
    private static boolean dfs(int current, int target, boolean[] visited) {
        visited[current] = true;
        System.out.println("DFS mengunjungi: a" + (current + 1) +
                           " (nilai = " + valueOf(current) + ")");
        if (valueOf(current) == target) {
            System.out.println(">> DITEMUKAN di node a" + (current + 1));
            return true;
        }
        for (int next : adj[current]) {
            if (!visited[next]) {
                if (dfs(next, target, visited)) return true; // hentikan jika sudah ketemu
            }
        }
        return false;
    }

    // ---------- BFS ----------
    private static boolean bfs(int start, int target) {
        boolean[] visited = new boolean[N];
        Queue<Integer> q = new LinkedList<>();
        q.add(start);
        visited[start] = true;

        while (!q.isEmpty()) {
            int current = q.poll();
            System.out.println("BFS mengunjungi: a" + (current + 1) +
                               " (nilai = " + valueOf(current) + ")");
            if (valueOf(current) == target) {
                System.out.println(">> DITEMUKAN di node a" + (current + 1));
                return true;
            }
            for (int next : adj[current]) {
                if (!visited[next]) {
                    visited[next] = true;
                    q.add(next);
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Masukkan angka n yang ingin dicari (1-8): ");
        int n = sc.nextInt();

        System.out.println("\n=== PENCARIAN DENGAN DEPTH-FIRST SEARCH (DFS) ===");
        boolean[] visited = new boolean[N];
        boolean foundDfs = dfs(0, n, visited); // mulai dari a1 (index 0)
        if (!foundDfs) {
            System.out.println("Angka " + n + " TIDAK ditemukan dengan DFS");
        }

        System.out.println("\n=== PENCARIAN DENGAN BREADTH-FIRST SEARCH (BFS) ===");
        boolean foundBfs = bfs(0, n); // mulai dari a1
        if (!foundBfs) {
            System.out.println("Angka " + n + " TIDAK ditemukan dengan BFS");
        }

        sc.close();
    }
}
