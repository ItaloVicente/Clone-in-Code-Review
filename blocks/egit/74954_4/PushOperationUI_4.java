package org.eclipse.egit.ui.internal.jobs;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.egit.core.RepositoryCache;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.jface.action.Action;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryCache.FileKey;
import org.eclipse.jgit.util.FS;

public abstract class RepositoryJobResultAction extends Action {

	private final File repositoryDir;

	private boolean repositoryGone;

	public RepositoryJobResultAction(@NonNull Repository repository,
			String title) {
		super(title);
		this.repositoryDir = repository.getDirectory();
	}

	@Override
	public final void run() {
		Repository repo = null;
		if (!repositoryGone) {
			RepositoryCache repoCache = org.eclipse.egit.core.Activator
					.getDefault().getRepositoryCache();
			repo = repoCache.getRepository(repositoryDir);
			if (repo == null
					&& FileKey.isGitRepository(repositoryDir, FS.DETECTED)) {
				try {
					repo = repoCache.lookupRepository(repositoryDir);
				} catch (IOException e) {
				}
			}
			repositoryGone = repo == null;
		}
		if (repositoryGone || repo == null) {
			Activator.showError(MessageFormat.format(
					UIText.RepositoryJobResultAction_RepositoryGone,
					repositoryDir), null);
			return;
		}
		showResult(repo);
	}

	abstract protected void showResult(@NonNull Repository repository);
}
