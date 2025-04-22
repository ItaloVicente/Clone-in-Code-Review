package org.eclipse.jgit.api;

import java.io.IOException;

import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.lib.RepositoryTestCase;

public class RmCommandTest extends RepositoryTestCase {

	private Git git;

	private static final String FILE = "test.txt";

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		git = new Git(db);
		writeTrashFile(FILE
		git.add().addFilepattern(FILE).call();
		git.commit().setMessage("Initial commit").call();
	}

	public void testRemove() throws JGitInternalException
			NoFilepatternException
		assertEquals("[test.txt
				indexState(CONTENT));
		RmCommand command = git.rm();
		command.addFilepattern(FILE);
		command.call();
		assertEquals(""
	}

}
