import java.util.LinkedList;
import java.util.Arrays;

public class TugasStrukturData1 {
    public static void main(String[] args) {

        // 1. Deklarasi variabel integer bernama StrukturBaris
        int StrukturBaris;

        // 2. Deklarasi variabel String bernama KataBaru
        String KataBaru = "Deklarasi tipe data String";

        // 3. Array 1 dimensi bernama empatAngka, tipe int
        int[] empatAngka = {7, 10, 20, 23}; 

        // 4. Array 2 dimensi bernama Angka, tipe String, 3 baris x 3 kolom
        String[][] Angka = {
            {"1",  "3",  "5"},
            {"14", "19", "20"},
            {"22", "27", "29"}
        };

        // 5. Linked list bernama listAngka dengan isi (22, 19, 44, 60, 72)
        LinkedList<Integer> listAngka =
                new LinkedList<>(Arrays.asList(22, 19, 44, 60, 72));

        // (opsional) print saja biar kelihatan waktu demo
        System.out.println("KataBaru = " + KataBaru);
        System.out.println("empatAngka[0] = " + empatAngka[0]);
        System.out.println("Angka[2][2] = " + Angka[2][2]);
        System.out.println("listAngka = " + listAngka);
    }
}
