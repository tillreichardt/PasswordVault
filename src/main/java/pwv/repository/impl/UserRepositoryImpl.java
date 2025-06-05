package pwv.repository.impl;

import pwv.domain.User;
import pwv.repository.CRUDRepository;
import pwv.util.DatabaseConnector;
import pwv.util.MariaDBConnector;
import pwv.util.QueryResult;

import java.util.List;
import java.util.ArrayList;

public class UserRepositoryImpl implements CRUDRepository<User> {
    private DatabaseConnector db = new DatabaseConnector();
    private MariaDBConnector dbConn;

    public UserRepositoryImpl() {
        dbConn = db.getDbConn();
    }

    /**
     * Saves a new user to the database.
     * @param user the User object to save
     */
    @Override
    public void save(User user) {
        String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
        int id = dbConn.executePrepared(sql, user.getName(), user.getEmail(), user.getPassword());
        user.setId(id);
    }

    /**
     * Retrieves a user by their ID.
     * @param id the ID of the user to retrieve
     * @return the User object if found, null otherwise
     */
    @Override
    public User getById(int id) {
        String sql = "SELECT name, email, password FROM users WHERE id = ?";
        dbConn.executePrepared(sql, id);

        QueryResult qr = dbConn.getCurrentQueryResult();
        if (qr == null || qr.getRowCount() == 0) {
            return null; // No user found with the given ID
        }
        String[] row = qr.getData()[0];
        User user = new User(row[0], row[1], row[2]);
        user.setId(id);
        return user;
    }

    /**
     * Retrieves all users from the database.
     * @return a List of User objects
     */
    @Override
    public List<User> getAll() {
        String sql = "SELECT name, email, password FROM users";
        dbConn.executePrepared(sql);

        QueryResult qr = dbConn.getCurrentQueryResult();
        List<User> users = new ArrayList<>();

        for (String[] row : qr.getData()) {
            users.add(new User(row[0], row[1], row[2]));
        }
        return users;
    }

    /**
     * Updates a user in the database.
     * @param user the User object containing updated information
     * @param id the ID of the user to update
     */
    @Override
    public void update(User user, int id){
        String sql = "UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?";
        dbConn.executePrepared(sql, user.getName(), user.getEmail(), user.getPassword(), id);
    }


    /**
     * Deletes a user from the database by their ID.
     * @param id the ID of the user to delete
     */
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        dbConn.executePrepared(sql, id);
    }


    /**
     * Retrieves a user by their email address.
     * @param email the email address of the user to retrieve
     * @return the User object if found, null otherwise
     */
    public User getUserByEmail(String email) {
        String sql = "SELECT id, name, email, password FROM users WHERE email = ?";
        dbConn.executePrepared(sql, email);

        QueryResult qr = dbConn.getCurrentQueryResult();
        if (qr == null || qr.getRowCount() == 0) {
            return null; // No user found with the given email
        }
        String[] row = qr.getData()[0];
        User newUser = new User(row[1], row[2], row[3]);
        newUser.setId(Integer.parseInt(row[0]));
        return newUser; 
    }

    public static void main(String[] args) {
        UserRepositoryImpl userRepo = new UserRepositoryImpl();
        User user = new User("adsf", "till@asd.de", "password123");
        userRepo.save(user);
        System.out.println("User saved: " + user);
        User readUser = userRepo.getById(user.getId());
        System.out.println(readUser);
    }
}