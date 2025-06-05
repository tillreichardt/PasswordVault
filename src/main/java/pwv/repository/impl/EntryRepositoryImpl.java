package pwv.repository.impl;

import pwv.domain.Entry;
import pwv.repository.CRUDRepository;
import pwv.util.DatabaseConnector;
import pwv.util.MariaDBConnector;
import pwv.util.QueryResult;

import java.util.List;
import java.util.ArrayList;

public class EntryRepositoryImpl implements CRUDRepository<Entry> {
    private DatabaseConnector db = new DatabaseConnector();
    private MariaDBConnector dbConn;

    public EntryRepositoryImpl() {
        dbConn = db.getDbConn();
    }

    /**
     * Saves a new entry to the database.
     * @param entry the Entry object to save
     */
    @Override
    public void save(Entry entry) {
        String sql = "INSERT INTO entries (title, username, secret_encrypted, url, notes, type_id) VALUES (?, ?, ?, ?, ?, ?)";
        int id = dbConn.executePrepared(sql, entry.getTitle(), entry.getUsername(), entry.getSecret_encrypted(), entry.getUrl(), entry.getNotes(), entry.getType_id());
        entry.setId(id);
    }

    public void save(Entry entry, int user_id) {
        String sql = "INSERT INTO entries (title, username, secret_encrypted, url, notes, type_id) VALUES (?, ?, ?, ?, ?, ?)";
        int id = dbConn.executePrepared(sql, entry.getTitle(), entry.getUsername(), entry.getSecret_encrypted(), entry.getUrl(), entry.getNotes(), entry.getType_id());
        entry.setId(id);
        String sql2 = "INSERT INTO user_entry_access (user_id, entry_id, permission_level) VALUES (?, ?, ?)";
        dbConn.executePrepared(sql2, user_id, id, "owner");
    }

    /**
     * Retrieves an entry by its ID.
     * @param id the ID of the entry to retrieve
     * @return the Entry object if found, null otherwise
     */
    @Override
    public Entry getById(int id) {
        String sql = "SELECT title, username, secret_encrypted, url, notes, type_id FROM entries WHERE id = ?";
        dbConn.executePrepared(sql, id);

        QueryResult qr = dbConn.getCurrentQueryResult();
        if (qr == null || qr.getRowCount() == 0) {
            return null; // No entry found with the given ID
        }
        String[] row = qr.getData()[0];
        Entry entry = new Entry(row[0], row[1], row[2], row[3], row[4], Integer.parseInt(row[5]));
        entry.setId(id);
        return entry;
    }

    /**
     * Retrieves all entries from the database.
     * @return a List of Entry objects
     */
    @Override
    public List<Entry> getAll() {
        String sql = "SELECT id, title, username, secret_encrypted, url, notes, type_id FROM entries";
        dbConn.executePrepared(sql);

        QueryResult qr = dbConn.getCurrentQueryResult();
        List<Entry> entries = new ArrayList<>();

        for (String[] row : qr.getData()) {
            Entry entry = new Entry(
                    row[1],
                    row[2],
                    row[3],
                    row[4],
                    row[5],
                    Integer.parseInt(row[6])
            );
            entry.setId(Integer.parseInt(row[0]));
            entries.add(entry);
        }
        return entries;
    }

    /**
     * Updates an entry in the database.
     * @param entry the Entry object containing updated information
     * @param id the ID of the entry to update
     */
    @Override
    public void update(Entry entry, int id) {
        String sql = "UPDATE entries SET title = ?, username = ?, secret_encrypted = ?, url = ?, notes = ?, type_id = ? WHERE id = ?";
        dbConn.executePrepared(sql, entry.getTitle(), entry.getUsername(), entry.getSecret_encrypted(), entry.getUrl(), entry.getNotes(), entry.getType_id(), id);
    }

    /**
     * Deletes an entry from the database by its ID.
     * @param id the ID of the entry to delete
     */
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM entries WHERE id = ?";
        dbConn.executePrepared(sql, id);
    }

    public static void main(String[] args) {
        EntryRepositoryImpl entryRepo = new EntryRepositoryImpl();
        Entry entry = new Entry("Sample Title", "Sample User", "encrypted_secret", "http://example.com", "Sample notes", 1);
        entryRepo.save(entry, 21);
        System.out.println("Entry saved: " + entry);
        Entry readEntry = entryRepo.getById(entry.getId());
        System.out.println(readEntry);
    }
}
