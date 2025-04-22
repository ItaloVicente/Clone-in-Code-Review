
package org.eclipse.jgit.patch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jgit.junit.JGitTestUtil;
import org.junit.Test;

public class PatchErrorTest {
	@Test
	public void testError_DisconnectedHunk() throws IOException {
		final Patch p = parseTestPatchFile();
		assertEquals(1
		{
			final FileHeader fh = p.getFiles().get(0);
			assertEquals(
					"org.eclipse.jgit/src/org/spearce/jgit/lib/RepositoryConfig.java"
					fh.getNewPath());
			assertEquals(1
		}

		assertEquals(1
		final FormatError e = p.getErrors().get(0);
		assertSame(FormatError.Severity.ERROR
		assertEquals("Hunk disconnected from file"
		assertEquals(18
		assertTrue(e.getLineText().startsWith("@@ -109
	}

	@Test
	public void testError_TruncatedOld() throws IOException {
		final Patch p = parseTestPatchFile();
		assertEquals(1
		assertEquals(1

		final FormatError e = p.getErrors().get(0);
		assertSame(FormatError.Severity.ERROR
		assertEquals("Truncated hunk
				.getMessage());
		assertEquals(313
		assertTrue(e.getLineText().startsWith("@@ -236
	}

	@Test
	public void testError_TruncatedNew() throws IOException {
		final Patch p = parseTestPatchFile();
		assertEquals(1
		assertEquals(1

		final FormatError e = p.getErrors().get(0);
		assertSame(FormatError.Severity.ERROR
		assertEquals("Truncated hunk
				.getMessage());
		assertEquals(313
		assertTrue(e.getLineText().startsWith("@@ -236
	}

	@Test
	public void testError_BodyTooLong() throws IOException {
		final Patch p = parseTestPatchFile();
		assertEquals(1
		assertEquals(1

		final FormatError e = p.getErrors().get(0);
		assertSame(FormatError.Severity.WARNING
		assertEquals("Hunk header 4:11 does not match body line count of 4:12"
				e.getMessage());
		assertEquals(349
		assertTrue(e.getLineText().startsWith("@@ -109
	}

	@Test
	public void testError_GarbageBetweenFiles() throws IOException {
		final Patch p = parseTestPatchFile();
		assertEquals(2
		{
			final FileHeader fh = p.getFiles().get(0);
			assertEquals(
					"org.eclipse.jgit.test/tst/org/spearce/jgit/lib/RepositoryConfigTest.java"
					fh.getNewPath());
			assertEquals(1
		}
		{
			final FileHeader fh = p.getFiles().get(1);
			assertEquals(
					"org.eclipse.jgit/src/org/spearce/jgit/lib/RepositoryConfig.java"
					fh.getNewPath());
			assertEquals(1
		}

		assertEquals(1
		final FormatError e = p.getErrors().get(0);
		assertSame(FormatError.Severity.WARNING
		assertEquals("Unexpected hunk trailer"
		assertEquals(926
		assertEquals("I AM NOT HERE\n"
	}

	@Test
	public void testError_GitBinaryNoForwardHunk() throws IOException {
		final Patch p = parseTestPatchFile();
		assertEquals(2
		{
			final FileHeader fh = p.getFiles().get(0);
			assertEquals("org.spearce.egit.ui/icons/toolbar/fetchd.png"
					.getNewPath());
			assertSame(FileHeader.PatchType.GIT_BINARY
			assertTrue(fh.getHunks().isEmpty());
			assertNull(fh.getForwardBinaryHunk());
		}
		{
			final FileHeader fh = p.getFiles().get(1);
			assertEquals("org.spearce.egit.ui/icons/toolbar/fetche.png"
					.getNewPath());
			assertSame(FileHeader.PatchType.UNIFIED
			assertTrue(fh.getHunks().isEmpty());
			assertNull(fh.getForwardBinaryHunk());
		}

		assertEquals(1
		final FormatError e = p.getErrors().get(0);
		assertSame(FormatError.Severity.ERROR
		assertEquals("Missing forward-image in GIT binary patch"
				.getMessage());
		assertEquals(297
		assertEquals("\n"
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
