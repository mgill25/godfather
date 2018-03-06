package com.manishgill.godfather.engine;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * @author Manish Gill <manish.gill@wingify.com>
 * @date 3/4/18
 */

public class DataBuffer implements Serializable {
    private ByteBuffer buffer;

    public DataBuffer(ByteBuffer buffer) {
        this.buffer = buffer;
        this.buffer.flip();
    }

    public DataBuffer(byte[] bytes) {
        buffer = ByteBuffer.allocate(bytes.length);
        buffer.put(bytes);
        buffer.flip();
    }

    public DataBuffer(String string) {
        byte[] bytes = string.getBytes();
        buffer = ByteBuffer.allocate(bytes.length);
        buffer.put(bytes);
        buffer.flip();
    }

    public byte[] getBytes() {
        final byte[] bytes = new byte[buffer.remaining()];
        // duplicate avoids original buffer consumption.
        buffer.duplicate().get(bytes);
        return bytes;
    }

    public String getPrintableBytes() {
        return Arrays.toString(this.getBytes());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || !(obj instanceof DataBuffer)) {
            return false;
        }

        DataBuffer secondBuffer = (DataBuffer) obj;
        // Should have same individual bytes.
        return Arrays.equals(
                this.getBytes(),
                secondBuffer.getBytes()
        );
    }

    @Override
    public int hashCode() {
        return buffer.hashCode();
    }
}
