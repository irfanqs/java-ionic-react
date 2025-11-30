import java.util.ArrayList;
import java.util.Scanner;

class Menu {
    String nama;
    int harga;
    String kategori; // makanan / minuman

    Menu(String nama, int harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }
}

public class Makanan {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Menu> daftarMenu = new ArrayList<>();

    public static void main(String[] args) {
        seedMenuAwal();
        mainMenu();
    }

    static void seedMenuAwal() {
        daftarMenu.add(new Menu("Nasi Goreng", 20000, "makanan"));
        daftarMenu.add(new Menu("Mie Ayam", 15000, "makanan"));
        daftarMenu.add(new Menu("Ayam Bakar", 25000, "makanan"));
        daftarMenu.add(new Menu("Sate Ayam", 22000, "makanan"));

        daftarMenu.add(new Menu("Es Teh", 5000, "minuman"));
        daftarMenu.add(new Menu("Jus Jeruk", 10000, "minuman"));
        daftarMenu.add(new Menu("Kopi Hitam", 8000, "minuman"));
        daftarMenu.add(new Menu("Cappuccino", 15000, "minuman"));
    }

    static void mainMenu() {
        while (true) {
            System.out.println("\n=== APLIKASI RESTORAN ===");
            System.out.println("1. Pemesanan");
            System.out.println("2. Manajemen Menu");
            System.out.println("3. Keluar");
            System.out.print("Pilih: ");

            String pilih = sc.nextLine();
            switch (pilih) {
                case "1": pemesanan(); break;
                case "2": manajemenMenu(); break;
                case "3": return;
                default: System.out.println("Input salah.");
            }
        }
    }

    static void tampilkanMenu() {
        System.out.println("\n--- MENU MAKANAN ---");
        for (int i = 0; i < daftarMenu.size(); i++) {
            if (daftarMenu.get(i).kategori.equals("makanan")) {
                System.out.println((i+1)+". "+daftarMenu.get(i).nama+" - Rp "+daftarMenu.get(i).harga);
            }
        }

        System.out.println("\n--- MENU MINUMAN ---");
        for (int i = 0; i < daftarMenu.size(); i++) {
            if (daftarMenu.get(i).kategori.equals("minuman")) {
                System.out.println((i+1)+". "+daftarMenu.get(i).nama+" - Rp "+daftarMenu.get(i).harga);
            }
        }
    }

    static void pemesanan() {
        ArrayList<Menu> pesanan = new ArrayList<>();
        ArrayList<Integer> jumlah = new ArrayList<>();

        while (true) {
            tampilkanMenu();
            System.out.print("\nMasukkan nama menu yang dipesan (atau 'selesai'): ");
            String input = sc.nextLine();

            if (input.equalsIgnoreCase("selesai")) break;

            Menu item = cariMenu(input);
            if (item == null) {
                System.out.println("Menu tidak ditemukan.");
                continue;
            }

            System.out.print("Jumlah: ");
            int jml = Integer.parseInt(sc.nextLine());

            pesanan.add(item);
            jumlah.add(jml);
        }

        cetakStruk(pesanan, jumlah);
    }

    static Menu cariMenu(String nama) {
        for (Menu m : daftarMenu) {
            if (m.nama.equalsIgnoreCase(nama)) return m;
        }
        return null;
    }

    static void cetakStruk(ArrayList<Menu> pesanan, ArrayList<Integer> jumlah) {
        System.out.println("\n=== STRUK PEMBAYARAN ===");

        int subtotal = 0;
        int totalMinuman = 0;
        int hargaMinumanTermurah = Integer.MAX_VALUE;

        for (int i = 0; i < pesanan.size(); i++) {
            Menu m = pesanan.get(i);
            int qty = jumlah.get(i);
            int total = m.harga * qty;

            System.out.println(m.nama + " x" + qty + " = Rp " + total);

            subtotal += total;

            if (m.kategori.equals("minuman")) {
                totalMinuman += qty;
                if (m.harga < hargaMinumanTermurah) hargaMinumanTermurah = m.harga;
            }
        }

        // Diskon & Promo
        int diskon = 0;
        int promoMinuman = 0;

        if (subtotal > 100000) {
            diskon = (int)(subtotal * 0.10);
        }

        if (subtotal > 50000 && totalMinuman >= 2) {
            promoMinuman = hargaMinumanTermurah;
        }

        int pajak = (int)(subtotal * 0.10);
        int service = 20000;

        int totalAkhir = subtotal - diskon - promoMinuman + pajak + service;

        System.out.println("--------------------------------");
        System.out.println("Subtotal: Rp " + subtotal);
        System.out.println("Diskon: Rp " + diskon);
        System.out.println("Promo B1G1 Minuman: -Rp " + promoMinuman);
        System.out.println("Pajak (10%): Rp " + pajak);
        System.out.println("Service: Rp " + service);
        System.out.println("TOTAL BAYAR: Rp " + totalAkhir);
        System.out.println("--------------------------------");
    }

    static void manajemenMenu() {
        while (true) {
            System.out.println("\n=== MANAJEMEN MENU ===");
            System.out.println("1. Tambah Menu");
            System.out.println("2. Ubah Harga");
            System.out.println("3. Hapus Menu");
            System.out.println("4. Kembali");
            System.out.print("Pilih: ");

            String pilih = sc.nextLine();
            switch (pilih) {
                case "1": tambahMenu(); break;
                case "2": ubahHarga(); break;
                case "3": hapusMenu(); break;
                case "4": return;
                default: System.out.println("Input salah.");
            }
        }
    }

    static void tambahMenu() {
        while (true) {
            System.out.print("Nama menu: ");
            String nama = sc.nextLine();

            System.out.print("Harga: ");
            int harga = Integer.parseInt(sc.nextLine());

            System.out.print("Kategori (makanan/minuman): ");
            String kat = sc.nextLine();

            daftarMenu.add(new Menu(nama, harga, kat));
            System.out.println("Menu ditambahkan.");

            System.out.print("Tambah menu lagi? (ya/tidak): ");
            if (!sc.nextLine().equalsIgnoreCase("ya")) break;
        }
    }

    static void ubahHarga() {
        tampilkanMenu();
        System.out.print("Masukkan nama menu yang ingin diubah: ");
        String nama = sc.nextLine();

        Menu m = cariMenu(nama);
        if (m == null) {
            System.out.println("Menu tidak ditemukan.");
            return;
        }

        System.out.print("Yakin ingin ubah? (ya/tidak): ");
        if (!sc.nextLine().equalsIgnoreCase("ya")) return;

        System.out.print("Harga baru: ");
        m.harga = Integer.parseInt(sc.nextLine());
        System.out.println("Harga berhasil diubah.");
    }

    static void hapusMenu() {
        tampilkanMenu();
        System.out.print("Masukkan nama menu yang ingin dihapus: ");
        String nama = sc.nextLine();

        Menu m = cariMenu(nama);
        if (m == null) {
            System.out.println("Menu tidak ditemukan.");
            return;
        }

        System.out.print("Yakin ingin hapus? (ya/tidak): ");
        if (!sc.nextLine().equalsIgnoreCase("ya")) return;

        daftarMenu.remove(m);
        System.out.println("Menu berhasil dihapus.");
    }
}
