package org.eclipse.ui.help;

import java.net.URL;

import org.eclipse.help.IContext;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public interface IWorkbenchHelpSystem {

	boolean hasHelpUI();

	void displayHelp();

	void displaySearch();

	void displayDynamicHelp();

	void search(String expression);

	void displayContext(IContext context, int x, int y);

	void displayHelpResource(String href);

	void displayHelp(String contextId);

	void displayHelp(IContext context);

	boolean isContextHelpDisplayed();

	void setHelp(IAction action, String contextId);

	void setHelp(Control control, String contextId);

	void setHelp(Menu menu, String contextId);

	void setHelp(MenuItem item, String contextId);

	URL resolve(String href, boolean documentOnly);
}
