package org.eclipse.egit.ui.internal.push;

import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.components.RepositorySelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.URIish;

public class AddRemoteWizard extends Wizard {

	private AddRemotePage page;

	private URIish uri;

	private String remoteName;

	public AddRemoteWizard(Repository repository) {
		setWindowTitle(UIText.AddRemoteWizard_Title);
		page = new AddRemotePage(repository);
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		uri = page.getSelection().getURI();
		remoteName = page.getRemoteName();
		return uri != null;
	}

	public RepositorySelection getRepositorySelection() {
		return page.getSelection();
	}

	AddRemotePage getAddRemotePage() {
		return page;
	}

	public URIish getUri() {
		return uri;
	}

	public String getRemoteName() {
		return remoteName;
	}
}
