package org.eclipse.egit.ui.internal.commands.shared;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.dialogs.BasicConfigurationDialog;
import org.eclipse.egit.ui.internal.rebase.RebaseHelper;
import org.eclipse.jgit.api.RebaseCommand;
import org.eclipse.jgit.lib.Repository;

public class RebaseInteractiveCommand extends AbstractRebaseCommandHandler {

	public RebaseInteractiveCommand() {
		super(RebaseCommand.Operation.BEGIN, "some jobname", //$NON-NLS-1$
				RebaseHelper.DEFAULT_CANCEL_DIALOG_MESSAGE, true);
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Repository repository = getRepository(event);
		BasicConfigurationDialog.show(repository);
		return super.execute(event);
	}

}
