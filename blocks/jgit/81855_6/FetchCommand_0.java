package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.SubmoduleConfig.FetchRecurseSubmodulesMode;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.submodule.SubmoduleWalk;
import org.eclipse.jgit.transport.RefSpec;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Theories.class)
public class FetchCommandRecurseSubmodulesTest extends RepositoryTestCase {
	private static final Logger LOG = LoggerFactory
			.getLogger(FetchCommandRecurseSubmodulesTest.class);

	private Git git;

	private final RefSpec REFSPEC = new RefSpec("refs/heads/master");

	private final String REMOTE = "origin";

	@DataPoints
	public static FetchRecurseSubmodulesMode allModes[] = FetchRecurseSubmodulesMode
			.values();

	@Theory
	public void fetchWithRecurseOption(FetchRecurseSubmodulesMode mode)
			throws Exception {
		git = new Git(db);

		LOG.info("Mode: " + mode);
		File submodule1 = createTempDirectory(
				"testCloneRepositoryWithNestedSubmodules1");
		Git sub1Git = Git.init().setDirectory(submodule1).call();
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
		Git sub2Git = Git.init().setDirectory(submodule2).call();
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
		Git git2 = clone.call();
		addRepoToClose(git2.getRepository());
		assertNotNull(git2);

		ObjectId submoduleHead;
		ObjectId submodule2Head;
		try (SubmoduleWalk walk = SubmoduleWalk
				.forIndex(git2.getRepository())) {
			assertTrue(walk.next());
			Repository r = walk.getRepository();
			submoduleHead = r.resolve(Constants.FETCH_HEAD);

			try (SubmoduleWalk walk2 = SubmoduleWalk.forIndex(r)) {
				assertTrue(walk2.next());
				submodule2Head = walk2.getRepository()
						.resolve(Constants.FETCH_HEAD);
			}
		}

		JGitTestUtil.writeTrashFile(r1
		sub1Git.add().addFilepattern("f1.txt").call();
		commit = sub1Git.commit().setMessage("new commit").call();

		JGitTestUtil.writeTrashFile(r2
		sub2Git.add().addFilepattern("f2.txt").call();
		RevCommit commit2 = sub2Git.commit().setMessage("new commit").call();

		FetchCommand fetchCommand = git2.fetch().setRemote(REMOTE)
				.setRefSpecs(REFSPEC).setRecurseSubmodules(mode);
		fetchCommand.call();

		try (SubmoduleWalk walk = SubmoduleWalk
				.forIndex(git2.getRepository())) {
			assertTrue(walk.next());
			ObjectId newHead;
			ObjectId newHead2;
			Repository r = walk.getRepository();
			newHead = r.resolve(Constants.FETCH_HEAD);
			try (SubmoduleWalk walk2 = SubmoduleWalk.forIndex(r)) {
				assertTrue(walk2.next());
				newHead2 = walk2.getRepository().resolve(Constants.FETCH_HEAD);
			}
			switch (mode) {
			case YES:
				assertEquals(commit
				assertEquals(commit2
				break;
			case NO:
				assertEquals(submoduleHead
				assertEquals(submodule2Head
				break;
			case ON_DEMAND:
				break;
			}
		}
	}
}
