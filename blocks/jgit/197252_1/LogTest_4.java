
package org.eclipse.jgit.pgm;

import static org.junit.Assert.assertEquals;

import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.junit.Before;
import org.junit.Test;

public class DiffTest extends CLIRepositoryTestCase {

	private static final String NO_NEWLINE = "\\ No newline at end of file";

	@Before
	public void setup() throws Exception {
		writeTrashFile("a"
		execute("git add a");
		execute("git commit -m added");
	}

	@Test
	public void testDiffCommitNewFile() throws Exception {
		writeTrashFile("a1"
		String result = toString(execute("git diff"));
		assertEquals(
				toString("diff --git a/a1 b/a1"
						"index 0000000..2e65efe"
						"@@ -0
				result);
	}

	@Test
	public void testDiffCommitModifiedFile() throws Exception {
		writeTrashFile("a"
		String result = toString(execute("git diff"));
		assertEquals(
				toString("diff --git a/a b/a"
						"--- a/a"
						"-a"
				result);
	}

	@Test
	public void testDiffCommitModifiedFileNameOnly() throws Exception {
		writeTrashFile("a"
		writeTrashFile("b"
		String result = toString(execute("git diff --name-only"));
		assertEquals(toString("a"
	}

	@Test
	public void testDiffCommitModifiedFileNameStatus() throws Exception {
		writeTrashFile("a"
		writeTrashFile("b"
		String result = toString(execute("git diff --name-status"));
		assertEquals(toString("M\ta"
	}
}
