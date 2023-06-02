/*
 * Sam SN
 * Map implemented using a doubly-linked list
 * Created: 5/18/2023
 * Modified: 5/31/2023
 */
import java.util.*;

public class MyListMap<K extends Comparable<? super K>, E> implements MyMap<K,E>, Iterable<KeyValuePair<K, E>>
{
  private ListNode<K, E> dummy;
  private ListNode<K, E> cursor;
  private int size;

  public MyListMap()
  {
      dummy = new ListNode<K,E>();
      cursor = dummy;
      size = 0;
  }

  public MyListMap(MyMap<K, E> otherMap)
  {
      this();
      for(Iterator<KeyValuePair<K, E>> it = otherMap.iterator(); it.hasNext(); )
      {
          KeyValuePair<K, E> pair = it.next();
          K key   = pair.getFirst();
          E info  = pair.getSecond();
          put(key, info);
      }
  }
  
  private static class ListNode<K, E>
  {
    private K key;
    private E info;
    private ListNode<K,E> prev;
    private ListNode<K,E> next;

    public ListNode()
    { prev = next = null; }

    public ListNode(K k, E obj, ListNode<K,E> previousNode, ListNode<K,E> nextNode)
    {
       key  = k;
       info = obj;
       prev = previousNode;
       next = nextNode;
    }

  }
 
  /**
   * Deletes all keys and values from the map.
   */
  public void clear() {
    dummy.next = null;
    size = 0;
  }

  /**
   * Returns the number of keys in the map.
   * @return the number of keys in the map.
   */
  public int size() {
    return size;
  }

  /**
   * Checks if the map is empty.
   * @return true if the map is empty, false if it is not.
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * Finds if a key is in the map.
   * @param key key that is to be searched for in this map.
   * @return true if the key is in the map, false if it is not.
   */
  public boolean contains(K key) {
    for(KeyValuePair<K, E> pair : this)
    {
      if(pair.getFirst().equals(key))
        return true;
    }
    return false;
  }

  /**
   * Removes the key and value of the given key
   * @param key key that is to be removed from this map.
   * @return true if the key is successfully removed, false if it does not exist.
   */
  public boolean remove(K key) {
    cursor = dummy;
    while(cursor.next != null)
    {
      if(cursor.next.key.equals(key))
      {
        cursor.next = cursor.next.next;
        if(cursor.next != null)
          cursor.next.prev = cursor;
        size--;
        return true;
      }
      cursor = cursor.next;
    }
    return false;
  }

  /**
   * Create and return an iterator for this map.
   * @return the iterator for this map.
   */
  public Iterator<KeyValuePair<K, E>> iterator() {
    return new Iterator<KeyValuePair<K, E>>()
    {
      private ListNode<K, E> current = dummy;

      public boolean hasNext()
      {
        return current.next != null;
      }

      public KeyValuePair<K, E> next()
      {
        if(!hasNext())
          throw new NoSuchElementException();
          current = current.next;
          return new KeyValuePair<K, E>(current.key, current.info);
      }
    };
  }

  /**
   * Return the value that is associated with the given key in this map.
   * @param key whose associated value is to be returned.
   * @return the value associated with the given key\
   * @throws KeyError if the key is not in the map.
   */
  public E get(K key) throws KeyError{
    for(KeyValuePair<K, E> pair : this)
    {
      if(pair.getFirst().equals(key))
        return pair.getSecond();
    }
    throw new KeyError("Key not found");
  }

  /**
   * Change the value associated with the given key to the given value.
   * @param key key that is to be associated with the key in this map.
   * @param info new value that is to be associated with the key in this map.
   * @return true if the key is present in the map, false if it is not.
   */
  public boolean set(K key, E info) {
    cursor = dummy;
    while(cursor.next != null)
    {
      cursor = cursor.next;
      if(cursor.key.equals(key))
      {
        cursor.info = info;
        return true;
      }
    }
    return false;
  }

  /**
   * Inserts key k in the map with the value, return true. If key K exists int he map return false.
   * @param key key that is to be associated with the key in this map.
   * @param info value that is to be associated with the key in this map.
   * @return true if the key is successfully inserted, false if it already exists.
   */
  public boolean put(K key, E info) {
    if(contains(key))
      return false;
    ListNode<K, E> newNode = new ListNode<K, E>(key, info, dummy, dummy.next);
    dummy.next = newNode;
    if(dummy.next.next != null)
      dummy.next.next.prev = newNode;
    size++;
    return true;
  }

  /**
   * Returns an ArrayList of all the keys in the map.
   * @return an ArrayList of all the keys in the map.
   */
  public ArrayList<K> keys()
  {
    ArrayList<K> keys = new ArrayList<K>();
    for(KeyValuePair<K, E> pair : this)
    {
      keys.add(pair.getFirst());
    }
    return keys;
  }

  /**
   * Returns an ArrayList of all the values in the map.
   * @return an ArrayList of all the values in the map.
   */
  public ArrayList<E> values()
  {
    ArrayList<E> values = new ArrayList<E>();
    for(KeyValuePair<K, E> pair : this)
    {
      values.add(pair.getSecond());
    }
    return values;
  }

  /**
   * Returns an ArrayList of all the items in the map.
   * @return an ArrayList of all the items in the map.
   */
  public ArrayList<KeyValuePair<K, E>> items(){
    ArrayList<KeyValuePair<K, E>> items = new ArrayList<KeyValuePair<K, E>>();
    for(KeyValuePair<K, E> pair : this)
    {
      items.add(pair);
    }
    return items;
  }

  /**
   * Returns map of this object and M2 merged together.
   * @param M2 map to be merged with this map.
   * @return the merged map.
   */
  public MyListMap<K,E> merge(MyListMap<K,E> M2){
    MyListMap<K,E> merged = this;
    for(KeyValuePair<K, E> pair : M2)
    {
      merged.put(pair.getFirst(), pair.getSecond());
    }
    return merged;
  }

  /**
   * Returns a string representation of the Map.
   * @return a string representation of the Map.
   */
  public String toString()
  {
    if(isEmpty())
      return "[]";
    StringBuilder items = new StringBuilder();
    items.append("[");
    for(KeyValuePair<K, E> pair : this)
    {
      items.append(pair.toString()+",\n");
    }
    return items.substring(0, items.length()-2)+"]";
  }

}
