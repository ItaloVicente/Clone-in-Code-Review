package org.eclipse.egit.ui.common;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;

public class PushResultDialogTester {

	private final SWTWorkbenchBot bot = new SWTWorkbenchBot();

	public void assertResultMessage(String expectedMessage) {
		bot.styledText(expectedMessage);
	}

	public void closeDialog() {
		bot.button(IDialogConstants.OK_LABEL).click();
	}

}
