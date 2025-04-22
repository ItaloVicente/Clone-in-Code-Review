package org.eclipse.jgit.pgm;

import static org.junit.Assert.assertEquals;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.junit.Before;
import org.junit.Test;

public class BranchTest extends CLIRepositoryTestCase {
	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		new Git(db).commit().setMessage("initial commit").call();
	}

	@Test
	public void testList() throws Exception {
		assertEquals("* master 6fd41be initial commit"
				execute("git branch -v")[0]);
	}
}
