
package com.couchbase.client.core.util;

import com.couchbase.client.core.utils.UnsignedLEB128;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UnsignedLEB128SmokeTest {
    @Test
    public void testEncoding() {
        byte[] res = UnsignedLEB128.encode(555);
        int[] ires = new int[res.length];
        for (int i=0;i<res.length;i++) {
            ires[i] = res[i];
            if (ires[i] < 0) ires[i] += 256;
        }
        assertEquals("Array length mismatch", 2, res.length);
        assertEquals("4", Integer.toString(ires[0], 16));
        assertEquals("ab", Integer.toString(ires[1], 16));
    }

    @Test
    public void testDecoding() {
        int n = 555;
        byte[] encoded = UnsignedLEB128.encode(n);
        int res = UnsignedLEB128.decode(encoded);
        assertEquals(res, n);
    }
}
