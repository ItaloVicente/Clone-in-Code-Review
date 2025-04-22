package org.eclipse.egit.ui.internal.repository.tree.command;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.dialogs.NewRemoteDialog;
import org.eclipse.egit.ui.internal.fetch.SimpleConfigureFetchDialog;
import org.eclipse.egit.ui.internal.push.SimpleConfigurePushDialog;
import org.eclipse.egit.ui.internal.repository.tree.RemotesNode;
import org.eclipse.jface.window.Window;
import org.eclipse.jgit.lib.Repository;

public class ConfigureRemoteCommand extends
		RepositoriesViewCommandHandler<RemotesNode> {
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		RemotesNode node = getSelectedNodes(event).get(0);
		Repository repository = node.getRepository();

		NewRemoteDialog nrd = new NewRemoteDialog(getShell(event), repository);
		if (nrd.open() != Window.OK)
			return null;

		if (nrd.getPushMode())
			SimpleConfigurePushDialog.getDialog(getShell(event), repository,
					nrd.getName()).open();
		else
			SimpleConfigureFetchDialog.getDialog(getShell(event), repository,
					nrd.getName()).open();
		return null;
	}
}
