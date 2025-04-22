
package org.eclipse.jgit.patch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.junit.Test;

public class FileHeaderTest {
	@Test
	public void testParseGitFileName_Empty() {
		final FileHeader fh = data("");
		assertEquals(-1
		assertNotNull(fh.getHunks());
		assertTrue(fh.getHunks().isEmpty());
		assertFalse(fh.hasMetaDataChanges());
	}

	@Test
	public void testParseGitFileName_NoLF() {
		final FileHeader fh = data("a/ b/");
		assertEquals(-1
	}

	@Test
	public void testParseGitFileName_NoSecondLine() {
		final FileHeader fh = data("\n");
		assertEquals(-1
	}

	@Test
	public void testParseGitFileName_EmptyHeader() {
		final FileHeader fh = data("\n\n");
		assertEquals(1
	}

	@Test
	public void testParseGitFileName_Foo() {
		final String name = "foo";
		final FileHeader fh = header(name);
		assertEquals(gitLine(name).length()
				fh.buf.length));
		assertEquals(name
		assertSame(fh.getOldPath()
		assertFalse(fh.hasMetaDataChanges());
	}

	@Test
	public void testParseGitFileName_FailFooBar() {
		final FileHeader fh = data("a/foo b/bar\n-");
		assertTrue(fh.parseGitFileName(0
		assertNull(fh.getOldPath());
		assertNull(fh.getNewPath());
		assertFalse(fh.hasMetaDataChanges());
	}

	@Test
	public void testParseGitFileName_FooSpBar() {
		final String name = "foo bar";
		final FileHeader fh = header(name);
		assertEquals(gitLine(name).length()
				fh.buf.length));
		assertEquals(name
		assertSame(fh.getOldPath()
		assertFalse(fh.hasMetaDataChanges());
	}

	@Test
	public void testParseGitFileName_DqFooTabBar() {
		final String name = "foo\tbar";
		final String dqName = "foo\\tbar";
		final FileHeader fh = dqHeader(dqName);
		assertEquals(dqGitLine(dqName).length()
				fh.buf.length));
		assertEquals(name
		assertSame(fh.getOldPath()
		assertFalse(fh.hasMetaDataChanges());
	}

	@Test
	public void testParseGitFileName_DqFooSpLfNulBar() {
		final String name = "foo \n\0bar";
		final String dqName = "foo \\n\\0bar";
		final FileHeader fh = dqHeader(dqName);
		assertEquals(dqGitLine(dqName).length()
				fh.buf.length));
		assertEquals(name
		assertSame(fh.getOldPath()
		assertFalse(fh.hasMetaDataChanges());
	}

	@Test
	public void testParseGitFileName_SrcFooC() {
		final String name = "src/foo/bar/argh/code.c";
		final FileHeader fh = header(name);
		assertEquals(gitLine(name).length()
				fh.buf.length));
		assertEquals(name
		assertSame(fh.getOldPath()
		assertFalse(fh.hasMetaDataChanges());
	}

	@Test
	public void testParseGitFileName_SrcFooCNonStandardPrefix() {
		final String name = "src/foo/bar/argh/code.c";
		final String header = "project-v-1.0/" + name + " mydev/" + name + "\n";
		final FileHeader fh = data(header + "-");
		assertEquals(header.length()
		assertEquals(name
		assertSame(fh.getOldPath()
		assertFalse(fh.hasMetaDataChanges());
	}

	@Test
	public void testParseUnicodeName_NewFile() {
		final FileHeader fh = data("diff --git \"a/\\303\\205ngstr\\303\\266m\" \"b/\\303\\205ngstr\\303\\266m\"\n"
				+ "new file mode 100644\n"
				+ "index 0000000..7898192\n"
				+ "--- /dev/null\n"
				+ "+++ \"b/\\303\\205ngstr\\303\\266m\"\n"
				+ "@@ -0
		assertParse(fh);

		assertEquals("/dev/null"
		assertSame(DiffEntry.DEV_NULL
		assertEquals("\u00c5ngstr\u00f6m"

		assertSame(FileHeader.ChangeType.ADD
		assertSame(FileHeader.PatchType.UNIFIED
		assertTrue(fh.hasMetaDataChanges());

		assertSame(FileMode.MISSING
		assertSame(FileMode.REGULAR_FILE

		assertEquals("0000000"
		assertEquals("7898192"
		assertEquals(0
	}

	@Test
	public void testParseUnicodeName_DeleteFile() {
		final FileHeader fh = data("diff --git \"a/\\303\\205ngstr\\303\\266m\" \"b/\\303\\205ngstr\\303\\266m\"\n"
				+ "deleted file mode 100644\n"
				+ "index 7898192..0000000\n"
				+ "--- \"a/\\303\\205ngstr\\303\\266m\"\n"
				+ "+++ /dev/null\n"
				+ "@@ -1 +0
		assertParse(fh);

		assertEquals("\u00c5ngstr\u00f6m"
		assertEquals("/dev/null"
		assertSame(DiffEntry.DEV_NULL

		assertSame(FileHeader.ChangeType.DELETE
		assertSame(FileHeader.PatchType.UNIFIED
		assertTrue(fh.hasMetaDataChanges());

		assertSame(FileMode.REGULAR_FILE
		assertSame(FileMode.MISSING

		assertEquals("7898192"
		assertEquals("0000000"
		assertEquals(0
	}

	@Test
	public void testParseModeChange() {
		final FileHeader fh = data("diff --git a/a b b/a b\n"
				+ "old mode 100644\n" + "new mode 100755\n");
		assertParse(fh);
		assertEquals("a b"
		assertEquals("a b"

		assertSame(FileHeader.ChangeType.MODIFY
		assertSame(FileHeader.PatchType.UNIFIED
		assertTrue(fh.hasMetaDataChanges());

		assertNull(fh.getOldId());
		assertNull(fh.getNewId());

		assertSame(FileMode.REGULAR_FILE
		assertSame(FileMode.EXECUTABLE_FILE
		assertEquals(0
	}

	@Test
	public void testParseRename100_NewStyle() {
		final FileHeader fh = data("diff --git a/a b/ c/\\303\\205ngstr\\303\\266m\n"
				+ "similarity index 100%\n"
				+ "rename from a\n"
				+ "rename to \" c/\\303\\205ngstr\\303\\266m\"\n");
		int ptr = fh.parseGitFileName(0
		assertTrue(ptr > 0);
		assertNull(fh.getNewPath());

		ptr = fh.parseGitHeaders(ptr
		assertTrue(ptr > 0);

		assertEquals("a"
		assertEquals(" c/\u00c5ngstr\u00f6m"

		assertSame(FileHeader.ChangeType.RENAME
		assertSame(FileHeader.PatchType.UNIFIED
		assertTrue(fh.hasMetaDataChanges());

		assertNull(fh.getOldId());
		assertNull(fh.getNewId());

		assertNull(fh.getOldMode());
		assertNull(fh.getNewMode());

		assertEquals(100
	}

	@Test
	public void testParseRename100_OldStyle() {
		final FileHeader fh = data("diff --git a/a b/ c/\\303\\205ngstr\\303\\266m\n"
				+ "similarity index 100%\n"
				+ "rename old a\n"
				+ "rename new \" c/\\303\\205ngstr\\303\\266m\"\n");
		int ptr = fh.parseGitFileName(0
		assertTrue(ptr > 0);
		assertNull(fh.getNewPath());

		ptr = fh.parseGitHeaders(ptr
		assertTrue(ptr > 0);

		assertEquals("a"
		assertEquals(" c/\u00c5ngstr\u00f6m"

		assertSame(FileHeader.ChangeType.RENAME
		assertSame(FileHeader.PatchType.UNIFIED
		assertTrue(fh.hasMetaDataChanges());

		assertNull(fh.getOldId());
		assertNull(fh.getNewId());

		assertNull(fh.getOldMode());
		assertNull(fh.getNewMode());

		assertEquals(100
	}

	@Test
	public void testParseCopy100() {
		final FileHeader fh = data("diff --git a/a b/ c/\\303\\205ngstr\\303\\266m\n"
				+ "similarity index 100%\n"
				+ "copy from a\n"
				+ "copy to \" c/\\303\\205ngstr\\303\\266m\"\n");
		int ptr = fh.parseGitFileName(0
		assertTrue(ptr > 0);
		assertNull(fh.getNewPath());

		ptr = fh.parseGitHeaders(ptr
		assertTrue(ptr > 0);

		assertEquals("a"
		assertEquals(" c/\u00c5ngstr\u00f6m"

		assertSame(FileHeader.ChangeType.COPY
		assertSame(FileHeader.PatchType.UNIFIED
		assertTrue(fh.hasMetaDataChanges());

		assertNull(fh.getOldId());
		assertNull(fh.getNewId());

		assertNull(fh.getOldMode());
		assertNull(fh.getNewMode());

		assertEquals(100
	}

	@Test
	public void testParseFullIndexLine_WithMode() {
		final String oid = "78981922613b2afb6025042ff6bd878ac1994e85";
		final String nid = "61780798228d17af2d34fce4cfbdf35556832472";
		final FileHeader fh = data("diff --git a/a b/a\n" + "index " + oid
				+ ".." + nid + " 100644\n" + "--- a/a\n" + "+++ b/a\n");
		assertParse(fh);

		assertEquals("a"
		assertEquals("a"

		assertSame(FileMode.REGULAR_FILE
		assertSame(FileMode.REGULAR_FILE
		assertFalse(fh.hasMetaDataChanges());

		assertNotNull(fh.getOldId());
		assertNotNull(fh.getNewId());

		assertTrue(fh.getOldId().isComplete());
		assertTrue(fh.getNewId().isComplete());

		assertEquals(ObjectId.fromString(oid)
		assertEquals(ObjectId.fromString(nid)
	}

	@Test
	public void testParseFullIndexLine_NoMode() {
		final String oid = "78981922613b2afb6025042ff6bd878ac1994e85";
		final String nid = "61780798228d17af2d34fce4cfbdf35556832472";
		final FileHeader fh = data("diff --git a/a b/a\n" + "index " + oid
				+ ".." + nid + "\n" + "--- a/a\n" + "+++ b/a\n");
		assertParse(fh);

		assertEquals("a"
		assertEquals("a"
		assertFalse(fh.hasMetaDataChanges());

		assertNull(fh.getOldMode());
		assertNull(fh.getNewMode());

		assertNotNull(fh.getOldId());
		assertNotNull(fh.getNewId());

		assertTrue(fh.getOldId().isComplete());
		assertTrue(fh.getNewId().isComplete());

		assertEquals(ObjectId.fromString(oid)
		assertEquals(ObjectId.fromString(nid)
	}

	@Test
	public void testParseAbbrIndexLine_WithMode() {
		final int a = 7;
		final String oid = "78981922613b2afb6025042ff6bd878ac1994e85";
		final String nid = "61780798228d17af2d34fce4cfbdf35556832472";
		final FileHeader fh = data("diff --git a/a b/a\n" + "index "
				+ oid.substring(0
				+ " 100644\n" + "--- a/a\n" + "+++ b/a\n");
		assertParse(fh);

		assertEquals("a"
		assertEquals("a"

		assertSame(FileMode.REGULAR_FILE
		assertSame(FileMode.REGULAR_FILE
		assertFalse(fh.hasMetaDataChanges());

		assertNotNull(fh.getOldId());
		assertNotNull(fh.getNewId());

		assertFalse(fh.getOldId().isComplete());
		assertFalse(fh.getNewId().isComplete());

		assertEquals(oid.substring(0
		assertEquals(nid.substring(0

		assertTrue(ObjectId.fromString(oid).startsWith(fh.getOldId()));
		assertTrue(ObjectId.fromString(nid).startsWith(fh.getNewId()));
	}

	@Test
	public void testParseAbbrIndexLine_NoMode() {
		final int a = 7;
		final String oid = "78981922613b2afb6025042ff6bd878ac1994e85";
		final String nid = "61780798228d17af2d34fce4cfbdf35556832472";
		final FileHeader fh = data("diff --git a/a b/a\n" + "index "
				+ oid.substring(0
				+ "\n" + "--- a/a\n" + "+++ b/a\n");
		assertParse(fh);

		assertEquals("a"
		assertEquals("a"

		assertNull(fh.getOldMode());
		assertNull(fh.getNewMode());
		assertFalse(fh.hasMetaDataChanges());

		assertNotNull(fh.getOldId());
		assertNotNull(fh.getNewId());

		assertFalse(fh.getOldId().isComplete());
		assertFalse(fh.getNewId().isComplete());

		assertEquals(oid.substring(0
		assertEquals(nid.substring(0

		assertTrue(ObjectId.fromString(oid).startsWith(fh.getOldId()));
		assertTrue(ObjectId.fromString(nid).startsWith(fh.getNewId()));
	}

	private static void assertParse(FileHeader fh) {
		int ptr = fh.parseGitFileName(0
		assertTrue(ptr > 0);
		ptr = fh.parseGitHeaders(ptr
		assertTrue(ptr > 0);
	}

	private static FileHeader data(String in) {
		return new FileHeader(Constants.encodeASCII(in)
	}

	private static FileHeader header(String path) {
		return data(gitLine(path) + "--- " + path + "\n");
	}

	private static String gitLine(String path) {
		return "a/" + path + " b/" + path + "\n";
	}

	private static FileHeader dqHeader(String path) {
		return data(dqGitLine(path) + "--- " + path + "\n");
	}

	private static String dqGitLine(String path) {
		return "\"a/" + path + "\" \"b/" + path + "\"\n";
	}
}
