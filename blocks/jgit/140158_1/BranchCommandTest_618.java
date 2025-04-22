package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.jgit.api.MergeCommand.FastForwardMode;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.blame.BlameResult;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.CoreConfig.AutoCRLF;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.junit.Test;

public class BlameCommandTest extends RepositoryTestCase {

	private static String join(String... lines) {
		StringBuilder joined = new StringBuilder();
		for (String line : lines)
			joined.append(line).append('\n');
		return joined.toString();
	}

	@Test
	public void testSingleRevision() throws Exception {
		try (Git git = new Git(db)) {
			String[] content = new String[] { "first"

			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit commit = git.commit().setMessage("create file").call();

			BlameCommand command = new BlameCommand(db);
			command.setFilePath("file.txt");
			BlameResult lines = command.call();
			assertNotNull(lines);
			assertEquals(3

			for (int i = 0; i < 3; i++) {
				assertEquals(commit
				assertEquals(i
			}
		}
	}

	@Test
	public void testTwoRevisions() throws Exception {
		try (Git git = new Git(db)) {
			String[] content1 = new String[] { "first"
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit commit1 = git.commit().setMessage("create file").call();

			String[] content2 = new String[] { "first"
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit commit2 = git.commit().setMessage("create file").call();

			BlameCommand command = new BlameCommand(db);
			command.setFilePath("file.txt");
			BlameResult lines = command.call();
			assertEquals(3

			assertEquals(commit1
			assertEquals(0

			assertEquals(commit1
			assertEquals(1

			assertEquals(commit2
			assertEquals(2
		}
	}

	@Test
	public void testRename() throws Exception {
		testRename("file1.txt"
	}

	@Test
	public void testRenameInSubDir() throws Exception {
		testRename("subdir/file1.txt"
	}

	@Test
	public void testMoveToOtherDir() throws Exception {
		testRename("subdir/file1.txt"
	}

	private void testRename(String sourcePath
			throws Exception {
		try (Git git = new Git(db)) {
			String[] content1 = new String[] { "a"
			writeTrashFile(sourcePath
			git.add().addFilepattern(sourcePath).call();
			RevCommit commit1 = git.commit().setMessage("create file").call();

			writeTrashFile(destPath
			git.add().addFilepattern(destPath).call();
			git.rm().addFilepattern(sourcePath).call();
			git.commit().setMessage("moving file").call();

			String[] content2 = new String[] { "a"
			writeTrashFile(destPath
			git.add().addFilepattern(destPath).call();
			RevCommit commit3 = git.commit().setMessage("editing file").call();

			BlameCommand command = new BlameCommand(db);
			command.setFollowFileRenames(true);
			command.setFilePath(destPath);
			BlameResult lines = command.call();

			assertEquals(commit1
			assertEquals(0
			assertEquals(sourcePath

			assertEquals(commit1
			assertEquals(1
			assertEquals(sourcePath

			assertEquals(commit3
			assertEquals(2
			assertEquals(destPath
		}
	}

	@Test
	public void testTwoRenames() throws Exception {
		try (Git git = new Git(db)) {
			String[] content1 = new String[] { "a" };
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit commit1 = git.commit().setMessage("create file").call();

			writeTrashFile("file1.txt"
			git.add().addFilepattern("file1.txt").call();
			git.rm().addFilepattern("file.txt").call();
			git.commit().setMessage("moving file").call();

			String[] content2 = new String[] { "a"
			writeTrashFile("file1.txt"
			git.add().addFilepattern("file1.txt").call();
			RevCommit commit3 = git.commit().setMessage("editing file").call();

			writeTrashFile("file2.txt"
			git.add().addFilepattern("file2.txt").call();
			git.rm().addFilepattern("file1.txt").call();
			git.commit().setMessage("moving file again").call();

			BlameCommand command = new BlameCommand(db);
			command.setFollowFileRenames(true);
			command.setFilePath("file2.txt");
			BlameResult lines = command.call();

			assertEquals(commit1
			assertEquals(0
			assertEquals("file.txt"

			assertEquals(commit3
			assertEquals(1
			assertEquals("file1.txt"
		}
	}

	@Test
	public void testDeleteTrailingLines() throws Exception {
		try (Git git = new Git(db)) {
			String[] content1 = new String[] { "a"
			String[] content2 = new String[] { "a"

			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit commit1 = git.commit().setMessage("create file").call();

			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("edit file").call();

			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("edit file").call();

			BlameCommand command = new BlameCommand(db);

			command.setFilePath("file.txt");
			BlameResult lines = command.call();
			assertEquals(content2.length

			assertEquals(commit1
			assertEquals(commit1

			assertEquals(0
			assertEquals(1
		}
	}

	@Test
	public void testDeleteMiddleLines() throws Exception {
		try (Git git = new Git(db)) {
			String[] content1 = new String[] { "a"
			String[] content2 = new String[] { "a"

			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit commit1 = git.commit().setMessage("edit file").call();

			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("edit file").call();

			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("edit file").call();

			BlameCommand command = new BlameCommand(db);

			command.setFilePath("file.txt");
			BlameResult lines = command.call();
			assertEquals(content2.length

			assertEquals(commit1
			assertEquals(0

			assertEquals(commit1
			assertEquals(1

			assertEquals(commit1
			assertEquals(2
		}
	}

	@Test
	public void testEditAllLines() throws Exception {
		try (Git git = new Git(db)) {
			String[] content1 = new String[] { "a"
			String[] content2 = new String[] { "b"

			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("edit file").call();

			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit commit2 = git.commit().setMessage("create file").call();

			BlameCommand command = new BlameCommand(db);

			command.setFilePath("file.txt");
			BlameResult lines = command.call();
			assertEquals(content2.length
			assertEquals(commit2
			assertEquals(commit2
		}
	}

	@Test
	public void testMiddleClearAllLines() throws Exception {
		try (Git git = new Git(db)) {
			String[] content1 = new String[] { "a"

			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("edit file").call();

			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("create file").call();

			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit commit3 = git.commit().setMessage("edit file").call();

			BlameCommand command = new BlameCommand(db);

			command.setFilePath("file.txt");
			BlameResult lines = command.call();
			assertEquals(content1.length
			assertEquals(commit3
			assertEquals(commit3
			assertEquals(commit3
		}
	}

	@Test
	public void testCoreAutoCrlf1() throws Exception {
		testCoreAutoCrlf(AutoCRLF.INPUT
	}

	@Test
	public void testCoreAutoCrlf2() throws Exception {
		testCoreAutoCrlf(AutoCRLF.FALSE
	}

	@Test
	public void testCoreAutoCrlf3() throws Exception {
		testCoreAutoCrlf(AutoCRLF.INPUT
	}

	@Test
	public void testCoreAutoCrlf4() throws Exception {
		testCoreAutoCrlf(AutoCRLF.FALSE
	}

	@Test
	public void testCoreAutoCrlf5() throws Exception {
		testCoreAutoCrlf(AutoCRLF.INPUT
	}

	private void testCoreAutoCrlf(AutoCRLF modeForCommitting
			AutoCRLF modeForReset) throws Exception {
		try (Git git = new Git(db)) {
			FileBasedConfig config = db.getConfig();
			config.setEnum(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_AUTOCRLF
			config.save();

			String joinedCrlf = "a\r\nb\r\nc\r\n";
			File trashFile = writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit commit = git.commit().setMessage("create file").call();

			trashFile.delete();
			config.setEnum(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_AUTOCRLF
			config.save();
			git.reset().setMode(ResetType.HARD).call();

			BlameCommand command = new BlameCommand(db);
			command.setFilePath("file.txt");
			BlameResult lines = command.call();

			assertEquals(3
			assertEquals(commit
			assertEquals(commit
			assertEquals(commit
		}
	}

	@Test
	public void testConflictingMerge1() throws Exception {
		try (Git git = new Git(db)) {
			RevCommit base = commitFile("file.txt"
					"master");

			git.checkout().setName("side").setCreateBranch(true)
					.setStartPoint(base).call();
			RevCommit side = commitFile("file.txt"
					join("0"

			commitFile("file.txt"

			checkoutBranch("refs/heads/master");
			git.merge().include(side).call();

			RevCommit merge = commitFile("file.txt"
					join("0"

			BlameCommand command = new BlameCommand(db);
			command.setFilePath("file.txt");
			BlameResult lines = command.call();

			assertEquals(5
			assertEquals(base
			assertEquals(side
			assertEquals(base
			assertEquals(merge
			assertEquals(base
		}
	}

	@Test
	public void testConflictingMerge2() throws Exception {
		try (Git git = new Git(db)) {
			RevCommit base = commitFile("file.txt"
					"master");

			commitFile("file.txt"

			git.checkout().setName("side").setCreateBranch(true)
					.setStartPoint(base).call();
			RevCommit side = commitFile("file.txt"
					join("0"

			checkoutBranch("refs/heads/master");
			git.merge().include(side).call();

			RevCommit merge = commitFile("file.txt"
					join("0"

			BlameCommand command = new BlameCommand(db);
			command.setFilePath("file.txt");
			BlameResult lines = command.call();

			assertEquals(5
			assertEquals(base
			assertEquals(side
			assertEquals(base
			assertEquals(merge
			assertEquals(base
		}
	}

	@Test
	public void testWhitespaceMerge() throws Exception {
		try (Git git = new Git(db)) {
			RevCommit base = commitFile("file.txt"
			RevCommit side = commitFile("file.txt"
					"side");

			checkoutBranch("refs/heads/master");
			git.merge().setFastForward(FastForwardMode.NO_FF).include(side).call();

			writeTrashFile("file.txt"
			RevCommit merge = git.commit().setAll(true).setMessage("merge")
					.setAmend(true)
					.call();

			BlameCommand command = new BlameCommand(db);
			command.setFilePath("file.txt")
					.setTextComparator(RawTextComparator.WS_IGNORE_ALL)
					.setStartCommit(merge.getId());
			BlameResult lines = command.call();

			assertEquals(3
			assertEquals(base
			assertEquals(base
			assertEquals(side
		}
	}

	@Test
	public void testBlameWithNulByteInHistory() throws Exception {
		try (Git git = new Git(db)) {
			String[] content1 = { "First line"
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit c1 = git.commit().setMessage("create file").call();

			String[] content2 = { "First line"
					"Another line" };
			assertTrue("Content should contain a NUL byte"
					content2[1].indexOf(0) > 0);
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("add line with NUL").call();

			String[] content3 = { "First line"
					"Third line" };
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit c3 = git.commit().setMessage("change third line").call();

			String[] content4 = { "First line"
					"Third line" };
			assertTrue("Content should not contain a NUL byte"
					content4[1].indexOf(0) < 0);
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit c4 = git.commit().setMessage("fix NUL line").call();

			BlameResult lines = git.blame().setFilePath("file.txt").call();
			assertEquals(3
			assertEquals(c1
			assertEquals(c4
			assertEquals(c3
		}
	}

	@Test
	public void testBlameWithNulByteInTopRevision() throws Exception {
		try (Git git = new Git(db)) {
			String[] content1 = { "First line"
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit c1 = git.commit().setMessage("create file").call();

			String[] content2 = { "First line"
					"Another line" };
			assertTrue("Content should contain a NUL byte"
					content2[1].indexOf(0) > 0);
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit c2 = git.commit().setMessage("add line with NUL").call();

			String[] content3 = { "First line"
					"Third line" };
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit c3 = git.commit().setMessage("change third line").call();

			BlameResult lines = git.blame().setFilePath("file.txt").call();
			assertEquals(3
			assertEquals(c1
			assertEquals(c2
			assertEquals(c3
		}
	}

}
