package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.Sets;
import org.junit.Test;

public class StatusCommandTest extends RepositoryTestCase {

	@Test
	public void testEmptyStatus() throws NoWorkTreeException
			GitAPIException {
		try (Git git = new Git(db)) {
			Status stat = git.status().call();
			assertEquals(0
			assertEquals(0
			assertEquals(0
			assertEquals(0
			assertEquals(0
			assertEquals(0
		}
	}

	@Test
	public void testDifferentStates() throws IOException
			NoFilepatternException
		try (Git git = new Git(db)) {
			writeTrashFile("a"
			writeTrashFile("b"
			writeTrashFile("c"
			git.add().addFilepattern("a").addFilepattern("b").call();
			Status stat = git.status().call();
			assertEquals(Sets.of("a"
			assertEquals(0
			assertEquals(0
			assertEquals(0
			assertEquals(0
			assertEquals(Sets.of("c")
			git.commit().setMessage("initial").call();

			writeTrashFile("a"
			writeTrashFile("b"
			writeTrashFile("d"
			git.add().addFilepattern("a").addFilepattern("d").call();
			writeTrashFile("a"
			stat = git.status().call();
			assertEquals(Sets.of("d")
			assertEquals(Sets.of("a")
			assertEquals(0
			assertEquals(Sets.of("b"
			assertEquals(0
			assertEquals(Sets.of("c")
			git.add().addFilepattern(".").call();
			git.commit().setMessage("second").call();

			stat = git.status().call();
			assertEquals(0
			assertEquals(0
			assertEquals(0
			assertEquals(0
			assertEquals(0
			assertEquals(0

			deleteTrashFile("a");
			assertFalse(new File(git.getRepository().getWorkTree()
			git.add().addFilepattern("a").setUpdate(true).call();
			writeTrashFile("a"
			stat = git.status().call();
			assertEquals(0
			assertEquals(0
			assertEquals(0
			assertEquals(0
			assertEquals(Sets.of("a")
			assertEquals(Sets.of("a")
			git.commit().setMessage("t").call();

			writeTrashFile("sub/a"
			stat = git.status().call();
			assertEquals(1
			assertTrue(stat.getUntrackedFolders().contains("sub"));
		}
	}

	@Test
	public void testDifferentStatesWithPaths() throws IOException
			NoFilepatternException
		try (Git git = new Git(db)) {
			writeTrashFile("a"
			writeTrashFile("D/b"
			writeTrashFile("D/c"
			writeTrashFile("D/D/d"
			git.add().addFilepattern(".").call();

			writeTrashFile("a"
			writeTrashFile("D/b"
			writeTrashFile("D/D/d"


			Status stat = git.status().addPath("x").call();
			assertEquals(0

			stat = git.status().addPath("a").call();
			assertEquals(Sets.of("a")

			stat = git.status().addPath("D").call();
			assertEquals(Sets.of("D/b"

			stat = git.status().addPath("D/D").addPath("a").call();
			assertEquals(Sets.of("a"

			stat = git.status().call();
			assertEquals(Sets.of("a"
		}
	}
}
