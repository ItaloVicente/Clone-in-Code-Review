package org.eclipse.egit.ui.internal.commands.shared;

import java.net.URISyntaxException;

import org.eclipse.egit.core.internal.hosts.GitHosts;
import org.eclipse.egit.ui.internal.UIIcons;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.clone.GitSelectRepositoryPage;
import org.eclipse.egit.ui.internal.fetch.FetchGithubPullRequestWizard;
import org.eclipse.egit.ui.internal.gerrit.FilteredSelectRepositoryPage;
import org.eclipse.egit.ui.internal.selection.SelectionRepositoryStateCache;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jgit.lib.Repository;

public class FetchPullRequestFromGithubCommand
		extends AbstractFetchFromHostCommand {

	@Override
	protected GitSelectRepositoryPage createSelectionPage() {
		return new FilteredSelectRepositoryPage(
				UIText.GithubSelectRepositoryPage_PageTitle,
				UIIcons.WIZBAN_FETCH) {

			@Override
			protected boolean includeRepository(Repository repo) {
				try {
					return GitHosts.hasGithubConfig(
							SelectionRepositoryStateCache.INSTANCE
									.getConfig(repo));
				} catch (URISyntaxException e) {
					return false;
				}
			}
		};
	}

	@Override
	protected Wizard createFetchWizard(Repository repository, String clipText) {
		return new FetchGithubPullRequestWizard(repository, clipText);
	}
}
