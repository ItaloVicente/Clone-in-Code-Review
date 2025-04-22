package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.List;

import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.ReflogEntry;
import org.eclipse.jgit.lib.ReflogReader;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Test;

public class StashDropCommandTest extends RepositoryTestCase {

	private RevCommit head;

	private Git git;

	private File committedFile;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = Git.wrap(db);
		committedFile = writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		head = git.commit().setMessage("add file").call();
		assertNotNull(head);
	}

	@Test(expected = IllegalArgumentException.class)
	public void dropNegativeRef() {
		git.stashDrop().setStashRef(-1);
	}

	@Test
	public void dropWithNoStashedCommits() throws Exception {
		assertNull(git.stashDrop().call());
	}

	@Test
	public void dropWithInvalidLogIndex() throws Exception {
		write(committedFile
		Ref stashRef = git.getRepository().exactRef(Constants.R_STASH);
		assertNull(stashRef);
		RevCommit stashed = git.stashCreate().call();
		assertNotNull(stashed);
		stashRef = git.getRepository().exactRef(Constants.R_STASH);
		assertEquals(stashed
				git.getRepository().exactRef(Constants.R_STASH).getObjectId());
		try {
			assertNull(git.stashDrop().setStashRef(100).call());
			fail("Exception not thrown");
		} catch (JGitInternalException e) {
			assertNotNull(e.getMessage());
			assertTrue(e.getMessage().length() > 0);
		}
	}

	@Test
	public void dropSingleStashedCommit() throws Exception {
		write(committedFile
		Ref stashRef = git.getRepository().exactRef(Constants.R_STASH);
		assertNull(stashRef);
		RevCommit stashed = git.stashCreate().call();
		assertNotNull(stashed);
		stashRef = git.getRepository().exactRef(Constants.R_STASH);
		assertEquals(stashed
				git.getRepository().exactRef(Constants.R_STASH).getObjectId());
		assertNull(git.stashDrop().call());
		stashRef = git.getRepository().exactRef(Constants.R_STASH);
		assertNull(stashRef);

		ReflogReader reader = git.getRepository().getReflogReader(
				Constants.R_STASH);
		assertNull(reader);
	}

	@Test
	public void dropAll() throws Exception {
		write(committedFile
		Ref stashRef = git.getRepository().exactRef(Constants.R_STASH);
		assertNull(stashRef);
		RevCommit firstStash = git.stashCreate().call();
		assertNotNull(firstStash);
		stashRef = git.getRepository().exactRef(Constants.R_STASH);
		assertNotNull(stashRef);
		assertEquals(firstStash
				git.getRepository().exactRef(Constants.R_STASH).getObjectId());

		write(committedFile
		RevCommit secondStash = git.stashCreate().call();
		assertNotNull(secondStash);
		stashRef = git.getRepository().exactRef(Constants.R_STASH);
		assertNotNull(stashRef);
		assertEquals(secondStash
				git.getRepository().exactRef(Constants.R_STASH).getObjectId());

		assertNull(git.stashDrop().setAll(true).call());
		assertNull(git.getRepository().exactRef(Constants.R_STASH));

		ReflogReader reader = git.getRepository().getReflogReader(
				Constants.R_STASH);
		assertNull(reader);
	}

	@Test
	public void dropFirstStashedCommit() throws Exception {
		write(committedFile
		Ref stashRef = git.getRepository().exactRef(Constants.R_STASH);
		assertNull(stashRef);
		RevCommit firstStash = git.stashCreate().call();
		assertNotNull(firstStash);
		stashRef = git.getRepository().exactRef(Constants.R_STASH);
		assertNotNull(stashRef);
		assertEquals(firstStash
				git.getRepository().exactRef(Constants.R_STASH).getObjectId());

		write(committedFile
		RevCommit secondStash = git.stashCreate().call();
		assertNotNull(secondStash);
		stashRef = git.getRepository().exactRef(Constants.R_STASH);
		assertNotNull(stashRef);
		assertEquals(secondStash
				git.getRepository().exactRef(Constants.R_STASH).getObjectId());

		assertEquals(firstStash
		stashRef = git.getRepository().exactRef(Constants.R_STASH);
		assertNotNull(stashRef);
		assertEquals(firstStash

		ReflogReader reader = git.getRepository().getReflogReader(
				Constants.R_STASH);
		List<ReflogEntry> entries = reader.getReverseEntries();
		assertEquals(1
		assertEquals(ObjectId.zeroId()
		assertEquals(firstStash
		assertTrue(entries.get(0).getComment().length() > 0);
	}

	@Test
	public void dropMiddleStashCommit() throws Exception {
		write(committedFile
		Ref stashRef = git.getRepository().exactRef(Constants.R_STASH);
		assertNull(stashRef);
		RevCommit firstStash = git.stashCreate().call();
		assertNotNull(firstStash);
		stashRef = git.getRepository().exactRef(Constants.R_STASH);
		assertNotNull(stashRef);
		assertEquals(firstStash
				git.getRepository().exactRef(Constants.R_STASH).getObjectId());

		write(committedFile
		RevCommit secondStash = git.stashCreate().call();
		assertNotNull(secondStash);
		stashRef = git.getRepository().exactRef(Constants.R_STASH);
		assertNotNull(stashRef);
		assertEquals(secondStash
				git.getRepository().exactRef(Constants.R_STASH).getObjectId());

		write(committedFile
		RevCommit thirdStash = git.stashCreate().call();
		assertNotNull(thirdStash);
		stashRef = git.getRepository().exactRef(Constants.R_STASH);
		assertNotNull(stashRef);
		assertEquals(thirdStash
				git.getRepository().exactRef(Constants.R_STASH).getObjectId());

		assertEquals(thirdStash
		stashRef = git.getRepository().exactRef(Constants.R_STASH);
		assertNotNull(stashRef);
		assertEquals(thirdStash

		ReflogReader reader = git.getRepository().getReflogReader(
				Constants.R_STASH);
		List<ReflogEntry> entries = reader.getReverseEntries();
		assertEquals(2
		assertEquals(ObjectId.zeroId()
		assertEquals(firstStash
		assertTrue(entries.get(1).getComment().length() > 0);
		assertEquals(entries.get(0).getOldId()
		assertEquals(thirdStash
		assertTrue(entries.get(0).getComment().length() > 0);
	}

	@Test
	public void dropBoundaryStashedCommits() throws Exception {
		write(committedFile
		Ref stashRef = git.getRepository().exactRef(Constants.R_STASH);
		assertNull(stashRef);
		RevCommit firstStash = git.stashCreate().call();
		assertNotNull(firstStash);
		stashRef = git.getRepository().exactRef(Constants.R_STASH);
		assertNotNull(stashRef);
		assertEquals(firstStash
				git.getRepository().exactRef(Constants.R_STASH).getObjectId());

		write(committedFile
		RevCommit secondStash = git.stashCreate().call();
		assertNotNull(secondStash);
		stashRef = git.getRepository().exactRef(Constants.R_STASH);
		assertNotNull(stashRef);
		assertEquals(secondStash
				git.getRepository().exactRef(Constants.R_STASH).getObjectId());

		write(committedFile
		RevCommit thirdStash = git.stashCreate().call();
		assertNotNull(thirdStash);
		stashRef = git.getRepository().exactRef(Constants.R_STASH);
		assertNotNull(stashRef);
		assertEquals(thirdStash
				git.getRepository().exactRef(Constants.R_STASH).getObjectId());

		write(committedFile
		RevCommit fourthStash = git.stashCreate().call();
		assertNotNull(fourthStash);
		stashRef = git.getRepository().exactRef(Constants.R_STASH);
		assertNotNull(stashRef);
		assertEquals(fourthStash
				git.getRepository().exactRef(Constants.R_STASH).getObjectId());

		assertEquals(thirdStash
		stashRef = git.getRepository().exactRef(Constants.R_STASH);
		assertNotNull(stashRef);
		assertEquals(thirdStash

		assertEquals(thirdStash
		stashRef = git.getRepository().exactRef(Constants.R_STASH);
		assertNotNull(stashRef);
		assertEquals(thirdStash

		ReflogReader reader = git.getRepository().getReflogReader(
				Constants.R_STASH);
		List<ReflogEntry> entries = reader.getReverseEntries();
		assertEquals(2
		assertEquals(ObjectId.zeroId()
		assertEquals(secondStash
		assertTrue(entries.get(1).getComment().length() > 0);
		assertEquals(entries.get(0).getOldId()
		assertEquals(thirdStash
		assertTrue(entries.get(0).getComment().length() > 0);
	}
}
