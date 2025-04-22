package org.eclipse.egit.ui.internal.fetch;

import org.eclipse.egit.ui.internal.UIIcons;
import org.eclipse.jgit.lib.Repository;

public class FetchChangeFromServerWizard extends AbstractFetchFromHostWizard {

	private final GitServer server;

	public FetchChangeFromServerWizard(GitServer server,
			Repository repository) {
		this(server, repository, null);
	}

	public FetchChangeFromServerWizard(GitServer server, Repository repository,
			String refName) {
		super(repository, refName);
		this.server = server;
		setWindowTitle(server.getWizardTitle());
		setDefaultPageImageDescriptor(UIIcons.WIZBAN_FETCH);
	}

	@Override
	protected AbstractFetchFromHostPage createPage(Repository repo,
			String initialText) {
		return new FetchChangeFromServerPage(server, repo, initialText);
	}
}
