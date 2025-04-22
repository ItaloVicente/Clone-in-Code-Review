package org.eclipse.jgit.api;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.util.FileUtils.RECURSIVE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import org.eclipse.jgit.api.errors.FilterFailedException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.attributes.FilterCommandRegistry;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lfs.BuiltinLFS;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.WorkingTreeOptions;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class AddCommandTest extends RepositoryTestCase {
	@DataPoints
	public static boolean[] sleepBeforeAddOptions = { true


	@Override
	public void setUp() throws Exception {
		BuiltinLFS.register();
		super.setUp();
	}

	@Test
	public void testAddNothing() throws GitAPIException {
		try (Git git = new Git(db)) {
			git.add().call();
			fail("Expected IllegalArgumentException");
		} catch (NoFilepatternException e) {
		}

	}

	@Test
	public void testAddNonExistingSingleFile() throws GitAPIException {
		try (Git git = new Git(db)) {
			DirCache dc = git.add().addFilepattern("a.txt").call();
			assertEquals(0
		}
	}

	@Test
	public void testAddExistingSingleFile() throws IOException
		File file = new File(db.getWorkTree()
		FileUtils.createNewFile(file);
		try (PrintWriter writer = new PrintWriter(file
			writer.print("content");
		}

		try (Git git = new Git(db)) {
			git.add().addFilepattern("a.txt").call();

			assertEquals(
					"[a.txt
					indexState(CONTENT));
		}
	}

	@Test
	public void testCleanFilter() throws IOException
		writeTrashFile(".gitattributes"
		writeTrashFile("src/a.tmp"
		writeTrashFile("src/a.txt"
		File script = writeTempFile("sed s/o/e/g");

		try (Git git = new Git(db)) {
			StoredConfig config = git.getRepository().getConfig();
			config.setString("filter"
					"sh " + slashify(script.getPath()));
			config.save();

			git.add().addFilepattern("src/a.txt").addFilepattern("src/a.tmp")
					.call();

			assertEquals(
					"[src/a.tmp
					indexState(CONTENT));
		}
	}

	@Theory
	public void testBuiltinFilters(boolean sleepBeforeAdd)
			throws IOException
			GitAPIException
		writeTrashFile(".gitattributes"
		writeTrashFile("src/a.tmp"
		File script = writeTempFile("sed s/o/e/g");
		File f = writeTrashFile("src/a.txt"

		try (Git git = new Git(db)) {
			if (!sleepBeforeAdd) {
				fsTick(f);
			}
			git.add().addFilepattern(".gitattributes").call();
			StoredConfig config = git.getRepository().getConfig();
			config.setString("filter"
					"sh " + slashify(script.getPath()));
			config.setString("filter"
					"sh " + slashify(script.getPath()));
			config.setBoolean("filter"
			config.save();

			if (!sleepBeforeAdd) {
				fsTick(f);
			}
			git.add().addFilepattern("src/a.txt").addFilepattern("src/a.tmp")
					.addFilepattern(".gitattributes").call();

			assertEquals(
					"[.gitattributes
					indexState(CONTENT));

			RevCommit c1 = git.commit().setMessage("c1").call();
			assertTrue(git.status().call().isClean());
			f = writeTrashFile("src/a.txt"
			if (!sleepBeforeAdd) {
				fsTick(f);
			}
			git.add().addFilepattern("src/a.txt").call();
			git.commit().setMessage("c2").call();
			assertTrue(git.status().call().isClean());
			assertEquals(
					"[.gitattributes
					indexState(CONTENT));
			assertEquals("foobar\n"
			git.checkout().setName(c1.getName()).call();
			assertEquals(
					"[.gitattributes
					indexState(CONTENT));
			assertEquals(
					"foo\n"
		}
	}

	@Theory
	public void testBuiltinCleanFilter(boolean sleepBeforeAdd)
			throws IOException
		writeTrashFile(".gitattributes"
		writeTrashFile("src/a.tmp"
		File script = writeTempFile("sed s/o/e/g");
		File f = writeTrashFile("src/a.txt"

		FilterCommandRegistry.unregister(
				org.eclipse.jgit.lib.Constants.BUILTIN_FILTER_PREFIX
						+ "lfs/smudge");

		try (Git git = new Git(db)) {
			if (!sleepBeforeAdd) {
				fsTick(f);
			}
			git.add().addFilepattern(".gitattributes").call();
			StoredConfig config = git.getRepository().getConfig();
			config.setString("filter"
					"sh " + slashify(script.getPath()));
			config.setString("filter"
					"sh " + slashify(script.getPath()));
			config.setBoolean("filter"
			config.save();

			if (!sleepBeforeAdd) {
				fsTick(f);
			}
			git.add().addFilepattern("src/a.txt").addFilepattern("src/a.tmp")
					.addFilepattern(".gitattributes").call();

			assertEquals(
					"[.gitattributes
					indexState(CONTENT));

			RevCommit c1 = git.commit().setMessage("c1").call();
			assertTrue(git.status().call().isClean());
			f = writeTrashFile("src/a.txt"
			if (!sleepBeforeAdd) {
				fsTick(f);
			}
			git.add().addFilepattern("src/a.txt").call();
			git.commit().setMessage("c2").call();
			assertTrue(git.status().call().isClean());
			assertEquals(
					"[.gitattributes
					indexState(CONTENT));
			assertEquals("foobar\n"
			git.checkout().setName(c1.getName()).call();
			assertEquals(
					"[.gitattributes
					indexState(CONTENT));
			assertEquals(
					read("src/a.txt"));
		}
	}

	@Test
	public void testAttributesWithTreeWalkFilter()
			throws IOException
		writeTrashFile(".gitattributes"
		writeTrashFile("src/a.tmp"
		writeTrashFile("src/a.txt"
		File script = writeTempFile("sed s/o/e/g");

		try (Git git = new Git(db)) {
			StoredConfig config = git.getRepository().getConfig();
			config.setString("filter"
					"sh " + slashify(script.getPath()));
			config.save();

			git.add().addFilepattern(".gitattributes").call();
			git.commit().setMessage("attr").call();
			git.add().addFilepattern("src/a.txt").addFilepattern("src/a.tmp")
					.addFilepattern(".gitattributes").call();
			git.commit().setMessage("c1").call();
			assertTrue(git.status().call().isClean());
		}
	}

	@Test
	public void testAttributesConflictingMatch() throws Exception {
		writeTrashFile(".gitattributes"
		writeTrashFile("foo/bar.jar"
		try (Git git = new Git(db)) {
			git.add().addFilepattern(".").call();
			assertEquals(
					"[.gitattributes
							+ "[foo/bar.jar
					indexState(CONTENT));
		}
	}

	@Test
	public void testCleanFilterEnvironment()
			throws IOException
		writeTrashFile(".gitattributes"
		writeTrashFile("src/a.txt"
		File script = writeTempFile("echo $GIT_DIR; echo 1 >xyz");

		try (Git git = new Git(db)) {
			StoredConfig config = git.getRepository().getConfig();
			config.setString("filter"
					"sh " + slashify(script.getPath()));
			config.save();
			git.add().addFilepattern("src/a.txt").call();

			String gitDir = db.getDirectory().getAbsolutePath();
			assertEquals("[src/a.txt
					+ "\n]"
			assertTrue(new File(db.getWorkTree()
		}
	}

	@Test
	public void testMultipleCleanFilter() throws IOException
		writeTrashFile(".gitattributes"
				"*.txt filter=tstFilter\n*.tmp filter=tstFilter2");
		writeTrashFile("src/a.tmp"
		writeTrashFile("src/a.txt"
		File script = writeTempFile("sed s/o/e/g");
		File script2 = writeTempFile("sed s/f/x/g");

		try (Git git = new Git(db)) {
			StoredConfig config = git.getRepository().getConfig();
			config.setString("filter"
					"sh " + slashify(script.getPath()));
			config.setString("filter"
					"sh " + slashify(script2.getPath()));
			config.save();

			git.add().addFilepattern("src/a.txt").addFilepattern("src/a.tmp")
					.call();

			assertEquals(
					"[src/a.tmp
					indexState(CONTENT));

		}
	}

	@Test
	public void testCommandInjection() throws IOException
		writeTrashFile("; echo virus"
		File script = writeTempFile("sed s/o/e/g");

		try (Git git = new Git(db)) {
			StoredConfig config = git.getRepository().getConfig();
			config.setString("filter"
					"sh " + slashify(script.getPath()) + " %f");
			writeTrashFile(".gitattributes"

			git.add().addFilepattern("; echo virus").call();
			assertEquals("[; echo virus
					indexState(CONTENT));
		}
	}

	@Test
	public void testBadCleanFilter() throws IOException
		writeTrashFile("a.txt"
		File script = writeTempFile("sedfoo s/o/e/g");

		try (Git git = new Git(db)) {
			StoredConfig config = git.getRepository().getConfig();
			config.setString("filter"
					"sh " + script.getPath());
			config.save();
			writeTrashFile(".gitattributes"

			try {
				git.add().addFilepattern("a.txt").call();
				fail("Didn't received the expected exception");
			} catch (FilterFailedException e) {
				assertEquals(127
			}
		}
	}

	@Test
	public void testBadCleanFilter2() throws IOException
		writeTrashFile("a.txt"
		File script = writeTempFile("sed s/o/e/g");

		try (Git git = new Git(db)) {
			StoredConfig config = git.getRepository().getConfig();
			config.setString("filter"
					"shfoo " + script.getPath());
			config.save();
			writeTrashFile(".gitattributes"

			try {
				git.add().addFilepattern("a.txt").call();
				fail("Didn't received the expected exception");
			} catch (FilterFailedException e) {
				assertEquals(127
			}
		}
	}

	@Test
	public void testCleanFilterReturning12() throws IOException
			GitAPIException {
		writeTrashFile("a.txt"
		File script = writeTempFile("exit 12");

		try (Git git = new Git(db)) {
			StoredConfig config = git.getRepository().getConfig();
			config.setString("filter"
					"sh " + slashify(script.getPath()));
			config.save();
			writeTrashFile(".gitattributes"

			try {
				git.add().addFilepattern("a.txt").call();
				fail("Didn't received the expected exception");
			} catch (FilterFailedException e) {
				assertEquals(12
			}
		}
	}

	@Test
	public void testNotApplicableFilter() throws IOException
		writeTrashFile("a.txt"
		File script = writeTempFile("sed s/o/e/g");

		try (Git git = new Git(db)) {
			StoredConfig config = git.getRepository().getConfig();
			config.setString("filter"
					"sh " + script.getPath());
			config.save();
			writeTrashFile(".gitattributes"

			git.add().addFilepattern("a.txt").call();

			assertEquals("[a.txt
					indexState(CONTENT));
		}
	}

	private File writeTempFile(String body) throws IOException {
		File f = File.createTempFile("AddCommandTest_"
		JGitTestUtil.write(f
		return f;
	}

	@Test
	public void testAddExistingSingleSmallFileWithNewLine() throws IOException
			GitAPIException {
		File file = new File(db.getWorkTree()
		FileUtils.createNewFile(file);
		try (PrintWriter writer = new PrintWriter(file
			writer.print("row1\r\nrow2");
		}

		try (Git git = new Git(db)) {
			db.getConfig().setString("core"
			git.add().addFilepattern("a.txt").call();
			assertEquals("[a.txt
					indexState(CONTENT));
			db.getConfig().setString("core"
			git.add().addFilepattern("a.txt").call();
			assertEquals("[a.txt
					indexState(CONTENT));
			db.getConfig().setString("core"
			git.add().addFilepattern("a.txt").call();
			assertEquals("[a.txt
					indexState(CONTENT));
		}
	}

	@Test
	public void testAddExistingSingleMediumSizeFileWithNewLine()
			throws IOException
		File file = new File(db.getWorkTree()
		FileUtils.createNewFile(file);
		StringBuilder data = new StringBuilder();
		for (int i = 0; i < 1000; ++i) {
			data.append("row1\r\nrow2");
		}
		String crData = data.toString();
		try (PrintWriter writer = new PrintWriter(file
			writer.print(crData);
		}
		try (Git git = new Git(db)) {
			db.getConfig().setString("core"
			git.add().addFilepattern("a.txt").call();
			assertEquals("[a.txt
					indexState(CONTENT));
			db.getConfig().setString("core"
			git.add().addFilepattern("a.txt").call();
			assertEquals("[a.txt
					indexState(CONTENT));
			db.getConfig().setString("core"
			git.add().addFilepattern("a.txt").call();
			assertEquals("[a.txt
					indexState(CONTENT));
		}
	}

	@Test
	public void testAddExistingSingleBinaryFile() throws IOException
			GitAPIException {
		File file = new File(db.getWorkTree()
		FileUtils.createNewFile(file);
		try (PrintWriter writer = new PrintWriter(file
			writer.print("row1\r\nrow2\u0000");
		}

		try (Git git = new Git(db)) {
			db.getConfig().setString("core"
			git.add().addFilepattern("a.txt").call();
			assertEquals("[a.txt
					indexState(CONTENT));
			db.getConfig().setString("core"
			git.add().addFilepattern("a.txt").call();
			assertEquals("[a.txt
					indexState(CONTENT));
			db.getConfig().setString("core"
			git.add().addFilepattern("a.txt").call();
			assertEquals("[a.txt
					indexState(CONTENT));
		}
	}

	@Test
	public void testAddExistingSingleFileInSubDir() throws IOException
			GitAPIException {
		FileUtils.mkdir(new File(db.getWorkTree()
		File file = new File(db.getWorkTree()
		FileUtils.createNewFile(file);
		try (PrintWriter writer = new PrintWriter(file
			writer.print("content");
		}

		try (Git git = new Git(db)) {
			git.add().addFilepattern("sub/a.txt").call();

			assertEquals(
					"[sub/a.txt
					indexState(CONTENT));
		}
	}

	@Test
	public void testAddExistingSingleFileTwice() throws IOException
			GitAPIException {
		File file = new File(db.getWorkTree()
		FileUtils.createNewFile(file);
		try (PrintWriter writer = new PrintWriter(file
			writer.print("content");
		}

		try (Git git = new Git(db)) {
			DirCache dc = git.add().addFilepattern("a.txt").call();

			dc.getEntry(0).getObjectId();

			try (PrintWriter writer = new PrintWriter(file
				writer.print("other content");
			}

			dc = git.add().addFilepattern("a.txt").call();

			assertEquals(
					"[a.txt
					indexState(CONTENT));
		}
	}

	@Test
	public void testAddExistingSingleFileTwiceWithCommit() throws Exception {
		File file = new File(db.getWorkTree()
		FileUtils.createNewFile(file);
		try (PrintWriter writer = new PrintWriter(file
			writer.print("content");
		}

		try (Git git = new Git(db)) {
			DirCache dc = git.add().addFilepattern("a.txt").call();

			dc.getEntry(0).getObjectId();

			git.commit().setMessage("commit a.txt").call();

			try (PrintWriter writer = new PrintWriter(file
				writer.print("other content");
			}

			dc = git.add().addFilepattern("a.txt").call();

			assertEquals(
					"[a.txt
					indexState(CONTENT));
		}
	}

	@Test
	public void testAddRemovedFile() throws Exception {
		File file = new File(db.getWorkTree()
		FileUtils.createNewFile(file);
		try (PrintWriter writer = new PrintWriter(file
			writer.print("content");
		}

		try (Git git = new Git(db)) {
			DirCache dc = git.add().addFilepattern("a.txt").call();

			dc.getEntry(0).getObjectId();
			FileUtils.delete(file);

			dc = git.add().addFilepattern("a.txt").call();

			assertEquals(
					"[a.txt
					indexState(CONTENT));
		}
	}

	@Test
	public void testAddRemovedCommittedFile() throws Exception {
		File file = new File(db.getWorkTree()
		FileUtils.createNewFile(file);
		try (PrintWriter writer = new PrintWriter(file
			writer.print("content");
		}

		try (Git git = new Git(db)) {
			DirCache dc = git.add().addFilepattern("a.txt").call();

			git.commit().setMessage("commit a.txt").call();

			dc.getEntry(0).getObjectId();
			FileUtils.delete(file);

			dc = git.add().addFilepattern("a.txt").call();

			assertEquals(
					"[a.txt
					indexState(CONTENT));
		}
	}

	@Test
	public void testAddWithConflicts() throws Exception {

		File file = new File(db.getWorkTree()
		FileUtils.createNewFile(file);
		try (PrintWriter writer = new PrintWriter(file
			writer.print("content");
		}

		File file2 = new File(db.getWorkTree()
		FileUtils.createNewFile(file2);
		try (PrintWriter writer = new PrintWriter(file2
			writer.print("content b");
		}

		ObjectInserter newObjectInserter = db.newObjectInserter();
		DirCache dc = db.lockDirCache();
		DirCacheBuilder builder = dc.builder();

		addEntryToBuilder("b.txt"
		addEntryToBuilder("a.txt"

		try (PrintWriter writer = new PrintWriter(file
			writer.print("other content");
		}
		addEntryToBuilder("a.txt"

		try (PrintWriter writer = new PrintWriter(file
			writer.print("our content");
		}
		addEntryToBuilder("a.txt"
				.getObjectId();

		builder.commit();

		assertEquals(
				"[a.txt
				"[a.txt
				"[a.txt
				"[b.txt
				indexState(CONTENT));


		try (Git git = new Git(db)) {
			dc = git.add().addFilepattern("a.txt").call();

			assertEquals(
					"[a.txt
					"[b.txt
					indexState(CONTENT));
		}
	}

	@Test
	public void testAddTwoFiles() throws Exception  {
		File file = new File(db.getWorkTree()
		FileUtils.createNewFile(file);
		try (PrintWriter writer = new PrintWriter(file
			writer.print("content");
		}

		File file2 = new File(db.getWorkTree()
		FileUtils.createNewFile(file2);
		try (PrintWriter writer = new PrintWriter(file2
			writer.print("content b");
		}

		try (Git git = new Git(db)) {
			git.add().addFilepattern("a.txt").addFilepattern("b.txt").call();
			assertEquals(
					"[a.txt
					"[b.txt
					indexState(CONTENT));
		}
	}

	@Test
	public void testAddFolder() throws Exception  {
		FileUtils.mkdir(new File(db.getWorkTree()
		File file = new File(db.getWorkTree()
		FileUtils.createNewFile(file);
		try (PrintWriter writer = new PrintWriter(file
			writer.print("content");
		}

		File file2 = new File(db.getWorkTree()
		FileUtils.createNewFile(file2);
		try (PrintWriter writer = new PrintWriter(file2
			writer.print("content b");
		}

		try (Git git = new Git(db)) {
			git.add().addFilepattern("sub").call();
			assertEquals(
					"[sub/a.txt
					"[sub/b.txt
					indexState(CONTENT));
		}
	}

	@Test
	public void testAddIgnoredFile() throws Exception  {
		FileUtils.mkdir(new File(db.getWorkTree()
		File file = new File(db.getWorkTree()
		FileUtils.createNewFile(file);
		try (PrintWriter writer = new PrintWriter(file
			writer.print("content");
		}

		File ignoreFile = new File(db.getWorkTree()
		FileUtils.createNewFile(ignoreFile);
		try (PrintWriter writer = new PrintWriter(ignoreFile
			writer.print("sub/b.txt");
		}

		File file2 = new File(db.getWorkTree()
		FileUtils.createNewFile(file2);
		try (PrintWriter writer = new PrintWriter(file2
			writer.print("content b");
		}

		try (Git git = new Git(db)) {
			git.add().addFilepattern("sub").call();

			assertEquals(
					"[sub/a.txt
					indexState(CONTENT));
		}
	}

	@Test
	public void testAddWholeRepo() throws Exception  {
		FileUtils.mkdir(new File(db.getWorkTree()
		File file = new File(db.getWorkTree()
		FileUtils.createNewFile(file);
		try (PrintWriter writer = new PrintWriter(file
			writer.print("content");
		}

		File file2 = new File(db.getWorkTree()
		FileUtils.createNewFile(file2);
		try (PrintWriter writer = new PrintWriter(file2
			writer.print("content b");
		}

		try (Git git = new Git(db)) {
			git.add().addFilepattern(".").call();
			assertEquals(
					"[sub/a.txt
					"[sub/b.txt
					indexState(CONTENT));
		}
	}

	@Test
	public void testAddWithoutParameterUpdate() throws Exception {
		FileUtils.mkdir(new File(db.getWorkTree()
		File file = new File(db.getWorkTree()
		FileUtils.createNewFile(file);
		try (PrintWriter writer = new PrintWriter(file
			writer.print("content");
		}

		File file2 = new File(db.getWorkTree()
		FileUtils.createNewFile(file2);
		try (PrintWriter writer = new PrintWriter(file2
			writer.print("content b");
		}

		try (Git git = new Git(db)) {
			git.add().addFilepattern("sub").call();

			assertEquals(
					"[sub/a.txt
					"[sub/b.txt
					indexState(CONTENT));

			git.commit().setMessage("commit").call();

			File file3 = new File(db.getWorkTree()
			FileUtils.createNewFile(file3);
			try (PrintWriter writer = new PrintWriter(file3
				writer.print("content c");
			}

			try (PrintWriter writer = new PrintWriter(file
				writer.print("modified content");
			}

			FileUtils.delete(file2);

			git.add().addFilepattern("sub").call();
			assertEquals(
					"[sub/a.txt
					"[sub/b.txt
					"[sub/c.txt
					indexState(CONTENT));
		}
	}

	@Test
	public void testAddWithParameterUpdate() throws Exception {
		FileUtils.mkdir(new File(db.getWorkTree()
		File file = new File(db.getWorkTree()
		FileUtils.createNewFile(file);
		try (PrintWriter writer = new PrintWriter(file
			writer.print("content");
		}

		File file2 = new File(db.getWorkTree()
		FileUtils.createNewFile(file2);
		try (PrintWriter writer = new PrintWriter(file2
			writer.print("content b");
		}

		try (Git git = new Git(db)) {
			git.add().addFilepattern("sub").call();

			assertEquals(
					"[sub/a.txt
					"[sub/b.txt
					indexState(CONTENT));

			git.commit().setMessage("commit").call();

			File file3 = new File(db.getWorkTree()
			FileUtils.createNewFile(file3);
			try (PrintWriter writer = new PrintWriter(file3
				writer.print("content c");
			}

			try (PrintWriter writer = new PrintWriter(file
				writer.print("modified content");
			}

			FileUtils.delete(file2);

			git.add().addFilepattern("sub").setUpdate(true).call();
			assertEquals(
					"[sub/a.txt
					indexState(CONTENT));
		}
	}

	@Test
	public void testAssumeUnchanged() throws Exception {
		try (Git git = new Git(db)) {
			String path = "a.txt";
			writeTrashFile(path
			git.add().addFilepattern(path).call();
			String path2 = "b.txt";
			writeTrashFile(path2
			git.add().addFilepattern(path2).call();
			git.commit().setMessage("commit").call();
			assertEquals("[a.txt
					+ "content
					+ "[b.txt
					+ "assume-unchanged:false]"
					| ASSUME_UNCHANGED));
			assumeUnchanged(path2);
			assertEquals("[a.txt
					+ "assume-unchanged:false][b.txt
					+ "content:content
					| ASSUME_UNCHANGED));
			writeTrashFile(path
			writeTrashFile(path2

			git.add().addFilepattern(".").call();

			assertEquals("[a.txt
					indexState(CONTENT
					| ASSUME_UNCHANGED));
		}
	}

	@Test
	public void testReplaceFileWithDirectory()
			throws IOException
		try (Git git = new Git(db)) {
			writeTrashFile("df"
			git.add().addFilepattern("df").call();
			assertEquals("[df
					indexState(CONTENT));
			FileUtils.delete(new File(db.getWorkTree()
			writeTrashFile("df/f"
			git.add().addFilepattern("df").call();
			assertEquals("[df/f
					indexState(CONTENT));
		}
	}

	@Test
	public void testReplaceDirectoryWithFile()
			throws IOException
		try (Git git = new Git(db)) {
			writeTrashFile("df/f"
			git.add().addFilepattern("df").call();
			assertEquals("[df/f
					indexState(CONTENT));
			FileUtils.delete(new File(db.getWorkTree()
			writeTrashFile("df"
			git.add().addFilepattern("df").call();
			assertEquals("[df
					indexState(CONTENT));
		}
	}

	@Test
	public void testReplaceFileByPartOfDirectory()
			throws IOException
		try (Git git = new Git(db)) {
			writeTrashFile("src/main"
			writeTrashFile("src/main"
			writeTrashFile("z"
			git.add().addFilepattern("src/main/df")
				.addFilepattern("src/main/z")
				.addFilepattern("z")
				.call();
			assertEquals(
					"[src/main/df
					"[src/main/z
					"[z
					indexState(CONTENT));
			FileUtils.delete(new File(db.getWorkTree()
			writeTrashFile("src/main/df"
			writeTrashFile("src/main/df"
			git.add().addFilepattern("src/main/df/a").call();
			assertEquals(
					"[src/main/df/a
					"[src/main/z
					"[z
					indexState(CONTENT));
		}
	}

	@Test
	public void testReplaceDirectoryConflictsWithFile()
			throws IOException
		DirCache dc = db.lockDirCache();
		try (ObjectInserter oi = db.newObjectInserter()) {
			DirCacheBuilder builder = dc.builder();
			File f = writeTrashFile("a"
			addEntryToBuilder("a"

			f = writeTrashFile("a"
			addEntryToBuilder("a/df"

			f = writeTrashFile("a"
			addEntryToBuilder("a/df"

			f = writeTrashFile("z"
			addEntryToBuilder("z"
			builder.commit();
		}
		assertEquals(
				"[a
				"[a/df
				"[a/df
				"[z
				indexState(CONTENT));

		try (Git git = new Git(db)) {
			FileUtils.delete(new File(db.getWorkTree()
			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			assertEquals("[a
					"[z
					indexState(CONTENT));
		}
	}

	@Test
	public void testExecutableRetention() throws Exception {
		StoredConfig config = db.getConfig();
		config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_FILEMODE
		config.save();

		FS executableFs = new FS() {

			@Override
			public boolean supportsExecute() {
				return true;
			}

			@Override
			public boolean setExecute(File f
				return true;
			}

			@Override
			public ProcessBuilder runInShell(String cmd
				return null;
			}

			@Override
			public boolean retryFailedLockFileCommit() {
				return false;
			}

			@Override
			public FS newInstance() {
				return this;
			}

			@Override
			protected File discoverGitExe() {
				return null;
			}

			@Override
			public boolean canExecute(File f) {
				try {
					return read(f).startsWith("binary:");
				} catch (IOException e) {
					return false;
				}
			}

			@Override
			public boolean isCaseSensitive() {
				return false;
			}
		};

		Git git = Git.open(db.getDirectory()
		String path = "a.txt";
		String path2 = "a.sh";
		writeTrashFile(path
		writeTrashFile(path2
		git.add().addFilepattern(path).addFilepattern(path2).call();
		RevCommit commit1 = git.commit().setMessage("commit").call();
		try (TreeWalk walk = new TreeWalk(db)) {
			walk.addTree(commit1.getTree());
			walk.next();
			assertEquals(path2
			assertEquals(FileMode.EXECUTABLE_FILE
			walk.next();
			assertEquals(path
			assertEquals(FileMode.REGULAR_FILE
		}

		config = db.getConfig();
		config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_FILEMODE
		config.save();

		Git git2 = Git.open(db.getDirectory()
		writeTrashFile(path2
		writeTrashFile(path
		git2.add().addFilepattern(path).addFilepattern(path2).call();
		RevCommit commit2 = git2.commit().setMessage("commit2").call();
		try (TreeWalk walk = new TreeWalk(db)) {
			walk.addTree(commit2.getTree());
			walk.next();
			assertEquals(path2
			assertEquals(FileMode.EXECUTABLE_FILE
			walk.next();
			assertEquals(path
			assertEquals(FileMode.REGULAR_FILE
		}
	}

	@Test
	public void testAddGitlink() throws Exception {
		createNestedRepo("git-link-dir");
		try (Git git = new Git(db)) {
			git.add().addFilepattern("git-link-dir").call();

			assertEquals(
					"[git-link-dir
					indexState(0));
			Set<String> untrackedFiles = git.status().call().getUntracked();
			assert (untrackedFiles.isEmpty());
		}

	}

	@Test
	public void testAddSubrepoWithDirNoGitlinks() throws Exception {
		createNestedRepo("nested-repo");

		StoredConfig config = db.getConfig();
		config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_DIRNOGITLINKS
		config.save();

		assert (db.getConfig().get(WorkingTreeOptions.KEY).isDirNoGitLinks());

		try (Git git = new Git(db)) {
			git.add().addFilepattern("nested-repo").call();

			assertEquals(
					"[nested-repo/README1.md
							"[nested-repo/README2.md
					indexState(0));
		}

		config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_DIRNOGITLINKS
		config.save();

		writeTrashFile("nested-repo"

		try (Git git = new Git(db)) {
			git.add().addFilepattern("nested-repo").call();

			assertEquals(
					"[nested-repo/README1.md
							"[nested-repo/README2.md
							"[nested-repo/README3.md
					indexState(0));
		}
	}

	@Test
	public void testAddGitlinkDoesNotChange() throws Exception {
		createNestedRepo("nested-repo");

		try (Git git = new Git(db)) {
			git.add().addFilepattern("nested-repo").call();

			assertEquals(
					"[nested-repo
					indexState(0));
		}

		StoredConfig config = db.getConfig();
		config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_DIRNOGITLINKS
		config.save();

		assertTrue(
				db.getConfig().get(WorkingTreeOptions.KEY).isDirNoGitLinks());

		try (Git git = new Git(db)) {
			git.add().addFilepattern("nested-repo").call();
			assertEquals(
					"[nested-repo/README1.md
					indexState(0));
		}
	}

	private static DirCacheEntry addEntryToBuilder(String path
			ObjectInserter newObjectInserter
			throws IOException {
		ObjectId id;
		try (FileInputStream inputStream = new FileInputStream(file)) {
			id = newObjectInserter.insert(
				Constants.OBJ_BLOB
		}
		DirCacheEntry entry = new DirCacheEntry(path
		entry.setObjectId(id);
		entry.setFileMode(FileMode.REGULAR_FILE);
		entry.setLastModified(file.lastModified());
		entry.setLength((int) file.length());

		builder.add(entry);
		return entry;
	}

	private void assumeUnchanged(String path) throws IOException {
		final DirCache dirc = db.lockDirCache();
		final DirCacheEntry ent = dirc.getEntry(path);
		if (ent != null)
			ent.setAssumeValid(true);
		dirc.write();
		if (!dirc.commit())
			throw new IOException("could not commit");
	}

	private void createNestedRepo(String path) throws IOException {
		File gitLinkDir = new File(db.getWorkTree()
		FileUtils.mkdir(gitLinkDir);

		FileRepositoryBuilder nestedBuilder = new FileRepositoryBuilder();
		nestedBuilder.setWorkTree(gitLinkDir);

		Repository nestedRepo = nestedBuilder.build();
		nestedRepo.create();

		writeTrashFile(path
		writeTrashFile(path

		try (Git git = new Git(nestedRepo)) {
			git.add().addFilepattern(".").call();
			git.commit().setMessage("subrepo commit").call();
		} catch (GitAPIException e) {
			throw new RuntimeException(e);
		}
	}
}
