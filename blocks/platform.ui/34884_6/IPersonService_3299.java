
package org.eclipse.ui.examples.contributions.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.examples.contributions.ContributionMessages;
import org.eclipse.ui.handlers.HandlerUtil;

public class InfoAboutHandler extends AbstractHandler {

	private final class InfoAboutDialog extends Dialog {
		private InfoAboutDialog(Shell parentShell) {
			super(parentShell);
		}

		protected void configureShell(Shell newShell) {
			super.configureShell(newShell);
			newShell.setText(ContributionMessages.InfoView_about_msg);
		}
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil
				.getActiveWorkbenchWindowChecked(event);
		Dialog dialog = new InfoAboutDialog(window.getShell());
		dialog.open();
		return null;
	}

}
