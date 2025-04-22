package org.eclipse.egit.ui.internal.synchronize;

import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.egit.ui.UIText;
import org.eclipse.egit.ui.internal.synchronize.dto.GitSynchronizeData;
import org.eclipse.egit.ui.internal.synchronize.dto.GitSynchronizeDataSet;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;

public class GitSynchronizeWizard extends Wizard {

	private GitSynchronizeWizardPage page;

	public GitSynchronizeWizard() {
		setWindowTitle(UIText.GitSynchronizeWizard_synchronize);
	}

	@Override
	public void addPages() {
		page = new GitSynchronizeWizardPage();
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		Set<IProject> projects = page.getSelectedProjects();
		GitSynchronizeDataSet gsdSet = new GitSynchronizeDataSet();

		Map<Repository, String> branches = page.getSelectedBranches();
		for (Repository repo : branches.keySet()) {
			gsdSet.add(new GitSynchronizeData(repo, Constants.HEAD, branches.get(repo), projects, false));
		}

		new GitSynchronize(gsdSet);

		return true;
	}

}
