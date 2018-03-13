package TradeDesk;

import TradeDesk.utils.Constants;
import TradeDesk.utils.CryptUtil;
import TradeDesk.utils.TimeUtil;
import TradeDesk.models.CacheEntry;
import TradeDesk.models.ReplacementAlgorithm;
import com.sun.xml.internal.ws.handler.HandlerException;

import java.nio.charset.UnsupportedCharsetException;

public class NSetAssociativeCache implements Cache {
    private final int numSets;
    private final int numEntry;

    public final CacheEntry[] cacheArray;
    public final String replacementAlgo;

    // Constructors
    public NSetAssociativeCache(int numSets){
        this.numSets = numSets;
        this.numEntry = Constants.DIRECT_MAPPED_CACHE_NUM_ENTRY;
        this.cacheArray = new CacheEntry[this.numSets*this.numEntry];
        this.replacementAlgo = ReplacementAlgorithm.LRU.toString();
        clear();
    }

    public NSetAssociativeCache(int numSets, int numEntry) {
        this.numSets = numSets;
        this.numEntry = numEntry;
        this.cacheArray = new CacheEntry[this.numSets*this.numEntry];
        this.replacementAlgo = ReplacementAlgorithm.LRU.toString();
        clear();
    }

    public NSetAssociativeCache(int numSets, int numEntry, ReplacementAlgorithm replacementAlgorithm) {
        this.numSets = numSets;
        this.numEntry = numEntry;
        this.cacheArray = new CacheEntry[this.numSets*this.numEntry];
        this.replacementAlgo = replacementAlgorithm.name();
        clear();
    }

    public void put(Object key, Object value) {
        int intKey = CryptUtil.getHash(key);
        int startIdx = getstartIdx(intKey);
        int endIdx = getendIdx(startIdx);

        boolean isSingleEntryEmpty = false;
        int emptyIdx = 0;
        boolean isCacheUpdated = false;

        CacheEntry cacheEntry = new CacheEntry(intKey, value, TimeUtil.getCurrentTimestamp(), TimeUtil.getCurrentTimestamp(), false);

        for(int i=startIdx; i<=endIdx; i++) {
            // cacheArray-i is empty, update emptyIdx
            if(cacheArray[i].isEmpty && !isSingleEntryEmpty) {
                emptyIdx = i;
                isSingleEntryEmpty = true;
                continue;
            }

            if(cacheArray[i].getTag() == intKey) { //match
                cacheArray[i] = cacheEntry;
                isCacheUpdated = true;
                break;
            } else {
                continue;
            }
        }

        if(isSingleEntryEmpty) {
            cacheArray[emptyIdx] = cacheEntry;
            isCacheUpdated = true;
        }

        // evict if full
        if(!isSingleEntryEmpty && !isCacheUpdated) {
            int evictIdx = getEvictedItemIndex(replacementAlgo, startIdx, endIdx);
            cacheArray[evictIdx] = cacheEntry;
        }
    }

    public Object get(Object key) {
        int intKey = CryptUtil.getHash(key);
        int startIdx = getstartIdx(intKey);
        int endIdx = getendIdx(startIdx);

        Object value = null;

        //Loop through entire cache blocks
        for (int i = startIdx; i <= endIdx ; i++) {
            // if hit, retrive data & update timestamp.
            if (cacheArray[i].getTag() == intKey) {
                value = cacheArray[i].data;
                cacheArray[i].setTimestamp(TimeUtil.getCurrentTimestamp());
                break;
            }
        }
        // if Miss - Check MM to retrieve data -- implementation out of scoepe
        return value;
    }

    public int size() {
        return numSets * numEntry;
    }

    public void clear() {
        for(int i=0; i<cacheArray.length;i++) {
            cacheArray[i] = new CacheEntry();
        }
    }

    private int getEvictedItemIndex(String replacementAlgorithm, int startIdx, int endIdx) {
        if(ReplacementAlgorithm.LRU.toString().equals(replacementAlgorithm)) {
            return getLeastRecentlyUsedIdx(startIdx, endIdx);
        } else if(ReplacementAlgorithm.MRU.toString().equals(replacementAlgorithm)) {
            return getMostRecentlyUsedIdx(startIdx, endIdx);
        } else if (ReplacementAlgorithm.CUSTOM.toString().equals(replacementAlgorithm)) {
            return getCustomReplacementAlgoIdx(startIdx, endIdx);
        } else{
            throw new IllegalArgumentException("Illegal/wrong Replacement alogorithm called.");
        }
    }

    // Implementation for getting an Index to evict using Least Recently Used Approach
    private int getLeastRecentlyUsedIdx(int startIdx, int endIdx) {
        int evictIdx = startIdx;
        long timestamp = cacheArray[startIdx].getTimestamp();
        for(int i=startIdx; i<=endIdx; i++) {
            long blockTimestamp = cacheArray[i].getTimestamp();
            if(blockTimestamp<timestamp) {
                evictIdx = i;
                timestamp = blockTimestamp;
            }
        }
        return evictIdx;
    }

    // Implementation for getting an Index to evict using Most Recently Used Approach
    private int getMostRecentlyUsedIdx(int startIdx, int endIdx) {
        int evictIdx = startIdx;
        long timestamp = cacheArray[startIdx].getTimestamp();
        for(int i=startIdx; i<=endIdx; i++) {
            long blockTimestamp = cacheArray[i].getTimestamp();
            if(blockTimestamp>timestamp) {
                evictIdx = i;
                timestamp = blockTimestamp;
            }
        }
        return evictIdx;
    }

    // Implementation for getting an Index to evict using an Custom Approach
    // Defaulting to Least Recently Used Approach
    public int getCustomReplacementAlgoIdx(int startIdx, int endIdx) {
        return getLeastRecentlyUsedIdx(startIdx, endIdx);
    }

    private int getstartIdx(int idxSetKey) {
        return (idxSetKey % numSets) * numEntry;
    }

    private int getendIdx(int startIdx) {
        return startIdx + numEntry - 1;
    }
}
