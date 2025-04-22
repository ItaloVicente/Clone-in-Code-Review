
package org.eclipse.jgit.dircache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.junit.Test;

public class DirCacheEntryTest {
	@Test
	public void testIsValidPath() {
		assertTrue(isValidPath("a"));
		assertTrue(isValidPath("a/b"));
		assertTrue(isValidPath("ab/cd/ef"));

		assertFalse(isValidPath(""));
		assertFalse(isValidPath("/a"));
		assertFalse(isValidPath("a/"));
		assertFalse(isValidPath("ab/cd/ef/"));
		assertFalse(isValidPath("a\u0000b"));
	}

	@SuppressWarnings("unused")
	private static boolean isValidPath(String path) {
		try {
			new DirCacheEntry(path);
			return true;
		} catch (InvalidPathException e) {
			return false;
		}
	}

	@SuppressWarnings("unused")
	@Test
	public void testCreate_ByStringPath() {
		assertEquals("a"
		assertEquals("a/b"

		try {
			new DirCacheEntry("/a");
			fail("Incorrectly created DirCacheEntry");
		} catch (IllegalArgumentException err) {
			assertEquals("Invalid path: /a"
		}
	}

	@SuppressWarnings("unused")
	@Test
	public void testCreate_ByStringPathAndStage() {
		DirCacheEntry e;

		e = new DirCacheEntry("a"
		assertEquals("a"
		assertEquals(0

		e = new DirCacheEntry("a/b"
		assertEquals("a/b"
		assertEquals(1

		e = new DirCacheEntry("a/c"
		assertEquals("a/c"
		assertEquals(2

		e = new DirCacheEntry("a/d"
		assertEquals("a/d"
		assertEquals(3

		try {
			new DirCacheEntry("/a"
			fail("Incorrectly created DirCacheEntry");
		} catch (IllegalArgumentException err) {
			assertEquals("Invalid path: /a"
		}

		try {
			new DirCacheEntry("a"
			fail("Incorrectly created DirCacheEntry");
		} catch (IllegalArgumentException err) {
			assertEquals("Invalid stage -11 for path a"
		}

		try {
			new DirCacheEntry("a"
			fail("Incorrectly created DirCacheEntry");
		} catch (IllegalArgumentException err) {
			assertEquals("Invalid stage 4 for path a"
		}
	}

	@Test
	public void testSetFileMode() {
		final DirCacheEntry e = new DirCacheEntry("a");

		assertEquals(0

		e.setFileMode(FileMode.REGULAR_FILE);
		assertSame(FileMode.REGULAR_FILE
		assertEquals(FileMode.REGULAR_FILE.getBits()

		e.setFileMode(FileMode.EXECUTABLE_FILE);
		assertSame(FileMode.EXECUTABLE_FILE
		assertEquals(FileMode.EXECUTABLE_FILE.getBits()

		e.setFileMode(FileMode.SYMLINK);
		assertSame(FileMode.SYMLINK
		assertEquals(FileMode.SYMLINK.getBits()

		e.setFileMode(FileMode.GITLINK);
		assertSame(FileMode.GITLINK
		assertEquals(FileMode.GITLINK.getBits()

		try {
			e.setFileMode(FileMode.MISSING);
			fail("incorrectly accepted FileMode.MISSING");
		} catch (IllegalArgumentException err) {
			assertEquals("Invalid mode 0 for path a"
		}

		try {
			e.setFileMode(FileMode.TREE);
			fail("incorrectly accepted FileMode.TREE");
		} catch (IllegalArgumentException err) {
			assertEquals("Invalid mode 40000 for path a"
		}
	}

	@Test
	public void testCopyMetaDataWithStage() {
		copyMetaDataHelper(false);
	}

	@Test
	public void testCopyMetaDataWithoutStage() {
		copyMetaDataHelper(true);
	}

	private static void copyMetaDataHelper(boolean keepStage) {
		DirCacheEntry e = new DirCacheEntry("some/path"
		e.setAssumeValid(false);
		e.setCreationTime(2L);
		e.setFileMode(FileMode.EXECUTABLE_FILE);
		e.setLastModified(3L);
		e.setLength(100L);
		e.setObjectId(ObjectId
				.fromString("0123456789012345678901234567890123456789"));
		e.setUpdateNeeded(true);

		DirCacheEntry f = new DirCacheEntry("someother/path"
				DirCacheEntry.STAGE_1);
		f.setAssumeValid(true);
		f.setCreationTime(10L);
		f.setFileMode(FileMode.SYMLINK);
		f.setLastModified(20L);
		f.setLength(100000000L);
		f.setObjectId(ObjectId
				.fromString("1234567890123456789012345678901234567890"));
		f.setUpdateNeeded(true);

		e.copyMetaData(f
		assertTrue(e.isAssumeValid());
		assertEquals(10L
		assertEquals(
				ObjectId.fromString("1234567890123456789012345678901234567890")
				e.getObjectId());
		assertEquals(FileMode.SYMLINK
		assertEquals(20L
		assertEquals(100000000L
		if (keepStage)
			assertEquals(DirCacheEntry.STAGE_2
		else
			assertEquals(DirCacheEntry.STAGE_1
		assertTrue(e.isUpdateNeeded());
		assertEquals("some/path"
	}
}
