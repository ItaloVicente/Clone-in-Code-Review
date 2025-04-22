
package org.eclipse.jgit.binarydiff;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class Checksum {

    private final Map<Long

    private static final int[] single_hash = {
        0x00000000
        0x508c0e37
        0x0a41a8bf
        0x4e4ef7f6
        0x1483517e
        0x440f5f49
        0x37c45b3d
        0x6b369a20
        0x2906a2fc
        0x798aaccb
        0x23470a43
        0x354510f2
        0x6f88b67a
        0x3f04b84d
        0x7d348091
        0x21c6418c
        0x520d45f8
        0x02814bcf
        0x584ced47
        0x1c43b20e
        0x468e1486
        0x16021ab1
        0x6a8a21e4
        0x3678e0f9
        0x7448d825
        0x24c4d612
        0x7e09709a
        0x0bfd137b
        0x5130b5f3
        0x01bcbbc4
        0x438c8318
        0x1f7e4205
        0x0f433f21
        0x5fcf3116
        0x0502979e
        0x410dc8d7
        0x1bc06e5f
        0x4b4c6068
        0x3887641c
        0x6475a501
        0x26459ddd
        0x76c993ea
        0x2c043562
    };

    public Checksum(SeekableSource source
        ByteBuffer byteBuffer = ByteBuffer.allocate(chunkSize * 2);
        int count = 0;
        while (true) {
            source.read(byteBuffer);
            byteBuffer.flip();
            if (byteBuffer.remaining() < chunkSize) {
                break;
            }
            while (byteBuffer.remaining() >= chunkSize) {
                long queryChecksum = calcChecksum(byteBuffer
                checksums.put(queryChecksum
            }
            byteBuffer.compact();
        }
    }

    private static long getUnsigned(int signed) {
        return signed >= 0 ? signed : 2 * (long) Integer.MAX_VALUE + 2 + signed;
    }

    public static long queryChecksum(ByteBuffer byteBuffer
        byteBuffer.mark();
        long sum = calcChecksum(byteBuffer
        byteBuffer.reset();
        return sum;
    }

    private static long calcChecksum(ByteBuffer byteBuffer
        long high = 0;
        long low = 0;
        for (int i = 0; i < len; i++) {
            low += getUnsigned(single_hash[byteBuffer.get() + 128]);
            high += low;
        }
        return ((high & 0xffff) << 16) | (low & 0xffff);
    }

    public static long incrementChecksum(long checksum
                                         int chunkSize) {
        long oldValue = getUnsigned(single_hash[out + 128]);
        long newValue = getUnsigned(single_hash[in + 128]);
        long low   = (((checksum) & 0xffff) - oldValue + newValue) & 0xffff;
        long high  = (((checksum) >> 16) - (oldValue * chunkSize) + low) & 0xffff;
        return (high << 16) | (low & 0xffff);
    }

    public long findChecksumIndex(long hash) {
        if (!checksums.containsKey(hash)) {
            return -1;
        }
        return checksums.get(hash);
    }

}
