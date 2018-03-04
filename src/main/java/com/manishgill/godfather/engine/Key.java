package com.manishgill.godfather.engine;

/**
 * @author Manish Gill <manish.gill@wingify.com>
 * @date 3/3/18
 *
 * A Key that is used to store and look up Value in
 * the StorageEngine.
 */
public class Key extends DataBuffer {

    public Key(byte[] keyname) {
        super(keyname);
    }

    public Key(String name) {
        super(name);
    }
}
