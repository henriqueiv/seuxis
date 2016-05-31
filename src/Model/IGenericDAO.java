package Model;

/**
 *
 * @author valcanaia
 * @param <T> is the Model which implements PersistentEntity to be persisted
 */
public interface IGenericDAO<T> {

    public void save(T t);

    public void delete(T t);

    public T getById(Integer id);
    
    public Integer getMaxId();

}
