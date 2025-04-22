
package org.eclipse.jgit.internal.storage.commitgraph;

import java.nio.charset.StandardCharsets;

import org.eclipse.jgit.lib.BloomFilter;
import org.eclipse.jgit.util.MurmurHash3;

public class ChangedPathFilter implements BloomFilter {

    static ChangedPathFilter TRUNCATED_LARGE_FILTER = new ChangedPathFilter(
            new byte[] { (byte) 0xFF }

    static ChangedPathFilter TRUNCATED_EMPTY_FILTER = new ChangedPathFilter(
            new byte[1]

    private final byte[] data;

    private final int numHashes;

    ChangedPathFilter(int size
        this.data = new byte[size];
        this.numHashes = numHashes;
    }

    ChangedPathFilter(byte[] data
        this.data = data;
        this.numHashes = numHashes;
    }

    static Key newBloomKey(String data
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);

        int seed0 = 0x293ae76f;
        int seed1 = 0x7e646e2c;

        int hash0 = MurmurHash3.hash32x86(bytes
        int hash1 = MurmurHash3.hash32x86(bytes

        int[] hashes = new int[numHashes];
        for (int i = 0; i < hashes.length; i++) {
            hashes[i] = hash0 + i * hash1;
        }
        return new Key(hashes);
    }

    @Override
    public boolean contains(Key key) {
        long mod = data.length * Byte.SIZE;
        int[] hashes = key.getHashes();

        for (int i = 0; i < numHashes; i++) {
            long hashMod = Integer.toUnsignedLong(hashes[i]) % mod;
            long blockPos = hashMod / Byte.SIZE;
            if ((data[(int) blockPos] & getBitMask((int) hashMod)) == 0) {
                return false;
            }
        }
        return true;
    }

    byte[] getData() {
        return data;
    }

    long getSize() {
        return data.length;
    }

    void addKey(Key key) {
        long mod = data.length * Byte.SIZE;

        int[] hashes = key.getHashes();
        for (int i = 0; i < hashes.length; i++) {
            long hashMod = Integer.toUnsignedLong(hashes[i]) % mod;
            long blockPos = hashMod / Byte.SIZE;

            data[(int) blockPos] |= getBitMask((int) hashMod);
        }
    }

    private byte getBitMask(int pos) {
        return (byte) (((byte) 1) << (pos & (Byte.SIZE - 1)));
    }
}
