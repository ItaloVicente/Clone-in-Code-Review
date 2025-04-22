package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.SubmoduleConfig.FetchRecurseSubmodulesMode;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.submodule.SubmoduleStatus;
import org.eclipse.jgit.submodule.SubmoduleStatusType;
import org.eclipse.jgit.submodule.SubmoduleWalk;
import org.eclipse.jgit.transport.RefSpec;
import org.junit.Before;
import org.junit.Test;

public class FetchCommandRecurseSubmodulesTest extends RepositoryTestCase {
	private Git git;

	private Git git2;

	private Git sub1Git;

	private Git sub2Git;

	private RevCommit commit1;

	private RevCommit commit2;

	private ObjectId submodule1Head;

	private ObjectId submodule2Head;

	private final RefSpec REFSPEC = new RefSpec("refs/heads/master");

	private final String REMOTE = "origin";

	@Before
	public void setUpSubmodules()
			throws Exception {
		git = new Git(db);

		File submodule1 = createTempDirectory(
				"testCloneRepositoryWithNestedSubmodules1");
		sub1Git = Git.init().setDirectory(submodule1).call();
		assertNotNull(sub1Git);
		Repository sub1 = sub1Git.getRepository();
		assertNotNull(sub1);
		addRepoToClose(sub1);

		String file = "file.txt";
		String path = "sub";

		write(new File(sub1.getWorkTree()
		sub1Git.add().addFilepattern(file).call();
		RevCommit commit = sub1Git.commit().setMessage("create file").call();
		assertNotNull(commit);

		File submodule2 = createTempDirectory(
				"testCloneRepositoryWithNestedSubmodules2");
		sub2Git = Git.init().setDirectory(submodule2).call();
		assertNotNull(sub2Git);
		Repository sub2 = sub2Git.getRepository();
		assertNotNull(sub2);
		addRepoToClose(sub2);

		write(new File(sub2.getWorkTree()
		sub2Git.add().addFilepattern(file).call();
		RevCommit sub2Head = sub2Git.commit().setMessage("create file").call();
		assertNotNull(sub2Head);

		Repository r2 = sub1Git.submoduleAdd().setPath(path)
				.setURI(sub2.getDirectory().toURI().toString()).call();
		assertNotNull(r2);
		addRepoToClose(r2);
		RevCommit sub1Head = sub1Git.commit().setAll(true)
				.setMessage("Adding submodule").call();
		assertNotNull(sub1Head);

		Repository r1 = git.submoduleAdd().setPath(path)
				.setURI(sub1.getDirectory().toURI().toString()).call();
		assertNotNull(r1);
		addRepoToClose(r1);
		assertNotNull(git.commit().setAll(true).setMessage("Adding submodule")
				.call());

		File directory = createTempDirectory(
				"testCloneRepositoryWithNestedSubmodules");
		CloneCommand clone = Git.cloneRepository();
		clone.setDirectory(directory);
		clone.setCloneSubmodules(true);
		clone.setURI(git.getRepository().getDirectory().toURI().toString());
		git2 = clone.call();
		addRepoToClose(git2.getRepository());
		assertNotNull(git2);

		try (SubmoduleWalk walk = SubmoduleWalk
				.forIndex(git2.getRepository())) {
			assertTrue(walk.next());
			Repository r = walk.getRepository();
			submodule1Head = r.resolve(Constants.FETCH_HEAD);

			try (SubmoduleWalk walk2 = SubmoduleWalk.forIndex(r)) {
				assertTrue(walk2.next());
				submodule2Head = walk2.getRepository()
						.resolve(Constants.FETCH_HEAD);
			}
		}

		JGitTestUtil.writeTrashFile(r1
		sub1Git.add().addFilepattern("f1.txt").call();
		commit1 = sub1Git.commit().setMessage("new commit").call();

		JGitTestUtil.writeTrashFile(r2
		sub2Git.add().addFilepattern("f2.txt").call();
		commit2 = sub2Git.commit().setMessage("new commit").call();
	}

	@Test
	public void shouldNotFetchSubmodulesWhenNo() throws Exception {
		fetch(FetchRecurseSubmodulesMode.NO);
		assertSubmoduleFetchHeads(submodule1Head
	}

	@Test
	public void shouldFetchSubmodulesWhenYes() throws Exception {
		fetch(FetchRecurseSubmodulesMode.YES);
		assertSubmoduleFetchHeads(commit1
	}

	@Test
	public void shouldFetchSubmodulesWhenOnDemandAndRevisionChanged()
			throws Exception {
		try (SubmoduleWalk w = SubmoduleWalk.forIndex(git.getRepository())) {
			assertTrue(w.next());
			try (Git g = new Git(w.getRepository())) {
				g.fetch().setRemote(REMOTE).setRefSpecs(REFSPEC).call();
				g.reset().setMode(ResetType.HARD).setRef(commit1.name()).call();
			}
		}

		SubmoduleStatus subStatus = git.submoduleStatus().call().get("sub");
		assertEquals(submodule1Head
		assertEquals(commit1
		assertEquals(SubmoduleStatusType.REV_CHECKED_OUT

		git.add().addFilepattern("sub").call();
		RevCommit update = git.commit().setMessage("update sub").call();

		subStatus = git.submoduleStatus().call().get("sub");
		assertEquals(commit1
		assertEquals(commit1
		assertEquals(SubmoduleStatusType.INITIALIZED

		fetch(FetchRecurseSubmodulesMode.ON_DEMAND);
		assertEquals(update
				git2.getRepository().resolve(Constants.FETCH_HEAD));

		assertSubmoduleFetchHeads(commit1
	}

	@Test
	public void shouldNotFetchSubmodulesWhenOnDemandAndRevisionNotChanged()
			throws Exception {
		fetch(FetchRecurseSubmodulesMode.ON_DEMAND);
		assertSubmoduleFetchHeads(submodule1Head
	}

	private void fetch(FetchRecurseSubmodulesMode mode) throws Exception {
		FetchCommand fetchCommand = git2.fetch().setRemote(REMOTE)
				.setRefSpecs(REFSPEC).setRecurseSubmodules(mode);
		fetchCommand.call();
	}

	private void assertSubmoduleFetchHeads(ObjectId expectedHead1
			ObjectId expectedHead2) throws Exception {
		try (SubmoduleWalk walk = SubmoduleWalk
				.forIndex(git2.getRepository())) {
			assertTrue(walk.next());
			Repository r = walk.getRepository();
			ObjectId newHead1 = r.resolve(Constants.FETCH_HEAD);
			ObjectId newHead2;
			try (SubmoduleWalk walk2 = SubmoduleWalk.forIndex(r)) {
				assertTrue(walk2.next());
				newHead2 = walk2.getRepository().resolve(Constants.FETCH_HEAD);
			}

			assertEquals(expectedHead1
			assertEquals(expectedHead2
		}
	}
}
