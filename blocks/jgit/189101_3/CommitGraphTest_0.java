
package org.eclipse.jgit.internal.storage.commitgraph;

import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.BLOOM_BITS_PER_ENTRY;
import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraphConstants.BLOOM_KEY_NUM_HASHES;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.jgit.lib.BloomFilter;
import org.junit.Test;

public class BloomFilterTest {

	private static String[] testCases = { "a"

    @Test
    public void testEmptyFilter() {
        ChangedPathFilter filter = ChangedPathFilter.TRUNCATED_EMPTY_FILTER;

        for (String str : testCases) {
            BloomFilter.Key key = ChangedPathFilter.newBloomKey(str
            assertFalse(filter.contains(key));
        }
    }

    @Test
    public void testLargeFilter() {
        ChangedPathFilter filter = ChangedPathFilter.TRUNCATED_LARGE_FILTER;

        for (String str : testCases) {
            BloomFilter.Key key = ChangedPathFilter.newBloomKey(str
            assertTrue(filter.contains(key));
        }
    }

    @Test
    public void testSimpleFilter() {
        ChangedPathFilter filter = createBloomFilter(testCases);

        for (String str : testCases) {
            BloomFilter.Key key = ChangedPathFilter.newBloomKey(str
            assertTrue(filter.contains(key));
        }
    }

	private ChangedPathFilter createBloomFilter(String[] strings) {
		int filterLen = (strings.length * BLOOM_BITS_PER_ENTRY + Byte.SIZE - 1)
				/ Byte.SIZE;
		ChangedPathFilter filter = new ChangedPathFilter(filterLen
				BLOOM_KEY_NUM_HASHES);

		for (String str : strings) {
			BloomFilter.Key key = ChangedPathFilter.newBloomKey(str
					BLOOM_KEY_NUM_HASHES);
			filter.addKey(key);
		}
		return filter;
	}
}
