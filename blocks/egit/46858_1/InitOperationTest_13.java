package org.eclipse.egit.gitflow.op;

import static org.junit.Assert.assertEquals;

import org.eclipse.egit.gitflow.GitFlowRepository;
import org.eclipse.jgit.lib.Repository;
import org.junit.Test;

public class HotfixStartOperationTest extends AbstractGitFlowOperationTest {
	@Test
	public void testHotfixStart() throws Exception {
		testRepository.createInitialCommit("testHotfixStart\n\nfirst commit\n");

		Repository repository = testRepository.getRepository();
		new InitOperation(repository).execute(null);
		GitFlowRepository gfRepo = new GitFlowRepository(repository);

		new HotfixStartOperation(gfRepo, MY_HOTFIX).execute(null);

		assertEquals(gfRepo.getFullHotfixBranchName(MY_HOTFIX),
				repository.getFullBranch());

	}
}
