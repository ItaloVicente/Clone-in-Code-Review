package org.eclipse.egit.gitflow.op;

import org.eclipse.egit.gitflow.GitFlowRepository;
import org.eclipse.jgit.lib.Repository;

abstract public class AbstractFeatureOperationTest extends
		AbstractGitFlowOperationTest {

	protected GitFlowRepository init(String initalCommit) throws Exception {
		testRepository.createInitialCommit(initalCommit);
		Repository repository = testRepository.getRepository();
		new InitOperation(repository).execute(null);
		return new GitFlowRepository(repository);
	}
}
