package org.eclipse.egit.ui.internal.fetch;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jgit.lib.Repository;

public abstract class AbstractFetchFromHostWizard extends Wizard {

	private final Repository repository;

	AbstractFetchFromHostPage page;

	private String refName;

	protected AbstractFetchFromHostWizard(Repository repository) {
		Assert.isNotNull(repository);
		this.repository = repository;
		setNeedsProgressMonitor(true);
		setHelpAvailable(false);
	}

	protected AbstractFetchFromHostWizard(Repository repository,
			String refName) {
		this(repository);
		this.refName = refName;
	}

	@Override
	public void addPages() {
		page = createPage(repository, refName);
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		return page.doFetch();
	}

	protected abstract AbstractFetchFromHostPage createPage(Repository repo,
			String initialText);
}
