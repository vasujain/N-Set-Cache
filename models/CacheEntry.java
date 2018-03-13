package TradeDesk.models;

public class CacheEntry {

    public int tag;
    public Object data;
    public long timestamp;
    public long createdTimestamp;
    public boolean isEmpty;

    public CacheEntry(){
        this.tag = 0;
        this.data = "null";
        this.timestamp = 0;
        this.createdTimestamp = 0;
        this.isEmpty = false;
    }

    public CacheEntry(int tag, Object data, long timestamp,long createdTimestamp, boolean isEmpty){
        this.tag = tag;
        this.data = data;
        this.timestamp = timestamp;
        this.createdTimestamp = createdTimestamp;
        this.isEmpty = isEmpty;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(long createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }
}
