package com.manishgill.godfather.engine.rocksdb;

import com.manishgill.godfather.engine.Key;
import com.manishgill.godfather.engine.StorageEngine;
import com.manishgill.godfather.engine.Value;
import com.manishgill.godfather.exceptions.KeyError;
import org.rocksdb.*;
import org.rocksdb.util.SizeUnit;

import java.util.Arrays;

/**
 * @author Manish Gill <manish.gill@wingify.com>
 * @date 3/5/18
 */
public class RocksDBEngine implements StorageEngine {
    private RocksDB store;

    public RocksDBEngine() {
        try {
            final String storagePath = "/tmp/godfather_rocks.db";
            store = setupEngine(storagePath);
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
    }

    // TODO: Avoid multiple setups in same run
    private static RocksDB setupEngine(String storagePath) throws RocksDBException {
        RocksDB.loadLibrary();
        try (final Options options = new Options();
             final RateLimiter rateLimiter = new RateLimiter(10000000,10000, 10);
             final Filter bloomFilter = new BloomFilter(10);
             final ReadOptions readOptions = new ReadOptions().setFillCache(false)) {
            // Configure the options
            try {
                options.setCreateIfMissing(true)
                        .setWriteBufferSize(8 * SizeUnit.KB)
                        .setMaxWriteBufferNumber(3)
                        .setMaxBackgroundCompactions(10)
                        .setCompressionType(CompressionType.SNAPPY_COMPRESSION)
                        .setCompactionStyle(CompactionStyle.UNIVERSAL);
                options.setMemTableConfig(
                        new HashSkipListMemTableConfig()
                                .setHeight(4)
                                .setBranchingFactor(4)
                                .setBucketCount(2000000));
                options.setMemTableConfig(
                        new HashLinkedListMemTableConfig()
                                .setBucketCount(100000));
                options.setMemTableConfig(
                        new VectorMemTableConfig().setReservedSize(10000));
                options.setTableFormatConfig(new PlainTableConfig());

                options.setMemTableConfig(new SkipListMemTableConfig());
                assert (options.memTableFactoryName().equals("SkipListFactory"));

                // Plain-Table requires mmap read
                options.setAllowMmapReads(true);
                assert (options.tableFactoryName().equals("PlainTable"));

                options.setRateLimiter(rateLimiter);

                final BlockBasedTableConfig table_options = new BlockBasedTableConfig();
                table_options.setBlockCacheSize(64 * SizeUnit.KB)
                        .setFilter(bloomFilter)
                        .setCacheNumShardBits(6)
                        .setBlockSizeDeviation(5)
                        .setBlockRestartInterval(10)
                        .setCacheIndexAndFilterBlocks(true)
                        .setHashIndexAllowCollision(false)
                        .setBlockCacheCompressedSize(64 * SizeUnit.KB)
                        .setBlockCacheCompressedNumShardBits(10);

                assert (table_options.blockCacheSize() == 64 * SizeUnit.KB);
                assert (table_options.cacheNumShardBits() == 6);
                assert (table_options.blockSizeDeviation() == 5);
                assert (table_options.blockRestartInterval() == 10);
                assert (table_options.cacheIndexAndFilterBlocks() == true);
                assert (table_options.hashIndexAllowCollision() == false);
                assert (table_options.blockCacheCompressedSize() == 64 * SizeUnit.KB);
                assert (table_options.blockCacheCompressedNumShardBits() == 10);

                options.setTableFormatConfig(table_options);
                assert (options.tableFactoryName().equals("BlockBasedTable"));

            } catch (final IllegalArgumentException e) {
                System.out.println("Got exception: " + e);
            }
            return RocksDB.open(options, storagePath);
        }
    }

    @Override
    public Boolean storeData(Key k, Value val) {
        try {
            store.put(k.getBytes(), val.getBytes());
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Value retrieveData(Key k) throws KeyError {
        try {
            final byte[] rawValue = store.get(k.getBytes());
            if (rawValue == null) {
                throw new KeyError("Could not find value for the given key");
            }
            return new Value(rawValue);
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean contains(Key k) {
        // TODO: Why does this require a StringBuilder as a second param?
        return store.keyMayExist(k.getBytes(), new StringBuilder());
    }

    @Override
    public Boolean deleteData(Key k) {
        try {
            store.delete(k.getBytes());
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
        return null;
    }
}
