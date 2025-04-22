
package org.eclipse.jgit.patch;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.eclipse.jgit.junit.JGitTestUtil;
import org.junit.Test;

public class GetTextTest {
	@Test
	public void testGetText_BothISO88591() throws IOException {
		final Charset cs = ISO_8859_1;
		final Patch p = parseTestPatchFile();
		assertTrue(p.getErrors().isEmpty());
		assertEquals(1
		final FileHeader fh = p.getFiles().get(0);
		assertEquals(2
		assertEquals(readTestPatchFile(cs)
	}

	@Test
	public void testGetText_NoBinary() throws IOException {
		final Charset cs = ISO_8859_1;
		final Patch p = parseTestPatchFile();
		assertTrue(p.getErrors().isEmpty());
		assertEquals(1
		final FileHeader fh = p.getFiles().get(0);
		assertEquals(0
		assertEquals(readTestPatchFile(cs)
	}

	@Test
	public void testGetText_Convert() throws IOException {
		final Charset csOld = ISO_8859_1;
		final Charset csNew = UTF_8;
		final Patch p = parseTestPatchFile();
		assertTrue(p.getErrors().isEmpty());
		assertEquals(1
		final FileHeader fh = p.getFiles().get(0);
		assertEquals(2

		String exp = readTestPatchFile(csOld);
		exp = exp.replace("\303\205ngstr\303\266m"

		assertEquals(exp
	}

	@Test
	public void testGetText_DiffCc() throws IOException {
		final Charset csOld = ISO_8859_1;
		final Charset csNew = UTF_8;
		final Patch p = parseTestPatchFile();
		assertTrue(p.getErrors().isEmpty());
		assertEquals(1
		final CombinedFileHeader fh = (CombinedFileHeader) p.getFiles().get(0);
		assertEquals(1

		String exp = readTestPatchFile(csOld);
		exp = exp.replace("\303\205ngstr\303\266m"

		assertEquals(exp
				.getScriptText(new Charset[] { csNew
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

	private String readTestPatchFile(Charset cs) throws IOException {
		final String patchFile = JGitTestUtil.getName() + ".patch";
		try (InputStream in = getClass().getResourceAsStream(patchFile)) {
			if (in == null) {
				fail("No " + patchFile + " test vector");
			}
			final InputStreamReader r = new InputStreamReader(in
			char[] tmp = new char[2048];
			final StringBuilder s = new StringBuilder();
			int n;
			while ((n = r.read(tmp)) > 0)
				s.append(tmp
			return s.toString();
		}
	}
}
