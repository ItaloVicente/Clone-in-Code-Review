package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;

import org.eclipse.jgit.lib.RepositoryTestCase;
import org.junit.Before;
import org.junit.Test;

public class BranchCommandTest extends RepositoryTestCase {
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
