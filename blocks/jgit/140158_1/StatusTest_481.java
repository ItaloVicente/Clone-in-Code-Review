package org.eclipse.jgit.pgm;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.io.File;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.junit.Before;
import org.junit.Test;

public class RmTest extends CLIRepositoryTestCase {
	private Git git;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);
	}

	@Test
	public void multiplePathsShouldBeRemoved() throws Exception {
		File a = writeTrashFile("a"
		File b = writeTrashFile("b"
		git.add().addFilepattern("a").addFilepattern("b").call();

		String[] result = execute("git rm a b");
		assertArrayEquals(new String[] { "" }
		DirCache cache = db.readDirCache();
		assertNull(cache.getEntry("a"));
		assertNull(cache.getEntry("b"));
		assertFalse(a.exists());
		assertFalse(b.exists());
	}
}
