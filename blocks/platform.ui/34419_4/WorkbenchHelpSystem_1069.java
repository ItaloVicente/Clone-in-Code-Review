
package org.eclipse.ui.internal.help;

import org.eclipse.e4.ui.internal.workbench.EHelpService;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class HelpServiceImpl implements EHelpService {

	@Override
	public void displayHelp(String contextId) {
		if (contextId != null) {
			WorkbenchHelpSystem.getInstance().displayHelp(contextId);
		}
	}

	@Override
	public void setHelp(Object helpTarget, String helpContextId) {
		if (helpTarget instanceof Control) {
			WorkbenchHelpSystem.getInstance().setHelp((Control) helpTarget, helpContextId);
		} else if (helpTarget instanceof IAction) {
			WorkbenchHelpSystem.getInstance().setHelp((IAction) helpTarget, helpContextId);
		} else if (helpTarget instanceof Menu) {
			WorkbenchHelpSystem.getInstance().setHelp((Menu) helpTarget, helpContextId);
		} else if (helpTarget instanceof MenuItem) {
			WorkbenchHelpSystem.getInstance().setHelp((MenuItem) helpTarget, helpContextId);
		}

	}
}
