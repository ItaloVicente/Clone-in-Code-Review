
package org.eclipse.jgit.lfs.lib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.jgit.lfs.test.LongObjectIdTestUtils;
import org.junit.Test;

public class LargeObjectPointerTest {

	@Test
	public void testEquals() {
		int size1 = 12345;
		LongObjectId id1 = LongObjectIdTestUtils.hash("test").toObjectId();
		LargeObjectPointer pointer1 = new LargeObjectPointer(id1
		assertTrue("pointer should equal itself"
		LargeObjectPointer pointer2 = new LargeObjectPointer(id1
		assertEquals("objects should be equals"

		LongObjectId id2 = LongObjectIdTestUtils.hash("other").toObjectId();
		pointer2 = new LargeObjectPointer(id2
		assertNotEquals("objects with different id should be not equal"
				pointer1

		pointer2 = new LargeObjectPointer(id1
		assertNotEquals("objects with size id should be not equal"
				pointer2);
	}
}
