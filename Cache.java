package TradeDesk;

public interface Cache {
    Object get(Object key);
    void put(Object key, Object value);
    int size();
    void clear();
}
