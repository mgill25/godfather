package com.manishgill.godfather.engine.memory;

import com.manishgill.godfather.engine.Key;
import com.manishgill.godfather.engine.StorageEngine;
import com.manishgill.godfather.engine.Value;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Manish Gill <manish.gill@wingify.com>
 * @date 3/3/18
 *
 * In-memory storage engine will never persist data
 * beyond the life-cycle of the server. Data is lost as soon
 * as godfather dies, or is shut down.
 */

public class InMemoryEngine implements StorageEngine {
    private ConcurrentHashMap<Key, Value> store;

    public InMemoryEngine() {
        this.store = new ConcurrentHashMap<>();
    }

    public Boolean storeData(Key k, Value val) {
        store.put(k, val);
        return true;
    }

    public Value retrieveData(Key k) {
        return store.get(k);
    }

    public Boolean deleteData(Key k) {
        if (store.containsKey(k)) {
            store.remove(k);
            return true;
        }
        return false;
    }
}
