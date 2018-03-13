package TradeDesk.impl;

import TradeDesk.NSetAssociativeCache;
import TradeDesk.models.ReplacementAlgorithm;

public class Client extends NSetAssociativeCache {
    // Initializes N-Set Associative cache
    public Client(int numSets, int numEntry) {
        super(numSets, numEntry);
    }

    // Initializes N-Set Associative cache with specific ReplacementAlgorithm
    public Client(int numSets, int numEntry, ReplacementAlgorithm replacementAlgorithm) {
        super(numSets, numEntry, replacementAlgorithm);
    }

    // Default Custom Algo method to evict cache overridden from LRU to FIFO
    @Override
    public int getCustomReplacementAlgoIdx(int startIdx, int endIdx) {
        int evictIdx = startIdx;
        long timestamp = cacheArray[startIdx].getCreatedTimestamp();
        for (int i = startIdx; i <= endIdx; i++) {
            long blockTimestamp = cacheArray[i].getCreatedTimestamp();
            if (timestamp > blockTimestamp) {
                evictIdx = i;
                timestamp = blockTimestamp;
            }
        }
        return evictIdx;
    }
}
