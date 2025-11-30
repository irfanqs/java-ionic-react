public class Diskon extends MenuItem {
    private double persenDiskon;

    public Diskon(String nama, double persenDiskon) {
        super(nama, 0, "Diskon");
        this.persenDiskon = persenDiskon;
    }

    public double getPersenDiskon() {
        return persenDiskon;
    }

    @Override
    public void tampilMenu() {
        System.out.println(getNama() + " | Diskon: " + persenDiskon + "%");
    }
}
