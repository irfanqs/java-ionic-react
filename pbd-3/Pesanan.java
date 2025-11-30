import java.io.*;
import java.util.ArrayList;

public class Pesanan {
    private ArrayList<MenuItem> pesanan = new ArrayList<>();

    public void tambah(MenuItem item) {
        pesanan.add(item);
    }

    public double hitungTotal() {
        double total = 0;
        double maxDiskon = 0;

        for (MenuItem i : pesanan) {
            if (i instanceof Diskon) {
                double persen = ((Diskon) i).getPersenDiskon();
                if (persen > maxDiskon) maxDiskon = persen;
            } else {
                total += i.getHarga();
            }
        }

        if (maxDiskon > 0) {
            total -= total * (maxDiskon / 100.0);
        }

        return total;
    }

    public void cetakStruk() {
        System.out.println("\n=== STRUK PESANAN ===");
        for (MenuItem i : pesanan) i.tampilMenu();

        System.out.println("Total: Rp " + hitungTotal());
    }

    public void simpanStruk() throws Exception {
        PrintWriter pw = new PrintWriter("struk.txt");
        pw.println("=== STRUK PESANAN ===");
        for (MenuItem i : pesanan) pw.println(i.getNama() + " - Rp " + i.getHarga());
        pw.println("Total: Rp " + hitungTotal());
        pw.close();

        System.out.println("Struk berhasil disimpan.");
    }
}
    