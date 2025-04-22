package org.eclipse.egit.ui.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.egit.ui.internal.branch.CleanupUncomittedChangesDialog;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.swt.widgets.Shell;

public final class UIRepositoryUtils {
	private UIRepositoryUtils() {
	}

	public static boolean handleUncommittedFiles(Repository repo, Shell shell)
			throws GitAPIException {
		Status status = new Git(repo).status().call();
		if (status.hasUncommittedChanges()) {
			List<String> files = new ArrayList<String>(status.getModified());
			Collections.sort(files);

			CleanupUncomittedChangesDialog cleanupUncomittedChangesDialog = new CleanupUncomittedChangesDialog(
					shell,
					UIText.AbstractRebaseCommandHandler_cleanupDialog_title,
					UIText.AbstractRebaseCommandHandler_cleanupDialog_text,
					repo, files);
			cleanupUncomittedChangesDialog.open();
			return cleanupUncomittedChangesDialog.shouldContinue();
		} else
			return false;
	}
}
