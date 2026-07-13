package model;

/**
 * Model: SparePart
 * One row of the Spare Part Catalog (/data/sparepart.xml).
 *
 * NOTE (assumption): the brief only specifies a Selling Price. To let the
 * Financial Report menu compute a real COGS (Cost of Goods Sold), a
 * hargaModal (cost/purchase price) field is added. If it is not supplied
 * on the input form it defaults to 60% of the selling price.
 */
public class SparePart {
    private String kode;        // unique code, e.g. SP-0001
    private String nama;        // part name
    private String kategori;    // category (from ComboBox)
    private int stok;           // current stock
    private double hargaJual;   // selling price
    private double hargaModal;  // cost price (assumption, used for COGS)

    public SparePart() {}

    public SparePart(String kode, String nama, String kategori, int stok, double hargaJual, double hargaModal) {
        this.kode = kode;
        this.nama = nama;
        this.kategori = kategori;
        this.stok = stok;
        this.hargaJual = hargaJual;
        this.hargaModal = hargaModal;
    }

    public String getKode() { return kode; }
    public void setKode(String kode) { this.kode = kode; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }

    public int getStok() { return stok; }
    public void setStok(int stok) { this.stok = stok; }

    public double getHargaJual() { return hargaJual; }
    public void setHargaJual(double hargaJual) { this.hargaJual = hargaJual; }

    public double getHargaModal() { return hargaModal; }
    public void setHargaModal(double hargaModal) { this.hargaModal = hargaModal; }

    @Override
    public String toString() {
        // Used by the ComboBox in the Cashier screen
        return kode + " - " + nama + " (Stok: " + stok + ")";
    }
}
