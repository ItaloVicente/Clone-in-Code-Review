
package org.eclipse.ui.internal.help;

import org.eclipse.e4.ui.internal.workbench.IHelpService;

public class HelpServiceImpl implements IHelpService {

	@Override
	public void displayHelp(String contextId) {
		if (contextId != null) {
			WorkbenchHelpSystem.getInstance().displayHelp(contextId);
		}
	}
}
