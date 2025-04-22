package org.eclipse.egit.ui.common;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.waits.Conditions;

public class RefSpecPageTester {

	private final SWTWorkbenchBot bot = new SWTWorkbenchBot();
	
	public void waitUntilPageIsReady(int nrOfEcpectedTableEntries) {
		bot.waitUntil(Conditions.tableHasRows(bot.table(), nrOfEcpectedTableEntries), 20000); 
	}
}
