import java.io.*;
import java.util.ArrayList;

public class Menu {
    private ArrayList<MenuItem> items = new ArrayList<>();

    public void tambah(MenuItem item) {
        items.add(item);
        System.out.println("Item berhasil ditambahkan.");
    }

    public void tampilMenu() {
        if (items.isEmpty()) {
            System.out.println("Menu kosong.");
            return;
        }

        System.out.println("\n=== MENU RESTORAN ===");
        for (MenuItem i : items) i.tampilMenu();
    }

    public MenuItem cari(String nama) throws Exception {
        for (MenuItem i : items) {
            if (i.getNama().equalsIgnoreCase(nama)) {
                return i;
            }
        }
        throw new Exception("Menu tidak ditemukan!");
    }

    public void simpanKeFile() throws Exception {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("menu.txt"));
        out.writeObject(items);
        out.close();
        System.out.println("Menu berhasil disimpan.");
    }

    @SuppressWarnings("unchecked")
    public void loadDariFile() throws Exception {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("menu.txt"));
        items = (ArrayList<MenuItem>) in.readObject();
        in.close();
        System.out.println("Menu berhasil dimuat.");
    }
}
