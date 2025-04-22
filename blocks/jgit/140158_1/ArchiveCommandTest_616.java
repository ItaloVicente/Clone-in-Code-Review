package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jgit.api.errors.PatchApplyException;
import org.eclipse.jgit.api.errors.PatchFormatException;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.junit.Test;

public class ApplyCommandTest extends RepositoryTestCase {

	private RawText a;

	private RawText b;

	private ApplyResult init(String name) throws Exception {
		return init(name
	}

	private ApplyResult init(final String name
			final boolean postExists) throws Exception {
		try (Git git = new Git(db)) {
			if (preExists) {
				a = new RawText(readFile(name + "_PreImage"));
				write(new File(db.getDirectory().getParent()
						a.getString(0

				git.add().addFilepattern(name).call();
				git.commit().setMessage("PreImage").call();
			}

			if (postExists) {
				b = new RawText(readFile(name + "_PostImage"));
			}

			return git
					.apply()
					.setPatch(getTestResource(name + ".patch")).call();
		}
	}

	@Test
	public void testAddA1() throws Exception {
		ApplyResult result = init("A1"
		assertEquals(1
		assertEquals(new File(db.getWorkTree()
				.get(0));
		checkFile(new File(db.getWorkTree()
				b.getString(0
	}

	@Test
	public void testAddA2() throws Exception {
		ApplyResult result = init("A2"
		assertEquals(1
		assertEquals(new File(db.getWorkTree()
				.get(0));
		checkFile(new File(db.getWorkTree()
				b.getString(0
	}

	@Test
	public void testAddA1Sub() throws Exception {
		ApplyResult result = init("A1_sub"
		assertEquals(1
		assertEquals(new File(db.getWorkTree()
				.getUpdatedFiles().get(0));
	}

	@Test
	public void testDeleteD() throws Exception {
		ApplyResult result = init("D"
		assertEquals(1
		assertEquals(new File(db.getWorkTree()
				.get(0));
		assertFalse(new File(db.getWorkTree()
	}

	@Test(expected = PatchFormatException.class)
	public void testFailureF1() throws Exception {
		init("F1"
	}

	@Test(expected = PatchApplyException.class)
	public void testFailureF2() throws Exception {
		init("F2"
	}

	@Test
	public void testModifyE() throws Exception {
		ApplyResult result = init("E");
		assertEquals(1
		assertEquals(new File(db.getWorkTree()
				.get(0));
		checkFile(new File(db.getWorkTree()
				b.getString(0
	}

	@Test
	public void testModifyW() throws Exception {
		ApplyResult result = init("W");
		assertEquals(1
		assertEquals(new File(db.getWorkTree()
				result.getUpdatedFiles().get(0));
		checkFile(new File(db.getWorkTree()
				b.getString(0
	}

	@Test
	public void testAddM1() throws Exception {
		ApplyResult result = init("M1"
		assertEquals(1
		assertTrue(result.getUpdatedFiles().get(0).canExecute());
		checkFile(new File(db.getWorkTree()
				b.getString(0
	}

	@Test
	public void testModifyM2() throws Exception {
		ApplyResult result = init("M2"
		assertEquals(1
		assertTrue(result.getUpdatedFiles().get(0).canExecute());
		checkFile(new File(db.getWorkTree()
				b.getString(0
	}

	@Test
	public void testModifyM3() throws Exception {
		ApplyResult result = init("M3"
		assertEquals(1
		assertFalse(result.getUpdatedFiles().get(0).canExecute());
		checkFile(new File(db.getWorkTree()
				b.getString(0
	}

	@Test
	public void testModifyX() throws Exception {
		ApplyResult result = init("X");
		assertEquals(1
		assertEquals(new File(db.getWorkTree()
				.get(0));
		checkFile(new File(db.getWorkTree()
				b.getString(0
	}

	@Test
	public void testModifyY() throws Exception {
		ApplyResult result = init("Y");
		assertEquals(1
		assertEquals(new File(db.getWorkTree()
				.get(0));
		checkFile(new File(db.getWorkTree()
				b.getString(0
	}

	@Test
	public void testModifyZ() throws Exception {
		ApplyResult result = init("Z");
		assertEquals(1
		assertEquals(new File(db.getWorkTree()
				.get(0));
		checkFile(new File(db.getWorkTree()
				b.getString(0
	}

	@Test
	public void testModifyNL1() throws Exception {
		ApplyResult result = init("NL1");
		assertEquals(1
		assertEquals(new File(db.getWorkTree()
				.getUpdatedFiles().get(0));
		checkFile(new File(db.getWorkTree()
				b.getString(0
	}

	@Test
	public void testNonASCII() throws Exception {
		ApplyResult result = init("NonASCII");
		assertEquals(1
		assertEquals(new File(db.getWorkTree()
				result.getUpdatedFiles().get(0));
		checkFile(new File(db.getWorkTree()
				b.getString(0
	}

	@Test
	public void testNonASCII2() throws Exception {
		ApplyResult result = init("NonASCII2");
		assertEquals(1
		assertEquals(new File(db.getWorkTree()
				result.getUpdatedFiles().get(0));
		checkFile(new File(db.getWorkTree()
				b.getString(0
	}

	@Test
	public void testNonASCIIAdd() throws Exception {
		ApplyResult result = init("NonASCIIAdd");
		assertEquals(1
		assertEquals(new File(db.getWorkTree()
				result.getUpdatedFiles().get(0));
		checkFile(new File(db.getWorkTree()
				b.getString(0
	}

	@Test
	public void testNonASCIIAdd2() throws Exception {
		ApplyResult result = init("NonASCIIAdd2"
		assertEquals(1
		assertEquals(new File(db.getWorkTree()
				result.getUpdatedFiles().get(0));
		checkFile(new File(db.getWorkTree()
				b.getString(0
	}

	@Test
	public void testNonASCIIDel() throws Exception {
		ApplyResult result = init("NonASCIIDel"
		assertEquals(1
		assertEquals(new File(db.getWorkTree()
				result.getUpdatedFiles().get(0));
		assertFalse(new File(db.getWorkTree()
	}

	private static byte[] readFile(String patchFile) throws IOException {
		final InputStream in = getTestResource(patchFile);
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

	private static InputStream getTestResource(String patchFile) {
		return ApplyCommandTest.class.getClassLoader()
				.getResourceAsStream("org/eclipse/jgit/diff/" + patchFile);
	}
}
