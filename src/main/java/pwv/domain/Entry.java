package pwv.domain;

public class Entry {
    private int id; 
    private String title;
    private String username;
    private String secret_encrypted;
    private String url; 
    private String notes;
    private int type_id;

    public Entry(String title, String username, String secret_encrypted, String url, String notes, int type_id) {
        this.title = title;
        this.username = username;
        this.secret_encrypted = secret_encrypted;
        this.url = url;
        this.notes = notes;
        this.type_id = type_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSecret_encrypted() {
        return secret_encrypted;
    }

    public void setSecret_encrypted(String secret_encrypted) {
        this.secret_encrypted = secret_encrypted;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", username='" + username + '\'' +
                ", secret_encrypted='" + secret_encrypted + '\'' +
                ", url='" + url + '\'' +
                ", notes='" + notes + '\'' +
                ", type_id=" + type_id +
                '}';
    }
}

