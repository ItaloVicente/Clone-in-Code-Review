package org.eclipse.egit.ui.internal.submodule;

import org.eclipse.egit.ui.UIIcons;
import org.eclipse.egit.ui.UIText;
import org.eclipse.egit.ui.internal.SecureStoreUtils;
import org.eclipse.egit.ui.internal.components.RepositorySelectionPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.URIish;

public class AddSubmoduleWizard extends Wizard {

	private final Repository repo;

	private SubmodulePathWizardPage pathPage;

	private RepositorySelectionPage uriPage;

	public AddSubmoduleWizard(Repository repo) {
		this.repo = repo;
		setWindowTitle(UIText.AddSubmoduleWizard_WindowTitle);
		setDefaultPageImageDescriptor(UIIcons.WIZBAN_IMPORT_REPO);
	}

	public void addPages() {
		pathPage = new SubmodulePathWizardPage(repo);
		addPage(pathPage);
		uriPage = new RepositorySelectionPage(true, null);
		uriPage.setPageComplete(false);
		addPage(uriPage);
	}

	public String getPath() {
		return pathPage.getPath();
	}

	public URIish getUri() {
		return uriPage.getSelection().getURI();
	}

	public boolean performFinish() {
		if (uriPage.getStoreInSecureStore()
				&& !SecureStoreUtils.storeCredentials(uriPage.getCredentials(),
						uriPage.getSelection().getURI()))
			return false;

		return true;
	}
}
