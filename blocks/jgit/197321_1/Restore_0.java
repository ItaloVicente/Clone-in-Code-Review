package org.eclipse.jgit.pgm;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RestoreTest extends CLIRepositoryTestCase {
	private Git git;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);
	}

	@Test
	public void testRestoreNothingFromWorkingDir() throws Exception {
		try {
			execute("git restore");
			fail("Must die");
		} catch (Die e) {
		}
	}

	@Test
	public void testRestoreUsage() throws Exception {
		execute("git restore --help");
	}

	@Test
	public void testRestoreAFileFromIndex() throws Exception {
		writeTrashFile("greeting"

		assertArrayEquals(new String[] { "On branch master"
						"Untracked files:"
						""
				 		"\tgreeting"
						""
				 }

		assertArrayEquals(new String[] { "" }
				execute("git add greeting"));

		assertArrayEquals(new String[] { "On branch master"
				"Changes to be committed:"
				""
				"\tnew file:   greeting"
				""
		}

		assertArrayEquals(new String[] { "" }
				execute("git restore --staged greeting"));

		assertArrayEquals(new String[] { "On branch master"
				"Untracked files:"
				""
				"\tgreeting"
				""
		}

	}

	@Test
	public void testRestoreAFileFromWorkingDir() throws Exception {
		writeTrashFile("greeting"

		assertArrayEquals(new String[] { "On branch master"
				"Untracked files:"
				""
				"\tgreeting"
				""
		}

		assertArrayEquals(new String[] { "" }
				execute("git add greeting"));

		assertArrayEquals(new String[] { "On branch master"
				"Changes to be committed:"
				""
				"\tnew file:   greeting"
				""
		}

		execute("git commit -m \"test\"");

		assertArrayEquals(new String[] { "On branch master"
				""
		}

		writeTrashFile("greeting"

		assertArrayEquals(new String[] { "On branch master"
				"Changes not staged for commit:"
				""
				"\tmodified:   greeting"
				""
		}

		assertArrayEquals(new String[] { "" }
				execute("git restore greeting"));

		assertArrayEquals(new String[] { "On branch master"
				""
		}

	}

}
