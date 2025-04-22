package org.eclipse.egit.ui.common;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.egit.ui.test.Eclipse;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(SWTBotJunit4ClassRunner.class)
public class EGitTestCase {

	static {
		System.setProperty("org.eclipse.swtbot.playback.delay", "50");
	}

	protected static final SWTWorkbenchBot bot = new SWTWorkbenchBot();

	@BeforeClass
	public static void closeWelcomePage() {
		try {
			bot.viewByTitle("Welcome").close();
		} catch (WidgetNotFoundException e) {
		}
	}

	@After
	public void resetWorkbench() {
		new Eclipse().reset();
	}

	protected static void waitForWorkspaceRefresh() {
		WorkspaceRefreshHook wrh = new WorkspaceRefreshHook();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(wrh);

		try {
			bot.waitUntil(wrh, 120000);
		} finally {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(wrh);
		}
	}

	private static class WorkspaceRefreshHook extends DefaultCondition
			implements IResourceChangeListener {
		private boolean state = false;

		public void resourceChanged(IResourceChangeEvent event) {
			if (event.getType() == IResourceChangeEvent.POST_CHANGE) {
				state = true;
			}
		}

		public String getFailureMessage() {
			return "Failed waiting for workspace refresh.";
		}

		public boolean test() throws Exception {
			return state;
		}
	}

}
