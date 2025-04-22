package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;

import org.eclipse.jgit.lib.RepositoryTestCase;
import org.junit.Before;
import org.junit.Test;

public class MvCommandTest extends RepositoryTestCase {

	private Git git;

	private static final String SRC = "old.txt";

	private static final String DST = "new.txt";

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);
		writeTrashFile(SRC
		git.add().addFilepattern(SRC).call();
		git.commit().setMessage("Initial commit").call();
	}

	@Test
	public void testMove() throws Exception {
		assertEquals("[old.txt
				indexState(CONTENT));
		MvCommand command = git.mv();
		command.setSource(SRC);
		command.setDestination(DST);
		command.call();
		assertEquals("[new.txt
				indexState(CONTENT));
	}
}
