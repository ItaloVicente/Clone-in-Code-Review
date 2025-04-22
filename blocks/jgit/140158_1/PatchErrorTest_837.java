
package org.eclipse.jgit.patch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.lib.FileMode;
import org.junit.Test;

public class PatchCcTest {
	@Test
	public void testParse_OneFileCc() throws IOException {
		final Patch p = parseTestPatchFile();
		assertEquals(1
		assertTrue(p.getErrors().isEmpty());

		final CombinedFileHeader cfh = (CombinedFileHeader) p.getFiles().get(0);

		assertEquals("org.spearce.egit.ui/src/org/spearce/egit/ui/UIText.java"
				cfh.getNewPath());
		assertEquals(cfh.getNewPath()

		assertEquals(98

		assertEquals(2
		assertSame(cfh.getOldId(0)
		assertEquals("169356b"
		assertEquals("dd8c317"
		assertEquals("fd85931"

		assertSame(cfh.getOldMode(0)
		assertSame(FileMode.REGULAR_FILE
		assertSame(FileMode.REGULAR_FILE
		assertSame(FileMode.EXECUTABLE_FILE
		assertSame(FileHeader.ChangeType.MODIFY
		assertSame(FileHeader.PatchType.UNIFIED

		assertEquals(1
		{
			final CombinedHunkHeader h = cfh.getHunks().get(0);

			assertSame(cfh
			assertEquals(346
			assertEquals(764

			assertSame(h.getOldImage(0)
			assertSame(cfh.getOldId(0)
			assertSame(cfh.getOldId(1)

			assertEquals(55
			assertEquals(12
			assertEquals(3
			assertEquals(0

			assertEquals(163
			assertEquals(13
			assertEquals(2
			assertEquals(0

			assertEquals(163
			assertEquals(15

			assertEquals(10
		}
	}

	@Test
	public void testParse_CcNewFile() throws IOException {
		final Patch p = parseTestPatchFile();
		assertEquals(1
		assertTrue(p.getErrors().isEmpty());

		final CombinedFileHeader cfh = (CombinedFileHeader) p.getFiles().get(0);

		assertSame(DiffEntry.DEV_NULL
		assertEquals("d"

		assertEquals(187

		assertEquals(2
		assertSame(cfh.getOldId(0)
		assertEquals("0000000"
		assertEquals("0000000"
		assertEquals("4bcfe98"

		assertSame(cfh.getOldMode(0)
		assertSame(FileMode.MISSING
		assertSame(FileMode.MISSING
		assertSame(FileMode.REGULAR_FILE
		assertSame(FileHeader.ChangeType.ADD
		assertSame(FileHeader.PatchType.UNIFIED

		assertEquals(1
		{
			final CombinedHunkHeader h = cfh.getHunks().get(0);

			assertSame(cfh
			assertEquals(273
			assertEquals(300

			assertSame(h.getOldImage(0)
			assertSame(cfh.getOldId(0)
			assertSame(cfh.getOldId(1)

			assertEquals(1
			assertEquals(0
			assertEquals(1
			assertEquals(0

			assertEquals(1
			assertEquals(0
			assertEquals(1
			assertEquals(0

			assertEquals(1
			assertEquals(1

			assertEquals(0
		}
	}

	@Test
	public void testParse_CcDeleteFile() throws IOException {
		final Patch p = parseTestPatchFile();
		assertEquals(1
		assertTrue(p.getErrors().isEmpty());

		final CombinedFileHeader cfh = (CombinedFileHeader) p.getFiles().get(0);

		assertEquals("a"
		assertSame(DiffEntry.DEV_NULL

		assertEquals(187

		assertEquals(2
		assertSame(cfh.getOldId(0)
		assertEquals("7898192"
		assertEquals("2e65efe"
		assertEquals("0000000"

		assertSame(cfh.getOldMode(0)
		assertSame(FileMode.REGULAR_FILE
		assertSame(FileMode.REGULAR_FILE
		assertSame(FileMode.MISSING
		assertSame(FileHeader.ChangeType.DELETE
		assertSame(FileHeader.PatchType.UNIFIED

		assertTrue(cfh.getHunks().isEmpty());
	}

	private Patch parseTestPatchFile() throws IOException {
		final String patchFile = JGitTestUtil.getName() + ".patch";
		try (InputStream in = getClass().getResourceAsStream(patchFile)) {
			if (in == null) {
				fail("No " + patchFile + " test vector");
			}
			final Patch p = new Patch();
			p.parse(in);
			return p;
		}
	}
}
