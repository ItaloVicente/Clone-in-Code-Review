package org.eclipse.egit.ui.internal.repository.tree.command;

import java.io.File;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIText;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNode;
import org.eclipse.jgit.lib.RepositoryCache;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;

public class PasteCommand extends
		RepositoriesViewCommandHandler<RepositoryTreeNode> {

	public Object execute(ExecutionEvent event) throws ExecutionException {

		String errorMessage = null;

		Clipboard clip = null;
		try {
			clip = new Clipboard(getView(event).getSite().getShell()
					.getDisplay());
			String content = (String) clip.getContents(TextTransfer
					.getInstance());
			if (content == null) {
				errorMessage = UIText.RepositoriesView_NothingToPasteMessage;
				return null;
			}

			File file = new File(content);
			if (!file.exists() || !file.isDirectory()) {
				errorMessage = UIText.RepositoriesView_ClipboardContentNotDirectoryMessage;
				return null;
			}

			if (!RepositoryCache.FileKey.isGitRepository(file)) {
				errorMessage = NLS
						.bind(
								UIText.RepositoriesView_ClipboardContentNoGitRepoMessage,
								content);
				return null;
			}

			if (util.addConfiguredRepository(file)) {
			} else
				errorMessage = NLS.bind(
						UIText.RepositoriesView_PasteRepoAlreadyThere, content);

			return null;
		} finally {
			if (clip != null)
				clip.dispose();
			if (errorMessage != null)
				Activator.handleError(errorMessage, null, true);
		}
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isHandled() {
		return true;
	}

}
