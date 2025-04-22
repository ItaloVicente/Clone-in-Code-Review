package org.eclipse.egit.ui.internal.repository.tree.command;

import java.io.File;
import java.net.URISyntaxException;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.components.RepositorySelectionPage.Protocol;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNode;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.RepositoryCache;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.util.FS;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;

public class PasteCommand extends
		RepositoriesViewCommandHandler<RepositoryTreeNode> {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		String errorMessage = null;

		Clipboard clip = null;
		try {
			clip = new Clipboard(getShell(event).getDisplay());
			String content = (String) clip.getContents(TextTransfer
					.getInstance());
			if (content == null) {
				errorMessage = UIText.RepositoriesView_NothingToPasteMessage;
				return null;
			}

			File file = new File(content);
			if (!file.exists() || !file.isDirectory()) {
				URIish cloneURI = getCloneURI(content);
				if (cloneURI == null) {
					errorMessage = UIText.RepositoriesView_ClipboardContentNotDirectoryOrURIMessage;
					return null;
				} else {
					CloneCommand cmd = new CloneCommand(cloneURI.toString());
					cmd.execute(event);
					return null;
				}
			}

			if (!RepositoryCache.FileKey.isGitRepository(file, FS.DETECTED)) {
				file = new File(file, Constants.DOT_GIT);
				if (!RepositoryCache.FileKey.isGitRepository(file, FS.DETECTED)) {
					errorMessage = NLS
							.bind(UIText.RepositoriesView_ClipboardContentNoGitRepoMessage,
									content);
					return null;
				}
			}

			if (util.addConfiguredRepository(file)) {
			} else {
				errorMessage = NLS.bind(
						UIText.RepositoriesView_PasteRepoAlreadyThere, content);
			}
			return null;
		} finally {
			if (clip != null) {
				clip.dispose();
			}
			if (errorMessage != null) {
				Activator.showError(errorMessage, null);
			}
		}
	}

	private URIish getCloneURI(String content) {
		if (content.startsWith("git clone")) { //$NON-NLS-1$
			content = content.substring("git clone".length()); //$NON-NLS-1$
		}
		URIish finalURI;
		try {
			finalURI = new URIish(content.trim());
			if (Protocol.FILE.handles(finalURI)
					|| Protocol.GIT.handles(finalURI)
					|| Protocol.HTTP.handles(finalURI)
					|| Protocol.HTTPS.handles(finalURI)
					|| Protocol.SSH.handles(finalURI)) {
				return finalURI;
			}
		} catch (URISyntaxException e) {
		}
		return null;
	}
}
