package org.eclipse.jgit.pgm;

import static org.junit.Assert.assertEquals;

import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.junit.Test;

public class CommitTest extends CLIRepositoryTestCase {

	@Test
	public void testCommitPath() throws Exception {
		writeTrashFile("a"
		writeTrashFile("b"
		String result = toString(execute("git add a"));
		assertEquals(""

		result = toString(execute("git status -- a"));
		assertEquals(toString("On branch master"
				"new file:   a")

		result = toString(execute("git status -- b"));
		assertEquals(toString("On branch master"
				result);

		result = toString(execute("git commit a -m 'added a'"));
		assertEquals(
				"[master 8cb3ef7e5171aaee1792df6302a5a0cd30425f7a] added a"
				result);

		result = toString(execute("git status -- a"));
		assertEquals("On branch master"

		result = toString(execute("git status -- b"));
		assertEquals(toString("On branch master"
				result);
	}

	@Test
	public void testCommitAll() throws Exception {
		writeTrashFile("a"
		writeTrashFile("b"
		String result = toString(execute("git add a b"));
		assertEquals(""

		result = toString(execute("git status -- a b"));
		assertEquals(toString("On branch master"
				"new file:   a"

		result = toString(execute("git commit -m 'added a b'"));
		assertEquals(
				"[master 3c93fa8e3a28ee26690498be78016edcb3a38c73] added a b"
				result);

		result = toString(execute("git status -- a b"));
		assertEquals("On branch master"
	}

}
