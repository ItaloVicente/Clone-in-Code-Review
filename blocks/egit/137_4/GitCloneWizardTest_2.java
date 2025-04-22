package org.eclipse.egit.ui.test;

import java.util.List;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.finders.WorkbenchContentsFinder;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.BoolResult;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;

public class Eclipse {

	private final SWTWorkbenchBot bot;

	public Eclipse() {
		this.bot = new SWTWorkbenchBot();
	}

	public void reset() {
		saveAll();
		closeAllEditors();
		closeAllShells();
	}

	private void closeAllShells() {
		SWTBotShell[] shells = bot.shells();
		for (SWTBotShell shell : shells) {
			if (!isEclipseShell(shell)) {
				shell.close();
			}
		}
	}

	@SuppressWarnings("boxing")
	public static boolean isEclipseShell(final SWTBotShell shell) {
		return UIThreadRunnable.syncExec(new BoolResult() {

			public Boolean run() {
				return new WorkbenchContentsFinder().activeWorkbenchWindow()
						.getShell() == shell.widget;
			}
		});
	}

	public void closeAllEditors() {
		List<? extends SWTBotEditor> editors = bot.editors();
		for (SWTBotEditor editor : editors) {
			editor.close();
		}
	}

	public void saveAll() {
		List<? extends SWTBotEditor> editors = bot.editors();
		for (SWTBotEditor editor : editors) {
			editor.save();
		}
	}

}
