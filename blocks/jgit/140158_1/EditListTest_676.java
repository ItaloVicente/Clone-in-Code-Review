
package org.eclipse.jgit.diff;

import static org.junit.Assert.assertEquals;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.patch.FileHeader;
import org.eclipse.jgit.patch.HunkHeader;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.RawParseUtils;
import org.eclipse.jgit.util.io.DisabledOutputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DiffFormatterTest extends RepositoryTestCase {
	private static final String DIFF = "diff --git ";

	private static final String REGULAR_FILE = "100644";

	private static final String GITLINK = "160000";

	private static final String PATH_A = "src/a";

	private static final String PATH_B = "src/b";

	private DiffFormatter df;

	private TestRepository<Repository> testDb;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		testDb = new TestRepository<>(db);
		df = new DiffFormatter(DisabledOutputStream.INSTANCE);
		df.setRepository(db);
		df.setAbbreviationLength(8);
	}

	@Override
	@After
	public void tearDown() throws Exception {
		if (df != null) {
			df.close();
		}
		super.tearDown();
	}

	@Test
	public void testCreateFileHeader_Add() throws Exception {
		ObjectId adId = blob("a\nd\n");
		DiffEntry ent = DiffEntry.add("FOO"
		FileHeader fh = df.toFileHeader(ent);

				+ "new file mode " + REGULAR_FILE + "\n"
				+ "index "
				+ ObjectId.zeroId().abbreviate(8).name()
				+ ".."
				+ "+++ b/FOO\n";
		assertEquals(diffHeader

		assertEquals(0
		assertEquals(fh.getBuffer().length
		assertEquals(FileHeader.PatchType.UNIFIED

		assertEquals(1

		HunkHeader hh = fh.getHunks().get(0);
		assertEquals(1

		EditList el = hh.toEditList();
		assertEquals(1

		Edit e = el.get(0);
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(2
		assertEquals(Edit.Type.INSERT
	}

	@Test
	public void testCreateFileHeader_Delete() throws Exception {
		ObjectId adId = blob("a\nd\n");
		DiffEntry ent = DiffEntry.delete("FOO"
		FileHeader fh = df.toFileHeader(ent);

				+ "deleted file mode " + REGULAR_FILE + "\n"
				+ "index "
				+ adId.abbreviate(8).name()
				+ ".."
				+ "+++ /dev/null\n";
		assertEquals(diffHeader

		assertEquals(0
		assertEquals(fh.getBuffer().length
		assertEquals(FileHeader.PatchType.UNIFIED

		assertEquals(1

		HunkHeader hh = fh.getHunks().get(0);
		assertEquals(1

		EditList el = hh.toEditList();
		assertEquals(1

		Edit e = el.get(0);
		assertEquals(0
		assertEquals(2
		assertEquals(0
		assertEquals(0
		assertEquals(Edit.Type.DELETE
	}

	@Test
	public void testCreateFileHeader_Modify() throws Exception {
		ObjectId adId = blob("a\nd\n");
		ObjectId abcdId = blob("a\nb\nc\nd\n");

		String diffHeader = makeDiffHeader(PATH_A

		DiffEntry ad = DiffEntry.delete(PATH_A
		DiffEntry abcd = DiffEntry.add(PATH_A

		DiffEntry mod = DiffEntry.pair(ChangeType.MODIFY

		FileHeader fh = df.toFileHeader(mod);

		assertEquals(diffHeader
		assertEquals(0
		assertEquals(fh.getBuffer().length
		assertEquals(FileHeader.PatchType.UNIFIED

		assertEquals(1

		HunkHeader hh = fh.getHunks().get(0);
		assertEquals(1

		EditList el = hh.toEditList();
		assertEquals(1

		Edit e = el.get(0);
		assertEquals(1
		assertEquals(1
		assertEquals(1
		assertEquals(3
		assertEquals(Edit.Type.INSERT
	}

	@Test
	public void testCreateFileHeader_Binary() throws Exception {
		ObjectId adId = blob("a\nd\n");
		ObjectId binId = blob("a\nb\nc\n\0\0\0\0d\n");

		String diffHeader = makeDiffHeader(PATH_A
				+ "Binary files differ\n";

		DiffEntry ad = DiffEntry.delete(PATH_A
		DiffEntry abcd = DiffEntry.add(PATH_B

		DiffEntry mod = DiffEntry.pair(ChangeType.MODIFY

		FileHeader fh = df.toFileHeader(mod);

		assertEquals(diffHeader
		assertEquals(FileHeader.PatchType.BINARY

		assertEquals(1

		HunkHeader hh = fh.getHunks().get(0);
		assertEquals(0
	}

	@Test
	public void testCreateFileHeader_GitLink() throws Exception {
		ObjectId aId = blob("a\n");
		ObjectId bId = blob("b\n");

		String diffHeader = makeDiffHeaderModeChange(PATH_A
				GITLINK

		DiffEntry ad = DiffEntry.delete(PATH_A
		ad.oldMode = FileMode.GITLINK;
		DiffEntry abcd = DiffEntry.add(PATH_A

		DiffEntry mod = DiffEntry.pair(ChangeType.MODIFY

		FileHeader fh = df.toFileHeader(mod);

		assertEquals(diffHeader

		assertEquals(1

		HunkHeader hh = fh.getHunks().get(0);
		assertEquals(1
	}

	@Test
	public void testCreateFileHeader_AddGitLink() throws Exception {
		ObjectId adId = blob("a\nd\n");
		DiffEntry ent = DiffEntry.add("FOO"
		ent.newMode = FileMode.GITLINK;
		FileHeader fh = df.toFileHeader(ent);

				+ "new file mode " + GITLINK + "\n"
				+ "index "
				+ ObjectId.zeroId().abbreviate(8).name()
				+ ".."
				+ "+++ b/FOO\n";
		assertEquals(diffHeader

		assertEquals(1
		HunkHeader hh = fh.getHunks().get(0);

		EditList el = hh.toEditList();
		assertEquals(1

		Edit e = el.get(0);
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(1
		assertEquals(Edit.Type.INSERT
	}

	@Test
	public void testCreateFileHeader_DeleteGitLink() throws Exception {
		ObjectId adId = blob("a\nd\n");
		DiffEntry ent = DiffEntry.delete("FOO"
		ent.oldMode = FileMode.GITLINK;
		FileHeader fh = df.toFileHeader(ent);

				+ "deleted file mode " + GITLINK + "\n"
				+ "index "
				+ adId.abbreviate(8).name()
				+ ".."
				+ "+++ /dev/null\n";
		assertEquals(diffHeader

		assertEquals(1
		HunkHeader hh = fh.getHunks().get(0);

		EditList el = hh.toEditList();
		assertEquals(1

		Edit e = el.get(0);
		assertEquals(0
		assertEquals(1
		assertEquals(0
		assertEquals(0
		assertEquals(Edit.Type.DELETE
	}

	@Test
	public void testCreateFileHeaderWithoutIndexLine() throws Exception {
		DiffEntry m = DiffEntry.modify(PATH_A);
		m.oldMode = FileMode.REGULAR_FILE;
		m.newMode = FileMode.EXECUTABLE_FILE;

		FileHeader fh = df.toFileHeader(m);
				"new mode 100755\n";
		assertEquals(expected
	}

	@Test
	public void testCreateFileHeaderForRenameWithoutContentChange() throws Exception {
		DiffEntry a = DiffEntry.delete(PATH_A
		DiffEntry b = DiffEntry.add(PATH_B
		DiffEntry m = DiffEntry.pair(ChangeType.RENAME
		m.oldId = null;
		m.newId = null;

		FileHeader fh = df.toFileHeader(m);
				"rename to src/b\n";
		assertEquals(expected
	}

	@Test
	public void testCreateFileHeaderForRenameModeChange()
			throws Exception {
		DiffEntry a = DiffEntry.delete(PATH_A
		DiffEntry b = DiffEntry.add(PATH_B
		b.oldMode = FileMode.REGULAR_FILE;
		b.newMode = FileMode.EXECUTABLE_FILE;
		DiffEntry m = DiffEntry.pair(ChangeType.RENAME
		m.oldId = null;
		m.newId = null;

		FileHeader fh = df.toFileHeader(m);
		String expected = DIFF + "a/src/a b/src/b\n" +
				"old mode 100644\n" +
				"new mode 100755\n" +
				"similarity index 100%\n" +
				"rename from src/a\n" +
				"rename to src/b\n";
		assertEquals(expected
	}

	@Test
	public void testDiff() throws Exception {
		write(new File(db.getDirectory().getParent()
		File folder = new File(db.getDirectory().getParent()
		FileUtils.mkdir(folder);
		write(new File(folder
		try (Git git = new Git(db);
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				DiffFormatter dfmt = new DiffFormatter(new BufferedOutputStream(os))) {
			git.add().addFilepattern(".").call();
			git.commit().setMessage("Initial commit").call();
			write(new File(folder
			dfmt.setRepository(db);
			dfmt.setPathFilter(PathFilter.create("folder"));
			DirCacheIterator oldTree = new DirCacheIterator(db.readDirCache());
			FileTreeIterator newTree = new FileTreeIterator(db);

			dfmt.format(oldTree
			dfmt.flush();

			String actual = os.toString("UTF-8");
			String expected =
					"diff --git a/folder/folder.txt b/folder/folder.txt\n"
					+ "index 0119635..95c4c65 100644\n"
					+ "--- a/folder/folder.txt\n" + "+++ b/folder/folder.txt\n"
					+ "@@ -1 +1 @@\n" + "-folder\n"
					+ "\\ No newline at end of file\n" + "+folder change\n"
					+ "\\ No newline at end of file\n";

			assertEquals(expected
		}
	}

	@Test
	public void testDiffRootNullToTree() throws Exception {
		write(new File(db.getDirectory().getParent()
		File folder = new File(db.getDirectory().getParent()
		FileUtils.mkdir(folder);
		write(new File(folder
		try (Git git = new Git(db);
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				DiffFormatter dfmt = new DiffFormatter(new BufferedOutputStream(os))) {
			git.add().addFilepattern(".").call();
			RevCommit commit = git.commit().setMessage("Initial commit").call();
			write(new File(folder

			dfmt.setRepository(db);
			dfmt.setPathFilter(PathFilter.create("folder"));
			dfmt.format(null
			dfmt.flush();

			String actual = os.toString("UTF-8");
			String expected = "diff --git a/folder/folder.txt b/folder/folder.txt\n"
					+ "new file mode 100644\n"
					+ "index 0000000..0119635\n"
					+ "--- /dev/null\n"
					+ "+++ b/folder/folder.txt\n"
					+ "@@ -0
					+ "+folder\n"
					+ "\\ No newline at end of file\n";

			assertEquals(expected
		}
	}

	@Test
	public void testDiffRootTreeToNull() throws Exception {
		write(new File(db.getDirectory().getParent()
		File folder = new File(db.getDirectory().getParent()
		FileUtils.mkdir(folder);
		write(new File(folder
		try (Git git = new Git(db);
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				DiffFormatter dfmt = new DiffFormatter(new BufferedOutputStream(os));) {
			git.add().addFilepattern(".").call();
			RevCommit commit = git.commit().setMessage("Initial commit").call();
			write(new File(folder

			dfmt.setRepository(db);
			dfmt.setPathFilter(PathFilter.create("folder"));
			dfmt.format(commit.getTree().getId()
			dfmt.flush();

			String actual = os.toString("UTF-8");
			String expected = "diff --git a/folder/folder.txt b/folder/folder.txt\n"
					+ "deleted file mode 100644\n"
					+ "index 0119635..0000000\n"
					+ "--- a/folder/folder.txt\n"
					+ "+++ /dev/null\n"
					+ "@@ -1 +0
					+ "-folder\n"
					+ "\\ No newline at end of file\n";

			assertEquals(expected
		}
	}

	@Test
	public void testDiffNullToNull() throws Exception {
		try (ByteArrayOutputStream os = new ByteArrayOutputStream();
				DiffFormatter dfmt = new DiffFormatter(new BufferedOutputStream(os))) {
			dfmt.setRepository(db);
			dfmt.format((AnyObjectId) null
			dfmt.flush();

			String actual = os.toString("UTF-8");
			String expected = "";

			assertEquals(expected
		}
	}

	@Test
	public void testDiffAutoCrlfSmallFile() throws Exception {
		String content = "01234\r\n01234\r\n01234\r\n";
		String expectedDiff = "diff --git a/test.txt b/test.txt\n"
				+ "@@ -1
				+ " 01234\n";
		doAutoCrLfTest(content
	}

	@Test
	public void testDiffAutoCrlfMediumFile() throws Exception {
		String content = mediumCrLfString();
		String expectedDiff = "diff --git a/test.txt b/test.txt\n"
				+ "@@ -1
				+ " 01234567\n";
		doAutoCrLfTest(content
	}

	@Test
	public void testDiffAutoCrlfLargeFile() throws Exception {
		String content = largeCrLfString();
		String expectedDiff = "diff --git a/test.txt b/test.txt\n"
				+ "@@ -1
				+ " 012345678901234567890123456789012345678901234567\n"
				+ "+ABCD\n"
				+ " 012345678901234567890123456789012345678901234567\n"
				+ " 012345678901234567890123456789012345678901234567\n"
				+ " 012345678901234567890123456789012345678901234567\n";
		doAutoCrLfTest(content
	}

	private void doAutoCrLfTest(String content
			throws Exception {
		FileBasedConfig config = db.getConfig();
		config.setString(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_AUTOCRLF
		config.save();
		commitFile("test.txt"
		int i = content.indexOf('\n');
		content = content.substring(0
				+ content.substring(i + 1);
		writeTrashFile("test.txt"
		try (ByteArrayOutputStream os = new ByteArrayOutputStream();
				DiffFormatter dfmt = new DiffFormatter(
						new BufferedOutputStream(os))) {
			dfmt.setRepository(db);
			dfmt.format(new DirCacheIterator(db.readDirCache())
					new FileTreeIterator(db));
			dfmt.flush();

			String actual = os.toString("UTF-8");

			assertEquals(expectedDiff
		}
	}

	private static String largeCrLfString() {
		String line = "012345678901234567890123456789012345678901234567\r\n";
		StringBuilder builder = new StringBuilder(
				2 * RawText.FIRST_FEW_BYTES);
		while (builder.length() < 2 * RawText.FIRST_FEW_BYTES) {
			builder.append(line);
		}
		return builder.toString();
	}

	private static String mediumCrLfString() {
		StringBuilder builder = new StringBuilder(
				RawText.FIRST_FEW_BYTES + line.length());
		while (builder.length() <= RawText.FIRST_FEW_BYTES) {
			builder.append(line);
		}
		return builder.toString();
	}

	private static String makeDiffHeader(String pathA
			ObjectId aId
			ObjectId bId) {
		String a = aId.abbreviate(8).name();
		String b = bId.abbreviate(8).name();
				"+++ b/" + pathB + "\n";
	}

	private static String makeDiffHeaderModeChange(String pathA
			ObjectId aId
		String a = aId.abbreviate(8).name();
		String b = bId.abbreviate(8).name();
				"+++ b/" + pathB + "\n";
	}

	private ObjectId blob(String content) throws Exception {
		return testDb.blob(content).copy();
	}
}
