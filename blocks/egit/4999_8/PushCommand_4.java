
package org.eclipse.egit.ui.internal.push;

import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

import org.eclipse.egit.core.op.PushOperation;
import org.eclipse.egit.core.op.PushOperationSpecification;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIPreferences;
import org.eclipse.egit.ui.internal.components.RefContentAssistProvider;
import org.eclipse.egit.ui.internal.components.RepositorySelection;
import org.eclipse.egit.ui.internal.components.RepositorySelectionPage;
import org.eclipse.egit.ui.internal.components.SimplePushSpecPage;
import org.eclipse.egit.ui.internal.push.PushWizard.PushJob;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.RemoteRefUpdate;

public class SimplePushRefWizard extends Wizard {

	private ObjectId pushObj;

	private RepositorySelectionPage repoPage;

	private String nicePushName;

	private Repository repo;

	private SimplePushSpecPage targetPage;

	public SimplePushRefWizard(Repository repo, Ref refToPush)
			throws URISyntaxException {
		this(repo, refToPush.getObjectId(), refToPush.getName());
	}

	public SimplePushRefWizard(Repository repo, ObjectId objectId)
			throws URISyntaxException {
		this(repo, objectId, AbbreviatedObjectId.fromObjectId(objectId).name());
	}

	private SimplePushRefWizard(Repository repo, ObjectId objectId, String name)
			throws URISyntaxException {
		final List<RemoteConfig> remotes = RemoteConfig
				.getAllRemoteConfigs(repo.getConfig());

		this.nicePushName = name;
		this.pushObj = objectId;
		this.repo = repo;

		repoPage = new RepositorySelectionPage(false, remotes, null);
		targetPage = new SimplePushSpecPage(nicePushName, repo) {
			@Override
			public void setVisible(boolean visible) {
				super.setVisible(visible);

				if (visible) {
					if(assist != null && assist.getRepository().equals(SimplePushRefWizard.this.repo))
						return;

					assist = new RefContentAssistProvider(
							SimplePushRefWizard.this.repo, repoPage
									.getSelection().getConfig(), getShell());

					updateDestinationField();
				}
			}
		};
	}

	@Override
	public void addPages() {
		addPage(repoPage);
		addPage(targetPage);
	}

	@Override
	public boolean performFinish() {

		try {
			int timeout = Activator.getDefault().getPreferenceStore()
					.getInt(UIPreferences.REMOTE_CONNECTION_TIMEOUT);

			PushOperationSpecification specification = new PushOperationSpecification();
			RepositorySelection remote = repoPage.getSelection();

			RemoteRefUpdate update = new RemoteRefUpdate(repo, null, pushObj,
					targetPage.getTargetRef(), targetPage.isForceUpdate(), null, null);

			specification.addURIRefUpdates(remote.getURI(true), Collections.singleton(update));

			PushOperation pop = new PushOperation(repo, specification, false,
					timeout);

			PushJob job = new PushWizard.PushJob(repo, pop, null,
					PushWizard.getDestinationString(remote));
			job.setUser(true);
			job.schedule();

			repoPage.saveUriInPrefs();
		} catch (Exception e) {
			Activator.handleError(e.getMessage(), e, true);
		}

		return true;
	}

}
