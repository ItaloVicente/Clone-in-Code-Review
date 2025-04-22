package org.eclipse.egit.gitflow.op;

import static org.eclipse.egit.gitflow.GitFlowDefaults.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.File;

import org.eclipse.egit.core.op.BranchOperation;
import org.eclipse.egit.gitflow.GitFlowRepository;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;

public class HotfixFinishOperationTest extends AbstractGitFlowOperationTest {
	@Test
	public void testHotfixFinish() throws Exception {
		testRepository
				.createInitialCommit("testHotfixFinish\n\nfirst commit\n");

		Repository repository = testRepository.getRepository();
		new InitOperation(repository).execute(null);
		GitFlowRepository gfRepo = new GitFlowRepository(repository);

		new HotfixStartOperation(gfRepo, MY_HOTFIX).execute(null);

		RevCommit branchCommit = testRepository
				.createInitialCommit("testHotfixFinish\n\nbranch commit\n");

		new HotfixFinishOperation(gfRepo).execute(null);

		assertEquals(gfRepo.getDevelopFull(), repository.getFullBranch());

		String branchName = gfRepo.getHotfixBranchName(MY_HOTFIX);

		assertEquals(branchCommit, gfRepo.findCommitForTag(MY_HOTFIX));

		assertEquals(findBranch(repository, branchName), null);

		RevCommit developHead = gfRepo.findHead(DEVELOP);
		assertEquals(branchCommit, developHead);

		RevCommit masterHead = gfRepo.findHead(MY_MASTER);
		assertEquals(branchCommit, masterHead);
	}

	@Test
	public void testMergeToDevelopFail() throws Exception {
		testRepository
				.createInitialCommit("testMergeToDevelopFail\n\nfirst commit\n");

		Repository repository = testRepository.getRepository();
		new InitOperation(repository).execute(null);
		GitFlowRepository gfRepo = new GitFlowRepository(repository);

		File file = testRepository.createFile(project.getProject(),
				"folder1/file1.txt");

		new ReleaseStartOperation(gfRepo, MY_RELEASE).execute(null);

		testRepository.appendContentAndCommit(project.getProject(), file,
				"Hello Release", "Release Commit");

		new ReleaseFinishOperation(gfRepo).execute(null);

		new HotfixStartOperation(gfRepo, MY_HOTFIX).execute(null);
		RevCommit hotfixCommit = testRepository.appendContentAndCommit(
				project.getProject(), file, "Hello Hotfix", "Hotfix Commit");
		new BranchOperation(repository, gfRepo.getDevelop()).execute(null);
		assertEquals(gfRepo.getDevelopFull(), repository.getFullBranch());

		RevCommit developCommit = testRepository.appendContentAndCommit(
				project.getProject(), file, "Hello Develop", "Develop Commit");

		String branchName = gfRepo.getHotfixBranchName(MY_HOTFIX);
		new BranchOperation(repository, branchName).execute(null);
		HotfixFinishOperation hotfixFinishOperation = new HotfixFinishOperation(
				gfRepo);
		hotfixFinishOperation.execute(null);

		assertNotEquals(hotfixCommit, gfRepo.findCommitForTag(MY_HOTFIX));

		assertNotEquals(findBranch(repository, branchName), null);

		RevCommit developHead = gfRepo.findHead(DEVELOP);
		assertEquals(developCommit, developHead);
		assertEquals(MergeResult.MergeStatus.CONFLICTING, hotfixFinishOperation
				.getOperationResult().getMergeStatus());

		RevCommit masterHead = gfRepo.findHead(MY_MASTER);
		assertEquals(hotfixCommit, masterHead);

		assertEquals(gfRepo.getDevelopFull(), repository.getFullBranch());
	}
}
