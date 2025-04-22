package org.eclipse.ui.internal.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IWorkbenchHelpSystem;

public final class DisplayHelpHandler extends AbstractHandler {

	private static final String PARAM_ID_HREF = "href"; //$NON-NLS-1$

	@Override
	public final Object execute(final ExecutionEvent event) {
		final IWorkbenchHelpSystem helpSystem = PlatformUI.getWorkbench()
				.getHelpSystem();
		final String href = event.getParameter(PARAM_ID_HREF);

		if (href == null) {
			helpSystem.displayHelp();
		} else {
			helpSystem.displayHelpResource(href);
		}

		return null;
	}
}
