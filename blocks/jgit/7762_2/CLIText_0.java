package org.eclipse.jgit.pgm;

import static org.junit.Assert.assertEquals;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.eclipse.jgit.merge.MergeStrategy;
import org.junit.Before;
import org.junit.Test;

public class MergeTest extends CLIRepositoryTestCase {
	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		new Git(db).commit().setMessage("initial commit").call();
	}

	@Test
	public void testMergeSelf() throws Exception {
		assertEquals("Already up-to-date."
	}

	@Test
	public void testFastForward() throws Exception {
		new Git(db).commit().setMessage("initial commit").call();
		new Git(db).branchCreate().setName("side").call();
		writeTrashFile("file"
		new Git(db).add().addFilepattern("file").call();
		new Git(db).commit().setMessage("commit").call();
		new Git(db).checkout().setName("side").call();

		assertEquals("Fast-forward"
	}

	@Test
	public void testMerge() throws Exception {
		new Git(db).commit().setMessage("initial commit").call();
		new Git(db).branchCreate().setName("side").call();
		writeTrashFile("master"
		new Git(db).add().addFilepattern("master").call();
		new Git(db).commit().setMessage("master commit").call();
		new Git(db).checkout().setName("side").call();
		writeTrashFile("side"
		new Git(db).add().addFilepattern("side").call();
		new Git(db).commit().setMessage("side commit").call();

		assertEquals("Merge made by the '" + MergeStrategy.RESOLVE.getName()
				+ "' strategy."
	}
}
