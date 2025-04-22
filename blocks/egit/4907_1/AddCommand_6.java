package org.eclipse.egit.ui.internal.repository;

import java.util.Collection;
import java.util.Set;

import org.eclipse.egit.ui.UIText;
import org.eclipse.jface.wizard.Wizard;

public class RepositorySearchWizard extends Wizard {

	private final Collection<String> dirs;

	private RepositorySearchDialog searchPage;

	public RepositorySearchWizard(Collection<String> existingDirs) {
		dirs = existingDirs;
		setWindowTitle(UIText.RepositorySearchDialog_AddGitRepositories);
		setNeedsProgressMonitor(true);
	}

	@Override
	public void addPages() {
		searchPage = new RepositorySearchDialog(dirs, true);
		addPage(searchPage);
	}

	public Set<String> getDirectories() {
		return searchPage.getDirectories();
	}

	public boolean performFinish() {
		return true;
	}
}
