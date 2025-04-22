package org.eclipse.jgit.api;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;

public class RevertCommandTest extends RepositoryTestCase {
	@Test
	public void testRevert() throws IOException
			GitAPIException {
		Git git = new Git(db);

		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("create a").call();

		writeTrashFile("b"
		git.add().addFilepattern("b").call();
		git.commit().setMessage("create b").call();

		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("enlarged a").call();

		writeTrashFile("a"
				"first line\nsecond line\nthird line\nfourth line\n");
		git.add().addFilepattern("a").call();
		RevCommit fixingA = git.commit().setMessage("fixed a").call();

		writeTrashFile("b"
		git.add().addFilepattern("b").call();
		git.commit().setMessage("fixed b").call();

		git.revert().include(fixingA).call();

		assertTrue(new File(db.getWorkTree()
		checkFile(new File(db.getWorkTree()
				"first line\nsec. line\nthird line\nfourth line\n");
		Iterator<RevCommit> history = git.log().call().iterator();
		assertEquals("Revert \"fixed a\""
		assertEquals("fixed b"
		assertEquals("fixed a"
		assertEquals("enlarged a"
		assertEquals("create b"
		assertEquals("create a"
		assertFalse(history.hasNext());
	}
}
