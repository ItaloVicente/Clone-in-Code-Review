package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;

import org.eclipse.jgit.api.errors.MoveException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.junit.Before;
import org.junit.Test;

public class MvCommandTest extends RepositoryTestCase {

	private Git git;

	private static final String SRC = "tracked-src.txt";

	private static final String UNTRACKED_FILE = "untracked-src.txt";

	private static final String DST = "tracked-dst.txt";

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);
		writeTrashFile(SRC
		writeTrashFile(UNTRACKED_FILE
		git.add().addFilepattern(SRC).call();
		git.commit().setMessage("Initial commit").call();
	}

	@Test
	public void testMoveTrackedFile() throws Exception {
		assertEquals("[tracked-src.txt
				indexState(CONTENT));
		MvCommand command = git.mv();
		command.setSource(SRC);
		command.setDestination(DST);
		command.call();
		assertEquals("[tracked-dst.txt
				indexState(CONTENT));
	}

	@Test(expected = MoveException.class)
	public void testMoveUntrackedFile() throws Exception {
		git.mv().setSource(UNTRACKED_FILE).setDestination(DST).call();
	}

	@Test(expected = NoFilepatternException.class)
	public void testNoSourceProvided() throws Exception {
		git.mv().setDestination(DST).call();
	}

	@Test(expected = NoFilepatternException.class)
	public void testNoDestinationProvided() throws Exception {
		git.mv().setSource(SRC).call();
	}

}
