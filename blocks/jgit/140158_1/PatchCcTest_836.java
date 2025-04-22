
package org.eclipse.jgit.patch;

import static java.lang.Integer.valueOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.junit.JGitTestUtil;
import org.junit.Test;

public class PatchCcErrorTest {
	@Test
	public void testError_CcTruncatedOld() throws IOException {
		final Patch p = parseTestPatchFile();
		assertEquals(1
		assertEquals(3
		{
			final FormatError e = p.getErrors().get(0);
			assertSame(FormatError.Severity.ERROR
			assertEquals(MessageFormat.format(
					JGitText.get().truncatedHunkLinesMissingForAncestor
					valueOf(1)
			assertEquals(346
			assertTrue(e.getLineText().startsWith(
					"@@@ -55
		}
		{
			final FormatError e = p.getErrors().get(1);
			assertSame(FormatError.Severity.ERROR
			assertEquals(MessageFormat.format(
					JGitText.get().truncatedHunkLinesMissingForAncestor
					valueOf(2)
			assertEquals(346
			assertTrue(e.getLineText().startsWith(
					"@@@ -55
		}
		{
			final FormatError e = p.getErrors().get(2);
			assertSame(FormatError.Severity.ERROR
			assertEquals("Truncated hunk
					.getMessage());
			assertEquals(346
			assertTrue(e.getLineText().startsWith(
					"@@@ -55
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
