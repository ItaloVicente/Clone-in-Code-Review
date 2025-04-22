package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.lib.SubmoduleConfig.FetchRecurseSubmodulesMode;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.submodule.SubmoduleStatus;
import org.eclipse.jgit.submodule.SubmoduleStatusType;
import org.eclipse.jgit.submodule.SubmoduleWalk;
import org.eclipse.jgit.transport.FetchResult;
import org.eclipse.jgit.transport.RefSpec;
import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class FetchAndPullCommandsRecurseSubmodulesTest extends RepositoryTestCase {
	@DataPoints
	public static boolean[] useFetch = { true

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

	private final String PATH = "sub";

	@Before
	public void setUpSubmodules() throws Exception {
		git = new Git(db);

		File submodule1 = createTempDirectory(
				"testCloneRepositoryWithNestedSubmodules1");
		sub1Git = Git.init().setDirectory(submodule1).call();
		assertNotNull(sub1Git);
		Repository sub1 = sub1Git.getRepository();
		assertNotNull(sub1);
		addRepoToClose(sub1);

		String file = "file.txt";

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

		Repository r2 = sub1Git.submoduleAdd().setPath(PATH)
				.setURI(sub2.getDirectory().toURI().toString()).call();
		assertNotNull(r2);
		addRepoToClose(r2);
		RevCommit sub1Head = sub1Git.commit().setAll(true)
				.setMessage("Adding submodule").call();
		assertNotNull(sub1Head);

		Repository r1 = git.submoduleAdd().setPath(PATH)
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

	@Theory
	public void shouldNotFetchSubmodulesWhenNo(boolean fetch) throws Exception {
		FetchResult result = execute(FetchRecurseSubmodulesMode.NO
		assertTrue(result.submoduleResults().isEmpty());
		assertSubmoduleFetchHeads(submodule1Head
	}

	@Theory
	public void shouldFetchSubmodulesWhenYes(boolean fetch) throws Exception {
		FetchResult result = execute(FetchRecurseSubmodulesMode.YES
		assertTrue(result.submoduleResults().containsKey("sub"));
		FetchResult subResult = result.submoduleResults().get("sub");
		assertTrue(subResult.submoduleResults().containsKey("sub"));
		assertSubmoduleFetchHeads(commit1
	}

	@Theory
	public void shouldFetchSubmodulesWhenOnDemandAndRevisionChanged(
			boolean fetch) throws Exception {
		RevCommit update = updateSubmoduleRevision();
		FetchResult result = execute(FetchRecurseSubmodulesMode.ON_DEMAND
				fetch);

		assertTrue(result.submoduleResults().containsKey("sub"));
		FetchResult subResult = result.submoduleResults().get("sub");

		assertTrue(subResult.submoduleResults().isEmpty());
		assertSubmoduleFetchHeads(commit1

		assertEquals(update
				git2.getRepository().resolve(Constants.FETCH_HEAD));
	}

	@Theory
	public void shouldNotFetchSubmodulesWhenOnDemandAndRevisionNotChanged(
			boolean fetch) throws Exception {
		FetchResult result = execute(FetchRecurseSubmodulesMode.ON_DEMAND
				fetch);
		assertTrue(result.submoduleResults().isEmpty());
		assertSubmoduleFetchHeads(submodule1Head
	}

	@Theory
	public void shouldNotFetchSubmodulesWhenSubmoduleConfigurationSetToNo(
			boolean fetch) throws Exception {
		StoredConfig config = git2.getRepository().getConfig();
		config.setEnum(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_FETCH_RECURSE_SUBMODULES
				FetchRecurseSubmodulesMode.NO);
		config.save();
		updateSubmoduleRevision();
		FetchResult result = execute(null
		assertTrue(result.submoduleResults().isEmpty());
		assertSubmoduleFetchHeads(submodule1Head
	}

	@Theory
	public void shouldFetchSubmodulesWhenSubmoduleConfigurationSetToYes(
			boolean fetch) throws Exception {
		StoredConfig config = git2.getRepository().getConfig();
		config.setEnum(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_FETCH_RECURSE_SUBMODULES
				FetchRecurseSubmodulesMode.YES);
		config.save();
		FetchResult result = execute(null
		assertTrue(result.submoduleResults().containsKey("sub"));
		FetchResult subResult = result.submoduleResults().get("sub");
		assertTrue(subResult.submoduleResults().containsKey("sub"));
		assertSubmoduleFetchHeads(commit1
	}

	@Theory
	public void shouldNotFetchSubmodulesWhenFetchConfigurationSetToNo(
			boolean fetch) throws Exception {
		StoredConfig config = git2.getRepository().getConfig();
		config.setEnum(ConfigConstants.CONFIG_FETCH_SECTION
				ConfigConstants.CONFIG_KEY_RECURSE_SUBMODULES
				FetchRecurseSubmodulesMode.NO);
		config.save();
		updateSubmoduleRevision();
		FetchResult result = execute(null
		assertTrue(result.submoduleResults().isEmpty());
		assertSubmoduleFetchHeads(submodule1Head
	}

	@Theory
	public void shouldFetchSubmodulesWhenFetchConfigurationSetToYes(
			boolean fetch) throws Exception {
		StoredConfig config = git2.getRepository().getConfig();
		config.setEnum(ConfigConstants.CONFIG_FETCH_SECTION
				ConfigConstants.CONFIG_KEY_RECURSE_SUBMODULES
				FetchRecurseSubmodulesMode.YES);
		config.save();
		FetchResult result = execute(null
		assertTrue(result.submoduleResults().containsKey("sub"));
		FetchResult subResult = result.submoduleResults().get("sub");
		assertTrue(subResult.submoduleResults().containsKey("sub"));
		assertSubmoduleFetchHeads(commit1
	}

	private RevCommit updateSubmoduleRevision() throws Exception {
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

		return update;
	}

	private FetchResult execute(FetchRecurseSubmodulesMode mode
			throws Exception {
		FetchResult result;

		if (fetch) {
			result = git2.fetch().setRemote(REMOTE).setRefSpecs(REFSPEC)
					.setRecurseSubmodules(mode).call();
		} else {
			result = git2.pull().setRemote(REMOTE).setRebase(true)
					.setRecurseSubmodules(mode).call().getFetchResult();
		}
		assertNotNull(result);
		return result;
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
