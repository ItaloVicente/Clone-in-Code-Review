package org.eclipse.egit.ui.internal.synchronize;

import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.egit.ui.UIText;
import org.eclipse.jface.wizard.Wizard;

public class GitSynchronizeWizard extends Wizard {

	private GitBranchSynchronizeWizardPage page;

	public GitSynchronizeWizard() {
		setWindowTitle(UIText.GitSynchronizeWizard_synchronize);
	}

	@Override
	public void addPages() {
		page = new GitBranchSynchronizeWizardPage();
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		Set<IProject> projects = page.getSelectedProjects();
		IResource[] resources = projects.toArray(new IResource[projects.size()]);

		new GitSynchronize(page.getSelectedBranches(), resources);

		return true;
	}

}
