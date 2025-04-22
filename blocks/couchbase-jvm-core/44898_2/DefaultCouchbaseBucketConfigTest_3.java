
package com.couchbase.client.core.config;

import com.couchbase.client.core.util.Resources;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import java.net.InetAddress;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DefaultCouchbaseBucketConfigTest {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    @Test
    public void shouldHavePrimaryPartitionsOnNode() throws Exception {
        String raw = Resources.read("config_with_mixed_partitions.json", getClass());
        CouchbaseBucketConfig config = JSON_MAPPER.readValue(raw, CouchbaseBucketConfig.class);

        assertTrue(config.hasPrimaryPartitionsOnNode(InetAddress.getByName("1.2.3.4")));
        assertFalse(config.hasPrimaryPartitionsOnNode(InetAddress.getByName("2.3.4.5")));
    }
}
