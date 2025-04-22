package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.junit.Before;
import org.junit.Test;

public class RmCommandTest extends RepositoryTestCase {

	private Git git;

	private static final String FILE = "test.txt";

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);
		writeTrashFile(FILE
		git.add().addFilepattern(FILE).call();
		git.commit().setMessage("Initial commit").call();
	}

	@Test
	public void testRemove() throws JGitInternalException
			IllegalStateException
		assertEquals("[test.txt
				indexState(CONTENT));
		RmCommand command = git.rm();
		command.addFilepattern(FILE);
		command.call();
		assertEquals(""
	}

	@Test
	public void testRemoveCached() throws Exception {
		File newFile = writeTrashFile("new.txt"
		git.add().addFilepattern(newFile.getName()).call();
		assertEquals("[new.txt
				indexState(0));

		git.rm().setCached(true).addFilepattern(newFile.getName()).call();

		assertEquals("[test.txt
		assertTrue("File should not have been removed."
	}
}
