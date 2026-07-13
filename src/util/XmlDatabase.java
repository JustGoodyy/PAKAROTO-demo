package util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * util.XmlDatabase<T>
 * -------------------
 * This is the "No SQL, .xml files + XStream" data layer required by the spec.
 * Every feature (Inventory, Cashier, Reports...) talks to its .xml file only
 * through this class, which bridges plain ArrayList<T> (Model layer) to disk.
 *
 * Uses xstream.toXML()/fromXML() (NOT createObjectOutputStream/InputStream,
 * which wraps content in an extra <object-stream> element) so the file on
 * disk is a plain <list>...</list> — human-readable and hand-editable.
 *
 * Usage:
 *   XmlDatabase<SparePart> db = new XmlDatabase<>("data/sparepart.xml", SparePart.class);
 *   ArrayList<SparePart> list = db.loadAll();
 *   db.saveAll(list);
 */
public class XmlDatabase<T> {

    private final XStream xstream;
    private final String filePath;

    public XmlDatabase(String filePath, Class<T> clazz) {
        this.filePath = filePath;
        this.xstream = new XStream(new StaxDriver());
        // XStream 1.4.18+ requires explicit permissions
        xstream.addPermission(AnyTypePermission.ANY);
        // Nicer tag names in the generated XML, e.g. <sparepart> instead of the FQCN
        xstream.alias(clazz.getSimpleName().toLowerCase(), clazz);
        xstream.alias("list", ArrayList.class);
    }

    /** Reads the whole file into memory as an ArrayList (empty list if file is missing). */
    @SuppressWarnings("unchecked")
    public ArrayList<T> loadAll() {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (FileReader reader = new FileReader(file)) {
            Object result = xstream.fromXML(reader);
            return result != null ? (ArrayList<T>) result : new ArrayList<>();
        } catch (Exception e) {
            System.err.println("[XmlDatabase] Failed to read " + filePath + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /** Overwrites the file with the full in-memory list ("save on every CRUD action"). */
    public void saveAll(ArrayList<T> data) {
        try {
            File file = new File(filePath);
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }
            try (FileWriter writer = new FileWriter(file)) {
                xstream.toXML(data, writer);
            }
        } catch (Exception e) {
            System.err.println("[XmlDatabase] Failed to write " + filePath + ": " + e.getMessage());
        }
    }
}
