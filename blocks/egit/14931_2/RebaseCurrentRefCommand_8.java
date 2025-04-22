package org.eclipse.egit.ui.internal.commands.shared;

import org.eclipse.egit.ui.internal.rebase.RebaseHelper;
import org.eclipse.jgit.api.RebaseCommand;

public class ProcessStepsRebaseCommand extends AbstractRebaseCommandHandler {

	public ProcessStepsRebaseCommand() {
		super(RebaseCommand.Operation.PROCESS_STEPS,
				"some jobname", RebaseHelper.DEFAULT_CANCEL_DIALOG_MESSAGE, true); //$NON-NLS-1$
	}
}
