package org.eclipse.jgit.pgm;

import static org.junit.Assert.assertTrue;

import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.junit.Test;

public class CommitTest extends CLIRepositoryTestCase {

	@Test
	public void testCommitPath() throws Exception {
		writeTrashFile("a"
		writeTrashFile("b"
		String result = toString(execute("git add a"));
		assertTrue("Unexpected output: " + result

		result = toString(execute("git status -- a"));
		assertTrue("Unexpected output: " + result
				result.contains("new file:   a"));

		result = toString(execute("git status -- b"));
		assertTrue("Unexpected output: " + result
				result.trim().contains("Untracked files:\n	b"));

		result = toString(execute("git commit a -m 'added a'"));
		assertTrue("Unexpected output: " + result

		result = toString(execute("git status -- a"));
		assertTrue("Unexpected output: " + result
				result.trim().equals("On branch master"));

		result = toString(execute("git status -- b"));
		assertTrue("Unexpected output: " + result
				result.trim().contains("Untracked files:\n	b"));
	}

	@Test
	public void testCommitAll() throws Exception {
		writeTrashFile("a"
		writeTrashFile("b"
		String result = toString(execute("git add a b"));
		assertTrue("Unexpected output: " + result

		result = toString(execute("git status -- a b"));
		assertTrue("Unexpected output: " + result
				result.contains("new file:   a"));
		assertTrue("Unexpected output: " + result
				result.contains("new file:   b"));

		result = toString(execute("git commit -m 'added a b'"));
		assertTrue("Unexpected output: " + result
				result.contains("added a b"));

		result = toString(execute("git status -- a b"));
		assertTrue("Unexpected output: " + result
				result.trim().equals("On branch master"));
	}

	String toString(String[] arr) {
		StringBuilder sb = new StringBuilder();
		for (String s : arr) {
			if (s != null && !s.isEmpty()) {
				sb.append(s);
				if (!s.endsWith("\n")) {
					sb.append('\n');
				}
			}
		}
		return sb.toString();
	}
}
