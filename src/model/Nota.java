package model;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Model: Nota
 * A finalized service receipt. Persisted to /data/nota.xml.
 *
 * NOTE: Originally fully immutable once paid. Setters below were added so
 * the Financial Report's Ledger screen can offer a limited "Edit" (customer
 * name, date, mechanic fee) and a "Delete" (which restores stock). The list
 * of items itself is intentionally NOT editable here — changing item
 * quantities would require re-deducting/re-crediting stock line by line,
 * which is out of scope; delete + re-create the nota via Cashier for that.
 */
public class Nota {
    private String idNota;
    private LocalDate tanggal;
    private String namaCustomer;
    private double biayaJasaMekanik;
    private ArrayList<NotaItem> items;
    private double totalBayar;

    public Nota() {}

    public Nota(String idNota, LocalDate tanggal, String namaCustomer,
                double biayaJasaMekanik, ArrayList<NotaItem> items) {
        this.idNota = idNota;
        this.tanggal = tanggal;
        this.namaCustomer = namaCustomer;
        this.biayaJasaMekanik = biayaJasaMekanik;
        this.items = items;
        recomputeTotal();
    }

    public String getIdNota() { return idNota; }
    public LocalDate getTanggal() { return tanggal; }
    public String getNamaCustomer() { return namaCustomer; }
    public double getBiayaJasaMekanik() { return biayaJasaMekanik; }
    public ArrayList<NotaItem> getItems() { return items; }
    public double getTotalBayar() { return totalBayar; }

    public void setTanggal(LocalDate tanggal) { this.tanggal = tanggal; }
    public void setNamaCustomer(String namaCustomer) { this.namaCustomer = namaCustomer; }
    public void setBiayaJasaMekanik(double biayaJasaMekanik) { this.biayaJasaMekanik = biayaJasaMekanik; }

    /** Recalculates totalBayar from current items + biayaJasaMekanik. Call after any edit. */
    public void recomputeTotal() {
        double totalItems = 0;
        if (items != null) {
            for (NotaItem it : items) totalItems += it.getSubtotal();
        }
        this.totalBayar = totalItems + biayaJasaMekanik;
    }
}
