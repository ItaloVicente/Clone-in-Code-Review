package org.eclipse.egit.ui.gitflow;

import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellIsActive;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.egit.core.op.BranchOperation;
import org.eclipse.egit.gitflow.op.InitOperation;
import org.eclipse.egit.gitflow.ui.Activator;
import org.eclipse.egit.gitflow.ui.internal.JobFamilies;
import org.eclipse.egit.gitflow.ui.internal.UIText;
import org.eclipse.egit.ui.test.ContextMenuHelper;
import org.eclipse.egit.ui.test.TestUtil;
import org.eclipse.swtbot.eclipse.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.ui.PlatformUI;
import org.junit.runner.RunWith;

@RunWith(SWTBotJunit4ClassRunner.class)
public abstract class AbstractFeatureFinishHandlerTest extends AbstractGitflowHandlerTest {

	protected void finishFeature() {
		final SWTBotTree projectExplorerTree = TestUtil.getExplorerTree();
		getProjectItem(projectExplorerTree, PROJ1).select();
		final String[] menuPath = new String[] {
				util.getPluginLocalizedValue("TeamMenu.label"),
				util.getPluginLocalizedValue("TeamGitFlowMenu.name", false, Activator.getDefault().getBundle()),
				util.getPluginLocalizedValue("TeamGitFlowFeatureFinish.name", false, Activator.getDefault().getBundle()) };
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				ContextMenuHelper.clickContextMenuSync(projectExplorerTree, menuPath);
			}
		});
		selectOptions();
		bot.button("OK").click();
		preFinish();
		bot.waitUntil(Conditions.waitForJobs(JobFamilies.GITFLOW_FAMILY, "Git flow jobs"));
	}

	protected void preFinish() {
	}

	abstract protected void selectOptions();

	protected void init() throws CoreException {
		new InitOperation(repository).execute(null);
	}

	@Override
	protected void createFeature(String featureName) {
		final SWTBotTree projectExplorerTree = TestUtil.getExplorerTree();
		getProjectItem(projectExplorerTree, PROJ1).select();
		final String[] menuPath = new String[] {
				util.getPluginLocalizedValue("TeamMenu.label"),
				util.getPluginLocalizedValue("TeamGitFlowMenu.name", false, Activator.getDefault().getBundle()),
				util.getPluginLocalizedValue("TeamGitFlowFeatureStart.name", false, Activator.getDefault().getBundle()) };

		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				ContextMenuHelper.clickContextMenuSync(projectExplorerTree, menuPath);
			}
		});

		bot.waitUntil(shellIsActive(UIText.FeatureStartHandler_provideFeatureName));
		bot.text().typeText(featureName);
		bot.button("OK").click();
		bot.waitUntil(Conditions.waitForJobs(JobFamilies.GITFLOW_FAMILY, "Git flow jobs"));
	}

	@Override
	public void checkoutFeature(String featureName) {
		final SWTBotTree projectExplorerTree = TestUtil.getExplorerTree();
		getProjectItem(projectExplorerTree, PROJ1).select();
		final String[] menuPath = new String[] {
				util.getPluginLocalizedValue("TeamMenu.label"),
				util.getPluginLocalizedValue("TeamGitFlowMenu.name", false, Activator.getDefault().getBundle()),
				util.getPluginLocalizedValue("TeamGitFlowFeatureCheckout.name", false, Activator.getDefault().getBundle()) };

		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				ContextMenuHelper.clickContextMenuSync(projectExplorerTree, menuPath);
			}
		});

		bot.waitUntil(shellIsActive(UIText.FeatureCheckoutHandler_selectFeature));
		bot.table().select(featureName);
		bot.button("OK").click();
		bot.waitUntil(Conditions.waitForJobs(JobFamilies.GITFLOW_FAMILY, "Git flow jobs"));
	}

	protected void checkoutBranch(String branchToCheckout) throws CoreException {
		new BranchOperation(repository, branchToCheckout).execute(null);
	}
}
