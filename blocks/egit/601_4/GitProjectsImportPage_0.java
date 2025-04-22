package org.eclipse.egit.ui.internal.clone;

import java.io.File;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jgit.lib.Repository;

public class GitImportProjectsWizard extends Wizard {

	private final String myWorkingDir;

	private final File myGitDir;

	public GitImportProjectsWizard(Repository repository, String path) {
		super();
		myWorkingDir = path;
		myGitDir = repository.getDirectory();
		setWindowTitle("Import existing projects");
	}

	@Override
	public void addPages() {

		GitProjectsImportPage page = new GitProjectsImportPage() {

			@Override
			public void setVisible(boolean visible) {
				setGitDir(myGitDir);
				setProjectsList(myWorkingDir);
				super.setVisible(visible);
			}

		};
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		GitProjectsImportPage page = (GitProjectsImportPage) getPages()[0];
		return page.createProjects();

	}

}
