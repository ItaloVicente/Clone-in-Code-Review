
package org.eclipse.jgit.pgm;

import static org.junit.Assert.assertEquals;

import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.junit.Before;
import org.junit.Test;

public class ShowTest extends CLIRepositoryTestCase {

	private static final String NO_NEWLINE = "\\ No newline at end of file";

	@Before
	public void setup() throws Exception {
		writeTrashFile("a"
		writeTrashFile("b"
		execute("git add a b");
		execute("git commit -m added");
		writeTrashFile("a"
		execute("git add a");
		execute("git commit -m modified");
	}

	@Test
	public void testShow() throws Exception {
		String result = toString(execute("git show"));
		assertEquals(
				toString("commit ecdf62e777b7413fc463c20e935403d424410ab2"
						"Author: GIT_COMMITTER_NAME <GIT_COMMITTER_EMAIL>"
						"Date:   Sat Aug 15 20:12:58 2009 -0330"
						"    modified"
						"index 2e65efe..59ef8d1 100644"
						"@@ -1 +1 @@"
				result);
	}

	@Test
	public void testShowNameOnly() throws Exception {
		String result = toString(execute("git show --name-only"));
		assertEquals(toString("commit ecdf62e777b7413fc463c20e935403d424410ab2"
				"Author: GIT_COMMITTER_NAME <GIT_COMMITTER_EMAIL>"
				"Date:   Sat Aug 15 20:12:58 2009 -0330"
				"a")
	}

	@Test
	public void testShowNameStatus() throws Exception {
		String result = toString(execute("git show --name-status"));
		assertEquals(toString("commit ecdf62e777b7413fc463c20e935403d424410ab2"
				"Author: GIT_COMMITTER_NAME <GIT_COMMITTER_EMAIL>"
				"Date:   Sat Aug 15 20:12:58 2009 -0330"
				"M\ta")
	}
}
