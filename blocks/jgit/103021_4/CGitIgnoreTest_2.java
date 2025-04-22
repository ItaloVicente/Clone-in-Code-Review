package org.eclipse.jgit.attributes;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.NotIgnoredFilter;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FS.ExecutionResult;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.RawParseUtils;
import org.eclipse.jgit.util.TemporaryBuffer;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class CGitAttributesTest extends RepositoryTestCase {

	@Before
	public void initRepo() throws IOException {
		StoredConfig config = db.getConfig();
		File fakeUserGitignore = writeTrashFile(".fake_user_gitignore"
		config.setString("core"
				fakeUserGitignore.getAbsolutePath());
		config.setBoolean("core"
		config.setString("core"
				fakeUserGitignore.getAbsolutePath());
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

	private Attribute fromString(String key
		if ("set".equals(value)) {
			return new Attribute(key
		}
		if ("unset".equals(value)) {
			return new Attribute(key
		}
		if ("unspecified".equals(value)) {
			return new Attribute(key
		}
		return new Attribute(key
	}

	private LinkedHashMap<String
			Set<String> allFiles) throws Exception {
		FS fs = db.getFS();
		StringBuilder input = new StringBuilder();
		for (String filename : allFiles) {
			input.append(filename).append('\n');
		}
		ProcessBuilder builder = fs.runInShell("git"
				new String[] { "check-attr"
		builder.directory(db.getWorkTree());
		ExecutionResult result = fs.execute(builder
				input.toString().getBytes(Constants.CHARSET)));
		assertEquals("External git reported errors"
				toString(result.getStderr()));
		assertEquals("External git failed"
		LinkedHashMap<String
		try (BufferedReader r = new BufferedReader(new InputStreamReader(new BufferedInputStream(result.getStdout().openInputStream())
			r.lines().forEach(line -> {
				int start = 0;
				int i = line.indexOf(':');
				String path = line.substring(0
				start = i + 1;
				i = line.indexOf(':'
				String key = line.substring(start
				String value = line.substring(i + 1).trim();
				Attribute attr = fromString(key
				Attributes attrs = map.get(path);
				if (attrs == null) {
					attrs = new Attributes(attr);
					map.put(path
				} else {
					attrs.put(attr);
				}
			});
		}
		return map;
	}

	private LinkedHashMap<String
			throws IOException {
		LinkedHashMap<String
		try (TreeWalk walk = new TreeWalk(db)) {
			walk.addTree(new FileTreeIterator(db));
			walk.setFilter(new NotIgnoredFilter(0));
			while (walk.next()) {
				String path = walk.getPathString();
				if (walk.isSubtree() && !path.endsWith("/")) {
					path += '/';
				}
				Attributes attrs = walk.getAttributes();
				if (attrs != null && !attrs.isEmpty()) {
					result.put(path
				} else {
					result.put(path
				}
				if (walk.isSubtree()) {
					walk.enterSubtree();
				}
			}
		}
		return result;
	}

	private void assertSameAsCGit() throws Exception {
		LinkedHashMap<String
		LinkedHashMap<String
		Iterator<Map.Entry<String
				.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String
			if (entry.getValue() == null) {
				iterator.remove();
			}
		}
		assertArrayEquals("JGit attributes differ from C git"
				cgit.entrySet().toArray()
	}

	@Test
	public void testBug508568() throws Exception {
		createFiles("foo.xml/bar.jar"
		writeTrashFile(".gitattributes"
		assertSameAsCGit();
	}

	@Test
	public void testRelativePath() throws Exception {
		createFiles("sub/foo.txt");
		writeTrashFile("sub/.gitattributes"
		assertSameAsCGit();
	}

	@Test
	public void testRelativePaths() throws Exception {
		createFiles("sub/foo.txt"
				"foo/sub/bar/a.tmp");
		writeTrashFile(".gitattributes"
		assertSameAsCGit();
	}

	@Test
	public void testNestedMatchNot() throws Exception {
		createFiles("foo.xml/bar.jar"
				"sub/b.xml");
		writeTrashFile("sub/.gitattributes"
		assertSameAsCGit();
	}

	@Test
	public void testNestedMatch() throws Exception {
		createFiles("foo/bar.jar"
				"sub/foo/b.jar");
		writeTrashFile(".gitattributes"
				"foo/ xml\n" + "sub/foo/ sub\n" + "*.jar jar\n");
		assertSameAsCGit();
	}

	@Test
	public void testNestedMatchWithWildcard() throws Exception {
		createFiles("foo/bar.jar"
				"sub/foo/b.jar");
		writeTrashFile(".gitattributes"
		assertSameAsCGit();
	}

	@Test
	public void testNestedMatchRecursive() throws Exception {
		createFiles("foo/bar.jar"
				"sub/foo/b.jar");
		writeTrashFile(".gitattributes"
		assertSameAsCGit();
	}

	@Test
	public void testStarMatchOnSlashNot() throws Exception {
		createFiles("sub/a.txt"
		writeTrashFile(".gitattributes"
		assertSameAsCGit();
	}

	@Test
	public void testPrefixMatchNot() throws Exception {
		createFiles("src/new/foo.txt");
		writeTrashFile(".gitattributes"
		assertSameAsCGit();
	}

	@Test
	public void testComplexPathMatchNot() throws Exception {
		createFiles("src/new/foo.txt"
		writeTrashFile(".gitattributes"
		assertSameAsCGit();
	}

	@Test
	public void testStarPathMatchNot() throws Exception {
		createFiles("src/new/foo.txt"
		writeTrashFile(".gitattributes"
		assertSameAsCGit();
	}

	@Test
	public void testDirectoryMatchSubSimple() throws Exception {
		createFiles("src/new/foo.txt"
		writeTrashFile(".gitattributes"
		assertSameAsCGit();
	}

	@Test
	public void testDirectoryMatchSubRecursive() throws Exception {
		createFiles("src/new/foo.txt"
		writeTrashFile(".gitattributes"
		assertSameAsCGit();
	}

	@Test
	@Ignore("Re-enable once bug 520920 is fixed")
	public void testDirectoryMatchSubRecursiveBacktrack() throws Exception {
		createFiles("src/new/foo.txt"
		writeTrashFile(".gitattributes"
		assertSameAsCGit();
	}

	@Test
	public void testDirectoryMatchSubComplex() throws Exception {
		createFiles("src/new/foo.txt"
		writeTrashFile(".gitattributes"
		assertSameAsCGit();
	}

	@Test
	public void testDirectoryMatch() throws Exception {
		createFiles("src/new/foo.txt"
		writeTrashFile(".gitattributes"
		assertSameAsCGit();
	}
}
