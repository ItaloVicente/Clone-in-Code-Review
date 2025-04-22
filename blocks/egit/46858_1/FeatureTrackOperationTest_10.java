package org.eclipse.egit.gitflow.op;

import static org.junit.Assert.assertEquals;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.egit.core.op.BranchOperation;
import org.eclipse.egit.gitflow.GitFlowRepository;
import org.eclipse.jgit.lib.Repository;
import org.junit.Test;

@SuppressWarnings("restriction")
public class FeatureStartOperationTest extends AbstractFeatureOperationTest {
	@Test
	public void testFeatureStart() throws Exception {
		testRepository
				.createInitialCommit("testFeatureStart\n\nfirst commit\n");

		Repository repository = testRepository.getRepository();
		new InitOperation(repository).execute(null);
		GitFlowRepository gfRepo = new GitFlowRepository(repository);

		new FeatureStartOperation(gfRepo, MY_FEATURE).execute(null);

		assertEquals(gfRepo.getFullFeatureBranchName(MY_FEATURE),
				repository.getFullBranch());
	}

	@Test(expected = CoreException.class)
	public void testFeatureStartFail() throws Exception {
		testRepository
				.createInitialCommit("testFeatureStart\n\nfirst commit\n");

		Repository repository = testRepository.getRepository();
		new InitOperation(repository).execute(null);
		GitFlowRepository gfRepo = new GitFlowRepository(repository);

		BranchOperation branchOperation = new BranchOperation(repository,
				MY_MASTER);
		branchOperation.execute(null);

		new FeatureStartOperation(gfRepo, MY_FEATURE).execute(null);
	}
}
