
package com.couchbase.client.core.cluster;

import com.couchbase.client.core.endpoint.ResponseStatusConverter;
import com.couchbase.client.core.endpoint.kv.ErrorMap;
import com.couchbase.client.core.util.ClusterDependentTest;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class KeyValueErrorMapTest extends ClusterDependentTest {

    @BeforeClass
    public static void checkExtendedAttributeAvailable() throws Exception {
        assumeMinimumVersionCompatible(5, 0);
    }

    @Test
    public void shouldCheckIfTheErrorMapIsRead() throws Exception {
        ErrorMap errMap = ResponseStatusConverter.getBinaryErrorMap();
        assertNotNull(errMap);
    }
}
