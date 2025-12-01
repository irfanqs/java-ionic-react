public class TugasSorting {

    public static void main(String[] args) {
        int[] dataMerge   = {34, 12, 90, 5, 67, 23, 41, 8};
        int[] dataCounting = dataMerge.clone();   

        System.out.print("Data awal       : ");
        printArray(dataMerge);

        // ====== MERGE SORT (dari terbesar ke terkecil) ======
        mergeSortDescending(dataMerge, 0, dataMerge.length - 1);
        System.out.print("Merge Sort desc : ");
        printArray(dataMerge);

        // ====== COUNTING SORT (dari terbesar ke terkecil) ======
        countingSortDescending(dataCounting);
        System.out.print("Counting desc   : ");
        printArray(dataCounting);
    }

    // ---------- Utilitas ----------
    private static void printArray(int[] arr) {
        for (int v : arr) System.out.print(v + " ");
        System.out.println();
    }

    // =========================================================
    //                MERGE SORT DESCENDING = O(n Log n)
    // =========================================================
    private static void mergeSortDescending(int[] arr, int left, int right) {
        if (left >= right) return;
        int mid = (left + right) / 2;
        mergeSortDescending(arr, left, mid);
        mergeSortDescending(arr, mid + 1, right);
        mergeDescending(arr, left, mid, right);
    }

    private static void mergeDescending(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int i = 0; i < n1; i++) L[i] = arr[left + i];
        for (int j = 0; j < n2; j++) R[j] = arr[mid + 1 + j];

        int i = 0, j = 0, k = left;

        // perhatikan tanda perbandingan: ">" supaya terbesar dulu
        while (i < n1 && j < n2) {
            if (L[i] > R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = L[i];
            i++; k++;
        }
        while (j < n2) {
            arr[k] = R[j];
            j++; k++;
        }
    }

    // =========================================================
    //                COUNTING SORT DESCENDING = O(n + k)
    // =========================================================
    private static void countingSortDescending(int[] arr) {
        if (arr.length == 0) return;

        int max = arr[0];
        for (int v : arr) if (v > max) max = v;

        int[] count = new int[max + 1];

        // hitung frekuensi tiap nilai
        for (int v : arr) {
            count[v]++;
        }

        // isi kembali array dari nilai terbesar ke terkecil
        int idx = 0;
        for (int v = max; v >= 0; v--) {
            while (count[v] > 0) {
                arr[idx++] = v;
                count[v]--;
            }
        }
    }
}
