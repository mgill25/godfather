package com.manishgill.godfather.engine;

/**
 * @author Manish Gill <manish.gill@wingify.com>
 * @date 3/3/18
 *
 * Represents a Value that is stored using a StorageEngine.
 * https://docs.oracle.com/javase/tutorial/essential/concurrency/imstrat.html
 */
public class Value {
    private final byte[] data;

    public Value(byte[] value) {
        this.data = value;
    }

    public Value(String value) {
        this.data = value.getBytes();
    }

    public byte[] getData() {
        return data;
    }

}
