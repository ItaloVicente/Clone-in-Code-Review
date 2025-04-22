package org.eclipse.egit.ui.common;

import org.eclipse.egit.ui.UIText;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;

public class LoginDialogTester {

	private final SWTWorkbenchBot bot = new SWTWorkbenchBot();
	
	public void login(String user, String password) {
		bot.textWithLabel(UIText.LoginDialog_user).setText(user);
		bot.textWithLabel(UIText.LoginDialog_password).setText(password);
		bot.checkBoxWithLabel(UIText.LoginDialog_storeInSecureStore).deselect();
		bot.button(IDialogConstants.OK_LABEL).click();
	}

}
