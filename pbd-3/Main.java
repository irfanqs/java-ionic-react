import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Menu menu = new Menu();
        Pesanan pesanan = new Pesanan();

        while (true) {
            System.out.println("\n=== APLIKASI RESTORAN ===");
            System.out.println("1. Tambah Item Menu");
            System.out.println("2. Tampilkan Menu");
            System.out.println("3. Buat Pesanan");
            System.out.println("4. Hitung Total");
            System.out.println("5. Cetak Struk");
            System.out.println("6. Simpan Menu ke File");
            System.out.println("7. Load Menu dari File");
            System.out.println("8. Keluar");
            System.out.print("Pilih: ");

            String pilih = sc.nextLine();

            try {
                switch (pilih) {
                    case "1":
                        System.out.println("1. Makanan | 2. Minuman | 3. Diskon");
                        String t = sc.nextLine();

                        if (t.equals("1")) {
                            System.out.print("Nama: ");
                            String n = sc.nextLine();
                            System.out.print("Harga: ");
                            double h = Double.parseDouble(sc.nextLine());
                            System.out.print("Jenis makanan: ");
                            String jm = sc.nextLine();
                            menu.tambah(new Makanan(n, h, jm));

                        } else if (t.equals("2")) {
                            System.out.print("Nama: ");
                            String n = sc.nextLine();
                            System.out.print("Harga: ");
                            double h = Double.parseDouble(sc.nextLine());
                            System.out.print("Jenis minuman: ");
                            String jm = sc.nextLine();
                            menu.tambah(new Minuman(n, h, jm));

                        } else if (t.equals("3")) {
                            System.out.print("Nama diskon: ");
                            String n = sc.nextLine();
                            System.out.print("Persen diskon: ");
                            double d = Double.parseDouble(sc.nextLine());
                            menu.tambah(new Diskon(n, d));
                        }
                        break;

                    case "2":
                        menu.tampilMenu();
                        break;

                    case "3":
                        System.out.print("Masukkan nama item yang dipesan: ");
                        String nm = sc.nextLine();
                        pesanan.tambah(menu.cari(nm));
                        break;

                    case "4":
                        System.out.println("Total biaya: Rp " + pesanan.hitungTotal());
                        break;

                    case "5":
                        pesanan.cetakStruk();
                        pesanan.simpanStruk();
                        break;

                    case "6":
                        menu.simpanKeFile();
                        break;

                    case "7":
                        menu.loadDariFile();
                        break;

                    case "8":
                        return;

                    default:
                        System.out.println("Menu tidak ada.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
