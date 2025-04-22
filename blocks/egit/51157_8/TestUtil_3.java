package org.eclipse.egit.ui.gitflow;

import static org.junit.Assert.assertEquals;

import org.eclipse.egit.gitflow.ui.Activator;
import org.eclipse.egit.gitflow.ui.internal.JobFamilies;
import org.eclipse.egit.ui.test.ContextMenuHelper;
import org.eclipse.egit.ui.test.TestUtil;
import org.eclipse.swtbot.eclipse.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SWTBotJunit4ClassRunner.class)
public class InitHandlerTest extends AbstractGitflowHandlerTest {

	@Test
	public void testInit() throws Exception {
		init();

		assertEquals(DEVELOP, repository.getBranch());
	}

	private void init() {
		SWTBotTree projectExplorerTree = TestUtil.getExplorerTree();
		getProjectItem(projectExplorerTree, PROJ1).select();
		String[] menuPath = new String[] {
				util.getPluginLocalizedValue("TeamMenu.label"),
				util.getPluginLocalizedValue("TeamGitFlowInit.name", false, Activator.getDefault().getBundle()) };
		ContextMenuHelper.clickContextMenuSync(projectExplorerTree, menuPath);
		bot.waitUntil(Conditions.waitForJobs(JobFamilies.GITFLOW_FAMILY, "Git flow jobs"));
	}
}
