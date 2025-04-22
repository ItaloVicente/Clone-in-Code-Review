
package org.eclipse.jgit.internal.storage.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.internal.storage.file.PackIndex.MutableEntry;
import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.junit.Before;
import org.junit.Test;

public class PackReverseIndexTest extends RepositoryTestCase {

	private PackIndex idx;

	private PackReverseIndex reverseIdx;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		idx = PackIndex.open(JGitTestUtil.getTestResourceFile(
				"pack-huge.idx"));
		reverseIdx = new PackReverseIndex(idx);
	}

	@Test
	public void testFindObject() {
		for (MutableEntry me : idx)
			assertEquals(me.toObjectId()
	}

	@Test
	public void testFindObjectWrongOffset() {
		assertNull(reverseIdx.findObject(0));
	}

	@Test
	public void testFindNextOffset() throws CorruptObjectException {
		long offset = findFirstOffset();
		assertTrue(offset > 0);
		for (int i = 0; i < idx.getObjectCount(); i++) {
			long newOffset = reverseIdx.findNextOffset(offset
			assertTrue(newOffset > offset);
			if (i == idx.getObjectCount() - 1)
				assertEquals(newOffset
			else
				assertEquals(newOffset
						.findObject(newOffset)));
			offset = newOffset;
		}
	}

	@Test
	public void testFindNextOffsetWrongOffset() {
		try {
			reverseIdx.findNextOffset(0
			fail("findNextOffset() should throw exception");
		} catch (CorruptObjectException x) {
		}
	}

	private long findFirstOffset() {
		long min = Long.MAX_VALUE;
		for (MutableEntry me : idx)
			min = Math.min(min
		return min;
	}
}
