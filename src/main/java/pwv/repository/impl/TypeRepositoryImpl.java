package pwv.repository.impl;

import pwv.domain.Type;
import pwv.repository.CRUDRepository;
import pwv.util.DatabaseConnector;
import pwv.util.MariaDBConnector;
import pwv.util.QueryResult;

import java.util.List;
import java.util.ArrayList;


public class TypeRepositoryImpl implements CRUDRepository<Type> {
    private DatabaseConnector db = new DatabaseConnector();
    private MariaDBConnector dbConn;

    public TypeRepositoryImpl() {
        dbConn = db.getDbConn();
    }

    /**
     * Saves a new type to the database.
     * @param type the Type object to save
     */
    @Override
    public void save(Type type) {
        String sql = "INSERT INTO types (id, name) VALUES (?, ?)";
        int id = dbConn.executePrepared(sql, type.getId(), type.getName());
        type.setId(id);
    }

    /**
     * Retrieves a type by its ID.
     * @param id the ID of the type to retrieve
     * @return the Type object if found, null otherwise
     */
    @Override
    public Type getById(int id) {
        String sql = "SELECT name FROM types WHERE id = ?";
        dbConn.executePrepared(sql, id);

        QueryResult qr = dbConn.getCurrentQueryResult();
        if (qr == null || qr.getRowCount() == 0) {
            return null; // No type found with the given ID
        }
        String[] row = qr.getData()[0];
        Type type = new Type(row[0]);
        type.setId(id);
        return type;
    }

    /**
     * Retrieves all types from the database.
     * @return a List of Type objects
     */
    @Override
    public void update(Type type, int id) {
        String sql = "UPDATE types SET name = ? WHERE id = ?";
        dbConn.executePrepared(sql, type.getName(), id);
    }

    /**
     * Deletes a type from the database.
     * @param id the ID of the type to delete
     */
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM types WHERE id = ?";
        dbConn.executePrepared(sql, id);
    }

    /**
     * Retrieves all types from the database.
     * @return a List of Type objects
     */
    @Override
    public List<Type> getAll() {
        String sql = "SELECT name FROM users";
        dbConn.executePrepared(sql);

        QueryResult qr = dbConn.getCurrentQueryResult();
        List<Type> types = new ArrayList<>();

        for (String[] row : qr.getData()) {
            types.add(new Type(row[0]));
        }
        return types; 
    }
    
    public Type getTypeByName(String name) {
        String sql = "SELECT id FROM types WHERE name = ?";
        dbConn.executePrepared(sql, name);

        QueryResult qr = dbConn.getCurrentQueryResult();
        if (qr == null || qr.getRowCount() == 0) {
            return null; // No type found with the given name
        }
        String[] row = qr.getData()[0];
        Type type = new Type(name);
        type.setId(Integer.parseInt(row[0]));
        return type;
    }
    /*
    public static void main(String[] args) {
        try{
            Thread.sleep(1);
            TypeRepositoryImpl typeRepo = new TypeRepositoryImpl();
            Type type = new Type("TestType");
            typeRepo.save(type);
            System.out.println("Saved Type: " + type);
            
            System.out.println("Waiting for 5 seconds before retrieving the type...");
            Thread.sleep(5000); // Simulate some delay
            Type retrievedType = typeRepo.getById(type.getId());
            System.out.println("Retrieved Type: " + retrievedType);
            
            System.out.println("Waiting for 5 seconds before updating the type...");
            Thread.sleep(5000); // Simulate some delay
            retrievedType.setName("UpdatedType");
            typeRepo.update(retrievedType, retrievedType.getId());
            System.out.println("Updated Type: " + typeRepo.getById(retrievedType.getId()));
            
            System.out.println("Waiting for 5 seconds before deleting the type...");
            Thread.sleep(5000); // Simulate some delay
            typeRepo.delete(retrievedType.getId());
            System.out.println("Deleted Type: " + typeRepo.getById(retrievedType.getId())); // Simulate some delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
        */
}
