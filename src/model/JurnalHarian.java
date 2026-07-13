package model;

import java.time.LocalDate;

/**
 * DTO: JurnalHarian ("Daily Journal")
 * Built on the fly by ReportController by merging Nota (income) and
 * Pengeluaran (expense) records into one chronological table for the
 * General Ledger TableView.
 *
 * UPDATE: now also persisted to its own file, data/jurnal.xml, via
 * XmlDatabase<JurnalHarian>, every time the ledger is (re)built. This is a
 * derived/cache copy — Nota and Pengeluaran remain the source of truth,
 * jurnal.xml is just a saved snapshot of the merged ledger for the last
 * generated report range. A no-arg constructor is required by XStream.
 */
public class JurnalHarian {
    private LocalDate tanggal;
    private String keterangan;
    private String jenis;       // "PEMASUKAN" or "PENGELUARAN"
    private double pemasukan;   // > 0 only when jenis == PEMASUKAN
    private double pengeluaran; // > 0 only when jenis == PENGELUARAN
    private String id;          // idNota (PEMASUKAN) or idPengeluaran (PENGELUARAN)

    public JurnalHarian() {}

    public JurnalHarian(LocalDate tanggal, String keterangan, String jenis,
                         double pemasukan, double pengeluaran, String id) {
        this.tanggal = tanggal;
        this.keterangan = keterangan;
        this.jenis = jenis;
        this.pemasukan = pemasukan;
        this.pengeluaran = pengeluaran;
        this.id = id;
    }

    public LocalDate getTanggal() { return tanggal; }
    public String getKeterangan() { return keterangan; }
    public String getJenis() { return jenis; }
    public double getPemasukan() { return pemasukan; }
    public double getPengeluaran() { return pengeluaran; }
    public String getId() { return id; }
}
