package com.manishgill.godfather.engine;

import com.manishgill.godfather.exceptions.KeyError;

/**
 * @author Manish Gill <manish.gill@wingify.com>
 * @date 3/3/18
 *
 * What should be the interface of a storage engine? What operations does it need to support?
 *      - storeData
 *      - retrieveData
 *      - deleteData
 * And the same operations in batch.
 */
public interface StorageEngine {

    public Boolean storeData(Key k, Value val);

    public Value retrieveData(Key k) throws KeyError;

    public Boolean deleteData(Key k);

}
