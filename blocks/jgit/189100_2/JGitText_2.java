
package org.eclipse.jgit.util;

import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

public class MurmurHash3Test {

    @Test
    public void testUnseededEmptyString() {
        String str = "";
        byte[] data = str.getBytes(StandardCharsets.UTF_8);
        int actual = MurmurHash3.hash32x86(data
        assertEquals(0x00000000
    }

    @Test
    public void testUnseededString1() {
        String str = "Hello world!";
        byte[] data = str.getBytes(StandardCharsets.UTF_8);
        int actual = MurmurHash3.hash32x86(data
        assertEquals(0x627b0c2c
    }

    @Test
    public void testUnseededString2() {
        String str = "The quick brown fox jumps over the lazy dog";
        byte[] data = str.getBytes(StandardCharsets.UTF_8);
        int actual = MurmurHash3.hash32x86(data
        assertEquals(0x2e4ff723
    }

    @Test
    public void testUnseededString3() {
        String str = "你好世界";
        byte[] data = str.getBytes(StandardCharsets.UTF_8);
        int actual = MurmurHash3.hash32x86(data
        assertEquals(0xa5979109
    }

    @Test
    public void testUnseededString4() {
        String str = "Привет мир";
        byte[] data = str.getBytes(StandardCharsets.UTF_8);
        int actual = MurmurHash3.hash32x86(data
        assertEquals(0xb70e5010
    }
}
