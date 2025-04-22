package com.couchbase.client.java.transcoder;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TranscoderUtilsTest {

    @Test
    public void testHasCommonFlags() {
        assertFalse(TranscoderUtils.hasCommonFlags(4));
        assertTrue(TranscoderUtils.hasCommonFlags(4 << 24));
    }

    @Test
    public void testExtractCommonFlags() {
        assertEquals(4, TranscoderUtils.extractCommonFlags(4 << 24));
    }

    @Test
    public void testCreateCommonFlags() {
        assertEquals(2 << 24, TranscoderUtils.createCommonFlags(2));
    }

    @Test
    public void testHasJsonFlags() {
        assertTrue(TranscoderUtils.hasJsonFlags(0));
        assertTrue(TranscoderUtils.hasJsonFlags(2 << 24));
        assertFalse(TranscoderUtils.hasJsonFlags(1));
        assertFalse(TranscoderUtils.hasJsonFlags(4 << 24));
    }

}
