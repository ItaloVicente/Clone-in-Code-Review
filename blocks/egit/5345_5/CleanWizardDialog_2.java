package org.eclipse.egit.ui.internal.clean;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jgit.lib.Repository;

public class CleanWizard extends Wizard {

	private CleanRepositoryPage cleanPage;

	public CleanWizard(Repository repository) {
		setNeedsProgressMonitor(true);
		cleanPage = new CleanRepositoryPage(repository);
		addPage(cleanPage);
	}

	@Override
	public boolean performFinish() {
		cleanPage.finish();
		return true;
	}

}
