package org.eclipse.egit.ui.common;

import org.eclipse.egit.ui.test.Eclipse;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
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

}
