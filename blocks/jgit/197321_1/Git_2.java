package org.eclipse.jgit.api;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lfs.BuiltinLFS;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.util.FileUtils;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.*;

@RunWith(Theories.class)
public class RestoreCommandTest extends RepositoryTestCase {
	@DataPoints
	public static boolean[] sleepBeforeAddOptions = { true


	@Override
	public void setUp() throws Exception {
		BuiltinLFS.register();
		super.setUp();
	}

	@Test
	public void testRestoreNothingFromWorkingDir() throws GitAPIException {
		try (Git git = new Git(db)) {
			git.restore().call();
			fail("Expected IllegalArgumentException");
		} catch (NoFilepatternException e) {
		}

	}

	@Test
	public void testRestoreNothingFromIndex() throws GitAPIException {
		try (Git git = new Git(db)) {
			git.restore().setCached(true).call();
			fail("Expected IllegalArgumentException");
		} catch (NoFilepatternException e) {
		}

	}

	@Test
	public void testRestoreNonExistingSingleFileFromWorkingDir() throws GitAPIException {
		try (Git git = new Git(db)) {
			DirCache dc = git.restore().addFilepattern("a.txt").call();
			assertEquals(0
		}
	}

	@Test
	public void testRestoreNonExistingSingleFileFromIndex() throws GitAPIException {
		try (Git git = new Git(db)) {
			DirCache dc = git.restore().setCached(true).addFilepattern("a.txt").call();
			assertEquals(0
		}
	}

	@Test
	public void testRestoreExistingSingleFileFromWorkingDir() throws IOException
		try (Git git = new Git(db)) {
			Status stat = git.status().call();
			assertEquals(0
			assertEquals(0
			assertEquals(0
			assertEquals(0
			assertEquals(0
			assertEquals(0

			File file = new File(db.getWorkTree()
			FileUtils.createNewFile(file);
			try (PrintWriter writer = new PrintWriter(file
				writer.print("content");
			}

			stat = git.status().call();
			assertEquals(1

			git.add().addFilepattern("a.txt").call();

			assertEquals(
					"[a.txt
					indexState(CONTENT));

			stat = git.status().call();
			assertEquals(1

			RevCommit commit = git.commit().setMessage("commit").call();

			stat = git.status().call();
			assertEquals(0
			assertEquals(0
			assertEquals(0
			assertEquals(0
			assertEquals(0
			assertEquals(0

			try (PrintWriter writer = new PrintWriter(file
				writer.print("testcontent");
			}

			stat = git.status().call();
			assertEquals(1

			git.restore().addFilepattern("a.txt").call();

			stat = git.status().call();
			assertEquals(0

		}
	}

	@Test
	public void testRestoreExistingSingleFileFromIndex() throws IOException
		try (Git git = new Git(db)) {

			Status stat = git.status().call();

			assertEquals(0
			assertEquals(0
			assertEquals(0
			assertEquals(0
			assertEquals(0
			assertEquals(0

			File file = new File(db.getWorkTree()
			FileUtils.createNewFile(file);
			try (PrintWriter writer = new PrintWriter(file
				writer.print("content");
			}

			stat = git.status().call();
			assertEquals(1

			git.add().addFilepattern("a.txt").call();

			assertEquals(
					"[a.txt
					indexState(CONTENT));

			stat = git.status().call();
			assertEquals(1

			git.restore().setCached(true).addFilepattern("a.txt").call();

			assertEquals(
					""
					indexState(CONTENT));

			stat = git.status().call();
			assertEquals(1

		}
	}
}
