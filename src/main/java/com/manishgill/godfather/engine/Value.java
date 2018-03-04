package com.manishgill.godfather.engine;

/**
 * @author Manish Gill <manish.gill@wingify.com>
 * @date 3/3/18
 *
 * Represents a Value that is stored using a StorageEngine.
 * https://docs.oracle.com/javase/tutorial/essential/concurrency/imstrat.html
 */
public class Value extends DataBuffer {

    public Value(byte[] value) {
        super(value);
    }

    public Value(String value) {
        super(value);
    }
}
