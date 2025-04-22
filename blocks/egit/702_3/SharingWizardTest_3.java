package org.eclipse.egit.ui.wizards.share;

import org.eclipse.egit.ui.test.ContextMenuHelper;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;

public class SharingWizard {

	private static final SWTWorkbenchBot bot = new SWTWorkbenchBot();

	public ExistingOrNewPage openWizard(String projectName) {
		SWTBotTree tree = bot.viewByTitle("Package Explorer").bot().tree();

		final SWTBotTreeItem item = tree.getTreeItem(projectName);
		item.select();
		ContextMenuHelper.clickContextMenu(tree, "Team", "Share Project...");

		bot.table().getTableItem("Git").select();
		bot.button("Next >").click();

		return new ExistingOrNewPage();
	}
}
