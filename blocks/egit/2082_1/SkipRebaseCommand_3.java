package org.eclipse.egit.ui.internal.repository.tree.command;

import org.eclipse.egit.ui.UIText;
import org.eclipse.jgit.api.RebaseCommand.Operation;

public class ContinueRebaseCommand extends AbstractRebaseCommand {
	public ContinueRebaseCommand() {
		super(Operation.CONTINUE, UIText.ContinueRebaseCommand_JobName,
				UIText.ContinueRebaseCommand_CancelDialogMessage);
	}
}
