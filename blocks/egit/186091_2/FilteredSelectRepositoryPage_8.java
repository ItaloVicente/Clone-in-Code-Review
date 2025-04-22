package org.eclipse.egit.ui.internal.fetch;

import org.eclipse.egit.ui.internal.UIIcons;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.jgit.lib.Repository;

public class FetchGithubPullRequestWizard extends AbstractFetchFromHostWizard {

	public FetchGithubPullRequestWizard(Repository repository) {
		super(repository);
		setWindowTitle(UIText.FetchGithubPullRequestWizard_WizardTitle);
		setDefaultPageImageDescriptor(UIIcons.WIZBAN_FETCH);
	}

	public FetchGithubPullRequestWizard(Repository repository, String refName) {
		super(repository, refName);
		setWindowTitle(UIText.FetchGithubPullRequestWizard_WizardTitle);
		setDefaultPageImageDescriptor(UIIcons.WIZBAN_FETCH);
	}

	@Override
	protected AbstractFetchFromHostPage createPage(Repository repo,
			String initialText) {
		return new FetchGithubPullRequestPage(repo, initialText);
	}
}
