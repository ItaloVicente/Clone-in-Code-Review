package org.eclipse.ui.help;

import java.net.URL;

import org.eclipse.help.IContext;

public abstract class AbstractHelpUI {

	public abstract void displayHelp();

	public void displaySearch() {
	}

	public void displayDynamicHelp() {
	}

	public void search(String expression) {
	}

	public URL resolve(String href, boolean documentOnly) {
		return null;
	}

	public abstract void displayContext(IContext context, int x, int y);

	public abstract void displayHelpResource(String href);

	public abstract boolean isContextHelpDisplayed();
}
