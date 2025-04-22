package org.eclipse.jgit.attributes;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.NotIgnoredFilter;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FS.ExecutionResult;
import org.eclipse.jgit.util.RawParseUtils;
import org.eclipse.jgit.util.TemporaryBuffer;
import org.junit.Before;
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

	private String toString(TemporaryBuffer b) throws IOException {
		return RawParseUtils.decode(b.toByteArray());
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
		builder.environment().put("HOME"
		ExecutionResult result = fs.execute(builder
				input.toString().getBytes(UTF_8)));
		String errorOut = toString(result.getStderr());
		assertEquals("External git failed"
				"exit " + result.getRc() + '\n' + errorOut);
		LinkedHashMap<String
		try (BufferedReader r = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(result.getStdout().openInputStream())
				UTF_8))) {
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
	public void testDirectoryMatchSubRecursiveBacktrack() throws Exception {
		createFiles("src/new/foo.txt"
		writeTrashFile(".gitattributes"
		assertSameAsCGit();
	}

	@Test
	public void testDirectoryMatchSubRecursiveBacktrack2() throws Exception {
		createFiles("src/new/foo.txt"
		writeTrashFile(".gitattributes"
		assertSameAsCGit();
	}

	@Test
	public void testDirectoryMatchSubRecursiveBacktrack3() throws Exception {
		createFiles("src/new/src/new/foo.txt"
				"foo/src/new/bar/src/new/foo.txt");
		writeTrashFile(".gitattributes"
		assertSameAsCGit();
	}

	@Test
	public void testDirectoryMatchSubRecursiveBacktrack4() throws Exception {
		createFiles("src/src/src/new/foo.txt"
				"foo/src/src/bar/src/new/foo.txt");
		writeTrashFile(".gitattributes"
		assertSameAsCGit();
	}

	@Test
	public void testDirectoryMatchSubRecursiveBacktrack5() throws Exception {
		createFiles("x/a/a/b/foo.txt"
				"x/y/a/a/a/a/b/foo.txt"
		writeTrashFile(".gitattributes"
		assertSameAsCGit();
	}

	@Test
	public void testDirectoryMatchSubRecursiveBacktrack6() throws Exception {
		createFiles("x/a/a/b/foo.txt"
		writeTrashFile(".gitattributes"
		assertSameAsCGit();
	}

	@Test
	public void testDirectoryWildmatchDoesNotMatchFiles1() throws Exception {
		createFiles("a"
		writeTrashFile(".gitattributes"
		assertSameAsCGit();
	}

	@Test
	public void testDirectoryWildmatchDoesNotMatchFiles2() throws Exception {
		createFiles("a"
		writeTrashFile(".gitattributes"
		assertSameAsCGit();
	}

	@Test
	public void testDirectoryWildmatchDoesNotMatchFiles3() throws Exception {
		createFiles("a"
		writeTrashFile(".gitattributes"
		assertSameAsCGit();
	}

	@Test
	public void testDirectoryWildmatchDoesNotMatchFiles4() throws Exception {
		createFiles("a"
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

	@Test
	public void testBracketsInGroup() throws Exception {
		createFiles("["
		writeTrashFile(".gitattributes"
				+ "[[\\]] bar3\n" + "[\\[\\]] bar4\n");
		assertSameAsCGit();
	}
}
