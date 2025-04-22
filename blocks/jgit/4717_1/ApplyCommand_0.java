package org.eclipse.jgit.api;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jgit.diff.DiffFormatterReflowTest;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.junit.Test;

public class ApplyCommandTest extends RepositoryTestCase {

	private RawText a;

	private RawText b;

	private ApplyResult init(final String name) throws Exception {
		a = new RawText(readFile(name + "_PreImage"));
		b = new RawText(readFile(name + "_PostImage"));
		write(new File(db.getDirectory().getParent()
				a.getString(0
		Git git = new Git(db);
		git.add().addFilepattern(name).call();
		git.commit().setMessage("PreImage").call();
		return git.apply()
				.setPatch(
						DiffFormatterReflowTest.class.getResourceAsStream(name
								+ ".patch"))
				.call();
	}

	@Test
	public void testModifyE() throws Exception {
		ApplyResult result = init("E");
		assertTrue(result.getFormatErrors().isEmpty());
		assertTrue(result.getApplyErrors().isEmpty());
		checkFile(new File(db.getWorkTree()
				b.getString(0
	}

	@Test
	public void testModifyX() throws Exception {
		ApplyResult result = init("X");
		assertTrue(result.getFormatErrors().isEmpty());
		assertTrue(result.getApplyErrors().isEmpty());
		checkFile(new File(db.getWorkTree()
				b.getString(0
	}

	@Test
	public void testModifyY() throws Exception {
		ApplyResult result = init("Y");
		assertTrue(result.getFormatErrors().isEmpty());
		assertTrue(result.getApplyErrors().isEmpty());
		checkFile(new File(db.getWorkTree()
				b.getString(0
	}

	@Test
	public void testModifyZ() throws Exception {
		ApplyResult result = init("Z");
		assertTrue(result.getFormatErrors().isEmpty());
		assertTrue(result.getApplyErrors().isEmpty());
		checkFile(new File(db.getWorkTree()
				b.getString(0
	}

	private byte[] readFile(final String patchFile) throws IOException {
		final InputStream in = DiffFormatterReflowTest.class
				.getResourceAsStream(patchFile);
		if (in == null) {
			fail("No " + patchFile + " test vector");
		}
		try {
			final byte[] buf = new byte[1024];
			final ByteArrayOutputStream temp = new ByteArrayOutputStream();
			int n;
			while ((n = in.read(buf)) > 0)
				temp.write(buf
			return temp.toByteArray();
		} finally {
			in.close();
		}
	}
}
