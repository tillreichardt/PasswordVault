package pwv.repository;
import java.util.List;


/**
 * CRUDRepository interface for basic CRUD operations.
 * @param <ContentType> the type of content to be managed by the repository
 */
public interface CRUDRepository <ContentType>{
    // create a new entity
    void save(ContentType content);

    // Read a entity by ID
    ContentType getById(int id);

    // Read all entities
    List<ContentType> getAll();

    // Update an existing entity
    void update(ContentType content, int id);

    // Delete an entity by ID
    void delete(int id);
}