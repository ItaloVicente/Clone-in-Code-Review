package org.eclipse.egit.ui.gitflow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.egit.gitflow.GitFlowRepository;
import org.eclipse.egit.gitflow.ui.internal.UIText;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SWTBotJunit4ClassRunner.class)
public class FeatureFinishKeepBranchHandlerTest extends
		AbstractFeatureFinishHandlerTest {

	@Test
	public void testFeatureStart() throws Exception {
		init();

		setContentAddAndCommit("bar");

		createFeature(FEATURE_NAME);
		RevCommit featureBranchCommit = setContentAddAndCommit("foo");

		checkoutBranch(DEVELOP);

		checkoutFeature(FEATURE_NAME);

		finishFeature();

		GitFlowRepository gfRepo = new GitFlowRepository(repository);
		RevCommit developHead = gfRepo.findHead();
		assertEquals(developHead, featureBranchCommit);

		assertNotNull(findBranch(gfRepo.getConfig().getFeatureBranchName(FEATURE_NAME)));
	}

	@Override
	protected void selectOptions() {
		bot.checkBox(UIText.FinishFeatureDialog_keepBranch).click();
	}
}
