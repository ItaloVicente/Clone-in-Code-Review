package org.eclipse.egit.ui.internal.repository;

import org.eclipse.egit.core.securestorage.UserPasswordCredentials;
import org.eclipse.egit.ui.internal.SecureStoreUtils;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.components.RepositorySelectionPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jgit.transport.URIish;

public class SelectUriWizard extends Wizard {
	private URIish uri;
	private RepositorySelectionPage page;


	public SelectUriWizard(boolean sourceSelection) {
		page = new RepositorySelectionPage(sourceSelection, null);
		addPage(page);
		setWindowTitle(UIText.SelectUriWiazrd_Title);
	}

	public SelectUriWizard(boolean sourceSelection, String presetUri) {
		page = new RepositorySelectionPage(sourceSelection, presetUri);
		addPage(page);
		setWindowTitle(UIText.SelectUriWiazrd_Title);
	}

	public URIish getUri() {
		return uri;
	}

	@Override
	public boolean performFinish() {
		uri = page.getSelection().getURI();
		if (page.getStoreInSecureStore()) {
			if (!SecureStoreUtils.storeCredentials(page.getCredentials(), uri))
				return false;
		}

		return uri != null;
	}

	public UserPasswordCredentials getCredentials() {
		return page.getCredentials();
	}
}
