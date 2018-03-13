###Goals
Goals of the program discussed during email/hackerrank:

Design and implement an N-way, Set-Associative cache, with the following features: 
1. Focus on is a clean design 
2. Optimal performance implement for the cache 
3. The execution of the replacement policies.  
4. functional out of the box for a client
5. Custom code easily pluggable for replacement policy without having to modify the actual cache code, 
6. The cache itself is entirely in memory (i.e. it does not communicate with a backing store)
7. The client interface should be type-safe for keys and values and allow for both the keys and values to be of an arbitrary type (e.g., strings, integers, classes, etc.). For a given instance of a cache all keys must be the same type and all values must be the same type.
8. Design the interface as a library to be distributed to clients. Assume that the client doesn’t have source code to your library.
9. Provide LRU and MRU replacement algorithms
10. Provide a way for any alternative replacement algorithm to be implemented by the client and used by the cache.

### Design Discussion
1. Broken into several modules/packages so code maintenance/changes is easy
2. Implementation is optimal as per the requirements
3. LRU/MRU replacement policies implemented in `NSetAssociativeCache.java`
4. functional out of the box for a client - `Client.Java` to interact
5. `Client.Java` overrides custom replacement policy to a FIFO policy
6. The cache itself is entirely in memory (i.e. it does not communicate with a backing store)
7. The client interface is type-safe for keys and values and allow keys/values to be of an Object type
8. the program can be bundled as a library to be distributed to clients. Even if client doesn't have source code, they can still work on extending it
9. Same as #3
10. Same as #5

### Project Structure
#### packages
##### impl 
contains:
 1. `Client` which is implementation for Client. That's where client can override replacement policy. A sample replacement is done here for `LRU` to `FIFO`.

##### models
contains:
 1. model for a `CacheEntry`
 2. model for a `ReplacementAlgorithm` which is an enum

##### utils
contains:
1. `Constants` which has constants used across codebase
2. `CryptUtil` which facilitates MD5 hash algorithm
3. `MapUtil` which facilitates conversion of byte array to int
3. `TimeUtil` which has method for converting time to long

#### files
1. `Cache interface`, defines methods for add/delete in cache, clear cache, cache size  
2. `NSetAssociativeCache`, implementation for `Cache interface` containing implementation of interface, constructor, evict algorithms like MRU, LRU, custom  

### Assumptions
1. Haven't written any tests
2. Client can be modified from FIFO to any other. To test, wrote FIFO for the client. 
