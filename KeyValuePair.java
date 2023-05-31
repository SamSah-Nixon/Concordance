/*
 * Class that stores two values together that can be retrived and changed
 * Sam S-N
 * Created: 5/18/23
 * Last Modified: 5/31/23
 */
public class KeyValuePair<K extends Comparable<? super K>, E> implements Comparable<KeyValuePair<K, E>> {
    private K key;
    private E info;

    public KeyValuePair() {
        key = null;
        info = null;
    }

    public KeyValuePair(K theKey, E theInfo) {
        key = theKey;
        info = theInfo;
    }

    public K getFirst() {
        return key;
    }

    public E getSecond() {
        return info;
    }

    public void setFirst(K newKey) {
        key = newKey;
    }

    public void setSecond(E newInfo) {
        info = newInfo;
    }

    public String toString() {
        return "(" + key + ":" + info + ")";
    }

    public int compareTo(KeyValuePair<K, E> o) {
        return key.compareTo(o.key);
    }

}


