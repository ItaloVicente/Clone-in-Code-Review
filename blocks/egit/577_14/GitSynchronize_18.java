package org.eclipse.egit.ui.internal.synchronize;

import org.eclipse.egit.core.synchronize.GitResourceVariantTreeSubscriber;
import org.eclipse.egit.core.synchronize.dto.GitSynchronizeDataSet;
import org.eclipse.egit.ui.UIText;
import org.eclipse.team.core.subscribers.Subscriber;
import org.eclipse.team.core.variants.ResourceVariantByteStore;
import org.eclipse.team.core.variants.SessionResourceVariantByteStore;
import org.eclipse.team.ui.synchronize.ISynchronizePageConfiguration;
import org.eclipse.team.ui.synchronize.SubscriberParticipant;
import org.eclipse.team.ui.synchronize.SynchronizePageActionGroup;

public class GitSubscriberParticipant extends SubscriberParticipant {

	public static final String PARTICIPANT_NAME = "org.eclipse.egit.ui.synchronizeParticipant"; //$NON-NLS-1$

	public GitSubscriberParticipant(GitSynchronizeDataSet data) {
		ResourceVariantByteStore store = new SessionResourceVariantByteStore();
		setSubscriber(new GitResourceVariantTreeSubscriber(data, store));
		setName(UIText.GitBranchSubscriberParticipant_git);
	}

	@Override
	protected void initializeConfiguration(
			ISynchronizePageConfiguration configuration) {
		super.initializeConfiguration(configuration);

		configuration.addActionContribution(new SynchronizePageActionGroup() {
			public void initialize(
					ISynchronizePageConfiguration pageConfiguration) {
				super.initialize(pageConfiguration);
				appendToGroup(ISynchronizePageConfiguration.P_CONTEXT_MENU,
						ISynchronizePageConfiguration.SYNCHRONIZE_GROUP,
						new CommitAction(pageConfiguration));
			}
		});

		configuration
				.setSupportedModes(ISynchronizePageConfiguration.ALL_MODES);
		configuration.setMode(ISynchronizePageConfiguration.BOTH_MODE);
	}

	@Override
	public String getId() {
		return PARTICIPANT_NAME;
	}

	@Override
	public String getSecondaryId() {
		return "secondaryId"; //$NON-NLS-1$
	}

	public void refresh(GitSynchronizeDataSet data) {
		refresh(data.getAllResources(),
				UIText.GitSynchronizeWizard_gitResourceSynchronization, null,
				null);
	}

	void reset(GitSynchronizeDataSet data) {
		GitResourceVariantTreeSubscriber subscriber = (GitResourceVariantTreeSubscriber) getSubscriber();
		subscriber.reset(data);
		reset();
	}

}
