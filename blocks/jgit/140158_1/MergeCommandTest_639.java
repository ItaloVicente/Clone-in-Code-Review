package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.Collection;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.junit.Test;

public class LsRemoteCommandTest extends RepositoryTestCase {

	private Git git;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);
		writeTrashFile("Test.txt"
		git.add().addFilepattern("Test.txt").call();
		git.commit().setMessage("Initial commit").call();

		git.branchCreate().setName("test").call();
		RefUpdate rup = db.updateRef(Constants.HEAD);
		rup.link("refs/heads/test");

		git.tag().setName("tag1").call();
		git.tag().setName("tag2").call();
		git.tag().setName("tag3").call();
	}

	@Test
	public void testLsRemote() throws Exception {
		File directory = createTempDirectory("testRepository");
		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setURI(fileUri());
		command.setCloneAllBranches(true);
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());


		LsRemoteCommand lsRemoteCommand = git2.lsRemote();
		Collection<Ref> refs = lsRemoteCommand.call();
		assertNotNull(refs);
		assertEquals(6
	}

	@Test
	public void testLsRemoteWithTags() throws Exception {
		File directory = createTempDirectory("testRepository");
		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setURI(fileUri());
		command.setCloneAllBranches(true);
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());

		LsRemoteCommand lsRemoteCommand = git2.lsRemote();
		lsRemoteCommand.setTags(true);
		Collection<Ref> refs = lsRemoteCommand.call();
		assertNotNull(refs);
		assertEquals(3
	}

	@Test
	public void testLsRemoteWithHeads() throws Exception {
		File directory = createTempDirectory("testRepository");
		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setURI(fileUri());
		command.setCloneAllBranches(true);
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());

		LsRemoteCommand lsRemoteCommand = git2.lsRemote();
		lsRemoteCommand.setHeads(true);
		Collection<Ref> refs = lsRemoteCommand.call();
		assertNotNull(refs);
		assertEquals(2
	}

	@Test
	public void testLsRemoteWithoutLocalRepository() throws Exception {
		String uri = fileUri();
		Collection<Ref> refs = Git.lsRemoteRepository().setRemote(uri).setHeads(true).call();
		assertNotNull(refs);
		assertEquals(2
	}

	private String fileUri() {
	}

}
