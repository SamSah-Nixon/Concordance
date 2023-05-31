import java.util.*;
public interface MyMap<K extends Comparable<? super K>, E> extends Iterable<KeyValuePair<K, E>>
{

  /**
  * Remove all the values from this collection so that it becomes empty.
  */
  public void clear();

  /**
  *@return the number of objects in this collection ;
  */
  public int size();

  /**
  * If this collection is empty, return true otherwise return false.
  * @return A boolean indicating if this collection is or is not empty.
  */
  public boolean isEmpty();


  /**
  * If this collection is contains the given value return true, otherwise return false.
  * @param key value being searched for in this collection.
  * @return A boolean indicating if this collection contains the value.
  */
  public boolean contains(K key);


  /**
  * Remove the given item from this collection. If the item is not a member of this
  * collection the operation fails.
  * @return true if the value is removed, false if it is not removed.
  */
  public boolean remove(K key);


  /**
  * Create and return an iterator for this collection.
  * @return an iterator for this collection.
  */
  public Iterator< KeyValuePair<K, E> > iterator();

  /**
  * Return the Object that is associated with the given key in this map.
  * @param key whose associated value is to be returned.
  * @return the Object associated with the given key if the key is present in the map,
  * null if the key is not in the map.
  * @throws KeyError if the key is not in the map.
  */
  public E get(K key) throws KeyError;

  /**
  * Change the value associated with the given key to the given value.
  * @param key and value that is to be associated with the key in this map.
  * @return true if the key is present in the map, false if it is not.
  */
  public boolean set(K key, E info);


  /**
  * Insert a key and value into this map if the key is not already present.
  * @param key and value the objects being added to this map
  * @return true if the object is added, false if it is not added.
  */
  public boolean put(K key, E info);
}
