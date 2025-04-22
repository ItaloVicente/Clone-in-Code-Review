package org.eclipse.egit.ui.internal.fetch;

import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;

public class SimpleFetchRefSpecWizard extends Wizard {

	private final FetchSourcePage sourcePage;

	private final FetchDestinationPage destinationPage;

	private RefSpec result;

	public SimpleFetchRefSpecWizard(Repository localRepository,
			RemoteConfig config) {
		sourcePage = new FetchSourcePage(localRepository, config);
		destinationPage = new FetchDestinationPage(localRepository, config);
		setWindowTitle(UIText.SimpleFetchRefSpecWizard_WizardTitle);
	}

	@Override
	public void addPages() {
		addPage(sourcePage);
		addPage(destinationPage);
	}

	@Override
	public boolean performFinish() {
		StringBuilder sb = new StringBuilder();
		if (destinationPage.isForce()) {
			sb.append('+');
		}
		sb.append(sourcePage.getSource());
		sb.append(':');
		sb.append(destinationPage.getDestination());
		result = new RefSpec(sb.toString());
		return true;
	}

	public RefSpec getSpec() {
		return result;
	}

}
