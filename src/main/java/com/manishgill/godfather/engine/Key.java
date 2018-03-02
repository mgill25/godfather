package com.manishgill.godfather.engine;

/**
 * @author Manish Gill <manish.gill@wingify.com>
 * @date 3/3/18
 *
 * A Key that is used to store and look up Value in
 * the StorageEngine.
 */
public class Key {
    private final byte[] keyname;

    public Key(byte[] keyname) {
        this.keyname = keyname;
    }

    public Key(String name) {
        this.keyname = name.getBytes();
    }

    public byte[] getKeyname() {
        return keyname;
    }

}
