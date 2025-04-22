
package org.eclipse.egit.ui.internal.history.command;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.rebase.RebaseInteractiveView;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Ref.Storage;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revplot.PlotCommit;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.ui.handlers.HandlerUtil;

public class RebaseInteractiveHandler extends AbstractHistoryCommandHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {

		PlotCommit commit = (PlotCommit) getSelection(getPage())
				.getFirstElement();
		if (commit == null)
			return null;
		final Repository repository = getRepository(event);
		if (repository == null)
			return null;

		if (hasCommitAlreadyBeenPushedToRemotes(repository, commit)) {
			boolean rebaseAnyway = MessageDialog
					.openQuestion(
							HandlerUtil.getActiveShellChecked(event),
							UIText.RebaseInteractiveHandler_CommitAlreadyPushed,
							UIText.RebaseInteractiveHandler_CommitAlreadyPushed_Message);
			if (!rebaseAnyway)
				return null;
		}

		try {
			RebaseInteractiveView rebaseInteractiveView = (RebaseInteractiveView) HandlerUtil
					.getActiveWorkbenchWindow(event).getActivePage()
					.showView(RebaseInteractiveView.VIEW_ID);

			Ref ref = getRef(commit);
			rebaseInteractiveView.startRebaseInteractiveAndOpen(repository, ref);

		} catch (Exception e) {
			Activator.handleError(e.getMessage(), e, true);
		}
		return null;
	}

	private boolean hasCommitAlreadyBeenPushedToRemotes(Repository repository,
			RevCommit commit) {
		return false;
	}

	private Ref getRef(PlotCommit commit) {
		return new ObjectIdRef.Unpeeled(Storage.LOOSE, commit.getName(), commit);
	}
}
