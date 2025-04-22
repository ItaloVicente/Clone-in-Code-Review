package org.eclipse.jgit.ignore;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.WorkingTreeIterator;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.SystemReader;
import org.junit.Test;

public class IgnoreNodeTest extends RepositoryTestCase {
	private static final FileMode D = FileMode.TREE;

	private static final FileMode F = FileMode.REGULAR_FILE;

	private static final boolean ignored = true;

	private static final boolean tracked = false;

	private TreeWalk walk;

	@Test
	public void testSimpleRootGitIgnoreGlobalIgnore() throws IOException {
		writeIgnoreFile(".gitignore"

		writeTrashFile("a/x/file"
		writeTrashFile("b/x"
		writeTrashFile("x/file"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(D
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(D
		assertEntry(F
		endWalk();
	}

	@Test
	public void testSimpleRootGitIgnoreGlobalDirIgnore() throws IOException {
		writeIgnoreFile(".gitignore"

		writeTrashFile("a/x/file"
		writeTrashFile("x/file"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(D
		assertEntry(F
		assertEntry(D
		assertEntry(F
		endWalk();
	}

	@Test
	public void testSimpleRootGitIgnoreWildMatcher() throws IOException {
		writeIgnoreFile(".gitignore"

		writeTrashFile("a/x"
		writeTrashFile("y"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		endWalk();
	}

	@Test
	public void testSimpleRootGitIgnoreWildMatcherDirOnly() throws IOException {
		writeIgnoreFile(".gitignore"

		writeTrashFile("a/x"
		writeTrashFile("y"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		endWalk();
	}

	@Test
	public void testSimpleRootGitIgnoreGlobalNegation1() throws IOException {
		writeIgnoreFile(".gitignore"
		writeTrashFile("x1"
		writeTrashFile("a/x2"
		writeTrashFile("x3/y"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(D
		assertEntry(F
		endWalk();
	}

	@Test
	public void testSimpleRootGitIgnoreGlobalNegation2() throws IOException {
		writeIgnoreFile(".gitignore"
		writeTrashFile("x1"
		writeTrashFile("a/x2"
		writeTrashFile("x3/y"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(D
		assertEntry(F
		endWalk();
	}

	@Test
	public void testSimpleRootGitIgnoreGlobalNegation3() throws IOException {
		writeIgnoreFile(".gitignore"
		writeTrashFile("x1"
		writeTrashFile("a/x2"
		writeTrashFile("x3/y"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(D
		assertEntry(F
		endWalk();
	}

	@Test
	public void testSimpleRootGitIgnoreGlobalNegation4() throws IOException {
		writeIgnoreFile(".gitignore"
		writeTrashFile("x1"
		writeTrashFile("a/x2"
		writeTrashFile("x3/y"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(D
		assertEntry(F
		endWalk();
	}

	@Test
	public void testRules() throws IOException {
		writeIgnoreFile(".git/info/exclude"

		writeIgnoreFile(".gitignore"
		writeTrashFile("config/secret"
		writeTrashFile("mylib.c"
		writeTrashFile("mylib.c~"
		writeTrashFile("mylib.o"

		writeTrashFile("out/object/foo.exe"
		writeIgnoreFile("src/config/.gitignore"
		writeTrashFile("src/config/lex.out"
		writeTrashFile("src/config/config.c"
		writeTrashFile("src/config/config.c~"
		writeTrashFile("src/config/old/lex.out"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(F
		assertEntry(F

		assertEntry(D
		assertEntry(D
		assertEntry(F

		assertEntry(D
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(F
		assertEntry(F
		assertEntry(D
		assertEntry(F
		endWalk();
	}

	@Test
	public void testNegation() throws IOException {
		writeIgnoreFile(".gitignore"

		writeIgnoreFile("src/a/b/.gitignore"
		writeTrashFile("src/a/b/keep.o"
		writeTrashFile("src/a/b/nothere.o"

		writeIgnoreFile("src/c/.gitignore"
		writeIgnoreFile("src/c/d/.gitignore"
		writeTrashFile("src/c/d/keep.o"
		writeTrashFile("src/c/d/nothere.o"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(D
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(F

		assertEntry(D
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(F
		endWalk();
	}

	@Test
	public void testNegateAllExceptJavaInSrc() throws IOException {
		writeIgnoreFile(".gitignore"
		writeTrashFile("nothere.o"

		writeIgnoreFile("src/.gitignore"

		writeTrashFile("src/keep.java"
		writeTrashFile("src/nothere.o"
		writeTrashFile("src/a/nothere.o"

		beginWalk();
		assertEntry(F
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(F
		endWalk();
	}

	@Test
	public void testNegationAllExceptJavaInSrcAndExceptChildDirInSrc()
			throws IOException {
		writeIgnoreFile(".gitignore"
		writeTrashFile("nothere.o"

		writeIgnoreFile("src/.gitignore"

		writeTrashFile("src/keep.java"
		writeTrashFile("src/nothere.o"
		writeTrashFile("src/a/keep.java"
		writeTrashFile("src/a/keep.o"

		beginWalk();
		assertEntry(F
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(F
		assertEntry(F
		endWalk();
	}

	@Test
	public void testRepeatedNegation() throws IOException {
		writeIgnoreFile(".gitignore"

		writeTrashFile("e/nothere.o"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(F
		endWalk();
	}

	@Test
	public void testRepeatedNegationInDifferentFiles1() throws IOException {
		writeIgnoreFile(".gitignore"

		writeIgnoreFile("e/.gitignore"
		writeTrashFile("e/nothere.o"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		endWalk();
	}

	@Test
	public void testRepeatedNegationInDifferentFiles2() throws IOException {
		writeIgnoreFile(".gitignore"

		writeIgnoreFile("a/.gitignore"
		writeTrashFile("a/e/nothere.o"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(D
		assertEntry(F
		endWalk();
	}

	@Test
	public void testRepeatedNegationInDifferentFiles3() throws IOException {
		writeIgnoreFile(".gitignore"

		writeIgnoreFile("a/.gitignore"
		writeIgnoreFile("a/b/.gitignore"
		writeTrashFile("a/b/e/nothere.o"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(D
		assertEntry(F
		endWalk();
	}

	@Test
	public void testRepeatedNegationInDifferentFiles4() throws IOException {
		writeIgnoreFile(".gitignore"

		writeIgnoreFile("a/.gitignore"
		writeIgnoreFile("a/b/.gitignore"
		writeIgnoreFile("a/b/c/.gitignore"
		writeTrashFile("a/b/c/e/nothere.o"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(D
		assertEntry(F
		endWalk();
	}

	@Test
	public void testRepeatedNegationInDifferentFiles5() throws IOException {
		writeIgnoreFile(".gitignore"
		writeIgnoreFile("a/.gitignore"
		writeIgnoreFile("a/b/.gitignore"
		writeTrashFile("a/b/e/nothere.o"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(D
		assertEntry(F
		endWalk();
	}

	@Test
	public void testIneffectiveNegationDifferentLevels1() throws IOException {
		writeIgnoreFile(".gitignore"
		writeTrashFile("a/b/e/nothere.o"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(D
		assertEntry(D
		assertEntry(F
		endWalk();
	}

	@Test
	public void testIneffectiveNegationDifferentLevels2() throws IOException {
		writeIgnoreFile(".gitignore"
		writeIgnoreFile("a/.gitignore"
		writeTrashFile("a/b/e/nothere.o"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(D
		assertEntry(D
		assertEntry(F
		endWalk();
	}

	@Test
	public void testIneffectiveNegationDifferentLevels3() throws IOException {
		writeIgnoreFile(".gitignore"
		writeIgnoreFile("a/b/.gitignore"
		writeTrashFile("a/b/e/nothere.o"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(D
		assertEntry(F
		assertEntry(D
		assertEntry(F
		endWalk();
	}

	@Test
	public void testIneffectiveNegationDifferentLevels4() throws IOException {
		writeIgnoreFile(".gitignore"
		writeIgnoreFile("a/b/e/.gitignore"
		writeTrashFile("a/b/e/nothere.o"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(D
		assertEntry(D
		assertEntry(F
		assertEntry(F
		endWalk();
	}

	@Test
	public void testIneffectiveNegationDifferentLevels5() throws IOException {
		writeIgnoreFile("a/.gitignore"
		writeIgnoreFile("a/b/.gitignore"
		writeTrashFile("a/b/e/nothere.o"

		beginWalk();
		assertEntry(D
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(D
		assertEntry(F
		endWalk();
	}

	@Test
	public void testEmptyIgnoreRules() throws IOException {
		IgnoreNode node = new IgnoreNode();
		node.parse(writeToString(""
		assertEquals(new ArrayList<>()
		node.parse(writeToString(" "
		assertEquals(2
	}

	@Test
	public void testSlashOnlyMatchesDirectory() throws IOException {
		writeIgnoreFile(".gitignore"
		writeTrashFile("out"

		beginWalk();
		assertEntry(F
		assertEntry(F

		FileUtils.delete(new File(trash
		writeTrashFile("out/foo"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(F
		endWalk();
	}

	@Test
	public void testSlashMatchesDirectory() throws IOException {
		writeIgnoreFile(".gitignore"

		writeTrashFile("out1/out1"
		writeTrashFile("out1/out2"
		writeTrashFile("out2/out1"
		writeTrashFile("out2/out2"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		endWalk();
	}

	@Test
	public void testWildcardWithSlashMatchesDirectory() throws IOException {
		writeIgnoreFile(".gitignore"

		writeTrashFile("out1/out1.txt"
		writeTrashFile("out1/out2"
		writeTrashFile("out1/out2.txt"
		writeTrashFile("out1/out2x/a"
		writeTrashFile("out2/out1.txt"
		writeTrashFile("out2/out2.txt"
		writeTrashFile("out2x/out1.txt"
		writeTrashFile("out2x/out2.txt"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		endWalk();
	}

	@Test
	public void testWithSlashDoesNotMatchInSubDirectory() throws IOException {
		writeIgnoreFile(".gitignore"
		writeTrashFile("a/a"
		writeTrashFile("a/b"
		writeTrashFile("src/a/a"
		writeTrashFile("src/a/b"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(D
		assertEntry(D
		assertEntry(F
		assertEntry(F
		endWalk();
	}

	@Test
	public void testNoPatterns() throws IOException {
		writeIgnoreFile(".gitignore"
		writeTrashFile("a/a"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(F
		endWalk();
	}

	@Test
	public void testLeadingSpaces() throws IOException {
		writeTrashFile("  a/  a"
		writeTrashFile("  a/ a"
		writeTrashFile("  a/a"
		writeTrashFile(" a/  a"
		writeTrashFile(" a/ a"
		writeTrashFile(" a/a"
		writeIgnoreFile(".gitignore"
		writeTrashFile("a/  a"
		writeTrashFile("a/ a"
		writeTrashFile("a/a"

		beginWalk();
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(F
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(F
		endWalk();
	}

	@Test
	public void testTrailingSpaces() throws IOException {
		org.junit.Assume.assumeFalse(SystemReader.getInstance().isWindows());
		writeTrashFile("a  /a"
		writeTrashFile("a  /a "
		writeTrashFile("a  /a  "
		writeTrashFile("a /a"
		writeTrashFile("a /a "
		writeTrashFile("a /a  "
		writeTrashFile("a/a"
		writeTrashFile("a/a "
		writeTrashFile("a/a  "
		writeTrashFile("b/c"

		writeIgnoreFile(".gitignore"

		beginWalk();
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(F
		assertEntry(D
		assertEntry(F
		assertEntry(F
		assertEntry(F
		assertEntry(D
		assertEntry(F
		endWalk();
	}

	@Test
	public void testToString() throws Exception {
		assertEquals(Arrays.asList("").toString()
		assertEquals(Arrays.asList("hello").toString()
				new IgnoreNode(Arrays.asList(new FastIgnoreRule("hello")))
						.toString());
	}

	private void beginWalk() {
		walk = new TreeWalk(db);
		FileTreeIterator iter = new FileTreeIterator(db);
		iter.setWalkIgnoredDirectories(true);
		walk.addTree(iter);
	}

	private void endWalk() throws IOException {
		assertFalse("Not all files tested"
	}

	private void assertEntry(FileMode type
			String pathName) throws IOException {
		assertTrue("walk has entry"
		assertEquals(pathName
		assertEquals(type

		WorkingTreeIterator itr = walk.getTree(0
		assertNotNull("has tree"
		assertEquals("is ignored"
		if (D.equals(type))
			walk.enterSubtree();
	}

	private void writeIgnoreFile(String name
			throws IOException {
		StringBuilder data = new StringBuilder();
		for (String line : rules)
			data.append(line + "\n");
		writeTrashFile(name
	}

	private InputStream writeToString(String... rules) {
		StringBuilder data = new StringBuilder();
		for (String line : rules) {
			data.append(line + "\n");
		}
		return new ByteArrayInputStream(data.toString().getBytes(UTF_8));
	}
}
