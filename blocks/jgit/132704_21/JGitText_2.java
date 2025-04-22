package org.eclipse.jgit.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.Assert;
import org.junit.Test;

public class LRUMapTest {

	@SuppressWarnings("boxing")
	@Test
	public void testLRUEntriesAreEvicted() {
		Map<Integer
		for (int i = 0; i < 3; i++) {
			map.put(i
		}
		map.get(2);
		map.get(0);

		map.put(3

		Map<Integer
		expectedMap.put(2
		expectedMap.put(0
		expectedMap.put(3

		Assert.assertThat(map.entrySet()
				IsIterableContainingInOrder
						.contains(expectedMap.entrySet().toArray()));
	}
}
