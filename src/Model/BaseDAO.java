package Model;

/**
 *
 * @author valcanaia
 */
public abstract class BaseDAO<T> implements IGenericDAO<T> {

    public void save() {
//        Class<?> c = this.getClass();
//        Field[] fields = c.getDeclaredFields();
//        for (Field field : fields) {
//            System.out.println(field.get);
//        }
    }

    public void delete(T t) { }
    
    public T getById(Integer id) {
        return null;
    }
    
    public Integer getMaxId() { return null; }
    
}
