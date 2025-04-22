package org.eclipse.egit.ui.test.team.actions;

import org.eclipse.egit.ui.UIText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCombo;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;

public class OptionsPage {

	private SWTBotShell shell;

	public OptionsPage(SWTBotShell shell) {
		this.shell = shell;
	}

	private SWTBotCombo getFormatCombo() {
		return shell.bot().comboBoxWithLabel(UIText.GitCreatePatchWizard_Format);
	}

	public void setFormat(final String selection) {
		getFormatCombo().setSelection(selection);
	}
}
