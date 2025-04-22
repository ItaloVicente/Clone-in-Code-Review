package org.eclipse.jgit.pgm;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.junit.Before;
import org.junit.Test;

public class AddTest extends CLIRepositoryTestCase {
	private Git git;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);
	}

	@Test
	public void testAddNothing() throws Exception {
		try {
			execute("git add");
			fail("Must die");
		} catch (Die e) {
		}
	}

	@Test
	public void testAddUsage() throws Exception {
		execute("git add --help");
	}

	@Test
	public void testAddAFile() throws Exception {
		writeTrashFile("greeting"
		assertArrayEquals(new String[] { "" }
				execute("git add greeting"));

		DirCache cache = db.readDirCache();
		assertNotNull(cache.getEntry("greeting"));
		assertEquals(1
	}

	@Test
	public void testAddFileTwice() throws Exception {
		writeTrashFile("greeting"
		assertArrayEquals(new String[] { "" }
				execute("git add greeting greeting"));

		DirCache cache = db.readDirCache();
		assertNotNull(cache.getEntry("greeting"));
		assertEquals(1
	}

	@Test
	public void testAddAlreadyAdded() throws Exception {
		writeTrashFile("greeting"
		git.add().addFilepattern("greeting").call();
		assertArrayEquals(new String[] { "" }
				execute("git add greeting"));

		DirCache cache = db.readDirCache();
		assertNotNull(cache.getEntry("greeting"));
		assertEquals(1
	}
}
