
package org.eclipse.jgit.patch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.diff.EditList;
import org.junit.Test;

public class EditListTest {
	@Test
	public void testHunkHeader() throws IOException {
		final Patch p = parseTestPatchFile("testGetText_BothISO88591.patch");
		final FileHeader fh = p.getFiles().get(0);

		final EditList list0 = fh.getHunks().get(0).toEditList();
		assertEquals(1
		assertEquals(new Edit(4 - 1

		final EditList list1 = fh.getHunks().get(1).toEditList();
		assertEquals(1
		assertEquals(new Edit(16 - 1
	}

	@Test
	public void testFileHeader() throws IOException {
		final Patch p = parseTestPatchFile("testGetText_BothISO88591.patch");
		final FileHeader fh = p.getFiles().get(0);
		final EditList e = fh.toEditList();
		assertEquals(2
		assertEquals(new Edit(4 - 1
		assertEquals(new Edit(16 - 1
	}

	@Test
	public void testTypes() throws IOException {
		final Patch p = parseTestPatchFile("testEditList_Types.patch");
		final FileHeader fh = p.getFiles().get(0);
		final EditList e = fh.toEditList();
		assertEquals(3
		assertEquals(new Edit(3 - 1
		assertEquals(new Edit(17 - 1
		assertEquals(new Edit(23 - 1
	}

	private Patch parseTestPatchFile(String patchFile) throws IOException {
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
