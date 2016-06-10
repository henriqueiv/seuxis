package models;

public interface IGenericDAO<T> {

    public void save(T t);

    public void delete(T t);

    public T getById(Integer id);
    
    public Integer getMaxId();

}
