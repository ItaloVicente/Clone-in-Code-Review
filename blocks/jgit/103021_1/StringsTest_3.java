package org.eclipse.jgit.ignore;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.WorkingTreeIterator;
import org.eclipse.jgit.treewalk.filter.NotIgnoredFilter;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FS.ExecutionResult;
import org.eclipse.jgit.util.IO;
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

	private String toString(TemporaryBuffer b) throws Exception {
		long length = b.length();
		int toRead = 10 * 1024;
		if (length < toRead) {
			toRead = (int) length;
		}
		try (InputStream stream = new BufferedInputStream(
				b.openInputStream())) {
			byte[] buffer = new byte[toRead];
			int read = IO.readFully(stream
			assertEquals("Read error"
			return RawParseUtils.decode(buffer);
		}
	}

	private String[] cgitIgnored() throws Exception {
		FS fs = db.getFS();
		ProcessBuilder builder = fs.runInShell("git"
		builder.directory(db.getWorkTree());
		ExecutionResult result = fs.execute(builder
		assertEquals("External git failed"
		assertEquals("External git reported errors"
		try (BufferedReader r = new BufferedReader(new InputStreamReader(new BufferedInputStream(result.getStdout().openInputStream())
			return r.lines().toArray(String[]::new);
		}
	}

	private LinkedHashSet<String> jgitIgnored() throws IOException {
		LinkedHashSet<String> result = new LinkedHashSet<>();
		try (TreeWalk walk = new TreeWalk(db)) {
			walk.addTree(new FileTreeIterator(db));
			walk.setRecursive(true);
			while (walk.next()) {
				if (walk.getTree(WorkingTreeIterator.class).isEntryIgnored()) {
					result.add(walk.getPathString());
				}
			}
		}
		return result;
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
		LinkedHashSet<String> ignored = jgitIgnored();
		String[] cgit = cgitIgnored();
		assertArrayEquals(cgit
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

}
