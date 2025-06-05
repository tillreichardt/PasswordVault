package pwv.service;

import pwv.domain.Entry;
import pwv.repository.impl.EntryRepositoryImpl;

public class EntryService {
    private final EntryRepositoryImpl entryRepo;
    
    public EntryService(EntryRepositoryImpl entryRepo) {
        this.entryRepo = entryRepo;
    }

    public Entry createEntry(int user_id, String title, String username, String secret_encrypted, String url, String notes, int type_id) {
        Entry entry = new Entry(title, username, secret_encrypted, url, notes, type_id);
        entryRepo.save(entry, user_id);
        return entry;
    }

    public void deleteEntry(int id) {
        Entry entry = entryRepo.getById(id);
        if (entry == null) {
            throw new IllegalArgumentException("Entry with ID '" + id + "' not found");
        }
        entryRepo.delete(id);
    }

    public Entry updateEntry(int id, String title, String username, String secret_encrypted, String url, String notes, int type_id) {
        Entry entry = entryRepo.getById(id);
        if (entry == null) {
            throw new IllegalArgumentException("Entry with ID '" + id + "' not found");
        }
        entry.setTitle(title);
        entry.setUsername(username);
        entry.setSecret_encrypted(secret_encrypted);
        entry.setUrl(url);
        entry.setNotes(notes);
        entry.setType_id(type_id);
        entryRepo.update(entry, id);
        return entry;
    }
}
