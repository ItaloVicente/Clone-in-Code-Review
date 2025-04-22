package org.eclipse.egit.ui.internal.repository.tree.command;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIText;
import org.eclipse.egit.ui.internal.repository.tree.RefNode;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.osgi.util.NLS;

public class DeleteBranchCommand extends
		RepositoriesViewCommandHandler<RefNode> {
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final RefNode node = getSelectedNodes(event).get(0);
		final Ref ref = node.getObject();

		if (!MessageDialog.openConfirm(getView(event).getSite().getShell(),
				UIText.RepositoriesView_ConfirmDeleteTitle, NLS.bind(
						UIText.RepositoriesView_ConfirmBranchDeletionMessage,
						ref.getName())))
			return null;

		try {
			new ProgressMonitorDialog(getView(event).getSite().getShell()).run(
					false, false, new IRunnableWithProgress() {
						public void run(IProgressMonitor monitor)
								throws InvocationTargetException,
								InterruptedException {
							try {
								RefUpdate op = node.getRepository().updateRef(
										ref.getName());
								op.setRefLogMessage("branch deleted", //$NON-NLS-1$
										false);
								op.setForceUpdate(true);
								op.delete();
							} catch (IOException ioe) {
								throw new InvocationTargetException(ioe);
							}
						}
					});
		} catch (InvocationTargetException e1) {
			Activator.handleError(
					UIText.RepositoriesView_BranchDeletionFailureMessage, e1
							.getCause(), true);
			e1.printStackTrace();
		} catch (InterruptedException e1) {
		}

		return null;
	}
}
