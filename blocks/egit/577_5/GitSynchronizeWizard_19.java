package org.eclipse.egit.ui.internal.synchronize;

import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.egit.ui.UIText;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.team.core.TeamException;
import org.eclipse.team.ui.TeamUI;
import org.eclipse.team.ui.synchronize.ISynchronizeManager;
import org.eclipse.team.ui.synchronize.ISynchronizeParticipant;
import org.eclipse.team.ui.synchronize.ISynchronizeParticipantReference;

public class GitSynchronize {

	public GitSynchronize(Map<Repository, String> branches, IResource[] resources) {

		GitBranchSubscriberParticipant participant = getParicipiant(branches, resources);
		participant.refresh(resources, UIText.GitSynchronizeWizard_gitResourceSynchronization, null,
				null);
	}

	private GitBranchSubscriberParticipant getParicipiant(
			Map<Repository, String> branches, IResource[] resources) {

		ISynchronizeManager synchronizeManager = TeamUI.getSynchronizeManager();
		ISynchronizeParticipantReference[] participants = synchronizeManager
				.get(GitBranchSubscriberParticipant.PARTICIPANT_NAME);

		GitBranchSubscriberParticipant participant;

		if (participants.length == 0) {
			participant = createParticipant(branches, resources);
		} else {
			try {
				participant = (GitBranchSubscriberParticipant) participants[0].getParticipant();
				participant.reset(branches, resources);
			} catch (TeamException e1) {
				participant = createParticipant(branches, resources);
			}
		}
		return participant;
	}

	private GitBranchSubscriberParticipant createParticipant(Map<Repository, String> branches, IResource[] resources) {
		GitBranchSubscriberParticipant participant = new GitBranchSubscriberParticipant(branches, resources);
		TeamUI.getSynchronizeManager().addSynchronizeParticipants(new ISynchronizeParticipant[] { participant });

		return participant;
	}

}
