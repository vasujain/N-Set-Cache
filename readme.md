# Overview

## Requirements
Design and implement an N-way, Set-Associative cache, with the following features:
1. The cache itself is entirely in memory (i.e. it does not communicate with a backing store)
2. The client interface should be type-safe for keys and values and allow for both the keys and values to be of an arbitrary type (e.g., strings, integers, classes, etc.). For a given instance of a cache all keys must be the same type and all values must be the same type.
3. Design the interface as a library to be distributed to clients. Assume that the client doesn’t have source code to your library.
4. Provide LRU and MRU replacement algorithms
5. Provide a way for any alternative replacement algorithm to be implemented by the client and used by the cache.

## Example use case:
As an in-memory cache on an application server, storing data associated with user id, in order to avoid a database dip for every request.
