package org.eclipse.egit.ui.internal.synchronize;

import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.egit.ui.UIText;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.team.ui.synchronize.ISynchronizePageConfiguration;
import org.eclipse.team.ui.synchronize.SubscriberParticipant;
import org.eclipse.team.ui.synchronize.SynchronizePageActionGroup;

class GitBranchSubscriberParticipant extends SubscriberParticipant {

	GitBranchSubscriberParticipant(Map<Repository, String> branches,
			IResource[] roots) {
		setSubscriber(new GitBranchResourceVariantTreeSubscriber(branches,
				roots));
		setName(UIText.GitBranchSubscriberParticipant_git);
	}

	void reset(Map<Repository, String> branches, IResource[] roots) {
		GitBranchResourceVariantTreeSubscriber subscriber = (GitBranchResourceVariantTreeSubscriber) getSubscriber();
		subscriber.reset(branches, roots);
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
		return "org.eclipse.egit.ui.synchronizeParticipant"; //$NON-NLS-1$
	}

	@Override
	public String getSecondaryId() {
		return "secondaryId"; //$NON-NLS-1$
	}

}
