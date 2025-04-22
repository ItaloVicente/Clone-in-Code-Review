package com.couchbase.client.java.cluster;

import com.couchbase.client.java.bucket.BucketType;
import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultBucketSettingsTest {

    @Test
    public void shouldApplyDefaults() {
        BucketSettings settings = DefaultBucketSettings.create("mybucket");

        assertEquals(100, settings.quota());
        assertEquals(BucketType.COUCHBASE, settings.type());
        assertEquals("mybucket", settings.name());
        assertEquals("", settings.password());
        assertEquals(0, settings.port());
        assertEquals(0, settings.replicas());
        assertFalse(settings.indexReplicas());
        assertFalse(settings.enableFlush());
    }

    @Test
    public void shouldAllowToSetBucketType() {
        for (BucketType type : BucketType.values()) {
            assertEquals(type, DefaultBucketSettings.builder().type(type).build().type());
        }
    }

    @Test
    public void shouldAllowToEnableFlush() {
        assertTrue(DefaultBucketSettings.builder().enableFlush(true).build().enableFlush());
    }

}
