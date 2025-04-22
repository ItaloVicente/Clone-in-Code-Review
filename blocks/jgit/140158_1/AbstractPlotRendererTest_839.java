
package org.eclipse.jgit.patch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.junit.Test;

public class PatchTest {
	@Test
	public void testEmpty() {
		final Patch p = new Patch();
		assertTrue(p.getFiles().isEmpty());
		assertTrue(p.getErrors().isEmpty());
	}

	@Test
	public void testParse_ConfigCaseInsensitive() throws IOException {
		final Patch p = parseTestPatchFile();
		assertEquals(2
		assertTrue(p.getErrors().isEmpty());

		final FileHeader fRepositoryConfigTest = p.getFiles().get(0);
		final FileHeader fRepositoryConfig = p.getFiles().get(1);

		assertEquals(
				"org.eclipse.jgit.test/tst/org/spearce/jgit/lib/RepositoryConfigTest.java"
				fRepositoryConfigTest.getNewPath());

		assertEquals(
				"org.eclipse.jgit/src/org/spearce/jgit/lib/RepositoryConfig.java"
				fRepositoryConfig.getNewPath());

		assertEquals(572
		assertEquals(1490

		assertEquals("da7e704"
		assertEquals("34ce04a"
		assertSame(FileHeader.PatchType.UNIFIED
				.getPatchType());
		assertSame(FileMode.REGULAR_FILE
		assertSame(FileMode.REGULAR_FILE
		assertEquals(1
		{
			final HunkHeader h = fRepositoryConfigTest.getHunks().get(0);
			assertSame(fRepositoryConfigTest
			assertEquals(921
			assertEquals(109
			assertEquals(4
			assertEquals(109
			assertEquals(11

			assertEquals(4
			assertEquals(7
			assertEquals(0
			assertSame(fRepositoryConfigTest.getOldId()
					.getId());

			assertEquals(1490
		}

		assertEquals("45c2f8a"
		assertEquals("3291bba"
		assertSame(FileHeader.PatchType.UNIFIED
				.getPatchType());
		assertSame(FileMode.REGULAR_FILE
		assertSame(FileMode.REGULAR_FILE
		assertEquals(3
		{
			final HunkHeader h = fRepositoryConfig.getHunks().get(0);
			assertSame(fRepositoryConfig
			assertEquals(1803
			assertEquals(236
			assertEquals(9
			assertEquals(236
			assertEquals(9

			assertEquals(7
			assertEquals(2
			assertEquals(2
			assertSame(fRepositoryConfig.getOldId()

			assertEquals(2434
		}
		{
			final HunkHeader h = fRepositoryConfig.getHunks().get(1);
			assertEquals(2434
			assertEquals(300
			assertEquals(7
			assertEquals(300
			assertEquals(7

			assertEquals(6
			assertEquals(1
			assertEquals(1

			assertEquals(2816
		}
		{
			final HunkHeader h = fRepositoryConfig.getHunks().get(2);
			assertEquals(2816
			assertEquals(954
			assertEquals(7
			assertEquals(954
			assertEquals(7

			assertEquals(6
			assertEquals(1
			assertEquals(1

			assertEquals(3035
		}
	}

	@Test
	public void testParse_NoBinary() throws IOException {
		final Patch p = parseTestPatchFile();
		assertEquals(5
		assertTrue(p.getErrors().isEmpty());

		for (int i = 0; i < 4; i++) {
			final FileHeader fh = p.getFiles().get(i);
			assertSame(FileHeader.ChangeType.ADD
			assertNotNull(fh.getOldId());
			assertNotNull(fh.getNewId());
			assertEquals("0000000"
			assertSame(FileMode.MISSING
			assertSame(FileMode.REGULAR_FILE
			assertTrue(fh.getNewPath().startsWith(
					"org.spearce.egit.ui/icons/toolbar/"));
			assertSame(FileHeader.PatchType.BINARY
			assertTrue(fh.getHunks().isEmpty());
			assertTrue(fh.hasMetaDataChanges());

			assertNull(fh.getForwardBinaryHunk());
			assertNull(fh.getReverseBinaryHunk());
		}

		final FileHeader fh = p.getFiles().get(4);
		assertEquals("org.spearce.egit.ui/plugin.xml"
		assertSame(FileHeader.ChangeType.MODIFY
		assertSame(FileHeader.PatchType.UNIFIED
		assertFalse(fh.hasMetaDataChanges());
		assertEquals("ee8a5a0"
		assertNull(fh.getForwardBinaryHunk());
		assertNull(fh.getReverseBinaryHunk());
		assertEquals(1
		assertEquals(272
	}

	@Test
	public void testParse_GitBinaryLiteral() throws IOException {
		final Patch p = parseTestPatchFile();
		final int[] binsizes = { 359
		assertEquals(5
		assertTrue(p.getErrors().isEmpty());

		for (int i = 0; i < 4; i++) {
			final FileHeader fh = p.getFiles().get(i);
			assertSame(FileHeader.ChangeType.ADD
			assertNotNull(fh.getOldId());
			assertNotNull(fh.getNewId());
			assertEquals(ObjectId.zeroId().name()
			assertSame(FileMode.REGULAR_FILE
			assertTrue(fh.getNewPath().startsWith(
					"org.spearce.egit.ui/icons/toolbar/"));
			assertSame(FileHeader.PatchType.GIT_BINARY
			assertTrue(fh.getHunks().isEmpty());
			assertTrue(fh.hasMetaDataChanges());

			final BinaryHunk fwd = fh.getForwardBinaryHunk();
			final BinaryHunk rev = fh.getReverseBinaryHunk();
			assertNotNull(fwd);
			assertNotNull(rev);
			assertEquals(binsizes[i]
			assertEquals(0

			assertSame(fh
			assertSame(fh

			assertSame(BinaryHunk.Type.LITERAL_DEFLATED
			assertSame(BinaryHunk.Type.LITERAL_DEFLATED
		}

		final FileHeader fh = p.getFiles().get(4);
		assertEquals("org.spearce.egit.ui/plugin.xml"
		assertSame(FileHeader.ChangeType.MODIFY
		assertSame(FileHeader.PatchType.UNIFIED
		assertFalse(fh.hasMetaDataChanges());
		assertEquals("ee8a5a0"
		assertNull(fh.getForwardBinaryHunk());
		assertNull(fh.getReverseBinaryHunk());
		assertEquals(1
		assertEquals(272
	}

	@Test
	public void testParse_GitBinaryDelta() throws IOException {
		final Patch p = parseTestPatchFile();
		assertEquals(1
		assertTrue(p.getErrors().isEmpty());

		final FileHeader fh = p.getFiles().get(0);
		assertTrue(fh.getNewPath().startsWith("zero.bin"));
		assertSame(FileHeader.ChangeType.MODIFY
		assertSame(FileHeader.PatchType.GIT_BINARY
		assertSame(FileMode.REGULAR_FILE

		assertNotNull(fh.getOldId());
		assertNotNull(fh.getNewId());
		assertEquals("08e7df176454f3ee5eeda13efa0adaa54828dfd8"
				.name());
		assertEquals("d70d8710b6d32ff844af0ee7c247e4b4b051867f"
				.name());

		assertTrue(fh.getHunks().isEmpty());
		assertFalse(fh.hasMetaDataChanges());

		final BinaryHunk fwd = fh.getForwardBinaryHunk();
		final BinaryHunk rev = fh.getReverseBinaryHunk();
		assertNotNull(fwd);
		assertNotNull(rev);
		assertEquals(12
		assertEquals(11

		assertSame(fh
		assertSame(fh

		assertSame(BinaryHunk.Type.DELTA_DEFLATED
		assertSame(BinaryHunk.Type.DELTA_DEFLATED

		assertEquals(496
	}

	@Test
	public void testParse_FixNoNewline() throws IOException {
		final Patch p = parseTestPatchFile();
		assertEquals(1
		assertTrue(p.getErrors().isEmpty());

		final FileHeader f = p.getFiles().get(0);

		assertEquals("a"
		assertEquals(252

		assertEquals("2e65efe"
		assertEquals("f2ad6c7"
		assertSame(FileHeader.PatchType.UNIFIED
		assertSame(FileMode.REGULAR_FILE
		assertSame(FileMode.REGULAR_FILE
		assertEquals(1
		{
			final HunkHeader h = f.getHunks().get(0);
			assertSame(f
			assertEquals(317
			assertEquals(1
			assertEquals(1
			assertEquals(1
			assertEquals(1

			assertEquals(0
			assertEquals(1
			assertEquals(1
			assertSame(f.getOldId()

			assertEquals(363
		}
	}

	@Test
	public void testParse_AddNoNewline() throws IOException {
		final Patch p = parseTestPatchFile();
		assertEquals(1
		assertTrue(p.getErrors().isEmpty());

		final FileHeader f = p.getFiles().get(0);

		assertEquals("a"
		assertEquals(256

		assertEquals("f2ad6c7"
		assertEquals("c59d9b6"
		assertSame(FileHeader.PatchType.UNIFIED
		assertSame(FileMode.REGULAR_FILE
		assertSame(FileMode.REGULAR_FILE
		assertEquals(1
		{
			final HunkHeader h = f.getHunks().get(0);
			assertSame(f
			assertEquals(321
			assertEquals(1
			assertEquals(1
			assertEquals(1
			assertEquals(1

			assertEquals(0
			assertEquals(1
			assertEquals(1
			assertSame(f.getOldId()

			assertEquals(367
		}
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
