package org.eclipse.ui.examples.contributions.rcp;

import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	private IWorkbenchAction openWindow;
	private IWorkbenchAction save;
	private IWorkbenchAction saveAll;

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	protected void makeActions(final IWorkbenchWindow window) {

		openWindow = ActionFactory.OPEN_NEW_WINDOW.create(window);
		register(openWindow);

		save = ActionFactory.SAVE.create(window);
		register(save);

		saveAll = ActionFactory.SAVE_ALL.create(window);
		register(saveAll);
	}

	public void dispose() {
		super.dispose();
		openWindow = null;
		save = null;
		saveAll = null;
	}
}
