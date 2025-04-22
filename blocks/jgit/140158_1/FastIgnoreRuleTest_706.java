package org.eclipse.jgit.ignore;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.WorkingTreeIterator;
import org.eclipse.jgit.treewalk.filter.NotIgnoredFilter;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FS.ExecutionResult;
import org.eclipse.jgit.util.RawParseUtils;
import org.eclipse.jgit.util.TemporaryBuffer;
import org.junit.Before;
import org.junit.Test;

public class CGitIgnoreTest extends RepositoryTestCase {

	@Before
	public void initRepo() throws IOException {
		File fakeUserGitignore = writeTrashFile(".fake_user_gitignore"
		StoredConfig config = db.getConfig();
		config.setString("core"
				fakeUserGitignore.getAbsolutePath());
		config.setBoolean("core"
		config.save();
	}

	private void createFiles(String... paths) throws IOException {
		for (String path : paths) {
			writeTrashFile(path
		}
	}

	private String toString(TemporaryBuffer b) throws IOException {
		return RawParseUtils.decode(b.toByteArray());
	}

	private String[] cgitIgnored() throws Exception {
		FS fs = db.getFS();
		ProcessBuilder builder = fs.runInShell("git"
				"--ignored"
		builder.directory(db.getWorkTree());
		builder.environment().put("HOME"
		ExecutionResult result = fs.execute(builder
				new ByteArrayInputStream(new byte[0]));
		String errorOut = toString(result.getStderr());
		assertEquals("External git failed"
				"exit " + result.getRc() + '\n' + errorOut);
		try (BufferedReader r = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(result.getStdout().openInputStream())
				UTF_8))) {
			return r.lines().toArray(String[]::new);
		}
	}

	private String[] cgitUntracked() throws Exception {
		FS fs = db.getFS();
		ProcessBuilder builder = fs.runInShell("git"
				new String[] { "ls-files"
		builder.directory(db.getWorkTree());
		builder.environment().put("HOME"
		ExecutionResult result = fs.execute(builder
				new ByteArrayInputStream(new byte[0]));
		String errorOut = toString(result.getStderr());
		assertEquals("External git failed"
				"exit " + result.getRc() + '\n' + errorOut);
		try (BufferedReader r = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(result.getStdout().openInputStream())
				UTF_8))) {
			return r.lines().toArray(String[]::new);
		}
	}

	private void jgitIgnoredAndUntracked(LinkedHashSet<String> ignored
			LinkedHashSet<String> untracked) throws IOException {
		try (TreeWalk walk = new TreeWalk(db)) {
			FileTreeIterator iter = new FileTreeIterator(db);
			iter.setWalkIgnoredDirectories(true);
			walk.addTree(iter);
			walk.setRecursive(true);
			while (walk.next()) {
				if (walk.getTree(WorkingTreeIterator.class).isEntryIgnored()) {
					ignored.add(walk.getPathString());
				} else {
					untracked.add(walk.getPathString());
				}
			}
		}
	}

	private void assertNoIgnoredVisited(Set<String> ignored) throws Exception {
		try (TreeWalk walk = new TreeWalk(db)) {
			walk.addTree(new FileTreeIterator(db));
			walk.setFilter(new NotIgnoredFilter(0));
			walk.setRecursive(true);
			while (walk.next()) {
				String path = walk.getPathString();
				assertFalse("File " + path + " is ignored
						ignored.contains(path));
			}
		}
	}

	private void assertSameAsCGit(String... notIgnored) throws Exception {
		LinkedHashSet<String> ignored = new LinkedHashSet<>();
		LinkedHashSet<String> untracked = new LinkedHashSet<>();
		jgitIgnoredAndUntracked(ignored
		String[] cgit = cgitIgnored();
		String[] cgitUntracked = cgitUntracked();
		assertArrayEquals(cgit
		assertArrayEquals(cgitUntracked
		for (String notExcluded : notIgnored) {
			assertFalse("File " + notExcluded + " should not be ignored"
					ignored.contains(notExcluded));
		}
		assertNoIgnoredVisited(ignored);
	}

	@Test
	public void testSimpleIgnored() throws Exception {
		createFiles("a.txt"
				"src/a.txt/b.tmp"
				"ignored/other/a.tmp");
		writeTrashFile(".gitignore"
		assertSameAsCGit("ignored/not_ignored/a.tmp");
	}

	@Test
	public void testDirOnlyMatch() throws Exception {
		createFiles("a.txt"
		writeTrashFile(".gitignore"
		assertSameAsCGit();
	}

	@Test
	public void testDirOnlyMatchDeep() throws Exception {
		createFiles("a.txt"
		writeTrashFile(".gitignore"
		assertSameAsCGit();
	}

	@Test
	public void testStarMatchOnSlashNot() throws Exception {
		createFiles("sub/a.txt"
		writeTrashFile(".gitignore"
		assertSameAsCGit("sub/a.txt");
	}

	@Test
	public void testPrefixMatch() throws Exception {
		createFiles("src/new/foo.txt");
		writeTrashFile(".gitignore"
		assertSameAsCGit();
	}

	@Test
	public void testDirectoryMatchSubRecursive() throws Exception {
		createFiles("src/new/foo.txt"
		writeTrashFile(".gitignore"
		assertSameAsCGit();
	}

	@Test
	public void testDirectoryMatchSubRecursiveBacktrack() throws Exception {
		createFiles("src/new/foo.txt"
		writeTrashFile(".gitignore"
		assertSameAsCGit();
	}

	@Test
	public void testDirectoryMatchSubRecursiveBacktrack2() throws Exception {
		createFiles("src/new/foo.txt"
		writeTrashFile(".gitignore"
		assertSameAsCGit();
	}

	@Test
	public void testDirectoryMatchSubRecursiveBacktrack3() throws Exception {
		createFiles("x/a/a/b/foo.txt");
		writeTrashFile(".gitignore"
		assertSameAsCGit();
	}

	@Test
	public void testDirectoryMatchSubRecursiveBacktrack4() throws Exception {
		createFiles("x/a/a/b/foo.txt"
				"x/y/a/a/a/a/b/foo.txt"
		writeTrashFile(".gitignore"
		assertSameAsCGit();
	}

	@Test
	public void testDirectoryMatchSubRecursiveBacktrack5() throws Exception {
		createFiles("x/a/a/b/foo.txt"
		writeTrashFile(".gitignore"
		assertSameAsCGit();
	}

	@Test
	public void testDirectoryWildmatchDoesNotMatchFiles1() throws Exception {
		createFiles("a"
		writeTrashFile(".gitignore"
		assertSameAsCGit();
	}

	@Test
	public void testDirectoryWildmatchDoesNotMatchFiles2() throws Exception {
		createFiles("a"
		writeTrashFile(".gitignore"
		assertSameAsCGit();
	}

	@Test
	public void testDirectoryWildmatchDoesNotMatchFiles3() throws Exception {
		createFiles("a"
		writeTrashFile(".gitignore"
		assertSameAsCGit();
	}

	@Test
	public void testDirectoryWildmatchDoesNotMatchFiles4() throws Exception {
		createFiles("a"
		writeTrashFile(".gitignore"
		assertSameAsCGit();
	}

	@Test
	public void testUnescapedBracketsInGroup() throws Exception {
		createFiles("["
		writeTrashFile(".gitignore"
		assertSameAsCGit();
	}

	@Test
	public void testEscapedFirstBracketInGroup() throws Exception {
		createFiles("["
		writeTrashFile(".gitignore"
		assertSameAsCGit();
	}

	@Test
	public void testEscapedSecondBracketInGroup() throws Exception {
		createFiles("["
		writeTrashFile(".gitignore"
		assertSameAsCGit();
	}

	@Test
	public void testEscapedBothBracketsInGroup() throws Exception {
		createFiles("["
		writeTrashFile(".gitignore"
		assertSameAsCGit();
	}

	@Test
	public void testSimpleRootGitIgnoreGlobalNegation1() throws Exception {
		createFiles("x1"
		writeTrashFile(".gitignore"
		assertSameAsCGit();
	}

	@Test
	public void testRepeatedNegationInDifferentFiles5() throws Exception {
		createFiles("a/b/e/nothere.o");
		writeTrashFile(".gitignore"
		writeTrashFile("a/.gitignore"
		writeTrashFile("a/b/.gitignore"
		assertSameAsCGit();
	}

	@Test
	public void testRepeatedNegationInDifferentFilesWithWildmatcher1()
			throws Exception {
		createFiles("e"
		writeTrashFile(".gitignore"
		writeTrashFile("a/.gitignore"
		assertSameAsCGit();
	}

	@Test
	public void testRepeatedNegationInDifferentFilesWithWildmatcher2()
			throws Exception {
		createFiles("e"
				"a/b/dir/l"
				"a/q/dir/dir/s"
		writeTrashFile(".gitignore"
		writeTrashFile("a/.gitignore"
		writeTrashFile("a/b/.gitignore"
		writeTrashFile("c/.gitignore"
		assertSameAsCGit();
	}

	@Test
	public void testNegationForSubDirectoryWithinIgnoredDirectoryHasNoEffect1()
			throws Exception {
		createFiles("e"
		writeTrashFile(".gitignore"
		writeTrashFile("a/.gitignore"
		assertSameAsCGit();
	}

	@Test
	public void testNegationAllExceptJavaInSrcAndExceptChildDirInSrc()
			throws Exception {
		createFiles("nothere.o"
				"src/a/keep.java"
		writeTrashFile(".gitignore"
		writeTrashFile("src/.gitignore"
		assertSameAsCGit();
	}
}
