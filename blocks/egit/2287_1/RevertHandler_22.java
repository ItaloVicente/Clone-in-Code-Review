
package org.eclipse.egit.ui.internal.history.command;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.UIText;
import org.eclipse.egit.ui.internal.rebase.RebaseHelper;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.Ref.Storage;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.osgi.util.NLS;

public class RebaseCurrentHandler extends AbstractHistoryCommanndHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {

		RevCommit commit = (RevCommit) getSelection(getPage()).getFirstElement();
		final Repository repository = getRepository(event);
		if (repository == null)
			return null;

		String jobname = NLS.bind(
				UIText.RebaseCurrentRefCommand_RebasingCurrentJobName, commit);
		RebaseHelper.runRebaseJob(repository, jobname,
				new ObjectIdRef.Unpeeled(Storage.LOOSE, commit.getName(),
						commit));
		return null;
	}

}
